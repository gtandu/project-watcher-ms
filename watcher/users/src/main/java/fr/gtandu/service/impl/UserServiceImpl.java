package fr.gtandu.service.impl;

import fr.gtandu.repository.UserRepository;
import fr.gtandu.service.UserService;
import fr.gtandu.shared.core.document.UserDocument;
import fr.gtandu.shared.core.dto.ReadingMediaDto;
import fr.gtandu.shared.core.dto.UserDto;
import fr.gtandu.shared.core.mapper.ReadingMediaMapper;
import fr.gtandu.shared.core.mapper.UserMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ReadingMediaMapper readingMediaMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, ReadingMediaMapper readingMediaMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.readingMediaMapper = readingMediaMapper;
    }

    @Override
    public Mono<UserDto> getUserById(String id) {
        return this.userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public Mono<UserDto> addMediaToReadingList(UserDto userDto, ReadingMediaDto readingMediaDto) {
        return this.userRepository.findById(userDto.getId())
                .switchIfEmpty(this.userRepository.save(userMapper.toDocument(userDto)))
                .map(userDocument -> addReadingMediaToUserDocument(readingMediaDto, userDocument))
                .flatMap(userRepository::save)
                .map(userMapper::toDto);
    }

    private UserDocument addReadingMediaToUserDocument(ReadingMediaDto readingMediaDto, UserDocument userDocument) {
        userDocument.getReadingMediaList().add(readingMediaMapper.toDocument(readingMediaDto));
        return userDocument;
    }
}
