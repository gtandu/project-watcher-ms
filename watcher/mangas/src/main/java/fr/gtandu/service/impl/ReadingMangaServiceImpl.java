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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    public List<ReadingMangaDto> getAllReadingMangasByUserId(String userId) {
        return readingMangaMapper.toDtoList(readingMangaRepository.findAllByUserId(userId).orElse(Collections.emptyList()));
    }

    @Override
    public ReadingMangaDto save(ReadingMangaEntity readingMangaEntity) {
        return readingMangaMapper.toDto(readingMangaRepository.save(readingMangaEntity));
    }

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

    private void createMangaIfNotExist(ReadingMangaDto readingMangaDto) {
        try {
            MangaDto mangaDto = mangaService.createManga(readingMangaDto.getManga());
            readingMangaDto.setManga(mangaDto);
        } catch (MangaAlreadyExistException exception) {
            log.info(exception.getMessage());
        }
    }
}
