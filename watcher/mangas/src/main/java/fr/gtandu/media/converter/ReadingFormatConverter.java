package fr.gtandu.media.converter;

import fr.gtandu.media.enums.ReadingFormat;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ReadingFormatConverter implements AttributeConverter<ReadingFormat, String> {

    @Override
    public String convertToDatabaseColumn(ReadingFormat readingFormat) {
        if (readingFormat == null) {
            return null;
        }
        return readingFormat.getName();
    }

    @Override
    public ReadingFormat convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(ReadingFormat.values())
                .filter(readingFormatValue -> readingFormatValue.getName().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
