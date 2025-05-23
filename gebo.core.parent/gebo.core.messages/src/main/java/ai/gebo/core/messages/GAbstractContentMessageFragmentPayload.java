/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.messages;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Represents an abstract payload for a message fragment containing content related to a document,
 * project, and knowledge base. This class provides methods to access various metadata.
 */
public abstract class GAbstractContentMessageFragmentPayload extends GBaseMessagePayload {

    /**
     * Enum representing the types of message fragments.
     */
    public static enum MessageFragmentType {
        SINGLE_FRAGMENT, NUMBERED_FRAGMENT, STREAM_FRAGMENT
    }

    @NotNull
    private MessageFragmentType fragmentType = null;

    @NotNull
    private GDocumentReference documentReference = null;

    private Integer n = null, totalNr = null;

    private Boolean hashableContent = null;

    private Boolean requiresEmbeddingHandshake = null;

    private String hash = null;

    private String jobId = null;

    private GProject project = null;

    private GKnowledgeBase knowledgeBase = null;

    private GProjectEndpoint endPoint = null;

    private int estimatedTokens = 0;

    /**
     * Default constructor.
     */
    public GAbstractContentMessageFragmentPayload() {

    }

    /**
     * Gets the document reference associated with the message fragment.
     * 
     * @return the document reference.
     */
    public GDocumentReference getDocumentReference() {
        return documentReference;
    }

    /**
     * Sets the document reference for the message fragment.
     * 
     * @param documentReference the document reference to set.
     */
    public void setDocumentReference(GDocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    /**
     * Gets the type of the message fragment.
     * 
     * @return the fragment type.
     */
    public MessageFragmentType getFragmentType() {
        return fragmentType;
    }

    /**
     * Sets the type of the message fragment.
     * 
     * @param fragmentType the fragment type to set.
     */
    public void setFragmentType(MessageFragmentType fragmentType) {
        this.fragmentType = fragmentType;
    }

    /**
     * Gets the fragment number.
     * 
     * @return the fragment number.
     */
    public Integer getN() {
        return n;
    }

    /**
     * Sets the fragment number.
     * 
     * @param n the fragment number to set.
     */
    public void setN(Integer n) {
        this.n = n;
    }

    /**
     * Gets the total number of fragments.
     * 
     * @return the total number of fragments.
     */
    public Integer getTotalNr() {
        return totalNr;
    }

    /**
     * Sets the total number of fragments.
     * 
     * @param totalNr the total number of fragments to set.
     */
    public void setTotalNr(Integer totalNr) {
        this.totalNr = totalNr;
    }

    /**
     * Determines if the content is hashable.
     * 
     * @return true if the content is hashable, false otherwise.
     */
    public Boolean getHashableContent() {
        return hashableContent;
    }

    /**
     * Sets whether the content is hashable.
     * 
     * @param hashableContent true if the content is hashable, false otherwise.
     */
    public void setHashableContent(Boolean hashableContent) {
        this.hashableContent = hashableContent;
    }

    /**
     * Gets the embedding handshake requirement.
     * 
     * @return true if embedding handshake is required, false otherwise.
     */
    public Boolean getRequiresEmbeddingHandshake() {
        return requiresEmbeddingHandshake;
    }

    /**
     * Sets the embedding handshake requirement.
     * 
     * @param requiresHandshake true if embedding handshake is required, false otherwise.
     */
    public void setRequiresEmbeddingHandshake(Boolean requiresHandshake) {
        this.requiresEmbeddingHandshake = requiresHandshake;
    }

    /**
     * Gets the hash value of the content.
     * 
     * @return the hash value.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets the hash value for the content.
     * 
     * @param hash the hash value to set.
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Gets the job ID associated with the fragment.
     * 
     * @return the job ID.
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Sets the job ID for the fragment.
     * 
     * @param jobId the job ID to set.
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * Gets the project associated with the fragment.
     * 
     * @return the project.
     */
    public GProject getProject() {
        return project;
    }

    /**
     * Sets the project for the fragment.
     * 
     * @param project the project to set.
     */
    public void setProject(GProject project) {
        this.project = project;
    }

    /**
     * Gets the knowledge base associated with the fragment.
     * 
     * @return the knowledge base.
     */
    public GKnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    /**
     * Sets the knowledge base for the fragment.
     * 
     * @param knowledgeBase the knowledge base to set.
     */
    public void setKnowledgeBase(GKnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    /**
     * Gets the project endpoint associated with the fragment.
     * 
     * @return the project endpoint.
     */
    public GProjectEndpoint getEndPoint() {
        return endPoint;
    }

    /**
     * Sets the project endpoint for the fragment.
     * 
     * @param endPoint the project endpoint to set.
     */
    public void setEndPoint(GProjectEndpoint endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Gets the estimated number of tokens for the fragment.
     * 
     * @return the estimated tokens.
     */
    public int getEstimatedTokens() {
        return estimatedTokens;
    }

    /**
     * Sets the estimated number of tokens for the fragment.
     * 
     * @param estimatedTokens the estimated tokens to set.
     */
    public void setEstimatedTokens(int estimatedTokens) {
        this.estimatedTokens = estimatedTokens;
    }
}