/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.ai.model.LibraryToolCollision;
import ai.gebo.security.services.IGeboSystemUserService;

/**
 * At startup the agents naming a library are checked for tool collisions, and
 * nothing that goes wrong there stops the application.
 */
class AgentToolCollisionsStartupCheckTest {
	private IAgentConfigDao configs;
	private IGAgentServiceRuntimeDao services;
	private IGGenericAgentService service;
	private AgentToolCollisionsStartupCheck check;

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> providing(T bean) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(bean);
		return provider;
	}

	private static GAgentConfig anAgent(String code, List<String> libraries) {
		GAgentConfig config = new GAgentConfig();
		config.setCode(code);
		config.setAgentServiceId("a-service");
		config.setLibraryTypeIds(libraries);
		return config;
	}

	@BeforeEach
	void setUp() {
		configs = mock(IAgentConfigDao.class);
		services = mock(IGAgentServiceRuntimeDao.class);
		service = mock(IGGenericAgentService.class);
		when(services.findByCode("a-service")).thenReturn(service);
		check = new AgentToolCollisionsStartupCheck(providing(configs), providing(services),
				providing((IGeboSystemUserService) null));
	}

	@Test
	void theCollisionsOfTheAgentsNamingALibraryAreCounted() {
		GAgentConfig coder = anAgent("a-coder", List.of("working-directory"));
		when(configs.getConfigurations()).thenReturn(List.of(coder));
		AgentMountedTools mounted = new AgentMountedTools();
		mounted.getCollisions().add(new LibraryToolCollision("readFile", "working-directory", "A directory",
				"mcp-files", null, "An MCP server"));
		when(service.getMountedTools(coder)).thenReturn(mounted);

		assertEquals(1, check.check());
	}

	@Test
	void anAgentNamingNoLibraryOnAServiceImplyingOneIsRead() {
		GAgentConfig loop = anAgent("a-loop-agent", List.of());
		when(configs.getConfigurations()).thenReturn(List.of(loop));
		when(service.getImpliedLibraryTypeIds()).thenReturn(List.of("user-questions-and-clarifications"));
		AgentMountedTools mounted = new AgentMountedTools();
		mounted.getCollisions().add(new LibraryToolCollision("askUserSingleChoiceQuestion",
				"user-questions-and-clarifications", "User questions", "a-source", null, "A source"));
		when(service.getMountedTools(loop)).thenReturn(mounted);

		assertEquals(1, check.check(), "the library the service implies is mounted as a named one");
	}

	@Test
	void anAgentNamingNoLibraryIsNotReadAtAll() {
		when(configs.getConfigurations()).thenReturn(List.of(anAgent("a-plain-agent", null)));

		assertEquals(0, check.check());
		verify(service, never()).getMountedTools(any());
	}

	@Test
	void anAgentWhoseServiceIsNotThereIsLeftOut() {
		GAgentConfig orphan = anAgent("an-orphan", List.of("working-directory"));
		orphan.setAgentServiceId("gone");
		when(configs.getConfigurations()).thenReturn(List.of(orphan));

		assertEquals(0, check.check());
	}

	@Test
	void aFailureDoesNotStopTheApplication() {
		when(configs.getConfigurations()).thenThrow(new StackOverflowError());

		assertDoesNotThrow(() -> check.onApplicationReady());
	}
}
