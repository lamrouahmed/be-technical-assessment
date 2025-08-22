package travel.cupid.model

import jakarta.persistence.*
import travel.cupid.model.embeddables.Description
import java.io.Serializable

@Entity
@Table(
    name = "hotel_translations",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["hotel_id", "language"]),
    ],
    indexes = [
        Index(name = "idx_hotel_translations_language", columnList = "language"),
    ],
)
class HotelTranslation : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_translations_seq")
    @SequenceGenerator(name = "hotel_translations_seq", sequenceName = "hotel_translations_seq", allocationSize = 50)
    var id: Long? = null

    @Column(nullable = false)
    var language: String? = null

    @Embedded
    var description: Description? = null

    @Column(columnDefinition = "TEXT")
    var importantInfo: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    var hotel: Hotel? = null
}
