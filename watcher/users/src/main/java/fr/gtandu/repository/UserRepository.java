package fr.gtandu.repository;

import fr.gtandu.shared.core.keycloak.entity.UserKeycloakEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserKeycloakEntity, String> {
}
