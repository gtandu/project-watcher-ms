package fr.gtandu.common.config.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt.auth.converter")
public record JwtAuthConverterProperties(String resourceId, String principalAttribute) {
}
