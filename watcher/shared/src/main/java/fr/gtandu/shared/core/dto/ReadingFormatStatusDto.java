package fr.gtandu.shared.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadingFormatStatusDto implements Serializable {
    private int mediaNumber;
    private boolean read;
}
