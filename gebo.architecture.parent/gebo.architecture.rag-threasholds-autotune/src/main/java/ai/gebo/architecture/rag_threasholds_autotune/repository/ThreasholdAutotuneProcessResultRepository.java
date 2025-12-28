package ai.gebo.architecture.rag_threasholds_autotune.repository;

import java.util.List;

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
}
