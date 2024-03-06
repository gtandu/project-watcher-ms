package fr.gtandu.shared.core.media.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(onMethod_ = @JsonValue)
@AllArgsConstructor
public enum MediaType {

    MANGA("manga");

    private final String type;

}
