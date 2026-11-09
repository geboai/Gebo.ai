/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * A saved intent reads as itself, one removed since as unknown.
 */
public class DeliverableIntentReadingConverterTest {
	private final DeliverableIntentReadingConverter converter = new DeliverableIntentReadingConverter();

	@Test
	public void everyIntentReadsAsItself() {
		for (DeliverableIntent intent : DeliverableIntent.values()) {
			assertEquals(intent, converter.convert(intent.name()));
		}
	}

	@Test
	public void aRemovedIntentReadsAsUnknown() {
		assertEquals(DeliverableIntent.UNKNOWN, converter.convert("PURE_SEARCH"));
	}
}
