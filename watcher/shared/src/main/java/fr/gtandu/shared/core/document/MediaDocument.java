package fr.gtandu.shared.core.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

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
