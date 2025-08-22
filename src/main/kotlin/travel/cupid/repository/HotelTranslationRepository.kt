package travel.cupid.repository

import org.springframework.data.jpa.repository.JpaRepository
import travel.cupid.model.HotelTranslation

interface HotelTranslationRepository : JpaRepository<HotelTranslation, Long> {
    fun findByHotelIdAndLanguage(
        hotelId: Long,
        language: String,
    ): HotelTranslation?
}
