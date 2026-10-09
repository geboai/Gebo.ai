/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.systems.abstraction.layer.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.systems.abstraction.layer.controllers.ContentsResetController.ResetContentRequest;
import ai.gebo.systems.abstraction.layer.controllers.ContentsResetController.ResetContentResponse;
import ai.gebo.systems.abstraction.layer.impl.repository.ContentHandshakeDataRepository;

/**
 * Pins the reset of the contents ingestion: it deletes the ingestion
 * acknowledgements of its scope, so the next ingestion indexes every document
 * again, and keeps the document references, where the documents' uniqueIds
 * live.
 */
class ContentsResetControllerTest {
	private DocumentReferenceRepository documents;
	private ContentHandshakeDataRepository acknowledgements;
	private ContentsResetController controller;

	@BeforeEach
	void setUp() {
		documents = mock(DocumentReferenceRepository.class);
		acknowledgements = mock(ContentHandshakeDataRepository.class);
		controller = new ContentsResetController();
		controller.documentRepository = documents;
		controller.handshakeRepository = acknowledgements;
	}

	private static Stream<GDocumentReference> documents(int count) {
		return IntStream.range(0, count).mapToObj(i -> {
			final GDocumentReference document = new GDocumentReference();
			document.setCode("doc-" + i);
			document.setUniqueId((long) i);
			return document;
		});
	}

	@Test
	void aKnowledgeBaseResetDeletesItsAcknowledgementsAndKeepsItsDocuments() {
		when(documents.findByRootKnowledgebaseCode("kb")).thenReturn(documents(250));
		final ResetContentRequest request = new ResetContentRequest();
		request.knowledgeBaseCode = "kb";

		final ResetContentResponse response = controller.resetContentsIngestion(request);

		assertEquals(250, response.resetEntries);
		assertFalse(response.deletedAll);
		@SuppressWarnings("unchecked")
		final ArgumentCaptor<List<String>> deleted = ArgumentCaptor.forClass(List.class);
		verify(acknowledgements, org.mockito.Mockito.times(3)).deleteByContentCodeIn(deleted.capture());
		assertEquals(List.of(100, 100, 50), deleted.getAllValues().stream().map(List::size).toList(),
				"100 documents at a time");
		assertEquals("doc-0", deleted.getAllValues().get(0).get(0));
		assertEquals("doc-249", deleted.getAllValues().get(2).get(49));
		verify(documents).findByRootKnowledgebaseCode("kb");
		verifyNoMoreInteractions(documents);
	}

	@Test
	void aResetWithNoScopeDeletesEveryAcknowledgementAndNoDocument() {
		final ResetContentResponse response = controller.resetContentsIngestion(new ResetContentRequest());

		assertTrue(response.deletedAll);
		verify(acknowledgements).deleteAll();
		verifyNoMoreInteractions(documents);
	}

	@Test
	void aProjectResetReadsItsDocumentsOnly() {
		when(documents.findByParentProjectCode("project")).thenReturn(documents(3));
		final ResetContentRequest request = new ResetContentRequest();
		request.projectCode = "project";

		assertEquals(3, controller.resetContentsIngestion(request).resetEntries);
		verify(acknowledgements).deleteByContentCodeIn(anyList());
		verify(documents).findByParentProjectCode("project");
		verifyNoMoreInteractions(documents);
	}
}
