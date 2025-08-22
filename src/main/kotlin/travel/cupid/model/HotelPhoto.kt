package travel.cupid.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "hotel_photos")
class HotelPhoto : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_photos_seq")
    @SequenceGenerator(name = "hotel_photos_seq", sequenceName = "hotel_photos_seq", allocationSize = 50)
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
