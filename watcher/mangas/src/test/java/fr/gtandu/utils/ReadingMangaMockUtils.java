package fr.gtandu.utils;


import fr.gtandu.media.entity.ReadingMangaEntity;
import fr.gtandu.media.enums.ReadingFormat;

public class ReadingMangaMockUtils {

    private ReadingMangaMockUtils() {
    }

    public static ReadingMangaEntity createMock() {
        return ReadingMangaEntity
                .builder()
                .id(1L)
                .readingFormat(ReadingFormat.VOLUME)
                .manga(MangaMockUtils.createMockManga(10L))
                .createdBy("test")
                .lastModifiedBy("test")
                .build();
    }

    public static ReadingMangaEntity createMock(Long id) {
        ReadingMangaEntity readingMangaEntity = createMock();
        readingMangaEntity.setId(id);
        return readingMangaEntity;
    }

    public static ReadingMangaEntity createMock(Long id, Long mangaId) {
        ReadingMangaEntity readingMangaEntity = createMock();
        readingMangaEntity.setId(id);
        readingMangaEntity.getManga().setId(mangaId);
        return readingMangaEntity;
    }

}
