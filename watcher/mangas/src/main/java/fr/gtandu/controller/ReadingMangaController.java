package fr.gtandu.controller;

import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.keycloak.utils.JwtMapper;
import fr.gtandu.media.dto.ReadingMangaDto;
import fr.gtandu.service.ReadingMangaService;
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
@RequestMapping("${watcher-api.readingManga.baseUrl}")
public class ReadingMangaController {
    private final ReadingMangaService readingMangaService;
    private final JwtMapper jwtMapper;

    @GetMapping
    public ResponseEntity<List<ReadingMangaDto>> getAllReadingMangasByUserId(@AuthenticationPrincipal Jwt principal) {
        UserDto userDto = jwtMapper.toUserDto(principal);
        return ResponseEntity.ok(readingMangaService.getAllReadingMangasByUserId(userDto.getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadingMangaDto> addMangaToReadingList(@AuthenticationPrincipal Jwt principal, @RequestBody ReadingMangaDto readingMangaDto) {
        UserDto userDto = jwtMapper.toUserDto(principal);
        return ResponseEntity.ok(readingMangaService.addMangaToReadingList(userDto, readingMangaDto));

    }
}
