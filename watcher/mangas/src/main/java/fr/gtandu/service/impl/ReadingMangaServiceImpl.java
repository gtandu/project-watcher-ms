package fr.gtandu.service.impl;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.keycloak.entity.UserKeycloakEntity;
import fr.gtandu.keycloak.mapper.UserMapper;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.media.dto.ReadingMangaDto;
import fr.gtandu.media.entity.ReadingMangaEntity;
import fr.gtandu.media.mapper.ReadingMangaMapper;
import fr.gtandu.repository.ReadingMangaRepository;
import fr.gtandu.service.MangaService;
import fr.gtandu.service.ReadingMangaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static fr.gtandu.common.constant.AppConstant.PAGE_SIZE_LIMIT;

/**
 * This class provides the implementation for the ReadingMangaService interface.
 * It provides methods to manage the reading list of mangas for a user.
 */
@Service
@Slf4j
public class ReadingMangaServiceImpl implements ReadingMangaService {
    private final ReadingMangaRepository readingMangaRepository;
    private final ReadingMangaMapper readingMangaMapper;
    private final UserMapper userMapper;
    private final MangaService mangaService;

    public ReadingMangaServiceImpl(ReadingMangaRepository readingMangaRepository, ReadingMangaMapper readingMangaMapper, UserMapper userMapper, MangaService mangaService) {
        this.readingMangaRepository = readingMangaRepository;
        this.readingMangaMapper = readingMangaMapper;
        this.userMapper = userMapper;
        this.mangaService = mangaService;
    }

    /**
     * Fetches all the mangas that a user is currently reading.
     * <p>
     * This method fetches all the mangas that a user is currently reading. It uses the user's ID and pagination information
     * to retrieve the data. If the page size exceeds the limit, it throws an IllegalArgumentException.
     *
     * @param userId   The ID of the user. This is a String representing the unique identifier of the user.
     * @param pageable The pagination information. This is a Pageable object containing the details of the page number and size.
     * @return A Slice of ReadingMangaDto objects representing the mangas the user is reading. Each ReadingMangaDto object contains
     * the details of a manga that the user is currently reading.
     * @throws IllegalArgumentException if the page size exceeds the limit.
     */
    @Override
    public Slice<ReadingMangaDto> getAllReadingMangasByUserId(String userId, Pageable pageable) {
        log.info("Fetching all reading mangas for user with ID: {}", userId);
        if (pageable.getPageSize() > PAGE_SIZE_LIMIT) {
            log.error("Page size exceeds the limit");
            throw new IllegalArgumentException("Page size exceeds the limit");
        }
        return readingMangaRepository.findByUserId(userId, pageable).map(readingMangaMapper::toDto);
    }

    /**
     * Saves a ReadingMangaEntity object to the database.
     *
     * @param readingMangaEntity The ReadingMangaEntity object to be saved.
     * @return The saved ReadingMangaDto object.
     */
    @Override
    public ReadingMangaDto save(ReadingMangaEntity readingMangaEntity) {
        return readingMangaMapper.toDto(readingMangaRepository.save(readingMangaEntity));
    }

    /**
     * Adds a manga to a user's reading list.
     * If the manga does not exist, it is created.
     *
     * @param userDto         The UserDto object representing the user.
     * @param readingMangaDto The ReadingMangaDto object representing the manga to be added.
     * @return The saved ReadingMangaDto object.
     */
    @Override
    public ReadingMangaDto addMangaToReadingList(UserDto userDto, ReadingMangaDto readingMangaDto) {
        createMangaIfNotExist(readingMangaDto);

        ReadingMangaEntity readingMangaEntity = readingMangaMapper.toEntity(readingMangaDto);
        UserKeycloakEntity userKeycloakEntity = userMapper.toEntity(userDto);

        if (userKeycloakEntity.getReadingMangaList() == null) {
            userKeycloakEntity.setReadingMangaList(new ArrayList<>());
        }

        userKeycloakEntity.getReadingMangaList().add(readingMangaEntity);
        readingMangaEntity.setUser(userKeycloakEntity);

        return save(readingMangaEntity);
    }

    /**
     * Creates a manga if it does not already exist.
     *
     * @param readingMangaDto The ReadingMangaDto object representing the manga to be created.
     */
    private void createMangaIfNotExist(ReadingMangaDto readingMangaDto) {
        try {
            MangaDto mangaDto = mangaService.createManga(readingMangaDto.getManga());
            readingMangaDto.setManga(mangaDto);
        } catch (MangaAlreadyExistException exception) {
            log.warn("Manga with ID {} already exists", readingMangaDto.getManga().getId());
        }
    }
}
