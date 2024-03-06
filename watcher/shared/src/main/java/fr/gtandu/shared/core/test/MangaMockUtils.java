package fr.gtandu.shared.core.test;


import fr.gtandu.shared.core.media.dto.MangaDto;
import fr.gtandu.shared.core.media.entity.MangaEntity;

public class MangaMockUtils {

    private MangaMockUtils() {
    }

    public static MangaEntity createMockManga() {
        return MangaEntity.builder()
                .id(1l)
                .name("name")
                .description("description")
                .rate(5.0)
                .review("review")
                .readingSource("readingSource")
                .state("state")
                .coverPictureUrl("coverPictureUrl")
                .build();
    }

    public static MangaEntity createMockManga(MangaDto mangaDto) {
        return MangaEntity.builder()
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

    public static MangaEntity createMockManga(Long id) {
        MangaEntity mockManga = createMockManga();
        mockManga.setId(id);
        return mockManga;
    }

}
