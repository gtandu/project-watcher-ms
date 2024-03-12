package fr.gtandu.repository;

import fr.gtandu.keycloak.entity.UserKeycloakEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserKeycloakEntity, String> {
}
