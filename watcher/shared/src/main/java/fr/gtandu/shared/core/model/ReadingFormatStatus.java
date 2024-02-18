package fr.gtandu.shared.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadingFormatStatus {
    private int mediaNumber;
    private boolean read;
}
