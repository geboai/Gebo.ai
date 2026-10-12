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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;

/**
 * Pins the reading of a collapsed (per document) hit's source: the client gives it
 * as jakarta.json values, read as plain Java values, so that the metadata of the
 * chunk is not quoted ("\"name.pdf\"") and its numbers stay numbers.
 */
class OpenSearchHitSourceTest {

	@Test
	@SuppressWarnings("unchecked")
	void theSourceOfACollapsedHitIsReadAsPlainValues() {
		JacksonJsonpMapper mapper = new JacksonJsonpMapper();
		String json = "{\"chunk_id\":\"c-1\",\"tokens_length\":120,\"score\":0.5,\"deleted\":false,\"none\":null,"
				+ "\"acl_aliases\":[1,2],\"meta\":{\"geboFileName\":\"name.pdf\",\"geboReferenceType\":\"FILE\"}}";
		JsonData data = JsonData._DESERIALIZER.deserialize(mapper.jsonProvider().createParser(new StringReader(json)),
				mapper);

		Map<String, Object> src = (Map<String, Object>) OpenSearchFullTextChunkSearchService
				.plain(data.to(Map.class));

		assertEquals("c-1", src.get("chunk_id"));
		assertEquals(120, src.get("tokens_length"));
		assertEquals(0.5, src.get("score"));
		assertEquals(Boolean.FALSE, src.get("deleted"));
		assertNull(src.get("none"));
		assertTrue(src.containsKey("none"));
		assertEquals(List.of(1, 2), src.get("acl_aliases"));
		Map<String, Object> meta = (Map<String, Object>) src.get("meta");
		assertEquals("name.pdf", meta.get("geboFileName"));
		assertEquals("FILE", meta.get("geboReferenceType"));
	}
}
