package fr.gtandu.shared.core.document;

import fr.gtandu.shared.core.model.ReadingMedia;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDocument extends BaseDocument {

    private String keycloakId;
    private String username;
    private String firstName;
    private String lastName;
    private List<ReadingMedia> readingMediaList;
}
