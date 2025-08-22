package travel.cupid.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "hotel_room_photos")
class HotelRoomPhoto : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_room_photos_seq")
    @SequenceGenerator(name = "hotel_room_photos_seq", sequenceName = "hotel_room_photos_seq", allocationSize = 50)
    var id: Long? = null

    @Column(nullable = false)
    var url: String? = null

    @Column(nullable = false)
    var hdUrl: String? = null

    @Column(columnDefinition = "TEXT")
    var description: String? = null

    @Column(nullable = false)
    var isMain: Boolean? = false

    var score: Float? = null
}
