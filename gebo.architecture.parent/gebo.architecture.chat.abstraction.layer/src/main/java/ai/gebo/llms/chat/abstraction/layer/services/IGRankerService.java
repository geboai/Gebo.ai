package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

public interface IGRankerService {
	public AIDocumentsSet call(AIDocumentsSet input, String query, int topK) throws LLMConfigException;

	public List<Document> call(List<Document> input, String query, int topK) throws LLMConfigException;

	public boolean isRankerConfigured();
}
