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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
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
