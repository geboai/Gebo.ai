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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.DeleteByQueryRequest;
import org.opensearch.client.opensearch.core.DeleteByQueryResponse;

/**
 * Pins the deletions the full-text erasure component asks the index for, on the
 * fields the indexer writes ({@code OpenSearchFullTextChunkIndexService#toOpenSearchDoc}):
 * a knowledge base by {@code knowledgebase_code}, a project by {@code project_code},
 * a data source by its parent project plus the content codes descending from its
 * root item ({@code <root knowledge base>/<project>/<data source>/...}).
 */
class OpenSearchFullTextChunkDeletionTest {

	private OpenSearchClient client;
	private OpenSearchFullTextChunkIndexService service;

	@BeforeEach
	void setUp() throws Exception {
		client = mock(OpenSearchClient.class);
		DeleteByQueryResponse response = mock(DeleteByQueryResponse.class);
		when(response.deleted()).thenReturn(7L);
		when(client.deleteByQuery(any(DeleteByQueryRequest.class))).thenReturn(response);
		service = new OpenSearchFullTextChunkIndexService(client);
	}

	private DeleteByQueryRequest sent() throws Exception {
		ArgumentCaptor<DeleteByQueryRequest> request = ArgumentCaptor.forClass(DeleteByQueryRequest.class);
		verify(client).deleteByQuery(request.capture());
		assertEquals("kb_chunks", request.getValue().index().get(0));
		return request.getValue();
	}

	@Test
	void aKnowledgeBaseIsDeletedByItsRootKnowledgeBaseCode() throws Exception {
		assertEquals(7L, service.deleteByKnowledgeBase("kb"));

		Query query = sent().query();
		assertEquals("knowledgebase_code", query.term().field());
		assertEquals("kb", query.term().value().stringValue());
	}

	@Test
	void aProjectIsDeletedByItsParentProjectCode() throws Exception {
		service.deleteByProject("project");

		Query query = sent().query();
		assertEquals("project_code", query.term().field());
		assertEquals("project", query.term().value().stringValue());
	}

	@Test
	void aDataSourceIsDeletedByItsProjectAndTheCodesDescendingFromItsRootItem() throws Exception {
		service.deleteByProjectEndpoint("project", "my*source");

		Query query = sent().query();
		assertEquals("project_code", query.bool().filter().get(0).term().field());
		assertEquals("project", query.bool().filter().get(0).term().value().stringValue());
		assertEquals("content_code", query.bool().filter().get(1).wildcard().field());
		// the code's own wildcard characters are matched literally
		assertEquals("*/project/my\\*source/*", query.bool().filter().get(1).wildcard().value());
	}

	@Test
	void noScopeNoDeletion() throws Exception {
		assertEquals(0L, service.deleteByKnowledgeBase(" "));
		assertEquals(0L, service.deleteByProject(null));
		assertEquals(0L, service.deleteByProjectEndpoint("project", ""));
		verify(client, never()).deleteByQuery(any(DeleteByQueryRequest.class));
	}
}
