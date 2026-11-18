/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ConversationFilesConfigTest {

	@Test
	void theFilesTakeAShareOfTheAgentsBudgetThatGrowsWithTheModel() {
		final ConversationFilesConfig config = new ConversationFilesConfig();
		assertEquals(20000, config.getMaxTokensPerFile());
		assertEquals(100000, config.maxTokens(400000));
		assertEquals(32000, config.maxTokens(128000));
		config.setMaxShareOfBudget(1.7);
		assertEquals(128000, config.maxTokens(128000));
		config.setMaxShareOfBudget(-1);
		assertEquals(0, config.maxTokens(128000));
	}

	@Test
	void disabledOrWithNoBudgetTheFilesTakeNothing() {
		final ConversationFilesConfig config = new ConversationFilesConfig();
		assertEquals(0, config.maxTokens(0));
		config.setEnabled(false);
		assertEquals(0, config.maxTokens(400000));
	}
}
