package fr.gtandu.shared.core.media.dto;

import fr.gtandu.shared.core.common.dto.BaseDto;
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
