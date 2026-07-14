/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.UserPrincipal;

/**
 * The platform's own identity: it must be usable as an authenticated admin, and
 * unusable as a login.
 *
 * Gebo.ai comment agent
 */
class GeboSystemUserServiceImplTest {

	/**
	 * jjwt base64-decodes the configured secret, and HS512 demands the result be at
	 * least 512 bits - so these are 128-char strings, like a real deployment's.
	 */
	private static final String TEST_SECRET = "04ca023b39512e46d0c2cf4b48d5aac61d34302994c87ed4eff225dcf3b0a218"
			+ "739f3897051a057f9b846a69ea2927a587044164b7bae5e1306219d50b588cb1";
	private static final String OTHER_SECRET = "b7bae5e1306219d50b588cb1739f3897051a057f9b846a69ea2927a587044164"
			+ "d0c2cf4b48d5aac61d34302994c87ed4eff225dcf3b0a21804ca023b39512e46";

	private GeboSecurityConfig securityConfig;
	private PasswordEncoder passwordEncoder;
	private GeboSystemUserServiceImpl systemUserService;

	@BeforeEach
	void setUp() {
		securityConfig = new GeboSecurityConfig();
		// The signing key is base64-decoded by jjwt, so it must be valid base64 - a hex
		// string, as the real deployments configure.
		securityConfig.getAuth().setTokenSecret(TEST_SECRET);
		securityConfig.getAuth().setTokenExpirationMsec(120000L);
		passwordEncoder = new BCryptPasswordEncoder();
		systemUserService = new GeboSystemUserServiceImpl(securityConfig,
				new LocalJwtTokenProvider(securityConfig), passwordEncoder);
	}

	@Test
	void defaultsToTheHeimdallIdentityWithSystemAndAdminRoles() {
		assertThat(systemUserService.getUsername()).isEqualTo("heimdall@bifrost.gebo.ai");
		assertThat(systemUserService.getRoles()).containsExactlyInAnyOrder("SYSTEM", "ADMIN");
	}

	@Test
	void theUsernameIsConfigurable() {
		securityConfig.getSystemUser().setUsername("robot@acme.example");

		assertThat(systemUserService.getUsername()).isEqualTo("robot@acme.example");
		assertThat(systemUserService.isSystemUser("robot@acme.example")).isTrue();
		assertThat(systemUserService.isSystemUser("heimdall@bifrost.gebo.ai")).isFalse();
	}

	/** The login paths lower-case what they are given, so the match must not be case-sensitive. */
	@Test
	void recognisesItsUsernameRegardlessOfCase() {
		assertThat(systemUserService.isSystemUser("HEIMDALL@BIFROST.GEBO.AI")).isTrue();
		assertThat(systemUserService.isSystemUser("  heimdall@bifrost.gebo.ai  ")).isTrue();
	}

	@Test
	void doesNotMistakeAnotherUserForItself() {
		assertThat(systemUserService.isSystemUser("paolo@gebo.ai")).isFalse();
		assertThat(systemUserService.isSystemUser(null)).isFalse();
		assertThat(systemUserService.isSystemUser("")).isFalse();
	}

	/**
	 * The whole reason ADMIN is in the default role set: the platform runs
	 * GrantedAuthorityDefaults("") - no ROLE_ prefix - so an authority literally named
	 * ADMIN is what the ~90 @PreAuthorize("hasRole('ADMIN')") endpoints test for.
	 */
	@Test
	void grantsAuthoritiesNamedExactlyAfterTheRoles() {
		UserPrincipal principal = systemUserService.getUserPrincipal();

		assertThat(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority))
				.containsExactlyInAnyOrder("SYSTEM", "ADMIN");
	}

	@Test
	void presentsAsAnEnabledLocalUser() {
		UserPrincipal principal = systemUserService.getUserPrincipal();

		assertThat(principal.getUsername()).isEqualTo("heimdall@bifrost.gebo.ai");
		assertThat(principal.isEnabled()).isTrue();
		assertThat(systemUserService.getUser().getDisabled()).isFalse();
		// LOCAL_JWT only - never federated.
		assertThat(systemUserService.getUser().getProvider()).isEqualTo(AuthProvider.local);
		assertThat(systemUserService.getUserInfos().getRoles()).contains("ADMIN");
		assertThat(systemUserService.getUserInfos().getDisabled()).isFalse();
	}

	/**
	 * It cannot log in. The password is a random value whose plaintext was discarded
	 * at construction, so no presented password can ever match it - not even an empty
	 * or null one, and not the username, which is the obvious guess.
	 */
	@Test
	void cannotBeLoggedIntoWithAnyPassword() {
		String encoded = systemUserService.getUserPrincipal().getPassword();
		assertThat(encoded).isNotBlank();

		for (String guess : List.of("", " ", "password", "heimdall@bifrost.gebo.ai", "SYSTEM", "ADMIN")) {
			assertThat(passwordEncoder.matches(guess, encoded))
					.as("password '%s' must not authenticate the system user", guess)
					.isFalse();
		}
	}

	/** Two instances must not share a password, so one process's hash tells you nothing about another's. */
	@Test
	void mintsADistinctUnusablePasswordPerInstance() {
		GeboSystemUserServiceImpl other = new GeboSystemUserServiceImpl(securityConfig,
				new LocalJwtTokenProvider(securityConfig), passwordEncoder);

		assertThat(other.getUserPrincipal().getPassword())
				.isNotEqualTo(systemUserService.getUserPrincipal().getPassword());
	}

	/**
	 * The fallback for "no token to propagate": a real, signed LOCAL_JWT whose subject
	 * is the system identity, validated by the ordinary path - not a bypass.
	 */
	@Test
	void mintsAValidLocalJwtForItself() {
		LocalJwtTokenProvider tokenProvider = new LocalJwtTokenProvider(securityConfig);

		String token = systemUserService.createToken();

		assertThat(token).isNotBlank();
		assertThat(tokenProvider.validateToken(token)).isTrue();
		assertThat(tokenProvider.getUserIdFromToken(token)).isEqualTo("heimdall@bifrost.gebo.ai");
	}

	@Test
	void aTokenSignedWithAnotherSecretIsRejected() {
		GeboSecurityConfig foreign = new GeboSecurityConfig();
		foreign.getAuth().setTokenSecret(OTHER_SECRET);
		foreign.getAuth().setTokenExpirationMsec(120000L);
		String forgedToken = new GeboSystemUserServiceImpl(foreign, new LocalJwtTokenProvider(foreign),
				passwordEncoder).createToken();

		// Our provider must not accept a system token minted outside our trust domain.
		assertThat(new LocalJwtTokenProvider(securityConfig).validateToken(forgedToken)).isFalse();
	}
}
