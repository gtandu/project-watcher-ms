package fr.gtandu.controller;

import fr.gtandu.service.MangaService;
import fr.gtandu.shared.core.dto.MangaDto;
import fr.gtandu.shared.core.dto.ReadingMediaDto;
import fr.gtandu.shared.core.dto.ReadingMediaDtoWithJwt;
import fr.gtandu.shared.core.service.MangaApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@RefreshScope
@Slf4j
@RequestMapping("/api/v1/mangas")
public class MangaController implements MangaApi {

    private final MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @Override
    public ResponseEntity<Mono<MangaDto>> getMangaById(String mangaId) {
        return ResponseEntity.ok(mangaService.getMangaById(mangaId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<MangaDto>> getAll(@AuthenticationPrincipal Jwt principal) {
        return ResponseEntity.ok(mangaService.getAll());
    }

    @Override
    public ResponseEntity<Mono<MangaDto>> createManga(MangaDto mangaDto) {
        return ResponseEntity.ok(mangaService.createManga(mangaDto));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Void>> addMediaToReadingList(@AuthenticationPrincipal Jwt principal, @RequestBody ReadingMediaDto readingMediaDto) {
        if (StringUtils.isEmpty(readingMediaDto.getMediaDocument().getId())) {
            return ResponseEntity.ok(mangaService
                    .createManga((MangaDto) readingMediaDto.getMediaDocument())
                    .and(userServiceClient.addMediaToReadingList(new ReadingMediaDtoWithJwt(principal, readingMediaDto))));
        } else {
            return ResponseEntity.ok(userServiceClient.addMediaToReadingList(new ReadingMediaDtoWithJwt(principal, readingMediaDto)).then());
        }

    }

    @Override
    public ResponseEntity<Mono<MangaDto>> updateManga(String mangaId, MangaDto mangaDto) {
        return ResponseEntity.ok(mangaService.updateManga(mangaDto));
    }

    @Override
    public ResponseEntity<Mono<Void>> deleteMangaById(String mangaId) {
        return ResponseEntity.ok(mangaService.deleteMangaById(mangaId));
    }
}
