package travel.cupid.connectors.cupid.clients.config

import org.springframework.context.annotation.Bean
import travel.cupid.connectors.cupid.clients.errordecoders.HotelContentClientErrorDecoder

class HotelContentClientConfig {
    @Bean
    fun hotelContentClientErrorDecoder(): HotelContentClientErrorDecoder = HotelContentClientErrorDecoder()
}
