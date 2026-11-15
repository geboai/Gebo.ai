/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.sessions.model;

import java.util.Date;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;

/**
 * A token, or a chain of tokens, in use: what an administrator sees as a live
 * session and revokes. The code is the session id:
 * <ul>
 * <li>a login's session id, shared by the token minted at login and by every
 * token renewed from it;</li>
 * <li>a generated API key's token id;</li>
 * <li>the hash of a token of an external identity provider accepted on the
 * OAuth2 resource-server path.</li>
 * </ul>
 * The record is written the first time a token of the session authenticates a
 * request and refreshed as the session goes on; revoking it refuses every token
 * of the session from the next request, before the token's own expiration.
 */
@Data
public class GUserSession extends GBaseObject {

	/** What opened the session. */
	public enum Kind {
		/** A login, local or through an OAuth2 login of this platform. */
		SESSION,
		/** A generated API key, personal or administrative. */
		API_KEY,
		/** A token of an external identity provider accepted directly. */
		OAUTH2
	}

	/** The user the tokens authenticate. */
	private String username = null;
	private Kind kind = null;
	/** The authentication type the tokens declare (LOCAL_JWT, OAUTH2). */
	private String authType = null;
	/** The authentication provider or the OAuth2 registration the tokens come from. */
	private String authProvider = null;
	/** When a token of the session first authenticated a request. */
	private Date firstSeen = null;
	/** When a token of the session last authenticated a request. */
	private Date lastSeen = null;
	/** The id ({@code jti} or hash) of the token last seen. */
	private String lastTokenId = null;
	private String lastClientIp = null;
	private String lastUserAgent = null;
	/** When the token last seen expires; a renewal moves it forward. */
	private Date expiresAt = null;
	private boolean revoked = false;
	private String revokedBy = null;
	private Date revokedAt = null;
}
