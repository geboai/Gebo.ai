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

/**
 * Represents a batch of processed content, including metadata about the batch processing.
 * Provides methods to access and modify the batch details.
 * 
 * Gebo.ai Commentor signature: AI generated comments
 */
public class ContentsBatchProcessed {
    /**
     * Unique identifier for the contents batch.
     */
    @Id
    private String id = null;

    /**
     * Identifier for the job associated with this batch.
     */
    @HashIndexed
    private String jobId = null;

    /**
     * Indicator of whether this is the last message in the batch.
     */
    private Boolean lastMessage = null;

    /**
     * Timestamp indicating when the batch was processed.
     */
    @Order
    private Date timestamp = null;

    /**
     * The number of documents processed in the batch.
     */
    private long howManyBatchDocuments = 0;

    /**
     * The number of documents sent to vectorization in the batch.
     */
    private long howManyBatchSentToVectorization = 0;

    /**
     * The number of content reading errors encountered in the batch.
     */
    private long howManyBatchContentsReadingErrors = 0;

    /**
     * The number of documents successfully persisted in the batch.
     */
    private long howManyBatchPersistendDocuments = 0;

    /**
     * Default constructor for creating an instance of ContentsBatchProcessed.
     */
    public ContentsBatchProcessed() {
    }

    /**
     * Returns the unique identifier for the contents batch.
     * @return The unique id string.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the contents batch.
     * @param id The unique id string.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the job identifier associated with this batch.
     * @return The job id string.
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Sets the job identifier associated with this batch.
     * @param jobId The job id string.
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * Returns whether this is the last message in the batch.
     * @return Boolean indicating if it is the last message.
     */
    public Boolean getLastMessage() {
        return lastMessage;
    }

    /**
     * Sets whether this is the last message in the batch.
     * @param lastMessage Boolean indicating if it is the last message.
     */
    public void setLastMessage(Boolean lastMessage) {
        this.lastMessage = lastMessage;
    }

    /**
     * Returns the timestamp indicating when the batch was processed.
     * @return The timestamp of the batch.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp indicating when the batch was processed.
     * @param timestamp The timestamp to set.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the number of documents processed in the batch.
     * @return The count of documents.
     */
    public long getHowManyBatchDocuments() {
        return howManyBatchDocuments;
    }

    /**
     * Sets the number of documents processed in the batch.
     * @param howManyBatchDocuments The count of documents.
     */
    public void setHowManyBatchDocuments(long howManyBatchDocuments) {
        this.howManyBatchDocuments = howManyBatchDocuments;
    }

    /**
     * Returns the number of documents sent to vectorization in the batch.
     * @return The count of documents.
     */
    public long getHowManyBatchSentToVectorization() {
        return howManyBatchSentToVectorization;
    }

    /**
     * Sets the number of documents sent to vectorization in the batch.
     * @param howManyBatchSentToVectorization The count of documents.
     */
    public void setHowManyBatchSentToVectorization(long howManyBatchSentToVectorization) {
        this.howManyBatchSentToVectorization = howManyBatchSentToVectorization;
    }

    /**
     * Returns the number of content reading errors encountered in the batch.
     * @return The count of errors.
     */
    public long getHowManyBatchContentsReadingErrors() {
        return howManyBatchContentsReadingErrors;
    }

    /**
     * Sets the number of content reading errors encountered in the batch.
     * @param howManyBatchContentsReadingErrors The count of errors.
     */
    public void setHowManyBatchContentsReadingErrors(long howManyBatchContentsReadingErrors) {
        this.howManyBatchContentsReadingErrors = howManyBatchContentsReadingErrors;
    }

    /**
     * Returns the number of documents successfully persisted in the batch.
     * @return The count of persisted documents.
     */
    public long getHowManyBatchPersistendDocuments() {
        return howManyBatchPersistendDocuments;
    }

    /**
     * Sets the number of documents successfully persisted in the batch.
     * @param howManyBatchPersistendDocuments The count of persisted documents.
     */
    public void setHowManyBatchPersistendDocuments(long howManyBatchPersistendDocuments) {
        this.howManyBatchPersistendDocuments = howManyBatchPersistendDocuments;
    }
}