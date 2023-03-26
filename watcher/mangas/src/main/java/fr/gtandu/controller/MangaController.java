package fr.gtandu.controller;

import fr.gtandu.service.MangaService;
import fr.gtandu.shared.core.dto.MangaDto;
import fr.gtandu.shared.core.service.MangaApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@RefreshScope
@Slf4j
@RequestMapping("/mangas")
public class MangaController implements MangaApi {

    private final MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @Override
    public ResponseEntity<Mono<MangaDto>> getMangaById(String mangaId) {
        return ResponseEntity.ok(mangaService.getMangaById(mangaId));
    }

    @Override
    public ResponseEntity<Flux<MangaDto>> getAll() {
        return ResponseEntity.ok(mangaService.getAll());
    }

    @Override
    public ResponseEntity<Mono<MangaDto>> createManga(MangaDto mangaDto) {
        return ResponseEntity.ok(mangaService.createManga(mangaDto));
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
