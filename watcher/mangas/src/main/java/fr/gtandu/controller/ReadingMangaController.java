package fr.gtandu.controller;

import fr.gtandu.keycloak.utils.JwtMapper;
import fr.gtandu.media.dto.ReadingMangaDto;
import fr.gtandu.service.ReadingMangaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The ReadingMangaController class is a REST controller that handles HTTP requests related to reading mangas.
 * It uses Spring's @RestController annotation, which means it's a Controller that includes @ResponseBody semantics by default.
 * The @Slf4j annotation is a Lombok annotation to create a Slf4j-based LoggerFactory as a log, allowing us to print log messages.
 * The @RefreshScope annotation is a Spring Cloud annotation that allows the application to refresh its configuration properties at runtime.
 * The @RequiredArgsConstructor annotation is a Lombok annotation that generates a constructor with required fields.
 * The @RequestMapping annotation maps HTTP requests to handler methods of MVC and REST controllers.
 */
@RestController
@Slf4j
@RefreshScope
@RequiredArgsConstructor
@Validated
@RequestMapping("${watcher-api.readingManga.baseUrl}")
public class ReadingMangaController {
    private final ReadingMangaService readingMangaService;
    private final JwtMapper jwtMapper;

    @GetMapping
    @PreAuthorize("hasRole(T(fr.gtandu.common.enums.Role).ROLE_USER.getName())")
    public ResponseEntity<Slice<ReadingMangaDto>> getAllReadingMangasByUserId(@AuthenticationPrincipal Jwt principal, Pageable pageable) {
        try {
            Slice<ReadingMangaDto> readingMangas = readingMangaService.getAllReadingMangasByUserId(jwtMapper.toUserDto(principal).getId(), pageable);
            return ResponseEntity.ok(readingMangas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    @PreAuthorize("hasRole(T(fr.gtandu.common.enums.Role).ROLE_USER.getName())")
    public ResponseEntity<ReadingMangaDto> addMangaToReadingList(@AuthenticationPrincipal Jwt principal, @RequestBody @Valid ReadingMangaDto readingMangaDto) {
        ReadingMangaDto readingManga = readingMangaService.addMangaToReadingList(jwtMapper.toUserDto(principal), readingMangaDto);
        return ResponseEntity.ok(readingManga);
    }
}
