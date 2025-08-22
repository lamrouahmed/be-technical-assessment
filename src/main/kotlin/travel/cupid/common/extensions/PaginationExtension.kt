package travel.cupid.common.extensions

import org.springframework.data.domain.Page
import travel.cupid.common.dto.PagedResponse

fun <T> Page<T>.toPagedResponse(): PagedResponse<T> = PagedResponse(
    content = this.content,
    page = this.number,
    size = this.size,
    totalPages = this.totalPages,
    totalElements = this.totalElements,
    numberOfElements = this.numberOfElements,
)
