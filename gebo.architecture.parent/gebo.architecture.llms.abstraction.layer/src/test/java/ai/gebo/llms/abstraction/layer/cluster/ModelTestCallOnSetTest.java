/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.cluster;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/**
 * Pins where the test call of a model happens: when a user sets or changes it, on the
 * instance doing it, before the model is associated with a provider deal or sent to
 * the other instances; a model the provider refuses is removed, a refused change
 * undone. Startup and the replicas use the plain operations, which never test.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ModelTestCallOnSetTest {

	/** A runtime DAO holding the one given model. */
	static class OneModelDao extends GAbstractClusteredModelRuntimeConfigurationDao<IGConfigurableModel, GBaseModelConfig> {
		final IGConfigurableModel model;
		final List<String> deleted = new ArrayList<>();

		OneModelDao(IGConfigurableModel model) {
			super(new ArrayList<>(), null);
			this.model = model;
		}

		@Override
		protected GLlmModelClusterCategory getClusterCategory() {
			return GLlmModelClusterCategory.CHAT;
		}

		@Override
		protected void initializeRuntimeModels() {
		}

		@Override
		public void add(IGConfigurableModel element) {
			staticConfigs.add(element);
		}

		@Override
		public void addRuntimeByConfig(GBaseModelConfig config) throws LLMConfigException {
			model.initialize(config, null);
			staticConfigs.add(model);
		}

		@Override
		public void deleteByCode(String code) throws LLMConfigException {
			staticConfigs.remove(findByCode(code));
			deleted.add(code);
		}

		@Override
		public IGConfigurableModel findByCode(String code) {
			return staticConfigs.stream().filter(x -> code.equals(x.getCode())).findFirst().orElse(null);
		}
	}

	private IGConfigurableModel model;
	private OneModelDao dao;
	private GLlmModelClusterSynchronizer synchronizer;
	private GBaseChatModelConfig config;

	@BeforeEach
	void setUp() {
		model = mock(IGConfigurableModel.class);
		when(model.getCode()).thenReturn("chat-model");
		dao = new OneModelDao(model);
		synchronizer = mock(GLlmModelClusterSynchronizer.class);
		dao.clusterSynchronizer = synchronizer;
		config = new GBaseChatModelConfig();
		config.setCode("chat-model");
	}

	@Test
	void aModelThatAnswersIsAddedAndSentToTheOtherInstances() throws Exception {
		dao.addRuntimeByConfigClustered(config);

		InOrder order = inOrder(model, synchronizer);
		order.verify(model).testCall();
		order.verify(synchronizer).broadcastAdd(GLlmModelClusterCategory.CHAT, config);
		assertSame(model, dao.findByCode("chat-model"));
	}

	@Test
	void aModelTheProviderRefusesIsRemovedAndNeverSentToTheOtherInstances() throws Exception {
		LLMConfigException refusal = new LLMConfigException("refused a test call");
		doThrow(refusal).when(model).testCall();

		LLMConfigException thrown = assertThrows(LLMConfigException.class,
				() -> dao.addRuntimeByConfigClustered(config));

		assertSame(refusal, thrown);
		assertEquals(List.of("chat-model"), dao.deleted);
		assertNull(dao.findByCode("chat-model"));
		verify(synchronizer, never()).broadcastAdd(any(), any());
	}

	@Test
	void aChangeTheProviderRefusesIsUndone() throws Exception {
		GBaseChatModelConfig previous = new GBaseChatModelConfig();
		previous.setCode("chat-model");
		dao.add(model);
		when(model.getConfig()).thenReturn(previous);
		doThrow(new LLMConfigException("refused a test call")).when(model).testCall();

		assertThrows(LLMConfigException.class, () -> dao.reconfigureByConfigClustered(config));

		InOrder order = inOrder(model);
		order.verify(model).reconfigure(config);
		order.verify(model).testCall();
		order.verify(model).reconfigure(previous);
		verify(synchronizer, never()).broadcastUpdate(any(), any());
	}

	@Test
	void aChangeThatAnswersIsSentToTheOtherInstances() throws Exception {
		dao.add(model);
		when(model.getConfig()).thenReturn(new GBaseChatModelConfig());

		dao.reconfigureByConfigClustered(config);

		InOrder order = inOrder(model, synchronizer);
		order.verify(model).reconfigure(config);
		order.verify(model).testCall();
		order.verify(synchronizer).broadcastUpdate(GLlmModelClusterCategory.CHAT, config);
	}

	@Test
	void thePlainOperationsOfStartupAndReplicasNeverTest() throws Exception {
		dao.addRuntimeByConfig(config);

		verify(model, never()).testCall();
	}
}
