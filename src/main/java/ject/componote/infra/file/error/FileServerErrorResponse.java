package ject.componote.infra.file.error;

import ject.componote.global.error.ErrorResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FileServerErrorResponse {
    private int status;
    private String message;

    public static ErrorResponse of(final HttpStatus status, final String message) {
        return new ErrorResponse(status.value(), message);
    }

    public static ErrorResponse of(final HttpStatus status, final Exception exception) {
        return new ErrorResponse(status.value(), exception.getMessage());
    }
}