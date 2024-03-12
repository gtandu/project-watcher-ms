package fr.gtandu.keycloak.entity;

import fr.gtandu.media.entity.ReadingMediaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "user_entity")
@NoArgsConstructor
@AllArgsConstructor
public class UserKeycloakEntity {
    @Id
    @Size(max = 36)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 255)
    @Column(name = "email_constraint")
    private String emailConstraint;

    @NotNull
    @Column(name = "email_verified", nullable = false)
    @Builder.Default
    private Boolean emailVerified = false;

    @NotNull
    @Column(name = "enabled", nullable = false)
    @Builder.Default
    private Boolean enabled = false;

    @Size(max = 255)
    @Column(name = "federation_link")
    private String federationLink;

    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 255)
    @Column(name = "realm_id")
    private String realmId;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @Column(name = "created_timestamp")
    private Long createdTimestamp;

    @Size(max = 255)
    @Column(name = "service_account_client_link")
    private String serviceAccountClientLink;

    @NotNull
    @Column(name = "not_before", nullable = false)
    private Integer notBefore;

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingMediaEntity> readingMediaList;

}
