/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * A network giving one of its participants a family of tools the installation
 * keeps out of automatic mounting: the participant names the tool source, and the
 * configuration the run works on carries it, while the same agent elsewhere - in a
 * chat, in another network - does not.
 */
class ParticipantAdditionalToolSourcesTest {
	private static final String OUTCOME_SOURCE = "work-outcome-tool-source";

	/** A factory that only has to resolve a participant's configuration. */
	private static class ConfigurationResolvingFactory
			extends GAbstractAgentsNetworkServiceFactory<String, String, IGAgentsNetworkService> {
		ConfigurationResolvingFactory(IGRuntimeBinder binder) {
			super("configuration-resolving-factory", "resolves participants", IGAgentsNetworkService.class, binder);
		}

		@Override
		protected IGAgentsNetworkService createAgentsNetworkService(GAgentsNetwork network,
				INotificationSink notificationSink, Class<String> inputType, Class<String> outputType,
				ReactiveIdentityUtil runAs, Map<String, RuntimeAgentInfos> agentsCache) {
			return null;
		}

		@Override
		public Class<String> getInputType() {
			return String.class;
		}

		@Override
		public Class<String> getOutputType() {
			return String.class;
		}
	}

	private static GAgentConfig anAgentMountingEveryTool() {
		GAgentConfig config = new GAgentConfig();
		config.setCode("an-agent-mounting-every-tool");
		config.setAgentServiceId("an-agent-service");
		config.setSubscribeAllTools(true);
		return config;
	}

	/** The configuration service, which answers every lookup with its own object. */
	private static IGRuntimeBinder binderOn(GAgentConfig stored) {
		IAgentConfigDao dao = mock(IAgentConfigDao.class);
		when(dao.findByCode(stored.getCode())).thenAnswer(invocation -> stored.jsonClone());
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		when(binder.getImplementationOf(IAgentConfigDao.class)).thenReturn(dao);
		return binder;
	}

	private static AgentNetworkParticipant participantOn(String agentConfigCode) {
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode(agentConfigCode);
		participant.setController(true);
		participant.setOutputNode(true);
		return participant;
	}

	@Test
	void aNetworkGivesTheSourceToTheParticipantThatNeedsIt() throws Exception {
		GAgentConfig stored = anAgentMountingEveryTool();
		ConfigurationResolvingFactory factory = new ConfigurationResolvingFactory(binderOn(stored));
		AgentNetworkParticipant participant = participantOn(stored.getCode());
		participant.setAdditionalToolSources(List.of(OUTCOME_SOURCE));

		GAgentConfig ofTheRun = factory.configurationOf(participant);

		assertEquals(List.of(OUTCOME_SOURCE), ofTheRun.getAdditionalToolSources());
		assertNull(stored.getAdditionalToolSources(),
				"the same agent in a chat, or in another network, is unaffected");
	}

	@Test
	void aParticipantAddsToWhatTheConfigurationAlreadyMounts() throws Exception {
		GAgentConfig stored = anAgentMountingEveryTool();
		stored.setAdditionalToolSources(List.of("another-source"));
		ConfigurationResolvingFactory factory = new ConfigurationResolvingFactory(binderOn(stored));
		AgentNetworkParticipant participant = participantOn(stored.getCode());
		participant.setAdditionalToolSources(List.of("another-source", OUTCOME_SOURCE));

		assertEquals(List.of("another-source", OUTCOME_SOURCE),
				factory.configurationOf(participant).getAdditionalToolSources(), "each source named once");
	}

	@Test
	void aParticipantNamingNoSourceIsResolvedAsItIs() throws Exception {
		GAgentConfig stored = anAgentMountingEveryTool();
		ConfigurationResolvingFactory factory = new ConfigurationResolvingFactory(binderOn(stored));

		assertNull(factory.configurationOf(participantOn(stored.getCode())).getAdditionalToolSources());
	}

	@Test
	void aParticipantOnAnAgentThatDoesNotExistIsRefused() {
		ConfigurationResolvingFactory factory = new ConfigurationResolvingFactory(binderOn(anAgentMountingEveryTool()));

		assertThrows(NetworkOfAgentsException.class, () -> factory.configurationOf(participantOn("no-such-agent")));
	}
}
