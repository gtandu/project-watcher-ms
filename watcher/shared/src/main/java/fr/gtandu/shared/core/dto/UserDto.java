package fr.gtandu.shared.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDocumentDto implements Serializable {

    private String keycloakId;
    private String username;
    private String firstName;
    private String lastName;
    private List<ReadingMediaDto> readingMediaList;
}



