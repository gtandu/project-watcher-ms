package fr.gtandu.controller;

import fr.gtandu.service.UserService;
import fr.gtandu.shared.core.dto.MangaDto;
import fr.gtandu.shared.core.dto.ReadingMediaDto;
import fr.gtandu.shared.core.dto.UserDto;
import fr.gtandu.shared.core.enums.MediaType;
import fr.gtandu.shared.core.enums.ReadingFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UserController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private UserService userService;

    private static final String USER_ID = "87238780-05bc-4073-a75e-ede64e0232f0";

    @Test
    void getUserById() {
        // GIVEN
        UserDto userDto = UserDto
                .builder()
                .keycloakId(USER_ID)
                .firstName("User")
                .lastName("Test")
                .username("userTest")
                .readingMediaList(new ArrayList<>())
                .build();

        when(userService.getUserById(USER_ID)).thenReturn(Mono.just(userDto));

        // WHEN
        webClient
                .get()
                .uri("/api/v1/users/{userId}", USER_ID)
                .exchange()
                // THEN
                .expectStatus()
                .isOk()
                .expectBody(UserDto.class);
    }

    @Test
    void addMediaToReadingList() {
        // GIVEN
        MangaDto mangaDto = MangaDto
                .builder()
                .name("Test manga")
                .description("Description manga")
                .type(MediaType.MANGA)
                .build();

        ReadingMediaDto readingMediaDto = ReadingMediaDto
                .builder()
                .mediaDocument(mangaDto)
                .readingFormat(ReadingFormat.CHAPTER)
                .build();

        UserDto userDto = UserDto
                .builder()
                .keycloakId(USER_ID)
                .firstName("User")
                .lastName("Test")
                .username("userTest")
                .readingMediaList(Collections.singletonList(readingMediaDto))
                .build();

        when(userService.addMediaToReadingList(any(UserDto.class), readingMediaDto)).thenReturn(Mono.just(userDto));

        // WHEN
        webClient
                .put()
                .uri("/api/v1/users/readingMedia/{userId}", USER_ID)
                .bodyValue(readingMediaDto)
                .exchange()
                // THEN
                .expectStatus()
                .isOk()
                .expectBody(UserDto.class);
    }
}
