package fr.gtandu.utils;


import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.media.entity.MangaEntity;

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
                .build();
    }

    public static MangaDto copyMockMangaDto(MangaDto mangaDto) {
        return MangaDto.builder()
                .id(mangaDto.getId())
                .name(mangaDto.getName())
                .description(mangaDto.getDescription())
                .rate(mangaDto.getRate())
                .review(mangaDto.getReview())
                .readingSource(mangaDto.getReadingSource())
                .state(mangaDto.getState())
                .coverPictureUrl(mangaDto.getCoverPictureUrl())
                .build();
    }

}
