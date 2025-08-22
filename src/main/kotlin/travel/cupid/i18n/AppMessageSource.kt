package travel.cupid.i18n

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

@Component
class AppMessageSource(
    private val messageSource: MessageSource,
) {
    fun getMessage(
        code: String,
        vararg args: Any?,
    ): String? {
        val locale = LocaleContextHolder.getLocale()
        return messageSource.getMessage(code, args, "", locale)
    }
}
