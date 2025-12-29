package io.doriball.moduleadmin.auth.adapter.out.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler

class CustomLoginFailureHandler: SimpleUrlAuthenticationFailureHandler() {
    override fun onAuthenticationFailure(

        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        super.onAuthenticationFailure(request, response, exception)
    }
}