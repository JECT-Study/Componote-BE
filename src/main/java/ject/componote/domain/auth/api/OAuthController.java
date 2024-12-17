package ject.componote.domain.auth.api;

import ject.componote.domain.auth.application.OAuthService;
import ject.componote.domain.auth.dto.login.response.OAuthLoginResponse;
import ject.componote.domain.auth.dto.authorize.response.OAuthAuthorizationUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/oauth/{providerType}")
@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/authorization-url")
    public ResponseEntity<OAuthAuthorizationUrlResponse> getOAuthAuthorizationCodeUrl(@PathVariable("providerType") final String providerType) {
        return ResponseEntity.ok(
                oAuthService.getOAuthAuthorizationCodeUrl(providerType)
        );
    }

    @GetMapping("/login")
    public ResponseEntity<OAuthLoginResponse> oauthLogin(@PathVariable("providerType") final String providerType,
                                                         @RequestParam("code") final String code) {
        return ResponseEntity.ok(
                oAuthService.login(providerType, code)
        );
    }
}
