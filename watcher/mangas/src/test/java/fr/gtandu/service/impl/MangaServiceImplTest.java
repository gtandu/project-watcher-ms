package fr.gtandu.service.impl;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.exception.MangaNotFoundException;
import fr.gtandu.mangadex.service.MangaDexService;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.media.dto.MediaDto;
import fr.gtandu.media.entity.MangaEntity;
import fr.gtandu.media.mapper.MangaMapperImpl;
import fr.gtandu.repository.MangaRepository;
import fr.gtandu.utils.MangaMockUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static fr.gtandu.common.constant.AppConstant.PAGE_SIZE_LIMIT;
import static fr.gtandu.utils.MangaDtoMockUtils.createMockMangaDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MangaMapperImpl.class})
class MangaServiceImplTest {
    public static final String NAR = "Nar";
    @Mock
    private MangaRepository mangaRepository;

    @Mock
    private MangaDexService mangaDexService;
    @Spy
    private MangaMapperImpl mangaMapper;
    @InjectMocks
    @Spy
    private MangaServiceImpl mangaService;

    @Test
    void createMangaShouldSaveMangaAndReturnSavedDto() throws MangaAlreadyExistException {
        // GIVEN
        MangaDto mockMangaDto = createMockMangaDto();
        MangaEntity savedMockMangaEntity = MangaMockUtils.createMockManga(mockMangaDto);

        doCallRealMethod().when(mangaMapper).toEntity(mockMangaDto);
        doCallRealMethod().when(mangaMapper).toDto(any());
        when(mangaRepository.save(any())).thenReturn(savedMockMangaEntity);

        // WHEN
        MangaDto mangaDto = mangaService.createManga(mockMangaDto);

        // THEN
        assertThat(mangaDto).isNotNull();
        assertThat(mangaDto.getId()).isEqualTo(savedMockMangaEntity.getId());
        assertThat(mangaDto.getName()).isEqualTo(savedMockMangaEntity.getName());
        assertThat(mangaDto.getRate()).isEqualTo(savedMockMangaEntity.getRate());
        assertThat(mangaDto.getState()).isEqualTo(savedMockMangaEntity.getState());
        assertThat(mangaDto.getDescription()).isEqualTo(savedMockMangaEntity.getDescription());
        assertThat(mangaDto.getReadingSource()).isEqualTo(savedMockMangaEntity.getReadingSource());
        assertThat(mangaDto.getReleasedDate()).isEqualTo(savedMockMangaEntity.getReleasedDate());
        assertThat(mangaDto.getCoverPictureUrl()).isEqualTo(savedMockMangaEntity.getCoverPictureUrl());

        verify(mangaMapper).toEntity(mockMangaDto);
        verify(mangaMapper).toDto(any());
        verify(mangaRepository).save(any());
    }

    @Test
    void createMangaShouldThrowMangaAlreaydyEcistExceptionWhenMangaExistInDb() {
        // GIVEN
        MangaDto mockMangaDto = createMockMangaDto();

        doReturn(true).when(mangaService).existsById(mockMangaDto.getId());

        // WHENs
        // THEN
        assertThrows(MangaAlreadyExistException.class, () -> mangaService.createManga(mockMangaDto));

        verify(mangaService).existsById(mockMangaDto.getId());
        verify(mangaMapper, never()).toEntity(any());
        verify(mangaMapper, never()).toDto(any());
        verify(mangaRepository, never()).save(any());
    }

    @Test
    void createMangaShouldThrowNullPointerExceptionWhenMangaIsNull() {
        // GIVEN

        // WHENs
        // THEN
        assertThrows(NullPointerException.class, () -> mangaService.createManga(null));
        verify(mangaMapper, never()).toEntity(any());
        verify(mangaMapper, never()).toDto(any());
        verify(mangaRepository, never()).save(any());
    }

    @Test
    void updateMangaShouldReturnUpdatedMangaDto() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = createMockMangaDto();
        MangaEntity mockManga = MangaMockUtils.createMockManga(mockMangaDto);


        when(mangaMapper.toDto(any())).thenCallRealMethod();
        when(mangaRepository.existsById(mockMangaDto.getId())).thenReturn(true);
        when(mangaMapper.toEntity(mockMangaDto)).thenCallRealMethod();
        when(mangaRepository.save(any())).thenReturn(mockManga);

        // WHEN
        MangaDto mangaDtoUpdated = mangaService.updateManga(mockMangaDto);

        // THEN

        assertThat(mangaDtoUpdated).isNotNull();
        assertThat(mangaDtoUpdated.getId()).isEqualTo(mockMangaDto.getId());
        assertThat(mangaDtoUpdated.getName()).isEqualTo(mockMangaDto.getName());
        assertThat(mangaDtoUpdated.getRate()).isEqualTo(mockMangaDto.getRate());
        assertThat(mangaDtoUpdated.getState()).isEqualTo(mockMangaDto.getState());
        assertThat(mangaDtoUpdated.getDescription()).isEqualTo(mockMangaDto.getDescription());
        assertThat(mangaDtoUpdated.getReadingSource()).isEqualTo(mockMangaDto.getReadingSource());
        assertThat(mangaDtoUpdated.getReleasedDate()).isEqualTo(mockMangaDto.getReleasedDate());
        assertThat(mangaDtoUpdated.getCoverPictureUrl()).isEqualTo(mockMangaDto.getCoverPictureUrl());

