package fr.gtandu.controller;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.service.MangaService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RefreshScope
@Slf4j
@RequestMapping("${watcher-api.manga.baseUrl}")
public class MangaController {

    private final MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }


    @GetMapping("${watcher-api.manga.searchByName}")
    public ResponseEntity<List<MangaDto>> searchByName(@PathVariable @NonNull @Min(3) String searchKey) {
        return ResponseEntity.ok(mangaService.searchByName(searchKey));
    }

    @PutMapping
    public ResponseEntity<MangaDto> createManga(@RequestBody MangaDto mangaDto) {
        try {
            MangaDto createdManga = mangaService.createManga(mangaDto);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdManga.getId()).toUri();

            return ResponseEntity.created(location).body(createdManga);
        } catch (MangaAlreadyExistException e) {
            return new ResponseEntity<>(mangaDto, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "${watcher-api.manga.deleteMangaById}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteMangaById(@PathVariable Long mangaId) {
        if (mangaService.deleteMangaById(mangaId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
