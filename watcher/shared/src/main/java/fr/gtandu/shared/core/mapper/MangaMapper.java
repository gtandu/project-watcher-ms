package fr.gtandu.shared.core.mapper;

import fr.gtandu.shared.core.document.MangaDocument;
import fr.gtandu.shared.core.dto.MangaDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MangaMapper {
    MangaDto toDto(MangaDocument mangaDocument);

    List<MangaDto> toDtoList(List<MangaDocument> mangaDocumentList);

    MangaDocument toDocument(MangaDto mangaDto);

    List<MangaDocument> toDocumentList(List<MangaDto> mangaDtoList);
}
