package travel.cupid.connectors.cupid.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CupidRoomAmenity(
    val amenityId: Long,
    val amenity: String,
    val sort: Int,
    val translation: List<CupidAmenityTranslation>,
)
