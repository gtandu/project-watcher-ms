package fr.gtandu.shared.core.keycloak.mapper;

import fr.gtandu.shared.core.keycloak.dto.UserDto;
import fr.gtandu.shared.core.keycloak.entity.UserKeycloakEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserKeycloakEntity userKeycloakEntity);

    UserKeycloakEntity toEntity(UserDto userDto);
}
