package fr.gtandu.repository;

import fr.gtandu.media.entity.ReadingMangaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingMangaRepository extends JpaRepository<ReadingMangaEntity, Long> {
    Slice<ReadingMangaEntity> findByUserId(String userId, Pageable pageable);
}
