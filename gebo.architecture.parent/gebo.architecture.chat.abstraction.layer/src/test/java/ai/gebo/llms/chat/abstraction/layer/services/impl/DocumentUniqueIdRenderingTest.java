/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.knowledgebase.repositories.uniqueid.VirtualFilesystemUniqueIds;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins the documentUniqueId shown with each fragment: the one ingested with it,
 * else the one stored for its document code (contents ingested before it existed).
 */
class DocumentUniqueIdRenderingTest {

	@Test
	void theIngestedUniqueIdIsUsedWithoutLookingItUp() {
		VirtualFilesystemUniqueIds uniqueIds = mock(VirtualFilesystemUniqueIds.class);

		assertEquals(12L, StandardDocumentRenderers.documentUniqueId(Map.of(DocumentMetaInfos.GEBO_UNIQUE_ID, 12), "doc",
				uniqueIds));
		assertEquals(13L, StandardDocumentRenderers.documentUniqueId(Map.of(DocumentMetaInfos.GEBO_UNIQUE_ID, "13.0"),
				"doc", uniqueIds));
		verify(uniqueIds, never()).documentUniqueId("doc");
	}

	@Test
	void anOlderContentGetsTheUniqueIdOfItsDocumentCode() {
		VirtualFilesystemUniqueIds uniqueIds = mock(VirtualFilesystemUniqueIds.class);
		when(uniqueIds.documentUniqueId("doc")).thenReturn(7L);

		assertEquals(7L, StandardDocumentRenderers.documentUniqueId(Map.of(), "doc", uniqueIds));
		assertNull(StandardDocumentRenderers.documentUniqueId(Map.of(), "doc", null));
		assertNull(StandardDocumentRenderers.documentUniqueId(null, null, uniqueIds));
	}

	@Test
	void theFragmentShowsItsDocumentUniqueId() {
		StandardDocumentRenderers.DocumentDocumentContentRenderer renderer = new StandardDocumentRenderers.DocumentDocumentContentRenderer();
		Document fragment = Document.builder().id("f1").text("text")
				.metadata(Map.of(DocumentMetaInfos.CONTENT_CODE, "doc", DocumentMetaInfos.GEBO_UNIQUE_ID, 21L)).build();

		String rendered = renderer.render(fragment);

		assertTrue(rendered.contains("documentUniqueId: 21"), rendered);
	}
}
