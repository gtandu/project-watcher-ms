package fr.gtandu.media.mapper;

import fr.gtandu.media.dto.ReadingFormatStatusDto;
import fr.gtandu.media.entity.ReadingFormatStatusEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReadingFormatStatusMapper {

    ReadingFormatStatusDto toDto(ReadingFormatStatusEntity readingFormatStatusEntity);

    List<ReadingFormatStatusDto> toDtoList(List<ReadingFormatStatusEntity> readingFormatStatusEntityList);

    ReadingFormatStatusEntity toEntity(ReadingFormatStatusDto readingFormatStatusDto);

    List<ReadingFormatStatusEntity> toEntityList(List<ReadingFormatStatusDto> readingFormatStatusDtos);
}
