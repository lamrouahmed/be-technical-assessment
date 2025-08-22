package travel.cupid.service

import travel.cupid.common.dto.PageableRequest
import travel.cupid.common.dto.PagedResponse
import travel.cupid.rest.gethoteldetails.v1.HotelDetailsResponseDto
import travel.cupid.rest.gethotelreviews.v1.HotelReviewResponseDto

interface HotelManagementService {
    fun getHotelDetailsById(
        hotelId: Long,
        language: String,
    ): HotelDetailsResponseDto

    fun getHotelDetailsByExternalId(
        externalHotelId: Long,
        provider: String,
        language: String,
    ): HotelDetailsResponseDto

    fun getHotelReviews(
        hotelId: Long,
        pageableRequest: PageableRequest,
    ): PagedResponse<HotelReviewResponseDto>
}
