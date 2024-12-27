package fr.gtandu.mangadex.service.impl;

import fr.gtandu.mangadex.config.MangaDexApiProperties;
import fr.gtandu.media.dto.MangaDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("noKeycloak")
class MangaDexServiceImplIT {

    @Autowired
    private MangaDexServiceImpl mangaDexService;

    @Autowired
    private MangaDexApiProperties mangaDexApiProperties;

    @Test
    void searchMangaByTitleShouldReturnResultWhenTitleIsNaruto() {

        // GIVEN
        String title = "Naruto";
        String nameProperty = "name";
        // WHEN
        List<MangaDto> mangaDtoList = mangaDexService.searchMangaByTitle(title, 10);

        // THEN
        assertThat(mangaDtoList)
                .isNotNull()
                .hasSizeLessThanOrEqualTo(mangaDexApiProperties.searchManga().queryParams().queryParamValueLimit())
                .extracting(nameProperty).contains(title)
                .doesNotContain(mangaDexApiProperties.searchManga().queryParams().excludedTagNames());
    }

    @Test
    void searchMangaByTitleShouldReturnEmptyListWhenTitleIsUnknown() {

        // GIVEN
        String title = "Azertyyyyy";
        // WHEN
        List<MangaDto> mangaDtoList = mangaDexService.searchMangaByTitle(title, 10);

        // THEN
        assertThat(mangaDtoList)
                .isNotNull()
                .isEmpty();
    }
}
