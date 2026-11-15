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

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import ai.gebo.security.services.IGAuthenticatedTokenObserver;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import lombok.AllArgsConstructor;

/**
 * Wraps the authentication manager of a token not minted here (the OAuth2
 * resource-server path): once the delegate accepted the token, the token is
 * refused when revoked in the sessions store and reported to it otherwise.
 */
@AllArgsConstructor
public class SessionAwareAuthenticationManager implements AuthenticationManager {
	private final AuthenticationManager delegate;
	private final String token;
	private final LocalJwtTokenProvider tokenProvider;
	private final List<IGAuthenticatedTokenObserver> observers;
	private final String clientIp;
	private final String userAgent;
	private final String authProviderId;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return SessionTracking.afterAuthentication(delegate.authenticate(authentication), token, tokenProvider,
				observers, clientIp, userAgent, authProviderId);
	}
}
