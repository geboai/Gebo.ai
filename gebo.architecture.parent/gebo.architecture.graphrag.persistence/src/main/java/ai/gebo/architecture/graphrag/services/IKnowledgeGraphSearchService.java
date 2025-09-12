package ai.gebo.architecture.graphrag.services;

import java.util.List;

import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

public interface IKnowledgeGraphSearchService {
	public List<KnowledgeGraphSearchResult> knowledgeGraphSearch(LLMExtractionResult extraction, int topK,
			List<String> knowledgeBasesCodes);

	public List<KnowledgeGraphSearchResult> knowledgeGraphSearch(LLMExtractionResult extraction, int topK);

	public List<KnowledgeGraphSearchResult> knowledgeGraphSearch(String query, List<String> knowledgeBases, int topK) throws LLMConfigException;

	public boolean isConfigured();
}
