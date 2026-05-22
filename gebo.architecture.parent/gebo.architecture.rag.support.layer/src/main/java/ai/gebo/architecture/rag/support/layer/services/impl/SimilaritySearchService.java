/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.rag.support.layer.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.knlowledgebase.model.contents.GDocumentReferenceSnapshot;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.model.ExtractedDocumentMetaData;

/**
 * AI generated comments Service for performing similarity search operations on
 * documents.
 */
@Service
class SimilaritySearchService {
	private final static Logger LOGGER = LoggerFactory.getLogger(SimilaritySearchService.class);
	// Repository for accessing document reference snapshot data
	@Autowired
	DocumentReferenceSnapshotRepository documentSnapshotRepository;

	/**
	 * A helper class to store results of similarity search.
	 */
	class ResultsMaps {
		final List<AIDocumentReferenceItem> docinfos = new ArrayList<AIDocumentReferenceItem>();
		final Map<String, Boolean> knowledgeBases = new HashMap<String, Boolean>();
	}

	/**
	 * Default constructor.
	 */
	public SimilaritySearchService() {
	}

	/**
	 * Aggregate results from the provided list of documents.
	 * 
	 * @param documents List of documents from the search
	 * @return An instance of ResultsMaps containing aggregated results
	 */
	private ResultsMaps cumulateResults(List<Document> documents) {
		final ResultsMaps result = new ResultsMaps();
		int index = 1;
		for (Document x : documents) {
			ExtractedDocumentMetaData metaData = ExtractedDocumentMetaData.of(x.getMetadata());
			// Find existing document reference item by code, if it exists
			Optional<AIDocumentReferenceItem> optionalItem = result.docinfos.stream()
					.filter(y -> y.getCode().equals(metaData.getCode())).findFirst();
			AIDocumentReferenceItem item = optionalItem.isPresent() ? optionalItem.get() : null;
			if (item == null) {
				item = fromExtracted(metaData); // Create new reference item if not found
				result.docinfos.add(item);
			}
			result.knowledgeBases.put(metaData.getRootKnowledgebaseCode(), true);
			AIDocumentFragment fragment = new AIDocumentFragment(x, metaData);
			fragment.setRankIndex(index); // Assign a rank index to the fragment
			item.getFragments().add(fragment);
			index++;

		}
		List<String> docCodes = result.docinfos.stream().map(x -> x.getCode()).toList();
		List<GDocumentReferenceSnapshot> shares = documentSnapshotRepository.findAllById(docCodes);
		shares.forEach(x -> {
			Optional<AIDocumentReferenceItem> optionalItem = result.docinfos.stream()
					.filter(y -> y.getCode().equals(x.getCode())).findFirst();
			if (optionalItem.isPresent()) {
				optionalItem.get()
						.setTotalFileNTokens(x.getTokensCount() != null ? x.getTokensCount().longValue() : 0l); // Set
																												// token
																												// count
																												// for
																												// the
																												// document
			}
		});
		return result;
	}

	/**
	 * Create a RagDocumentReferenceItem from given extracted metadata.
	 * 
	 * @param metaData Metadata extracted from document
	 * @return A new instance of RagDocumentReferenceItem
	 */
	private AIDocumentReferenceItem fromExtracted(ExtractedDocumentMetaData metaData) {
		AIDocumentReferenceItem item = new AIDocumentReferenceItem(metaData);

		return item;
	}

	/**
	 * Execute a similarity search using the given search request and embedding
	 * model.
	 * 
	 * @param searchRequest  The search query parameters
	 * @param embeddingModel The embedding model to use for the search
	 * @return Result containing the cached DAO of the search operation
	 */
	AIDocumentsSet executeSearch(SearchRequest searchRequest, IGConfigurableEmbeddingModel<?> embeddingModel) {
		if (LOGGER.isDebugEnabled()) {
			StringBuffer expression = new StringBuffer();

			expression.append("{query:" + searchRequest.getQuery());
			expression.append(",topK:" + searchRequest.getTopK());
			expression.append(",similarityThreashold:" + searchRequest.getSimilarityThreshold());
			if (searchRequest.getFilterExpression() != null) {
				expression.append(",filterExpression:" + searchRequest.getFilterExpression());
			}
			expression.append("}");
			LOGGER.debug("Begin executeSearch(...) with: "+expression.toString());
		}
		VectorStore vectorStore = embeddingModel.getVectorStore();

		List<Document> documents = vectorStore.similaritySearch(searchRequest);
		ResultsMaps cumulated = cumulateResults(documents);
		AIDocumentsSet result = new AIDocumentsSet();
		result.setDocumentItems(new ArrayList<AIDocumentReferenceItem>(cumulated.docinfos));
		result.recalculateSize(); // Recalculate size of the result
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End executeSearch(...)");
		}
		return result;
	}
}
