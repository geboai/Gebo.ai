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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.GeboLoginPolicy;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.impl.authmanagers.GOauth2ResourceServerUserProvisioner;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGUserPasswordService;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.SecurityAuditConstraints;
import ai.gebo.security.services.SecurityAuditTaxonomy;

/**
 * Integration test for the security audit logging pipeline: verifies that
 * {@link IGSecurityAuditLoggerService} pulls request context from MDC (fed by
 * {@code RequestAuditFilter} on real requests, set manually here since these
 * tests call service beans directly rather than over HTTP), serializes
 * events as JSON, and routes them exclusively through the dedicated
 * "security-log" SLF4J logger - the one every executable module's logback
 * config wires to a separate append-only, Wazuh-compatible file. It also
 * exercises a real audited call path (secret create/update) to confirm the
 * instrumentation added to business services actually reaches the logger,
 * for both the success and failure outcome.
 */
public class SecurityAuditLoggingIntegrationTest extends AbstractBaseIntegrationTest {

	private static final String CONTEXT_CODE = "security-audit-log-test";
	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private IGSecurityAuditLoggerService securityAuditLoggerService;

	@Autowired
	private IGUsersAdminService usersAdminService;

	@Autowired
	private IGUserPasswordService userPasswordService;

	@Autowired
	private GOauth2ResourceServerUserProvisioner resourceServerUserProvisioner;

	@Autowired
	private GeboSecurityConfig geboSecurityConfig;

	private Logger securityLogger;
	private ListAppender<ILoggingEvent> listAppender;

	@BeforeEach
	public void attachSecurityLogAppender() {
		securityLogger = (Logger) LoggerFactory.getLogger(SecurityAuditConstraints.SECURITY_LOG);
		listAppender = new ListAppender<>();
		listAppender.start();
		securityLogger.addAppender(listAppender);
	}

	@AfterEach
	public void detachSecurityLogAppender() {
		if (securityLogger != null && listAppender != null) {
			securityLogger.detachAppender(listAppender);
		}
	}

	/**
	 * The logged events carrying one taxonomy action.
	 *
	 * <p>
	 * {@link #singleLoggedEventAsJson()} cannot be used for the user-administration
	 * paths: they are audited at several tiers on purpose (the users-admin service, the
	 * password service below it, and the secret store below that), so one call
	 * legitimately produces several events and the assertion has to name which one it
	 * is about.
	 * </p>
	 */
	private List<JsonNode> loggedEventsWithAction(String action) {
		List<JsonNode> out = new ArrayList<>();
		for (ILoggingEvent logged : listAppender.list) {
			JsonNode node = mapper.readTree(logged.getFormattedMessage());
			if (node.has("action") && action.equals(node.get("action").asText()))
				out.add(node);
		}
		return out;
	}

	private JsonNode singleLoggedEventWithAction(String action) {
		List<JsonNode> found = loggedEventsWithAction(action);
		assertEquals(1, found.size(), "Exactly one \"" + action + "\" event should have been logged");
		return found.get(0);
	}

	private JsonNode singleLoggedEventAsJson() {
		List<ILoggingEvent> events = listAppender.list;
		assertEquals(1, events.size(), "Exactly one security event should have been logged on \""
				+ SecurityAuditConstraints.SECURITY_LOG + "\"");
		String json = events.get(0).getFormattedMessage();
		JsonNode node = mapper.readTree(json);
		assertNotNull(node, "The logged message must be valid JSON (required for Wazuh's json log_format)");
		return node;
	}

	/**
	 * newSecurityEvent() is meant to be called from inside a real HTTP request,
	 * where RequestAuditFilter has already populated MDC. Outside of that
	 * (calling a service bean directly, as this test does), we populate MDC
	 * ourselves to verify the propagation logic in isolation.
	 */
	@Test
	public void newSecurityEventCapturesRequestContextFromMdc() {
		MDC.put(SecurityAuditConstraints.CORRELATION_ID, "test-correlation-id");
		MDC.put(SecurityAuditConstraints.CLIENT_IP, "203.0.113.7");
		MDC.put(SecurityAuditConstraints.USERID, "mdc-test-user");
		MDC.put(SecurityAuditConstraints.HTTP_METHOD, "POST");
		MDC.put(SecurityAuditConstraints.REQUEST_URI, "/api/test/endpoint");
		try {
			SecurityEvent event = securityAuditLoggerService.newSecurityEvent();

			assertEquals("test-correlation-id", event.getCorrelationId());
			assertEquals("203.0.113.7", event.getSourceIp());
			assertEquals("mdc-test-user", event.getUserId());
			assertEquals("POST", event.getHttpMethod());
			assertEquals("/api/test/endpoint", event.getRequestUri());
			assertNotNull(event.getArchitectureType(), "architectureType must be populated");
			assertNotNull(event.getApplication(), "application must be populated");
			assertNotNull(event.getTimestamp(), "timestamp must be populated");
			assertNotNull(event.getStackPoint(), "stackPoint (caller trace) must be populated");
		} finally {
			MDC.remove(SecurityAuditConstraints.CORRELATION_ID);
			MDC.remove(SecurityAuditConstraints.CLIENT_IP);
			MDC.remove(SecurityAuditConstraints.USERID);
			MDC.remove(SecurityAuditConstraints.HTTP_METHOD);
			MDC.remove(SecurityAuditConstraints.REQUEST_URI);
		}
	}

