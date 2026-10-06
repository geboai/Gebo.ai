/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
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
import ai.gebo.architecture.search.config.OpenNetworkLoadingConfig;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultsLoading;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

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
	/**
	 * Documents loaded and chunked in parallel when no configuration says otherwise
	 * (see {@code ai.gebo.agents.standard.search-documents-parallelism}).
	 */
	public static final int DEFAULT_DOCUMENTS_PARALLELISM = 2;
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
		return chunkToDocuments(chunkingService, results, params, maxChunksPerDocument, callerId, DOCUMENT_TIMEOUT,
				DEFAULT_DOCUMENTS_PARALLELISM);
	}

	/**
	 * The same, {@code documentsParallelism} documents loaded and chunked at the same
	 * time.
	 */
	public static List<Document> chunkToDocuments(IDocumentsChunkService chunkingService, List<SearchResult> results,
			ChunkingParams params, int maxChunksPerDocument, String callerId, int documentsParallelism) {
		return chunkToDocuments(chunkingService, results, params, maxChunksPerDocument, callerId, DOCUMENT_TIMEOUT,
				documentsParallelism);
	}

	/**
	 * Chunks the given search results into documents, best effort, the default number of
	 * documents at the same time.
	 *
	 * @see #chunkToDocuments(IDocumentsChunkService, List, ChunkingParams, int, String, Duration, int)
	 */
	public static List<Document> chunkToDocuments(IDocumentsChunkService chunkingService, List<SearchResult> results,
			ChunkingParams params, int maxChunksPerDocument, String callerId, Duration documentTimeout) {
		return chunkToDocuments(chunkingService, results, params, maxChunksPerDocument, callerId, documentTimeout,
				DEFAULT_DOCUMENTS_PARALLELISM);
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
			ChunkingParams params, int maxChunksPerDocument, String callerId, Duration documentTimeout,
			int documentsParallelism) {
		return load(chunkingService, results, params, maxChunksPerDocument, callerId, documentTimeout,
				documentsParallelism, SearchResultsLoading.RELIABLE, null).documents();
	}

	/** A search result that gave no content to read, and why. */
	public record NotLoaded(SearchResult result, String reason) {
	}

	/** The contents of the results loaded, and the results that gave none, with why. */
	public record LoadedResults(List<Document> documents, List<NotLoaded> notLoaded) {
	}

	/** Why a result loaded with no error gave nothing to read: its cause is in the log. */
	public static final String NO_READABLE_CONTENT = "no readable content: the page could not be downloaded or read";

	/**
	 * Loads and chunks the given search results as their service says
	 * ({@link SearchResultsLoading}), best effort, telling the results that gave no
	 * content and why.
	 *
	 * @param openNetwork the settings of the {@link SearchResultsLoading#OPEN_NETWORK}
	 *                    loading, the defaults when null
	 */
	public static LoadedResults load(IDocumentsChunkService chunkingService, List<SearchResult> results,
			ChunkingParams params, int maxChunksPerDocument, String callerId, int documentsParallelism,
			SearchResultsLoading loading, OpenNetworkLoadingConfig openNetwork) {
		return load(chunkingService, results, params, maxChunksPerDocument, callerId, DOCUMENT_TIMEOUT,
				documentsParallelism, loading, openNetwork);
	}

	static LoadedResults load(IDocumentsChunkService chunkingService, List<SearchResult> results,
			ChunkingParams params, int maxChunksPerDocument, String callerId, Duration documentTimeout,
			int documentsParallelism, SearchResultsLoading loading, OpenNetworkLoadingConfig openNetwork) {
		final int parallelism = documentsParallelism > 0 ? documentsParallelism : DEFAULT_DOCUMENTS_PARALLELISM;
		if (results == null || results.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("chunkToDocuments(...) caller:" + callerId + " has no search result to chunk");
			}
			return new LoadedResults(new ArrayList<>(), List.of());
		}
		final boolean openNetworkLoading = loading == SearchResultsLoading.OPEN_NETWORK;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin chunkToDocuments(...) caller:" + callerId + " searchResults:" + results.size()
					+ " maxChunksPerDocument:" + maxChunksPerDocument + " loading:"
					+ (openNetworkLoading ? SearchResultsLoading.OPEN_NETWORK
							: SearchResultsLoading.RELIABLE + " documentsParallelism:" + parallelism));
		}
		final String chunkingSession = chunkingService.createChunkingSession("search-chunks:" + UUID.randomUUID());
		// the first reason each result gave no content for, by result code
		final Map<String, String> reasons = new ConcurrentHashMap<>();
		try {
			// the user's identity, sampled here: the documents after the first ones start on
			// the chunking threads, which have none
			final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
			final List<IDocumentChunkWithRef> chunks = openNetworkLoading
					? openNetworkChunks(chunkingService, results, params, callerId, chunkingSession, runAs,
							openNetwork != null ? openNetwork : new OpenNetworkLoadingConfig(), reasons)
					: reliableChunks(chunkingService, results, params, callerId, chunkingSession, runAs, documentTimeout,
							parallelism, reasons);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("chunkToDocuments(...) produced " + (chunks != null ? chunks.size() : 0)
						+ " raw chunk(s) in session:" + chunkingSession);
			}
			final List<Document> documents = toDocuments(chunks, maxChunksPerDocument, callerId);
			final Set<String> withContent = new HashSet<>();
			for (Document document : documents) {
				final Object code = document.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
				if (code != null) {
					withContent.add(code.toString());
				}
			}
			final List<NotLoaded> notLoaded = new ArrayList<>();
			for (SearchResult result : results) {
				if (result == null || result.getCode() != null && withContent.contains(result.getCode())) {
					// read, maybe in part
					continue;
				}
				final String reason = result.getCode() != null ? reasons.get(result.getCode()) : null;
				notLoaded.add(new NotLoaded(result, reason != null ? reason : NO_READABLE_CONTENT));
			}
			if (!notLoaded.isEmpty()) {
				LOGGER.warn("chunkToDocuments(...) caller:" + callerId + " read " + (results.size() - notLoaded.size())
						+ " of " + results.size() + " result(s), " + notLoaded.size() + " gave no content");
				if (LOGGER.isDebugEnabled()) {
					for (NotLoaded missing : notLoaded) {
						LOGGER.debug("chunkToDocuments(...) caller:" + callerId + " no content from:"
								+ missing.result().getCode() + ": " + missing.reason());
					}
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End chunkToDocuments(...) caller:" + callerId + " kept " + documents.size()
						+ " content document(s)");
			}
			return new LoadedResults(documents, notLoaded);
		} finally {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Disposing the chunking session:" + chunkingSession + " of caller:" + callerId);
			}
			chunkingService.disposeChunkingSession(chunkingSession);
		}
	}

	/**
	 * A system sized to answer: {@code parallelism} documents at a time, each skipped
	 * when it fails or takes longer than {@code documentTimeout}.
	 */
	private static List<IDocumentChunkWithRef> reliableChunks(IDocumentsChunkService chunkingService,
			List<SearchResult> results, ChunkingParams params, String callerId, String chunkingSession,
			ReactiveIdentityUtil runAs, Duration documentTimeout, int parallelism, Map<String, String> reasons) {
		final AtomicInteger skippedDocuments = new AtomicInteger(0);
		final List<IDocumentChunkWithRef> chunks = Flux.fromIterable(results).filter(result -> result != null)
				.flatMap(result -> Flux
						.defer(() -> runAs
								.doRunAsWithReturn(() -> chunkingService.streamChunks(result, params, chunkingSession)))
						.timeout(documentTimeout).onErrorResume(th -> {
							skippedDocuments.incrementAndGet();
							LOGGER.warn("chunkToDocuments(...) caller:" + callerId + " skipped the document:"
									+ result.getCode() + " that could not be loaded: " + th);
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Loading failure of document:" + result.getCode(), th);
							}
							remember(reasons, result, reasonOf(th, documentTimeout));
							return Flux.empty();
						}), parallelism)
				.collectList().block();
		if (skippedDocuments.get() > 0) {
			LOGGER.warn("chunkToDocuments(...) caller:" + callerId + " skipped " + skippedDocuments.get() + " of "
					+ results.size() + " document(s) that could not be loaded");
		}
		return chunks;
	}

	/**
	 * Sites of an open network: every candidate (or the first ones, when capped) loaded
	 * at once, grouped by host, each host at most {@code perHostConcurrency} pages at a
	 * time with a pause between its requests, and skipped after
	 * {@code perHostFailuresBeforeSkip} failed pages; each page within its deadline, the
	 * whole loading within the loading phase deadline, what has not arrived by then
	 * left. The reason of every candidate that gives nothing goes to {@code reasons}.
	 */
	static List<IDocumentChunkWithRef> openNetworkChunks(IDocumentsChunkService chunkingService,
			List<SearchResult> results, ChunkingParams params, String callerId, String chunkingSession,
			ReactiveIdentityUtil runAs, OpenNetworkLoadingConfig config, Map<String, String> reasons) {
		List<SearchResult> candidates = results.stream().filter(result -> result != null).toList();
		if (config.isCapped() && candidates.size() > config.effectiveCandidatesCap()) {
			final int cap = config.effectiveCandidatesCap();
			for (SearchResult beyond : candidates.subList(cap, candidates.size())) {
				remember(reasons, beyond, "not loaded: only the first " + cap + " results found are loaded");
			}
			candidates = candidates.subList(0, cap);
		}
		final Map<String, List<SearchResult>> byHost = new LinkedHashMap<>();
		for (SearchResult candidate : candidates) {
			byHost.computeIfAbsent(hostOf(candidate), host -> new ArrayList<>()).add(candidate);
		}
		final Duration perPage = config.perPageDeadline();
		final Duration phase = config.loadingPhaseDeadline();
		final Duration pause = config.perHostPause();
		final int perHost = config.effectivePerHostConcurrency();
		final int skipAfter = config.effectivePerHostFailuresBeforeSkip();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin openNetworkChunks(...) caller:" + callerId + " " + candidates.size() + " of "
					+ results.size() + " candidate(s) from " + byHost.size() + " host(s), per page:"
					+ perPage.toSeconds() + " s, loading phase:" + phase.toSeconds() + " s, per host:" + perHost
					+ " at a time, pause:" + pause.toMillis() + " ms, skipped after " + skipAfter + " failure(s)");
		}
		final Map<String, AtomicInteger> failures = new ConcurrentHashMap<>();
		final Set<SearchResult> started = ConcurrentHashMap.newKeySet();
		final Set<SearchResult> settled = ConcurrentHashMap.newKeySet();
		final List<Flux<IDocumentChunkWithRef>> hosts = new ArrayList<>();
		for (Map.Entry<String, List<SearchResult>> host : byHost.entrySet()) {
			final AtomicInteger hostFailures = failures.computeIfAbsent(host.getKey(), key -> new AtomicInteger());
			hosts.add(Flux.fromIterable(host.getValue()).index()
					// a pause between two requests to the same host
					.delayUntil(indexed -> indexed.getT1() == 0 || pause.isZero() ? Mono.empty() : Mono.delay(pause))
					.map(Tuple2::getT2)
					.flatMap(candidate -> page(chunkingService, candidate, params, chunkingSession, runAs, host.getKey(),
							hostFailures, skipAfter, perPage, callerId, reasons, started, settled), perHost));
		}
		final List<IDocumentChunkWithRef> chunks = new ArrayList<>();
		try {
			// what has not arrived by the end of the loading phase is left
			Flux.merge(hosts).take(phase).doOnNext(chunks::add).blockLast(phase.plus(LOADING_PHASE_MARGIN));
		} catch (RuntimeException e) {
			LOGGER.warn("openNetworkChunks(...) caller:" + callerId + " loading phase did not end in time", e);
		}
		int stillLoading = 0;
		int notRequested = 0;
		for (SearchResult candidate : candidates) {
			if (settled.contains(candidate)) {
				continue;
			}
			if (started.contains(candidate)) {
				stillLoading++;
				remember(reasons, candidate,
						"not loaded: still loading when the loading phase ended after " + phase.toSeconds() + " s");
			} else {
				notRequested++;
				remember(reasons, candidate,
						"not requested: the loading phase ended after " + phase.toSeconds() + " s");
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End openNetworkChunks(...) caller:" + callerId + " " + chunks.size() + " raw chunk(s), "
					+ settled.size() + " candidate(s) done, " + stillLoading + " still loading and " + notRequested
					+ " not requested at the end of the loading phase");
		}
		return chunks;
	}

	/** Margin over the loading phase deadline before the wait for it gives up. */
	static final Duration LOADING_PHASE_MARGIN = Duration.ofSeconds(10);

	/** One page of an open network host, within its deadline, unless its host keeps failing. */
	private static Flux<IDocumentChunkWithRef> page(IDocumentsChunkService chunkingService, SearchResult candidate,
			ChunkingParams params, String chunkingSession, ReactiveIdentityUtil runAs, String host,
			AtomicInteger hostFailures, int skipAfter, Duration perPage, String callerId, Map<String, String> reasons,
			Set<SearchResult> started, Set<SearchResult> settled) {
		return Flux.defer(() -> {
			final int failed = hostFailures.get();
			if (failed >= skipAfter) {
				// a site that keeps failing is not asked more: it may be refusing the requests
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("openNetworkChunks(...) caller:" + callerId + " not requesting:" + candidate.getCode()
							+ ", host:" + host + " failed " + failed + " time(s)");
				}
				remember(reasons, candidate,
						"not requested: its site " + host + " did not answer for " + failed + " of its pages");
				settled.add(candidate);
				return Flux.empty();
			}
			started.add(candidate);
			final AtomicBoolean content = new AtomicBoolean(false);
			// deferred: a loading that throws at once fails this page only
			return Flux
					.defer(() -> runAs
							.doRunAsWithReturn(() -> chunkingService.streamChunks(candidate, params, chunkingSession)))
					.doOnNext(chunk -> {
						if (chunk != null && !chunk.isErrorState() && chunk.getChunk() != null
								&& chunk.getChunk().getChunkData() != null
								&& !chunk.getChunk().getChunkData().isBlank()) {
							content.set(true);
						} else if (chunk != null && chunk.isErrorState()) {
							remember(reasons, candidate, "could not be loaded: " + errorText(chunk));
						}
					}).timeout(perPage).onErrorResume(th -> {
						LOGGER.warn("openNetworkChunks(...) caller:" + callerId + " page:" + candidate.getCode()
								+ " of host:" + host + " not loaded: " + th);
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Loading failure of page:" + candidate.getCode(), th);
						}
						remember(reasons, candidate, reasonOf(th, perPage));
						return Flux.empty();
					}).doOnComplete(() -> {
						settled.add(candidate);
						if (!content.get()) {
							final int now = hostFailures.incrementAndGet();
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("openNetworkChunks(...) caller:" + callerId + " page:" + candidate.getCode()
										+ " gave no content, host:" + host + " failures:" + now);
							}
						}
					});
		});
	}

	/** The host a search result comes from, "" when its address tells none. */
	static String hostOf(SearchResult result) {
		String address = null;
		if (result.getResultReference() != null) {
			address = result.getResultReference().getUri();
		}
		if ((address == null || address.isBlank()) && result.getNavigationReference() != null
				&& result.getNavigationReference().path != null) {
			address = result.getNavigationReference().path.absolutePath;
		}
		if (address == null || address.isBlank()) {
			return "";
		}
		try {
			final String host = URI.create(address.trim()).getHost();
			return host != null ? host.toLowerCase(Locale.ROOT) : "";
		} catch (RuntimeException e) {
			return "";
		}
	}

	private static void remember(Map<String, String> reasons, SearchResult result, String reason) {
		if (result != null && result.getCode() != null) {
			reasons.putIfAbsent(result.getCode(), reason);
		}
	}

	/** Why a loading failed, as told to the model. */
	static String reasonOf(Throwable th, Duration deadline) {
		if (th instanceof TimeoutException || th != null && th.getCause() instanceof TimeoutException) {
			return "not loaded within " + deadline.toSeconds() + " s";
		}
		return "could not be loaded: " + shortText(th != null ? (th.getMessage() != null ? th.getMessage()
				: th.getClass().getSimpleName()) : "unknown error");
	}

	private static String errorText(IDocumentChunkWithRef chunk) {
		final GUserMessage message = chunk.getErrorMessage();
		return shortText(message != null && message.getSummary() != null ? message.getSummary() : "loading error");
	}

	private static String shortText(String text) {
		final String oneLine = text.replaceAll("\\s+", " ").trim();
		return oneLine.length() > MAX_REASON_CHARACTERS ? oneLine.substring(0, MAX_REASON_CHARACTERS) + "..." : oneLine;
	}

	/** Longest failure text told for a result. */
	static final int MAX_REASON_CHARACTERS = 160;

	/**
	 * The chunks as documents: error chunks and chunks with blank content skipped, no
	 * more than {@code maxChunksPerDocument} kept per source document.
	 */
	private static List<Document> toDocuments(List<IDocumentChunkWithRef> chunks, int maxChunksPerDocument,
			String callerId) {
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
		return documents;
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
