package fr.gtandu.common.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile(value = {"!noKeycloak"})
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;
    private final AuthenticationEntryPointJwt authenticationEntryPointJwt;
    private final AccessDeniedHandlerJwt accessDeniedHandlerJwt;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter, AuthenticationEntryPointJwt authenticationEntryPointJwt, AccessDeniedHandlerJwt accessDeniedHandlerJwt) {
        this.jwtAuthConverter = jwtAuthConverter;
        this.authenticationEntryPointJwt = authenticationEntryPointJwt;
        this.accessDeniedHandlerJwt = accessDeniedHandlerJwt;
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer
                                .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(authenticationEntryPointJwt)
                                .accessDeniedHandler(accessDeniedHandlerJwt)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}

