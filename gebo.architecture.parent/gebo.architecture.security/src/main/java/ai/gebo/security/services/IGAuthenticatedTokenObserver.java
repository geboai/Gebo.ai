/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services;

import java.util.Map;

/**
 * Told about every locally issued token that just authenticated a request,
 * with where the request came from. The sessions store listens to keep, per
 * session, the user, when it was last seen and from where; a token without an
 * id (a system token) is never reported.
 * <p>
 * Called on the request path: an implementation must be cheap and must never
 * throw, a failure to record a session is not a failure to authenticate.
 */
public interface IGAuthenticatedTokenObserver {

	/**
	 * @param tokenId   the token's {@code jti}
	 * @param claims    its verified claims, read-only
	 * @param clientIp  the caller's address, or {@code null}
	 * @param userAgent the caller's user agent, or {@code null}
	 */
	void onAuthenticated(String tokenId, Map<String, Object> claims, String clientIp, String userAgent);
}
