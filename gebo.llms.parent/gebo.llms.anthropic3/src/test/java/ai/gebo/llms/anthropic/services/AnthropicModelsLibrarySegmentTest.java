/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.anthropic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.anthropic.config.AnthropicModelsLibrary;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/**
 * Pins the Anthropic segment of the models library: the models that cannot turn
 * thinking off and refuse sampling are covered, Mythos Preview has no xhigh, the
 * older models have no rule.
 */
class AnthropicModelsLibrarySegmentTest {

	@Test
	void modelsThatCannotTurnThinkingOffAreCovered() throws Exception {
		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(new ModelsLibraryConfig(), List.of(GProviderModelsLibrary
				.load(AnthropicModelsLibrary.YML, AnthropicModelsLibrary.PREFIX, AnthropicModelsLibrary.class)));

		for (String id : List.of("claude-sonnet-5-5", "claude-opus-5-5", "claude-fable-5-1", "claude-mythos-5")) {
			ModelParameterRules rules = library.findParameterRules("anthropic", id);
			assertEquals(Sampling.REJECTED, rules.getSampling(), id);
			assertFalse(rules.acceptsEffort(ModelParameterRules.NO_REASONING_EFFORT), id);
			assertTrue(rules.acceptsEffort("xhigh"), id);
		}
		ModelParameterRules preview = library.findParameterRules("anthropic", "claude-mythos-preview");
		assertFalse(preview.acceptsEffort("xhigh"));
		assertTrue(preview.acceptsEffort("max"));
		assertNull(library.findParameterRules("anthropic", "claude-sonnet-4-5"));
		assertNull(library.findParameterRules("aws-bedrock", "claude-sonnet-5-5"), "a rule is its provider's only");
	}
}
