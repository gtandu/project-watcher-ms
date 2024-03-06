package fr.gtandu.service.impl;

import fr.gtandu.client.mangas.MangaServiceClient;
import fr.gtandu.exception.UserNotFoundException;
import fr.gtandu.repository.ReadingMediaRepository;
import fr.gtandu.service.ReadingMediaService;
import fr.gtandu.service.UserService;
import fr.gtandu.shared.core.keycloak.dto.UserDto;
import fr.gtandu.shared.core.keycloak.entity.UserKeycloakEntity;
import fr.gtandu.shared.core.keycloak.mapper.UserMapper;
import fr.gtandu.shared.core.media.dto.MangaDto;
import fr.gtandu.shared.core.media.dto.ReadingMediaDto;
import fr.gtandu.shared.core.media.entity.ReadingMediaEntity;
import fr.gtandu.shared.core.media.mapper.ReadingMediaMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReadingMediaServiceImpl implements ReadingMediaService {
    private final ReadingMediaRepository readingMediaRepository;
    private final ReadingMediaMapper readingMediaMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final MangaServiceClient mangaServiceClient;

    public ReadingMediaServiceImpl(ReadingMediaRepository readingMediaRepository, ReadingMediaMapper readingMediaMapper, UserMapper userMapper, UserService userService, MangaServiceClient mangaServiceClient) {
        this.readingMediaRepository = readingMediaRepository;
        this.readingMediaMapper = readingMediaMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.mangaServiceClient = mangaServiceClient;
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
    public ReadingMediaDto addMediaToReadingList(UserDto userDto, ReadingMediaDto readingMediaDto) throws UserNotFoundException {
        createMangaIfNotExist(readingMediaDto);

        if (this.userService.existsById(userDto.getId())) {

            ReadingMediaEntity readingMediaEntity = readingMediaMapper.toEntity(readingMediaDto);
            UserKeycloakEntity userKeycloakEntity = userMapper.toEntity(userDto);

            if (userKeycloakEntity.getReadingMediaList() == null) {
                userKeycloakEntity.setReadingMediaList(new ArrayList<>());
            }

            userKeycloakEntity.getReadingMediaList().add(readingMediaEntity);
            readingMediaEntity.setUser(userKeycloakEntity);

            return save(readingMediaEntity);
        } else {
            throw new UserNotFoundException();
        }
    }

    private void createMangaIfNotExist(ReadingMediaDto readingMediaDto) {
        if (null == readingMediaDto.getMedia().getId() || !mangaServiceClient.existsById(readingMediaDto.getMedia().getId())) {
            MangaDto mangaDto = mangaServiceClient.createManga((MangaDto) readingMediaDto.getMedia());
            readingMediaDto.setMedia(mangaDto);
        }
    }
}
