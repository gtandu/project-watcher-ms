package fr.gtandu.shared.core.test;

import fr.gtandu.shared.core.media.dto.MangaDto;
import fr.gtandu.shared.core.media.entity.MangaEntity;
import fr.gtandu.shared.core.media.enums.MediaType;

public class MangaDtoMockUtils {

    private MangaDtoMockUtils() {
    }

    public static MangaDto createMockMangaDto() {
        return MangaDto.builder()
                .id(1L)
                .name("name")
                .description("description")
                .rate(5.0)
                .review("review")
                .readingSource("readingSource")
                .state("state")
                .coverPictureUrl("coverPictureUrl")
                .build();
    }

    public static MangaDto createMockMangaDtoFromManga(MangaEntity manga) {
        return MangaDto.builder()
                .id(manga.getId())
                .name(manga.getName())
                .description(manga.getDescription())
                .rate(manga.getRate())
                .review(manga.getReview())
                .readingSource(manga.getReadingSource())
                .state(manga.getState())
                .coverPictureUrl(manga.getCoverPictureUrl())
                .build();
    }

    public static MangaDto createMockMangaDto(Long id) {
        return MangaDto.builder()
                .id(id)
                .name("name")
                .description("description")
                .rate(5.0)
                .review("review")
                .readingSource("readingSource")
                .state("state")
                .coverPictureUrl("coverPictureUrl")
                .type(MediaType.MANGA)
                .build();
    }

}
