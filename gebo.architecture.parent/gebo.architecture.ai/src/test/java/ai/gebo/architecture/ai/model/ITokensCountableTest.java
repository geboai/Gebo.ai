/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.ai.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Pins the tokens of a map: every value, each sized by itself, never the keys.
 */
class ITokensCountableTest {

	@Test
	void everyValueIsCountedNotOnlyTheLast() {
		final Map<String, Object> params = new LinkedHashMap<>();
		params.put("a", "The first value has several words in it.");
		params.put("b", "The second one too.");
		params.put("c", "And the third.");

		assertEquals(ITokensCountable.stringsTokensSize("The first value has several words in it.")
				+ ITokensCountable.stringsTokensSize("The second one too.")
				+ ITokensCountable.stringsTokensSize("And the third."), ITokensCountable.tokensSize(params));
	}

	@Test
	void theKeysAreNotCounted() {
		final Map<String, Object> params = new HashMap<>();
		params.put("a very long key that would add many tokens if it were counted", "value");

		assertEquals(ITokensCountable.stringsTokensSize("value"), ITokensCountable.tokensSize(params));
	}

	@Test
	void aCountableValueGivesItsOwnSizeANullValueNone() {
		final Map<String, Object> params = new HashMap<>();
		params.put("countable", (ITokensCountable) () -> 42);
		params.put("nothing", null);
		params.put("number", 7);

		assertEquals(42 + ITokensCountable.stringsTokensSize("7"), ITokensCountable.tokensSize(params));
	}

	@Test
	void noMapNoTokens() {
		assertEquals(0, ITokensCountable.tokensSize((Map<String, Object>) null));
		assertEquals(0, ITokensCountable.tokensSize(new HashMap<String, Object>()));
	}
}
