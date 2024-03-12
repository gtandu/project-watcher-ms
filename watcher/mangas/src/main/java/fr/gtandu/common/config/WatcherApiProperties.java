package fr.gtandu.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "watcher-api")
public record WatcherApiProperties(Manga manga, ReadingMedia readingMedia) {
    public record Manga(String baseUrl, String deleteMangaById) {
    }

    public record ReadingMedia(String baseUrl) {
    }
}
