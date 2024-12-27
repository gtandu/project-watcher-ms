package fr.gtandu.keycloak.utils;

import fr.gtandu.keycloak.dto.UserDto;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class JwtMapper {

    public UserDto toUserDto(Jwt principal) {
        return UserDto.builder()
                .id(principal.getSubject())
                .email(principal.getClaim("email"))
                .firstName(principal.getClaim("given_name"))
                .lastName(principal.getClaim("family_name"))
                .username(principal.getClaim("preferred_username"))
                .build();
    }
}
