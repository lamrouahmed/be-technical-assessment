package travel.cupid.common.enums

enum class Language(
    val code: String,
    val isSupported: Boolean,
    val isPrimary: Boolean,
) {
    ENGLISH("en", isSupported = true, isPrimary = true),
    SPANISH("es", isSupported = true, isPrimary = false),
    FRENCH("fr", isSupported = true, isPrimary = false),
    ARABIC("ar", isSupported = false, isPrimary = false),
    ;

    companion object {
        fun from(code: String): Language? = entries.find { it.code.equals(code, ignoreCase = true) }

        fun getPrimary(): String = entries.first { it.isPrimary }.code
    }
}