        verify(mangaMapper).toDto(any());
        verify(mangaRepository).existsById(mockMangaDto.getId());
        verify(mangaMapper).toEntity(mockMangaDto);
        verify(mangaRepository).save(any());
    }

    @Test
    void updateMangaShouldThrowNullPointerExceptionWhenMangaIsNull() {
        // GIVEN

        // WHEN
        // THEN
        assertThrows(NullPointerException.class, () -> mangaService.updateManga(null));
    }

    @Test
    void updateMangaShouldThrowMangaNotFoundExceptionWhenMangaIdIsNull() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = createMockMangaDto(null);
        // WHEN
        // THEN
        assertThrows(MangaNotFoundException.class, () -> mangaService.updateManga(mockMangaDto));
    }

    @Test
    void updateMangaShouldThrowMangaNotFoundExceptionWhenMangaIdIsUnknown() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = createMockMangaDto(12L);
        when(mangaRepository.existsById(mockMangaDto.getId())).thenReturn(false);

        // WHEN
        // THEN
        assertThrows(MangaNotFoundException.class, () -> mangaService.updateManga(mockMangaDto));

        verify(mangaRepository).existsById(mockMangaDto.getId());
    }

    @Test
    void deleteMangaByIdShouldReturnTrueWhenMangaIsDeleted() {
        // GIVEN
        when(mangaRepository.deleteByMangaId(1L)).thenReturn(1);

        // WHEN
        boolean deletedMangaById = mangaService.deleteMangaById(1L);

        // THEN
        assertThat(deletedMangaById).isTrue();

        verify(mangaRepository).deleteByMangaId(1L);
    }

    @Test
    void deleteMangaByIdShouldReturnFalseWhenMangaIsNotDeleted() {
        // GIVEN
        when(mangaRepository.deleteByMangaId(1L)).thenReturn(0);

        // WHEN
        boolean deletedMangaById = mangaService.deleteMangaById(1L);

        // THEN
        assertThat(deletedMangaById).isFalse();

        verify(mangaRepository).deleteByMangaId(1L);
    }

    @Test
    void deleteMangaByIdShouldThrowNullPointerExceptionWhenMangaIsNull() {
        // GIVEN

        // WHEN
        // THEN
        assertThrows(NullPointerException.class, () -> mangaService.deleteMangaById(null));
        verify(mangaRepository, never()).deleteByMangaId(1L);
    }


    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"N", "Na"})
    void searchByNameShouldReturnEmptyListWhenSearchKeyIsNullOrEmptyOrLessThanThreeCharacters(String searchKey) {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10);

        // WHEN
        List<MangaDto> mangaDtoList = mangaService.searchByName(searchKey, pageable);

        // THEN
        assertThat(mangaDtoList).isEmpty();
    }

    @Test
    void searchByNameShouldReturnPageSizeLimitMangasFromBddWithNameContainsNaruto() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, PAGE_SIZE_LIMIT);

        List<MangaEntity> mangaEntityList = new ArrayList<>();

        for (long i = 0L; i < PAGE_SIZE_LIMIT; i++) {
            mangaEntityList.add(MangaMockUtils.createMockManga(i, "Naruto " + i));
        }

        doCallRealMethod().when(mangaMapper).toDtoList(mangaEntityList);
        when(mangaRepository.findByNameStartingWith(NAR, pageable)).thenReturn(mangaEntityList);

        // WHEN
        List<MangaDto> mangaDtoList = mangaService.searchByName(NAR, pageable);

        // THEN
        assertThat(mangaDtoList).isNotEmpty();

        verify(mangaDexService, never()).searchMangaByTitle(NAR);
        verify(mangaMapper).toDtoList(mangaEntityList);
        verify(mangaRepository).findByNameStartingWith(NAR, pageable);
    }

    @Test
    void searchByNameShouldReturnDistinctSevenMangasFromBddAndMangaDexWithNameContainsNaruto() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 5);
        int expectedSize = 7;

        List<MangaEntity> mangaEntityList = Arrays.asList(
                MangaMockUtils.createMockManga(1L, "Renge to Naruto!"),
                MangaMockUtils.createMockManga(2L, "The Last: Naruto Alternative"),
                MangaMockUtils.createMockManga(3L, "Naruto")
        );
        List<MangaDto> mangaDexList = Arrays.asList(
                createMockMangaDto(null, "Renge to Naruto!"),
                createMockMangaDto(null, "Naruto"),
                createMockMangaDto(null, "CC NARUTO: Fuu no Sho - Sugao no Shinjitsu...!!"),
                createMockMangaDto(null, "AA NARUTO: Rai no Sho - Ai wo Itareta Kemono!!")
        );

        when(mangaRepository.findByNameStartingWith(NAR, pageable)).thenReturn(mangaEntityList);
        when(mangaDexService.searchMangaByTitle(NAR)).thenReturn(mangaDexList);

        // WHEN
        List<MangaDto> mangaDtoList = mangaService.searchByName(NAR, pageable);

        // THEN
        assertThat(mangaDtoList)
                .isNotEmpty()
                .hasSize(expectedSize)
                .isSortedAccordingTo(Comparator.comparing(MediaDto::getName))
                .doesNotHaveDuplicates();

        verify(mangaDexService).searchMangaByTitle(NAR);
        verify(mangaRepository).findByNameStartingWith(NAR, pageable);
    }

    @Test
    void searchByNameShouldThrowExceptionWhenPageSizeExceedsLimit() {
        // Given
        String searchKey = "Naruto";
        Pageable pageable = PageRequest.of(0, PAGE_SIZE_LIMIT + 1);

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mangaService.searchByName(searchKey, pageable));

        // Then
        assertThat(exception.getMessage()).isEqualTo("Page size exceeds the limit");
    }
}
