/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.services;

import java.util.List;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.IGToolCallbackSource;
import ai.gebo.architecture.ai.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.lucene.model.SearchKeywordRequest;
import ai.gebo.architecture.lucene.model.SearchKeywordResults;

/**
 * AI generated comments
 * 
 * GLuceneFunctions is responsible for handling keyword search operations
 * through the LuceneService. It implements IGToolCallbackSource to provide
 * tool callbacks for searching keyword content within configured knowledge
 * bases. The component is activated when the specified configuration property
 * is enabled.
 */
@Component
@Scope("singleton")
@ConditionalOnProperty(prefix = "ai.gebo.lucene.config", name = "enabled", havingValue = "true")
public class GLuceneFunctions implements IGToolCallbackSource {

    // Logger for the class
	private final static Logger LOGGER = LoggerFactory.getLogger(GLuceneFunctions.class);

	// Descriptions for running keyword search
	private static final String RUN_KEWORD_SEARCH_DESCRIPTION = "Run a keword search for document contents";
	private static final String RUN_KEYWORD_SEARCH = "runKeywordSearch";

	// Lucene service for executing search operations
	@Autowired
	LuceneService luceneService;

	/**
	 * Returns the ID of the tool callback source.
	 * 
	 * @return the identifier for the tool callback source.
	 */
	@Override
	public String getId() {
		return "luceneIndexing";
	}

	/**
	 * Returns the category of tools this class is associated with.
	 * 
	 * @return ToolsCategory indicating type of search operations.
	 */
	@Override
	public ToolsCategory getToolCategory() {
		return ToolsCategory.KNOWLEDGE_BASE_VARIOUS_SEARCHES;
	}

	/**
	 * Creates a ToolCallback for running keyword searches.
	 * 
	 * @return the ToolCallback configured for keyword search operations.
	 */
	protected ToolCallback create() {

		// BiFunction to handle search requests and return search results
		BiFunction<SearchKeywordRequest, ToolContext, SearchKeywordResults> function = (x, c) -> {
			List<String> kbases = null;
			KBContext contextVisibility = LLMtInteractionContextThreadLocal.Context.get();
			CalledFunction _function = new CalledFunction();
			_function.setFunctionName(RUN_KEYWORD_SEARCH);
			_function.setFunctionDescription(RUN_KEWORD_SEARCH_DESCRIPTION);
			_function.setParamsDescription(x.getKeywords());

			if (contextVisibility != null) {
				// If context is available, perform search operation and add function to context
				contextVisibility.getCalledFunctions().add(_function);
				kbases = contextVisibility.getKnowledgeBasesCodes();
				try {
					return luceneService.runSearch(kbases, x);
				} catch (Throwable e) {
					LOGGER.error("Error in function", e);
					return new SearchKeywordResults();
				}
			}

			// Log error and return empty results if context is not correct
			ToolCallbackDeclarationUtil.addCallToContext(c, _function);
			LOGGER.error("No correct context present in LLM function");
			return new SearchKeywordResults();
		};

		// Declare the ToolCallback using the specified function and descriptors
		return ToolCallbackDeclarationUtil.declare(
		    function, 
		    RUN_KEYWORD_SEARCH, 
		    RUN_KEWORD_SEARCH_DESCRIPTION,
			SearchKeywordRequest.class, 
			SearchKeywordResults.class
		);
	}

	/**
	 * Provides the full tool references with descriptions used by the user
	 * interface.
	 * 
	 * @return a list of ToolReferences for user UI.
	 */
	@Override
	public List<ToolReference> getFullToolReferences() {
		ToolReference reference = new ToolReference();
		reference.setName(RUN_KEYWORD_SEARCH);
		reference.setDescription(RUN_KEWORD_SEARCH_DESCRIPTION);
		reference.setUserUIfunctionDescription(
				"Run a keyword search for contents in the knowledge base the user is working with");
		return List.of(reference);
	}

	/**
	 * Initializes and retrieves the list of ToolCallbacks available for use.
	 * 
	 * @return a list of ToolCallbacks for search operation.
	 */
	@Override
	public List<ToolCallback> getToolCallbacks() {
		return List.of(create());
	}
}