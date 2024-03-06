package fr.gtandu.shared.core.test;

import fr.gtandu.shared.core.media.entity.ReadingMediaEntity;
import fr.gtandu.shared.core.media.enums.ReadingFormat;

public class ReadingMediaMockUtils {

    private ReadingMediaMockUtils() {
    }

    public static ReadingMediaEntity createMock() {
        return ReadingMediaEntity
                .builder()
                .id(1L)
                .readingFormat(ReadingFormat.VOLUME)
                .media(MangaMockUtils.createMockManga(10L))
                .build();
    }

    public static ReadingMediaEntity createMock(Long id) {
        ReadingMediaEntity readingMediaEntity = createMock();
        readingMediaEntity.setId(id);
        return readingMediaEntity;
    }

    public static ReadingMediaEntity createMock(Long id, Long mangaId) {
        ReadingMediaEntity readingMediaEntity = createMock();
        readingMediaEntity.setId(id);
        readingMediaEntity.getMedia().setId(mangaId);
        return readingMediaEntity;
    }

}
