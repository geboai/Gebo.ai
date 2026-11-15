/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGPromptContextProvider;
import ai.gebo.llms.abstraction.layer.services.IGPromptPlaceholdersDeclaration;

class GPromptContextProvidersImplTest {

	@SuppressWarnings("unchecked")
	private static GPromptContextProvidersImpl registry(IGPromptContextProvider... providers) {
		ObjectProvider<IGPromptContextProvider> found = mock(ObjectProvider.class);
		when(found.stream()).thenReturn(Stream.of(providers));
		ObjectProvider<IGPromptPlaceholdersDeclaration> declared = mock(ObjectProvider.class);
		when(declared.stream()).thenReturn(Stream.of((IGPromptPlaceholdersDeclaration) () -> Set.of("INPUT")));
		return new GPromptContextProvidersImpl(found, declared);
	}

	private static IGPromptContextProvider provider(Set<String> names, Map<String, Object> values) {
		return new IGPromptContextProvider() {
			@Override
			public Set<String> getPlaceholders() {
				return names;
			}

			@Override
			public Map<String, Object> valuesFor(Set<String> asked, IChatRequestContext context) {
				assertTrue(names.containsAll(asked), "asked only what it fills: " + asked);
				return values;
			}
		};
	}

	@Test
	void fillsOnlyThePlaceholdersTheTemplateNamesAndTheCallerLeftUnfilled() {
		GPromptContextProvidersImpl registry = registry(
				provider(Set.of("currentDate", "timeZone"), Map.of("currentDate", "2026-10-29", "timeZone", "UTC")),
				provider(Set.of("chatTitle"), Map.of("chatTitle", "A title")));
		GPromptTemplateConfig prompt = GPromptTemplateConfig.of("Today is {currentDate} in {chatTitle}.",
				"{question} {timeZone}", "use");

		Map<String, Object> completed = registry.complete(prompt, Map.of("timeZone", "the caller's"),
				IChatRequestContext.of("q"));

		assertEquals("2026-10-29", completed.get("currentDate"));
		assertEquals("A title", completed.get("chatTitle"));
		assertEquals("the caller's", completed.get("timeZone"), "a caller's value always wins");
		assertFalse(completed.containsKey("question"), "what the renderer fills is left to it");
	}

	@Test
	void aTemplateNamingNoProviderPlaceholderGetsExactlyTheCallersParameters() {
		GPromptContextProvidersImpl registry = registry(provider(Set.of("currentDate"), Map.of("currentDate", "x")));
		GPromptTemplateConfig prompt = GPromptTemplateConfig.of("Be brief.", "{question} {documents}", "use");

		Map<String, Object> completed = registry.complete(prompt, Map.of("a", 1), null);

		assertEquals(Map.of("a", 1), completed);
	}

	@Test
	void aFailingProviderLeavesItsPlaceholdersToTheOthers() {
		IGPromptContextProvider failing = new IGPromptContextProvider() {
			@Override
			public Set<String> getPlaceholders() {
				return Set.of("chatTitle");
			}

			@Override
			public Map<String, Object> valuesFor(Set<String> asked, IChatRequestContext context) {
				throw new IllegalStateException("down");
			}
		};
		GPromptContextProvidersImpl registry = registry(failing,
				provider(Set.of("chatTitle"), Map.of("chatTitle", "from the second")));
		GPromptTemplateConfig prompt = GPromptTemplateConfig.of("{chatTitle}", "{question}", "use");

		assertEquals("from the second", registry.complete(prompt, null, null).get("chatTitle"));
	}

	@Test
	void theCatalogKnowsTheProvidersPlaceholdersAndTheDeclaredOnes() {
		GPromptContextProvidersImpl registry = registry(provider(Set.of("currentDate"), Map.of()));

		assertEquals(Set.of("currentDate", "INPUT"), registry.knownPlaceholders());
		assertTrue(registry.builtInPlaceholders().containsAll(List.of("question", "documents", "userLanguage")));
	}
}
