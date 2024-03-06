package fr.gtandu.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class MangaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //    @Test
//    void shouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, World")));
//    }
    @Test
    void getMangaById() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void getAll() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void createManga() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void addMediaToReadingList() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void updateManga() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void deleteMangaById() {
        // GIVEN

        // WHEN

        // THEN
    }
}