package travel.cupid.model.embeddables

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class Description(
    @Column(name = "description", columnDefinition = "TEXT")
    var text: String? = null,
    @Column(name = "markdown_description", columnDefinition = "TEXT")
    var markdown: String? = null,
) : Serializable
