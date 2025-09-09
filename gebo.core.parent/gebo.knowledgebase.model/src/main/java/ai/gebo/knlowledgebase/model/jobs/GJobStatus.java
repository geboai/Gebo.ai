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

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * Represents the status of a job process within the system, tracking various stages such as processing and vectorization.
 */
@Document
public class GJobStatus extends GBaseObject {

    /**
     * Enum representing different types of jobs that can be processed.
     */
    public static enum JobType {
        CONTENTS_READING, VECTORIZING_CONTENTS, CONTENTS_READING_VECTORIZING
    }

    // Type of job currently being processed
    private JobType jobType = null;

    // Indicates whether the job is currently in processing state
    private Boolean processing = null;

    // Indicates whether the vectorization process has ended
    private Boolean vectorizationEnded = null;

    // Indicates whether the content batch process has ended
    private Boolean contentBatchEnded = null;

    // Indicates whether the job has finished execution
    private Boolean finished = null;

    // Indicator of whether there was an error in the job
    private Boolean error = null;

    // Indicates the last message of the batch process
    private Boolean lastBatchMessage = null;

    // Counter for different stages in batch processing
    private long howManyBatchDocuments = 0, howManyBatchSentToVectorization = 0, howManyBatchContentsReadingErrors = 0,
            howManyBatchPersistendDocuments = 0;

    // Counters for document vectorization process
    private long currentBatchDocumentVectorizedCounter = 0, currentBatchDocumentReceviedCounter = 0;

    // Count of errors occurred during vectorization
    private long vectorizationErrors = 0l;

    // Keeps track of segments and tokens processed during vectorization
    private long vectorizedSegments = 0l;
    private long vectorizedTokens = 0l;

    // Start date and time of the job, with highest precedence order
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    private Date startDateTime = null;

    // End date and time of the job
    private Date endDateTime = null;

    // Reference to the associated project endpoint
    private GObjectRef<GProjectEndpoint> projectEndpointReference = null;

    // Code identifying the parent job
    private String parentJobCode = null;

    /**
     * Serial version UID for serialization purposes.
     */
    private static final long serialVersionUID = -3660157973402786486L;

    /**
     * Default constructor for GJobStatus.
     */
    public GJobStatus() {

    }

    /**
     * @return whether the job is processing
     */
    public Boolean getProcessing() {
        return processing;
    }

    /**
     * Sets the processing state of the job.
     * @param processing new processing state
     */
    public void setProcessing(Boolean processing) {
        this.processing = processing;
    }

    /**
     * @return whether the job is finished
     */
    public Boolean getFinished() {
        return finished;
    }

    /**
     * Sets the finished state of the job.
     * @param finished new finished state
     */
    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    /**
     * @return whether there was an error in the job
     */
    public Boolean getError() {
        return error;
    }

    /**
     * Sets the error state of the job.
     * @param error new error state
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     * @return start datetime of the job
     */
    public Date getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets the start datetime of the job.
     * @param startDateTime new start datetime
     */
    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * @return end datetime of the job
     */
    public Date getEndDateTime() {
        return endDateTime;
    }

    /**
     * Sets the end datetime of the job.
     * @param endDateTime new end datetime
     */
    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * @return reference to the project endpoint
     */
    public GObjectRef<GProjectEndpoint> getProjectEndpointReference() {
        return projectEndpointReference;
    }

    /**
     * Sets the project endpoint reference for the job.
     * @param projectEndpointReference new project endpoint reference
     */
    public void setProjectEndpointReference(GObjectRef<GProjectEndpoint> projectEndpointReference) {
        this.projectEndpointReference = projectEndpointReference;
    }

    /**
     * @return the code identifying the parent job
     */
    public String getParentJobCode() {
        return parentJobCode;
    }

    /**
     * Sets the parent job code.
     * @param parentJobCode new parent job code
     */
    public void setParentJobCode(String parentJobCode) {
        this.parentJobCode = parentJobCode;
    }

    /**
     * @return the type of job
     */
    public JobType getJobType() {
        return jobType;
    }

    /**
     * Sets the type of job.
     * @param jobType new job type
     */
    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    /**
     * @return whether vectorization process has ended
     */
    public Boolean getVectorizationEnded() {
        return vectorizationEnded;
    }

    /**
     * Sets the vectorization ended state.
     * @param vectorizationEnded new vectorization ended state
     */
    public void setVectorizationEnded(Boolean vectorizationEnded) {
        this.vectorizationEnded = vectorizationEnded;
    }

    /**
     * @return whether content batch process has ended
     */
    public Boolean getContentBatchEnded() {
        return contentBatchEnded;
    }

    /**
     * Sets the content batch ended state.
     * @param contentBatchEnded new content batch ended state
     */
    public void setContentBatchEnded(Boolean contentBatchEnded) {
        this.contentBatchEnded = contentBatchEnded;
    }

    /**
     * @return whether this is the last batch message
     */
    public Boolean getLastBatchMessage() {
        return lastBatchMessage;
    }

