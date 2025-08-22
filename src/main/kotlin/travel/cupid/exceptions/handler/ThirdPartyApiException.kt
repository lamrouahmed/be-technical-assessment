package travel.cupid.exceptions.handler

import org.springframework.http.HttpStatus
import travel.cupid.common.error.KeyValueError

open class ThirdPartyApiException(
    private val keyValueError: KeyValueError,
    val status: HttpStatus = HttpStatus.BAD_GATEWAY,
) : Exception(keyValueError.msgKey) {
    val code: Int get() = keyValueError.code
    val msgKey: String get() = keyValueError.msgKey
}
