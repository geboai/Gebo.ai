/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jobs.services.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knlowledgebase.model.jobs.VectorizatorBatchProcessed;

/**
 * AI generated comments
 * Represents a summary of a job execution including statistics and status information.
 * This class stores metrics about document processing, vectorization, and overall job status.
 * It implements Serializable to support serialization/deserialization operations.
 */
public class JobSummary implements Serializable {
	// Job identification
	private String code = null;
	private String description = null;
	
	// Job timing information
	private Date startDateTime = null;
	private Date endDateTime = null;
	
	// Batch processing metrics
	private long howManyBatchDocuments = 0, howManyBatchSentToVectorization = 0, howManyBatchContentsReadingErrors = 0,
			howManyBatchPersistendDocuments = 0;
	
	// Current batch counters
	private long currentBatchDocumentVectorizedCounter = 0, currentBatchDocumentReceviedCounter = 0;
	
	// Vectorization metrics
	private long vectorizationErrors = 0l;
	private long vectorizedSegments = 0l;
	private long vectorizedTokens = 0l;
	
	// Status flags
	private Boolean contentsReadTerminated = false;
	private Boolean vectorizationTerminated = false;
	
	// Detailed processing data
	private List<ContentsBatchProcessed> contentsProcessingData=new ArrayList<ContentsBatchProcessed>();
	
	
	/**
	 * Default constructor for JobSummary
	 */
	public JobSummary() {

	}

	/**
	 * Gets the job code identifier
	 * @return the job code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the job code identifier
	 * @param code the job code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the job description
	 * @return the job description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the job description
	 * @param description the job description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the job start date and time
	 * @return the start date and time
	 */
	public Date getStartDateTime() {
		return startDateTime;
	}

	/**
	 * Sets the job start date and time
	 * @param startDateTime the start date and time to set
	 */
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * Gets the job end date and time
	 * @return the end date and time
	 */
	public Date getEndDateTime() {
		return endDateTime;
	}

	/**
	 * Sets the job end date and time
	 * @param endDateTime the end date and time to set
	 */
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	/**
	 * Gets the total number of batch documents processed
	 * @return the count of batch documents
	 */
	public long getHowManyBatchDocuments() {
		return howManyBatchDocuments;
	}

	/**
	 * Sets the total number of batch documents processed
	 * @param howManyBatchDocuments the count to set
	 */
	public void setHowManyBatchDocuments(long howManyBatchDocuments) {
		this.howManyBatchDocuments = howManyBatchDocuments;
	}

	/**
	 * Gets the number of batches sent for vectorization
	 * @return the count of batches sent for vectorization
	 */
	public long getHowManyBatchSentToVectorization() {
		return howManyBatchSentToVectorization;
	}

	/**
	 * Sets the number of batches sent for vectorization
	 * @param howManyBatchSentToVectorization the count to set
	 */
	public void setHowManyBatchSentToVectorization(long howManyBatchSentToVectorization) {
		this.howManyBatchSentToVectorization = howManyBatchSentToVectorization;
	}

	/**
	 * Gets the number of batch content reading errors
	 * @return the count of batch content reading errors
	 */
	public long getHowManyBatchContentsReadingErrors() {
		return howManyBatchContentsReadingErrors;
	}

	/**
	 * Sets the number of batch content reading errors
	 * @param howManyBatchContentsReadingErrors the count to set
	 */
	public void setHowManyBatchContentsReadingErrors(long howManyBatchContentsReadingErrors) {
		this.howManyBatchContentsReadingErrors = howManyBatchContentsReadingErrors;
	}

	/**
	 * Gets the number of batch documents that were persisted
	 * @return the count of persisted batch documents
	 */
	public long getHowManyBatchPersistendDocuments() {
		return howManyBatchPersistendDocuments;
	}

	/**
	 * Sets the number of batch documents that were persisted
	 * @param howManyBatchPersistendDocuments the count to set
	 */
	public void setHowManyBatchPersistendDocuments(long howManyBatchPersistendDocuments) {
		this.howManyBatchPersistendDocuments = howManyBatchPersistendDocuments;
	}

