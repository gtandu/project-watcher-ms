package fr.gtandu.mangadex.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "manga-dex-api")
public record MangaDexApiProperties(String baseUrl, SearchCover searchCover, SearchManga searchManga,
                                    SearchTags searchTags, Integer searchLimit, String cacheName) {

    public record QueryParams(String queryParamNameTitle, String queryParamNameLimit, Integer queryParamValueLimit,
                              String queryParamNameIncludes,
                              List<Object> queryParamValueIncludes, String queryParamExcludedTagIDs,
                              List<String> excludedTagNames) {
    }

    public record SearchCover(String uri) {
    }

    public record SearchManga(String uri, QueryParams queryParams) {
    }

    public record SearchTags(String uri) {
    }
}
