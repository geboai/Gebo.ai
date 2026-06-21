package ai.gebo.architecture.rag_threasholds_autotune.service;

import java.util.List;

import ai.gebo.architecture.rag_threasholds_autotune.model.AutotuneVectorStoreInfo;
import ai.gebo.architecture.rag_threasholds_autotune.model.OptimizedThreashold;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

public interface IRagThreasholdAutotuneService {
	public OptimizedThreashold findByVectorStoreId(String vectorStoreId);

	public OptimizedThreashold findByEmbeddingModelCode(String vectorStoreId);

	public OptimizedThreashold findByKnowledgeBase(String knowledgeBaseCode);

	public void processAutotune(String vectorStoreId) throws LLMConfigException;

	public List<AutotuneVectorStoreInfo> getLatestComputedVectorStores();
	public boolean isRunning();
}
