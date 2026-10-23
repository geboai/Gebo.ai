/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import java.util.List;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;

import ai.gebo.security.services.IGAuthenticatedTokenObserver;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * The reactive counterpart of {@link SessionAwareAuthenticationManager}: the
 * revocation check and the observers' call run off the event loop.
 */
@AllArgsConstructor
public class SessionAwareReactiveAuthenticationManager implements ReactiveAuthenticationManager {
	private final ReactiveAuthenticationManager delegate;
	private final String token;
	private final LocalJwtTokenProvider tokenProvider;
	private final List<IGAuthenticatedTokenObserver> observers;
	private final String clientIp;
	private final String userAgent;
	private final String authProviderId;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return delegate.authenticate(authentication)
				.flatMap(authenticated -> Mono
						.fromCallable(() -> SessionTracking.afterAuthentication(authenticated, token, tokenProvider,
								observers, clientIp, userAgent, authProviderId))
						.subscribeOn(Schedulers.boundedElastic()));
	}
}
