package fr.gtandu.service;

import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.media.dto.ReadingMangaDto;
import fr.gtandu.media.entity.ReadingMangaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReadingMangaService {

    /**
     * Adds a Manga to the user's reading list.
     *
     * @param userDto         The User data transfer object containing the details of the user.
     * @param readingMangaDto The Reading Manga data transfer object containing the details of the Manga to be added to the user's reading list.
     * @return The Reading Manga data transfer object of the Manga that was added to the user's reading list.
     */
    ReadingMangaDto addMangaToReadingList(UserDto userDto, ReadingMangaDto readingMangaDto);

    /**
     * Fetches all the Mangas in the user's reading list.
     *
     * @param userId   The Keycloak user id of the user.
     * @param pageable The pagination information.
     * @return A Slice of Reading Manga data transfer objects that are in the user's reading list.
     * @throws IllegalArgumentException if the userId is null or empty.
     */
    Slice<ReadingMangaDto> getAllReadingMangasByUserId(String userId, Pageable pageable) throws IllegalArgumentException;

    /**
     * Saves a Reading Manga entity in the system.
     *
     * @param readingMangaEntity The Reading Manga entity to be saved.
     * @return The Reading Manga data transfer object of the saved Reading Manga entity.
     */
    ReadingMangaDto save(ReadingMangaEntity readingMangaEntity);
}