	/**
	 * Gets the current count of documents vectorized in the current batch
	 * @return the count of vectorized documents in the current batch
	 */
	public long getCurrentBatchDocumentVectorizedCounter() {
		return currentBatchDocumentVectorizedCounter;
	}

	/**
	 * Sets the current count of documents vectorized in the current batch
	 * @param currentBatchDocumentVectorizedCounter the count to set
	 */
	public void setCurrentBatchDocumentVectorizedCounter(long currentBatchDocumentVectorizedCounter) {
		this.currentBatchDocumentVectorizedCounter = currentBatchDocumentVectorizedCounter;
	}

	/**
	 * Gets the current count of documents received in the current batch
	 * @return the count of received documents in the current batch
	 */
	public long getCurrentBatchDocumentReceviedCounter() {
		return currentBatchDocumentReceviedCounter;
	}

	/**
	 * Sets the current count of documents received in the current batch
	 * @param currentBatchDocumentReceviedCounter the count to set
	 */
	public void setCurrentBatchDocumentReceviedCounter(long currentBatchDocumentReceviedCounter) {
		this.currentBatchDocumentReceviedCounter = currentBatchDocumentReceviedCounter;
	}

	/**
	 * Checks if content reading has been terminated
	 * @return true if content reading is complete, false otherwise
	 */
	public Boolean getContentsReadTerminated() {
		return contentsReadTerminated;
	}

	/**
	 * Sets the content reading termination status
	 * @param contentsReadTerminated the termination status to set
	 */
	public void setContentsReadTerminated(Boolean contentsReadTerminated) {
		this.contentsReadTerminated = contentsReadTerminated;
	}

	/**
	 * Checks if vectorization has been terminated
	 * @return true if vectorization is complete, false otherwise
	 */
	public Boolean getVectorizationTerminated() {
		return vectorizationTerminated;
	}

	/**
	 * Sets the vectorization termination status
	 * @param vectorizationTerminated the termination status to set
	 */
	public void setVectorizationTerminated(Boolean vectorizationTerminated) {
		this.vectorizationTerminated = vectorizationTerminated;
	}

	/**
	 * Gets the list of content batch processing data
	 * @return list of content batch processing data
	 */
	public List<ContentsBatchProcessed> getContentsProcessingData() {
		return contentsProcessingData;
	}

	/**
	 * Sets the list of content batch processing data
	 * @param contentsProcessingData the list to set
	 */
	public void setContentsProcessingData(List<ContentsBatchProcessed> contentsProcessingData) {
		this.contentsProcessingData = contentsProcessingData;
	}

	

	/**
	 * Gets the total count of vectorized segments
	 * @return the count of vectorized segments
	 */
	public long getVectorizedSegments() {
		return vectorizedSegments;
	}

	/**
	 * Sets the total count of vectorized segments
	 * @param vectorizedSegments the count to set
	 */
	public void setVectorizedSegments(long vectorizedSegments) {
		this.vectorizedSegments = vectorizedSegments;
	}

	/**
	 * Gets the total count of vectorized tokens
	 * @return the count of vectorized tokens
	 */
	public long getVectorizedTokens() {
		return vectorizedTokens;
	}

	/**
	 * Sets the total count of vectorized tokens
	 * @param vectorizedTokens the count to set
	 */
	public void setVectorizedTokens(long vectorizedTokens) {
		this.vectorizedTokens = vectorizedTokens;
	}

	/**
	 * Gets the total count of vectorization errors
	 * @return the count of vectorization errors
	 */
	public long getVectorizationErrors() {
		return vectorizationErrors;
	}

	/**
	 * Sets the total count of vectorization errors
	 * @param vectorizedErrors the count to set
	 */
	public void setVectorizationErrors(long vectorizedErrors) {
		this.vectorizationErrors = vectorizedErrors;
	}
	
}