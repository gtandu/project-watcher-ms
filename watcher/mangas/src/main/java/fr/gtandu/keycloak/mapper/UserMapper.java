package fr.gtandu.keycloak.mapper;

import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.keycloak.entity.UserKeycloakEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserKeycloakEntity userKeycloakEntity);

    UserKeycloakEntity toEntity(UserDto userDto);
}
