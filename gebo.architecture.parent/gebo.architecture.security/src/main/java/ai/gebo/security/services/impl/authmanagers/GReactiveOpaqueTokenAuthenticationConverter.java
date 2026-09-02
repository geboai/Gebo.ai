/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenAuthenticationConverter;

import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Reactive counterpart of {@link GOpaqueTokenAuthenticationConverter}, carrying
 * the same restructure: provisioning fires strictly on a
 * {@link UsernameNotFoundException} from the (blocking) user-details lookup,
 * reachable only after {@code OpaqueTokenReactiveAuthenticationManager} has
 * introspected and validated the token (an inactive token errors earlier, during
 * introspection).
 */
public class GReactiveOpaqueTokenAuthenticationConverter implements ReactiveOpaqueTokenAuthenticationConverter {

	private final UserDetailsService userDetailsService;
	private final GOauth2ResourceServerUserProvisioner provisioner;
	private final Oauth2RuntimeConfiguration runtimeConfig;

	public GReactiveOpaqueTokenAuthenticationConverter(UserDetailsService userDetailsService,
			GOauth2ResourceServerUserProvisioner provisioner, Oauth2RuntimeConfiguration runtimeConfig) {
		this.userDetailsService = userDetailsService;
		this.provisioner = provisioner;
		this.runtimeConfig = runtimeConfig;
	}

	@Override
	public Mono<Authentication> convert(String introspectedToken,
			OAuth2AuthenticatedPrincipal authenticatedPrincipal) {
		return Mono.fromCallable(() -> convertBlocking(introspectedToken, authenticatedPrincipal))
				.subscribeOn(Schedulers.boundedElastic());
	}

	private Authentication convertBlocking(String introspectedToken, OAuth2AuthenticatedPrincipal principal) {
		String username = principal.getAttribute("email");
		if (username == null)
			username = principal.getAttribute("sub");
		UserDetails user = loadOrProvision(username, introspectedToken, principal);
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(user.getAuthorities());
		DefaultOAuth2AuthenticatedPrincipal customPrincipal = new DefaultOAuth2AuthenticatedPrincipal(
				user.getUsername(), principal.getAttributes(), authorities);
		OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, introspectedToken,
				Instant.now(), Instant.now().plusSeconds(3600));
		return new BearerTokenAuthentication(customPrincipal, accessToken, authorities);
	}

	private UserDetails loadOrProvision(String username, String introspectedToken, OAuth2AuthenticatedPrincipal principal) {
		try {
			return userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException notFound) {
			if (provisioner != null && provisioner.provisionOnValidatedUnknownUser(runtimeConfig, introspectedToken,
					principal.getAttributes())) {
				return userDetailsService.loadUserByUsername(username);
			}
			throw notFound;
		}
	}
}
