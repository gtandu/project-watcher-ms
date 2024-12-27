package fr.gtandu.service.impl;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.exception.MangaNotFoundException;
import fr.gtandu.mangadex.service.MangaDexService;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.media.dto.MediaDto;
import fr.gtandu.media.mapper.MangaMapper;
import fr.gtandu.repository.MangaRepository;
import fr.gtandu.service.MangaService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static fr.gtandu.common.constant.AppConstant.SEARCH_MANGAS_BY_NAME_PAGE_SIZE_LIMIT;

/**
 * MangaServiceImpl is a service class that implements the MangaService interface.
 * It provides methods for creating, updating, deleting, and searching for Manga.
 * It uses MangaRepository for database operations, MangaMapper for converting entities to DTOs and vice versa,
 * and MangaDexService for additional operations related to Manga.
 */
@Service
@Transactional
@Log4j2
public class MangaServiceImpl implements MangaService {

    private final MangaRepository mangaRepository;
    private final MangaMapper mangaMapper;
    private final MangaDexService mangaDexService;

    /**
     * Constructor for MangaServiceImpl.
     *
     * @param mangaRepository the MangaRepository to be used for database operations.
     * @param mangaMapper     the MangaMapper to be used for converting entities to DTOs and vice versa.
     * @param mangaDexService the MangaDexService to be used for additional operations related to Manga.
     */
    public MangaServiceImpl(MangaRepository mangaRepository, MangaMapper mangaMapper, MangaDexService mangaDexService) {
        this.mangaRepository = mangaRepository;
        this.mangaMapper = mangaMapper;
        this.mangaDexService = mangaDexService;
    }

    /**
     * Creates a new Manga.
     *
     * @param mangaDto the MangaDto containing the details of the Manga to be created.
     * @return the created MangaDto.
     * @throws MangaAlreadyExistException if the Manga already exists.
     */
    @Override
    public MangaDto createManga(@NonNull MangaDto mangaDto) throws MangaAlreadyExistException {
        if (null != mangaDto.getId() && existsById(mangaDto.getId())) {
            log.warn("Manga with ID {} already exists", mangaDto.getId());
            throw new MangaAlreadyExistException();
        } else {
            return mangaMapper.toDto(mangaRepository.save(mangaMapper.toEntity(mangaDto)));
        }
    }

    /**
     * Updates an existing Manga.
     *
     * @param mangaDto the MangaDto containing the updated details of the Manga.
     * @return the updated MangaDto.
     * @throws MangaNotFoundException if the Manga does not exist.
     */
    @Override
    public MangaDto updateManga(@NonNull MangaDto mangaDto) throws MangaNotFoundException {
        if (null != mangaDto.getId() && existsById(mangaDto.getId())) {
            return mangaMapper.toDto(mangaRepository.save(mangaMapper.toEntity(mangaDto)));
        }
        log.warn("Manga with ID {} not found", mangaDto.getId());
        throw new MangaNotFoundException();
    }

    /**
     * Deletes a Manga by its ID.
     *
     * @param mangaId the ID of the Manga to be deleted.
     * @return true if the Manga was deleted, false otherwise.
     */
    @Override
    public boolean deleteMangaById(@NonNull Long mangaId) {
        // Verifier lq suppression en cascade ou sinon juste desactiver
        return mangaRepository.deleteByMangaId(mangaId) == 1;
    }

    /**
     * Searches for Manga by name.
     *
     * @param searchKey the search key to be used for searching. It should be a string containing the name or part of the name of the Manga.
     * @param pageable  an object of type Pageable that specifies the amount of data to be fetched from the database. It contains information about the size of the page and the number of the page to be fetched.
     * @return a list of MangaDto that match the search key. The list is sorted by the name of the Manga in ascending order. If the search key is less than 3 characters long, an empty list is returned. If the page size exceeds the limit, an IllegalArgumentException is thrown.
     * @throws IllegalArgumentException if the page size exceeds the limit.
     */
    @Override
    public List<MangaDto> searchByName(String searchKey, Pageable pageable) {
        if (pageable.getPageSize() > SEARCH_MANGAS_BY_NAME_PAGE_SIZE_LIMIT) {
            log.warn("Page size exceeds the limit");
            throw new IllegalArgumentException("Page size exceeds the limit. Current limit : ".concat(String.valueOf(SEARCH_MANGAS_BY_NAME_PAGE_SIZE_LIMIT)).concat(" ").concat("Request size : ").concat(String.valueOf(pageable.getPageSize())));
        }

        if (StringUtils.length(searchKey) < 3) {
            log.warn("Search key is less than 3 characters");
            return Collections.emptyList();
        }

        List<MangaDto> mangaDtoList = mangaMapper.toDtoList(mangaRepository.findByNameStartingWith(searchKey, pageable));
        if (mangaDtoList.size() < SEARCH_MANGAS_BY_NAME_PAGE_SIZE_LIMIT) {
            mangaDtoList.addAll(mangaDexService.searchMangaByTitle(searchKey, pageable.getPageSize()));
        }

        return mangaDtoList.stream()
                .filter(distinctByKey(MediaDto::getName))
                .sorted(Comparator.comparing(MediaDto::getName))
                .toList();
    }

    /**
     * Checks if a Manga exists by its ID.
     *
     * @param id the ID of the Manga to be checked.
     * @return true if the Manga exists, false otherwise.
     */
    @Override
    public boolean existsById(@NonNull Long id) {
        return mangaRepository.existsById(id);
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
