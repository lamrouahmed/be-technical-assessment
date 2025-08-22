package travel.cupid.service

import travel.cupid.rest.gethoteldetails.v1.HotelDetailsResponseDto

interface HotelLookupService {
    fun getHotel(
        id: Long,
        language: String,
    ): HotelDetailsResponseDto
}
