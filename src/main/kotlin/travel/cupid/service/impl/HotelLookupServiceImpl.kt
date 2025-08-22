package travel.cupid.service.impl

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import travel.cupid.common.enums.Language
import travel.cupid.exceptions.HotelNotFoundException
import travel.cupid.repository.HotelRepository
import travel.cupid.rest.gethoteldetails.v1.HotelDetailsResponseDto
import travel.cupid.service.HotelLookupService
import travel.cupid.service.HotelTranslationService

@Service
class HotelLookupServiceImpl(
    private val hotelRepository: HotelRepository,
    private val hotelTranslationService: HotelTranslationService,
) : HotelLookupService {

    @Cacheable(value = ["hotel"], key = "#id + ':' + #language", sync = true)
    override fun getHotel(
        id: Long,
        language: String,
    ): HotelDetailsResponseDto {
        val hotel = hotelRepository.findByIdOrNull(id) ?: throw HotelNotFoundException()
        val hotelTranslation = if (Language.getPrimary() == language) null else hotelTranslationService.translateHotel(hotel, language)
        return HotelDetailsResponseDto.from(hotel, hotelTranslation, language)
    }
}
