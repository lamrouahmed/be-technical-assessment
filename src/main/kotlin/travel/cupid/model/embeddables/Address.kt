package travel.cupid.model.embeddables

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class Address(
    val street: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val postalCode: String? = null,
) : Serializable
