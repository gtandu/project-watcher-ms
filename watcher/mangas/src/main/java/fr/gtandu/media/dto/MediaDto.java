package fr.gtandu.media.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.gtandu.common.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(as = MangaDto.class)
public abstract class MediaDto extends BaseDto implements Serializable {
    protected String name;
    protected String description;
    protected Integer releasedDate;
    protected String coverPictureUrl;
    protected Double rate;
    protected String review;
    protected String state;
}
