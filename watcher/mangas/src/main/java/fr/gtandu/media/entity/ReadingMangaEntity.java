package fr.gtandu.media.entity;

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
@Table(name = "reading_mangas")
public class ReadingMangaEntity extends ReadingMediaEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id")
    private MangaEntity manga;
    @Column
    private ReadingFormat readingFormat;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "readingManga", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<ReadingFormatStatusEntity> readingFormatStatusList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ReadingMangaEntity that)) return false;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(getManga(), that.getManga()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(getManga()).toHashCode();
    }
}
