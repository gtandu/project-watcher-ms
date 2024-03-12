package fr.gtandu.service;

import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.media.dto.ReadingMediaDto;
import fr.gtandu.media.entity.ReadingMediaEntity;

import java.util.List;

public interface ReadingMediaService {

    /**
     * @param userDto         User data
     * @param readingMediaDto Reading Media to add to user media list
     * @return User dto
     */
    ReadingMediaDto addMediaToReadingList(UserDto userDto, ReadingMediaDto readingMediaDto);

    /**
     * Fetch all reading medias link to user
     *
     * @param userId Keycloak user id
     * @return Reading medias list
     */
    List<ReadingMediaDto> getAllReadingMediasByUserId(String userId);

    /**
     * Save reading media entity
     *
     * @param readingMediaEntity Reading media entity
     * @return Reading media dto saved
     */
    ReadingMediaDto save(ReadingMediaEntity readingMediaEntity);
}
