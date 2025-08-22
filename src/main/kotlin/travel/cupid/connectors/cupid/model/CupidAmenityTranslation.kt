package travel.cupid.connectors.cupid.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CupidAmenityTranslation(
    @JsonProperty("lang")
    val language: String,
    val amenity: String,
)
