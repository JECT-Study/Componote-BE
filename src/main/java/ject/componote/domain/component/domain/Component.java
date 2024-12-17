package ject.componote.domain.component.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import ject.componote.domain.common.domain.BaseEntity;
import ject.componote.domain.common.model.Count;
import ject.componote.domain.common.model.converter.CountConverter;
import ject.componote.domain.component.domain.detail.block.ContentBlock;
import ject.componote.domain.component.domain.summary.ComponentSummary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Component extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComponentType type;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "component_id")
    private List<MixedName> mixedNames = new ArrayList<>();

    @Embedded
    private ComponentSummary summary;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "component_id")
    private List<ContentBlock> contentBlocks = new ArrayList<>();

    @Column(name = "bookmark_count", nullable = false)
    @Convert(converter = CountConverter.class)
    private Count bookmarkCount;

    @Column(name = "comment_count", nullable = false)
    @Convert(converter = CountConverter.class)
    private Count commentCount;

    private Component(final ComponentType type, final List<MixedName> mixedNames, final ComponentSummary summary) {
        this.type = type;
        this.mixedNames.addAll(mixedNames);
        this.summary = summary;
        this.bookmarkCount = Count.create();
        this.commentCount = Count.create();
    }

    public static Component of(final ComponentType type, final List<MixedName> mixedNames, final ComponentSummary summary) {
        return new Component(type, mixedNames, summary);
    }

    public void addBlock(final ContentBlock contentBlock) {
        this.contentBlocks.add(contentBlock);
    }

    // 중복 검사 필요... Set으로 둬야되나? 너무 비효율적일 것 같음...
    public void addMixedName(final String mixedName) {
        mixedNames.add(MixedName.from(mixedName));
    }

    public void removeMixedName(final String mixedName) {
        mixedNames.remove(MixedName.from(mixedName));
    }

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", type=" + type +
                ", summary=" + summary +
                ", commentCount=" + commentCount +
                ", bookmarkCount=" + bookmarkCount +
                '}';
    }
}
