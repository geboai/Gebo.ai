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
 * Represents a payload for updating the status of contents vectorization jobs.
 * Includes information about the current state of the vectorization process.
 * 
 * AI generated comments
 */
public class GContentsVectorizationStatusUpdatePayload extends GBaseMessagePayload {
	// Unique identifier for the vectorization job
	private String jobId = null;

	// Counters for tracking the number of documents vectorized and received in the current batch
	private long currentBatchDocumentVectorizedCounter = 0l, currentBatchDocumentReceviedCounter = 0l;
	
	// Counter for tracking the number of errors occurred during vectorization
	private long vectorizationErrors = 0l;
	
	// Number of segments and tokens that have been vectorized
	private long vectorizedSegments = 0l;
	private long vectorizedTokens = 0l;
	
	// Timestamp indicating the last update time
	private Date timestamp = new Date();

	/**
	 * Default constructor for creating an instance of the payload with default values.
	 */
	public GContentsVectorizationStatusUpdatePayload() {

	}

	/**
	 * Retrieves the job ID.
	 * @return the job ID as a String.
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * Sets the job ID for the vectorization process.
	 * @param jobId the unique identifier of the job.
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * Retrieves the number of documents vectorized in the current batch.
	 * @return the number of vectorized documents.
	 */
	public long getCurrentBatchDocumentVectorizedCounter() {
		return currentBatchDocumentVectorizedCounter;
	}

	/**
	 * Sets the number of documents vectorized in the current batch.
	 * @param currentBatchDocumentVectorizedCounter count of vectorized documents.
	 */
	public void setCurrentBatchDocumentVectorizedCounter(long currentBatchDocumentVectorizedCounter) {
		this.currentBatchDocumentVectorizedCounter = currentBatchDocumentVectorizedCounter;
	}

	/**
	 * Retrieves the number of documents received in the current batch.
	 * @return the number of received documents.
	 */
	public long getCurrentBatchDocumentReceviedCounter() {
		return currentBatchDocumentReceviedCounter;
	}

	/**
	 * Sets the number of documents received in the current batch.
	 * @param currentBatchDocumentReceviedCounter count of received documents.
	 */
	public void setCurrentBatchDocumentReceviedCounter(long currentBatchDocumentReceviedCounter) {
		this.currentBatchDocumentReceviedCounter = currentBatchDocumentReceviedCounter;
	}

	/**
	 * Retrieves the timestamp of the last update.
	 * @return a Date object representing the timestamp.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp for the status update.
	 * @param timestamp the Date to be set.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Retrieves the total number of vectorized segments.
	 * @return the number of vectorized segments.
	 */
	public long getVectorizedSegments() {
		return vectorizedSegments;
	}

	/**
	 * Sets the total number of vectorized segments.
	 * @param vectorizedSegments the count of vectorized segments.
	 */
	public void setVectorizedSegments(long vectorizedSegments) {
		this.vectorizedSegments = vectorizedSegments;
	}

	/**
	 * Retrieves the total number of vectorized tokens.
	 * @return the number of vectorized tokens.
	 */
	public long getVectorizedTokens() {
		return vectorizedTokens;
	}

	/**
	 * Sets the total number of vectorized tokens.
	 * @param vectorizedTokens the count of vectorized tokens.
	 */
	public void setVectorizedTokens(long vectorizedTokens) {
		this.vectorizedTokens = vectorizedTokens;
	}

	/**
	 * Retrieves the count of errors occurred during vectorization.
	 * @return the number of vectorization errors.
	 */
	public long getVectorizationErrors() {
		return vectorizationErrors;
	}

	/**
	 * Sets the count of errors that occurred during vectorization.
	 * @param vectorizationErrors the number of errors to set.
	 */
	public void setVectorizationErrors(long vectorizationErrors) {
		this.vectorizationErrors = vectorizationErrors;
	}

}