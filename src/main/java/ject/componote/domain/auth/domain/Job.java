package ject.componote.domain.auth.domain;

import java.util.Arrays;

public enum Job {
    DIRECTOR, DEVELOPER, DESIGNER, MARKETER, ETC;

    public static Job from(final String jobValue) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(jobValue.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown job: " + jobValue));
    }
}
