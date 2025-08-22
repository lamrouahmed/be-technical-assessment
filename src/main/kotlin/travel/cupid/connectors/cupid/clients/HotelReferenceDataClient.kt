package travel.cupid.connectors.cupid.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import travel.cupid.connectors.cupid.clients.config.HotelReferenceDataClientConfig
import travel.cupid.connectors.cupid.interceptors.CupidApiInterceptors
import travel.cupid.connectors.cupid.model.CupidFacility
import travel.cupid.connectors.cupid.model.CupidRoomAmenity

@FeignClient(
    name = "hotel-reference-data-client",
    url = "\${connectors.cupid.api.url}/property",
    configuration = [CupidApiInterceptors::class, HotelReferenceDataClientConfig::class],
)
interface HotelReferenceDataClient {
    @GetMapping("/room_amenities")
    fun getRoomAmenities(): List<CupidRoomAmenity>

    @GetMapping("/facilities")
    fun getFacilities(): List<CupidFacility>
}
