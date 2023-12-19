package fr.gtandu.shared.core.document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class MediaDocument extends BaseDocument {

    protected String name;
    protected String description;
    protected Integer releasedDate;
    protected String coverPictureUrl;
    @Min(0)
    @Max(5)
    protected Double rate;
    protected String review;
    protected String state;

}
