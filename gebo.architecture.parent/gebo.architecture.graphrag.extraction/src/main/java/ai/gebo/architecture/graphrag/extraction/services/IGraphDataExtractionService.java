package ai.gebo.architecture.graphrag.extraction.services;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

public interface IGraphDataExtractionService {
	public LLMExtractionResult extract(Document document, GraphRagExtractionConfig configuration,
			Map<String, Object> cache) throws LLMConfigException;

	public boolean isConfigured();

	public LLMExtractionResult extract(Document document, GDocumentReference docreference, Map<String, Object> cache)
			throws LLMConfigException;

	public LLMExtractionResult extract(String query, GraphRagExtractionConfig configuration, Map<String, Object> cache)
			throws LLMConfigException;

	public LLMExtractionResult extract(String query, List<String> knowledgeBases, Map<String, Object> cache)
			throws LLMConfigException;

}
