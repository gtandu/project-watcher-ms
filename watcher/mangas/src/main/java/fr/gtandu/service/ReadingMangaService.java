package fr.gtandu.service;

import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.media.dto.ReadingMangaDto;
import fr.gtandu.media.entity.ReadingMangaEntity;

import java.util.List;

public interface ReadingMangaService {

    /**
     * @param userDto         User data
     * @param readingMangaDto Reading Manga to add to user media list
     * @return User dto
     */
    ReadingMangaDto addMangaToReadingList(UserDto userDto, ReadingMangaDto readingMangaDto);

    /**
     * Fetch all reading manags link to user
     *
     * @param userId Keycloak user id
     * @return Reading mangas list
     */
    List<ReadingMangaDto> getAllReadingMangasByUserId(String userId);

    /**
     * Save reading manga entity
     *
     * @param readingMangaEntity Reading manga entity
     * @return Reading media dto saved
     */
    ReadingMangaDto save(ReadingMangaEntity readingMangaEntity);
}
