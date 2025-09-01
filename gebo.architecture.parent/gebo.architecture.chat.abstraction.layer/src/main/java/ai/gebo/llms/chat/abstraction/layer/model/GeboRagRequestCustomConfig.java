/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

/**
 * GeboRagRequestCustomConfig is a configuration class used to store parameters 
 * for handling request customization in the Gebo RAG system.
 * This includes settings like `topK`, `similarityThreshold`, `historyFillWindowWeight`, 
 * and `documentsFillWindowWeight`.
 *
 * Gebo.ai comment agent 
 */
public class GeboRagRequestCustomConfig {

    // The maximum number of top results to consider (null if not set).
    Integer topK = null;

    // Threshold for similarity score (null if not set).
    Double similarityThreashold = null;

    // Weighting factor for the influence of history on the current window (null if not set).
    Double historyFillWindowWeight = null;

    // Weighting factor for the influence of documents on the current window (null if not set).
    Double documentsFillWindowWeight = null;

    /**
     * Default constructor for GeboRagRequestCustomConfig.
     * Initializes a new instance with all values set to null.
     */
    public GeboRagRequestCustomConfig() {

    }

    /**
     * Gets the value of topK.
     * 
     * @return the maximum number of top results to consider, or null if not set.
     */
    public Integer getTopK() {
        return topK;
    }

    /**
     * Sets the value of topK.
     * 
     * @param topK the maximum number of top results to set.
     */
    public void setTopK(Integer topK) {
        this.topK = topK;
    }

    /**
     * Gets the similarity threshold.
     * 
     * @return the similarity threshold value, or null if not set.
     */
    public Double getSimilarityThreashold() {
        return similarityThreashold;
    }

    /**
     * Sets the similarity threshold.
     * 
     * @param similarityThreashold the similarity threshold to set.
     */
    public void setSimilarityThreashold(Double similarityThreashold) {
        this.similarityThreashold = similarityThreashold;
    }

    /**
     * Gets the history fill window weight.
     * 
     * @return the weight for history fill window, or null if not set.
     */
    public Double getHistoryFillWindowWeight() {
        return historyFillWindowWeight;
    }

    /**
     * Sets the history fill window weight.
     * 
     * @param historyFillWindowWeight the weight to set for the history fill window.
     */
    public void setHistoryFillWindowWeight(Double historyFillWindowWeight) {
        this.historyFillWindowWeight = historyFillWindowWeight;
    }

    /**
     * Gets the documents fill window weight.
     * 
     * @return the weight for documents fill window, or null if not set.
     */
    public Double getDocumentsFillWindowWeight() {
        return documentsFillWindowWeight;
    }

    /**
     * Sets the documents fill window weight.
     * 
     * @param documentsFillWindowWeight the weight to set for the documents fill window.
     */
    public void setDocumentsFillWindowWeight(Double documentsFillWindowWeight) {
        this.documentsFillWindowWeight = documentsFillWindowWeight;
    }

}