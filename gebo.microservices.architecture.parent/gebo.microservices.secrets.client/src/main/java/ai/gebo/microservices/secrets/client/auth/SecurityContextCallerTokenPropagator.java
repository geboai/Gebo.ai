/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.client.auth;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;

/**
 * Forwards the token of whoever is authenticated on this thread and, when there
 * is none, falls back to a freshly minted token for the platform's own system
 * identity.
 *
 * <p>
 * Both bearer-token authentications the platform produces -
 * {@code JwtAuthenticationToken} (validated JWT) and
 * {@code BearerTokenAuthentication} (introspected opaque token) - extend
 * {@link AbstractOAuth2TokenAuthenticationToken}, so a single check covers them
 * and yields the original, untouched token value.
 * </p>
 *
 * <h2>Why the fallback is not optional</h2>
 * <p>
 * A large share of secret reads happen on <b>no user's</b> thread: LLM clients are
 * built at startup and on model replication, MCP connectors reconnect in the
 * background, schedulers run jobs. Those threads either carry no
 * {@code SecurityContext} at all or - under {@code IdentityUtil.doAs} - one
 * holding an authentication that was synthesized locally with <b>null
 * credentials</b>. In neither case does a token exist: propagation is not merely
 * unavailable there, there is nothing to propagate. Without the fallback those
 * calls would reach the secrets service unauthenticated and be refused.
 * </p>
 *
 * <p>
 * The fallback does not weaken anything: it does not bypass authentication, it
 * <i>performs</i> it, minting an ordinary short-lived {@code LOCAL_JWT} that the
 * remote side validates on the normal path. The calls arrive attributable to the
 * system identity rather than anonymous.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class SecurityContextCallerTokenPropagator implements IGeboSecretsCallerTokenPropagator {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityContextCallerTokenPropagator.class);

	/**
	 * Mints a token for the platform's own identity. Supplied rather than injected so
	 * this module stays independent of the security implementation; in a Gebo service
	 * it is {@code IGeboSystemUserService::createToken}.
	 */
	private final Supplier<String> systemTokenSupplier;

	public SecurityContextCallerTokenPropagator(Supplier<String> systemTokenSupplier) {
		this.systemTokenSupplier = systemTokenSupplier;
	}

	@Override
	public String currentToken() {
		String callerToken = tokenFromSecurityContext();
		if (callerToken != null) {
			return callerToken;
		}
		return systemToken();
	}

	/**
	 * The caller's own token, so the remote side sees the identity that originated the
	 * request rather than the platform's.
	 */
	private String tokenFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		if (authentication instanceof AbstractOAuth2TokenAuthenticationToken<?> tokenAuthentication) {
			return valueOrNull(tokenAuthentication.getToken().getTokenValue());
		}
		// Some of the platform's authentication managers keep the raw presented
		// credential (e.g. an api key). Note this is deliberately NOT where a
		// doAs/RunAs identity is picked up: those carry null credentials, so they fall
		// through to the system token below - which is the correct outcome, since a
		// synthesized identity has no token that a remote service could verify.
		if (authentication.getCredentials() instanceof String credentials) {
			return valueOrNull(credentials);
		}
		return null;
	}

	private String systemToken() {
		if (systemTokenSupplier == null) {
			// Only reachable on a service without the security implementation on its
			// classpath. Say so loudly: the call is about to be refused, and the reason
			// would otherwise look like a mysterious 401.
			LOGGER.warn("No caller token on this thread and no system-identity token provider is available "
					+ "(is gebo.architecture.security on the classpath?). The call will go out unauthenticated "
					+ "and is likely to be refused.");
			return null;
		}
		try {
			return valueOrNull(systemTokenSupplier.get());
		} catch (RuntimeException ex) {
			LOGGER.error("Cannot mint a system-identity token; the call will go out unauthenticated", ex);
			return null;
		}
	}

	private static String valueOrNull(String value) {
		return value == null || value.isBlank() ? null : value;
	}
}
