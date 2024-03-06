package fr.gtandu.controller;

import fr.gtandu.exception.UserNotFoundException;
import fr.gtandu.service.ReadingMediaService;
import fr.gtandu.shared.core.keycloak.dto.UserDto;
import fr.gtandu.shared.core.keycloak.utils.JwtMapper;
import fr.gtandu.shared.core.media.dto.ReadingMediaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RefreshScope
@RequiredArgsConstructor
@RequestMapping("/api/v1/reading-medias")
public class ReadingMediaController {
    private final ReadingMediaService readingMediaService;
    private final JwtMapper jwtMapper;

    @GetMapping
    public List<ReadingMediaDto> getAllReadingMediaByUserId(Jwt principal) {
        UserDto userDto = jwtMapper.toUserDto(principal);
        return readingMediaService.getAllReadingMediasByUserId(userDto.getId());
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadingMediaDto> addMediaToReadingList(@AuthenticationPrincipal Jwt principal, @RequestBody ReadingMediaDto readingMediaDto) {
        UserDto userDto = jwtMapper.toUserDto(principal);
        try {
            return ResponseEntity.ok(readingMediaService.addMediaToReadingList(userDto, readingMediaDto));
        } catch (UserNotFoundException userNotFoundException) {
            return ResponseEntity.notFound().build();
        }
    }
}
