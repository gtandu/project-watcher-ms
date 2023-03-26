package fr.gtandu.shared.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class MediaDocumentDto extends BaseDocumentDto {

    protected String name;
    protected String description;
    protected Integer releasedDate;
    protected String coverPictureUrl;
    protected Double rate;
    protected String review;
    protected String state;

}
