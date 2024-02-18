package fr.gtandu.shared.core.model;

import fr.gtandu.shared.core.document.MediaDocument;
import fr.gtandu.shared.core.enums.ReadingFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@Builder
public class ReadingMedia {
    @DBRef
    private MediaDocument mediaDocument;
    private ReadingFormat readingFormat;
    private List<ReadingFormatStatus> readingFormatStatusList;
}
