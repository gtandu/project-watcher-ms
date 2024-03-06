package fr.gtandu.utils;

import fr.gtandu.shared.core.keycloak.dto.UserDto;

public class UserDtoMockTest {


    public static UserDto createMockUserDto() {
        return UserDto
                .builder()
                .id("id")
                .firstName("firstName")
                .lastName("lastName")
                .username("username")
                .build();
    }
}
