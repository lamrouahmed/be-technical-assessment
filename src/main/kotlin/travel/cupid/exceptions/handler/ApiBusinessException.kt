package travel.cupid.exceptions.handler

import org.springframework.http.HttpStatus
import travel.cupid.common.error.KeyValueError

open class ApiBusinessException(
    private val keyValueError: KeyValueError,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
) : Exception(keyValueError.msgKey) {
    val code: Int get() = keyValueError.code
    val msgKey: String get() = keyValueError.msgKey
}
