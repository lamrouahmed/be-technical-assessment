package travel.cupid.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import travel.cupid.common.dto.PageableRequest
import travel.cupid.common.dto.PagedResponse
import travel.cupid.common.extensions.toPagedResponse
import travel.cupid.connectors.cupid.clients.HotelContentClient
import travel.cupid.connectors.cupid.exceptions.CupidHotelNotFoundException
import travel.cupid.exceptions.HotelNotFoundException
import travel.cupid.mapper.toEntity
import travel.cupid.model.Hotel
import travel.cupid.repository.*
import travel.cupid.rest.gethoteldetails.v1.HotelDetailsResponseDto
import travel.cupid.rest.gethotelreviews.v1.HotelReviewResponseDto
import travel.cupid.service.HotelLookupService
import travel.cupid.service.HotelManagementService
import travel.cupid.service.HotelReviewBackgroundService
import travel.cupid.service.ReferenceDataService

@Service
class HotelManagementServiceImpl(
    private val hotelRepository: HotelRepository,
    private val hotelReviewRepository: HotelReviewRepository,
    private val facilityRepository: FacilityRepository,
    private val hotelContentClient: HotelContentClient,
    private val amenityRepository: AmenityRepository,
    private val hotelReviewBackgroundService: HotelReviewBackgroundService,
    private val referenceDataService: ReferenceDataService,
    private val hotelLookupService: HotelLookupService,
    @Value("\${app.sync.reviews-batch-size}") private val hotelReviewBatchSize: Int,
) : HotelManagementService {

    companion object {
        val log: Logger = LoggerFactory.getLogger(HotelManagementServiceImpl::class.java)
    }

    @Transactional
    override fun getHotelDetailsById(hotelId: Long, language: String): HotelDetailsResponseDto {
        log.info("getHotelDetailsById: Getting hotel details for $hotelId using language $language")
        referenceDataService.ensureAmenitiesLoaded()
        referenceDataService.ensureFacilitiesLoaded()
        return hotelLookupService.getHotel(hotelId, language)
    }

    @Transactional
    @Cacheable(value = ["external-hotel"], key = "#externalHotelId + ':' + #provider + ':' + #language", sync = true)
    override fun getHotelDetailsByExternalId(externalHotelId: Long, provider: String, language: String): HotelDetailsResponseDto {
        log.info("getHotelDetailsByExternalId: Getting hotel details for $externalHotelId and provider $provider using language $language")

        referenceDataService.ensureAmenitiesLoaded()
        referenceDataService.ensureFacilitiesLoaded()

        val hotel = hotelRepository.findByExternalId(externalHotelId) ?: syncAndPersist(externalHotelId)
        return hotelLookupService.getHotel(hotel.id!!, language)
    }

    @Transactional
    @Cacheable(value = ["hotel-review"], key = "#hotelId + ':' + #pageableRequest.page + ':' + #pageableRequest.size", sync = true)
    override fun getHotelReviews(hotelId: Long, pageableRequest: PageableRequest): PagedResponse<HotelReviewResponseDto> {
        log.info("getHotelReviews: Getting reviews for hotelId: $hotelId using page: ${pageableRequest.page} and size: ${pageableRequest.size}")

        val hotel = hotelRepository.findByIdOrNull(hotelId)
            ?: throw HotelNotFoundException()
        val pageable = Pageable.ofSize(pageableRequest.size).withPage(pageableRequest.page)

        if (hotelReviewRepository.existsByHotelId(hotelId)) {
            log.info("getHotelReviews: Using cached reviews for hotelId: $hotelId")
            val page = hotelReviewRepository.findByHotelId(hotelId, pageable)
            return page.map { HotelReviewResponseDto.from(it) }.toPagedResponse()
        }

        val hotelReviews = try {
            log.info("getHotelReviews: Fetching reviews for hotelId: $hotelId")
            hotelContentClient.getHotelReviews(hotel.externalId!!, hotel.reviewsCount ?: 0)
        } catch (e: CupidHotelNotFoundException) {
            log.error("getHotelReviews: Hotel not found for external hotel id ${hotel.externalId}", e)
            throw HotelNotFoundException()
        }

        val firstBatch = hotelReviews.take(hotelReviewBatchSize)
        val remaining = hotelReviews.drop(hotelReviewBatchSize)
        val hotelReviewEntities = firstBatch.map { it.toEntity(hotel) }

        hotelReviewRepository.saveAll(hotelReviewEntities)
        val page = hotelReviewRepository.findByHotelId(hotelId, pageable)
        hotelReviewBackgroundService.saveHotelReviews(hotel, remaining)

        return page.map { HotelReviewResponseDto.from(it) }.toPagedResponse()
    }

    private fun syncAndPersist(externalHotelId: Long): Hotel {
        log.info("syncAndPersist: Syncing and persisting external hotel $externalHotelId")
        return try {
            val externalHotel = hotelContentClient.getHotel(externalHotelId)
            val hotelFacilities = facilityRepository.findByExternalIdIn(externalHotel.facilities.map { it.facilityId }.toMutableSet())
            val uniqueAmenitiesIds = externalHotel.rooms
                .flatMap { room -> room.roomAmenities.map { it.amenitiesId } }
                .toSet()
            val amenities = amenityRepository.findByExternalIdIn(uniqueAmenitiesIds)
            hotelRepository.save(externalHotel.toEntity(hotelFacilities, amenities.associateBy { it.externalId }))
        } catch (e: CupidHotelNotFoundException) {
            log.error("syncAndPersist: Hotel not found for external hotel id $externalHotelId", e)
            throw HotelNotFoundException()
        }
    }
}
