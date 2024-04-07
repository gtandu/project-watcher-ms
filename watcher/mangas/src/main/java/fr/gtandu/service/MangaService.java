package fr.gtandu.service;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.exception.MangaNotFoundException;
import fr.gtandu.media.dto.MangaDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MangaService {

    /**
     * Creates a new Manga in the system.
     *
     * @param mangaDto The Manga data transfer object containing the details of the Manga to be created.
     * @return The created Manga data transfer object.
     * @throws MangaAlreadyExistException if a Manga with the same details already exists in the system.
     */
    MangaDto createManga(MangaDto mangaDto) throws MangaAlreadyExistException;

    /**
     * Updates an existing Manga in the system.
     *
     * @param mangaDto The Manga data transfer object containing the updated details of the Manga.
     * @return The updated Manga data transfer object.
     * @throws MangaNotFoundException if the Manga to be updated does not exist in the system.
     */
    MangaDto updateManga(MangaDto mangaDto) throws MangaNotFoundException;

    /**
     * Deletes a Manga from the system by its ID.
     *
     * @param mangaId The ID of the Manga to be deleted.
     * @return true if the Manga was successfully deleted, false otherwise.
     */
    boolean deleteMangaById(Long mangaId);

    /**
     * Searches for Mangas in the system by their name.
     *
     * @param searchKey The search key to be used in the search.
     * @param pageable  The pagination information.
     * @return A list of Manga data transfer objects that match the search key.
     */
    List<MangaDto> searchByName(String searchKey, Pageable pageable);

    /**
     * Checks if a Manga exists in the system by its ID.
     *
     * @param id The ID of the Manga.
     * @return true if the Manga exists, false otherwise.
     */
    boolean existsById(Long id);
}
