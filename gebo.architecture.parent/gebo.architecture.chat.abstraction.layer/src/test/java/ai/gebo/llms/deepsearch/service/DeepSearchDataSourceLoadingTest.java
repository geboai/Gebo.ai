/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService.DocumentWithSearchResult;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import reactor.core.publisher.Flux;

/**
 * Pins how the deep search pipelines load what a data source finds: as the deep search
 * tools load it ({@link SearchResultsChunker}), with the same chunks and the same
 * fragments per document, a document that can not be loaded left out.
 */
class DeepSearchDataSourceLoadingTest {

	/** A data source finding the given results, with no model involved. */
	static class FixedResultsDataSource extends GAbstractReactiveDeepSearchDataSourceService<BaseSearchResultsExtractionDataType> {
		private final List<SearchResult> found;

		FixedResultsDataSource(IDocumentsChunkService chunkingService, List<SearchResult> found) {
			super(mock(IGChatModelRuntimeConfigurationDao.class), mock(IGEmbeddingModelRuntimeConfigurationDao.class),
					chunkingService, BaseSearchResultsExtractionDataType.class, mock(IGeboThreadManager.class),
					new DeepSearchDefaultConfig(), mock(IGPromptConfigDao.class));
			this.found = found;
		}

		@Override
		protected List<SearchWithResults> executeSearches(DeepSearchRequest request,
				MinimalChatContext minimalChatContext, DeepSearchConfig deepSearchConfig,
				IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String string, int topK) {
			final SearchWithResults searched = new SearchWithResults();
			searched.setResults(new ArrayList<>(found));
			return List.of(searched);
		}

		@Override
		protected BaseSearchResultsExtractionDataType customStructureConsolidation(
				BaseSearchResultsExtractionDataType actualData, BaseSearchResultsExtractionDataType currentConsolidation) {
			return actualData;
		}

		@Override
		public String getHandlerId() {
			return "fixed";
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public String getDescription(DeepSearchConfig deepSearchConfig) {
			return "fixed results";
		}

		@Override
		public String getProductId() {
			return "fixed";
		}
	}

	private static SearchResult result(String url) {
		final SearchResult result = new SearchResult();
		result.setResultReference(new SearchResultReference());
		result.getResultReference().setUri(url);
		result.setSystemConfigurationCode("intranet");
		return result;
	}

	private static Flux<IDocumentChunkWithRef> chunks(SearchResult result, int count) {
		final List<IDocumentChunkWithRef> chunks = new ArrayList<>();
		for (int i = 1; i <= count; i++) {
			final DocumentChunk chunk = DocumentChunk.ofText(result.getCode(), "chunk " + i + " of " + result.getCode(),
					Map.of());
			chunk.setChunkPosition((long) i);
			chunks.add(IDocumentChunkWithRef.of(chunk, result));
		}
		return Flux.fromIterable(chunks);
	}

	private static ChatPipelineExecutionRuntimeData runtime(String question) {
		final GeboChatRequest request = new GeboChatRequest();
		request.setId("request-1");
		request.setQuery(question);
		final LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request);
		return new ChatPipelineExecutionRuntimeData(null, 10_000, resources, null, null, true);
	}

	@Test
	void theDocumentsFoundAreLoadedAsTheDeepSearchToolsLoadThem() throws Exception {
		final SearchResult huge = result("https://intranet.example/huge");
		final SearchResult small = result("https://intranet.example/small");
		final SearchResult broken = result("https://intranet.example/broken");
		final IDocumentsChunkService chunkingService = mock(IDocumentsChunkService.class);
		when(chunkingService.createChunkingSession(anyString())).thenReturn("session");
		final List<ChunkingParams> paramsUsed = new CopyOnWriteArrayList<>();
		when(chunkingService.streamChunks(any(IGComponentOriginatedDocument.class), any(), anyString()))
				.thenAnswer(invocation -> {
					paramsUsed.add(invocation.getArgument(1));
					final SearchResult asked = invocation.getArgument(0);
					if (asked == broken) {
						return Flux.error(new java.net.ConnectException("Connection refused"));
					}
					return chunks(asked, asked == huge ? 20 : 2);
				});
		final FixedResultsDataSource source = new FixedResultsDataSource(chunkingService, List.of(huge, small, broken));

		final List<DocumentWithSearchResult> loaded = source
				.streamSearchResults(runtime("what does the intranet say about the rotation"), null, null, null,
						"request-session", 10)
				.collectList().block();

		final long ofHuge = loaded.stream().filter(x -> x.getSearchResult() == huge).count();
		final long ofSmall = loaded.stream().filter(x -> x.getSearchResult() == small).count();
		assertEquals(SearchResultsChunker.DEEP_SEARCH_FRAGMENTS_PER_DOCUMENT, ofHuge,
				"a huge document gives the tools' fragments per document, no more");
		assertEquals(2, ofSmall);
		assertTrue(loaded.stream().noneMatch(x -> x.getSearchResult() == broken), "a document not loaded is left out");
		assertTrue(loaded.stream().allMatch(x -> x.getSearchResult().getCode()
				.equals(x.getDocument().getMetadata().get(DocumentMetaInfos.CONTENT_CODE))),
				"each fragment with the result it comes from");
		final TextChunkingSpecs specs = (TextChunkingSpecs) paramsUsed.get(0).getChunkingSpecs().get(0);
		assertEquals(SearchResultsChunker.LLM_CHUNK_TOKENS, specs.getDefaultChunkSize(), "the tools' chunks");
		assertEquals(SearchResultsChunker.DEEP_SEARCH_FRAGMENTS_PER_DOCUMENT, specs.getMaxNumChunks());
	}

	@Test
	void aDataSourceFindingNothingGivesNothing() throws Exception {
		final IDocumentsChunkService chunkingService = mock(IDocumentsChunkService.class);
		final FixedResultsDataSource source = new FixedResultsDataSource(chunkingService, List.of());

		assertEquals(0, source.streamSearchResults(runtime("anything"), null, null, null, "request-session", 10)
				.collectList().block().size());
	}
}
