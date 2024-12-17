package ject.componote.domain.component.domain.detail.block.detail;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import ject.componote.domain.component.domain.detail.DetailType;
import ject.componote.domain.component.domain.detail.block.ContentBlock;
import ject.componote.domain.component.model.ComponentContent;
import ject.componote.domain.component.model.converter.ComponentContentConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TextBlock extends ContentBlock {
    @Convert(converter = ComponentContentConverter.class)
    @Column(name = "content", nullable = false)
    private ComponentContent content;

    private TextBlock(final DetailType type, final ComponentContent content, final Integer order) {
        super(type, order);
        this.content = content;
    }

    public static TextBlock of(final DetailType type, final ComponentContent content, final Integer order) {
        return new TextBlock(type, content, order);
    }
}
