package travel.cupid.model

import jakarta.persistence.*
import java.io.Serializable

@Table(name = "room_beds")
@Entity
class RoomBed : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_beds_seq")
    @SequenceGenerator(name = "room_beds_seq", sequenceName = "room_beds_seq", allocationSize = 50)
    var id: Long? = null

    var externalId: Int? = null
    var quantity: Int? = null
    var type: String? = null
    var size: String? = null
}
