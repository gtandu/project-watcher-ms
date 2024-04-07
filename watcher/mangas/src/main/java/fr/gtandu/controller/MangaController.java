package fr.gtandu.controller;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.service.MangaService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * MangaController is a REST controller that handles HTTP requests related to Manga.
 * It uses MangaService to perform operations on Manga.
 */
@RestController
@RefreshScope
@Slf4j
@RequestMapping("${watcher-api.manga.baseUrl}")
@Validated
public class MangaController {

    private final MangaService mangaService;


    /**
     * Constructor for MangaController.
     *
     * @param mangaService the MangaService to be used by this controller.
     */
    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles GET requests to search for Manga by name.
     *
     * @param searchKey the name to search for.
     * @return a ResponseEntity containing a list of MangaDto that match the search key.
     */
    @GetMapping("${watcher-api.manga.searchByName}")
    @PreAuthorize("hasRole(T(fr.gtandu.common.enums.Role).ROLE_USER.getName())")
    public ResponseEntity<List<MangaDto>> searchByName(@PathVariable("searchKey") @NotNull @Size(min = 3) String searchKey, Pageable pageable) {
        return ResponseEntity.ok(mangaService.searchByName(searchKey, pageable));
    }

    /**
     * Handles PUT requests to create a new Manga.
     *
     * @param mangaDto the MangaDto containing the data of the Manga to be created.
     * @return a ResponseEntity containing the created MangaDto, or a conflict status if the Manga already exists.
     */
    @PutMapping
    @PreAuthorize("hasRole(T(fr.gtandu.common.enums.Role).ROLE_ADMIN.getName())")
    public ResponseEntity<MangaDto> createManga(@RequestBody @Valid MangaDto mangaDto) {
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

    /**
     * Handles DELETE requests to delete a Manga by ID.
     *
     * @param mangaId the ID of the Manga to be deleted.
     * @return a ResponseEntity with no content if the Manga was deleted, or not found status if the Manga does not exist.
     */
    @DeleteMapping(value = "${watcher-api.manga.deleteMangaById}")
    @PreAuthorize("hasRole(T(fr.gtandu.common.enums.Role).ROLE_ADMIN.getName())")
    public ResponseEntity<Void> deleteMangaById(@PathVariable("mangaId") Long mangaId) {
        if (mangaService.deleteMangaById(mangaId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
