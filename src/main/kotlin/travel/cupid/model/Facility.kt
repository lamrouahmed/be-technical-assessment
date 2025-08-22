package travel.cupid.model

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant

@Entity
@Table(
    name = "facilities",
    indexes = [
        Index(name = "idx_facilities_external_id", columnList = "external_id"),
    ],
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["external_id", "provider"]),
    ],
)
@EntityListeners(AuditingEntityListener::class)
@SQLDelete(sql = "UPDATE facilities SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
class Facility : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facility_seq")
    @SequenceGenerator(name = "facility_seq", sequenceName = "facility_seq", allocationSize = 50)
    var id: Int? = null

    @Column(name = "external_id", updatable = false)
    var externalId: Long? = null

    @Column(updatable = false)
    var provider: String? = null

    @Column(nullable = false)
    var name: String? = null

    var sortOrder: Int? = null

    @CreatedDate
    var createdAt: Instant? = null

    @LastModifiedDate
    var updatedAt: Instant? = null

    var deletedAt: Instant? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "facility", orphanRemoval = true)
    var translations: MutableSet<FacilityTranslation> = mutableSetOf()
}
