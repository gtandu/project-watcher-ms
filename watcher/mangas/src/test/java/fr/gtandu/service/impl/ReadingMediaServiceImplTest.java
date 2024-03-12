package fr.gtandu.service.impl;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.keycloak.mapper.UserMapper;
import fr.gtandu.keycloak.mapper.UserMapperImpl;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.media.dto.ReadingMediaDto;
import fr.gtandu.media.entity.ReadingMediaEntity;
import fr.gtandu.media.mapper.*;
import fr.gtandu.repository.ReadingMediaRepository;
import fr.gtandu.service.MangaService;
import fr.gtandu.service.UserService;
import fr.gtandu.utils.ReadingMediaMockDtoUtils;
import fr.gtandu.utils.ReadingMediaMockUtils;
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
@ContextConfiguration(classes = {UserMapperImpl.class, ReadingMediaMapperImpl.class, MediaMapperImpl.class, MangaMapperImpl.class, ReadingFormatStatusMapperImpl.class})
class ReadingMediaServiceImplTest {

    @Mock
    private MangaService mangaService;
    @Mock
    private ReadingMediaRepository readingMediaRepository;
    @Mock
    private UserService userService;
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Spy
    private ReadingMediaMapper readingMediaMapper = Mappers.getMapper(ReadingMediaMapper.class);
    ;
    @InjectMocks
    @Spy
    private ReadingMediaServiceImpl readingMediaService;

    private final String USER_ID = "8f6561be-039f-43fe-90d6-fc493cc1759a";


    @BeforeEach
    public void init() {
        MediaMapper mediaMapper = Mappers.getMapper(MediaMapper.class);
        ReadingFormatStatusMapper readingFormatStatusMapper = Mappers.getMapper(ReadingFormatStatusMapper.class);

        ReflectionTestUtils.setField(mediaMapper, "mangaMapper", Mappers.getMapper(MangaMapper.class));
        ReflectionTestUtils.setField(readingMediaMapper, "mediaMapper", mediaMapper);
        ReflectionTestUtils.setField(readingMediaMapper, "readingFormatStatusMapper", readingFormatStatusMapper);
    }

    @Test
    void getAllReadingMediasByUserIdShouldReturnEmptyListOfReadingMediasWhenOptionalIsEmpty() {
        // GIVEN
        String userId = "8f6561be-039f-43fe-90d6-fc493cc1759a";

        doCallRealMethod().when(readingMediaMapper).toDtoList(anyList());
        when(readingMediaRepository.findAllByUserId(userId)).thenReturn(Optional.empty());

        // WHEN
        List<ReadingMediaDto> allReadingMediasByUserId = readingMediaService.getAllReadingMediasByUserId(userId);

        // THEN
        assertThat(allReadingMediasByUserId).isEmpty();

        verify(readingMediaMapper).toDtoList(any());
        verify(readingMediaRepository).findAllByUserId(userId);
    }

    @Test
    void getAllReadingMediasByUserIdShouldReturnFilledListOfReadingMedias() {
        // GIVEN
        long readingMediaId1 = 1L;
        long mangaId1 = 10L;
        ReadingMediaEntity readingMediaEntity1 = ReadingMediaMockUtils.createMock(readingMediaId1, mangaId1);
        ReadingMediaDto readingMediaDto1 = ReadingMediaMockDtoUtils.createMockDto(readingMediaId1, mangaId1);

        long readingMediaId2 = 2L;
        long mangaId2 = 20L;
        ReadingMediaEntity readingMediaEntity2 = ReadingMediaMockUtils.createMock(readingMediaId2, mangaId2);
        ReadingMediaDto readingMediaDto2 = ReadingMediaMockDtoUtils.createMockDto(readingMediaId2, mangaId2);

        long readingMediaId3 = 3L;
        long mangaId3 = 30L;
        ReadingMediaEntity readingMediaEntity3 = ReadingMediaMockUtils.createMock(readingMediaId3, mangaId3);
        ReadingMediaDto readingMediaDto3 = ReadingMediaMockDtoUtils.createMockDto(readingMediaId3, mangaId3);

        List<ReadingMediaDto> expectedReadingMediaDto = Arrays.asList(readingMediaDto1, readingMediaDto2, readingMediaDto3);

        doCallRealMethod().when(readingMediaMapper).toDtoList(anyList());
        when(readingMediaRepository.findAllByUserId(USER_ID)).thenReturn(Optional.of(Arrays.asList(readingMediaEntity1, readingMediaEntity2, readingMediaEntity3)));

        // WHEN
        List<ReadingMediaDto> allReadingMediasByUserId = readingMediaService.getAllReadingMediasByUserId(USER_ID);

        // THEN
        assertThat(allReadingMediasByUserId)
                .isNotEmpty();

        verify(readingMediaMapper).toDtoList(any());
        verify(readingMediaRepository).findAllByUserId(USER_ID);
    }

    @Test
    void saveShouldReturnSavedReadingMediaDto() {
        // GIVEN
        ReadingMediaEntity readingMediaEntity = ReadingMediaMockUtils.createMock(null, 10L);
        ReadingMediaEntity savedReadingMediaEntity = ReadingMediaMockUtils.createMock(1L, 10L);

        doCallRealMethod().when(readingMediaMapper).toDto(readingMediaEntity);
        when(readingMediaRepository.save(readingMediaEntity)).thenReturn(savedReadingMediaEntity);
        // WHEN
        ReadingMediaDto readingMediaDto = readingMediaService.save(readingMediaEntity);

        // THEN
        assertThat(readingMediaDto).isNotNull();
        assertThat(readingMediaDto.getId()).isEqualTo(savedReadingMediaEntity.getId());
    }

    @Test
    void addMediaToReadingList() throws MangaAlreadyExistException {
        // GIVEN
        UserDto userDto = UserDtoMockUtils.createMockUserDto(USER_ID);

        ReadingMediaDto readingMediaDto = ReadingMediaMockDtoUtils.createMockDto(null);
        ReadingMediaEntity unSavedReadingMediaEntity = ReadingMediaMockUtils.createMock(null);
        ReadingMediaDto expectedSavedReadingMediaDto = ReadingMediaMockDtoUtils.createMockDto();


        when(mangaService.createManga((MangaDto) readingMediaDto.getMedia())).thenThrow(MangaAlreadyExistException.class);
        when(readingMediaMapper.toEntity(any())).thenCallRealMethod();
        when(userMapper.toEntity(any())).thenCallRealMethod();
        doReturn(expectedSavedReadingMediaDto).when(readingMediaService).save(any());

        // WHEN
        ReadingMediaDto savedReadingMediaDto = readingMediaService.addMediaToReadingList(userDto, readingMediaDto);

        assertThat(savedReadingMediaDto).isEqualTo(expectedSavedReadingMediaDto);

        verify(mangaService).createManga((MangaDto) readingMediaDto.getMedia());
        verify(readingMediaMapper).toEntity(any());
        verify(userMapper).toEntity(any());
        verify(readingMediaService).save(any());
    }
}
