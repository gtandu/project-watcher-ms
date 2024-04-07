package fr.gtandu.repository;

import fr.gtandu.keycloak.entity.UserKeycloakEntity;
import fr.gtandu.media.entity.ReadingMangaEntity;
import fr.gtandu.utils.ReadingMangaMockUtils;
import fr.gtandu.utils.UserMockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;

import static fr.gtandu.common.constant.AppConstant.PAGE_SIZE_LIMIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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
        Slice<ReadingMangaEntity> allByUserId = readingMangaRepository.findByUserId(userKeycloakEntity.getId(), null);

        // THEN

        assertThat(allByUserId).isNotNull();
        assertThat(allByUserId.getContent()).isNotEmpty().extracting("id", "user.id").containsExactly(
                tuple(readingMangaEntitySaved1.getId(), userKeycloakEntity.getId()),
                tuple(readingMangaEntitySaved2.getId(), userKeycloakEntity.getId()),
                tuple(readingMangaEntitySaved3.getId(), userKeycloakEntity.getId())
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"unknown"})
    void findAllByUserIdShouldReturnEmptyListWhenUserIdIsNullOrEmptyOrUnknown(String userId) {
        assertThat(readingMangaRepository.findByUserId(userId, null)).isEmpty();
    }

    @Test
    void findAllByUserIdShouldReturnReadingMangaListFirstPageWithThreeElements() {
        // GIVEN
        int expectedSize = 3;

        // WHEN
        Slice<ReadingMangaEntity> allByUserId = readingMangaRepository.findByUserId(userKeycloakEntity.getId(), PageRequest.of(0, PAGE_SIZE_LIMIT));

        // THEN

        assertThat(allByUserId).isNotNull();
        assertThat(allByUserId.getContent()).isNotEmpty().hasSize(expectedSize).extracting("id", "user.id").containsExactly(
                tuple(readingMangaEntitySaved1.getId(), userKeycloakEntity.getId()),
                tuple(readingMangaEntitySaved2.getId(), userKeycloakEntity.getId()),
                tuple(readingMangaEntitySaved3.getId(), userKeycloakEntity.getId()));
    }

    @Test
    void findAllByUserIdShouldReturnReadingMangaListFirstPageWithTwoElementsNEW() {
        // GIVEN
        int expectedSize = 2;

        // WHEN
        Slice<ReadingMangaEntity> allByUserId = readingMangaRepository.findByUserId(userKeycloakEntity.getId(), PageRequest.of(0, expectedSize));

        // THEN

        assertThat(allByUserId).isNotNull();
        assertThat(allByUserId.getContent()).isNotEmpty().hasSize(expectedSize).extracting("id", "user.id").containsExactly(
                tuple(readingMangaEntitySaved1.getId(), userKeycloakEntity.getId()),
                tuple(readingMangaEntitySaved2.getId(), userKeycloakEntity.getId()));
    }
}
