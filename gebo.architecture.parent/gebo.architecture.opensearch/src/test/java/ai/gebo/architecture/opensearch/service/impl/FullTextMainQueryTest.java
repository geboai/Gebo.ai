/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.opensearch.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.Test;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch._types.query_dsl.Query;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonGenerator;

/**
 * Pins the full-text query: the words as written weighed first, without their accents
 * weighed less, close together weighed more, and most of the words, not all of them.
 */
class FullTextMainQueryTest {

	private static JsonObject json(Query query) {
		final JacksonJsonpMapper mapper = new JacksonJsonpMapper();
		final StringWriter writer = new StringWriter();
		try (JsonGenerator generator = mapper.jsonProvider().createGenerator(writer)) {
			query.serialize(generator, mapper);
		}
		return Json.createReader(new StringReader(writer.toString())).readObject();
	}

	@Test
	void theWordsAsWrittenWithoutAccentsAndCloseTogether() {
		final JsonObject bool = json(OpenSearchFullTextChunkSearchService.buildMainQuery("Svabhavat nella Dottrina"))
				.getJsonObject("bool");
		final JsonArray should = bool.getJsonArray("should");

		assertEquals(3, should.size());
		assertEquals("1", bool.get("minimum_should_match").toString().replace("\"", ""));

		final JsonObject asWritten = should.getJsonObject(0).getJsonObject("multi_match");
		assertTrue(asWritten.getJsonArray("fields").toString().contains("content^4"));
		assertEquals(OpenSearchFullTextChunkSearchService.MINIMUM_WORDS_MATCHING,
				asWritten.getString("minimum_should_match"));
		assertFalse(asWritten.containsKey("operator"), "most of the words, not all of them");

		final JsonObject folded = should.getJsonObject(1).getJsonObject("multi_match");
		assertTrue(folded.getJsonArray("fields").toString().contains("content.folded^2"));
		assertTrue(folded.getJsonArray("fields").toString().contains("document_title.folded"));
		assertEquals(OpenSearchFullTextChunkSearchService.MINIMUM_WORDS_MATCHING,
				folded.getString("minimum_should_match"));

		final JsonObject near = should.getJsonObject(2).getJsonObject("match_phrase").getJsonObject("content.folded");
		assertEquals("Svabhavat nella Dottrina", near.getString("query"));
		assertEquals(2, near.getInt("slop"));
	}
}
