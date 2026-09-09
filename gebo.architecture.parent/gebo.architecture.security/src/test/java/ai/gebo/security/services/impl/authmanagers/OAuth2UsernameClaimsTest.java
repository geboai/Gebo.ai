/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * The property that makes the two resource-server provisioning paths agree: one
 * ladder, so a token resolves to the same username whichever path first sees it.
 */
class OAuth2UsernameClaimsTest {

	@Test
	void prefersEmailOverEveryOtherClaim() {
		Map<String, Object> claims = new LinkedHashMap<>();
		claims.put("sub", "uuid-123");
		claims.put("cognito:username", "jdoe");
		claims.put("email", "jane@example.com");

		assertThat(OAuth2UsernameClaims.pickClaimName(claims)).isEqualTo("email");
		assertThat(OAuth2UsernameClaims.resolveUsername(claims)).isEqualTo("jane@example.com");
	}

	@Test
	void fallsToCognitoUsernameWhenNoEmail() {
		// A Cognito access token: no email, identity under cognito:username. The old
		// email-then-sub logic would have keyed this on the opaque sub instead.
		Map<String, Object> claims = new LinkedHashMap<>();
		claims.put("sub", "uuid-123");
		claims.put("cognito:username", "jdoe");

		assertThat(OAuth2UsernameClaims.pickClaimName(claims)).isEqualTo("cognito:username");
		assertThat(OAuth2UsernameClaims.resolveUsername(claims)).isEqualTo("jdoe");
	}

	@Test
	void fallsToSubAsLastResort() {
		Map<String, Object> claims = Map.of("sub", "uuid-123");

		assertThat(OAuth2UsernameClaims.pickClaimName(claims)).isEqualTo("sub");
		assertThat(OAuth2UsernameClaims.resolveUsername(claims)).isEqualTo("uuid-123");
	}

	@Test
	void ignoresBlankClaimValues() {
		Map<String, Object> claims = new LinkedHashMap<>();
		claims.put("email", "   ");
		claims.put("preferred_username", "jdoe");

		assertThat(OAuth2UsernameClaims.pickClaimName(claims)).isEqualTo("preferred_username");
	}

	@Test
	void returnsNullWhenNoUsernameClaimPresent() {
		Map<String, Object> claims = new HashMap<>();
		claims.put("scope", "read");

		assertThat(OAuth2UsernameClaims.pickClaimName(claims)).isNull();
		assertThat(OAuth2UsernameClaims.resolveUsername(claims)).isNull();
	}

	@Test
	void toleratesNullClaims() {
		assertThat(OAuth2UsernameClaims.pickClaimName(null)).isNull();
		assertThat(OAuth2UsernameClaims.resolveUsername(null)).isNull();
	}
}
