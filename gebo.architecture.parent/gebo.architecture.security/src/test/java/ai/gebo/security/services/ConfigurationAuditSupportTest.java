/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.environment.ArchitectureType;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;

/**
 * The configuration changes reach the security log with the outcome they really
 * had, the exception of a failed change is the one its caller gets, and the access
 * settings are described without the user ids (e-mail addresses).
 */
class ConfigurationAuditSupportTest {

	/** Collects the events instead of writing them to the security-log logger. */
	private static final class CollectingAuditLogger implements IGSecurityAuditLoggerService {
		private final List<SecurityEvent> logged = new ArrayList<>();

		@Override
		public void log(SecurityEvent event) {
			logged.add(event);
		}

		@Override
		public SecurityEvent newSecurityEvent() {
			return new SecurityEvent(ArchitectureType.MONOLITHIC, "admin@example.com", "203.0.113.7", "test",
					"correlation", "2026-10-18T00:00:00Z", "ConfigurationAuditSupportTest#endpoint", "POST",
					"/api/admin/test");
		}
	}

	/** A configuration with access settings, like the networks of agents or the knowledge bases. */
	private static final class SecuredConfig extends GBaseObject implements IGObjectWithSecurity {
		private static final long serialVersionUID = 1L;
		private final Boolean toAll;
		private final List<String> users;
		private final List<String> groups;

		SecuredConfig(String code, Boolean toAll, List<String> users, List<String> groups) {
			setCode(code);
			this.toAll = toAll;
			this.users = users;
			this.groups = groups;
		}

		@Override
		public String owner() {
			return null;
		}

		@Override
		public Boolean getAccessibleToAll() {
			return toAll;
		}

		@Override
		public List<String> getAccessibleUsers() {
			return users;
		}

		@Override
		public List<String> getAccessibleGroups() {
			return groups;
		}
	}

	private CollectingAuditLogger auditLogger;

	@BeforeEach
	void setUp() {
		auditLogger = new CollectingAuditLogger();
	}

	private SecurityEvent onlyEvent() {
		assertThat(auditLogger.logged).hasSize(1);
		return auditLogger.logged.get(0);
	}

	@Test
	void aChangeThatReturnsIsAuditedAsASuccessWithItsTaxonomy() {
		SecurityEvent event = auditLogger.newSecurityEvent();
		String result = ConfigurationAuditSupport.audited(auditLogger, event,
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENTS_NETWORK_INSERT,
				SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK, "SALES_NETWORK", null, () -> "saved");

		assertThat(result).isEqualTo("saved");
		SecurityEvent logged = onlyEvent();
		assertThat(logged).isSameAs(event);
		assertThat(logged.getEventType()).isEqualTo("agentsConfiguration");
		assertThat(logged.getCategory()).isEqualTo(SecurityAuditTaxonomy.Category.AGENTS_CONFIGURATION);
		assertThat(logged.getAction()).isEqualTo("agentsNetworkInsert");
		assertThat(logged.getResourceType()).isEqualTo("agentsNetwork");
		assertThat(logged.getResourceId()).isEqualTo("SALES_NETWORK");
		assertThat(logged.getOutcome()).isEqualTo(SecurityAuditTaxonomy.Outcome.SUCCESS);
	}

	@Test
	void anOperationStatusCarryingErrorsIsAuditedAsAFailure() {
		SecurityEvent event = auditLogger.newSecurityEvent();
		OperationStatus<GBaseObject> status = ConfigurationAuditSupport.audited(auditLogger, event,
				SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.MCP_SERVER_INSERT, SecurityAuditTaxonomy.ResourceType.MCP_SERVER,
				"deepwiki", null, () -> OperationStatus.<GBaseObject>ofError("Invalid", "The url is missing"));

		assertThat(status.isHasErrorMessages()).isTrue();
		SecurityEvent logged = onlyEvent();
		assertThat(logged.getOutcome()).isEqualTo(SecurityAuditTaxonomy.Outcome.FAILURE);
		assertThat(logged.getDetails()).containsEntry(ConfigurationAuditSupport.DETAIL_MESSAGES_COUNT, 1);
	}

