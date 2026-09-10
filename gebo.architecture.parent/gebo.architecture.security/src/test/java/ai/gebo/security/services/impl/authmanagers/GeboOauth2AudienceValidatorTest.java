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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * The audience gate that keeps a validly-signed token from another client of the
 * same issuer out of this resource server: accept only when the token's {@code aud}
 * or its {@code client_id} names an audience this registration declared.
 */
class GeboOauth2AudienceValidatorTest {

	private static final List<String> ACCEPTED = List.of("gebo-api");

	private static Jwt.Builder baseJwt() {
		return Jwt.withTokenValue("token").header("alg", "RS256").issuedAt(Instant.now())
				.expiresAt(Instant.now().plusSeconds(300)).subject("jane@example.com");
	}

	@Test
	void acceptsWhenAudMatches() {
		Jwt jwt = baseJwt().audience(List.of("gebo-api")).build();
		assertThat(new GeboOauth2AudienceValidator(ACCEPTED).validate(jwt).hasErrors()).isFalse();
	}

	@Test
	void acceptsWhenOneOfSeveralAudMatches() {
		Jwt jwt = baseJwt().audience(List.of("other-service", "gebo-api")).build();
		assertThat(new GeboOauth2AudienceValidator(ACCEPTED).validate(jwt).hasErrors()).isFalse();
	}

	@Test
	void acceptsCognitoAccessTokenViaClientId() {
		// Cognito access tokens carry no aud; the recipient is the client_id claim.
		Jwt jwt = baseJwt().claim("client_id", "gebo-api").build();
		assertThat(new GeboOauth2AudienceValidator(ACCEPTED).validate(jwt).hasErrors()).isFalse();
	}

	@Test
	void rejectsTokenForAnotherClient() {
		Jwt jwt = baseJwt().audience(List.of("some-other-app")).claim("client_id", "some-other-app").build();
		assertThat(new GeboOauth2AudienceValidator(ACCEPTED).validate(jwt).hasErrors()).isTrue();
	}

	@Test
	void rejectsTokenWithNoAudienceInformation() {
		Jwt jwt = baseJwt().build();
		assertThat(new GeboOauth2AudienceValidator(ACCEPTED).validate(jwt).hasErrors()).isTrue();
	}

	@Test
	void refusesToBeBuiltWithNoAcceptedAudiences() {
		// A caller with nothing to enforce must not install the validator at all,
		// rather than install one that accepts everything.
		assertThatThrownBy(() -> new GeboOauth2AudienceValidator(List.of()))
				.isInstanceOf(IllegalArgumentException.class);
	}
}
