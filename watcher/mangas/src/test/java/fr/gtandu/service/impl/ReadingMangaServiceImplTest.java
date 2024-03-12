package fr.gtandu.service.impl;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.keycloak.mapper.UserMapper;
import fr.gtandu.keycloak.mapper.UserMapperImpl;
import fr.gtandu.media.dto.ReadingMangaDto;
import fr.gtandu.media.entity.ReadingMangaEntity;
import fr.gtandu.media.mapper.*;
import fr.gtandu.repository.ReadingMangaRepository;
import fr.gtandu.service.MangaService;
import fr.gtandu.service.UserService;
import fr.gtandu.utils.ReadingMangaMockDtoUtils;
import fr.gtandu.utils.ReadingMangaMockUtils;
import fr.gtandu.utils.UserDtoMockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Spy
    private ReadingMangaMapper readingMangaMapper = Mappers.getMapper(ReadingMangaMapper.class);

    @InjectMocks
    @Spy
    private ReadingMangaServiceImpl readingMangaService;

    private final String USER_ID = "8f6561be-039f-43fe-90d6-fc493cc1759a";


    @BeforeEach
    public void init() {
        MangaMapper mangaMapper = Mappers.getMapper(MangaMapper.class);
        ReadingFormatStatusMapper readingFormatStatusMapper = Mappers.getMapper(ReadingFormatStatusMapper.class);

        ReflectionTestUtils.setField(readingMangaMapper, "mangaMapper", mangaMapper);
        ReflectionTestUtils.setField(readingMangaMapper, "readingFormatStatusMapper", readingFormatStatusMapper);
    }

    @Test
    void getAllReadingMangasByUserIdShouldReturnEmptyListOfReadingMangasWhenOptionalIsEmpty() {
        // GIVEN
        String userId = "8f6561be-039f-43fe-90d6-fc493cc1759a";

        doCallRealMethod().when(readingMangaMapper).toDtoList(anyList());
        when(readingMangaRepository.findAllByUserId(userId)).thenReturn(Optional.empty());

        // WHEN
        List<ReadingMangaDto> allReadingMangasByUserId = readingMangaService.getAllReadingMangasByUserId(userId);

        // THEN
        assertThat(allReadingMangasByUserId).isEmpty();

        verify(readingMangaMapper).toDtoList(any());
        verify(readingMangaRepository).findAllByUserId(userId);
    }

    @Test
    void getAllReadingMangasByUserIdShouldReturnFilledListOfReadingMangas() {
        // GIVEN
        long readingMangaId1 = 1L;
        long mangaId1 = 10L;
        ReadingMangaEntity readingMangaEntity1 = ReadingMangaMockUtils.createMock(readingMangaId1, mangaId1);
        ReadingMangaDto readingMangaDto1 = ReadingMangaMockDtoUtils.createMockDto(readingMangaId1, mangaId1);

        long readingMangaId2 = 2L;
        long mangaId2 = 20L;
        ReadingMangaEntity readingMangaEntity2 = ReadingMangaMockUtils.createMock(readingMangaId2, mangaId2);
        ReadingMangaDto readingMangaDto2 = ReadingMangaMockDtoUtils.createMockDto(readingMangaId2, mangaId2);

        long readingMangaId3 = 3L;
        long mangaId3 = 30L;
        ReadingMangaEntity readingMangaEntity3 = ReadingMangaMockUtils.createMock(readingMangaId3, mangaId3);
        ReadingMangaDto readingMangaDto3 = ReadingMangaMockDtoUtils.createMockDto(readingMangaId3, mangaId3);

        List<ReadingMangaDto> expectedReadingMangaDtoList = Arrays.asList(readingMangaDto1, readingMangaDto2, readingMangaDto3);

        doCallRealMethod().when(readingMangaMapper).toDtoList(anyList());
        when(readingMangaRepository.findAllByUserId(USER_ID)).thenReturn(Optional.of(Arrays.asList(readingMangaEntity1, readingMangaEntity2, readingMangaEntity3)));

        // WHEN
        List<ReadingMangaDto> allReadingMangasByUserId = readingMangaService.getAllReadingMangasByUserId(USER_ID);

        // THEN
        assertThat(allReadingMangasByUserId)
                .isNotEmpty();

        verify(readingMangaMapper).toDtoList(any());
        verify(readingMangaRepository).findAllByUserId(USER_ID);
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
    void addMangaToReadingList() throws MangaAlreadyExistException {
        // GIVEN
        UserDto userDto = UserDtoMockUtils.createMockUserDto(USER_ID);

        ReadingMangaDto readingMangaDto = ReadingMangaMockDtoUtils.createMockDto(null);
        ReadingMangaDto expectedSavedReadingMediaDto = ReadingMangaMockDtoUtils.createMockDto();


        when(mangaService.createManga(readingMangaDto.getManga())).thenThrow(MangaAlreadyExistException.class);
        when(readingMangaMapper.toEntity(any())).thenCallRealMethod();
        when(userMapper.toEntity(any())).thenCallRealMethod();
        doReturn(expectedSavedReadingMediaDto).when(readingMangaService).save(any());

        // WHEN
        ReadingMangaDto savedReadingMediaDto = readingMangaService.addMangaToReadingList(userDto, readingMangaDto);

        assertThat(savedReadingMediaDto).isEqualTo(expectedSavedReadingMediaDto);

        verify(mangaService).createManga(readingMangaDto.getManga());
        verify(readingMangaMapper).toEntity(any());
        verify(userMapper).toEntity(any());
        verify(readingMangaService).save(any());
    }
}
