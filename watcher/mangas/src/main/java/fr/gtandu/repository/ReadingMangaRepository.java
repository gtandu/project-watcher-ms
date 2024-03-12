package fr.gtandu.repository;

import fr.gtandu.media.entity.ReadingMangaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReadingMangaRepository extends JpaRepository<ReadingMangaEntity, Long> {

    @Query(value = "SELECT readingManga from ReadingMangaEntity readingManga where readingManga.user.id = ?1")
    Optional<List<ReadingMangaEntity>> findAllByUserId(String userId);
}
