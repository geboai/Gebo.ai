/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.jobs;

import java.util.Date;

import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * AI generated comments
 * Represents a batch processed by the Vectorizator with relevant counters and identifiers.
 */
@Document
public class VectorizatorBatchProcessed {

	/** Unique identifier for the batch process */
	@Id
	private String id = null;

	/** Identifier for the job associated with this batch */
	@HashIndexed
	private String jobId = null;

	/** Counter for the number of documents vectorized in the current batch */
	private long currentBatchDocumentVectorizedCounter = 0, currentBatchDocumentReceviedCounter = 0;

	/** Number of errors encountered during vectorization */
	private long vectorizationErrors = 0l;

	/** Counter for the number of segments vectorized */
	private long vectorizedSegments = 0l;

	/** Counter for the number of tokens vectorized */
	private long vectorizedTokens = 0l;

	/** Timestamp indicating when the batch was processed */
	@Order
	private Date timestamp = null;

	/** Flag indicating if this is the last message in a batch sequence */
	private Boolean lastMessage = null;

	/**
	 * Default constructor for VectorizatorBatchProcessed.
	 */
	public VectorizatorBatchProcessed() {

	}

	/**
	 * Gets the unique identifier for the batch process.
	 * @return the unique identifier
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for the batch process.
	 * @param id the unique identifier to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the job identifier associated with this batch.
	 * @return the job identifier
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * Sets the job identifier for this batch.
	 * @param jobId the job identifier to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * Gets the number of documents vectorized in the current batch.
	 * @return the number of vectorized documents
	 */
	public long getCurrentBatchDocumentVectorizedCounter() {
		return currentBatchDocumentVectorizedCounter;
	}

	/**
	 * Sets the number of documents vectorized in the current batch.
	 * @param currentBatchDocumentVectorizedCounter the count to set
	 */
	public void setCurrentBatchDocumentVectorizedCounter(long currentBatchDocumentVectorizedCounter) {
		this.currentBatchDocumentVectorizedCounter = currentBatchDocumentVectorizedCounter;
	}

	/**
	 * Gets the number of documents received in the current batch.
	 * @return the number of received documents
	 */
	public long getCurrentBatchDocumentReceviedCounter() {
		return currentBatchDocumentReceviedCounter;
	}

	/**
	 * Sets the number of documents received in the current batch.
	 * @param currentBatchDocumentReceviedCounter the count to set
	 */
	public void setCurrentBatchDocumentReceviedCounter(long currentBatchDocumentReceviedCounter) {
		this.currentBatchDocumentReceviedCounter = currentBatchDocumentReceviedCounter;
	}

	/**
	 * Gets the timestamp indicating when the batch was processed.
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp indicating when the batch was processed.
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the flag indicating if this is the last message in a batch sequence.
	 * @return true if it is the last message, otherwise false
	 */
	public Boolean getLastMessage() {
		return lastMessage;
	}

	/**
	 * Sets the flag indicating if this is the last message in a batch sequence.
	 * @param lastMessage the flag to set
	 */
	public void setLastMessage(Boolean lastMessage) {
		this.lastMessage = lastMessage;
	}

	/**
	 * Gets the number of segments vectorized in the batch.
	 * @return the number of vectorized segments
	 */
	public long getVectorizedSegments() {
		return vectorizedSegments;
	}

	/**
	 * Sets the number of segments vectorized in the batch.
	 * @param vectorizedSegments the number of segments to set
	 */
	public void setVectorizedSegments(long vectorizedSegments) {
		this.vectorizedSegments = vectorizedSegments;
	}

	/**
	 * Gets the number of tokens vectorized in the batch.
	 * @return the number of vectorized tokens
	 */
	public long getVectorizedTokens() {
		return vectorizedTokens;
	}

	/**
	 * Sets the number of tokens vectorized in the batch.
	 * @param vectorizedTokens the number of tokens to set
	 */
	public void setVectorizedTokens(long vectorizedTokens) {
		this.vectorizedTokens = vectorizedTokens;
	}

	/**
	 * Gets the number of errors encountered during vectorization.
	 * @return the number of vectorization errors
	 */
	public long getVectorizationErrors() {
		return vectorizationErrors;
	}

	/**
	 * Sets the number of errors encountered during vectorization.
	 * @param vectorizationErrors the number of errors to set
	 */
	public void setVectorizationErrors(long vectorizationErrors) {
		this.vectorizationErrors = vectorizationErrors;
	}
}