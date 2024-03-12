package fr.gtandu.media.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.gtandu.common.dto.BaseDto;
import fr.gtandu.media.enums.MediaType;
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
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MangaDto.class, name = "manga"),
})
public abstract class MediaDto extends BaseDto implements Serializable {
    protected String name;
    protected String description;
    protected Integer releasedDate;
    protected String coverPictureUrl;
    protected Double rate;
    protected String review;
    protected String state;
    protected MediaType type;
}
