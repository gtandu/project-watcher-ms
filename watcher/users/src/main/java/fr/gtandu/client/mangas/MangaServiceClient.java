package fr.gtandu.client.mangas;

import fr.gtandu.shared.core.media.dto.MangaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "MANGAS-SERVICE", path = "api/v1/mangas")
public interface MangaServiceClient {
    @PutMapping
    MangaDto createManga(@RequestBody MangaDto mangaDto);

    @DeleteMapping(value = "/{mangaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Void deleteMangaById(@PathVariable Long mangaId);

    @GetMapping(value = "/existsById/{id}")
    Boolean existsById(@PathVariable Long id);

}
