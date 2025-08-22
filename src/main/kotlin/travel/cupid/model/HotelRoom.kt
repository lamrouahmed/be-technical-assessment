package travel.cupid.model

import jakarta.persistence.*
import travel.cupid.model.embeddables.RoomSize
import java.io.Serializable

@Entity
@Table(
    name = "hotel_rooms",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["external_id", "provider"]),
    ],
)
class HotelRoom : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_rooms_seq")
    @SequenceGenerator(name = "hotel_rooms_seq", sequenceName = "hotel_rooms_seq", allocationSize = 50)
    var id: Long? = null

    var name: String? = null

    var externalId: Long? = null
    var provider: String? = null

    @Column(columnDefinition = "TEXT")
    var description: String? = null

    var size: RoomSize? = null
    var maxChildren: Int? = null
    var maxAdults: Int? = null
    var maxOccupancy: Int? = null

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "room_id")
    var beds: MutableSet<RoomBed> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "room_amenities",
        joinColumns = [JoinColumn(name = "room_id")],
        inverseJoinColumns = [JoinColumn(name = "amenity_id")],
    )
    var amenities: MutableSet<Amenity> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "room_id")
    var photos: MutableSet<HotelRoomPhoto> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "hotelRoom")
    var translations: MutableSet<HotelRoomTranslation> = mutableSetOf()
}
