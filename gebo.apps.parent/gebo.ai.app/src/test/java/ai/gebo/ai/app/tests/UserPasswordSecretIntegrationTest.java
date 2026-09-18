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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.directory.mongo.UserPasswordSecretMigration;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.User;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.IGUserPasswordService;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.impl.CustomUserDetailsService;

/**
 * The password of a local user lives in the secret store, not in its user
 * document - end to end, against the real Mongo and the real secrets service.
 *
 * <p>
 * Two things are worth testing here rather than in a unit test, because both are
 * about how the pieces meet. First, that a login still works: the password travels
 * from {@code IGUsersAdminService} into a secret, back out through
 * {@code CustomUserDetailsService} and through {@code GPasswordEncoder} in the form
 * {@code DaoAuthenticationProvider} compares - four components that each hold up
 * fine on their own and could still fail to agree on what "the encoded password"
 * means. Second, that {@link UserPasswordSecretMigration} actually drains a
 * <b>real</b> legacy document, which needs a document shaped the way the old code
 * wrote it - something no mapped write can produce any more, since {@link User}
 * stopped declaring the field.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class UserPasswordSecretIntegrationTest extends AbstractBaseIntegrationTest {

	private static final String LEGACY_PASSWORD_FIELD = "password";

	@Autowired
	private IGUserPasswordService userPasswordService;

	@Autowired
	private IGUsersAdminService usersAdminService;

	@Autowired
	private IGSecurityDirectory securityDirectory;

	@Autowired
	private IGeboSecretsAccessService secretsAccessService;

	@Autowired
	private IGeboCryptingService cryptService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserPasswordSecretMigration migration;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * The whole point of the change: creating a user must leave no password behind in
	 * the user document, and put one in the secret store instead.
	 */
	@Test
	public void aNewUsersPasswordIsASecretAndNotAFieldOfItsDocument() throws Exception {
		String username = "pwd-new@gebo.ai";
		usersAdminService.insertUser(editableUser(username), "TheFirstPassword1!");

		assertFalse(rawUserDocument(username).containsKey(LEGACY_PASSWORD_FIELD),
				"the user document must not carry a password field");

		SecretInfo info = passwordSecretOf(username);
		assertNotNull(info, "a USERNAME_PASSWORD secret must exist for the user");
		assertEquals("user:" + username, info.getContextCode());
		assertEquals("TheFirstPassword1!", userPasswordService.findRawPassword(username));
	}

	/** And the credential still authenticates, through the directory and through the login path. */
	@Test
	public void aStoredPasswordStillAuthenticates() throws Exception {
		String username = "pwd-login@gebo.ai";
		usersAdminService.insertUser(editableUser(username), "TheFirstPassword1!");

		assertTrue(securityDirectory.checkPassword(username, "TheFirstPassword1!"));
		assertFalse(securityDirectory.checkPassword(username, "TheWrongPassword1!"));

		// The shape DaoAuthenticationProvider actually compares: whatever
		// CustomUserDetailsService hands back must satisfy the PasswordEncoder it is
		// paired with.
		UserDetails details = customUserDetailsService.loadUserByUsername(username);
		assertTrue(passwordEncoder.matches("TheFirstPassword1!", details.getPassword()));
		assertFalse(passwordEncoder.matches("TheWrongPassword1!", details.getPassword()));
	}

	/** A change replaces the one secret; the old password stops working immediately. */
	@Test
	public void changingThePasswordReplacesTheSecret() throws Exception {
		String username = "pwd-change@gebo.ai";
		usersAdminService.insertUser(editableUser(username), "TheFirstPassword1!");
		String originalCode = passwordSecretOf(username).getCode();

		usersAdminService.changePassword(username, "TheSecondPassword2!");

		assertEquals(1, passwordSecretsOf(username).size(), "a password change must not add a second secret");
		assertEquals(originalCode, passwordSecretOf(username).getCode());
		assertTrue(securityDirectory.checkPassword(username, "TheSecondPassword2!"));
		assertFalse(securityDirectory.checkPassword(username, "TheFirstPassword1!"));
	}

	/** A deleted user must not leave a live credential behind in the secret store. */
	@Test
	public void deletingAUserRemovesItsPasswordSecret() throws Exception {
		String username = "pwd-delete@gebo.ai";
		usersAdminService.insertUser(editableUser(username), "TheFirstPassword1!");
		assertNotNull(passwordSecretOf(username));

		usersAdminService.deleteUser(editableUser(username));

		assertTrue(passwordSecretsOf(username).isEmpty(), "the password secret must go with the user");
		assertFalse(userPasswordService.hasPassword(username));
	}

	/**
	 * The upgrade path of an installation that is already deployed: a document written
	 * by the old code, with the password encrypted in place, must come out with the
	 * credential moved to the secret store and the field gone.
	 */
	@Test
	public void migratesALegacyUserDocument() throws Exception {
		String username = "pwd-legacy@gebo.ai";
		insertLegacyUser(username, "TheLegacyPassword1!");

		// Before: the field is there and nothing can authenticate.
		assertTrue(rawUserDocument(username).containsKey(LEGACY_PASSWORD_FIELD));
		assertFalse(securityDirectory.checkPassword(username, "TheLegacyPassword1!"));

		assertEquals(1, migration.migrate());

		assertFalse(rawUserDocument(username).containsKey(LEGACY_PASSWORD_FIELD),
				"the legacy password field must be removed from the document");
		assertEquals("TheLegacyPassword1!", userPasswordService.findRawPassword(username));
		assertTrue(securityDirectory.checkPassword(username, "TheLegacyPassword1!"),
				"the migrated user must be able to log in with its original password");
	}

	/** Running it again must find nothing and change nothing - it runs on every start. */
	@Test
	public void theMigrationIsIdempotent() throws Exception {
		String username = "pwd-legacy-twice@gebo.ai";
		insertLegacyUser(username, "TheLegacyPassword1!");

		assertEquals(1, migration.migrate());
		String codeAfterFirstPass = passwordSecretOf(username).getCode();

		assertEquals(0, migration.migrate(), "a second pass has nothing left to migrate");

		assertEquals(1, passwordSecretsOf(username).size());
		assertEquals(codeAfterFirstPass, passwordSecretOf(username).getCode());
		assertTrue(securityDirectory.checkPassword(username, "TheLegacyPassword1!"));
	}

	/**
	 * A federated account written by the old code has the field but no value. There is
	 * no credential to carry over, and the dead field still has to go.
	 */
	@Test
	public void migrationClearsAnEmptyLegacyFieldWithoutCreatingASecret() throws Exception {
		String username = "pwd-legacy-empty@gebo.ai";
		insertLegacyUser(username, null);

		assertEquals(0, migration.migrate(), "an empty field is cleared, not counted as migrated");

		assertFalse(rawUserDocument(username).containsKey(LEGACY_PASSWORD_FIELD));
		assertTrue(passwordSecretsOf(username).isEmpty());
		assertFalse(securityDirectory.checkPassword(username, ""));
	}

	/**
	 * A value the crypting service cannot read is left where it is: unsetting it would
	 * destroy the only copy of a credential a corrected keystore might still recover.
	 */
	@Test
	public void migrationLeavesAnUndecryptableValueInPlace() throws Exception {
		String username = "pwd-legacy-broken@gebo.ai";
		insertLegacyUserWithRawField(username, "not-something-this-keystore-can-decrypt");

		assertEquals(0, migration.migrate());

		assertTrue(rawUserDocument(username).containsKey(LEGACY_PASSWORD_FIELD),
				"an undecryptable legacy password must be kept for a later attempt");
		assertTrue(passwordSecretsOf(username).isEmpty());
	}

	/** A federated identity has no password secret, and that is not an error anywhere. */
	@Test
	public void aUserWithoutAPasswordIsNotAnError() throws Exception {
		String username = "pwd-federated@gebo.ai";
		User user = new User();
		user.setUsername(username);
		user.setProvider(AuthProvider.local);
		user.setRoles(List.of("USER"));
		user.setDisabled(false);
		userRepository.insert(user);

		assertNull(userPasswordService.findRawPassword(username));
		assertFalse(userPasswordService.hasPassword(username));
		assertFalse(securityDirectory.checkPassword(username, "anything"));
		// It still loads as a principal - it just cannot pass a password check.
		UserDetails details = customUserDetailsService.loadUserByUsername(username);
		assertNotNull(details.getPassword(), "an unusable password, not a null one");
		assertFalse(passwordEncoder.matches("anything", details.getPassword()));
	}

	private EditableUser editableUser(String username) {
		EditableUser user = new EditableUser();
		user.setUsername(username);
		user.setAuthProvider(AuthProvider.local);
		user.setRoles(List.of("USER"));
		user.setDisabled(false);
		return user;
	}

	/**
	 * Writes a user document the way the pre-migration code did: the mapped entity has
	 * no such field any more, so it has to be added to the raw document.
	 */
	private void insertLegacyUser(String username, String rawPassword) throws Exception {
		insertLegacyUserWithRawField(username, rawPassword == null ? null : cryptService.crypt(rawPassword));
	}

	private void insertLegacyUserWithRawField(String username, String storedValue) {
		User user = new User();
		user.setUsername(username);
		user.setProvider(AuthProvider.local);
		user.setRoles(List.of("USER"));
		user.setDisabled(false);
		userRepository.insert(user);
		mongoTemplate.getCollection(userCollection()).updateOne(new org.bson.Document("_id", username),
				new org.bson.Document("$set", new org.bson.Document(LEGACY_PASSWORD_FIELD, storedValue)));
	}

	private String userCollection() {
		return mongoTemplate.getCollectionName(User.class);
	}

	private org.bson.Document rawUserDocument(String username) {
		org.bson.Document document = mongoTemplate.findOne(new Query(Criteria.where("_id").is(username)),
				org.bson.Document.class, userCollection());
		assertNotNull(document, "no user document for " + username);
		return document;
	}

	private List<SecretInfo> passwordSecretsOf(String username) throws Exception {
		return secretsAccessService.getSecretInfoByContextCode(IGUserPasswordService.contextCodeOf(username)).stream()
				.filter(i -> i.getSecretType() == GeboSecretType.USERNAME_PASSWORD).toList();
	}

	private SecretInfo passwordSecretOf(String username) throws Exception {
		List<SecretInfo> found = passwordSecretsOf(username);
		return found.isEmpty() ? null : found.get(0);
	}
}
