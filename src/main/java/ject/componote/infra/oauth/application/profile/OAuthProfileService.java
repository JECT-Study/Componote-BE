package ject.componote.infra.oauth.application.profile;

import ject.componote.infra.util.TimeoutDecorator;
import ject.componote.infra.oauth.dto.token.response.OAuthTokenResponse;
import ject.componote.infra.oauth.model.OAuthProvider;
import ject.componote.infra.oauth.model.profile.OAuthProfile;
import ject.componote.infra.oauth.model.profile.OAuthProfileFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthProfileService {
    private final TimeoutDecorator timeoutDecorator;
    private final ProfileFailHandler profileFailHandler;
    private final WebClient webClient;

    public Mono<OAuthProfile> getProfile(final OAuthProvider oAuthProvider,
                                         final OAuthTokenResponse oAuthTokenResponse,
                                         final int maxRetry,
                                         final int timeout) {
        return timeoutDecorator.decorate(
                getProfileAttributeByToken(oAuthProvider, oAuthTokenResponse.access_token())
                        .map(attribute -> OAuthProfileFactory.of(attribute, oAuthProvider)),
                maxRetry,
                timeout
        );
    }

    private Mono<Map<String, Object>> getProfileAttributeByToken(final OAuthProvider oAuthProvider,
                                                                 final String socialAccessToken) {
        return webClient.method(oAuthProvider.profileMethod())
                .uri(oAuthProvider.profileUrl())
                .accept(MediaType.parseMediaType("application/vnd.github+json"))
                .headers(header -> header.setBearerAuth(socialAccessToken))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> profileFailHandler.handle4xxError(clientResponse, oAuthProvider))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> profileFailHandler.handle5xxError(clientResponse, oAuthProvider))
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
