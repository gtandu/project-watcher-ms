package fr.gtandu.repository;

import fr.gtandu.shared.core.media.entity.ReadingMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReadingMediaRepository extends JpaRepository<ReadingMediaEntity, Long> {

    @Query(value = "SELECT readingMedia from ReadingMediaEntity readingMedia where readingMedia.user.id = ?1")
    Optional<List<ReadingMediaEntity>> findAllByUserId(String userId);
}