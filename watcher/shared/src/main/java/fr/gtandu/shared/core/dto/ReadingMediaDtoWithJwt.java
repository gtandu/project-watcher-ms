package fr.gtandu.shared.core.dto;

import org.springframework.security.oauth2.jwt.Jwt;

public record ReadingMediaDtoWithJwt(Jwt principal, ReadingMediaDto readingMediaDto) {
}
