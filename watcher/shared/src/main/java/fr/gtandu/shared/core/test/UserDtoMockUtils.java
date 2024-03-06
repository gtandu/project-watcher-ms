package fr.gtandu.shared.core.test;

import fr.gtandu.shared.core.keycloak.dto.UserDto;

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
