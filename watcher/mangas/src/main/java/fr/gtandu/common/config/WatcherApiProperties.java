package fr.gtandu.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "watcher-api")
public record WatcherApiProperties(Manga manga, ReadingManga readingManga) {
    public record Manga(String baseUrl, String deleteMangaById) {
    }

    public record ReadingManga(String baseUrl) {
    }
}
