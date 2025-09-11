package ai.gebo.architecture.graphrag.persistence;

import java.util.List;

import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;

public interface IKnowledgeGraphSearchService {
	public List<KnowledgeGraphSearchResult> knowledgeGraphSearch(LLMExtractionResult extraction, int topK,
			List<String> knowledgeBasesCodes);

	public List<KnowledgeGraphSearchResult> knowledgeGraphSearch(LLMExtractionResult extraction, int topK);
}
