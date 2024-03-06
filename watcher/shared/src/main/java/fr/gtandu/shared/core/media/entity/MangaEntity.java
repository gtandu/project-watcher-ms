package fr.gtandu.shared.core.media.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("manga")
public class MangaEntity extends MediaEntity {

    @Column(name = "reading_source")
    private String readingSource;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MangaEntity that)) return false;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getReadingSource(), that.getReadingSource()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getReadingSource()).toHashCode();
    }
}
