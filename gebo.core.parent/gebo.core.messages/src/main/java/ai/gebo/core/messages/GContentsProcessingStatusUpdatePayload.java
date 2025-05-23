/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.messages;

import java.util.Date;

import ai.gebo.application.messaging.model.GBaseMessagePayload;

/**
 * AI generated comments
 * Represents the payload for a contents processing status update message.
 * This class contains information about the processing status of batch documents.
 */
public class GContentsProcessingStatusUpdatePayload extends GBaseMessagePayload{

    // Identifier for the current processing job
	private String jobId = null;
	
    // Flag to indicate if this is the last message
	private Boolean lastMessage = null;
	
    // Number of batch documents processed in various stages
	private Long howManyBatchDocuments = null,
	             howManyBatchSentToVectorization = null,
	             howManyBatchContentsReadingErrors = null,
	             howManyBatchPersistendDocuments = null;
	
    // Timestamp indicating when this status update was created
	private Date timestamp = new Date();
	
    /**
     * Default constructor.
     */
	public GContentsProcessingStatusUpdatePayload() {
	}
	
    /**
     * Retrieves the job identifier.
     *
     * @return the job identifier
     */
	public String getJobId() {
		return jobId;
	}
	
    /**
     * Sets the job identifier.
     *
     * @param jobId the job identifier
     */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
    /**
     * Retrieves the total number of batch documents.
     *
     * @return the number of batch documents
     */
	public Long getHowManyBatchDocuments() {
		return howManyBatchDocuments;
	}
	
    /**
     * Sets the total number of batch documents.
     *
     * @param howManyBatchDocuments the number of batch documents
     */
	public void setHowManyBatchDocuments(Long howManyBatchDocuments) {
		this.howManyBatchDocuments = howManyBatchDocuments;
	}
	
    /**
     * Retrieves the number of batch documents sent to vectorization.
     *
     * @return the number of batch documents sent to vectorization
     */
	public Long getHowManyBatchSentToVectorization() {
		return howManyBatchSentToVectorization;
	}
	
    /**
     * Sets the number of batch documents sent to vectorization.
     *
     * @param howManyBatchSentToVectorization the number of batch documents for vectorization
     */
	public void setHowManyBatchSentToVectorization(Long howManyBatchSentToVectorization) {
		this.howManyBatchSentToVectorization = howManyBatchSentToVectorization;
	}
	
    /**
     * Retrieves the number of content reading errors.
     *
     * @return the number of content reading errors
     */
	public Long getHowManyBatchContentsReadingErrors() {
		return howManyBatchContentsReadingErrors;
	}
	
    /**
     * Sets the number of content reading errors.
     *
     * @param howManyBatchContentsReadingErrors the number of content reading errors
     */
	public void setHowManyBatchContentsReadingErrors(Long howManyBatchContentsReadingErrors) {
		this.howManyBatchContentsReadingErrors = howManyBatchContentsReadingErrors;
	}
	
    /**
     * Retrieves the number of persistent documents in the batch.
     *
     * @return the number of persistent documents
     */
	public Long getHowManyBatchPersistendDocuments() {
		return howManyBatchPersistendDocuments;
	}
	
    /**
     * Sets the number of persistent documents in the batch.
     *
     * @param howManyBatchPersistendDocuments the number of persistent documents
     */
	public void setHowManyBatchPersistendDocuments(Long howManyBatchPersistendDocuments) {
		this.howManyBatchPersistendDocuments = howManyBatchPersistendDocuments;
	}
	
    /**
     * Checks if this is the last message in the series.
     *
     * @return true if it's the last message, false otherwise
     */
	public Boolean getLastMessage() {
		return lastMessage;
	}
	
    /**
     * Sets the flag indicating if this is the last message.
     *
     * @param lastMessage flag to indicate if this is the last message
     */
	public void setLastMessage(Boolean lastMessage) {
		this.lastMessage = lastMessage;
	}
	
    /**
     * Retrieves the timestamp of when this status update was created.
     *
     * @return the timestamp
     */
	public Date getTimestamp() {
		return timestamp;
	}
	
    /**
     * Sets the timestamp for when this status update was created.
     *
     * @param timestamp the timestamp of the status update
     */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}