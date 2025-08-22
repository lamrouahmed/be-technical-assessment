package travel.cupid.common.error

enum class GenericError(
    override val code: Int,
    override val msgKey: String,
) : KeyValueError {
    TECHNICAL_ERROR(500, "app.technical.error"),
}
