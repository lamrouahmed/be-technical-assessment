package travel.cupid.repository

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import travel.cupid.model.Facility

@Repository
interface FacilityRepository : JpaRepository<Facility, Int> {
    @Cacheable(value = ["facility"], key = "#cupidId")
    fun findByExternalId(cupidId: Long): Facility?

    @Cacheable(value = ["facilities"], key = "#externalIds")
    fun findByExternalIdIn(externalIds: Set<Long>): List<Facility>
}
