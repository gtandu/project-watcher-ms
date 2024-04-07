package fr.gtandu.media.dto;

import fr.gtandu.common.dto.BaseDto;
import fr.gtandu.media.enums.ReadingFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ReadingMangaDto extends BaseDto implements Serializable {
    @NotNull
    private MangaDto manga;
    @NotNull
    private ReadingFormat readingFormat;
    private List<ReadingFormatStatusDto> readingFormatStatusList;
}
