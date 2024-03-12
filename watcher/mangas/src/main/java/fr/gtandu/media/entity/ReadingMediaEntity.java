package fr.gtandu.media.entity;

import fr.gtandu.common.entity.BaseEntity;
import fr.gtandu.keycloak.entity.UserKeycloakEntity;
import fr.gtandu.media.enums.ReadingFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reading_medias")
public class ReadingMediaEntity extends BaseEntity {
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private UserKeycloakEntity user;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private MediaEntity media;
    @Column
    private ReadingFormat readingFormat;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "readingMedia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingFormatStatusEntity> readingFormatStatusList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ReadingMediaEntity that)) return false;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getMedia(), that.getMedia()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getMedia()).toHashCode();
    }
}
