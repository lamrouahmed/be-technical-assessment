package travel.cupid.service.impl

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import travel.cupid.service.ReferenceDataService
import travel.cupid.service.ReferentialDataETLService

@Service
class ReferenceDataServiceImpl(
    private val referenceDataETLService: ReferentialDataETLService,
) : ReferenceDataService {
    @Cacheable(value = ["amenities"], key = "'all'", sync = true)
    override fun ensureAmenitiesLoaded(): Boolean {
        referenceDataETLService.syncRoomAmenities()
        return true
    }

    @Cacheable(value = ["facilities"], key = "'all'", sync = true)
    override fun ensureFacilitiesLoaded(): Boolean {
        referenceDataETLService.syncFacilities()
        return true
    }
}
