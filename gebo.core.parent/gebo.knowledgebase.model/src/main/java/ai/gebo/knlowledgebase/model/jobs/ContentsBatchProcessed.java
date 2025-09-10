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

import lombok.Data;

/**
 * Represents a batch of processed content, including metadata about the batch
 * processing. Provides methods to access and modify the batch details.
 * 
 * Gebo.ai Commentor signature: AI generated comments
 */
@Data
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
	@HashIndexed
	private String workflowType = null;
	@HashIndexed
	private String workflowId = null;
	@HashIndexed
	private String workflowStepId = null;

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
	private long batchDocumentsInput = 0;

	/**
	 * The number of documents sent to vectorization in the batch.
	 */
	private long batchSentToNextStep = 0;
	private long chunksProcessed = 0l;
	private long tokensProcessed = 0l;
	/**
	 * The number of content reading errors encountered in the batch.
	 */
	private long batchDocumentsProcessingErrors = 0;

	/**
	 * The number of documents successfully persisted in the batch.
	 */
	private long batchDocumentsProcessed = 0;

	public void incrementBy(ContentsBatchProcessed x) {
		this.batchDocumentsInput += x.batchDocumentsInput;
		this.batchDocumentsProcessed += x.batchDocumentsProcessed;
		this.batchDocumentsProcessingErrors += x.batchDocumentsProcessingErrors;
		this.batchSentToNextStep += x.batchSentToNextStep;
		this.chunksProcessed += x.chunksProcessed;
		this.tokensProcessed += x.tokensProcessed;
		this.lastMessage = (this.lastMessage != null && this.lastMessage)
				|| (x.getLastMessage() != null && x.getLastMessage());
	}

}