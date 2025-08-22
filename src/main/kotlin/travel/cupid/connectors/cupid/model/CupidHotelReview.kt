package travel.cupid.connectors.cupid.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CupidHotelReview(
    val reviewId: Long,
    val averageScore: Int,
    val country: String,
    val type: String,
    val name: String,
    val date: String,
    val headline: String,
    val language: String,
    val pros: String,
    val cons: String,
    val source: String,
)
