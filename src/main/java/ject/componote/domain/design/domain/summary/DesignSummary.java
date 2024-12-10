package ject.componote.domain.design.domain.summary;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import ject.componote.domain.common.model.Image;
import ject.componote.domain.common.model.converter.ImageConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class DesignSummary {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "organization", nullable = false)
    private String organization;

    @Column(name = "description", nullable = false)
    private String description;

    @Convert(converter = ImageConverter.class)
    @Column(name = "thumbnail", nullable = false)
    private Image thumbnail;

    private DesignSummary(final String name, final String organization, final String description, final Image thumbnail) {
        this.name = name;
        this.organization = organization;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public static DesignSummary of(final String name, final String organization, final String description, final Image thumbnail) {
        return new DesignSummary(name, organization, description, thumbnail);
    }
}
