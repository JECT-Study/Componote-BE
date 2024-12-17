package ject.componote.domain.auth.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ject.componote.domain.auth.model.Email;

@Converter
public class EmailConverter implements AttributeConverter<Email, String> {
    @Override
    public String convertToDatabaseColumn(final Email attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getValue();
    }

    @Override
    public Email convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return null;
        }

        return Email.from(dbData);
    }
}
