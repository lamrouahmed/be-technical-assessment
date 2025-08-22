package travel.cupid.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import travel.cupid.connectors.cupid.clients.HotelContentClient
import travel.cupid.connectors.cupid.exceptions.CupidHotelNotFoundException
import travel.cupid.connectors.cupid.model.CupidHotel
import travel.cupid.exceptions.HotelNotFoundException
import travel.cupid.mapper.toTranslationEntity
import travel.cupid.model.Hotel
import travel.cupid.model.HotelRoomTranslation
import travel.cupid.model.HotelTranslation
import travel.cupid.repository.HotelRoomTranslationRepository
import travel.cupid.repository.HotelTranslationRepository
import travel.cupid.service.HotelTranslationService

@Service
class HotelTranslationServiceImpl(
    private val hotelTranslationRepository: HotelTranslationRepository,
    private val hotelContentClient: HotelContentClient,
    private val hotelRoomTranslationRepository: HotelRoomTranslationRepository,
) : HotelTranslationService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Cacheable(value = ["hotel-translation"], key = "#hotel.id + ':' + #lang", sync = true)
    override fun translateHotel(
        hotel: Hotel,
        lang: String,
    ): HotelTranslation = hotelTranslationRepository.findByHotelIdAndLanguage(hotel.id!!, lang) ?: fetchAndStoreTranslation(hotel, lang)

    private fun fetchAndStoreTranslation(
        hotel: Hotel,
        lang: String,
    ): HotelTranslation {
        try {
            val externalTranslatedHotel = hotelContentClient.getHotel(hotel.externalId!!, lang)
            val hotelTranslation = hotelTranslationRepository.save(externalTranslatedHotel.toTranslationEntity(hotel, lang))
            saveRoomTranslations(hotel, externalTranslatedHotel, lang)
            return hotelTranslation
        } catch (e: CupidHotelNotFoundException) {
            log.error("translateHotel: Hotel not found for external hotel id ${hotel.externalId}", e)
            throw HotelNotFoundException()
        }
    }

    private fun saveRoomTranslations(
        hotel: Hotel,
        translatedHotel: CupidHotel,
        language: String,
    ) {
        val translations = mutableListOf<HotelRoomTranslation>()

        translatedHotel.rooms.forEach { externalRoom ->
            val room = hotel.rooms.find { it.externalId == externalRoom.id }
            if (room != null) {
                val translation =
                    HotelRoomTranslation().apply {
                        this.language = language
                        this.description = externalRoom.description
                        this.hotelRoom = room
                        this.name = externalRoom.roomName
                    }
                translations.add(hotelRoomTranslationRepository.save(translation))
                room.translations.add(translation)
            }
        }
    }
}
