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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.GPromptTemplateLibraryReference;
import ai.gebo.architecture.ai.service.PromptTemplateProvidersImplementation;

/**
 * Pins the loading of a prompts library entry's tools results part: optional, unlike
 * the system and user templates.
 */
class ToolsResultsPromptLoadingTest {

	private static GPromptTemplateLibraryReference reference(String use, String toolsResults) {
		GPromptTemplateLibraryReference reference = new GPromptTemplateLibraryReference();
		reference.setPromptUse(use);
		reference.setLangCode("en");
		reference.setDescription(use);
		reference.setChatHistory(ContextContentRequired.REQUIRED);
		reference.setContextDocuments(ContextContentRequired.REQUIRED);
		reference.setToolsCalling(ContextContentRequired.REQUIRED);
		reference.setSystemReference("/prompts-closing-test/system.txt");
		reference.setUserReference("/prompts-closing-test/user.txt");
		reference.setToolsResultsReference(toolsResults);
		return reference;
	}

	@Test
	void anEntryMayCloseItsToolsResults() throws Exception {
		List<GPromptTemplateConfig> prompts = new PromptTemplateProvidersImplementation(this,
				List.of(reference("with-closing", "/prompts-closing-test/tools-results.txt"),
						reference("without-closing", null), reference("blank-closing", " ")))
				.promptsList();

		for (GPromptTemplateConfig prompt : prompts) {
			switch (prompt.getPromptUse()) {
			case "with-closing" -> assertEquals("Answer in the language of the request.",
					prompt.getToolsResultsPromptTemplate().strip());
			default -> assertNull(prompt.getToolsResultsPromptTemplate(), prompt.getPromptUse());
			}
		}
		assertEquals(3, prompts.size());
	}
}
