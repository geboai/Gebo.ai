/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Pins the reading of the request understanding's userRequiredLanguage field: the language
 * the user explicitly asks the answer in, which wins over the one the message is
 * written in; none when the user asks for no language.
 */
class AnswerLanguageTest {

	private static String of(String value) {
		return DefaultRoutingChatPipelineStepServiceImpl.userRequiredLanguage(value == null ? null : List.of(value));
	}

	@Test
	void anAskedLanguageIsNamedCapitalized() {
		assertEquals("English", of("English"));
		assertEquals("English", of(" english "));
		assertEquals("Italian", of("Italian."));
		assertEquals("English", of("English (asked in the latest question)"), "the name, not the comment");
	}

	@Test
	void noAskedLanguageIsNone() {
		assertNull(of(null));
		assertNull(DefaultRoutingChatPipelineStepServiceImpl.userRequiredLanguage(List.of()));
		assertNull(of(""));
		assertNull(of("none"));
		assertNull(of("None."));
		assertNull(of("no"));
		assertNull(of("not asked"));
		assertNull(of("null"));
		assertNull(of("n/a"));
	}
}
