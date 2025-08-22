package travel.cupid.repository

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import travel.cupid.model.Amenity

@Repository
interface AmenityRepository : JpaRepository<Amenity, Int> {
    fun findByExternalId(cupidId: Long): Amenity?

    @Cacheable(value = ["amenities"], key = "#externalIds")
    fun findByExternalIdIn(externalIds: Set<Long>): List<Amenity>
}
