package travel.cupid.model.enums

enum class RoomSizeUnit(
    val value: String,
) {
    M2("m2"),
    ;

    companion object {
        fun from(value: String): RoomSizeUnit? = entries.find { it.value.equals(value, ignoreCase = true) }
    }
}
