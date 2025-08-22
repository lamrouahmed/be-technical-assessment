package travel.cupid.model.embeddables

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import travel.cupid.model.enums.RoomSizeUnit
import java.io.Serializable

@Embeddable
class RoomSize : Serializable {
    @Column(name = "room_size")
    var value: Float = 0f

    @Enumerated(EnumType.STRING)
    @Column(name = "room_size_unit")
    var unit: RoomSizeUnit? = null
}
