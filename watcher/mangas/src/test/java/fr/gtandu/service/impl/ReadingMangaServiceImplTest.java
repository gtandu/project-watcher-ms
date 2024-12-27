package fr.gtandu.service.impl;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.keycloak.mapper.UserMapperImpl;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.media.dto.ReadingMangaDto;
import fr.gtandu.media.entity.ReadingMangaEntity;
import fr.gtandu.media.mapper.MangaMapperImpl;
import fr.gtandu.media.mapper.ReadingFormatStatusMapperImpl;
import fr.gtandu.media.mapper.ReadingMangaMapperImpl;
import fr.gtandu.repository.ReadingMangaRepository;
import fr.gtandu.service.MangaService;
import fr.gtandu.service.UserService;
import fr.gtandu.utils.ReadingMangaMockDtoUtils;
import fr.gtandu.utils.ReadingMangaMockUtils;
import fr.gtandu.utils.UserDtoMockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static fr.gtandu.common.constant.AppConstant.SEARCH_MANGAS_BY_NAME_PAGE_SIZE_LIMIT;
import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserMapperImpl.class, ReadingMangaMapperImpl.class, MangaMapperImpl.class, ReadingFormatStatusMapperImpl.class})
class ReadingMangaServiceImplTest {

    @Mock
    private MangaService mangaService;
    @Mock
    private ReadingMangaRepository readingMangaRepository;
    @Mock
    private UserService userService;
    @Mock
    private UserMapperImpl userMapper = mock(UserMapperImpl.class);
    @Mock
    private ReadingMangaMapperImpl readingMangaMapper = mock(ReadingMangaMapperImpl.class);

    @InjectMocks
    @Spy
    private ReadingMangaServiceImpl readingMangaService;

    private final String userId = "8f6561be-039f-43fe-90d6-fc493cc1759a";

    @BeforeEach
    public void init() {
        MangaMapperImpl mangaMapper = mock(MangaMapperImpl.class);
        ReadingFormatStatusMapperImpl readingFormatStatusMapper = mock(ReadingFormatStatusMapperImpl.class);

        ReflectionTestUtils.setField(readingMangaMapper, "mangaMapper", mangaMapper);
        ReflectionTestUtils.setField(readingMangaMapper, "readingFormatStatusMapper", readingFormatStatusMapper);
    }

