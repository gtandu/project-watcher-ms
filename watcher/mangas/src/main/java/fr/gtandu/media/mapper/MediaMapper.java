package fr.gtandu.media.mapper;

import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.media.dto.MediaDto;
import fr.gtandu.media.entity.MangaEntity;
import fr.gtandu.media.entity.MediaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION, uses = MangaMapper.class)
public interface MediaMapper {

    @SubclassMapping(target = MangaDto.class, source = MangaEntity.class)
    MediaDto toDto(MediaEntity mediaEntity);

    @SubclassMapping(target = MangaEntity.class, source = MangaDto.class)
    MediaEntity toEntity(MediaDto mediaDto);
}
