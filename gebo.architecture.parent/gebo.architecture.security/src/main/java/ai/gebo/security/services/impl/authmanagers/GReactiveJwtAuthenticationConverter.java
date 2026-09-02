/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Reactive counterpart of {@link GJwtAuthenticationConverter}, carrying the same
 * defense-in-depth restructure: provisioning is triggered strictly by a
 * {@link UsernameNotFoundException} from the (blocking) user-details lookup,
 * which is reachable only after {@code JwtReactiveAuthenticationManager} has
 * decoded and validated the JWT. So an unauthenticated token can never reach
 * provisioning here either.
 *
 * <p>
 * The user-details lookup is blocking, so it is wrapped in
 * {@code Mono.fromCallable(...)} on the bounded-elastic scheduler to keep the
 * reactive pipeline non-blocking.
 * </p>
 */
public class GReactiveJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

	private final UserDetailsService userDetailsService;
	// Nullable: policy-gated resource-server provisioner (as on the servlet path).
	private final GOauth2ResourceServerUserProvisioner provisioner;
	// Nullable: the provider config the token validated against, for handler routing.
	private final Oauth2RuntimeConfiguration runtimeConfig;

	public GReactiveJwtAuthenticationConverter(UserDetailsService userDetailsService,
			GOauth2ResourceServerUserProvisioner provisioner, Oauth2RuntimeConfiguration runtimeConfig) {
		this.userDetailsService = userDetailsService;
		this.provisioner = provisioner;
		this.runtimeConfig = runtimeConfig;
	}

	@Override
	public Mono<AbstractAuthenticationToken> convert(Jwt source) {
		return Mono.fromCallable(() -> convertBlocking(source)).subscribeOn(Schedulers.boundedElastic());
	}

	private AbstractAuthenticationToken convertBlocking(Jwt source) {
		String email = source.getClaim("email");
		if (email == null)
			email = source.getSubject();
		UserDetails user = loadOrProvision(email, source);
		return new JwtAuthenticationToken(source, user.getAuthorities(), user.getUsername());
	}

	private UserDetails loadOrProvision(String email, Jwt source) {
		try {
			return userDetailsService.loadUserByUsername(email);
		} catch (UsernameNotFoundException notFound) {
			if (provisioner != null && provisioner.provisionOnValidatedUnknownUser(runtimeConfig,
					source.getTokenValue(), source.getClaims())) {
				return userDetailsService.loadUserByUsername(email);
			}
			throw notFound;
		}
	}
}
