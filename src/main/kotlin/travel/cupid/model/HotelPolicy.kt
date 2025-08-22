package travel.cupid.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "hotel_policies")
class HotelPolicy : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_policies_seq")
    @SequenceGenerator(name = "hotel_policies_seq", sequenceName = "hotel_policies_seq", allocationSize = 50)
    var id: Long? = null

    var externalId: Long? = null
    var provider: String? = null
    var name: String? = null
    var type: String? = null

    @Column(columnDefinition = "TEXT")
    var description: String? = null

    var childAllowed: String? = null
    var petsAllowed: String? = null
    var parking: String? = null
}
