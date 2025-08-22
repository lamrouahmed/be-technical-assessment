package travel.cupid.connectors.cupid.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CupidFacilityTranslation(
    @JsonProperty("lang")
    val language: String,
    val facility: String,
)
