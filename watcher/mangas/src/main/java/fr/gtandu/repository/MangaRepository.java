package fr.gtandu.repository;

import fr.gtandu.shared.core.document.MangaDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MangaRepository extends ReactiveMongoRepository<MangaDocument, String> {
}
