package fr.gtandu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.gtandu.common.config.WatcherApiProperties;
import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.keycloak.utils.JwtMapper;
import fr.gtandu.media.dto.ReadingMediaDto;
import fr.gtandu.service.ReadingMediaService;
import fr.gtandu.utils.ReadingMediaMockDtoUtils;
import fr.gtandu.utils.UserDtoMockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReadingMediaController.class)
class ReadingMediaControllerTest extends SetupControllerTest {

    @MockBean
    private ReadingMediaService readingMediaService;
    @MockBean
    private JwtMapper jwtMapper;
    @Autowired
    private WatcherApiProperties watcherApiProperties;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllReadingMediaByUserId() throws Exception {
        // GIVEN
        UserDto mockUserDto = UserDtoMockUtils.createMockUserDto();
        when(jwtMapper.toUserDto(any())).thenReturn(mockUserDto);
        when(readingMediaService.getAllReadingMediasByUserId(mockUserDto.getId())).thenReturn(ReadingMediaMockDtoUtils.createMockDtoList());

        // WHEN
        mockMvc.perform(get(watcherApiProperties.readingMedia().baseUrl())
                        .with(JWT_REQUEST_POST_PROCESSOR))
                .andDo(print())
                // THEN
                .andExpect(status().isOk());

    }

    @Test
    void addMediaToReadingList() throws Exception {
        // GIVEN
        UserDto mockUserDto = UserDtoMockUtils.createMockUserDto();
        ReadingMediaDto readingMediaDto = ReadingMediaMockDtoUtils.createMockDto(null);
        ReadingMediaDto savedReadingMediaDto = ReadingMediaMockDtoUtils.createMockDto(1L);

        when(jwtMapper.toUserDto(any())).thenReturn(mockUserDto);
        when(readingMediaService.addMediaToReadingList(mockUserDto, readingMediaDto)).thenReturn(savedReadingMediaDto);

        // WHEN
        mockMvc.perform(put(watcherApiProperties.readingMedia().baseUrl())
                        .with(JWT_REQUEST_POST_PROCESSOR)
                        .content(objectMapper.writeValueAsString(readingMediaDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        // THEN
    }
}
