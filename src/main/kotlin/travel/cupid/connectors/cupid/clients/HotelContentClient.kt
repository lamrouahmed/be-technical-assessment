package travel.cupid.connectors.cupid.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import travel.cupid.connectors.cupid.clients.config.HotelContentClientConfig
import travel.cupid.connectors.cupid.interceptors.CupidApiInterceptors
import travel.cupid.connectors.cupid.model.CupidHotel
import travel.cupid.connectors.cupid.model.CupidHotelReview

@FeignClient(name = "hotel-content-client", url = "\${connectors.cupid.api.url}/property", configuration = [CupidApiInterceptors::class, HotelContentClientConfig::class])
interface HotelContentClient {
    @GetMapping("/{propertyId}")
    fun getHotel(
        @PathVariable("propertyId") id: Long,
    ): CupidHotel

    @GetMapping("/{propertyId}/lang/{language}")
    fun getHotel(
        @PathVariable("propertyId") id: Long,
        @PathVariable("language") language: String,
    ): CupidHotel

    @GetMapping("reviews/{propertyId}/{size}")
    fun getHotelReviews(
        @PathVariable("propertyId") id: Long,
        @PathVariable("size") size: Int,
    ): List<CupidHotelReview>
}
