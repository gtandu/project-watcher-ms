package fr.gtandu.service;

import fr.gtandu.shared.core.dto.MangaDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MangaService {
    /**
     * @param mangaId
     * @return
     */
    Mono<MangaDto> getMangaById(String mangaId);

    /**
     * @return
     */
    Flux<MangaDto> getAll();

    /**
     * @param mangaDto
     * @return
     */
    Mono<MangaDto> createManga(@RequestBody MangaDto mangaDto);

    /**
     * @param mangaDto
     * @return
     */
    Mono<MangaDto> updateManga(@RequestBody MangaDto mangaDto);

    /**
     * @param mangaId
     * @return
     */
    Mono<Void> deleteMangaById(@PathVariable String mangaId);
}
