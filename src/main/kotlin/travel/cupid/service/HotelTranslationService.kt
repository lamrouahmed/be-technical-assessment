package travel.cupid.service

import travel.cupid.model.Hotel
import travel.cupid.model.HotelTranslation

interface HotelTranslationService {
    fun translateHotel(
        hotel: Hotel,
        lang: String,
    ): HotelTranslation
}
