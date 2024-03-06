package fr.gtandu.service.impl;

import fr.gtandu.exception.MangaNotFoundException;
import fr.gtandu.repository.MangaRepository;
import fr.gtandu.shared.core.media.dto.MangaDto;
import fr.gtandu.shared.core.media.entity.MangaEntity;
import fr.gtandu.shared.core.media.mapper.MangaMapperImpl;
import fr.gtandu.shared.core.test.MangaDtoMockUtils;
import fr.gtandu.shared.core.test.MangaMockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MangaMapperImpl.class})
class MangaServiceImplTest {
    @Mock
    private MangaRepository mangaRepository;
    @Spy
    private MangaMapperImpl mangaMapper;
    @InjectMocks
    private MangaServiceImpl mangaService;

    @Test
    void createMangaShouldSaveMangaAndReturnSavedDto() {
        // GIVEN
        MangaDto mockMangaDto = MangaDtoMockUtils.createMockMangaDto();
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
    void createMangaShouldThrowExceptionWhenMangaIsNull() {
        // GIVEN
        MangaDto mockMangaDto = MangaDtoMockUtils.createMockMangaDto();
        MangaEntity savedMockMangaEntity = MangaMockUtils.createMockManga(mockMangaDto);

        doCallRealMethod().when(mangaMapper).toEntity(mockMangaDto);
        doCallRealMethod().when(mangaMapper).toDto(any());
        when(mangaRepository.save(any())).thenReturn(savedMockMangaEntity);

        // WHEN
        MangaDto mangaDto = mangaService.createManga(null);

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
    void updateMangaShouldReturnUpdatedMangaDto() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = MangaDtoMockUtils.createMockMangaDto();
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
    void updateMangaShouldThrowMangaNotFoundExceptionWhenMangaIsNull() throws Exception {
        // GIVEN

        // WHEN
        // THEN
        Assertions.assertThrows(MangaNotFoundException.class, () -> {
            mangaService.updateManga(null);
        });
    }

    @Test
    void updateMangaShouldThrowMangaNotFoundExceptionWhenMangaIdIsNull() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = MangaDtoMockUtils.createMockMangaDto(null);
        // WHEN
        // THEN
        Assertions.assertThrows(MangaNotFoundException.class, () -> {
            mangaService.updateManga(mockMangaDto);
        });
    }

    @Test
    void updateMangaShouldThrowMangaNotFoundExceptionWhenMangaIdIsUnknown() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = MangaDtoMockUtils.createMockMangaDto(12L);
        when(mangaRepository.existsById(mockMangaDto.getId())).thenReturn(false);

        // WHEN
        // THEN
        Assertions.assertThrows(MangaNotFoundException.class, () -> {
            mangaService.updateManga(mockMangaDto);
        });

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
    void deleteMangaByIdShouldReturnFalseWhenMangaIsNull() {
        // GIVEN
        // WHEN
        boolean deletedMangaById = mangaService.deleteMangaById(null);

        // THEN
        assertThat(deletedMangaById).isFalse();

        verify(mangaRepository, never()).deleteByMangaId(1L);

    }
}