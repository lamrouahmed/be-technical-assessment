package travel.cupid.connectors.cupid.clients.errordecoders

import feign.codec.ErrorDecoder
import travel.cupid.connectors.cupid.exceptions.HotelReferenceDataClientException

class HotelReferenceDataClientErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: feign.Response): Exception = HotelReferenceDataClientException()
}
