package fr.gtandu.mapper;

import fr.gtandu.document.Manga;
import fr.gtandu.shared.core.dto.MangaDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MangaMapper {
    MangaDto toDto(Manga manga);

    List<MangaDto> toDtoList(List<Manga> mangaList);

    Manga toDocument(MangaDto mangaDto);

    List<Manga> toDocumentList(List<MangaDto> mangaDtoList);
}
