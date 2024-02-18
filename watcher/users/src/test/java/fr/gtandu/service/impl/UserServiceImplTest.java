package fr.gtandu.service.impl;

import fr.gtandu.repository.UserRepository;
import fr.gtandu.shared.core.document.MangaDocument;
import fr.gtandu.shared.core.document.UserDocument;
import fr.gtandu.shared.core.dto.MangaDto;
import fr.gtandu.shared.core.dto.ReadingMediaDto;
import fr.gtandu.shared.core.dto.UserDto;
import fr.gtandu.shared.core.enums.ReadingFormat;
import fr.gtandu.shared.core.mapper.*;
import fr.gtandu.shared.core.model.ReadingMedia;
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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserMapperImpl.class, ReadingMediaMapperImpl.class, MediaDocumentMapperImpl.class, MangaMapperImpl.class, ReadingFormatStatusMapperImpl.class})
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private ReadingMediaMapper readingMediaMapper = Mappers.getMapper(ReadingMediaMapper.class);
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private static final String USER_ID = "87238780-05bc-4073-a75e-ede64e0232f0";

    @BeforeEach
    public void init() {
        MediaDocumentMapper mediaDocumentMapper = Mappers.getMapper(MediaDocumentMapper.class);
        ReadingFormatStatusMapper readingFormatStatusMapper = Mappers.getMapper(ReadingFormatStatusMapper.class);

        ReflectionTestUtils.setField(readingMediaMapper, "mediaDocumentMapper", mediaDocumentMapper);
        ReflectionTestUtils.setField(readingMediaMapper, "readingFormatStatusMapper", readingFormatStatusMapper);
        ReflectionTestUtils.setField(mediaDocumentMapper, "mangaMapper", Mappers.getMapper(MangaMapper.class));
        ReflectionTestUtils.setField(userMapper, "readingMediaMapper", readingMediaMapper);
    }

    @Test
    void getUserById() {
        // GIVEN

        UserDocument userDocument = UserDocument
                .builder()
                .keycloakId(USER_ID)
                .username("userTest")
                .firstName("User")
                .lastName("Test")
                .build();

        when(userRepository.findById(USER_ID)).thenReturn(Mono.just(userDocument));
        when(userMapper.toDto(any())).thenCallRealMethod();

        // WHEN
        Mono<UserDto> userDtoMono = userService.getUserById(USER_ID);

        // THEN
        StepVerifier.create(userDtoMono).consumeNextWith(userFound -> {
            assertEquals(USER_ID, userFound.getKeycloakId());
        }).verifyComplete();

        verify(userRepository).findById(USER_ID);
        verify(userMapper).toDto(any());
    }

    @Test
    void addMediaToReadingList() {
        // GIVEN
        MangaDocument mangaDocument = MangaDocument
                .builder()
                .name("Test manga")
                .description("Description manga")
                .build();
        ReadingMedia readingMedia = ReadingMedia
                .builder()
                .mediaDocument(mangaDocument)
                .readingFormat(ReadingFormat.CHAPTER)
                .build();

        MangaDto mangaDto = MangaDto
                .builder()
                .name("Test manga")
                .description("Description manga")
                .build();

        ReadingMediaDto readingMediaDto = ReadingMediaDto
                .builder()
                .mediaDocument(mangaDto)
                .readingFormat(ReadingFormat.CHAPTER)
                .build();

        UserDocument userDocument = UserDocument
                .builder()
                .keycloakId(USER_ID)
                .readingMediaList(new ArrayList<>())
                .build();

        UserDocument userDocumentWithReadingMedia = UserDocument
                .builder()
                .keycloakId(USER_ID)
                .firstName("user")
                .lastName("test")
                .username("userTest")
                .readingMediaList(Collections.singletonList(readingMedia))
                .build();

        when(userRepository.findById(USER_ID)).thenReturn(Mono.just(userDocument));
        when(readingMediaMapper.toDocument(any())).thenCallRealMethod();
        when(userRepository.save(any())).thenReturn(Mono.just(userDocumentWithReadingMedia));
        when(userMapper.toDto(any())).thenCallRealMethod();


        // WHEN
        // TODO Test avec le switch
//        Mono<UserDto> userDtoMono = userService.addMediaToReadingList(USER_ID, readingMediaDto);
//
//        // THEN
//        StepVerifier
//                .create(userDtoMono)
//                .consumeNextWith(userDtoSaved -> {
//                    assertThat(userDtoSaved.getReadingMediaList()).containsExactly(readingMediaDto);
//                }).verifyComplete();

        verify(userRepository).findById(USER_ID);
        verify(readingMediaMapper).toDocument(any());
        verify(userRepository).save(any());
        verify(userMapper).toDto(any());
    }
}
