package fr.gtandu.shared.core.common.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Collections;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange.anyExchange().authenticated())
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoderByIssuerUri(OAuth2ResourceServerProperties properties) {
        String issuerUri = properties.getJwt().getIssuerUri();
        NimbusReactiveJwtDecoder jwtDecoder = (NimbusReactiveJwtDecoder) ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
        // Use preferred_username from claims as authentication name, instead of UUID subject
        jwtDecoder.setClaimSetConverter(new UsernameSubClaimAdapter());
        return jwtDecoder;
    }

    static class UsernameSubClaimAdapter implements Converter<Map<String, Object>, Map<String, Object>> {

        private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

        @Override
        public Map<String, Object> convert(Map<String, Object> claims) {
            Map<String, Object> convertedClaims = this.delegate.convert(claims);
            if (convertedClaims != null && convertedClaims.get("preferred_username") != null) {
                String username = (String) convertedClaims.get("preferred_username");
                convertedClaims.put("username", username);
            }
            return convertedClaims;
        }

    }
}
