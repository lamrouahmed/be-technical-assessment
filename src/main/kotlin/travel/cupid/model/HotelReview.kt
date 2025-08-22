package travel.cupid.model

import jakarta.persistence.*
import java.io.Serializable
import java.time.Instant

@Entity
@Table(name = "hotel_reviews")
class HotelReview : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_reviews_seq")
    @SequenceGenerator(name = "hotel_reviews_seq", sequenceName = "hotel_reviews_seq", allocationSize = 50)
    var id: Long? = null

    var externalId: Long? = null
    var provider: String? = null
    var country: String? = null
    var type: String? = null
    var name: String? = null

    @Column(columnDefinition = "TEXT")
    var headline: String? = null

    var language: String? = null

    @Column(columnDefinition = "TEXT")
    var pros: String? = null

    @Column(columnDefinition = "TEXT")
    var cons: String? = null

    var source: String? = null
    var reviewedAt: Instant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    var hotel: Hotel? = null
}
