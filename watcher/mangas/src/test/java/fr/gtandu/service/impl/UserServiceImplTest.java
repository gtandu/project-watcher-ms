package fr.gtandu.service.impl;

import fr.gtandu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    private static final String USER_ID = "87238780-05bc-4073-a75e-ede64e0232f0";

    @Test
    void existsByIdShouldReturnFalseWhenUserIdIsUnknown() {
        // GIVEN
        when(userService.existsById("UNKNOWN")).thenReturn(false);

        // WHEN
        boolean existsById = userService.existsById("UNKNOWN");

        // THEN
        assertThat(existsById).isFalse();
    }

    @Test
    void existsByIdShouldReturnTrueWhenUserIdExist() {
        // GIVEN
        when(userService.existsById(USER_ID)).thenReturn(true);

        // WHEN
        boolean existsById = userService.existsById(USER_ID);

        // THEN
        assertThat(existsById).isTrue();
    }
}
