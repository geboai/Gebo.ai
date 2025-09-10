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

import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knlowledgebase.model.jobs.WorkflowStatus;
import lombok.Data;

/**
 * AI generated comments Represents a summary of a job execution including
 * statistics and status information. This class stores metrics about document
 * processing, vectorization, and overall job status. It implements Serializable
 * to support serialization/deserialization operations.
 */
@Data
public class JobSummary implements Serializable {
	// Job identification
	private String code = null;
	private String description = null;
	private String workflowType = null;
	private String workflowId = null;

	// Job timing information
	private Date startDateTime = null;
	private Date endDateTime = null;
	private ComputedWorkflowResult workflowStatus = null;

}