package fr.gtandu.media.entity;

import fr.gtandu.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "media_type",
        discriminatorType = DiscriminatorType.STRING)
@Table(name = "medias",
        indexes = {
                @Index(name = "index_medias_name", columnList = "name")
        })
public abstract class MediaEntity extends BaseEntity {
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected String description;
    @Column(name = "released_date", nullable = false)
    protected Integer releasedDate;
    @Column(name = "cover_picture_url", nullable = false)
    protected String coverPictureUrl;
    @Min(0)
    @Max(5)
    @Column
    protected Double rate;
    @Column
    protected String review;
    @Column(nullable = false)
    protected String state;
    @Column(name = "media_type", nullable = false, insertable = false, updatable = false)
    protected String mediaType;
}
