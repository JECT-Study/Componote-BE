package ject.componote.infra.file.error;

import ject.componote.global.error.ErrorResponse;
import ject.componote.infra.error.InfraException;
import org.springframework.http.HttpStatus;

public class FileClientException extends InfraException {
    public FileClientException(final ErrorResponse errorResponse) {
        super(errorResponse.getMessage(), HttpStatus.valueOf(errorResponse.getStatus()));
    }
}