    /**
     * Sets the last batch message state.
     * @param lastBatchMessage new last batch message state
     */
    public void setLastBatchMessage(Boolean lastBatchMessage) {
        this.lastBatchMessage = lastBatchMessage;
    }

    /**
     * @return total number of documents in the batch
     */
    public long getHowManyBatchDocuments() {
        return howManyBatchDocuments;
    }

    /**
     * Sets the total number of documents in the batch.
     * @param howManyBatchDocuments total number of documents
     */
    public void setHowManyBatchDocuments(long howManyBatchDocuments) {
        this.howManyBatchDocuments = howManyBatchDocuments;
    }

    /**
     * @return number of documents sent to vectorization
     */
    public long getHowManyBatchSentToVectorization() {
        return howManyBatchSentToVectorization;
    }

    /**
     * Sets the number of documents sent to vectorization.
     * @param howManyBatchSentToVectorization number of documents sent
     */
    public void setHowManyBatchSentToVectorization(long howManyBatchSentToVectorization) {
        this.howManyBatchSentToVectorization = howManyBatchSentToVectorization;
    }

    /**
     * @return number of content reading errors in the batch
     */
    public long getHowManyBatchContentsReadingErrors() {
        return howManyBatchContentsReadingErrors;
    }

    /**
     * Sets the number of content reading errors in the batch.
     * @param howManyBatchContentsReadingErrors number of errors
     */
    public void setHowManyBatchContentsReadingErrors(long howManyBatchContentsReadingErrors) {
        this.howManyBatchContentsReadingErrors = howManyBatchContentsReadingErrors;
    }

    /**
     * @return number of documents that have been persisted in the batch
     */
    public long getHowManyBatchPersistendDocuments() {
        return howManyBatchPersistendDocuments;
    }

    /**
     * Sets the number of persisted documents in the batch.
     * @param howManyBatchPersistendDocuments number of persisted documents
     */
    public void setHowManyBatchPersistendDocuments(long howManyBatchPersistendDocuments) {
        this.howManyBatchPersistendDocuments = howManyBatchPersistendDocuments;
    }

    /**
     * @return number of vectorized segments
     */
    public long getVectorizedSegments() {
        return vectorizedSegments;
    }

    /**
     * Sets the number of vectorized segments.
     * @param vectorizedSegments number of vectorized segments
     */
    public void setVectorizedSegments(long vectorizedSegments) {
        this.vectorizedSegments = vectorizedSegments;
    }

    /**
     * @return number of vectorized tokens
     */
    public long getVectorizedTokens() {
        return vectorizedTokens;
    }

    /**
     * Sets the number of vectorized tokens.
     * @param vectorizedTokens number of vectorized tokens
     */
    public void setVectorizedTokens(long vectorizedTokens) {
        this.vectorizedTokens = vectorizedTokens;
    }

    /**
     * Updates the job status with the processed contents batch information.
     * @param processed the contents batch processed information
     */
    public void updateWith(ContentsBatchProcessed processed) {
        if (processed.getLastMessage() != null && processed.getLastMessage()) {
            this.lastBatchMessage = true;
            this.contentBatchEnded = true;
        }
        this.howManyBatchDocuments += processed.getBatchDocumentsInput();
        this.howManyBatchContentsReadingErrors += processed.getBatchDocumentsProcessingErrors();
        this.howManyBatchPersistendDocuments += processed.getBatchDocumentsInput();
        this.howManyBatchSentToVectorization += processed.getBatchSentToNextStep();
    }

    

    /**
     * @return number of currently vectorized documents in the batch
     */
    public long getCurrentBatchDocumentVectorizedCounter() {
        return currentBatchDocumentVectorizedCounter;
    }

    /**
     * Sets the currently vectorized document counter for the batch.
     * @param currentBatchDocumentVectorizedCounter number of vectorized documents
     */
    public void setCurrentBatchDocumentVectorizedCounter(long currentBatchDocumentVectorizedCounter) {
        this.currentBatchDocumentVectorizedCounter = currentBatchDocumentVectorizedCounter;
    }

    /**
     * @return number of currently received documents in the batch
     */
    public long getCurrentBatchDocumentReceviedCounter() {
        return currentBatchDocumentReceviedCounter;
    }

    /**
     * Sets the currently received document counter for the batch.
     * @param currentBatchDocumentReceviedCounter number of received documents
     */
    public void setCurrentBatchDocumentReceviedCounter(long currentBatchDocumentReceviedCounter) {
        this.currentBatchDocumentReceviedCounter = currentBatchDocumentReceviedCounter;
    }

    /**
     * @return number of errors occurred during vectorization
     */
    public long getVectorizationErrors() {
        return vectorizationErrors;
    }

    /**
     * Sets the number of errors occurred during vectorization.
     * @param vectorizationErrors number of vectorization errors
     */
    public void setVectorizationErrors(long vectorizationErrors) {
        this.vectorizationErrors = vectorizationErrors;
    }
}