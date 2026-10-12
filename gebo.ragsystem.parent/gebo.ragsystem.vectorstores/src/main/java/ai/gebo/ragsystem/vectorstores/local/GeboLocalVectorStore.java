/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.content.Content;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.filter.Filter.Expression;

import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorizedFragmentMetadata;
import ai.gebo.ragsystem.vectorstores.local.model.LocalConfig;

/**
 * Embedded vector store of the OSS single-dependency installation: Spring AI's
 * own {@link SimpleVectorStore}, persisted as JSON under the work directory.
 *
 * It exists so that a Gebo.ai installation can do retrieval augmented generation
 * with NO vector database service beside it. That is what makes a Windows install
 * viable, because the obvious alternative - letting MongoDB hold the vectors -
 * is not available: {@code $vectorSearch} is served by the separate
 * {@code mongot} binary, which ships for Linux only and additionally requires a
 * replica set rather than the standalone {@code mongod} the compose files run.
 *
 * Only this class is specific to the embedded product; everything above it - the
 * {@code ai.gebo.vectorstore.use} switch, the factory, the runtime configuration -
 * is the machinery the Qdrant and Redis products use, so the vendor stays a
 * deployment choice.
 *
 * <h2>Intended scope</h2> The corpus lives in memory and every query scans all of
 * it, so this product targets the community / OSS installation and small to
 * medium corpora. A deployment that outgrows it switches
 * {@code ai.gebo.vectorstore.use} to a server-backed product; the store logs a
 * warning once it passes {@link LocalConfig#getWarnAboveFragments()} so the
 * operator finds out before retrieval quality does.
 *
 * <h2>What is inherited and what is replaced</h2> Adding, embedding, deleting by
 * id and the JSON persistence come from {@link SimpleVectorStore} unchanged. The
 * two SEARCH paths are overridden, because Spring AI's filter evaluator throws on
 * a multi-valued metadata attribute - see {@link LocalMetadataFilterEvaluator} for
 * the exact failure and the semantics used instead.
 */
public class GeboLocalVectorStore extends SimpleVectorStore implements IGExtendedVectorStore {

	private final static Logger LOGGER = LoggerFactory.getLogger(GeboLocalVectorStore.class);

	/** File this collection is persisted to. */
	private final Path storeFile;

	/** Settings this store was opened with. */
	private final LocalConfig config;

	/** Correct-by-construction replacement for Spring AI's filter evaluator. */
	private final LocalMetadataFilterEvaluator filterEvaluator = new LocalMetadataFilterEvaluator();

	/** Writes that have not reached {@link #storeFile} yet. */
	private boolean dirty = false;

	/** When the last successful save happened, for the debounce. */
	private long lastSaveMillis = 0L;

	/** Whether the capacity warning has already been logged. */
	private boolean capacityWarned = false;

	/**
	 * Opens the store of one collection, loading any data already on disk.
	 *
	 * @param storeFile      JSON file backing this collection
	 * @param config         settings of the embedded store
	 * @param embeddingModel model used to vectorise documents and queries
	 * @throws IOException if the file cannot be created or read
	 */
	GeboLocalVectorStore(Path storeFile, LocalConfig config, EmbeddingModel embeddingModel) throws IOException {
		super(SimpleVectorStore.builder(embeddingModel));
		this.storeFile = storeFile;
		this.config = config;
		Files.createDirectories(storeFile.getParent());
		if (Files.exists(storeFile) && Files.size(storeFile) > 0) {
			LOGGER.info("Loading the embedded vector store of {} ({} bytes)", storeFile, Files.size(storeFile));
			load(storeFile.toFile());
			LOGGER.info("Loaded {} fragment(s) from {}", fragmentCount(), storeFile);
		} else {
			LOGGER.info("Starting an empty embedded vector store at {}", storeFile);
		}
		this.lastSaveMillis = System.currentTimeMillis();
	}

	/**
	 * Number of fragments currently held.
	 *
	 * @return the live fragment count
	 */
	public int fragmentCount() {
		// The value type of the inherited map is package private in Spring AI, so it
		// is only ever referred to through a wildcard or the public Content interface.
		Map<String, ?> entries = this.store;
		return entries.size();
	}