	@Test
	void anOperationStatusWithoutErrorsIsAuditedAsASuccess() {
		SecurityEvent event = auditLogger.newSecurityEvent();
		GBaseObject saved = new GBaseObject();
		saved.setCode("profile-1");
		ConfigurationAuditSupport.audited(auditLogger, event, SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.CHAT_PROFILE_UPDATE, SecurityAuditTaxonomy.ResourceType.CHAT_PROFILE,
				"profile-1", null, () -> OperationStatus.of(saved));

		assertThat(onlyEvent().getOutcome()).isEqualTo(SecurityAuditTaxonomy.Outcome.SUCCESS);
	}

	@Test
	void aCheckedExceptionIsAuditedAsAFailureAndRethrownUnchanged() {
		SecurityEvent event = auditLogger.newSecurityEvent();
		IOException thrown = new IOException("The store is unavailable");

		assertThatThrownBy(() -> ConfigurationAuditSupport.audited(auditLogger, event,
				SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION, SecurityAuditTaxonomy.Action.KNOWLEDGE_BASE_DELETE,
				SecurityAuditTaxonomy.ResourceType.KNOWLEDGE_BASE, "kb-1", null, () -> {
					throw thrown;
				})).isSameAs(thrown);

		SecurityEvent logged = onlyEvent();
		assertThat(logged.getOutcome()).isEqualTo(SecurityAuditTaxonomy.Outcome.FAILURE);
		assertThat(logged.getDetails()).containsEntry(ConfigurationAuditSupport.DETAIL_ERROR, "The store is unavailable");
		assertThat(logged.getResourceId()).isEqualTo("kb-1");
	}

	@Test
	void aRuntimeExceptionIsAuditedAsAFailureAndRethrownUnchanged() {
		SecurityEvent event = auditLogger.newSecurityEvent();
		IllegalStateException thrown = new IllegalStateException("No such network");

		assertThatThrownBy(() -> ConfigurationAuditSupport.auditedVoid(auditLogger, event,
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENT_DELETE,
				SecurityAuditTaxonomy.ResourceType.AGENT, "agent-1", null, () -> {
					throw thrown;
				})).isSameAs(thrown);

		assertThat(onlyEvent().getOutcome()).isEqualTo(SecurityAuditTaxonomy.Outcome.FAILURE);
	}

	@Test
	void aVoidChangeThatCompletesIsAuditedAsASuccess() {
		SecurityEvent event = auditLogger.newSecurityEvent();
		List<String> calls = new ArrayList<>();
		ConfigurationAuditSupport.auditedVoid(auditLogger, event, SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION,
				SecurityAuditTaxonomy.Action.WEB_SEARCH_CONFIG_DELETE, SecurityAuditTaxonomy.ResourceType.WEB_SEARCH_CONFIG,
				"tavily-1", null, () -> calls.add("deleted"));

		assertThat(calls).containsExactly("deleted");
		SecurityEvent logged = onlyEvent();
		assertThat(logged.getOutcome()).isEqualTo(SecurityAuditTaxonomy.Outcome.SUCCESS);
		assertThat(logged.getAction()).isEqualTo("webSearchConfigDelete");
	}

	@Test
	void theAccessSettingsAreDescribedWithTheGroupsAndOnlyTheCountOfTheUsers() {
		SecurityEvent event = auditLogger.newSecurityEvent();
		SecuredConfig config = new SecuredConfig("kb-hr", false, List.of("alice@example.com", "bob@example.com"),
				List.of("HR", "MANAGEMENT"));
		ConfigurationAuditSupport.audited(auditLogger, event, SecurityAuditTaxonomy.EventType.KNOWLEDGE_CONFIGURATION,
				SecurityAuditTaxonomy.Action.KNOWLEDGE_BASE_UPDATE, SecurityAuditTaxonomy.ResourceType.KNOWLEDGE_BASE,
				config.getCode(), config, () -> config);

		SecurityEvent logged = onlyEvent();
		assertThat(logged.getDetails()).containsEntry(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_TO_ALL, false)
				.containsEntry(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_GROUPS, List.of("HR", "MANAGEMENT"))
				.containsEntry(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_USERS_COUNT, 2);
		// the user ids are e-mail addresses: nowhere in the event's details
		assertThat(String.valueOf(logged.getDetails())).doesNotContain("alice@example.com", "bob@example.com");
	}

