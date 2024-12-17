package ject.componote.domain.common.error;

import ject.componote.global.error.ComponoteException;
import org.springframework.http.HttpStatus;

public class ImageException extends ComponoteException {
    public ImageException(final String message, final HttpStatus status) {
        super(message, status);
    }
}
