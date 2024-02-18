package fr.gtandu.shared.core.mapper;

import fr.gtandu.shared.core.document.MangaDocument;
import fr.gtandu.shared.core.document.MediaDocument;
import fr.gtandu.shared.core.dto.MangaDto;
import fr.gtandu.shared.core.dto.MediaDocumentDto;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION, uses = MangaMapper.class)
public interface MediaDocumentMapper {

    @SubclassMapping(target = MangaDto.class, source = MangaDocument.class)
    MediaDocumentDto toDto(MediaDocument mediaDocument);

    @SubclassMapping(target = MangaDocument.class, source = MangaDto.class)
    MediaDocument toDocument(MediaDocumentDto mediaDocumentDto);
}
