package fr.gtandu.repository;

import fr.gtandu.document.Manga;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MangaRepository extends ReactiveMongoRepository<Manga, String> {
}
