package ject.componote.domain.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import ject.componote.domain.auth.model.Email;
import ject.componote.domain.auth.model.Nickname;
import ject.componote.domain.auth.model.converter.EmailConverter;
import ject.componote.domain.auth.model.converter.NicknameConverter;
import ject.componote.domain.common.domain.BaseEntity;
import ject.componote.domain.common.model.Image;
import ject.componote.domain.common.model.converter.ImageConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false)
    @Convert(converter = NicknameConverter.class)
    private Nickname nickname;

    @Column(name = "email", nullable = true)
    @Convert(converter = EmailConverter.class)
    private Email email;

    @Column(name = "job", nullable = false)
    @Enumerated(EnumType.STRING)
    private Job job;

    @Column(name = "profile_image", nullable = false)
    @Convert(converter = ImageConverter.class)
    private Image profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "social_account_id", nullable = false)
    private Long socialAccountId;

    private Member(final Nickname nickname, final Job job, final Image profileImage, final Long socialAccountId) {
        this.nickname = nickname;
        this.job = job;
        this.profileImage = profileImage;
        this.role = Role.USER;
        this.socialAccountId = socialAccountId;
    }

    public static Member of(final String nickname, final String job, final Image profileImage, final Long socialAccountId) {
        return new Member(
                Nickname.from(nickname),
                Job.from(job),
                profileImage,
                socialAccountId
        );
    }

    public void setEmail(final String email) {
        if (email == null || email.isEmpty()) {
            return;
        }

        this.email = Email.from(email);
    }
}
