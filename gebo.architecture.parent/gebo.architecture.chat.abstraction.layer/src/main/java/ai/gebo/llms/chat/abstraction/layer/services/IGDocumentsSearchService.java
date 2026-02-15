package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;

public interface IGDocumentsSearchService {
	public AIDocumentsSet search(GeboChatRequest request, List<String> semanticSearches, List<String> fullTextSearches,
			String userQuery, int globalTopK, int tokensBudget) throws FullTextException, LLMConfigException;

	public AIDocumentsSet search(GeboChatRequest chatRequest, int tokensBudget) throws FullTextException, LLMConfigException;
}
