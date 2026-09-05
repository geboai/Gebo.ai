/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * The two properties that make {@code EditableUser.customInfos} usable as the
 * only supported way to write a user's custom infos: it survives the
 * read-modify-write every caller of {@code IGUsersAdminService} performs, and a
 * caller that knows nothing about it cannot erase what another one wrote.
 */
class UserCustomInfosTest {

	private static User storedUser(Map<String, Object> customInfos) {
		User user = new User();
		user.setUsername("someone@example.com");
		user.setName("Some");
		user.setSourname("One");
		user.setRoles(List.of("USER"));
		user.setDisabled(false);
		user.setProvider(AuthProvider.local);
		user.setCustomInfos(customInfos);
		return user;
	}

	@Test
	void survivesTheReadModifyWriteRoundTrip() {
		User stored = storedUser(Map.of("oauth2User", Map.of("provider", "keycloak")));

		// What findUserByUsername() hands a caller, and what updateUser() does with it.
		EditableUser read = new EditableUser(stored);
		User reloaded = storedUser(Map.of("oauth2User", Map.of("provider", "keycloak")));
		reloaded.assignValues(read);

		assertThat(read.getCustomInfos()).isEqualTo(stored.getCustomInfos());
		assertThat(reloaded.getCustomInfos()).isEqualTo(stored.getCustomInfos());
	}

	@Test
	void aCallerThatNeverSetItCannotEraseIt() {
		User stored = storedUser(Map.of("oauth2User", Map.of("provider", "keycloak")));

		// An admin-UI style update: a complete user posted by a client that has never
		// heard of custom infos, so the field arrives null.
		EditableUser posted = new EditableUser();
		posted.setUsername(stored.getUsername());
		posted.setName("Renamed");
		posted.setSourname(stored.getSourname());
		posted.setRoles(List.of("USER", "ADMIN"));
		posted.setDisabled(false);
		posted.setAuthProvider(AuthProvider.local);
		assertThat(posted.getCustomInfos()).isNull();

		stored.assignValues(posted);

		assertThat(stored.getName()).as("the fields the caller did set are applied").isEqualTo("Renamed");
		assertThat(stored.getRoles()).containsExactly("USER", "ADMIN");
		assertThat(stored.getCustomInfos()).as("the ones it did not are left alone")
				.isEqualTo(Map.of("oauth2User", Map.of("provider", "keycloak")));
	}

	@Test
	void aCallerThatSetsItWins() {
		User stored = storedUser(Map.of("oauth2User", Map.of("provider", "keycloak")));

		EditableUser update = new EditableUser(stored);
		update.setCustomInfos(Map.of("oauth2User", Map.of("provider", "microsoft")));
		stored.assignValues(update);

		assertThat(stored.getCustomInfos()).isEqualTo(Map.of("oauth2User", Map.of("provider", "microsoft")));
	}
}
