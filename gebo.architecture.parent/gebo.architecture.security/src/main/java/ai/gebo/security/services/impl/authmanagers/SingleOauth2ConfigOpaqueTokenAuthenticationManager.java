package ai.gebo.security.services.impl.authmanagers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SingleOauth2ConfigOpaqueTokenAuthenticationManager implements AuthenticationManager {
	final SecurityHeaderData header;
	final Oauth2RuntimeConfiguration oauth2Configuration;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SpringOpaqueTokenIntrospector opaqueTokenIntrospector = new SpringOpaqueTokenIntrospector(
				oauth2Configuration.getProviderConfig().getIntrospectionUri(),
				oauth2Configuration.getClient().getClientId(), oauth2Configuration.getClient().getSecret());

		OpaqueTokenAuthenticationProvider opaqueProvider = new OpaqueTokenAuthenticationProvider(
				opaqueTokenIntrospector);
		return opaqueProvider.authenticate(authentication);

	}

}
