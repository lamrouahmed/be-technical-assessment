package travel.cupid.model

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import travel.cupid.model.embeddables.Address
import travel.cupid.model.embeddables.Coordinates
import travel.cupid.model.embeddables.Description
import java.io.Serializable
import java.time.Instant

@Table(
    name = "hotels",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["external_id", "provider"]),
    ],
)
@Entity
@SQLDelete(sql = "UPDATE hotels SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@EntityListeners(AuditingEntityListener::class)
class Hotel : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_seq")
    @SequenceGenerator(name = "hotel_seq", sequenceName = "hotel_seq", allocationSize = 50)
    var id: Long? = null

    @Column(name = "external_id", updatable = false)
    var externalId: Long? = null

    @Column(name = "provider", updatable = false)
    var provider: String? = null

    var mainImage: String? = null
    var chainId: Int? = null

    @Embedded
    var coordinates: Coordinates? = null

    @Embedded
    var address: Address? = null

    @Embedded
    var description: Description? = null

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "hotel_id")
    var photos: MutableSet<HotelPhoto> = mutableSetOf()

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "hotel_facilities",
        joinColumns = [JoinColumn(name = "hotel_id")],
        inverseJoinColumns = [JoinColumn(name = "facility_id")],
    )
    var facilities: MutableSet<Facility> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "hotel_id")
    var policies: MutableSet<HotelPolicy> = mutableSetOf()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "hotel_id")
    var rooms: MutableSet<HotelRoom> = mutableSetOf()

    var reviewsCount: Int? = null

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "hotel")
    var translations: MutableSet<HotelTranslation> = mutableSetOf()

    @CreatedDate
    var createdAt: Instant? = null

    @LastModifiedDate
    var updatedAt: Instant? = null

    var deletedAt: Instant? = null
}
