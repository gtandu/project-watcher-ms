package fr.gtandu.repository;

import fr.gtandu.shared.core.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<UserDocument, String> {
}