    @Test
    void getAllReadingMangasByUserIdShouldReturnEmptyListOfReadingMangasWhenUserHasNoReadingMangas() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, SEARCH_MANGAS_BY_NAME_PAGE_SIZE_LIMIT);

        when(readingMangaRepository.findByUserId(userId, pageable)).thenReturn(new SliceImpl<>(EMPTY_LIST));

        // WHEN
        Slice<ReadingMangaDto> allReadingMangasByUserId = readingMangaService.getAllReadingMangasByUserId(userId, pageable);

        // THEN
        assertThat(allReadingMangasByUserId).isEmpty();

        verify(readingMangaMapper, never()).toDto(any(ReadingMangaEntity.class));
        verify(readingMangaRepository).findByUserId(userId, pageable);
    }

    @Test
    void getAllReadingMangasByUserIdShouldReturnReadingMangasWhenPageSizeIsWithinLimit() {
        // Given
        Pageable pageable = PageRequest.of(0, 5); // PAGE_SIZE_LIMIT > 5
        Slice<ReadingMangaEntity> readingMangaEntities = new SliceImpl<>(Collections.singletonList(new ReadingMangaEntity()));
        when(readingMangaRepository.findByUserId(userId, pageable)).thenReturn(readingMangaEntities);
        when(readingMangaMapper.toDto(any(ReadingMangaEntity.class))).thenReturn(new ReadingMangaDto());

        // When
        Slice<ReadingMangaDto> result = readingMangaService.getAllReadingMangasByUserId(userId, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        verify(readingMangaRepository).findByUserId(userId, pageable);
        verify(readingMangaMapper, times(1)).toDto(any(ReadingMangaEntity.class));
    }

    @Test
    void getAllReadingMangasByUserIdShouldThrowExceptionWhenPageSizeExceedsLimit() {
        // Given
        Pageable pageable = PageRequest.of(0, 100); // PAGE_SIZE_LIMIT < 100

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> readingMangaService.getAllReadingMangasByUserId(userId, pageable));

        // Then
        assertThat(exception.getMessage()).isEqualTo("Page size exceeds the limit. Current limit : 20 Request size : 100");
        verify(readingMangaRepository, never()).findByUserId(anyString(), any(Pageable.class));
    }

    @Test
    void saveShouldReturnSavedReadingMangaDto() {
        // GIVEN
        ReadingMangaEntity readingMangaEntity = ReadingMangaMockUtils.createMock(null, 10L);
        ReadingMangaEntity savedReadingMangaEntity = ReadingMangaMockUtils.createMock(1L, 10L);

        doCallRealMethod().when(readingMangaMapper).toDto(readingMangaEntity);
        when(readingMangaRepository.save(readingMangaEntity)).thenReturn(savedReadingMangaEntity);
        // WHEN
        ReadingMangaDto readingMangaDto = readingMangaService.save(readingMangaEntity);

        // THEN
        assertThat(readingMangaDto).isNotNull();
        assertThat(readingMangaDto.getId()).isEqualTo(savedReadingMangaEntity.getId());
    }

    @Test
    void addMangaToReadingListAddsMangaWhenMangaDoesNotExist() throws MangaAlreadyExistException {
        // Given
        UserDto userDto = UserDtoMockUtils.createMockUserDto(userId);
        ReadingMangaDto readingMangaDto = ReadingMangaMockDtoUtils.createMockDto(null);
        ReadingMangaDto expectedSavedReadingMediaDto = ReadingMangaMockDtoUtils.createMockDto();

        when(mangaService.createManga(any(MangaDto.class))).thenReturn(new MangaDto());
        when(readingMangaMapper.toEntity(any())).thenCallRealMethod();
        when(userMapper.toEntity(any())).thenCallRealMethod();
        doReturn(expectedSavedReadingMediaDto).when(readingMangaService).save(any());

        // When
        ReadingMangaDto savedReadingMediaDto = readingMangaService.addMangaToReadingList(userDto, readingMangaDto);

        // Then
        assertThat(savedReadingMediaDto).isEqualTo(expectedSavedReadingMediaDto);
        verify(mangaService).createManga(any(MangaDto.class));
        verify(readingMangaMapper).toEntity(any());
        verify(userMapper).toEntity(any());
        verify(readingMangaService).save(any());
    }

    @Test
    void addMangaToReadingListAddsMangaWhenMangaAlreadyExists() throws MangaAlreadyExistException {
        // Given
        UserDto userDto = UserDtoMockUtils.createMockUserDto(userId);
        ReadingMangaDto readingMangaDto = ReadingMangaMockDtoUtils.createMockDto(null);
        ReadingMangaDto expectedSavedReadingMediaDto = ReadingMangaMockDtoUtils.createMockDto();

        when(mangaService.createManga(readingMangaDto.getManga())).thenThrow(MangaAlreadyExistException.class);
        when(readingMangaMapper.toEntity(any())).thenCallRealMethod();
        when(userMapper.toEntity(any())).thenCallRealMethod();
        doReturn(expectedSavedReadingMediaDto).when(readingMangaService).save(any());

        // When
        ReadingMangaDto savedReadingMediaDto = readingMangaService.addMangaToReadingList(userDto, readingMangaDto);

        // Then
        assertThat(savedReadingMediaDto).isEqualTo(expectedSavedReadingMediaDto);
        verify(mangaService).createManga(readingMangaDto.getManga());
        verify(readingMangaMapper).toEntity(any());
        verify(userMapper).toEntity(any());
        verify(readingMangaService).save(any());
    }
}
