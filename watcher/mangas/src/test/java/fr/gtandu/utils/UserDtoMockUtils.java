package fr.gtandu.utils;

import fr.gtandu.keycloak.dto.UserDto;

public class UserDtoMockUtils {

    private UserDtoMockUtils() {
    }

    public static UserDto createMockUserDto() {
        return UserDto.builder()
                .id("id")
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .username("username")
                .build();
    }

    public static UserDto createMockUserDto(String id) {
        UserDto mockUserDto = createMockUserDto();
        mockUserDto.setId(id);
        return mockUserDto;
    }

}
