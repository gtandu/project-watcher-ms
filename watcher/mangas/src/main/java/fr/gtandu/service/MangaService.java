package fr.gtandu.service;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.exception.MangaNotFoundException;
import fr.gtandu.media.dto.MangaDto;

import java.util.List;

public interface MangaService {
    /**
     * @param mangaDto
     * @return
     */
    MangaDto createManga(MangaDto mangaDto) throws MangaAlreadyExistException;

    /**
     * @param mangaDto
     * @return
     */
    MangaDto updateManga(MangaDto mangaDto) throws MangaNotFoundException;

    /**
     * @param mangaId
     * @return
     */
    boolean deleteMangaById(Long mangaId);

    List<MangaDto> searchByName(String searchKey);

    boolean existsById(Long id);

}
