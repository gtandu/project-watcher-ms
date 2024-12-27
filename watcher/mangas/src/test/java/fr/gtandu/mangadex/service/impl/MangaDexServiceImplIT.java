package fr.gtandu.mangadex.service.impl;

import fr.gtandu.config.RedisTestConfiguration;
import fr.gtandu.mangadex.config.MangaDexApiProperties;
import fr.gtandu.media.dto.MangaDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = RedisTestConfiguration.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("noKeycloak")
@EnableCaching
class MangaDexServiceImplIT {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MangaDexServiceImpl mangaDexService;

    @Autowired
    private MangaDexApiProperties mangaDexApiProperties;

    @Test
    void searchMangaByTitleShouldCallCache() {

        // GIVEN
        String title = "Naruto";
        String nameProperty = "name";
        List<MangaDto> mangaDtoList = mangaDexService.searchMangaByTitle(title, 10);

        assertThat(mangaDtoList)
                .isNotNull()
                .hasSizeLessThanOrEqualTo(mangaDexApiProperties.searchManga().queryParams().queryParamValueLimit())
                .extracting(nameProperty).contains(title)
                .doesNotContain(mangaDexApiProperties.searchManga().queryParams().excludedTagNames());

        Cache cache = cacheManager.getCache("mangaDexCache");
        assertThat(cache).isNotNull();
        assertThat(cache.get(title)).isNotNull();  // La donnée doit être dans le cache

        // WHEN
        List<MangaDto> mangaDtoListFromCache = mangaDexService.searchMangaByTitle(title, 10);

        // THEN
        assertThat(mangaDtoListFromCache)
                .isEqualTo(mangaDtoList);
    }

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
