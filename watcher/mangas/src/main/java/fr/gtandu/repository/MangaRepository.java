package fr.gtandu.repository;


import fr.gtandu.media.entity.MangaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MangaRepository extends JpaRepository<MangaEntity, Long> {

    List<MangaEntity> findByNameStartingWith(String searchKey, Pageable pageable);

    @Modifying
    @Query("DELETE from MangaEntity manga where manga.id = ?1")
    int deleteByMangaId(Long id);
}
