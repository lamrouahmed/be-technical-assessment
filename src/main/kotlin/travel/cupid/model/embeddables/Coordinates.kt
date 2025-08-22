package travel.cupid.model.embeddables

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class Coordinates(
    val latitude: Double? = null,
    val longitude: Double? = null,
) : Serializable
