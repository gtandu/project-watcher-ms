package fr.gtandu.media.entity;

import fr.gtandu.common.entity.BaseEntity;
import fr.gtandu.keycloak.entity.UserKeycloakEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class ReadingMediaEntity extends BaseEntity {
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    protected UserKeycloakEntity user;
}
