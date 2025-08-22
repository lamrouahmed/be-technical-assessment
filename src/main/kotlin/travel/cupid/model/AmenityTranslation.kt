package travel.cupid.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(
    name = "amenity_translations",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["amenity_id", "language"]),
    ],
    indexes = [
        Index(name = "idx_amenity_translations_language", columnList = "language"),
    ],
)
class AmenityTranslation : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "amenity_translations_seq")
    @SequenceGenerator(name = "amenity_translations_seq", sequenceName = "amenity_translations_seq", allocationSize = 50)
    var id: Int? = null

    @Column(nullable = false)
    var language: String? = null

    @Column(nullable = false, length = 1000)
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "amenity_id", nullable = false)
    var amenity: Amenity? = null
}
