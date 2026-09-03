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
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.fastsetup.system.configuration.SystemInitializationAdminConfiguration;
import ai.gebo.architecture.fastsetup.system.services.SystemInitializationAdminService;
import ai.gebo.knlowledgebase.model.licence.GeboLicence;
import ai.gebo.knowledgebase.repositories.GeboLicenceRepository;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.User;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.IGUserPasswordService;
import ai.gebo.security.services.impl.CustomUserDetailsService;

/**
 * The <b>declarative</b> way an installation gets its first admin: the
 * {@code ai.gebo.sysinit.admin.config} block of {@code application.yml}, applied by
 * {@link SystemInitializationAdminService} on a one-shot scheduled tick shortly
 * after startup.
 *
 * <h2>Why this needs its own test</h2>
 * <p>
 * The other way in - the browser setup wizard - goes through
 * {@code IGUsersAdminService.insertUser}, which is the chokepoint where the
 * password-as-a-secret behaviour lives, so it inherited the change for free and is
 * covered wherever a suite provisions its admin. This path does <b>not</b> go
 * through that chokepoint: it writes the user document itself, and it was the only
 * place outside the security modules that used to set a password on it directly.
 * It is therefore the one place where the two stores could silently drift apart -
 * a user row written here with its password left behind in the old field would be
 * an admin account nobody could sign in as, on an installation whose <i>only</i>
 * account it is.
 * </p>
 *
 * <h2>Why the tick is called rather than waited for</h2>
 * <p>
 * The properties are set for real, so the {@code ai.gebo.sysinit.admin.config}
 * prefix and its binding are genuinely exercised - but the scheduled trigger is a
 * one-shot 20 seconds after context refresh, which is neither observable nor
 * controllable from a test. {@link SystemInitializationAdminService#onTick()} is
 * therefore invoked directly, on the real bean with the real bound configuration.
 * The scheduled firing may or may not have already happened by then; every test
 * here establishes the precondition it needs first and asserts an end state that is
 * the same either way, so it cannot race.
 * </p>
 *
 * Gebo.ai comment agent
 */
@TestPropertySource(properties = {
		"ai.gebo.sysinit.admin.config.adminUsername=" + SystemInitializationAdminIntegrationTest.ADMIN_USERNAME,
		"ai.gebo.sysinit.admin.config.adminPassword=" + SystemInitializationAdminIntegrationTest.ADMIN_PASSWORD })
public class SystemInitializationAdminIntegrationTest extends AbstractBaseIntegrationTest {

	static final String ADMIN_USERNAME = "sysinit-admin@gebo.ai";
	static final String ADMIN_PASSWORD = "TheConfiguredAdminPassword1!";

	private static final String LEGACY_PASSWORD_FIELD = "password";

	@Autowired
	private SystemInitializationAdminService systemInitializationAdminService;

	@Autowired
	private SystemInitializationAdminConfiguration adminConfiguration;

	@Autowired
	private GeboLicenceRepository licenceRepository;

	@Autowired
	private IGUserPasswordService userPasswordService;

	@Autowired
	private IGSecurityDirectory securityDirectory;

	@Autowired
	private IGeboSecretsAccessService secretsAccessService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * The property contract itself. Spelled exactly as an operator writes it in
	 * {@code application.yml} - a renamed prefix or a field this stopped binding to
	 * would leave the silent registration quietly doing nothing, with no error
	 * anywhere.
	 */
	@Test
	public void bindsTheApplicationYmlAdminBlock() {
		assertNotNull(adminConfiguration, "ai.gebo.sysinit.admin.config must bind to a configuration bean");
		assertEquals(ADMIN_USERNAME, adminConfiguration.getAdminUsername());
		assertEquals(ADMIN_PASSWORD, adminConfiguration.getAdminPassword());
	}

