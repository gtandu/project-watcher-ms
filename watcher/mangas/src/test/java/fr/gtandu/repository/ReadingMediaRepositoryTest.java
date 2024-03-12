package fr.gtandu.repository;

import fr.gtandu.keycloak.entity.UserKeycloakEntity;
import fr.gtandu.media.entity.ReadingMediaEntity;
import fr.gtandu.utils.ReadingMediaMockUtils;
import fr.gtandu.utils.UserMockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@DataJpaTest
@ActiveProfiles("noKeycloak")
class ReadingMediaRepositoryTest {

    @Autowired
    ReadingMediaRepository readingMediaRepository;

    @Autowired
    UserRepository userRepository;

    private UserKeycloakEntity userKeycloakEntity;
    private ReadingMediaEntity readingMediaEntitySaved1;
    private ReadingMediaEntity readingMediaEntitySaved2;
    private ReadingMediaEntity readingMediaEntitySaved3;

    @BeforeEach
    void initData() {
        userKeycloakEntity = userRepository.save(UserMockUtils.createMockUser());

        ReadingMediaEntity readingMediaEntity1 = ReadingMediaMockUtils.createMock(null, 1L);
        readingMediaEntity1.setUser(userKeycloakEntity);

        ReadingMediaEntity readingMediaEntity2 = ReadingMediaMockUtils.createMock(null, 2L);
        readingMediaEntity2.setUser(userKeycloakEntity);

        ReadingMediaEntity readingMediaEntity3 = ReadingMediaMockUtils.createMock(null, 3L);
        readingMediaEntity3.setUser(userKeycloakEntity);

        readingMediaEntitySaved1 = readingMediaRepository.save(readingMediaEntity1);
        readingMediaEntitySaved2 = readingMediaRepository.save(readingMediaEntity2);
        readingMediaEntitySaved3 = readingMediaRepository.save(readingMediaEntity3);
    }

    @Test
    void findAllByUserIdShouldReturnReadingMediaList() {
        // GIVEN
        // WHEN
        List<ReadingMediaEntity> allByUserId = readingMediaRepository.findAllByUserId(userKeycloakEntity.getId()).get();

        // THEN

        assertThat(allByUserId).isNotEmpty();
        assertThat(allByUserId).extracting("id", "user.id").containsExactly(
                tuple(readingMediaEntitySaved1.getId(), userKeycloakEntity.getId()),
                tuple(readingMediaEntitySaved2.getId(), userKeycloakEntity.getId()),
                tuple(readingMediaEntitySaved3.getId(), userKeycloakEntity.getId())
        );
    }

    @Test
    void findAllByUserIdShouldReturnEmptyListWhenUserIdIsNull() {
        // GIVEN
        // WHEN
        List<ReadingMediaEntity> allByUserId = readingMediaRepository.findAllByUserId(null).get();

        // THEN
        assertThat(allByUserId).isEmpty();
    }

    @Test
    void findAllByUserIdShouldReturnEmptyListWhenUserIdIsUnknown() {
        // GIVEN
        String unknownId = "unknown";
        // WHEN
        List<ReadingMediaEntity> allByUserId = readingMediaRepository.findAllByUserId(unknownId).get();

        // THEN
        assertThat(allByUserId).isEmpty();
    }
}
