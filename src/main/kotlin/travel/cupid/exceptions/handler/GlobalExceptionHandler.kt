package travel.cupid.exceptions.handler

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import travel.cupid.common.error.ApiErrorResponse
import travel.cupid.i18n.AppMessageSource

@RestControllerAdvice
class GlobalExceptionHandler(
    private val appMessageSource: AppMessageSource,
) {
    @ExceptionHandler(ApiBusinessException::class)
    fun handleApiBusinessException(exception: ApiBusinessException): ResponseEntity<ApiErrorResponse> {
        val apiErrorResponse = ApiErrorResponse(exception.code, appMessageSource.getMessage(exception.msgKey) ?: "")
        return ResponseEntity.status(exception.status).body(apiErrorResponse)
    }

    @ExceptionHandler(ThirdPartyApiException::class)
    fun handleThirdPartyApiException(exception: ThirdPartyApiException): ResponseEntity<ApiErrorResponse> {
        val apiErrorResponse = ApiErrorResponse(exception.code, appMessageSource.getMessage(exception.msgKey) ?: "")
        return ResponseEntity.status(exception.status).body(apiErrorResponse)
    }
}
