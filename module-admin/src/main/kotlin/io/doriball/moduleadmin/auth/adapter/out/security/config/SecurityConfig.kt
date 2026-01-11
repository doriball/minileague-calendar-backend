package io.doriball.moduleadmin.auth.adapter.out.security.config

import io.doriball.moduleadmin.auth.adapter.out.security.handler.CustomLoginFailureHandler
import io.doriball.moduleadmin.auth.adapter.out.security.handler.CustomLoginSuccessHandler
import io.doriball.moduleadmin.auth.adapter.out.security.handler.CustomLogoutSuccessHandler
import io.doriball.moduleadmin.auth.adapter.out.security.service.CustomUserDetailsService
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableMethodSecurity(securedEnabled = true)
@Configuration
class SecurityConfig(val customUserDetailsService: CustomUserDetailsService) {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .userDetailsService(customUserDetailsService)
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers("/login").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { form ->
                form
                    .loginPage("/login")
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler())
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .logoutSuccessHandler(logoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            }
            .cors { configurer -> configurer.configurationSource(corsConfigurationSource()) }

        return httpSecurity.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationSuccessHandler(): AuthenticationSuccessHandler = CustomLoginSuccessHandler()

    @Bean
    fun authenticationFailureHandler(): AuthenticationFailureHandler = CustomLoginFailureHandler()

    @Bean
    fun logoutSuccessHandler(): LogoutSuccessHandler = CustomLogoutSuccessHandler()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfigurationSource = CorsConfiguration().apply {
            allowedOrigins = listOf(
                "http://dev-admin.doriball.com:9427"
            )
            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfigurationSource)
        return source
    }

}