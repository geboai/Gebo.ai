package ai.gebo.architecture.rag_threasholds_autotune.repository;

import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.architecture.rag_threasholds_autotune.model.ThreasholdAutotuneProcessResult;

public interface ThreasholdAutotuneProcessResultRepository
		extends IGBaseMongoDBRepository<ThreasholdAutotuneProcessResult> {
	@Override
	default Class<ThreasholdAutotuneProcessResult> getManagedType() {

		return ThreasholdAutotuneProcessResult.class;
	}

	public List<ThreasholdAutotuneProcessResult> findByVectorStoreId(String vectorStoreId);

	public List<ThreasholdAutotuneProcessResult> findByEmbeddingModelCode(String embeddingModelCode);

	public List<ThreasholdAutotuneProcessResult> findByRootKnowledgeBase(String knowledgeBaseCode);

	/**
	 * The most recently computed result for a vector store, resolved by the store rather
	 * than read out of the whole history.
	 * <p>
	 * A run appends a result and nothing prunes them, so the collection grows for as long
	 * as the tuning stays enabled - one record every four hours by default, each carrying
	 * its whole evaluated curve. The lookup that feeds the retrieval sits inside the per
	 * embedding model loop of every semantic search, so reading the list and sorting it in
	 * memory turned every search into a scan of that entire history.
	 */
	public Optional<ThreasholdAutotuneProcessResult> findFirstByVectorStoreIdOrderByProcessedDateTimeDesc(
			String vectorStoreId);

	/**
	 * The most recently computed result for a provider side embedding model name. Kept
	 * beside the vector store lookup for the records written before the two were told
	 * apart.
	 */
	public Optional<ThreasholdAutotuneProcessResult> findFirstByEmbeddingModelCodeOrderByProcessedDateTimeDesc(
			String embeddingModelCode);

	/**
	 * The most recently computed result for a knowledge base.
	 */
	public Optional<ThreasholdAutotuneProcessResult> findFirstByRootKnowledgeBaseOrderByProcessedDateTimeDesc(
			String knowledgeBaseCode);
}
