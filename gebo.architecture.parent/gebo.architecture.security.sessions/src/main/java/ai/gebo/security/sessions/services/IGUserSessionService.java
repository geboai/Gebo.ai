/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.sessions.services;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.security.sessions.model.GUserSession;
import lombok.Data;

/**
 * The sessions store: records every token in use under its session, answers
 * whether a session was revoked, and revokes sessions.
 */
public interface IGUserSessionService {

	/** The filters of the administrative listing. */
	@Data
	public static class SessionsQuery {
		/** Part of the user name, case insensitive. */
		private String username = null;
		private GUserSession.Kind kind = null;
		/** Only sessions seen in the last so many minutes. */
		private Integer activeWithinMinutes = null;
		private boolean includeRevoked = false;
		private boolean includeExpired = false;
	}

	/**
	 * The session id of a token's claims: the login's session, the API key's own
	 * id, or the hash standing for an external token; {@code null} for a token
	 * that belongs to no session (a system token).
	 */
	String sessionIdOf(String tokenId, Map<String, Object> claims);

	/**
	 * A token of the session just authenticated a request: the session is
	 * recorded the first time, refreshed afterwards. Never throws.
	 */
	void touch(String tokenId, Map<String, Object> claims, String clientIp, String userAgent);

	boolean isRevoked(String sessionId);

	/** Revokes the session; {@code null} when it does not exist. */
	GUserSession revoke(String sessionId, String revokedBy);

	/** Revokes every live session of the user; how many. */
	int revokeAllOf(String username, String revokedBy);

	/** Revokes the session of the bearer token presented, if it belongs to one. */
	GUserSession revokeOfToken(String token, String revokedBy);

	Page<GUserSession> list(SessionsQuery query, Pageable pageable);

	/** How many sessions were seen in the last so many minutes, revoked ones excluded. */
	long countActive(int withinMinutes);
}
