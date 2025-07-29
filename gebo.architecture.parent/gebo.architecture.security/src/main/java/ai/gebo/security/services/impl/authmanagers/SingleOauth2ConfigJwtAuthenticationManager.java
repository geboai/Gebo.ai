package ai.gebo.security.services.impl.authmanagers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SingleOauth2ConfigJwtAuthenticationManager implements AuthenticationManager {
	final SecurityHeaderData header;
	final Oauth2RuntimeConfiguration oauth2Configuration;
	final Converter<Jwt, AbstractAuthenticationToken> converter;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String issuerUri = oauth2Configuration.getProviderConfig().getIssuerUri();
		JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
		JwtAuthenticationProvider jwtProvider = new JwtAuthenticationProvider(jwtDecoder);
		jwtProvider.setJwtAuthenticationConverter(converter);
		return jwtProvider.authenticate(authentication);
	}

}
