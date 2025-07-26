package ai.gebo.security.services.impl.authmanagers;

import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class MultiOauth2ConfigJwtAuthenticationManager implements AuthenticationManager {
	final SecurityHeaderData header;
	final List<Oauth2RuntimeConfiguration> oauth2AuthenticationConfigs;
	final Converter<Jwt, AbstractAuthenticationToken> converter;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		for (Oauth2RuntimeConfiguration oauth2RuntimeConfiguration : oauth2AuthenticationConfigs) {

			try {
				SingleOauth2ConfigJwtAuthenticationManager manager = new SingleOauth2ConfigJwtAuthenticationManager(
						header, oauth2RuntimeConfiguration,converter);
				return manager.authenticate(authentication);
			} catch (OAuth2AuthenticationException ex) {
				// Token not valid for this provider, try next
			}
		}
		throw new BadCredentialsException("JWT not recognized by any configured provider");
	}

}