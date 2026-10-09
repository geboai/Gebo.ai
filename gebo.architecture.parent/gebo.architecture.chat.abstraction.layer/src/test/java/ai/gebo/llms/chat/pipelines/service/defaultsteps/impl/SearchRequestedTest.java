/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;

/**
 * Pins the reading of the request understanding's searchRequested field, and that
 * no deliverable stands for a search any more.
 */
class SearchRequestedTest {

	@Test
	void theUserAskedToSearchWhenTheFieldSaysSo() {
		assertTrue(DefaultRoutingChatPipelineStepServiceImpl.searchRequested(List.of("true")));
		assertTrue(DefaultRoutingChatPipelineStepServiceImpl.searchRequested(List.of(" True ")));
		assertTrue(DefaultRoutingChatPipelineStepServiceImpl.searchRequested(List.of("yes")));
		assertTrue(DefaultRoutingChatPipelineStepServiceImpl.searchRequested(List.of("1")));
		assertFalse(DefaultRoutingChatPipelineStepServiceImpl.searchRequested(List.of("false")));
		assertFalse(DefaultRoutingChatPipelineStepServiceImpl.searchRequested(List.of("no")));
		assertFalse(DefaultRoutingChatPipelineStepServiceImpl.searchRequested(List.of()));
		assertFalse(DefaultRoutingChatPipelineStepServiceImpl.searchRequested(null));
	}

	@Test
	void theUserAskedNotToSearchWhenTheFieldSaysNever() {
		assertTrue(DefaultRoutingChatPipelineStepServiceImpl.searchForbidden(List.of("never")));
		assertTrue(DefaultRoutingChatPipelineStepServiceImpl.searchForbidden(List.of(" Never ")));
		assertFalse(DefaultRoutingChatPipelineStepServiceImpl.searchRequested(List.of("never")));
		assertFalse(DefaultRoutingChatPipelineStepServiceImpl.searchForbidden(List.of("true")));
		assertFalse(DefaultRoutingChatPipelineStepServiceImpl.searchForbidden(List.of("false")));
		assertFalse(DefaultRoutingChatPipelineStepServiceImpl.searchForbidden(null));
	}

	@Test
	void noDeliverableStandsForASearch() {
		assertTrue(Arrays.stream(DeliverableIntent.values()).noneMatch(intent -> intent.name().contains("SEARCH")));
	}
}
