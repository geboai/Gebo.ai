/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mock.env.MockEnvironment;

import ai.gebo.architecture.agents.model.GAgenticChatDefaultNetworkOfAgents;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.repository.GAgenticChatDefaultNetworkOfAgentsRepository;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService.AgenticChatDefaultNetworkInfo;
import ai.gebo.architecture.agents.services.IGConfiguredDefaultChatNetworksOfAgents;

/**
 * Pins the choice of the network a chat is handed to: the chat profile's, else the
 * administrator's system default, else the configured one, an option naming a
 * missing or not choosable network being skipped.
 */
class GAgenticChatDefaultNetworkOfAgentsServiceImplTest {
	private final Map<String, GAgentsNetwork> networks = new HashMap<>();
	private final Map<String, GAgenticChatDefaultNetworkOfAgents> saved = new HashMap<>();
	private GAgenticChatDefaultNetworkOfAgentsRepository repository;
	private GAgenticChatDefaultNetworkOfAgentsServiceImpl service;
	private final MockEnvironment environment = new MockEnvironment();

	private void network(String code, PipelineType... types) {
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode(code);
		network.setChoosableForPipelineTypes(types.length > 0 ? List.of(types) : null);
		networks.put(code, network);
	}

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	@BeforeEach
	void setUp() {
		network("LOOP", PipelineType.RAG_PIPELINE);
		network("LOOP_PURE", PipelineType.PURE_CHAT_PIPELINE);
		network("CONTROLLER", PipelineType.RAG_PIPELINE);
		network("OFFICE");
		IAgentsNetworkDao dao = mock(IAgentsNetworkDao.class);
		when(dao.getConfigurations()).thenAnswer(x -> List.copyOf(networks.values()));
		when(dao.findByCode(any())).thenAnswer(x -> networks.get((String) x.getArgument(0)));
		IGConfiguredDefaultChatNetworksOfAgents configured = type -> type == PipelineType.PURE_CHAT_PIPELINE
				? "LOOP_PURE"
				: "LOOP";
		repository = mock(GAgenticChatDefaultNetworkOfAgentsRepository.class);
		when(repository.findById(any())).thenAnswer(x -> Optional.ofNullable(saved.get((String) x.getArgument(0))));
		when(repository.save(any())).thenAnswer(x -> {
			GAgenticChatDefaultNetworkOfAgents chosen = x.getArgument(0);
			saved.put(chosen.getId(), chosen);
			return chosen;
		});
		service = new GAgenticChatDefaultNetworkOfAgentsServiceImpl(provider(dao), provider(configured), repository,
				environment);
	}

	@Test
	void theConfiguredDefaultAppliesWhenNothingElseIsChosen() {
		assertEquals("LOOP", service.resolveChatNetworkOfAgents(PipelineType.RAG_PIPELINE, null));
		assertEquals("LOOP_PURE", service.resolveChatNetworkOfAgents(PipelineType.PURE_CHAT_PIPELINE, null));
	}

	@Test
	void theSystemDefaultOverridesTheConfiguredOne() {
		service.setAgenticChatDefaultNetwork(PipelineType.RAG_PIPELINE, "CONTROLLER");

		assertEquals("CONTROLLER", service.resolveChatNetworkOfAgents(PipelineType.RAG_PIPELINE, null));
		assertEquals("LOOP_PURE", service.resolveChatNetworkOfAgents(PipelineType.PURE_CHAT_PIPELINE, null));
	}

	@Test
	void theChatProfileOverridesTheSystemDefault() {
		service.setAgenticChatDefaultNetwork(PipelineType.RAG_PIPELINE, "CONTROLLER");

		assertEquals("LOOP", service.resolveChatNetworkOfAgents(PipelineType.RAG_PIPELINE, "LOOP"));
		assertEquals("LOOP", service.resolveChatNetwork(PipelineType.RAG_PIPELINE, "LOOP").getCode());
	}

