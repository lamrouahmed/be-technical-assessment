package travel.cupid.repository

import org.springframework.data.jpa.repository.JpaRepository
import travel.cupid.model.HotelRoomTranslation

interface HotelRoomTranslationRepository : JpaRepository<HotelRoomTranslation, Long>
