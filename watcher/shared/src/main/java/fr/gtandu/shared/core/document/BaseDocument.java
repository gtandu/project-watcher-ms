package fr.gtandu.shared.core.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseDocument {

    @Id
    protected String id;
}
