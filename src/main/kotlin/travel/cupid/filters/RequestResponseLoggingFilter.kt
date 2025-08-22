package travel.cupid.filters

import jakarta.annotation.Nonnull
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RequestResponseLoggingFilter : OncePerRequestFilter() {

    companion object {
        private val log = LoggerFactory.getLogger(RequestResponseLoggingFilter::class.java)
    }

    override fun doFilterInternal(
        @Nonnull httpServletRequest: HttpServletRequest,
        @Nonnull httpServletResponse: HttpServletResponse,
        @Nonnull filterChain: FilterChain,
    ) {
        log.info(
            "Request begin method:{} uri{} queryParams: [ {} ]",
            httpServletRequest.method,
            httpServletRequest.requestURI,
            httpServletRequest.queryString,
        )

        filterChain.doFilter(httpServletRequest, httpServletResponse)

        log.info(
            "Request end method:{} uri{} status:{}",
            httpServletRequest.method,
            httpServletRequest.requestURI,
            httpServletResponse.status,
        )
    }
}