	@Test
	public void loggedEventIsSerializedAsJsonOnTheDedicatedSecurityLogger() {
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.SESSION_MANAGEMENT);
		event.setCategory(SecurityAuditTaxonomy.Category.SESSION_MANAGEMENT);
		event.setAction(SecurityAuditTaxonomy.Action.SESSION_TOKEN_RENEW);
		event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
		event.setResourceId("test-resource-001");

		securityAuditLoggerService.log(event);

		JsonNode node = singleLoggedEventAsJson();
		assertEquals(SecurityAuditTaxonomy.EventType.SESSION_MANAGEMENT, node.get("eventType").asText());
		assertEquals(SecurityAuditTaxonomy.Category.SESSION_MANAGEMENT, node.get("category").asText());
		assertEquals(SecurityAuditTaxonomy.Action.SESSION_TOKEN_RENEW, node.get("action").asText());
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, node.get("outcome").asText());
		assertEquals("test-resource-001", node.get("resourceId").asText());
		assertTrue(node.has("timestamp") && !node.get("timestamp").isNull(), "JSON payload must carry a timestamp");
		assertTrue(node.has("stackPoint"), "JSON payload must carry the caller trace (stackPoint)");
	}

	/**
	 * Exercises the real, instrumented secret-creation call path
	 * (GeboSecretsAccessServiceImpl.storeSecret), not a synthetic event, to
	 * prove the audit logging wired into business services actually reaches
	 * the security-log logger with the expected taxonomy and outcome.
	 */
	@Test
	public void secretCreationIsAuditedWithSuccessOutcome() throws GeboCryptSecretException {
		GeboTokenContent content = new GeboTokenContent();
		content.setToken("test-token-abc123");
		content.setUser("audit-test-user");

		String secretId = secretsAccessService.storeSecret(content, "Audit logging test secret", CONTEXT_CODE);

		JsonNode node = singleLoggedEventAsJson();
		assertEquals(SecurityAuditTaxonomy.EventType.SECRET_MANAGEMENT, node.get("eventType").asText());
		assertEquals(SecurityAuditTaxonomy.Action.SECRET_CREATE, node.get("action").asText());
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, node.get("outcome").asText());
		assertEquals(secretId, node.get("resourceId").asText());
	}

	// --- User / group administration ---------------------------------------
	//
	// These exercise the real IGUsersAdminService and IGUserPasswordService beans, not
	// synthetic events, so they prove the store-level instrumentation actually fires -
	// including on the paths that have no controller to be audited at (this test calls
	// the services directly, exactly as the installation bootstrap and the OAuth2
	// provisioner do).

	private EditableUser newTestUser(String username, List<String> roles) {
		EditableUser user = new EditableUser();
		user.setUsername(username);
		user.setName("Audit");
		user.setSourname("Test");
		user.setRoles(roles);
		user.setDisabled(false);
		user.setAuthProvider(AuthProvider.local);
		return user;
	}

	/**
	 * Creating a user must land on the security log at both tiers: the identity that
	 * appeared ({@code userInsert}) and the credential that was stored for it
	 * ({@code passwordSecretStore}). Neither event may carry the password.
	 */
	@Test
	public void userCreationIsAuditedAtTheIdentityAndTheCredentialTier() {
		String username = "audit-insert-" + java.util.UUID.randomUUID() + "@gebo.ai";

		usersAdminService.insertUser(newTestUser(username, List.of("USER")), "a-secret-password");

		JsonNode inserted = singleLoggedEventWithAction(SecurityAuditTaxonomy.Action.USER_INSERT);
		assertEquals(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION, inserted.get("eventType").asText());
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, inserted.get("outcome").asText());
		assertEquals(username, inserted.get("resourceId").asText());
		assertEquals("user", inserted.get("resourceType").asText());
		assertTrue(inserted.get("details").get("newRoles").toString().contains("USER"),
				"The granted roles must be in the event: they are the whole point of auditing a creation");

		JsonNode stored = singleLoggedEventWithAction(SecurityAuditTaxonomy.Action.PASSWORD_SECRET_STORE);
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, stored.get("outcome").asText());
		assertEquals(username, stored.get("resourceId").asText());
		assertFalse(stored.get("details").get("replacedExisting").asBoolean(),
				"A brand new account replaced no credential");

		for (ILoggingEvent logged : listAppender.list) {
			assertFalse(logged.getFormattedMessage().contains("a-secret-password"),
					"No security event may ever carry a password");
		}
	}

	/**
	 * A role change is the event a SIEM correlates on, and it is only meaningful as a
	 * difference - so the previous roles have to be in the event too.
	 */
	@Test
	public void aRoleChangeIsAuditedWithTheRolesItReplaced() {
		String username = "audit-update-" + java.util.UUID.randomUUID() + "@gebo.ai";
		usersAdminService.insertUser(newTestUser(username, List.of("USER")), "a-secret-password");
		listAppender.list.clear();

		usersAdminService.updateUser(newTestUser(username, List.of("USER", "ADMIN")));

		JsonNode updated = singleLoggedEventWithAction(SecurityAuditTaxonomy.Action.USER_UPDATE);
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, updated.get("outcome").asText());
		assertEquals(username, updated.get("resourceId").asText());
		JsonNode details = updated.get("details");
		assertTrue(details.get("rolesChanged").asBoolean(), "The event must say the roles changed");
		assertFalse(details.get("previousRoles").toString().contains("ADMIN"), "ADMIN was not held before");
		assertTrue(details.get("newRoles").toString().contains("ADMIN"), "ADMIN is held after");
	}

	/**
	 * Deleting a user removes a Mongo document AND a credential in a different store;
	 * both halves are audited, so an orphaned secret is visible in the log.
	 */
	@Test
	public void userDeletionIsAuditedAtTheIdentityAndTheCredentialTier() {
		String username = "audit-delete-" + java.util.UUID.randomUUID() + "@gebo.ai";
		usersAdminService.insertUser(newTestUser(username, List.of("USER")), "a-secret-password");
		listAppender.list.clear();

		usersAdminService.deleteUser(newTestUser(username, List.of("USER")));

		JsonNode deleted = singleLoggedEventWithAction(SecurityAuditTaxonomy.Action.USER_DELETE);
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, deleted.get("outcome").asText());
		assertEquals(username, deleted.get("resourceId").asText());

		JsonNode credential = singleLoggedEventWithAction(SecurityAuditTaxonomy.Action.PASSWORD_SECRET_DELETE);
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, credential.get("outcome").asText());
		assertEquals(1, credential.get("details").get("deletedSecrets").asInt(),
				"The account had exactly one password secret, and it had to go");
	}

	/** A rejected creation must be on the log too - that is the interesting one. */
	@Test
	public void aRejectedUserCreationIsAuditedAsAFailure() {
		String username = "audit-duplicate-" + java.util.UUID.randomUUID() + "@gebo.ai";
		usersAdminService.insertUser(newTestUser(username, List.of("USER")), "a-secret-password");
		listAppender.list.clear();

		assertThrows(IllegalStateException.class,
				() -> usersAdminService.insertUser(newTestUser(username, List.of("ADMIN")), "another-password"));

		JsonNode failed = singleLoggedEventWithAction(SecurityAuditTaxonomy.Action.USER_INSERT);
		assertEquals(SecurityAuditTaxonomy.Outcome.FAILURE, failed.get("outcome").asText());
		assertEquals(username, failed.get("resourceId").asText());
	}

	/**
	 * A group is an ACL principal, so its membership is a grant: creating and changing
	 * one is audited with who is in it.
	 */
	@Test
	public void groupCreationAndMembershipChangeAreAudited() {
		String code = "audit-group-" + java.util.UUID.randomUUID();
		UsersGroup group = new UsersGroup();
		group.setCode(code);
		group.setDescription("audit test group");
		group.setUserIds(new ArrayList<>(List.of("someone@gebo.ai")));

		usersAdminService.insertGroup(group);

		JsonNode inserted = singleLoggedEventWithAction(SecurityAuditTaxonomy.Action.GROUP_INSERT);
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, inserted.get("outcome").asText());
		assertEquals(code, inserted.get("resourceId").asText());
		assertEquals("usersGroup", inserted.get("resourceType").asText());
		assertEquals(1, inserted.get("details").get("newMembersCount").asInt());

		listAppender.list.clear();
		group.setUserIds(new ArrayList<>(List.of("someone@gebo.ai", "someone.else@gebo.ai")));
		usersAdminService.updateGroup(group);

		JsonNode updated = singleLoggedEventWithAction(SecurityAuditTaxonomy.Action.GROUP_UPDATE);
		assertEquals(1, updated.get("details").get("previousMembersCount").asInt());
		assertEquals(2, updated.get("details").get("newMembersCount").asInt());
	}

	/**
	 * The reason the password service is instrumented and not only its callers: a
	 * password replaced on an existing account is a different thing, to a SIEM, from a
	 * password set on a new one.
	 */
	@Test
	public void aPasswordReplacementIsAuditedAsSuch() throws GeboCryptSecretException {
		String username = "audit-password-" + java.util.UUID.randomUUID() + "@gebo.ai";
		usersAdminService.insertUser(newTestUser(username, List.of("USER")), "a-secret-password");
		listAppender.list.clear();

		userPasswordService.storePassword(username, "the-replacement-password");

		JsonNode stored = singleLoggedEventWithAction(SecurityAuditTaxonomy.Action.PASSWORD_SECRET_STORE);
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, stored.get("outcome").asText());
		assertEquals(username, stored.get("resourceId").asText());
		assertEquals("userPassword", stored.get("resourceType").asText());
		assertTrue(stored.get("details").get("replacedExisting").asBoolean());
		for (ILoggingEvent logged : listAppender.list) {
			assertFalse(logged.getFormattedMessage().contains("the-replacement-password"),
					"No security event may ever carry a password");
		}
	}

	// --- OAuth2 provisioning -----------------------------------------------
	//
	// GOauth2ResourceServerUserProvisioner is the single chokepoint every bearer-token
	// converter reaches (servlet + reactive, JWT + opaque), so driving it directly
	// covers all four. It is also the piece that most needed events: it is designed to
	// swallow provisioning failures, so before this instrumentation an identity that
	// could never be provisioned produced nothing but 401s.

	/** The policy this provisioner acts under, restored after each test that flips it. */
	private void withTrustEveryOauthIdentity(Runnable body) {
		GeboLoginPolicy previous = geboSecurityConfig.getLoginPolicy();
		geboSecurityConfig.setLoginPolicy(GeboLoginPolicy.TRUST_EVERY_OAUTH_IDENTITY);
		try {
			body.run();
		} finally {
			geboSecurityConfig.setLoginPolicy(previous);
		}
	}

	private Oauth2RuntimeConfiguration testRuntimeConfig() {
		Oauth2RuntimeConfiguration config = new Oauth2RuntimeConfiguration();
		config.setRegistrationId("audit-test-registration");
		config.setProvider(AuthProvider.oauth2_generic);
		return config;
	}

	@Test
	public void resourceServerProvisioningOfAnUnknownIdentityIsAudited() {
		String username = "audit-oauth2-" + java.util.UUID.randomUUID() + "@gebo.ai";
		withTrustEveryOauthIdentity(() -> {
			boolean provisioned = resourceServerUserProvisioner.provisionOnValidatedUnknownUser(testRuntimeConfig(),
					"token-" + java.util.UUID.randomUUID(), java.util.Map.of("email", username));
			assertTrue(provisioned, "TRUST_EVERY_OAUTH_IDENTITY must admit an unknown identity");
		});

		JsonNode provisioning = singleLoggedEventWithAction(
				SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_PROVISION);
		assertEquals(SecurityAuditTaxonomy.EventType.USER_ADMINISTRATION, provisioning.get("eventType").asText());
		assertEquals(SecurityAuditTaxonomy.Outcome.SUCCESS, provisioning.get("outcome").asText());
		assertEquals(username, provisioning.get("resourceId").asText());
		JsonNode details = provisioning.get("details");
		assertEquals("oauth2ResourceServer", details.get("flow").asText());
		assertEquals("email", details.get("usernameClaim").asText());
		assertFalse(details.get("alreadyExisted").asBoolean(), "This identity had no local user");
		assertTrue(details.has("syncHandler"),
				"The handler that performed the write must be named - a custom one bypasses the audited "
						+ "IGUsersAdminService path entirely");

		// And the tiers below it fired too, each saying something the others cannot: the
		// store was asked to create-if-missing, and a user really was created.
		assertEquals(1, loggedEventsWithAction(SecurityAuditTaxonomy.Action.USER_AUTO_PROVISION).size(),
				"The store's own create-if-missing event is a separate action from the chain's decision");
		assertEquals(1, loggedEventsWithAction(SecurityAuditTaxonomy.Action.USER_INSERT).size(),
				"The provisioning decision and the user creation it caused are both on the log");
	}

	/**
	 * A validated token from a configured provider that carries no usable username is a
	 * provider/claims misconfiguration. It is refused, and it must be visible as a
	 * refusal - otherwise it only ever shows up as a 401.
	 */
	@Test
	public void aTokenWithNoUsernameClaimIsAuditedAsDenied() {
		withTrustEveryOauthIdentity(() -> resourceServerUserProvisioner.provisionOnValidatedUnknownUser(
				testRuntimeConfig(), "token-" + java.util.UUID.randomUUID(),
				java.util.Map.of("some-unrelated-claim", "x")));

		JsonNode denied = singleLoggedEventWithAction(SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_PROVISION);
		assertEquals(SecurityAuditTaxonomy.Outcome.DENIED, denied.get("outcome").asText());
		assertEquals("noUsernameClaim", denied.get("details").get("reason").asText());
		assertTrue(denied.get("details").get("presentedClaims").toString().contains("some-unrelated-claim"),
				"The claim NAMES help diagnose the misconfiguration; their values are never logged");
	}

	/**
	 * The TTL throttle in front of the provisioner is also the audit log's rate limit -
	 * the reason the events are emitted behind it. A client retrying one token must not
	 * be able to write an event per request.
	 */
	@Test
	public void repeatingOneTokenProducesOneProvisioningEvent() {
		String username = "audit-oauth2-throttle-" + java.util.UUID.randomUUID() + "@gebo.ai";
		String token = "token-" + java.util.UUID.randomUUID();
		withTrustEveryOauthIdentity(() -> {
			resourceServerUserProvisioner.provisionOnValidatedUnknownUser(testRuntimeConfig(), token,
					java.util.Map.of("email", username));
			resourceServerUserProvisioner.provisionOnValidatedUnknownUser(testRuntimeConfig(), token,
					java.util.Map.of("email", username));
			resourceServerUserProvisioner.provisionOnValidatedUnknownUser(testRuntimeConfig(), token,
					java.util.Map.of("email", username));
		});

		assertEquals(1, loggedEventsWithAction(SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_PROVISION).size(),
				"One event per token per TTL window, not one per request");
	}

	/**
	 * The deliberately unaudited branch, pinned so it stays deliberate: under a policy
	 * that forbids provisioning this declines on every request without consulting the
	 * throttle, so an event here would be one per request.
	 */
	@Test
	public void aPolicyThatForbidsProvisioningDoesNotWriteAnEventPerRequest() {
		geboSecurityConfig.setLoginPolicy(GeboLoginPolicy.REQUIRE_INVITATION);

		for (int i = 0; i < 5; i++) {
			resourceServerUserProvisioner.provisionOnValidatedUnknownUser(testRuntimeConfig(),
					"token-" + java.util.UUID.randomUUID(),
					java.util.Map.of("email", "audit-oauth2-denied@gebo.ai"));
		}

		assertTrue(loggedEventsWithAction(SecurityAuditTaxonomy.Action.OAUTH2_IDENTITY_PROVISION).isEmpty(),
				"The policy gate is a standing configuration fact, recorded by the request's own 401");
	}

	/**
	 * Same call path as above, but forced down the failure branch (updating a
	 * secret code that was never created), to prove failure outcomes are
	 * audited too and not only the happy path.
	 */
	@Test
	public void secretUpdateOfUnknownCodeIsAuditedWithFailureOutcome() {
		GeboTokenContent content = new GeboTokenContent();
		content.setToken("does-not-matter");
		content.setUser("audit-test-user");
		String unknownCode = "unknown-secret-code-" + java.util.UUID.randomUUID();

		assertThrows(GeboCryptSecretException.class,
				() -> secretsAccessService.updateSecret(content, "n/a", CONTEXT_CODE, unknownCode));

		JsonNode node = singleLoggedEventAsJson();
		assertEquals(SecurityAuditTaxonomy.EventType.SECRET_MANAGEMENT, node.get("eventType").asText());
		assertEquals(SecurityAuditTaxonomy.Action.SECRET_UPDATE, node.get("action").asText());
		assertEquals(SecurityAuditTaxonomy.Outcome.FAILURE, node.get("outcome").asText());
		assertEquals(unknownCode, node.get("resourceId").asText());
	}
}
