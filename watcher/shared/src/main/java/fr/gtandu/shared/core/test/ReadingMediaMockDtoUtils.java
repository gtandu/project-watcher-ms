package fr.gtandu.shared.core.test;

import fr.gtandu.shared.core.media.dto.ReadingMediaDto;
import fr.gtandu.shared.core.media.enums.ReadingFormat;

import java.util.Arrays;
import java.util.List;

public class ReadingMediaMockDtoUtils {

    private ReadingMediaMockDtoUtils() {
    }

    public static ReadingMediaDto createMockDto() {
        return ReadingMediaDto
                .builder()
                .id(1L)
                .readingFormat(ReadingFormat.VOLUME)
                .media(MangaDtoMockUtils.createMockMangaDto(10L))
                .build();
    }

    public static ReadingMediaDto createMockDto(Long id) {
        ReadingMediaDto readingMediaDto = createMockDto();
        readingMediaDto.setId(id);
        return readingMediaDto;
    }

    public static ReadingMediaDto createMockDto(Long id, Long mangaId) {
        ReadingMediaDto readingMediaDto = createMockDto();
        readingMediaDto.setId(id);
        readingMediaDto.getMedia().setId(mangaId);
        return readingMediaDto;
    }

    public static List<ReadingMediaDto> createMockDtoList() {
        long readingMediaId1 = 1L;
        long mangaId1 = 10L;
        ReadingMediaDto readingMediaDto1 = ReadingMediaMockDtoUtils.createMockDto(readingMediaId1, mangaId1);

        long readingMediaId2 = 2L;
        long mangaId2 = 20L;
        ReadingMediaDto readingMediaDto2 = ReadingMediaMockDtoUtils.createMockDto(readingMediaId2, mangaId2);

        long readingMediaId3 = 3L;
        long mangaId3 = 30L;
        ReadingMediaDto readingMediaDto3 = ReadingMediaMockDtoUtils.createMockDto(readingMediaId3, mangaId3);

        return Arrays.asList(readingMediaDto1, readingMediaDto2, readingMediaDto3);
    }

}
