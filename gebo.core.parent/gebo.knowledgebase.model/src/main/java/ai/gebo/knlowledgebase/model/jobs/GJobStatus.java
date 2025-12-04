/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knlowledgebase.model.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI generated comments Represents the status of a job process within the
 * system, tracking various stages such as processing and vectorization.
 */
@Document
@Data
public class GJobStatus extends GBaseObject {

	/**
	 * Enum representing different types of jobs that can be processed.
	 */
	public static enum JobType {
		CONTENTS_READING, VECTORIZING_CONTENTS, CONTENTS_READING_VECTORIZING
	}

	// Type of job currently being processed
	@NotNull
	private JobType jobType = null;
	@NotNull
	private String workflowType = null;
	@NotNull
	private String workflowId = null;

	// Indicates whether the job is currently in processing state
	private Boolean processing = null;

	// Indicates whether the job has finished execution
	private Boolean finished = null;

	// Indicator of whether there was an error in the job
	private Boolean error = null;

	// Start date and time of the job, with highest precedence order
	@Order(value = Ordered.HIGHEST_PRECEDENCE)
	private Date startDateTime = null;

	// End date and time of the job
	private Date endDateTime = null;
	// Reference to the associated project endpoint
	@NotNull
	private GObjectRef<GProjectEndpoint> projectEndpointReference = null;
	// Knowledge base code
	@NotNull
	private String knowledgeBaseCode = null;
	@NotNull
	private String projectCode = null;
	// Code identifying the parent job
	private String parentJobCode = null;
	private WorkflowStatus workflowStatus = null;

	/**
	 * Serial version UID for serialization purposes.
	 */
	private static final long serialVersionUID = -3660157973402786486L;

}