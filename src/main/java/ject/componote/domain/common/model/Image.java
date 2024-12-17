package ject.componote.domain.common.model;

import ject.componote.domain.common.error.InvalidImageExtensionException;
import ject.componote.domain.common.error.NotFoundImageException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Image {
    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

    private final String objectKey;  // 변경해도 상관없음

    private Image(final String objectKey) {
        validateObjectKey(objectKey);
        this.objectKey = objectKey;
    }

    public static Image from(final String objectKey) {
        return new Image(objectKey);
    }

    private void validateObjectKey(final String objectKey) {
        if (objectKey == null || objectKey.isEmpty()) {
            throw new NotFoundImageException();
        }

        final String extension = StringUtils.getFilenameExtension(objectKey);
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
            throw new InvalidImageExtensionException(extension);
        }
    }
}
