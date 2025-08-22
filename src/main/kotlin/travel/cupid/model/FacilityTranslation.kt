package travel.cupid.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(
    name = "facility_translations",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["facility_id", "language"]),
    ],
    indexes = [
        Index(name = "idx_facility_translations_language", columnList = "language"),
    ],
)
class FacilityTranslation : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facility_translations_seq")
    @SequenceGenerator(name = "facility_translations_seq", sequenceName = "facility_translations_seq", allocationSize = 50)
    var id: Int? = null

    @Column(nullable = false)
    var language: String? = null

    @Column(nullable = false, length = 1000)
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "facility_id", nullable = false)
    var facility: Facility? = null
}
