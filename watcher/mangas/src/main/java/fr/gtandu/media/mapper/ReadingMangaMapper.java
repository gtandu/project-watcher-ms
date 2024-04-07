package fr.gtandu.media.mapper;

import fr.gtandu.media.dto.ReadingMangaDto;
import fr.gtandu.media.entity.ReadingMangaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MangaMapper.class, ReadingFormatStatusMapper.class})
public interface ReadingMangaMapper {
    ReadingMangaDto toDto(ReadingMangaEntity readingMangaEntity);
    ReadingMangaEntity toEntity(ReadingMangaDto readingMediaDto);
    List<ReadingMangaDto> toDtoList(List<ReadingMangaEntity> readingMangaEntityList);
}
