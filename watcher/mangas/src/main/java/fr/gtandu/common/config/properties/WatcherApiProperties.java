package fr.gtandu.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "watcher-api")
public record WatcherApiProperties(Manga manga, ReadingManga readingManga) {
    public record Manga(String baseUrl, String deleteMangaById, String searchByName) {
    }

    public record ReadingManga(String baseUrl) {
    }
}
