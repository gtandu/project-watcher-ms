package fr.gtandu.controller;

import fr.gtandu.service.UserService;
import fr.gtandu.shared.core.dto.ReadingMediaDtoWithJwt;
import fr.gtandu.shared.core.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RefreshScope
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Mono<UserDto>> getUserById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/readingMedia")
    public ResponseEntity<Mono<UserDto>> addMediaToReadingList(@RequestBody ReadingMediaDtoWithJwt readingMediaDtoWithJwt) {
        UserDto userDto = UserDto.builder()
                .keycloakId(readingMediaDtoWithJwt.principal().getSubject())
                .firstName(readingMediaDtoWithJwt.principal().getClaim("given_name"))
                .lastName(readingMediaDtoWithJwt.principal().getClaim("family_name"))
                .username(readingMediaDtoWithJwt.principal().getClaim("preferred_username"))
                .build();
        return ResponseEntity.ok(userService.addMediaToReadingList(userDto, readingMediaDtoWithJwt.readingMediaDto()));
    }
}
