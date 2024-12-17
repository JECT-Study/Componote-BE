package ject.componote.domain.component.domain.detail.block.detail;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import ject.componote.domain.common.model.Image;
import ject.componote.domain.common.model.converter.ImageConverter;
import ject.componote.domain.component.domain.detail.DetailType;
import ject.componote.domain.component.domain.detail.block.ContentBlock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ImageBlock extends ContentBlock {
    @Convert(converter = ImageConverter.class)
    @Column(name = "image", nullable = false)
    private Image image;

    private ImageBlock(final DetailType type, final Image image, final Integer order) {
        super(type, order);
        this.image = image;
    }

    public static ImageBlock of(final DetailType type, final Image image, final Integer order) {
        return new ImageBlock(type, image, order);
    }
}