package travel.cupid.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import travel.cupid.common.error.HotelApiErrors
import travel.cupid.connectors.cupid.clients.HotelReferenceDataClient
import travel.cupid.connectors.cupid.exceptions.HotelReferenceDataClientException
import travel.cupid.exceptions.handler.ThirdPartyApiException
import travel.cupid.model.Amenity
import travel.cupid.model.AmenityTranslation
import travel.cupid.model.Facility
import travel.cupid.model.FacilityTranslation
import travel.cupid.repository.AmenityRepository
import travel.cupid.repository.FacilityRepository
import travel.cupid.service.ReferentialDataETLService

@Service
class ReferentialDataETLServiceImpl(
    private val amenityRepository: AmenityRepository,
    private val facilityRepository: FacilityRepository,
    private val hotelReferenceDataClient: HotelReferenceDataClient,
) : ReferentialDataETLService {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(ReferentialDataETLServiceImpl::class.java)
    }

    @Transactional
    override fun syncRoomAmenities() {
        log.info("Syncing room amenities")
        try {
            val roomAmenities = hotelReferenceDataClient.getRoomAmenities()

            if (roomAmenities.isEmpty()) return
            val uniqueAmenitiesIds = roomAmenities.map { it.amenityId }.toSet()
            val existingAmenities = amenityRepository.findByExternalIdIn(uniqueAmenitiesIds).associateBy { it.externalId }
            val amenitiesToSave =
                roomAmenities.map { externalAmenity ->
                    val amenity =
                        existingAmenities[externalAmenity.amenityId] ?: Amenity().apply {
                            externalId = externalAmenity.amenityId
                        }
                    amenity.name = externalAmenity.amenity
                    amenity.sortOrder = externalAmenity.sort
                    amenity.translations.clear()
                    amenity.translations.addAll(
                        externalAmenity.translation.map {
                            AmenityTranslation().apply {
                                language = it.language
                                name = it.amenity
                                this.amenity = amenity
                            }
                        },
                    )

                    amenity
                }

            amenityRepository.saveAll(amenitiesToSave)
        } catch (e: HotelReferenceDataClientException) {
            log.error("Failed to sync room amenities", e)
            throw ThirdPartyApiException(HotelApiErrors.REFERENCE_DATA_SYNC_ERROR)
        }
    }

    @Transactional
    override fun syncFacilities() {
        log.info("Syncing facilities")
        val facilities = hotelReferenceDataClient.getFacilities()

        if (facilities.isEmpty()) return

        val uniqueFacilitiesIds = facilities.map { it.facilityId }.toSet()
        val existingFacilities = facilityRepository.findByExternalIdIn(uniqueFacilitiesIds).associateBy { it.externalId }
        val facilitiesToSave =
            facilities.map { externalFacility ->
                val facility =
                    existingFacilities[externalFacility.facilityId] ?: Facility().apply {
                        externalId = externalFacility.facilityId
                    }
                facility.name = externalFacility.facility
                facility.sortOrder = externalFacility.sort
                facility.translations.clear()
                facility.translations.addAll(
                    externalFacility.translation.map {
                        FacilityTranslation().apply {
                            language = it.language
                            name = it.facility
                            this.facility = facility
                        }
                    },
                )

                facility
            }

        facilityRepository.saveAll(facilitiesToSave)
    }
}
