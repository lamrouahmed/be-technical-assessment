package travel.cupid.connectors.cupid.clients.errordecoders

import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import travel.cupid.connectors.cupid.exceptions.CupidHotelNotFoundException
import travel.cupid.connectors.cupid.exceptions.HotelContentClientException

class HotelContentClientErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: feign.Response): Exception {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return CupidHotelNotFoundException()
        }
        return HotelContentClientException()
    }
}
