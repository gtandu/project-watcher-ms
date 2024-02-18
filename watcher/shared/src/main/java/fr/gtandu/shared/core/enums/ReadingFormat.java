package fr.gtandu.shared.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter(onMethod_ = @JsonValue)
public enum ReadingFormat {
    CHAPTER("chapter"), VOLUME("volume");

    private final String name;

    ReadingFormat(String name) {
        this.name = name;
    }
}
