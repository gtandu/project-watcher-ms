package fr.gtandu.service.impl;

import fr.gtandu.repository.UserRepository;
import fr.gtandu.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }
}
