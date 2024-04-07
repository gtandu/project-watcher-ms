package fr.gtandu.mangadex.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MangaDexStatus {

    ONGOING("ongoing"), COMPLETED("completed"), HIATUS("hiatus"), CANCELLED("cancelled");

    private final String status;
}
