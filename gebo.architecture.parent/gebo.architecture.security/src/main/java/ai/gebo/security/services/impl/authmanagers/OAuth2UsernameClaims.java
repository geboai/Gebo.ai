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
import java.util.Map;

/**
 * Single source of truth for turning an OAuth2 token's claims into the username
 * Gebo keys a user on.
 *
 * <p>
 * The two resource-server provisioning paths used to disagree on this. The
 * {@code GJwtAuthenticationConverter} (JWT path) resolved only {@code email},
 * falling back to {@code sub}; the {@link GOauth2ResourceServerUserProvisioner}
 * (opaque path, and the sync side of the JWT path) walked a longer ladder. That
 * divergence is unsafe with issuers whose <b>access</b> token carries no
 * {@code email} - AWS Cognito being the case in hand: its access token exposes
 * the identity as {@code cognito:username}, never {@code email} (only the id
 * token has {@code email}). A Cognito bearer caller therefore got provisioned by
 * the converter under its opaque {@code sub} (a UUID), while the same human
 * logging in interactively is keyed on their {@code email} - two accounts for
 * one person, which nothing later reconciles.
 * </p>
 *
 * <p>
 * Both paths now resolve through here, so a given token always yields the same
 * username regardless of which path first sees it. The ladder is ordered
 * most-human-meaningful first: a stable, portable {@code email} beats an
 * issuer-scoped login name, which beats the opaque {@code sub} of last resort.
 * </p>
 */
public final class OAuth2UsernameClaims {

	/**
	 * Claim names, in preference order, that can carry the identity's username.
	 * {@code upn} and {@code cognito:username} cover Microsoft Entra and Cognito
	 * access tokens respectively; {@code sub} is the always-present last resort.
	 */
	public static final List<String> USERNAME_CLAIMS = List.of("email", "preferred_username", "upn",
			"cognito:username", "sub");

	private OAuth2UsernameClaims() {
	}

	/**
	 * Returns the name of the first claim in {@link #USERNAME_CLAIMS} that is
	 * present and non-blank, or {@code null} when none is.
	 *
	 * @param claims the validated token claims / introspection attributes (may be
	 *               {@code null})
	 * @return the winning claim name, or {@code null}
	 */
	public static String pickClaimName(Map<String, Object> claims) {
		if (claims == null) {
			return null;
		}
		for (String claim : USERNAME_CLAIMS) {
			Object value = claims.get(claim);
			if (value != null && !value.toString().trim().isEmpty()) {
				return claim;
			}
		}
		return null;
	}

	/**
	 * Returns the username value carried by the first present, non-blank claim in
	 * {@link #USERNAME_CLAIMS}, or {@code null} when none is.
	 *
	 * @param claims the validated token claims / introspection attributes (may be
	 *               {@code null})
	 * @return the resolved username, or {@code null}
	 */
	public static String resolveUsername(Map<String, Object> claims) {
		String claimName = pickClaimName(claims);
		return claimName == null ? null : claims.get(claimName).toString();
	}
}
