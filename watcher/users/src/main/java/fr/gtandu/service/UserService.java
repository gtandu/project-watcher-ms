package fr.gtandu.service;

import fr.gtandu.shared.core.dto.ReadingMediaDto;
import fr.gtandu.shared.core.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    /**
     * @param id Keycloak user id
     * @return User dto
     */
    Mono<UserDto> getUserById(String id);

    /**
     * @param userDto         User data
     * @param readingMediaDto Reading Media to add to user media list
     * @return User dto
     */
    Mono<UserDto> addMediaToReadingList(UserDto userDto, ReadingMediaDto readingMediaDto);
}