	@Test
	void aMissingOrNotChoosableNetworkIsSkipped() {
		assertEquals("LOOP", service.resolveChatNetworkOfAgents(PipelineType.RAG_PIPELINE, "DELETED"));
		assertEquals("LOOP", service.resolveChatNetworkOfAgents(PipelineType.RAG_PIPELINE, "LOOP_PURE"));
		assertEquals("LOOP", service.resolveChatNetworkOfAgents(PipelineType.RAG_PIPELINE, "OFFICE"));
		networks.remove("LOOP");
		assertNull(service.resolveChatNetworkOfAgents(PipelineType.RAG_PIPELINE, null),
				"nothing usable left");
	}

	@Test
	void onlyAnExistingChoosableNetworkCanBeTheSystemDefault() {
		assertThrows(IllegalArgumentException.class,
				() -> service.setAgenticChatDefaultNetwork(PipelineType.PURE_CHAT_PIPELINE, "LOOP"));
		assertThrows(IllegalArgumentException.class,
				() -> service.setAgenticChatDefaultNetwork(PipelineType.RAG_PIPELINE, "DELETED"));
		verify(repository, never()).save(any());
	}

	@Test
	void theSystemDefaultIsSavedPerPipelineTypeAndCanBeReset() {
		AgenticChatDefaultNetworkInfo info = service.setAgenticChatDefaultNetwork(PipelineType.RAG_PIPELINE,
				"CONTROLLER");
		ArgumentCaptor<GAgenticChatDefaultNetworkOfAgents> captor = ArgumentCaptor
				.forClass(GAgenticChatDefaultNetworkOfAgents.class);
		verify(repository).save(captor.capture());
		assertEquals("RAG_PIPELINE", captor.getValue().getId());
		assertEquals("CONTROLLER", info.getDefaultChatNetworkOfAgents());
		assertEquals("LOOP", info.getConfiguredDefaultChatNetworkOfAgents());
		assertEquals("CONTROLLER", info.getEffectiveChatNetworkOfAgents());

		when(repository.findById("RAG_PIPELINE")).thenReturn(Optional.empty());
		AgenticChatDefaultNetworkInfo reset = service.resetAgenticChatDefaultNetwork(PipelineType.RAG_PIPELINE);
		verify(repository).deleteById("RAG_PIPELINE");
		assertNull(reset.getDefaultChatNetworkOfAgents());
		assertEquals("LOOP", reset.getEffectiveChatNetworkOfAgents());
	}

	@Test
	void theChoosableNetworksAreTheOnesOfThePipelineType() {
		assertEquals(List.of("CONTROLLER", "LOOP"), service.getChoosableNetworksOfAgents(PipelineType.RAG_PIPELINE)
				.stream().map(GAgentsNetwork::getCode).sorted().toList());
		assertEquals(List.of("LOOP_PURE"), service.getChoosableNetworksOfAgents(PipelineType.PURE_CHAT_PIPELINE)
				.stream().map(GAgentsNetwork::getCode).toList());
		assertEquals(2, service.getAgenticChatDefaultNetworks().size());
	}

	@Test
	void theAgentsAreEnabledWhenTheSwitchIsMissing() {
		assertEquals(true, service.isAgenticChatNetworksEnabled());
	}

	@Test
	void nothingCanBeChosenWhenTheAgentsAreDisabled() {
		environment.setProperty("ai.gebo.agents.standard.enabled", "false");

		assertFalse(service.isAgenticChatNetworksEnabled());
		assertEquals(List.of(), service.getChoosableNetworksOfAgents(PipelineType.RAG_PIPELINE));
		assertEquals(List.of(), service.getAgenticChatDefaultNetworks());
		assertThrows(IllegalArgumentException.class,
				() -> service.setAgenticChatDefaultNetwork(PipelineType.RAG_PIPELINE, "CONTROLLER"));
		assertThrows(IllegalArgumentException.class,
				() -> service.resetAgenticChatDefaultNetwork(PipelineType.RAG_PIPELINE));
		verify(repository, never()).save(any());
		verify(repository, never()).deleteById(any());
	}
}
