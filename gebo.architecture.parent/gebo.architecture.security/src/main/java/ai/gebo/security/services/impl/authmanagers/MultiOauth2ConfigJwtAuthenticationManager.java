package ai.gebo.security.services.impl.authmanagers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class MultiOauth2ConfigJwtAuthenticationManager implements AuthenticationManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(MultiOauth2ConfigJwtAuthenticationManager.class);

	final SecurityHeaderData header;
	final List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs;
	final Converter<Jwt, AbstractAuthenticationToken> converter;
	final JwtDecoderCache decoderCache;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Exception lastError = null;
		for (Oauth2RuntimeConfiguration oauth2RuntimeConfiguration : oauth2AuthenticationConfigs) {
			String issuerUri = oauth2RuntimeConfiguration.getProviderConfig().getIssuerUri();
			try {
				SingleOauth2ConfigJwtAuthenticationManager manager = new SingleOauth2ConfigJwtAuthenticationManager(
						header, oauth2RuntimeConfiguration, converter, decoderCache);
				return manager.authenticate(authentication);
			} catch (AuthenticationException ex) {
				// Token not valid for this provider (bad signature/issuer/expiry); try next.
				lastError = ex;
				LOGGER.debug("JWT not accepted by provider issuer [{}]: {}", issuerUri, ex.getMessage());
			} catch (RuntimeException ex) {
				// Decoder build / OIDC discovery / JWKS failure for this provider (e.g. the
				// issuer is unreachable or misconfigured). Do not abort the whole chain: a
				// different configured provider may still validate the token.
				lastError = ex;
				LOGGER.warn("Could not evaluate JWT against provider issuer [{}]: {}", issuerUri, ex.getMessage());
			}
		}
		throw new BadCredentialsException("JWT not recognized by any configured provider", lastError);
	}

}