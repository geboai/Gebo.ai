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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;

/** Pins what fills the additional rules slot and how a prompt without the slot gets it. */
class AgentAdditionalRulesTest {
	private static final String SLOT_NAME = AgentPromptTemplateParams.ADDITIONAL_RULES_SLOT_TEMPLATE_PARAM;

	private static AIToolsAndResourcesLibraryType library(String rules) {
		AIToolsAndResourcesLibraryType library = new AIToolsAndResourcesLibraryType();
		library.setId("a-library");
		library.setAgentRules(rules);
		return library;
	}

	private static GPromptTemplateConfig prompt(String system) {
		GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setPromptUse("a-use");
		prompt.setSystemPromptTemplate(system);
		return prompt;
	}

	@Test
	void theSlotIsNamedAsTheUserAskedAndAPromptMayNameIt() {
		assertEquals("additionalRulesSlot", SLOT_NAME);
		assertTrue(new GAgentPromptPlaceholdersDeclaration().declaredPlaceholders().contains(SLOT_NAME));
	}

	@Test
	void withNoRulesTheSlotIsFilledWithAnEmptyText() {
		assertEquals("", AgentAdditionalRules.of(null));
		assertEquals("", AgentAdditionalRules.of(List.of()));
		assertEquals("", AgentAdditionalRules.of(Arrays.asList(null, library(null), library("  "))));
	}

	@Test
	void theRulesOfTheLibrariesFollowEachOtherEachOnce() {
		assertEquals("first\r\n\r\nsecond",
				AgentAdditionalRules.of(List.of(library(" first "), library("second"), library("first"))));
	}

	@Test
	void aPromptNamingTheSlotOrWithNoRulesIsLeftAsItIs() {
		GPromptTemplateConfig naming = prompt("Do this.\n{additionalRulesSlot}\nThen that.");
		GPromptTemplateConfig notNaming = prompt("Do this.");

		assertSame(naming, AgentAdditionalRules.withSlot(naming, "a rule"));
		assertSame(notNaming, AgentAdditionalRules.withSlot(notNaming, ""));
		assertSame(notNaming, AgentAdditionalRules.withSlot(notNaming, null));
		assertNull(AgentAdditionalRules.withSlot(null, "a rule"));
	}

	@Test
	void aPromptSavedWithoutTheSlotGetsItInTheCopyOfTheRunOnly() {
		GPromptTemplateConfig saved = prompt("Do this.");
		assertFalse(saved.getPlaceholders().containsKey(SLOT_NAME), "the placeholders are computed before the copy");

		GPromptTemplateConfig patched = AgentAdditionalRules.withSlot(saved, "a rule");

		assertNotSame(saved, patched);
		assertEquals("Do this.\r\n\r\n{additionalRulesSlot}", patched.getSystemPromptTemplate());
		assertTrue(patched.getPlaceholders().containsKey(SLOT_NAME));
		assertEquals("Do this.", saved.getSystemPromptTemplate(), "what is saved stays as it is");
		assertFalse(saved.getPlaceholders().containsKey(SLOT_NAME));
	}

	@Test
	void aPromptWithNoSystemTemplateGetsTheSlotAlone() {
		assertEquals("{additionalRulesSlot}",
				AgentAdditionalRules.withSlot(prompt(null), "a rule").getSystemPromptTemplate());
	}

	@Test
	void theRulesAreForTheModelTheyAreNotPartOfTheLibraryTheApiDescribes() throws Exception {
		assertFalse(new ObjectMapper().writeValueAsString(library("a rule")).contains("a rule"));
	}
}
