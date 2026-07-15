/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.cluster.auth;

/**
 * Supplies the bearer token a cluster client forwards to the service it calls,
 * so the callee sees the <b>original caller's</b> identity rather than an
 * anonymous service-to-service call.
 *
 * <p>
 * Replace the default bean to plug in a different scheme (a token exchange, a
 * service-account grant, ...).
 * </p>
 *
 * Gebo.ai comment agent
 */
public interface IGeboCallerTokenPropagator {

	/**
	 * The token to send on the next call.
	 *
	 * @return the raw bearer token value, or {@code null} when there is none to
	 *         forward (the call then goes out unauthenticated and heimdall decides
	 *         what to do with it)
	 */
	String currentToken();
}
