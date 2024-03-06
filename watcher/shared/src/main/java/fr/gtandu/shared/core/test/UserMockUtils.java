package fr.gtandu.shared.core.test;

import fr.gtandu.shared.core.keycloak.entity.UserKeycloakEntity;

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
                .build();
    }

    public static UserKeycloakEntity createMockUserto(String id) {
        UserKeycloakEntity userKeycloakEntity = createMockUser();
        userKeycloakEntity.setId(id);
        return userKeycloakEntity;
    }

}
