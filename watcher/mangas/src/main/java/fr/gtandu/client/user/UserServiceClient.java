package fr.gtandu.client.user;


import fr.gtandu.shared.core.dto.ReadingMediaDtoWithJwt;
import fr.gtandu.shared.core.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "GATEWAY", path = "api/v1/users")
public interface UserServiceClient {

    @GetMapping(value = "${user-service.urls.get-user-by-id-url}")
    Mono<UserDto> getUserById(@PathVariable("userId") String userId);

    @PutMapping(value = "${user-service.urls.add-media-to-reading-list-url}", headers = {"Content-Type=application/json"})
    Mono<UserDto> addMediaToReadingList(@RequestBody ReadingMediaDtoWithJwt readingMediaDtoWithJwt);
}
