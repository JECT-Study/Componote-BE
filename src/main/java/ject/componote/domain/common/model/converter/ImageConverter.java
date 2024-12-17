package ject.componote.domain.common.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ject.componote.domain.common.model.Image;

@Converter
public class ImageConverter implements AttributeConverter<Image, String> {
    @Override
    public String convertToDatabaseColumn(final Image attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getObjectKey();
    }

    @Override
    public Image convertToEntityAttribute(final String dbData) {
        return Image.from(dbData);
    }
}
