package fr.gtandu.shared.core.mapper;

import fr.gtandu.shared.core.dto.ReadingMediaDto;
import fr.gtandu.shared.core.model.ReadingMedia;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MediaDocumentMapper.class, ReadingFormatStatusMapper.class})
public interface ReadingMediaMapper {
    ReadingMediaDto toDto(ReadingMedia readingMedia);

    ReadingMedia toDocument(ReadingMediaDto readingMediaDto);
}
