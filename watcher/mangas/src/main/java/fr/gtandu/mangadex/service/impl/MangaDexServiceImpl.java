package fr.gtandu.mangadex.service.impl;

import fr.gtandu.mangadex.config.MangaDexApiProperties;
import fr.gtandu.mangadex.dto.MangaDexResponse;
import fr.gtandu.mangadex.service.MangaDexService;
import fr.gtandu.media.dto.MangaDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MangaDexServiceImpl implements MangaDexService {
    public static final String COVER_ART = "cover_art";
    private final WebClient webClient;
    private final MangaDexApiProperties mangaDexApiProperties;

    public MangaDexServiceImpl(MangaDexApiProperties mangaDexApiProperties) {
        this.mangaDexApiProperties = mangaDexApiProperties;

        webClient = WebClient.builder()
                .baseUrl(mangaDexApiProperties.baseUrl())
                .build();
    }


    @Cacheable(value = "mangaDexCache", key = "#title")
    @Override
    public List<MangaDto> searchMangaByTitle(String title, Integer limit) {
        // Make a request to the MangaDex API to search for manga with the given title, excluding the doujinshi
        MangaDexResponse mangaDexResponse = getMangaDexResponse(title, limit);

        // If the API response is not null, map the response data to a list of MangaDto objects and return it
        if (mangaDexResponse != null) {
            return Arrays.stream(mangaDexResponse.data())
                    .filter(mangaDexObject -> StringUtils.isNotEmpty(mangaDexObject.attributes().title().en()))
                    .map(this::mapToMangaDto)
                    .toList();
        }

        // If the API response is null, return an empty list
        return Collections.emptyList();
    }

    private List<String> getDoujinshiIds() {
        MangaDexResponse mangaDexResponseTags = webClient.get()
                .uri(mangaDexApiProperties.searchTags().uri())
                .retrieve()
                .bodyToMono(MangaDexResponse.class)
                .block();

        if (mangaDexResponseTags == null || mangaDexResponseTags.data() == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(mangaDexResponseTags.data())
                .filter(mangaDexObject -> mangaDexApiProperties.searchManga().queryParams().excludedTagNames().stream().anyMatch(s -> s.contains(mangaDexObject.attributes().name().en())))
                .map(MangaDexResponse.MangaDexObject::id)
                .toList();
    }

    private MangaDexResponse getMangaDexResponse(String title, Integer limit) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(mangaDexApiProperties.searchManga().uri())
                        .queryParam(mangaDexApiProperties.searchManga().queryParams().queryParamNameTitle(), title)
                        .queryParam(mangaDexApiProperties.searchManga().queryParams().queryParamNameLimit(), limit != null ? limit : mangaDexApiProperties.searchManga().queryParams().queryParamValueLimit())
                        .queryParam(mangaDexApiProperties.searchManga().queryParams().queryParamNameIncludes(), mangaDexApiProperties.searchManga().queryParams().queryParamValueIncludes())
                        .queryParam(mangaDexApiProperties.searchManga().queryParams().queryParamExcludedTagIDs(), getDoujinshiIds())
                        .build())
                .retrieve()
                .bodyToMono(MangaDexResponse.class)
                .block();
    }

    private MangaDto mapToMangaDto(MangaDexResponse.MangaDexObject mangaDexObject) {
        Optional<MangaDexResponse.MangaDexObjectRelationshipsData> coverArt = getCoverArt(mangaDexObject, COVER_ART);
        MangaDexResponse.MangaDexObjectRelationshipsData mangaDexObjectRelationshipsData = coverArt.orElse(null);

        return buildMangaDto(mangaDexObject, mangaDexObjectRelationshipsData);
    }

    private Optional<MangaDexResponse.MangaDexObjectRelationshipsData> getCoverArt(MangaDexResponse.MangaDexObject mangaDexObject, String queryParamValueCoverArt) {
        return Arrays.stream(mangaDexObject.relationships()).filter(mangaDexObjectRelationshipsData -> mangaDexObjectRelationshipsData.type().equals(queryParamValueCoverArt)).findAny();
    }

    private MangaDto buildMangaDto(MangaDexResponse.MangaDexObject mangaDexObject, MangaDexResponse.MangaDexObjectRelationshipsData mangaDexObjectRelationshipsData) {
        return MangaDto.builder()
                .name(mangaDexObject.attributes().title().en())
                .description(mangaDexObject.attributes().description().en())
                .releasedDate(mangaDexObject.attributes().year())
                .coverPictureUrl(mangaDexObjectRelationshipsData != null && mangaDexObjectRelationshipsData.attributes() != null ? buildCoverUri(mangaDexObject, mangaDexObjectRelationshipsData) : StringUtils.EMPTY)
                .rate(0.0)
                .review(StringUtils.EMPTY)
                .state(mangaDexObject.attributes().status())
                .readingSource("")
                .build();
    }

    private String buildCoverUri(MangaDexResponse.MangaDexObject mangaDexObject, MangaDexResponse.MangaDexObjectRelationshipsData mangaDexObjectRelationshipsData) {
        String slash = "/";
        return mangaDexApiProperties.searchCover().uri() + slash + mangaDexObject.id() + slash + mangaDexObjectRelationshipsData.attributes().fileName();
    }
}
