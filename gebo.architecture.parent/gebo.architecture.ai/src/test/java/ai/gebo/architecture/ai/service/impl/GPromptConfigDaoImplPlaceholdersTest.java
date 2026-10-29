/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.repository.PromptConfigRepository;
import ai.gebo.architecture.ai.service.IGPromptPlaceholdersCatalog;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import ai.gebo.architecture.ai.service.IStaticPromptsProviderRepositoryPattern;

class GPromptConfigDaoImplPlaceholdersTest {
	private GPromptConfigDaoImpl dao;

	@BeforeEach
	@SuppressWarnings("unchecked")
	void setUp() throws Exception {
		GPromptTemplateConfig routing = GPromptTemplateConfig.of("Route {deliverableTypesList}", "{question}",
				"routing");
		routing.setConfigDeclarated(true);
		IGStaticPromptsProvider statics = mock(IGStaticPromptsProvider.class);
		when(statics.promptsList()).thenReturn(List.of(routing));
		IStaticPromptsProviderRepositoryPattern pattern = mock(IStaticPromptsProviderRepositoryPattern.class);
		when(pattern.getImplementations()).thenReturn(List.of(statics));
		IGPromptPlaceholdersCatalog catalog = new IGPromptPlaceholdersCatalog() {
			@Override
			public Set<String> builtInPlaceholders() {
				return Set.of("question", "documents", "userLanguage");
			}

			@Override
			public Set<String> knownPlaceholders() {
				return Set.of("currentDate", "INPUT");
			}
		};
		ObjectProvider<IGPromptPlaceholdersCatalog> catalogs = mock(ObjectProvider.class);
		when(catalogs.getIfAvailable()).thenReturn(catalog);
		dao = new GPromptConfigDaoImpl(mock(PromptConfigRepository.class), pattern, null, catalogs);
	}

	@Test
	void aPromptMayNameWhatItsCallersTheProvidersAndTheRendererFill() {
		assertDoesNotThrow(() -> dao.checkPlaceholders(GPromptTemplateConfig.of(
				"Route {deliverableTypesList} on {currentDate}", "{question} {documents}", "routing")));
	}

	@Test
	void aPromptNamingAPlaceholderNobodyFillsIsRefusedNamingIt() {
		IllegalArgumentException refused = assertThrows(IllegalArgumentException.class, () -> dao.checkPlaceholders(
				GPromptTemplateConfig.of("Hello {currentUserNme}", "{question}", "routing")));
		assertTrue(refused.getMessage().contains("currentUserNme"), refused.getMessage());
		assertTrue(refused.getMessage().contains("deliverableTypesList"), refused.getMessage());
	}

	@Test
	void anAgentPromptMayNameTheAgentsPlaceholdersAndNothingUnknown() {
		GPromptTemplateConfig agent = GPromptTemplateConfig.of("{INPUT} {currentDate}", "{question}", "my-agent");
		agent.setAgentPrompt(true);
		assertDoesNotThrow(() -> dao.checkPlaceholders(agent));
		GPromptTemplateConfig wrong = GPromptTemplateConfig.of("{INPUTS}", "{question}", "my-agent");
		wrong.setAgentPrompt(true);
		assertThrows(IllegalArgumentException.class, () -> dao.checkPlaceholders(wrong));
	}

	@Test
	void aUseNothingIsKnownOfIsNotChecked() {
		assertDoesNotThrow(() -> dao.checkPlaceholders(
				GPromptTemplateConfig.of("{whateverTheCallerFills}", "{question}", "a-new-use")));
	}
}
