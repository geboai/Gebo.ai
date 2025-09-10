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
import lombok.Data;

/**
 * AI generated comments Represents the payload for a contents processing status
 * update message. This class contains information about the processing status
 * of batch documents.
 */
@Data
public class GContentsProcessingStatusUpdatePayload extends GBaseMessagePayload {

	// Identifier for the current processing job
	private String jobId = null;

	// Flag to indicate if this is the last message
	private Boolean lastMessage = null;

	// Number of batch documents processed in various stages
	private long batchDocumentsInput = 0l, batchSentToNextStep = 0l, batchDocumentsProcessingErrors = 0l,
			batchDocumentsProcessed = 0l, chunksProcessed = 0l, tokensProcessed = 0l;
	private String workflowType = null, workflowId = null, workflowStepId = null;
	// Timestamp indicating when this status update was created
	private Date timestamp = new Date();
	public void incrementBy(GContentsProcessingStatusUpdatePayload x) {
		this.batchDocumentsInput += x.batchDocumentsInput;
		this.batchDocumentsProcessed += x.batchDocumentsProcessed;
		this.batchDocumentsProcessingErrors += x.batchDocumentsProcessingErrors;
		this.batchSentToNextStep += x.batchSentToNextStep;
		this.chunksProcessed += x.chunksProcessed;
		this.tokensProcessed += x.tokensProcessed;
		
	}

}