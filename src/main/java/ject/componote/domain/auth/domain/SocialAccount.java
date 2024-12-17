package ject.componote.domain.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Column(name = "provider_type", nullable = false)
    private String providerType;

    private SocialAccount(final String socialId, final String providerType) {
        this.socialId = socialId;
        this.providerType = providerType;
    }

    public static SocialAccount of(final String socialId, final String providerType) {
        return new SocialAccount(socialId, providerType);
    }
}
