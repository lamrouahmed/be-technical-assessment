package travel.cupid.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(
    name = "hotel_room_translations",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["room_id", "language"]),
    ],
    indexes = [
        Index(name = "idx_hotel_room_translations_language", columnList = "language"),
    ],
)
class HotelRoomTranslation : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_room_translations_seq")
    @SequenceGenerator(name = "hotel_room_translations_seq", sequenceName = "hotel_room_translations_seq", allocationSize = 50)
    var id: Long? = null

    @Column(nullable = false)
    var language: String? = null

    var name: String? = null

    @Column(columnDefinition = "TEXT")
    var description: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    var hotelRoom: HotelRoom? = null
}
