package travel.cupid.common.dto

data class PageableRequest(
    val size: Int = 10,
    val page: Int = 0,
)
