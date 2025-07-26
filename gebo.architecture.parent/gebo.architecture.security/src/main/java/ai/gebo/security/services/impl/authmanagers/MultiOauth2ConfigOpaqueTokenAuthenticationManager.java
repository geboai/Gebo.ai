package ai.gebo.security.services.impl.authmanagers;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class MultiOauth2ConfigOpaqueTokenAuthenticationManager implements AuthenticationManager {
	final SecurityHeaderData header;
	final List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs;
	final OpaqueTokenAuthenticationConverter converter;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		for (Oauth2RuntimeConfiguration oauth2RuntimeConfiguration : oauth2AuthenticationConfigs) {

			try {
				SingleOauth2ConfigOpaqueTokenAuthenticationManager manager = new SingleOauth2ConfigOpaqueTokenAuthenticationManager(
						header, oauth2RuntimeConfiguration, converter);
				return manager.authenticate(authentication);
			} catch (OAuth2AuthenticationException ex) {
				// Token not valid for this provider, try next
			}
		}
		throw new BadCredentialsException("Opaque Token not recognized by any configured provider");
	}
}