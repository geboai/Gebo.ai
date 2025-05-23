/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.client.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SearchRequest.Builder;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * REST controller responsible for semantic search operations on Gebo user knowledge bases.
 * This controller handles queries to search across multiple knowledge bases using semantic
 * vector similarity search capabilities.
 */
@RestController
@RequestMapping(path = "api/users/GeboUserKnowledgeBaseSemanticSearchController")
public class GeboUserKnowledgeBaseSemanticSearchController {
	/** Manages persistence operations for Gebo objects */
	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	
	/** Data access object for embedding model runtime configurations */
	@Autowired
	IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao;

	/**
	 * Inner class representing parameters for semantic search queries.
	 */
	public static class SemanticQueryParam {
		/** The text query to find semantically similar documents */
		@NotNull
		public String query = null;
		
		/** List of knowledge base codes to search within */
		@NotNull
		public List<String> knowledgeBaseCodes = null;
		
		/** Optional parameter to limit the number of results returned */
		public Integer topK = null;
	}

	/**
	 * Performs a semantic search across specified knowledge bases.
	 * 
	 * @param param Contains the query, knowledge base codes, and optional topK parameter
	 * @return List of content codes for documents that match the semantic query
	 * @throws GeboPersistenceException If there's an error fetching objects from the persistent store
	 */
	@PostMapping(value = "semanticSearch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<String> semanticSearch(@NotNull @Valid @RequestBody SemanticQueryParam param)
			throws GeboPersistenceException {
		List<String> out = new ArrayList<String>();
		
		// Retrieve knowledge bases by their codes
		List<GKnowledgeBase> kbases = persistentObjectManager.findAllByIds(GKnowledgeBase.class,
				param.knowledgeBaseCodes);
		if (kbases == null)
			throw new IllegalStateException(
					"The knowledge base with code:" + param.knowledgeBaseCodes + " does not exist");
		
		// Collect embedding models to use for search
		List<IGConfigurableEmbeddingModel> embeddingModels = new ArrayList<IGConfigurableEmbeddingModel>();
		
		// Add default embedding model if available
		IGConfigurableEmbeddingModel defaultEmbeddingModel = this.embeddingModelsDao.defaultHandler();
		if (defaultEmbeddingModel != null)
			embeddingModels.add(defaultEmbeddingModel);
		
		// Add specific embedding models from each knowledge base
		for (GKnowledgeBase kb : kbases) {
			List<GObjectRef> _embeddingModels = kb.getEmbeddingModelReferences();
			if (_embeddingModels != null && !_embeddingModels.isEmpty()) {
				final GObjectRef emRef = _embeddingModels.get(0);
				IGConfigurableEmbeddingModel embeddingModel = embeddingModelsDao.findByPredicate(x -> {
					String className = x.getConfig().getClass().getName();
					String code = x.getConfig().getCode();
					return className != null && code != null && emRef.getClassName().equals(className)
							&& emRef.getCode().equals(code);
				});
				if (embeddingModel != null) {
					embeddingModels.add(embeddingModel);
				}
			}
		}
		
		// Construct filter expression to limit search to specified knowledge bases
		StringBuffer filterExpression = new StringBuffer();
		filterExpression.append(DocumentMetaInfos.KNOWLEDGEBASE_CODE + " IN [");
		for (int i = 0; i < param.knowledgeBaseCodes.size(); i++) {
			filterExpression.append("'" + param.knowledgeBaseCodes.get(i) + "'");
			if (i < (param.knowledgeBaseCodes.size() - 1)) {
				filterExpression.append(",");
			}
		}
		filterExpression.append("]");
		
		// Execute semantic search across all embedding models
		for (IGConfigurableEmbeddingModel embeddingModel : embeddingModels) {
			VectorStore vectorStore = embeddingModel.getVectorStore();
			Builder searchRequestBuilder = SearchRequest.builder();
			searchRequestBuilder.query(param.query);
			if (param.topK != null) {
				searchRequestBuilder.topK(param.topK);
			}
			searchRequestBuilder.filterExpression(filterExpression.toString());
			SearchRequest searchRequest = searchRequestBuilder.build();
			
			// Retrieve similar documents and extract their content codes
			List<Document> results = vectorStore.similaritySearch(searchRequest);
			List<String> foundCodes = results.stream().map(x -> {
				Object code = x.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
				return code != null ? code.toString() : null;
			}).filter(y -> y != null).toList();
			
			// Add unique content codes to the result list
			foundCodes.forEach(x -> {
				if (!out.contains(x))
					out.add(x);
			});
		}
		return out;
	}
}