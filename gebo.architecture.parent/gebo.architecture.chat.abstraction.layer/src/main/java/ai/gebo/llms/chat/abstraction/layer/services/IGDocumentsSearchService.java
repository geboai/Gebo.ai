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

	/**
	 * Variant of {@link #search(GeboChatRequest, List, SemanticSearchMetaDataFilter, List, FullTextSearchMetaDataFilter, String, int, int)}
	 * that takes a plain query String instead of a {@link GeboChatRequest}. Intended for callers
	 * with no chat session/profile (e.g. agents): visible knowledge bases are resolved through the
	 * visibility service rather than from a chat profile.
	 */
	public AIDocumentsSet search(String request, List<String> semanticSearches, SemanticSearchMetaDataFilter semanticSearchMetaDataFilter,
			List<String> fullTextSearches, FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter, String userQuery, int globalTopK, int tokensBudget) throws FullTextException, LLMConfigException;

	public AIDocumentsSet search(GeboChatRequest chatRequest, SemanticSearchMetaDataFilter semanticSearchMetaDataFilter, FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter, int tokensBudget) throws FullTextException, LLMConfigException;
}
