package travel.cupid.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import travel.cupid.model.HotelReview

interface HotelReviewRepository : JpaRepository<HotelReview, Long> {
    fun existsByHotelId(id: Long): Boolean

    fun findByHotelId(
        hotelId: Long,
        pageable: Pageable,
    ): Page<HotelReview>
}
