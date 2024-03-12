package fr.gtandu.media.mapper;

import fr.gtandu.media.dto.ReadingMediaDto;
import fr.gtandu.media.entity.ReadingMediaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MediaMapper.class, ReadingFormatStatusMapper.class})
public interface ReadingMediaMapper {
    ReadingMediaDto toDto(ReadingMediaEntity readingMediaEntity);

    ReadingMediaEntity toEntity(ReadingMediaDto readingMediaDto);

    List<ReadingMediaDto> toDtoList(List<ReadingMediaEntity> readingMediaEntityList);

    List<ReadingMediaEntity> toEntityList(List<ReadingMediaDto> readingMediaDtoList);
}
