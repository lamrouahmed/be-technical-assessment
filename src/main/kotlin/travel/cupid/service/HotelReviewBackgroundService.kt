package travel.cupid.service

import travel.cupid.connectors.cupid.model.CupidHotelReview
import travel.cupid.model.Hotel

interface HotelReviewBackgroundService {
    fun saveHotelReviews(
        hotel: Hotel,
        reviews: List<CupidHotelReview>,
    )
}
