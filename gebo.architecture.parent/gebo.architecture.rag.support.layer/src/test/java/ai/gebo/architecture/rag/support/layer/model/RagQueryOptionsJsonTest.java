/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.rag.support.layer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions.CompletenessLevel;
import tools.jackson.databind.json.JsonMapper;

/**
 * The options as an editor sends them: a field left empty arrives as null, which
 * keeps the default instead of refusing the whole request.
 */
class RagQueryOptionsJsonTest {

	private final JsonMapper mapper = JsonMapper.builder().build();

	@Test
	void emptyFieldsKeepTheDefaults() {
		RagQueryOptions options = mapper.readValue("{\"topK\":null,\"similarityThreashold\":null,\"completeness\":null}",
				RagQueryOptions.class);
		assertEquals(RagQueryOptions.DEFAULT_TOPK, options.getTopK());
		assertEquals(RagQueryOptions.DEFAULT_SIMILARITY_THRESHOLD, options.getSimilarityThreashold());
		assertEquals(RagQueryOptions.DEFAULT_MAXTOKENS, options.getMaxTokens());
	}

	@Test
	void givenNumbersAreKept() {
		RagQueryOptions options = mapper.readValue(
				"{\"topK\":12,\"similarityThreashold\":0.7,\"maxTokens\":4000,\"completeness\":\"FULL_DOCUMENTS\"}",
				RagQueryOptions.class);
		assertEquals(12, options.getTopK());
		assertEquals(0.7, options.getSimilarityThreashold());
		assertEquals(4000, options.getMaxTokens());
		assertEquals(CompletenessLevel.FULL_DOCUMENTS, options.getCompleteness());
	}
}