	/**
	 * @return the file this collection is persisted to
	 */
	public Path getStoreFile() {
		return storeFile;
	}

	/**
	 * Indexes the documents, then persists according to the save policy.
	 *
	 * @param documents the fragments to store
	 */
	@Override
	public void doAdd(List<Document> documents) {
		super.doAdd(documents);
		markDirtyAndMaybeSave();
		warnIfAboveCapacity();
	}

	/**
	 * Deletes the fragments with the given ids, then persists.
	 *
	 * @param idList ids to remove
	 */
	@Override
	public void doDelete(List<String> idList) {
		super.doDelete(idList);
		markDirtyAndMaybeSave();
	}

	/**
	 * Deletes every fragment matching a metadata filter.
	 *
	 * Overridden rather than inherited because the inherited implementation routes
	 * through Spring AI's filter evaluator, which throws on the list-valued
	 * attributes Gebo.ai uses.
	 *
	 * @param filterExpression the filter selecting what to remove
	 */
	@Override
	public void doDelete(Expression filterExpression) {
		if (filterExpression == null) {
			return;
		}
		List<String> matching = new ArrayList<>();
		for (Map.Entry<String, ?> entry : this.store.entrySet()) {
			if (entry.getValue() instanceof Content content
					&& filterEvaluator.evaluate(filterExpression, content.getMetadata())) {
				matching.add(entry.getKey());
			}
		}
		if (!matching.isEmpty()) {
			super.doDelete(matching);
			markDirtyAndMaybeSave();
		}
	}

	/**
	 * Similarity search with Gebo.ai's own metadata filtering.
	 *
	 * An unfiltered request is handed straight to {@link SimpleVectorStore}. A
	 * filtered one is served by asking the parent for the WHOLE corpus ranked by
	 * similarity and with no filter attached, then applying the filter, the
	 * threshold and the top-k here. Nothing is lost by that: the parent scans every
	 * fragment for any query anyway, so the only extra work is carrying a longer
	 * list through one sort - and in exchange the filter never reaches the
	 * evaluator that would throw on a list-valued attribute.
	 *
	 * @param request what to search for
	 * @return the matching fragments, most similar first
	 */
	@Override
	public List<Document> doSimilaritySearch(SearchRequest request) {
		if (request == null) {
			return List.of();
		}
		Expression filter = request.getFilterExpression();
		if (filter == null) {
			return super.doSimilaritySearch(request);
		}
		SearchRequest unfiltered = SearchRequest.builder().query(request.getQuery())
				// Ranked candidates must cover the corpus: the fragments the filter
				// keeps can sit anywhere in the similarity order.
				.topK(Math.max(1, fragmentCount())).similarityThresholdAll().build();
		List<Document> ranked = super.doSimilaritySearch(unfiltered);
		double threshold = request.getSimilarityThreshold();
		int topK = Math.max(1, request.getTopK());
		List<Document> out = new ArrayList<>();
		for (Document candidate : ranked) {
			if (out.size() >= topK) {
				break;
			}
			if (threshold > SearchRequest.SIMILARITY_THRESHOLD_ACCEPT_ALL && candidate.getScore() != null
					&& candidate.getScore().doubleValue() < threshold) {
				// ranked is ordered by descending score, so the first candidate under
				// the threshold ends the run.
				break;
			}
			if (filterEvaluator.evaluate(filter, candidate.getMetadata())) {
				out.add(candidate);
			}
		}
		return out;
	}

	/**
	 * Reads back the metadata of the given fragments.
	 *
	 * @param ids the fragment ids
	 * @return one entry per id found, in the order requested
	 */
	@Override
	public List<VectorizedFragmentMetadata> readMetadataByIds(List<String> ids)
			throws ExecutionException, InterruptedException {
		if (ids == null || ids.isEmpty()) {
			return List.of();
		}
		List<VectorizedFragmentMetadata> out = new ArrayList<>();
		for (String id : ids) {
			if (id == null) {
				continue;
			}
			Object entry = this.store.get(id);
			if (entry instanceof Content content) {
				out.add(new VectorizedFragmentMetadata(id, new LinkedHashMap<>(content.getMetadata())));
			}
		}
		return out;
	}

