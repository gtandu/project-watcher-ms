package fr.gtandu.media.dto;

import fr.gtandu.common.dto.BaseDto;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadingFormatStatusDto extends BaseDto implements Serializable {
    private int mediaNumber;
    private boolean read;
}
