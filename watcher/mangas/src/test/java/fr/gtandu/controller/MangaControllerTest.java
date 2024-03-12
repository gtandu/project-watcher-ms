package fr.gtandu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.gtandu.common.config.WatcherApiProperties;
import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.service.MangaService;
import fr.gtandu.utils.MangaDtoMockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MangaController.class)
class MangaControllerTest extends SetupControllerTest {

    @MockBean
    private MangaService mangaService;

    @Autowired
    private WatcherApiProperties watcherApiProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void searchByName() {
    }

    @Test
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
                .andExpect(status().isForbidden());

        // THEN

        verify(mangaService, never()).createManga(mockMangaDto);
    }

    @Test
    void createMangaStatusShouldBeCreatedWhenUserIsAuthenticated() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = MangaDtoMockUtils.createMockMangaDto(null);
        MangaDto savedMockMangaDto = MangaDtoMockUtils.copyMockMangaDto(mockMangaDto);
        savedMockMangaDto.setId(1L);

        when(mangaService.createManga(mockMangaDto)).thenReturn(savedMockMangaDto);

        // WHEN
        mockMvc.perform(put(watcherApiProperties.manga().baseUrl())
                        .with(JWT_REQUEST_POST_PROCESSOR)
                        .content(objectMapper.writeValueAsString(mockMangaDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());

        // THEN

        verify(mangaService).createManga(mockMangaDto);
    }

    @Test
    void createMangaStatusShouldBeConflictWhenMangaAlreadyExist() throws Exception {
        // GIVEN
        MangaDto mockMangaDto = MangaDtoMockUtils.createMockMangaDto();

        when(mangaService.createManga(mockMangaDto)).thenThrow(MangaAlreadyExistException.class);

        // WHEN
        mockMvc.perform(put(watcherApiProperties.manga().baseUrl())
                        .with(JWT_REQUEST_POST_PROCESSOR)
                        .content(objectMapper.writeValueAsString(mockMangaDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isConflict());

        // THEN

        verify(mangaService).createManga(mockMangaDto);
    }

    @Test
    void deleteMangaByIdStatusShouldBeForbiddenWhenUserIsNotAuthenticated() throws Exception {
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
    void deleteMangaByIdStatusShouldBeNoContent() throws Exception {
        // GIVEN
        String deleteMangaByIdUrl = watcherApiProperties.manga().baseUrl().concat(watcherApiProperties.manga().deleteMangaById());
        long mangaId = 1L;
        boolean isDeleted = true;

        when(mangaService.deleteMangaById(mangaId)).thenReturn(isDeleted);

        // WHEN

        mockMvc.perform(delete(deleteMangaByIdUrl, mangaId)
                        .with(JWT_REQUEST_POST_PROCESSOR))
                .andExpect(status().isNoContent());

        // THEN
        verify(mangaService).deleteMangaById(mangaId);

    }

    @Test
    void deleteMangaByIdStatusShouldBeNotFound() throws Exception {
        // GIVEN
        String deleteMangaByIdUrl = watcherApiProperties.manga().baseUrl().concat(watcherApiProperties.manga().deleteMangaById());
        boolean isDeleted = false;
        long mangaId = 1L;
        when(mangaService.deleteMangaById(mangaId)).thenReturn(isDeleted);


        // WHEN
        mockMvc.perform(delete(deleteMangaByIdUrl, mangaId)
                        .with(JWT_REQUEST_POST_PROCESSOR))
                .andExpect(status().isNotFound());

        // THEN
        verify(mangaService).deleteMangaById(mangaId);
    }

}
