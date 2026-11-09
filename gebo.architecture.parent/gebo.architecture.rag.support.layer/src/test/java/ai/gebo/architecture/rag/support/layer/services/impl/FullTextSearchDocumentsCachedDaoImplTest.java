package ai.gebo.architecture.rag.support.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;

/**
 * Pins that the full-text search, like the semantic one, searches nothing
 * without a knowledge base: the index drops an empty knowledge base term, so the
 * query would reach every indexed chunk under the ACL alone.
 */
class FullTextSearchDocumentsCachedDaoImplTest {
	private final IGFullTextSearchService index = mock(IGFullTextSearchService.class);
	private final FullTextSearchDocumentsCachedDaoImpl dao = new FullTextSearchDocumentsCachedDaoImpl(index);

	private static FullTextSearchMetaDataFilter of(List<String> knowledgeBases) {
		final FullTextSearchMetaDataFilter filter = new FullTextSearchMetaDataFilter();
		filter.setKnowledgebaseCodes(knowledgeBases);
		filter.setAclAliases(List.of(1));
		return filter;
	}

	@Test
	void withoutAKnowledgeBaseNothingIsSearched() throws Exception {
		assertEquals(0, dao.search("invoices", 10, of(List.of())).countFragments());
		assertEquals(0, dao.search(List.of("invoices"), 10, of(null)).countFragments());
		assertEquals(0, dao.search("invoices", 10, null).countFragments());

		verify(index, never()).search(anyString(), anyInt(), any());
		verify(index, never()).search(anyList(), anyInt(), any());
	}

	@Test
	void withAKnowledgeBaseTheIndexIsSearched() throws Exception {
		when(index.search(anyString(), anyInt(), any())).thenReturn(List.of());

		dao.search("invoices", 10, of(List.of("kb-1")));

		verify(index).search(anyString(), anyInt(), any());
	}
}
