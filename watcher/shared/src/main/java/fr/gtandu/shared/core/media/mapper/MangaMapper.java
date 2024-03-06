package fr.gtandu.shared.core.media.mapper;

import fr.gtandu.shared.core.media.dto.MangaDto;
import fr.gtandu.shared.core.media.entity.MangaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MangaMapper {
    MangaDto toDto(MangaEntity mangaDocument);

    List<MangaDto> toDtoList(List<MangaEntity> mangaDocumentList);

    MangaEntity toEntity(MangaDto mangaDto);

    List<MangaEntity> toEntityList(List<MangaDto> mangaDtoList);
}
