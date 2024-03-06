package fr.gtandu.shared.core.keycloak.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto implements Serializable {
    @Size(max = 36)
    String id;
    @Size(max = 255)
    String email;
    @Size(max = 255)
    String emailConstraint;
    @NotNull
    Boolean emailVerified;
    @NotNull
    Boolean enabled;
    @Size(max = 255)
    String federationLink;
    @Size(max = 255)
    String firstName;
    @Size(max = 255)
    String lastName;
    @Size(max = 255)
    String realmId;
    @Size(max = 255)
    String username;
    Long createdTimestamp;
    @Size(max = 255)
    String serviceAccountClientLink;
    @NotNull
    Integer notBefore;
}



