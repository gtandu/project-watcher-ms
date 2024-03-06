package fr.gtandu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.gtandu.service.ReadingMediaService;
import fr.gtandu.shared.core.keycloak.dto.UserDto;
import fr.gtandu.shared.core.keycloak.utils.JwtMapper;
import fr.gtandu.shared.core.media.dto.ReadingMediaDto;
import fr.gtandu.shared.core.media.dto.ReadingMediaDtoWithJwt;
import fr.gtandu.shared.core.test.ReadingMediaMockDtoUtils;
import fr.gtandu.shared.core.test.UserDtoMockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReadingMediaController.class, properties = {"server.ssl.enabled=false"})
@MockBean(JpaMetamodelMappingContext.class)
class ReadingMediaControllerTest {

    @MockBean
    private ReadingMediaService readingMediaService;
    @MockBean
    private JwtMapper jwtMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
//    @WithJwt("mockUserWIthUserRole.json")
    void getAllReadingMediaByUserId() throws Exception {
        // GIVEN
        UserDto mockUserDto = UserDtoMockUtils.createMockUserDto();
        when(jwtMapper.toUserDto(any())).thenReturn(mockUserDto);
        when(readingMediaService.getAllReadingMediasByUserId(mockUserDto.getId())).thenReturn(ReadingMediaMockDtoUtils.createMockDtoList());

        // WHEN
        mockMvc.perform(get("/api/v1/reading-medias").with(jwt()))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(content().string(containsString("Hello, World")));

        // THEN
    }

    @Test
    void addMediaToReadingList() throws Exception {
        // GIVEN
        UserDto mockUserDto = UserDtoMockUtils.createMockUserDto();
        ReadingMediaDto readingMediaDto = ReadingMediaMockDtoUtils.createMockDto(null);
        ReadingMediaDto savedReadingMediaDto = ReadingMediaMockDtoUtils.createMockDto(1L);

        ReadingMediaDtoWithJwt readingMediaDtoWithJwt = new ReadingMediaDtoWithJwt(null, readingMediaDto);

        when(jwtMapper.toUserDto(any())).thenReturn(mockUserDto);
        when(readingMediaService.addMediaToReadingList(mockUserDto, readingMediaDto)).thenReturn(savedReadingMediaDto);

        // WHEN
        mockMvc.perform(put("/api/v1/reading-medias")
                        .content(objectMapper.writeValueAsString(readingMediaDtoWithJwt)))
                .andExpect(status().isOk());

        // THEN
    }
}