package ai.gebo.security.services.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GOpaqueTokenAuthenticationConverter implements OpaqueTokenAuthenticationConverter {
	private final UserDetailsService userDetailsService;

	@Override
	public Authentication convert(String introspectedToken, OAuth2AuthenticatedPrincipal authenticatedPrincipal) {
		String username = authenticatedPrincipal.getAttribute("email");
		if (username == null) {
			username = authenticatedPrincipal.getAttribute("sub");
		}

		UserDetails user = userDetailsService.loadUserByUsername(username);
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(user.getAuthorities());
		Map<String, Object> map = authenticatedPrincipal.getAttributes();
		DefaultOAuth2AuthenticatedPrincipal customPrincipal = new DefaultOAuth2AuthenticatedPrincipal(
				user.getUsername(), map, authorities);
		OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
				introspectedToken, Instant.now(), Instant.now().plusSeconds(3600));
		return new BearerTokenAuthentication(customPrincipal, oAuth2AccessToken, authorities);
	}

}
