package travel.cupid.common.dto

import java.io.Serializable

data class PagedResponse<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalElements: Long,
    val numberOfElements: Int,
) : Serializable
