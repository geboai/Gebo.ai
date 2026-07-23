package ai.gebo.security.services.impl.authmanagers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class MultiOauth2ConfigOpaqueTokenAuthenticationManager implements AuthenticationManager {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MultiOauth2ConfigOpaqueTokenAuthenticationManager.class);

	final SecurityHeaderData header;
	final List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs;
	final OpaqueTokenAuthenticationConverter converter;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Exception lastError = null;
		for (Oauth2RuntimeConfiguration oauth2RuntimeConfiguration : oauth2AuthenticationConfigs) {
			String introspectionUri = oauth2RuntimeConfiguration.getProviderConfig().getIntrospectionUri();
			try {
				SingleOauth2ConfigOpaqueTokenAuthenticationManager manager = new SingleOauth2ConfigOpaqueTokenAuthenticationManager(
						header, oauth2RuntimeConfiguration, converter);
				return manager.authenticate(authentication);
			} catch (AuthenticationException ex) {
				// Token not valid for this provider; try next.
				lastError = ex;
				LOGGER.debug("Opaque token not accepted by provider introspection [{}]: {}", introspectionUri,
						ex.getMessage());
			} catch (RuntimeException ex) {
				// Introspection endpoint failure for this provider (unreachable/misconfigured).
				// Do not abort the whole chain: another provider may still validate the token.
				lastError = ex;
				LOGGER.warn("Could not introspect opaque token against provider [{}]: {}", introspectionUri,
						ex.getMessage());
			}
		}
		throw new BadCredentialsException("Opaque Token not recognized by any configured provider", lastError);
	}
}