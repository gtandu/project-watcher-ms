package fr.gtandu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.gtandu.common.config.properties.WatcherApiProperties;
import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.service.MangaService;
import fr.gtandu.utils.MangaDtoMockUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static fr.gtandu.common.constant.AppConstant.PAGE_SIZE_LIMIT;
import static fr.gtandu.utils.MangaDtoMockUtils.createMockMangaDto;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MangaControllerTest extends SetupControllerTest {

    @MockBean
    private MangaService mangaService;

    @Autowired
    private WatcherApiProperties watcherApiProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static Stream<Arguments> provideArgumentsForSearchByName() {
        return Stream.of(
                Arguments.of("Naruto", Collections.singletonList(createMockMangaDto(null, "Naruto"))),
                Arguments.of("NonExistentManga", Collections.emptyList())
        );
    }

    @ParameterizedTest
    @DisplayName("should return ok status when mangas are found or not")
    @MethodSource("provideArgumentsForSearchByName")
    @WithMockUser(username = "user")
    void searchByNameReturnsOkWhenMangasAreFoundedOrNot(String searchKey, List<MangaDto> mangaDtoList) throws Exception {
        // GIVEN
        Pageable pageable = PageRequest.of(0, PAGE_SIZE_LIMIT);
        final String SEARCH_BY_NAME_URL = watcherApiProperties.manga().baseUrl().concat(watcherApiProperties.manga().searchByName());


        when(mangaService.searchByName(searchKey, pageable)).thenReturn(mangaDtoList);

        // WHEN
        mockMvc.perform(get(SEARCH_BY_NAME_URL, searchKey))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(mangaDtoList.size())));


        // THEN
        verify(mangaService).searchByName(searchKey, pageable);
    }

    @Test
    @DisplayName("should return bad request status when search key is too short")
    @WithMockUser(username = "user")
    void searchByNameReturnsBadRequestWhenSearchKeyIsTooShort() throws Exception {
        // GIVEN
        Pageable pageable = PageRequest.of(0, PAGE_SIZE_LIMIT);
        final String SEARCH_BY_NAME_URL = watcherApiProperties.manga().baseUrl().concat(watcherApiProperties.manga().searchByName());
        String searchKey = "Na";

        // WHEN
        mockMvc.perform(get(SEARCH_BY_NAME_URL, searchKey))
                .andExpect(status().isBadRequest());

        // THEN
        verify(mangaService, never()).searchByName(searchKey, pageable);
    }

    @Test
    @DisplayName("should return forbidden status when jwt authentication is missing")
    void createMangaStatusShouldBeForbiddenWhenJwtAuthenticationIsMissing() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = MangaDtoMockUtils.createMockMangaDto(null);
        MangaDto savedMockMangaDto = MangaDtoMockUtils.copyMockMangaDto(mockMangaDto);
        savedMockMangaDto.setId(1L);

        when(mangaService.createManga(mockMangaDto)).thenReturn(savedMockMangaDto);

        // WHEN
        mockMvc.perform(put(watcherApiProperties.manga().baseUrl())
                        .content(objectMapper.writeValueAsString(mockMangaDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        // THEN

        verify(mangaService, never()).createManga(mockMangaDto);
    }

    @Test
    @DisplayName("should create manga when user is authenticated with admin roles")
    @WithMockUser(username = "user", roles = "ADMIN")
    void createMangaStatusShouldBeCreatedWhenUserIsAuthenticatedWithAdminRoles() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = MangaDtoMockUtils.createMockMangaDto(null);
        MangaDto savedMockMangaDto = MangaDtoMockUtils.copyMockMangaDto(mockMangaDto);
        savedMockMangaDto.setId(1L);

        when(mangaService.createManga(mockMangaDto)).thenReturn(savedMockMangaDto);

        // WHEN
        mockMvc.perform(put(watcherApiProperties.manga().baseUrl())
                        .content(objectMapper.writeValueAsString(mockMangaDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());

        // THEN

        verify(mangaService).createManga(mockMangaDto);
    }

    @Test
    @DisplayName("should return conflict status when manga already exist")
    @WithMockUser(username = "user", roles = "ADMIN")
    void createMangaStatusShouldBeConflictWhenMangaAlreadyExist() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = MangaDtoMockUtils.createMockMangaDto();

        when(mangaService.createManga(mockMangaDto)).thenThrow(MangaAlreadyExistException.class);

        // WHEN
        mockMvc.perform(put(watcherApiProperties.manga().baseUrl())
                        .content(objectMapper.writeValueAsString(mockMangaDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isConflict());

        // THEN

        verify(mangaService).createManga(mockMangaDto);
    }

    @Test
    @DisplayName("should return unauthorized status when user is not authenticated")
    void deleteMangaByIdStatusShouldBeUnauthorizedWhenUserIsNotAuthenticated() throws Exception {
        // GIVEN
        String deleteMangaByIdUrl = watcherApiProperties.manga().baseUrl().concat(watcherApiProperties.manga().deleteMangaById());
        long mangaId = 1L;
        boolean isDeleted = true;
        when(mangaService.deleteMangaById(mangaId)).thenReturn(isDeleted);

        // WHEN
        mockMvc.perform(delete(deleteMangaByIdUrl, mangaId))
                .andExpect(status().isUnauthorized());

        // THEN
        verify(mangaService, never()).deleteMangaById(mangaId);
    }

    @Test
    @DisplayName("should return unauthorized status when user has not admin role")
    @WithMockUser(username = "user")
    void deleteMangaByIdStatusShouldBeUnauthorizedWhenUserHasNotAdminRole() throws Exception {
        // GIVEN
        String deleteMangaByIdUrl = watcherApiProperties.manga().baseUrl().concat(watcherApiProperties.manga().deleteMangaById());
        long mangaId = 1L;
        boolean isDeleted = true;
        when(mangaService.deleteMangaById(mangaId)).thenReturn(isDeleted);

        // WHEN
        mockMvc.perform(delete(deleteMangaByIdUrl, mangaId))
                .andExpect(status().isForbidden());

        // THEN
        verify(mangaService, never()).deleteMangaById(mangaId);
    }

    @Test
    @DisplayName("should return no content status when manga is deleted")
    @WithMockUser(username = "user", roles = "ADMIN")
    void deleteMangaByIdStatusShouldBeNoContent() throws Exception {
        // GIVEN
        String deleteMangaByIdUrl = watcherApiProperties.manga().baseUrl().concat(watcherApiProperties.manga().deleteMangaById());
        long mangaId = 1L;
        boolean isDeleted = true;

        when(mangaService.deleteMangaById(mangaId)).thenReturn(isDeleted);

        // WHEN

        mockMvc.perform(delete(deleteMangaByIdUrl, mangaId))
                .andExpect(status().isNoContent());

        // THEN
        verify(mangaService).deleteMangaById(mangaId);

    }

    @Test
    @DisplayName("should return not found status when manga is not deleted")
    @WithMockUser(username = "user", roles = "ADMIN")
    void deleteMangaByIdStatusShouldBeNotFound() throws Exception {
        // GIVEN
        String deleteMangaByIdUrl = watcherApiProperties.manga().baseUrl().concat(watcherApiProperties.manga().deleteMangaById());
        boolean isDeleted = false;
        long mangaId = 1L;
        when(mangaService.deleteMangaById(mangaId)).thenReturn(isDeleted);


        // WHEN
        mockMvc.perform(delete(deleteMangaByIdUrl, mangaId))
                .andExpect(status().isNotFound());

        // THEN
        verify(mangaService).deleteMangaById(mangaId);
    }

}
