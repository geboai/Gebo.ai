/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;

/**
 * Pins the system roles of the chat models, unique among the configured ones: the
 * system default chat model (uses CHAT, default) and the system default service model
 * (uses INTERNAL_SERVICES, not default); a model taking a role takes it from the
 * others, which become plain chat models (uses CHAT, not default). Changing the service model in the setup
 * wizard left two of them, the runtime keeping the first one found.
 */
class SystemChatModelsRolesTest {

	private IGPersistentObjectManager persistence;
	private IGChatModelRuntimeConfigurationDao dao;
	private ModelRuntimeConfigureHandler handler;
	private final List<GBaseChatModelConfig> saved = new ArrayList<>();

	private static GBaseChatModelConfig model(String code, Boolean defaultModel, ChatModelsUses... uses) {
		final GBaseChatModelConfig config = new GBaseChatModelConfig();
		config.setCode(code);
		config.setDefaultModel(defaultModel);
		config.setForUses(new ArrayList<>(List.of(uses)));
		return config;
	}

	@BeforeEach
	void aHandler() throws Exception {
		persistence = mock(IGPersistentObjectManager.class);
		dao = mock(IGChatModelRuntimeConfigurationDao.class);
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		when(binder.getImplementationOf(IGChatModelRuntimeConfigurationDao.class)).thenReturn(dao);
		when(persistence.update(any())).thenAnswer(call -> call.getArgument(0));
		handler = new ModelRuntimeConfigureHandler(persistence, binder);
	}

	private void configured(GBaseChatModelConfig... configs) throws Exception {
		saved.addAll(List.of(configs));
		when(persistence.findAllExtendingType(GBaseChatModelConfig.class)).thenReturn(new ArrayList<>(saved));
	}

	@Test
	void aNewServiceModelTakesTheRoleFromTheOldOne() throws Exception {
		final GBaseChatModelConfig oldService = model("gpt-4o-mini", false, ChatModelsUses.INTERNAL_SERVICES);
		final GBaseChatModelConfig chatAndService = model("gpt-4.1", false, ChatModelsUses.CHAT,
				ChatModelsUses.INTERNAL_SERVICES);
		final GBaseChatModelConfig defaultChat = model("qwen", true, ChatModelsUses.CHAT);
		final GBaseChatModelConfig newService = model("gpt-oss-20b", false, ChatModelsUses.INTERNAL_SERVICES);
		configured(oldService, chatAndService, defaultChat, newService);

		handler.handleSystemChatModels(newService);

		assertEquals(List.of(ChatModelsUses.CHAT), oldService.getForUses(), "a former service model stays a chat model");
		assertEquals(List.of(ChatModelsUses.CHAT), chatAndService.getForUses());
		assertEquals(List.of(ChatModelsUses.INTERNAL_SERVICES), newService.getForUses());
		assertTrue(defaultChat.getDefaultModel(), "the default chat model keeps its role");
		assertFalse(oldService.getDefaultModel());
		// the two former service models (equal once plain chat models), not the default chat model
		verify(persistence, times(2)).update(any());
	}

	@Test
	void aNewDefaultChatModelTakesTheRoleFromTheOldOne() throws Exception {
		final GBaseChatModelConfig oldDefault = model("qwen", true, ChatModelsUses.CHAT);
		final GBaseChatModelConfig service = model("gpt-oss-20b", false, ChatModelsUses.INTERNAL_SERVICES);
		final GBaseChatModelConfig newDefault = model("gpt-4.1", true, ChatModelsUses.CHAT);
		configured(oldDefault, service, newDefault);

		handler.handleSystemChatModels(newDefault);

		assertFalse(oldDefault.getDefaultModel());
		assertEquals(List.of(ChatModelsUses.CHAT), oldDefault.getForUses());
		assertTrue(newDefault.getDefaultModel());
		assertEquals(List.of(ChatModelsUses.INTERNAL_SERVICES), service.getForUses(), "the service model keeps its role");
		verify(persistence).update(oldDefault);
		verify(persistence, never()).update(service);
	}

	@Test
	void aPlainChatModelTakesNoRole() throws Exception {
		final GBaseChatModelConfig service = model("gpt-oss-20b", false, ChatModelsUses.INTERNAL_SERVICES);
		final GBaseChatModelConfig defaultChat = model("qwen", true, ChatModelsUses.CHAT);
		final GBaseChatModelConfig chat = model("gpt-4.1", false, ChatModelsUses.CHAT);
		configured(service, defaultChat, chat);

		handler.handleSystemChatModels(chat);

		verify(persistence, never()).update(any());
	}
}
