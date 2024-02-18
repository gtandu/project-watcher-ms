package fr.gtandu.shared.core.dto;

import fr.gtandu.shared.core.enums.ReadingFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadingMediaDto implements Serializable {

    private MediaDocumentDto mediaDocument;
    private ReadingFormat readingFormat;
    private List<ReadingFormatStatusDto> readingFormatStatusList;
}
