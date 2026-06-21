package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;

public interface IGDocumentsSearchService {
	public AIDocumentsSet search(GeboChatRequest request, List<String> semanticSearches, SemanticSearchMetaDataFilter semanticSearchMetaDataFilter,
			List<String> fullTextSearches, FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter, String userQuery, int globalTopK, int tokensBudget) throws FullTextException, LLMConfigException;

	public AIDocumentsSet search(GeboChatRequest chatRequest, SemanticSearchMetaDataFilter semanticSearchMetaDataFilter, FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter, int tokensBudget) throws FullTextException, LLMConfigException;
}
