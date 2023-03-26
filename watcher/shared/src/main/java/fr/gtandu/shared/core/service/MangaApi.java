package fr.gtandu.shared.core.service;

import fr.gtandu.shared.core.dto.MangaDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MangaApi {

    @GetMapping(value = "/{mangaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Mono<MangaDto>> getMangaById(@PathVariable String mangaId);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Flux<MangaDto>> getAll();

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Mono<MangaDto>> createManga(@RequestBody MangaDto mangaDto);

    @PutMapping(value = "/{mangaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Mono<MangaDto>> updateManga(@PathVariable String mangaId, @RequestBody MangaDto mangaDto);

    @DeleteMapping(value = "/{mangaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Mono<Void>> deleteMangaById(@PathVariable String mangaId);

}
