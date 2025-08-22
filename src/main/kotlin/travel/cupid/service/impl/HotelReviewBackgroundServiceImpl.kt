package travel.cupid.service.impl

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import travel.cupid.connectors.cupid.model.CupidHotelReview
import travel.cupid.mapper.toEntity
import travel.cupid.model.Hotel
import travel.cupid.repository.HotelReviewRepository
import travel.cupid.service.HotelReviewBackgroundService

@Service
class HotelReviewBackgroundServiceImpl(
    private val hotelReviewRepository: HotelReviewRepository,
) : HotelReviewBackgroundService {
    @Async
    override fun saveHotelReviews(
        hotel: Hotel,
        reviews: List<CupidHotelReview>,
    ) {
        val entities = reviews.map { it.toEntity(hotel) }
        hotelReviewRepository.saveAll(entities)
    }
}
