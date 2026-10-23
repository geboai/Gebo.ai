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
 * Tells whether a locally issued token that carries a token id ({@code jti})
 * has been revoked since it was minted.
 * <p>
 * Session tokens carry no id and are never asked about: they live as long as
 * their expiration says. Long-lived tokens handed out to third parties, the
 * generated API keys, carry the id of the record that keeps them, and that
 * record's deletion must take the token out of service before it expires. The
 * token provider asks every registered checker on each validation of a token
 * with an id; one {@code true} rejects the token. A checker answers only for
 * the tokens it knows (the claims tell which kind it is) and {@code false} for
 * every other.
 */
public interface IGIssuedTokenRevocationChecker {

	/**
	 * @param tokenId the token's {@code jti} claim
	 * @param claims  the verified claims of the token, read-only
	 * @return {@code true} when the token must no longer be accepted
	 */
	boolean isRevoked(String tokenId, Map<String, Object> claims);
}
