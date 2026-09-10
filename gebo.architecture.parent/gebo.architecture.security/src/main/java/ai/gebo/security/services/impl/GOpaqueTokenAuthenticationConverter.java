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

import ai.gebo.security.services.impl.authmanagers.OAuth2UsernameClaims;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GOpaqueTokenAuthenticationConverter implements OpaqueTokenAuthenticationConverter {
	private final UserDetailsService userDetailsService;

	@Override
	public Authentication convert(String introspectedToken, OAuth2AuthenticatedPrincipal authenticatedPrincipal) {
		// Same shared claim ladder the provisioner uses to CREATE the user, so the
		// lookup key here matches the create key - otherwise a Cognito identity
		// (username under cognito:username) provisioned by the manager's not-found
		// branch could not be re-loaded under sub, and auth would fail after a
		// successful provisioning.
		String username = OAuth2UsernameClaims.resolveUsername(authenticatedPrincipal.getAttributes());
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
