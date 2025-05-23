/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.model;

import lombok.ToString;

/**
 * RagQueryOptions represents configuration options for RAG (Retrieval-Augmented Generation) queries.
 * This class provides various settings to control the query processing behavior, such as
 * the number of top K documents to retrieve, similarity threshold, token limits, and completeness level.
 * AI generated comments
 */
@ToString
public class RagQueryOptions {

    /** Default options instance of RagQueryOptions */
    public static final RagQueryOptions defaultOptions = new RagQueryOptions();

    /** Enumeration defining levels of completeness for query results */
    public static enum CompletenessLevel {
        STRICT_QUERY_RELATED, // Only strict query-related results
        FULL_DOCUMENTS, // Include full documents
        MAX_TOKENS // Limit by the maximum tokens
    }

    /** Default value for `topK` parameter */
    public final static int DEFAULT_TOPK = -1;
    
    /** Number of top results to retrieve */
    private int topK = DEFAULT_TOPK;
    
    /** Default similarity threshold for filtering results */
    public final static double DEFAULT_SIMILARITY_THRESHOLD = -1;
    
    /** Similarity threshold for filtering results */
    private double similarityThreashold = DEFAULT_SIMILARITY_THRESHOLD;
    
    /** Default maximum number of tokens allowed */
    public final static long DEFAULT_MAXTOKENS = -1;
    
    /** Maximum number of tokens to consider in results */
    private long maxTokens = DEFAULT_MAXTOKENS;
    
    /** Default completeness level for results */
    public final static CompletenessLevel DEFAULT_COMPLETENESS = CompletenessLevel.STRICT_QUERY_RELATED;
    
    /** Completeness level for query results */
    private CompletenessLevel completeness = DEFAULT_COMPLETENESS;

    /**
     * Constructor to initialize RagQueryOptions with specified max tokens and completeness level.
     *
     * @param maxTokens the maximum number of tokens
     * @param level the completeness level for the results
     */
    public RagQueryOptions(long maxTokens, CompletenessLevel level) {
        this.maxTokens = maxTokens;
        this.completeness = level;
    }

    /**
     * Constructor to initialize RagQueryOptions with specified attributes.
     *
     * @param maxTokens the maximum number of tokens
     * @param level the completeness level for the results
     * @param topK the number of top K results to retrieve
     * @param similarityThreashold the similarity threshold for filtering results
     */
    public RagQueryOptions(long maxTokens, CompletenessLevel level, int topK, double similarityThreashold) {
        this.maxTokens = maxTokens;
        this.completeness = level;
        this.topK = topK;
        this.similarityThreashold = similarityThreashold;
    }

    /**
     * Copy constructor for creating a new instance from existing RagQueryOptions.
     *
     * @param o the RagQueryOptions instance to copy from
     */
    public RagQueryOptions(RagQueryOptions o) {
        this.completeness = o.completeness;
        this.maxTokens = o.maxTokens;
        this.similarityThreashold = o.similarityThreashold;
        this.topK = o.topK;
    }

    /** Default constructor */
    public RagQueryOptions() {
    }

    /**
     * Gets the maximum number of tokens allowed in the results.
     *
     * @return the maximum number of tokens
     */
    public long getMaxTokens() {
        return maxTokens;
    }

    /**
     * Gets the completeness level of the query results.
     *
     * @return the completeness level
     */
    public CompletenessLevel getCompleteness() {
        return completeness;
    }

    /**
     * Gets the number of top K results to retrieve.
     *
     * @return the number of top K results
     */
    public int getTopK() {
        return topK;
    }

    /**
     * Gets the similarity threshold used for filtering results.
     *
     * @return the similarity threshold
     */
    public double getSimilarityThreashold() {
        return similarityThreashold;
    }

    /**
     * Sets the number of top K results to retrieve.
     *
     * @param topK the number of top K results
     */
    public void setTopK(int topK) {
        this.topK = topK;
    }

    /**
     * Sets the similarity threshold for filtering results.
     *
     * @param similarityThreashold the new similarity threshold
     */
    public void setSimilarityThreashold(double similarityThreashold) {
        this.similarityThreashold = similarityThreashold;
    }

    /**
     * Sets the maximum number of tokens allowed in the results.
     *
     * @param maxTokens the new maximum number of tokens
     */
    public void setMaxTokens(long maxTokens) {
        this.maxTokens = maxTokens;
    }

    /**
     * Sets the completeness level for the results.
     *
     * @param completeness the new completeness level
     */
    public void setCompleteness(CompletenessLevel completeness) {
        this.completeness = completeness;
    }

}