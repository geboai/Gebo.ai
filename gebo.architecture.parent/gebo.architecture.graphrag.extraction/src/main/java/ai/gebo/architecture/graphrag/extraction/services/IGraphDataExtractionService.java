package ai.gebo.architecture.graphrag.extraction.services;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

public interface IGraphDataExtractionService {
	public LLMExtractionResult extract(Document document, GraphRagExtractionConfig configuration)
			throws LLMConfigException;

	public LLMExtractionResult extract(Document document, GDocumentReference docreference) throws LLMConfigException;

	public LLMExtractionResult extract(String text, GraphRagExtractionConfig configuration) throws LLMConfigException;

}
