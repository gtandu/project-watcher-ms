package fr.gtandu.media.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.gtandu.common.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonDeserialize(as = MangaDto.class)
public abstract class MediaDto extends BaseDto implements Serializable {
    @NotBlank
    protected String name;
    @NotBlank
    protected String description;
    protected Integer releasedDate;
    protected String coverPictureUrl;
    protected Double rate;
    protected String review;
    protected String state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MediaDto mediaDto = (MediaDto) o;
        return Objects.equals(name, mediaDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
