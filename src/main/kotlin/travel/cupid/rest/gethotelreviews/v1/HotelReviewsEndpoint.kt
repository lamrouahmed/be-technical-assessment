package travel.cupid.rest.gethotelreviews.v1

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import travel.cupid.common.dto.PageableRequest
import travel.cupid.common.dto.PagedResponse
import travel.cupid.rest.gethoteldetails.v1.HotelDetailsEndpoint
import travel.cupid.service.HotelManagementService

@RestController
@RequestMapping("api/v1/hotels")
@Tag(name = "Hotel Reviews Endpoint")
class HotelReviewsEndpoint(
    private val hotelService: HotelManagementService,
) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(HotelDetailsEndpoint::class.java)
    }

    @GetMapping("/{hotelId}/reviews")
    @Operation(summary = "Get hotel reviews for a specific hotel via the id")
    fun getHotelDetails(
        @PathVariable("hotelId") hotelId: Long,
        @ModelAttribute pageableRequest: PageableRequest,
    ): PagedResponse<HotelReviewResponseDto> {
        log.info("Getting reviews for hotelId: $hotelId")
        return hotelService.getHotelReviews(hotelId, pageableRequest)
    }
}