	/**
	 * Merges new metadata into the stored fragments.
	 *
	 * This is the one operation the embedded store pays an embedding for: Spring AI
	 * keeps a fragment's vector inside a package private type whose metadata map is
	 * immutable, so a patched fragment has to be re-added and is therefore
	 * re-embedded. Nothing in the platform drives this path today - only the Qdrant
	 * store, which can patch a payload in place, has a caller - so the cost is
	 * documented rather than worked around.
	 *
	 * @param entries the id / metadata pairs to merge in
	 */
	@Override
	public void patchMetadataByIds(List<VectorizedFragmentMetadata> entries)
			throws ExecutionException, InterruptedException {
		if (entries == null || entries.isEmpty()) {
			return;
		}
		List<Document> patched = new ArrayList<>();
		for (VectorizedFragmentMetadata entry : entries) {
			if (entry == null || entry.getId() == null) {
				continue;
			}
			Map<String, Object> patch = entry.getMetadata();
			if (patch == null || patch.isEmpty()) {
				continue;
			}
			Object stored = this.store.get(entry.getId());
			if (!(stored instanceof Content content)) {
				LOGGER.warn("Cannot patch metadata of the unknown fragment {} in {}", entry.getId(), storeFile);
				continue;
			}
			Map<String, Object> merged = new LinkedHashMap<>(content.getMetadata());
			merged.putAll(patch);
			String text = content.getText();
			patched.add(Document.builder().id(entry.getId()).text(text == null ? "" : text).metadata(merged).build());
		}
		if (!patched.isEmpty()) {
			// add() replaces the entries keyed by these ids, and re-embeds them.
			add(patched);
		}
	}

	/**
	 * Marks the store dirty and persists it when the policy allows.
	 */
	private synchronized void markDirtyAndMaybeSave() {
		dirty = true;
		if (config.isSaveOnEveryWrite()) {
			flush();
			return;
		}
		long interval = config.getMinimumSaveIntervalSeconds() == null ? 30L
				: config.getMinimumSaveIntervalSeconds().longValue();
		if (System.currentTimeMillis() - lastSaveMillis >= interval * 1000L) {
			flush();
		}
	}

	/**
	 * Writes the store to disk if anything changed since the last save.
	 *
	 * Safe to call at any time; it is what {@link #close()} and the shutdown hook
	 * of {@link LocalVectorStoreRegistry} use to make the last batch durable.
	 */
	public synchronized void flush() {
		if (!dirty) {
			return;
		}
		try {
			File target = storeFile.toFile();
			save(target);
			dirty = false;
			lastSaveMillis = System.currentTimeMillis();
			LOGGER.debug("Persisted {} fragment(s) to {}", fragmentCount(), storeFile);
		} catch (Throwable failure) {
			// Deliberately not rethrown: losing a save must not fail the ingestion
			// batch that triggered it. The store stays dirty, so the next write or
			// the close will try again.
			LOGGER.error("Cannot persist the embedded vector store to " + storeFile, failure);
		}
	}

	/**
	 * Logs once when the corpus grows past the configured advisory limit.
	 */
	private synchronized void warnIfAboveCapacity() {
		if (capacityWarned || config.getWarnAboveFragments() == null) {
			return;
		}
		int count = fragmentCount();
		if (count < config.getWarnAboveFragments().intValue()) {
			return;
		}
		capacityWarned = true;
		LOGGER.warn("The embedded vector store of {} now holds {} fragments. It keeps the whole corpus in memory and "
				+ "scans all of it per query, so past this size a server backed vector store "
				+ "(ai.gebo.vectorstore.use) will retrieve faster and use far less heap.", storeFile, count);
	}

	/**
	 * Persists and lets go of this store.
	 *
	 * The instance is shared per file, so closing only releases one user of it; the
	 * data is written out when the last user lets go.
	 */
	@Override
	public void close() throws IOException {
		LocalVectorStoreRegistry.release(this);
	}
}
