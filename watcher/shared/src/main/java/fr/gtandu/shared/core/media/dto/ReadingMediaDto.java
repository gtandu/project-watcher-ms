package fr.gtandu.shared.core.media.dto;

import fr.gtandu.shared.core.common.dto.BaseDto;
import fr.gtandu.shared.core.media.enums.ReadingFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ReadingMediaDto extends BaseDto implements Serializable {
    private MediaDto media;
    private ReadingFormat readingFormat;
    private List<ReadingFormatStatusDto> readingFormatStatusList;
}
