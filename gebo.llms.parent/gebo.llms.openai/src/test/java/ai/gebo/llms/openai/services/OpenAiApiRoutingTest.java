/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;

/**
 * Pins which OpenAI models go through the Responses api: the ones the models library
 * names, then version 6 and later.
 */
class OpenAiApiRoutingTest {

	@Test
	void theModelsOfVersionSixAndLaterGoThroughTheResponsesApi() {
		for (String id : List.of("gpt-6.1-sol", "gpt-6-astra", "gpt-6-sol", "gpt-6-luna", "gpt-6", "gpt-10-x"))
			assertTrue(OpenAiApiRouting.usesResponsesApi(id), id);
		for (String id : List.of("gpt-5.5", "gpt-5.6-sol", "gpt-5", "gpt-4.1-mini-2025-04-14", "gpt-4o", "o3",
				"o4-mini", "chatgpt-4o-latest"))
			assertFalse(OpenAiApiRouting.usesResponsesApi(id), id);
		assertEquals(6.1, OpenAiApiRouting.versionOf("gpt-6.1-sol"));
		assertEquals(4.1, OpenAiApiRouting.versionOf("gpt-4.1-mini-2025-04-14"));
		assertNull(OpenAiApiRouting.versionOf("davinci"));
		assertFalse(OpenAiApiRouting.usesResponsesApi(null));
	}

	@Test
	void theLibrarysApiIsReadBeforeTheVersion() {
		IGModelsLibraryDao library = new IGModelsLibraryDao() {
			@Override
			public ModelMetaInfo findByModelId(String modelId) {
				return null;
			}

			@Override
			public ModelMetaInfo findByProviderIdAndModelId(String providerId, String modelId) {
				return null;
			}

			@Override
			public String findApi(String providerId, String modelId) {
				if (!"openai".equals(providerId))
					return null;
				return switch (modelId) {
				case "gpt-5.5-pro" -> "responses";
				case "gpt-6-old" -> "chat-completions";
				default -> null;
				};
			}
		};
		assertTrue(OpenAiApiRouting.usesResponsesApi(library, "gpt-5.5-pro"), "named by the library");
		assertFalse(OpenAiApiRouting.usesResponsesApi(library, "gpt-6-old"), "named by the library");
		assertTrue(OpenAiApiRouting.usesResponsesApi(library, "gpt-6.1-sol"), "by its version");
		assertFalse(OpenAiApiRouting.usesResponsesApi(library, "gpt-5.5"), "by its version");
		assertFalse(OpenAiApiRouting.usesResponsesApi(null, "gpt-5.5-pro"), "no library: by its version");
		assertFalse(OpenAiApiRouting.usesResponsesApi(library, null));
	}
}
