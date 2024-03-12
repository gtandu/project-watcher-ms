package fr.gtandu.utils;

import fr.gtandu.keycloak.entity.UserKeycloakEntity;

public class UserMockUtils {

    private UserMockUtils() {
    }

    public static UserKeycloakEntity createMockUser() {
        return UserKeycloakEntity.builder()
                .id("id")
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .username("username")
                .notBefore(1)
                .build();
    }

    public static UserKeycloakEntity createMockUserto(String id) {
        UserKeycloakEntity userKeycloakEntity = createMockUser();
        userKeycloakEntity.setId(id);
        return userKeycloakEntity;
    }

}
