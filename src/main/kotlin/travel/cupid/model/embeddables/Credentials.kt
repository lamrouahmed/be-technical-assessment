package travel.cupid.model.embeddables

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class Credentials : Serializable {
    var phone: String? = null
    var email: String? = null
    var fax: String? = null
}
