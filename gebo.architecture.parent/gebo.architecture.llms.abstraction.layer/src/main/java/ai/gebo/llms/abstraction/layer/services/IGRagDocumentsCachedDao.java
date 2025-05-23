/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions;

/**
 * This interface defines the contract for interacting with cached RAG (Retrieval-Augmented Generation) documents.
 * It provides methods for performing various types of searches on these documents.
 * 
 * AI generated comments
 */
public interface IGRagDocumentsCachedDao {

    /**
     * Performs a chat-based search on the documents using the specified query and options.
     * 
     * @param query The search query to be executed.
     * @param ragQueryOptions Options to customize the RAG query.
     * @param codes A list of document codes to include in the search.
     * @param knowledgeBases A list of knowledge bases to search within.
     * @param embeddingModel The model used for embeddings in the search.
     * @return The result of the documents search.
     */
    public RagDocumentsCachedDaoResult chatWithDocumentsSearch(String query, RagQueryOptions ragQueryOptions,
            List<String> codes, List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel);

    /**
     * Performs a semantic search on a list of documents using the specified query and options.
     * 
     * @param query The search query to be executed.
     * @param options Options to customize the search process.
     * @param codes A list of document codes to include in the search.
     * @param knowledgeBases A list of knowledge bases to search within.
     * @param embeddingModel The model used for embeddings in the search.
     * @return The result of the semantic search.
     */
    public RagDocumentsCachedDaoResult semanticSearchOnDocumentsList(String query, RagQueryOptions options,
            List<String> codes, List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel);

    /**
     * Performs a semantic search on a list of documents using default query options.
     * 
     * @param query The search query to be executed.
     * @param codes A list of document codes to include in the search.
     * @param knowledgeBases A list of knowledge bases to search within.
     * @param embeddingModel The model used for embeddings in the search.
     * @return The result of the semantic search.
     */
    public default RagDocumentsCachedDaoResult semanticSearchOnDocumentsList(String query, List<String> codes,
            List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel) {
        return this.semanticSearchOnDocumentsList(query, RagQueryOptions.defaultOptions, codes, knowledgeBases,
                embeddingModel);
    };

    /**
     * Performs a multi-hop semantic search using the specified initial query and options.
     * 
     * @param initialQuery The initial query for starting the search.
     * @param options Options to customize the search process.
     * @param knowledgeBases A list of knowledge bases to search within.
     * @param embeddingModel The model used for embeddings in the search.
     * @param firstSearchThreshold Threshold for the first search.
     * @param otherSearchThreshold Threshold for subsequent searches.
     * @return The result of the multi-hop semantic search.
     */
    public RagDocumentsCachedDaoResult multiHopSemanticSearch(String initialQuery, RagQueryOptions options,
            List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel, Double firstSearchThreshold, Double otherSearchThreshold);

    /**
     * Performs a semantic search using the specified query and options.
     * 
     * @param query The search query to be executed.
     * @param options Options to customize the search process.
     * @param knowledgeBases A list of knowledge bases to search within.
     * @param embeddingModel The model used for embeddings in the search.
     * @return The result of the semantic search.
     */
    public RagDocumentsCachedDaoResult semanticSearch(String query, RagQueryOptions options,
            List<String> knowledgeBases, IGConfigurableEmbeddingModel<?> embeddingModel);

    /**
     * Performs a semantic search using the specified query with default options.
     * 
     * @param query The search query to be executed.
     * @param knowledgeBases A list of knowledge bases to search within.
     * @param embeddingModel The model used for embeddings in the search.
     * @return The result of the semantic search.
     */
    public default RagDocumentsCachedDaoResult semanticSearch(String query, List<String> knowledgeBases,
            IGConfigurableEmbeddingModel<?> embeddingModel) {
        return this.semanticSearch(query, RagQueryOptions.defaultOptions, knowledgeBases, embeddingModel);
    }

}