	/**
	 * The whole point: the configured admin is created, its password goes to the
	 * secret store, the user document carries none, and the account can actually
	 * authenticate.
	 */
	@Test
	public void registersTheConfiguredAdminWithItsPasswordInTheSecretStore() throws Exception {
		givenAFreshInstallation();

		systemInitializationAdminService.onTick();

		Optional<User> created = userRepository.findById(ADMIN_USERNAME);
		assertTrue(created.isPresent(), "the configured admin must be created");
		assertEquals(AuthProvider.local, created.get().getProvider());
		assertEquals(List.of("USER", "ADMIN"), created.get().getRoles());
		assertFalse(created.get().getDisabled());

		assertFalse(rawUserDocument(ADMIN_USERNAME).containsKey(LEGACY_PASSWORD_FIELD),
				"the admin's document must not carry a password field");

		SecretInfo secret = passwordSecretOf(ADMIN_USERNAME);
		assertNotNull(secret, "the admin's password must be a secret");
		assertEquals("user:" + ADMIN_USERNAME, secret.getContextCode());
		assertEquals(ADMIN_PASSWORD, userPasswordService.findRawPassword(ADMIN_USERNAME));
	}

	/**
	 * An admin created this way must be able to log in - the failure that would
	 * otherwise land on an installation with exactly one account and no other way in.
	 */
	@Test
	public void theConfiguredAdminCanAuthenticate() throws Exception {
		givenAFreshInstallation();

		systemInitializationAdminService.onTick();

		assertTrue(securityDirectory.checkPassword(ADMIN_USERNAME, ADMIN_PASSWORD));
		assertFalse(securityDirectory.checkPassword(ADMIN_USERNAME, "TheWrongPassword1!"));

		// The shape DaoAuthenticationProvider actually compares on the login path.
		UserDetails details = customUserDetailsService.loadUserByUsername(ADMIN_USERNAME);
		assertTrue(passwordEncoder.matches(ADMIN_PASSWORD, details.getPassword()));
		assertFalse(passwordEncoder.matches("TheWrongPassword1!", details.getPassword()));
	}

	/** The silent registration also signs the licence, which is half of what marks the install as done. */
	@Test
	public void recordsTheSilentRegistrationLicence() {
		givenAFreshInstallation();

		systemInitializationAdminService.onTick();

		Optional<GeboLicence> licence = licenceRepository.findById("ConfigLicence");
		assertTrue(licence.isPresent(), "the silent registration must record its licence");
		assertEquals(ADMIN_USERNAME, licence.get().getSignerUser());
	}

	/**
	 * It must never touch an installation that already has users - the guard that
	 * stops a configured password from being (re)applied to a running system.
	 */
	@Test
	public void leavesAnAlreadyInitialisedInstallationAlone() throws Exception {
		// The default user from prepareEnvironment() is deliberately left in place: it is
		// what makes this an already-initialised installation.
		licenceRepository.deleteAll();
		userRepository.deleteById(ADMIN_USERNAME);
		userPasswordService.deletePassword(ADMIN_USERNAME);

		systemInitializationAdminService.onTick();

		assertTrue(userRepository.findById(ADMIN_USERNAME).isEmpty(),
				"an installation that already has users must not gain the configured admin");
		assertNull(passwordSecretOf(ADMIN_USERNAME), "and no password secret must be written for it");
	}

	/**
	 * Empties the two collections the service's own guard reads, and clears any
	 * password secret left by an earlier test or by the scheduled firing - secrets
	 * live outside the user store, so {@code cleanAllDb()} does not reach them.
	 */
	private void givenAFreshInstallation() {
		userRepository.deleteAll();
		licenceRepository.deleteAll();
		try {
			userPasswordService.deletePassword(ADMIN_USERNAME);
		} catch (Exception e) {
			throw new IllegalStateException("Cannot clear the admin password secret before the test", e);
		}
	}

	private org.bson.Document rawUserDocument(String username) {
		org.bson.Document document = mongoTemplate.findOne(new Query(Criteria.where("_id").is(username)),
				org.bson.Document.class, mongoTemplate.getCollectionName(User.class));
		assertNotNull(document, "no user document for " + username);
		return document;
	}

	private SecretInfo passwordSecretOf(String username) throws Exception {
		List<SecretInfo> found = secretsAccessService
				.getSecretInfoByContextCode(IGUserPasswordService.contextCodeOf(username)).stream()
				.filter(i -> i.getSecretType() == GeboSecretType.USERNAME_PASSWORD).toList();
		return found.isEmpty() ? null : found.get(0);
	}
}
