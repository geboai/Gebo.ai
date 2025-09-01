package ai.gebo.security.services.impl;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
	private final UserDetailsService userDetailsService;

	@Override
	@Nullable
	public AbstractAuthenticationToken convert(Jwt source) {
		String email = source.getClaim("email");
		if (email == null) {
			email = source.getSubject();
		}
		UserDetails user = userDetailsService.loadUserByUsername(email);
		return new JwtAuthenticationToken(source, user.getAuthorities(), user.getUsername());
	}

}
