package travel.cupid.rest.gethotelreviews.v1

import travel.cupid.model.HotelReview
import java.io.Serializable
import java.time.Instant

data class HotelReviewResponseDto(
    val id: Long?,
    val country: String?,
    val type: String?,
    val name: String?,
    val headline: String?,
    val pros: String?,
    val cons: String?,
    val source: String?,
    val reviewedAt: Instant?,
) : Serializable {
    companion object {
        fun from(hotelReview: HotelReview): HotelReviewResponseDto = HotelReviewResponseDto(
            id = hotelReview.id,
            country = hotelReview.country,
            type = hotelReview.type,
            name = hotelReview.name,
            headline = hotelReview.headline,
            pros = hotelReview.pros,
            cons = hotelReview.cons,
            source = hotelReview.source,
            reviewedAt = hotelReview.reviewedAt,
        )

        fun from(hotelReviews: Set<HotelReview>): Set<HotelReviewResponseDto> = hotelReviews.map { from(it) }.toSet()
    }
}
