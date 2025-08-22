package travel.cupid.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import travel.cupid.model.Hotel

interface HotelRepository : JpaRepository<Hotel, Long> {
    fun findByExternalId(externalId: Long): Hotel?

    @Query(
        """
        SELECT DISTINCT h FROM Hotel h 
        LEFT JOIN FETCH h.translations ht
        LEFT JOIN FETCH h.rooms r
        LEFT JOIN FETCH r.translations rt
        WHERE h.id = :id 
        AND (ht.language = :language OR ht.language IS NULL)
        AND (rt.language = :language OR rt.language IS NULL)
    """,
    )
    fun findByIdWithTranslation(
        id: Long,
        language: String,
    ): Hotel?
}
