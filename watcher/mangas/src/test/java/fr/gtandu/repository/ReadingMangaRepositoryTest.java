package fr.gtandu.repository;

import fr.gtandu.keycloak.entity.UserKeycloakEntity;
import fr.gtandu.media.entity.ReadingMangaEntity;
import fr.gtandu.utils.ReadingMangaMockUtils;
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
class ReadingMangaRepositoryTest {

    @Autowired
    ReadingMangaRepository readingMangaRepository;

    @Autowired
    UserRepository userRepository;

    private UserKeycloakEntity userKeycloakEntity;
    private ReadingMangaEntity readingMangaEntitySaved1;
    private ReadingMangaEntity readingMangaEntitySaved2;
    private ReadingMangaEntity readingMangaEntitySaved3;

    @BeforeEach
    void initData() {
        userKeycloakEntity = userRepository.save(UserMockUtils.createMockUser());

        ReadingMangaEntity readingMangaEntity1 = ReadingMangaMockUtils.createMock(null, 1L);
        readingMangaEntity1.setUser(userKeycloakEntity);

        ReadingMangaEntity readingMangaEntity2 = ReadingMangaMockUtils.createMock(null, 2L);
        readingMangaEntity2.setUser(userKeycloakEntity);

        ReadingMangaEntity readingMangaEntity3 = ReadingMangaMockUtils.createMock(null, 3L);
        readingMangaEntity3.setUser(userKeycloakEntity);

        readingMangaEntitySaved1 = readingMangaRepository.save(readingMangaEntity1);
        readingMangaEntitySaved2 = readingMangaRepository.save(readingMangaEntity2);
        readingMangaEntitySaved3 = readingMangaRepository.save(readingMangaEntity3);
    }

    @Test
    void findAllByUserIdShouldReturnReadingMangaList() {
        // GIVEN
        // WHEN
        List<ReadingMangaEntity> allByUserId = readingMangaRepository.findAllByUserId(userKeycloakEntity.getId()).get();

        // THEN

        assertThat(allByUserId).isNotEmpty();
        assertThat(allByUserId).extracting("id", "user.id").containsExactly(
                tuple(readingMangaEntitySaved1.getId(), userKeycloakEntity.getId()),
                tuple(readingMangaEntitySaved2.getId(), userKeycloakEntity.getId()),
                tuple(readingMangaEntitySaved3.getId(), userKeycloakEntity.getId())
        );
    }

    @Test
    void findAllByUserIdShouldReturnEmptyListWhenUserIdIsNull() {
        // GIVEN
        // WHEN
        List<ReadingMangaEntity> allByUserId = readingMangaRepository.findAllByUserId(null).get();

        // THEN
        assertThat(allByUserId).isEmpty();
    }

    @Test
    void findAllByUserIdShouldReturnEmptyListWhenUserIdIsUnknown() {
        // GIVEN
        String unknownId = "unknown";
        // WHEN
        List<ReadingMangaEntity> allByUserId = readingMangaRepository.findAllByUserId(unknownId).get();

        // THEN
        assertThat(allByUserId).isEmpty();
    }
}
