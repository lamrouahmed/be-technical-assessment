package travel.cupid.connectors.cupid.interceptors

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class CupidApiInterceptors(
    @Value("\${connectors.cupid.api.key}") val apiKey: String,
) {
    @Bean
    fun authHeaderInterceptor(): RequestInterceptor = RequestInterceptor { requestTemplate: RequestTemplate ->
        requestTemplate.header(
            "x-api-key",
            apiKey,
        )
    }
}
