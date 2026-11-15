/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.cluster.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The installation channel's token: accepted only as minted for it, a plain
 * JWT of five minutes signed with the shared secret.
 */
class GeboInstallationBootstrapTokenTest {

	/** jjwt base64-decodes the signing key and HS512 needs 512 bits: a long hex string. */
	private static final String SECRET = "a3f1c9e7b5d2046f8e1c3a5b7d9f0e2c4a6b8d0f1e3c5a7b9d1f3e5c7a9b0d2fa3f1c9e7b5d2046f8e1c3a5b7d9f0e2c4a6b8d0f1e3c5a7b9d1f3e5c7a9b0d2f";
	private static final String OTHER_SECRET = "b4e2d0f8c6a3157e9f2d4b6c8e0a1f3d5b7c9e1a2f4d6b8c0e2a3d5f7b9c1e3ab4e2d0f8c6a3157e9f2d4b6c8e0a1f3d5b7c9e1a2f4d6b8c0e2a3d5f7b9c1e3a";

	@Test
	void aMintedTokenIsAcceptedAndNamesTheCaller() {
		String header = GeboInstallationBootstrapToken.mint(SECRET, "brain_gebo_ai");

		assertThat(header).startsWith("Bearer ");
		assertThat(GeboInstallationBootstrapToken.verify(SECRET, header)).isEqualTo("brain_gebo_ai");
	}

	@Test
	void aTokenSignedWithAnotherSecretIsRefused() {
		String header = GeboInstallationBootstrapToken.mint(OTHER_SECRET, "brain_gebo_ai");

		assertThatThrownBy(() -> GeboInstallationBootstrapToken.verify(SECRET, header))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void aMissingHeaderIsRefused() {
		assertThatThrownBy(() -> GeboInstallationBootstrapToken.verify(SECRET, null))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void aTokenLivingLongerThanFiveMinutesIsRefused() {
		Date now = new Date();
		String longLived = Jwts.builder().setSubject("brain_gebo_ai").setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + 3_600_000L)).signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();

		assertThatThrownBy(() -> GeboInstallationBootstrapToken.verify(SECRET, "Bearer " + longLived))
				.isInstanceOf(IllegalArgumentException.class).hasMessageContaining("longer");
	}

	@Test
	void aPlatformTokenIsRefusedOnThisChannel() {
		GeboSecurityConfig config = new GeboSecurityConfig();
		config.getAuth().setTokenSecret(SECRET);
		config.getAuth().setTokenExpirationMsec(120_000L);
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(config, List.of(), "install-A@host-a");
		// a platform token of four minutes: right lifetime, wrong shape
		String platform = provider.createToken("someone@gebo.ai", new Date(System.currentTimeMillis() + 240_000L));

		assertThatThrownBy(() -> GeboInstallationBootstrapToken.verify(SECRET, "Bearer " + platform))
				.isInstanceOf(IllegalArgumentException.class).hasMessageContaining("platform");
	}

	@Test
	void theChannelTokenIsRefusedByThePlatform() {
		GeboSecurityConfig config = new GeboSecurityConfig();
		config.getAuth().setTokenSecret(SECRET);
		config.getAuth().setTokenExpirationMsec(120_000L);
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(config, List.of(), "install-A@host-a");
		String jwt = GeboInstallationBootstrapToken.mint(SECRET, "brain_gebo_ai").substring("Bearer ".length());

		assertThat(provider.validateToken(jwt)).as("no id, no installation id: not a platform token").isFalse();
	}
}
