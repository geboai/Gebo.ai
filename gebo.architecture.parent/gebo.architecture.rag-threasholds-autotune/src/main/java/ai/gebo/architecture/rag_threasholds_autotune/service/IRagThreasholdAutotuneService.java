package ai.gebo.architecture.rag_threasholds_autotune.service;

import ai.gebo.architecture.rag_threasholds_autotune.model.OptimizedThreashold;

public interface IRagThreasholdAutotuneService {
	public OptimizedThreashold findByVectorStoreId(String vectorStoreId);

	public OptimizedThreashold findByEmbeddingModelCode(String vectorStoreId);

	public OptimizedThreashold findByKnowledgeBase(String knowledgeBaseCode);
	
	public void processAutotune(String vectorStoreId);
}
