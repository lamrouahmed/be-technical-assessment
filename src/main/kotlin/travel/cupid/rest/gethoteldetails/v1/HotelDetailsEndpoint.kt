package travel.cupid.rest.gethoteldetails.v1

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import travel.cupid.service.HotelManagementService

@RestController
@RequestMapping("api/v1/hotels")
@Tag(name = "Hotel Details Endpoint")
class HotelDetailsEndpoint(private val hotelService: HotelManagementService) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(HotelDetailsEndpoint::class.java)
    }

    @GetMapping("/{hotelId}")
    @Operation(summary = "Get hotel details for a specific hotel via the id")
    fun getHotelDetails(
        @Parameter(description = "The hotel id", example = "1") @PathVariable("hotelId") hotelId: Long,
        @Parameter(description = "Language code", example = "en") @RequestParam("language", defaultValue = "en") language: String,
    ): HotelDetailsResponseDto {
        log.info("getHotelDetails: Getting hotel details for $hotelId using language $language")
        return hotelService.getHotelDetailsById(hotelId, language)
    }

    @GetMapping("/providers/{provider}/{externalHotelId}")
    @Operation(summary = "Get hotel details for a specific hotel using the external id + the provider")
    fun getHotelDetailsByExternalId(
        @Parameter(description = "Provider name", example = "cupid") @PathVariable("provider") provider: String,
        @Parameter(description = "External hotel id", example = "1641879") @PathVariable("externalHotelId") externalHotelId: Long,
        @Parameter(description = "Language code", example = "en") @RequestParam("language", defaultValue = "en") language: String,
    ): HotelDetailsResponseDto {
        log.info("getHotelDetailsByExternalId: Getting external hotel details for $externalHotelId using language $language")
        return hotelService.getHotelDetailsByExternalId(externalHotelId, provider, language)
    }
}
