package fr.gtandu.service.impl;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.keycloak.dto.UserDto;
import fr.gtandu.keycloak.entity.UserKeycloakEntity;
import fr.gtandu.keycloak.mapper.UserMapper;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.media.dto.ReadingMediaDto;
import fr.gtandu.media.entity.ReadingMediaEntity;
import fr.gtandu.media.mapper.ReadingMediaMapper;
import fr.gtandu.repository.ReadingMediaRepository;
import fr.gtandu.service.MangaService;
import fr.gtandu.service.ReadingMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ReadingMediaServiceImpl implements ReadingMediaService {
    private final ReadingMediaRepository readingMediaRepository;
    private final ReadingMediaMapper readingMediaMapper;
    private final UserMapper userMapper;
    private final MangaService mangaService;

    public ReadingMediaServiceImpl(ReadingMediaRepository readingMediaRepository, ReadingMediaMapper readingMediaMapper, UserMapper userMapper, MangaService mangaService) {
        this.readingMediaRepository = readingMediaRepository;
        this.readingMediaMapper = readingMediaMapper;
        this.userMapper = userMapper;
        this.mangaService = mangaService;
    }

    @Override
    public List<ReadingMediaDto> getAllReadingMediasByUserId(String userId) {
        return readingMediaMapper.toDtoList(readingMediaRepository.findAllByUserId(userId).orElse(Collections.emptyList()));
    }

    @Override
    public ReadingMediaDto save(ReadingMediaEntity readingMediaEntity) {
        return readingMediaMapper.toDto(readingMediaRepository.save(readingMediaEntity));
    }

    @Override
    public ReadingMediaDto addMediaToReadingList(UserDto userDto, ReadingMediaDto readingMediaDto) {
        createMangaIfNotExist(readingMediaDto);

        ReadingMediaEntity readingMediaEntity = readingMediaMapper.toEntity(readingMediaDto);
        UserKeycloakEntity userKeycloakEntity = userMapper.toEntity(userDto);

        if (userKeycloakEntity.getReadingMediaList() == null) {
            userKeycloakEntity.setReadingMediaList(new ArrayList<>());
        }

        userKeycloakEntity.getReadingMediaList().add(readingMediaEntity);
        readingMediaEntity.setUser(userKeycloakEntity);

        return save(readingMediaEntity);

    }

    private void createMangaIfNotExist(ReadingMediaDto readingMediaDto) {
        try {
            MangaDto mangaDto = mangaService.createManga((MangaDto) readingMediaDto.getMedia());
            readingMediaDto.setMedia(mangaDto);
        } catch (MangaAlreadyExistException exception) {
            log.info(exception.getMessage());
        }
    }
}
