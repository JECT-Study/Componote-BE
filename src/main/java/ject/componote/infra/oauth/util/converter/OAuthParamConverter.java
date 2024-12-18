package ject.componote.infra.oauth.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ject.componote.infra.oauth.dto.authorize.request.OAuthAuthorizeRequest;
import ject.componote.infra.oauth.dto.token.request.OAuthTokenRequest;
import ject.componote.infra.oauth.model.OAuthProvider;
import ject.componote.infra.oauth.util.mapper.OAuthObjectMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class OAuthParamConverter {
    public MultiValueMap<String, String> convertToAuthorizeParams(final OAuthProvider oAuthProvider) {
        final ObjectMapper objectMapper = OAuthObjectMapperFactory.getFrom(oAuthProvider.name());
        final OAuthAuthorizeRequest oAuthAuthorizeRequest = OAuthAuthorizeRequest.from(oAuthProvider);
        return MultiValueMapConverter.convertFrom(objectMapper, oAuthAuthorizeRequest, TransformationStrategy.ENCODE);
    }

    public MultiValueMap<String, String> convertToTokenParams(final OAuthProvider oAuthProvider,
                                                              final String code) {
        final ObjectMapper objectMapper = OAuthObjectMapperFactory.getFrom(oAuthProvider.name());
        final OAuthTokenRequest oAuthTokenRequest = OAuthTokenRequest.of(oAuthProvider, code);
        return MultiValueMapConverter.convertFrom(objectMapper, oAuthTokenRequest, TransformationStrategy.DECODE);
    }
}
