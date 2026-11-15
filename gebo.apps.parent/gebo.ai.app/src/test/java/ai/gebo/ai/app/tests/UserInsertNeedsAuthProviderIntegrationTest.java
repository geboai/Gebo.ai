/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.controller.UsersAdminController.InsertUserParam;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.services.IGUserPasswordService;
import ai.gebo.security.services.IGUsersAdminService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * A user is created with an authentication provider, or not at all: without one it
 * could never sign in, and the admin operations that validate the user they are
 * given (update, delete) would refuse it as stored.
 */
public class UserInsertNeedsAuthProviderIntegrationTest extends AbstractBaseIntegrationTest {
	@Autowired
	private IGUsersAdminService usersAdminService;
	@Autowired
	private IGeboSecretsAccessService secretsAccessService;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private Validator validator;

	/** Refused before anything is written: no user document, no password secret. */
	@Test
	public void aUserWithNoAuthenticationProviderIsRefusedAndLeavesNothingBehind() throws Exception {
		String username = "no-provider@gebo.ai";
		EditableUser user = editableUser(username);
		user.setAuthProvider(null);

		IllegalStateException refused = assertThrows(IllegalStateException.class,
				() -> usersAdminService.insertUser(user, "TheFirstPassword1!"));
		assertEquals("A user needs an authentication provider", refused.getMessage());
		assertNull(mongoTemplate.findOne(new Query(Criteria.where("_id").is(username)), org.bson.Document.class,
				mongoTemplate.getCollectionName(User.class)), "no user document");
		assertTrue(secretsAccessService.getSecretInfoByContextCode(IGUserPasswordService.contextCodeOf(username))
				.isEmpty(), "no password secret");
	}

	/** The same user with its provider goes in, as the admin UI sends it. */
	@Test
	public void aLocalUserIsCreated() throws Exception {
		String username = "with-provider@gebo.ai";
		usersAdminService.insertUser(editableUser(username), "TheFirstPassword1!");
		assertNotNull(usersAdminService.findUserByUsername(username));
		assertEquals(AuthProvider.local, usersAdminService.findUserByUsername(username).getAuthProvider());
	}

	/**
	 * The admin endpoint's body checks the user itself, as updateUser and deleteUser
	 * do: the request is refused (400) before the service is called.
	 */
	@Test
	public void theAdminInsertRequestChecksTheUserItCarries() {
		InsertUserParam request = new InsertUserParam();
		request.user = editableUser("request@gebo.ai");
		request.password = "TheFirstPassword1!";
		assertTrue(validator.validate(request).isEmpty(), String.valueOf(validator.validate(request)));

		request.user.setAuthProvider(null);
		Set<ConstraintViolation<InsertUserParam>> violations = validator.validate(request);
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("user.authProvider")),
				String.valueOf(violations));
	}

	private EditableUser editableUser(String username) {
		EditableUser user = new EditableUser();
		user.setUsername(username);
		user.setName("Test");
		user.setSourname("User");
		user.setAuthProvider(AuthProvider.local);
		user.setRoles(List.of("USER"));
		user.setDisabled(false);
		return user;
	}
}
