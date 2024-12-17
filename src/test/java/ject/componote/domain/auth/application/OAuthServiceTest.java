package ject.componote.domain.auth.application;

import ject.componote.domain.auth.domain.MemberRepository;
import ject.componote.domain.auth.domain.SocialAccount;
import ject.componote.domain.auth.dto.authorize.response.OAuthAuthorizationUrlResponse;
import ject.componote.domain.auth.dto.login.response.OAuthLoginResponse;
import ject.componote.infra.oauth.application.OAuthClient;
import ject.componote.infra.oauth.dto.authorize.response.OAuthAuthorizePayload;
import ject.componote.infra.oauth.error.InvalidAuthorizationCodeException;
import ject.componote.infra.oauth.error.UnsupportedProviderException;
import ject.componote.infra.oauth.model.OAuthProvider;
import ject.componote.infra.oauth.model.profile.OAuthProfile;
import ject.componote.infra.oauth.model.profile.OAuthProfileFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static ject.componote.fixture.SocialAccountFixture.KIM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class OAuthServiceTest {
    @Mock
    MemberRepository memberRepository;

    @Mock
    OAuthClient oAuthClient;

    @Mock
    OAuthResultHandler oAuthResultHandler;

    @InjectMocks
    OAuthService oAuthService;

    @DisplayName("소셜 인가 코드 요청 URL 조회")
    @ParameterizedTest
    @ValueSource(strings = {"google", "github", "naver"})
    public void getOAuthAuthorizationCodeUrl(final String providerType) throws Exception {
        // given
        final OAuthAuthorizePayload payload = new OAuthAuthorizePayload(HttpMethod.GET, URI.create("authorizeUrl"));
        final OAuthAuthorizationUrlResponse expect = OAuthAuthorizationUrlResponse.from(payload);

        // when
        doReturn(payload).when(oAuthClient)
                .getAuthorizePayload(providerType);
        final OAuthAuthorizationUrlResponse actual = oAuthService.getOAuthAuthorizationCodeUrl(providerType);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    @DisplayName("소셜 인가 코드 요청 URL 조회 시 지원하지 않는 providerType은 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"kakao", "okky"})
    public void getOAuthAuthorizationCodeUrlWhenInvalidProviderType(final String providerType) throws Exception {
        // when
        doThrow(UnsupportedProviderException.class).when(oAuthClient)
                .getAuthorizePayload(providerType);

        // then
        assertThatThrownBy(() -> oAuthService.getOAuthAuthorizationCodeUrl(providerType))
                .isInstanceOf(UnsupportedProviderException.class);
    }

    @DisplayName("소셜 로그인")
    @ParameterizedTest
    @ValueSource(strings = {"google", "github", "naver"})
    public void login(final String providerType) throws Exception {
        // given
        final String code = "code";
        final OAuthProvider oAuthProvider = getOAuthProvider(providerType);
        final OAuthProfile oAuthProfile = getOAuthProfile(oAuthProvider);
        final SocialAccount socialAccount = KIM.생성(providerType);
        final OAuthLoginResponse expect = OAuthLoginResponse.of(true, socialAccount);

        // when
        doReturn(Mono.just(oAuthProfile)).when(oAuthClient)
                        .getProfile(providerType, code);
        doReturn(socialAccount).when(oAuthResultHandler)
                .saveOrGet(oAuthProfile);
        doReturn(true).when(memberRepository)
                .existsBySocialAccountId(socialAccount.getId());
        final OAuthLoginResponse actual = oAuthService.login(providerType, code);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    @DisplayName("소셜 로그인 시 잘못된 인가 코드시 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"google", "github", "naver"})
    public void loginWhenInvalidCode(final String providerType) throws Exception {
        // given
        final String code = "code";

        // when
        doThrow(new InvalidAuthorizationCodeException(code)).when(oAuthClient)
                .getProfile(providerType, code);

        // then
        assertThatThrownBy(() -> oAuthService.login(providerType, code))
                .isInstanceOf(InvalidAuthorizationCodeException.class);
    }

    @DisplayName("소셜 로그인 시 지원하지 않는 providerType은 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"kakao", "okky"})
    public void loginWhenInvalidProviderType(final String providerType) throws Exception {
        // given
        final String code = "code";

        // when
        doThrow(UnsupportedProviderException.class).when(oAuthClient)
                .getProfile(providerType, code);

        // then
        assertThatThrownBy(() -> oAuthService.login(providerType, code))
                .isInstanceOf(UnsupportedProviderException.class);
    }

    private OAuthProvider getOAuthProvider(final String providerName) {
        return new OAuthProvider(
                providerName,
                "clientId",
                "clientSecret",
                "code",
                "authorizeUrl",
                HttpMethod.GET,
                List.of("profile", "image"),
                "redirectUrl",
                "grantType",
                HttpMethod.GET,
                "tokenUrl",
                HttpMethod.GET,
                "profileUrl"
        );
    }

    private OAuthProfile getOAuthProfile(final OAuthProvider oAuthProvider) {
        return OAuthProfileFactory.of(Collections.emptyMap(), oAuthProvider);
    }
}