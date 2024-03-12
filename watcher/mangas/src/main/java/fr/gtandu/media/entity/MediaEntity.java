package fr.gtandu.media.entity;

import fr.gtandu.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
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
@MappedSuperclass
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
}
