package fr.gtandu.mangadex.dto;

public record MangaDexResponse(String result, String response, MangaDexObject[] data, Integer limit, Integer offset,
                               Integer total) {

    public record MangaDexObjectAttributesLanguageData(String en, String fr) {
    }

    public record MangaDexObjectRelationshipsAttributesData(String fileName) {
    }

    public record MangaDexObjectRelationshipsData(String id, String type,
                                                  MangaDexObjectRelationshipsAttributesData attributes) {
    }

    public record MangaDexObjectAttributes(MangaDexObjectName name,
                                           MangaDexObjectAttributesLanguageData title,
                                           MangaDexObjectAttributesLanguageData description,
                                           String lastVolume,
                                           String lastChapter,
                                           String publicationDemographic,
                                           String status,
                                           Integer year) {
    }

    public record MangaDexObject(String id, String type, MangaDexObjectAttributes attributes,
                                 MangaDexObjectRelationshipsData[] relationships) {
    }

    public record MangaDexObjectName(String en, String fr) {
    }

}
