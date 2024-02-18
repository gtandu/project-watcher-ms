package fr.gtandu.shared.core.mapper;

import fr.gtandu.shared.core.dto.ReadingFormatStatusDto;
import fr.gtandu.shared.core.model.ReadingFormatStatus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReadingFormatStatusMapper {

    ReadingFormatStatusDto toDto(ReadingFormatStatus readingFormatStatus);

    List<ReadingFormatStatusDto> toDtoList(List<ReadingFormatStatus> readingFormatStatusList);

    ReadingFormatStatus toDocument(ReadingFormatStatusDto readingFormatStatusDto);

    List<ReadingFormatStatus> toDocumentList(List<ReadingFormatStatusDto> readingFormatStatusDtos);
}
