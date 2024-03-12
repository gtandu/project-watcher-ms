package fr.gtandu.utils;


import fr.gtandu.media.dto.ReadingMangaDto;
import fr.gtandu.media.enums.ReadingFormat;

import java.util.Arrays;
import java.util.List;

public class ReadingMangaMockDtoUtils {

    private ReadingMangaMockDtoUtils() {
    }

    public static ReadingMangaDto createMockDto() {
        return ReadingMangaDto
                .builder()
                .id(1L)
                .readingFormat(ReadingFormat.VOLUME)
                .manga(MangaDtoMockUtils.createMockMangaDto(10L))
                .build();
    }

    public static ReadingMangaDto createMockDto(Long id) {
        ReadingMangaDto readingMangaDto = createMockDto();
        readingMangaDto.setId(id);
        return readingMangaDto;
    }

    public static ReadingMangaDto createMockDto(Long id, Long mangaId) {
        ReadingMangaDto readingMangaDto = createMockDto();
        readingMangaDto.setId(id);
        readingMangaDto.getManga().setId(mangaId);
        return readingMangaDto;
    }

    public static List<ReadingMangaDto> createMockDtoList() {
        long readingMangaId1 = 1L;
        long mangaId1 = 10L;
        ReadingMangaDto readingMangaDto1 = ReadingMangaMockDtoUtils.createMockDto(readingMangaId1, mangaId1);

        long readingMangaId2 = 2L;
        long mangaId2 = 20L;
        ReadingMangaDto readingMangaDto2 = ReadingMangaMockDtoUtils.createMockDto(readingMangaId2, mangaId2);

        long readingMangaId3 = 3L;
        long mangaId3 = 30L;
        ReadingMangaDto readingMangaDto3 = ReadingMangaMockDtoUtils.createMockDto(readingMangaId3, mangaId3);

        return Arrays.asList(readingMangaDto1, readingMangaDto2, readingMangaDto3);
    }

}
