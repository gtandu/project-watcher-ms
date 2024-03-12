package fr.gtandu.media.entity;

import fr.gtandu.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reading_format_status")
public class ReadingFormatStatusEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "reading_manga_id")
    private ReadingMangaEntity readingManga;
    private int mangaNumber;
    private boolean read;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ReadingFormatStatusEntity that)) return false;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getMangaNumber(), that.getMangaNumber()).append(isRead(), that.isRead()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getMangaNumber()).append(isRead()).toHashCode();
    }
}
