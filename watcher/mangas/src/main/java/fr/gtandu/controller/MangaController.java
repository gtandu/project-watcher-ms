package fr.gtandu.controller;

import fr.gtandu.service.MangaService;
import fr.gtandu.shared.core.media.dto.MangaDto;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RefreshScope
@Slf4j
@RequestMapping("/api/v1/mangas")
public class MangaController {

    private final MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }


    @GetMapping("/{searchKey}")
    public ResponseEntity<List<MangaDto>> searchByName(@PathVariable @NonNull @Min(3) String searchKey) {
        return ResponseEntity.ok(mangaService.searchByName(searchKey));
    }

    @PutMapping
    public ResponseEntity<MangaDto> createManga(@RequestBody MangaDto mangaDto) {
        return ResponseEntity.ok(mangaService.createManga(mangaDto));
    }

    @DeleteMapping(value = "/{mangaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteMangaById(@PathVariable Long mangaId) {
        if (mangaService.deleteMangaById(mangaId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/existsById/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(mangaService.existsById(id));
    }
}
