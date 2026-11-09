/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;

class GPipelineStepToolingConfigTest {

	@Test
	void aStepSharedByTwoPipelinesHasOneConfigurationUnderEach() {
		assertNotEquals(GPipelineStepToolingConfig.codeOf("open-chat", "a-step"),
				GPipelineStepToolingConfig.codeOf(ChatPipelinesConfiguration.DEFAULT_PIPELINE, "a-step"));
	}

	@Test
	void aNullPipelineIsTheDefaultOne() {
		assertEquals(GPipelineStepToolingConfig.codeOf(ChatPipelinesConfiguration.DEFAULT_PIPELINE, "a-step"),
				GPipelineStepToolingConfig.codeOf(null, "a-step"));
		assertEquals(ChatPipelinesConfiguration.DEFAULT_PIPELINE,
				GPipelineStepToolingConfig.of(null, "a-step").getPipelineCode());
	}

	@Test
	void aNewConfigurationCarriesItsKey() {
		final GPipelineStepToolingConfig config = GPipelineStepToolingConfig.of("open-chat", "a-step");

		assertEquals(GPipelineStepToolingConfig.codeOf("open-chat", "a-step"), config.getCode());
		assertEquals("open-chat", config.getPipelineCode());
		assertEquals("a-step", config.getPipelineStepId());
	}
}
