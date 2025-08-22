package travel.cupid.service

interface ReferenceDataService {
    fun ensureAmenitiesLoaded(): Boolean

    fun ensureFacilitiesLoaded(): Boolean
}
