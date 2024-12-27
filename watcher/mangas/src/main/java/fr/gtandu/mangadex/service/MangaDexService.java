package fr.gtandu.mangadex.service;

import fr.gtandu.media.dto.MangaDto;

import java.util.List;

public interface MangaDexService {
    /**
     * Searches for manga by title.
     * <p>
     * This method first retrieves a list of doujinshi IDs, which are used to exclude certain types of manga from the search results.
     * It then makes a request to the MangaDex API to search for manga with the given title, excluding the doujinshi.
     * If the API response is not null, it maps the response data to a list of MangaDto objects and returns it.
     * If the API response is null, it returns an empty list.
     *
     * @param title The title of the manga to search for.
     * @param limit The maximum number of results to return.
     * @return A list of MangaDto objects representing the search results, or an empty list if the API response is null.
     */
    List<MangaDto> searchMangaByTitle(String title, Integer limit);
}