	@Test
	void aConfigurationWithoutAccessSettingsAddsNoAccessDetails() {
		SecurityEvent event = auditLogger.newSecurityEvent();
		ConfigurationAuditSupport.audited(auditLogger, event, SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION,
				SecurityAuditTaxonomy.Action.AGENTIC_CHAT_DEFAULT_NETWORK_SET,
				SecurityAuditTaxonomy.ResourceType.AGENTIC_CHAT_DEFAULT_NETWORK, "RAG", "not secured", () -> "set");

		assertThat(onlyEvent().getDetails()).doesNotContainKeys(ConfigurationAuditSupport.DETAIL_ACCESSIBLE_TO_ALL,
				ConfigurationAuditSupport.DETAIL_ACCESSIBLE_GROUPS, ConfigurationAuditSupport.DETAIL_ACCESSIBLE_USERS_COUNT);
	}

	@Test
	void aCodeGeneratedOnInsertIsTakenFromTheSavedResult() {
		GBaseObject saved = new GBaseObject();
		saved.setCode("generated-code");

		ConfigurationAuditSupport.audited(auditLogger, auditLogger.newSecurityEvent(),
				SecurityAuditTaxonomy.EventType.SEARCH_CONFIGURATION, SecurityAuditTaxonomy.Action.WEB_SEARCH_CONFIG_INSERT,
				SecurityAuditTaxonomy.ResourceType.WEB_SEARCH_CONFIG, null, null, () -> saved);
		ConfigurationAuditSupport.audited(auditLogger, auditLogger.newSecurityEvent(),
				SecurityAuditTaxonomy.EventType.INTEROPERABILITY_CONFIGURATION,
				SecurityAuditTaxonomy.Action.A2A_EXPORT_INSERT, SecurityAuditTaxonomy.ResourceType.A2A_EXPORT, null, null,
				() -> OperationStatus.of(saved));

		assertThat(auditLogger.logged).extracting(SecurityEvent::getResourceId).containsExactly("generated-code",
				"generated-code");
	}

	@Test
	void theResultDescriberAddsItsDetailsOnlyToASuccessAndNeverFailsTheChange() {
		GBaseObject copy = new GBaseObject();
		copy.setCode("SALES_NETWORK-1");
		ConfigurationAuditSupport.audited(auditLogger, auditLogger.newSecurityEvent(),
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENTS_NETWORK_COPY,
				SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK, "SALES_NETWORK", null, () -> OperationStatus.of(copy),
				(e, status) -> e.getDetails().put("copyCode", status.getResult().getCode()));
		assertThat(auditLogger.logged.get(0).getDetails()).containsEntry("copyCode", "SALES_NETWORK-1");

		// a failed copy: the describer is not asked
		ConfigurationAuditSupport.audited(auditLogger, auditLogger.newSecurityEvent(),
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENTS_NETWORK_COPY,
				SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK, "MISSING", null,
				() -> OperationStatus.<GBaseObject>ofError("No network MISSING"),
				(e, status) -> e.getDetails().put("copyCode", "never"));
		assertThat(auditLogger.logged.get(1).getDetails()).doesNotContainKey("copyCode");

		// a describer that throws does not undo a change that worked
		String result = ConfigurationAuditSupport.audited(auditLogger, auditLogger.newSecurityEvent(),
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENT_COPY,
				SecurityAuditTaxonomy.ResourceType.AGENT, "agent-1", null, () -> "copied", (e, r) -> {
					throw new IllegalStateException("describer bug");
				});
		assertThat(result).isEqualTo("copied");
		assertThat(auditLogger.logged.get(2).getOutcome()).isEqualTo(SecurityAuditTaxonomy.Outcome.SUCCESS);
	}
}
