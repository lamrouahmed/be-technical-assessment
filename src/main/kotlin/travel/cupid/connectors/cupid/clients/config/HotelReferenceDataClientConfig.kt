package travel.cupid.connectors.cupid.clients.config

import org.springframework.context.annotation.Bean
import travel.cupid.connectors.cupid.clients.errordecoders.HotelReferenceDataClientErrorDecoder

class HotelReferenceDataClientConfig {
    @Bean
    fun hotelReferenceDataClientErrorDecoder(): HotelReferenceDataClientErrorDecoder = HotelReferenceDataClientErrorDecoder()
}
