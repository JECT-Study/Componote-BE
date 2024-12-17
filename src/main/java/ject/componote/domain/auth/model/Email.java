package ject.componote.domain.auth.model;

import ject.componote.domain.auth.error.InvalidEmailException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class Email {
    private static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private final String value;

    private Email(final String value) {
        validate(value);
        this.value = value;
    }

    public static Email from(final String value) {
        return new Email(value);
    }

    private void validate(final String value) {
        if (value == null || !value.matches(EMAIL_REGEX_PATTERN)) {
            throw new InvalidEmailException(value);
        }
    }
}
