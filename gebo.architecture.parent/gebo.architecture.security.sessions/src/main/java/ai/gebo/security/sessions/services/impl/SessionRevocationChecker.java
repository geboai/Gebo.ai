/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.sessions.services.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.security.services.IGAuthenticatedTokenObserver;
import ai.gebo.security.services.IGIssuedTokenRevocationChecker;
import ai.gebo.security.sessions.services.IGUserSessionService;
import lombok.AllArgsConstructor;

/**
 * The sessions store on the request path: a token of a revoked session is
 * refused, every token that authenticates refreshes its session.
 */
@Service
@AllArgsConstructor
public class SessionRevocationChecker implements IGIssuedTokenRevocationChecker, IGAuthenticatedTokenObserver {

	private final IGUserSessionService sessions;

	@Override
	public boolean isRevoked(String tokenId, Map<String, Object> claims) {
		return sessions.isRevoked(sessions.sessionIdOf(tokenId, claims));
	}

	@Override
	public void onAuthenticated(String tokenId, Map<String, Object> claims, String clientIp, String userAgent) {
		sessions.touch(tokenId, claims, clientIp, userAgent);
	}
}
