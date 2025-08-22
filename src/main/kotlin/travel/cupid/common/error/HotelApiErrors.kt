package travel.cupid.common.error

enum class HotelApiErrors(
    override val code: Int,
    override val msgKey: String,
) : KeyValueError {
    HOTEL_NOT_FOUND(1000, "hotel.not.found"),
    UNSUPPORTED_LANGUAGE(1001, "hotel.language.not.supported"),
    REFERENCE_DATA_SYNC_ERROR(1002, "reference.data.sync.error"),
}
