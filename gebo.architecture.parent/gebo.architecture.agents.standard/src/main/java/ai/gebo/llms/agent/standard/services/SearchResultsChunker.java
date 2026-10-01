/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.ChunkingPolicy;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.model.DocumentMetaInfos;
import reactor.core.publisher.Flux;

/**
 * Turns external {@link SearchResult}s - handles to contents living in the
 * searched systems - into content-bearing Spring AI {@link Document}s, loading
 * them through the {@link IDocumentsChunkService} (which streams each result
 * from its originating search service and parses it with the ingestion readers).
 * <p>
 * Shared by the external search agents and the search tools, so both feed the
 * ranker with the same kind of LLM-sized chunks.
 */
public final class SearchResultsChunker {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchResultsChunker.class);
	/** Target chunk size when feeding chunks to an LLM (larger than the embedding default of 512). */
	public static final int LLM_CHUNK_TOKENS = 1024;
	/** Cache-file batching granularity for the chunking service. */
	public static final long DEFAULT_TOKENS_PER_CHUNK_SET = 50000L;
	/** Minimum length of a word to be kept as a matching keyword. */
	public static final int MIN_KEYWORD_LENGTH = 3;
	/** Documents chunked in parallel. */
	private static final int DOCUMENTS_CONCURRENCY = 4;
	/**
	 * Longest time a single document is loaded and chunked for: a site that does not
	 * answer only loses its own document.
	 */
	public static final Duration DOCUMENT_TIMEOUT = Duration.ofSeconds(60);

	private SearchResultsChunker() {
	}

	/**
	 * Chunking parameters tuned for feeding chunks to an LLM rather than for
	 * embedding: chunks of {@value #LLM_CHUNK_TOKENS} tokens, at most
	 * {@code maxNumChunks} per document. When keywords are available the tail of
	 * a document beyond {@code perDocumentBudget} tokens is kept only where it
	 * matches them.
	 */
	public static ChunkingParams buildChunkingParams(int perDocumentBudget, int maxNumChunks,
			List<String> keywords) {
		final TextChunkingSpecs specs = TextChunkingSpecs.of(LLM_CHUNK_TOKENS,
				TextChunkingSpecs.MIN_CHUNKS_LENGTH_TO_EMBED, Math.max(1, maxNumChunks));
		final List<String> matchingKeywords = keywords != null
				? keywords.stream().filter(k -> k != null && !k.isBlank()).map(String::trim).distinct().toList()
				: List.of();
		final ChunkingParams params = new ChunkingParams();
		params.setChunkingSpecs(List.of(specs));
		params.setEnrichWithMetaData(true);
		params.setTokensPerChunkSet(DEFAULT_TOKENS_PER_CHUNK_SET);
		if (!matchingKeywords.isEmpty()) {
			params.setChunkingPolicy(ChunkingPolicy.MATCHING_CHUNKS_AFTER_THREASHOLD);
			params.setTokensThreashold(Math.max(1, perDocumentBudget));
			params.setMatchingKeywords(matchingKeywords);
			params.setKeywordHits(1);
		} else {
			params.setChunkingPolicy(ChunkingPolicy.SPLIT_CHUNKS);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buildChunkingParams(...) perDocumentBudget:" + perDocumentBudget + " (tok) maxNumChunks:"
					+ maxNumChunks + " policy:" + params.getChunkingPolicy() + " matchingKeywords:"
					+ matchingKeywords.size());
		}
		return params;
	}

	/**
	 * Chunks the given search results into documents. Error chunks and blank
	 * chunks are skipped, no more than {@code maxChunksPerDocument} chunks are kept
	 * per source result, and every document carries the code of its source result
	 * as {@link DocumentMetaInfos#CONTENT_CODE}. The chunking session is disposed
	 * before returning, whatever happens.
	 *
	 * @param callerId the agent or tool asking, for the logs
	 */
	public static List<Document> chunkToDocuments(IDocumentsChunkService chunkingService, List<SearchResult> results,
			ChunkingParams params, int maxChunksPerDocument, String callerId) {
		return chunkToDocuments(chunkingService, results, params, maxChunksPerDocument, callerId, DOCUMENT_TIMEOUT);
	}

	/**
	 * Chunks the given search results into documents, best effort: each document is
	 * loaded on its own, and one that fails or takes longer than
	 * {@code documentTimeout} (a site that refuses the connection or does not answer)
	 * is skipped, the others are kept.
	 *
	 * @see #chunkToDocuments(IDocumentsChunkService, List, ChunkingParams, int, String)
	 */
	public static List<Document> chunkToDocuments(IDocumentsChunkService chunkingService, List<SearchResult> results,
			ChunkingParams params, int maxChunksPerDocument, String callerId, Duration documentTimeout) {
		if (results == null || results.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("chunkToDocuments(...) caller:" + callerId + " has no search result to chunk");
			}
			return new ArrayList<>();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin chunkToDocuments(...) caller:" + callerId + " searchResults:" + results.size()
					+ " maxChunksPerDocument:" + maxChunksPerDocument);
		}
		final String chunkingSession = chunkingService.createChunkingSession("search-chunks:" + UUID.randomUUID());
		try {
			final AtomicInteger skippedDocuments = new AtomicInteger(0);
			List<IDocumentChunkWithRef> chunks = Flux.fromIterable(results).flatMap(result -> Flux
					.defer(() -> chunkingService.streamChunks(result, params, chunkingSession)).timeout(documentTimeout)
					.onErrorResume(th -> {
						skippedDocuments.incrementAndGet();
						LOGGER.warn("chunkToDocuments(...) caller:" + callerId + " skipped the document:"
								+ result.getCode() + " that could not be loaded: " + th);
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Loading failure of document:" + result.getCode(), th);
						}
						return Flux.empty();
					}), DOCUMENTS_CONCURRENCY).collectList().block();
			if (skippedDocuments.get() > 0) {
				LOGGER.warn("chunkToDocuments(...) caller:" + callerId + " skipped " + skippedDocuments.get() + " of "
						+ results.size() + " document(s) that could not be loaded");
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("chunkToDocuments(...) produced " + (chunks != null ? chunks.size() : 0)
						+ " raw chunk(s) in session:" + chunkingSession);
			}
			List<Document> documents = new ArrayList<>();
			if (chunks != null) {
				final Map<String, Integer> chunksPerDocument = new HashMap<>();
				int cappedOut = 0;
				int errors = 0;
				for (IDocumentChunkWithRef chunkWithRef : chunks) {
					if (chunkWithRef == null || chunkWithRef.isErrorState() || chunkWithRef.getChunk() == null) {
						if (chunkWithRef != null && chunkWithRef.isErrorState()) {
							errors++;
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("chunkToDocuments(...) caller:" + callerId + " skipped an error chunk of:"
										+ (chunkWithRef.getDocumentRef() != null
												? chunkWithRef.getDocumentRef().getCode()
												: null));
							}
						}
						continue;
					}
					DocumentChunk chunk = chunkWithRef.getChunk();
					if (chunk.getChunkData() == null || chunk.getChunkData().isBlank()) {
						continue;
					}
					final String sourceCode = chunk.getOriginalDocumentCode() != null ? chunk.getOriginalDocumentCode()
							: "";
					final int kept = chunksPerDocument.getOrDefault(sourceCode, 0);
					if (kept >= maxChunksPerDocument) {
						cappedOut++;
						continue;
					}
					chunksPerDocument.put(sourceCode, kept + 1);
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("<SEARCH_CHUNK document=" + sourceCode + " nr=" + (kept + 1) + ">");
						LOGGER.trace(chunk.getChunkData());
						LOGGER.trace("</SEARCH_CHUNK>");
					}
					final Map<String, Object> metaData = new HashMap<>();
					if (chunk.getMetaData() != null) {
						metaData.putAll(chunk.getMetaData());
					}
					if (!sourceCode.isEmpty()) {
						metaData.putIfAbsent(DocumentMetaInfos.CONTENT_CODE, sourceCode);
					}
					documents.add(new Document(chunk.getChunkData(), metaData));
				}
				if (LOGGER.isDebugEnabled() && (cappedOut > 0 || errors > 0)) {
					LOGGER.debug("chunkToDocuments(...) caller:" + callerId + " dropped " + cappedOut
							+ " chunk(s) over the per-document cap of " + maxChunksPerDocument + " across "
							+ chunksPerDocument.size() + " source document(s), " + errors + " error chunk(s)");
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End chunkToDocuments(...) caller:" + callerId + " kept " + documents.size()
						+ " content document(s)");
			}
			return documents;
		} finally {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Disposing the chunking session:" + chunkingSession + " of caller:" + callerId);
			}
			chunkingService.disposeChunkingSession(chunkingSession);
		}
	}

	/**
	 * Splits a text into words usable as matching keywords: the relevance filter on
	 * the tail of large documents.
	 */
	public static List<String> keywordsFromText(String text) {
		if (text == null || text.isBlank()) {
			return List.of();
		}
		return Arrays.stream(text.split("\\W+")).map(String::trim).filter(word -> word.length() >= MIN_KEYWORD_LENGTH)
				.distinct().toList();
	}
}
