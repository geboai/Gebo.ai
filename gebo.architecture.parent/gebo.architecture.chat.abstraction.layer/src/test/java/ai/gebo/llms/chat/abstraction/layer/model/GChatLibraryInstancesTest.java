/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * The instances a conversation holds are saved by library type id, and a type id
 * may have dots: written and read by Spring Data's own converter, which refuses
 * a map key with a dot.
 */
class GChatLibraryInstancesTest {

	private MappingMongoConverter converter;

	@BeforeEach
	void converter() {
		final MongoMappingContext context = new MongoMappingContext();
		context.afterPropertiesSet();
		converter = new MappingMongoConverter(NoOpDbRefResolver.INSTANCE, context);
		converter.afterPropertiesSet();
	}

	private static Map<String, String> held() {
		final Map<String, String> ids = new LinkedHashMap<>();
		ids.put("working-directory", "a-working-directory");
		ids.put("sandbox:python:3.12-slim", "containers/sandboxes/one");
		ids.put("sandbox:shell:3.20", "containers/sandboxes/two");
		ids.put("sandbox:odd:1%2E0", "containers/sandboxes/three");
		return ids;
	}

	@Test
	void typeIdsWithDotsAreSavedAndReadBack() {
		final GChatLibraryInstances record = new GChatLibraryInstances();
		record.setUserChatContextCode("a-conversation");
		record.setInstanceIds(held());

		final Document saved = new Document();
		converter.write(record, saved);

		final Document keys = saved.get("instanceIds", Document.class);
		assertTrue(keys.keySet().stream().noneMatch(key -> key.contains(".")), keys.keySet().toString());
		final GChatLibraryInstances read = converter.read(GChatLibraryInstances.class, saved);
		assertEquals(held(), read.getInstanceIds());
		assertEquals(held(), record.getInstanceIds());
	}

	@Test
	void aRecordSavedBeforeReadsTheSame() {
		final Document saved = new Document("_id", "a-conversation").append("instanceIds",
				new Document("working-directory", "a-working-directory").append("sandbox:shell:latest",
						"containers/sandboxes/two"));

		final GChatLibraryInstances read = converter.read(GChatLibraryInstances.class, saved);

		assertEquals(Map.of("working-directory", "a-working-directory", "sandbox:shell:latest",
				"containers/sandboxes/two"), read.getInstanceIds());
	}

	@Test
	void noIdsRecordedStaysNone() {
		final GChatLibraryInstances record = new GChatLibraryInstances();
		record.setInstanceIds(null);
		assertNull(record.getInstanceIds());
	}

	@Test
	void escapingIsExact() {
		for (String typeId : new String[] { "a.b", "a%2Eb", "a%25b", "%", "..", "plain" }) {
			assertEquals(typeId, GChatLibraryInstances.unescape(GChatLibraryInstances.escape(typeId)));
			assertTrue(!GChatLibraryInstances.escape(typeId).contains("."));
		}
	}
}
