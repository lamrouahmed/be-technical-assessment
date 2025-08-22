package travel.cupid.connectors.cupid.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CupidFacility(
    val facilityId: Long,
    val facility: String,
    val sort: Int,
    val translation: List<CupidFacilityTranslation>,
)
