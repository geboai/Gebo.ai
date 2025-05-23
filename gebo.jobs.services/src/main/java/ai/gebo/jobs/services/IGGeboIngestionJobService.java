/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jobs.services;

import ai.gebo.architecture.multithreading.IGRunnable;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * Interface defining operations for managing Gebo ingestion jobs.
 * This service handles the lifecycle of ingestion jobs including creation, status checking, and completion.
 */
public interface IGGeboIngestionJobService {

	/**
	 * Completes an asynchronous job with the provided status.
	 * 
	 * @param status the current status of the job to complete
	 * @throws GeboJobServiceException if there is an error completing the job
	 */
	public void completeAsyncJob(GJobStatus status) throws GeboJobServiceException;

	/**
	 * Completes a synchronous job with the provided status and returns the updated status.
	 * 
	 * @param status the current status of the job to complete
	 * @return the updated job status after completion
	 * @throws GeboJobServiceException if there is an error completing the job
	 */
	public GJobStatus completeSyncJob(GJobStatus status) throws GeboJobServiceException;

	/**
	 * Checks if a job is currently running for the specified endpoint.
	 * 
	 * @param endpoint reference to the project endpoint to check
	 * @return true if a job is running for the endpoint, false otherwise
	 */
	public boolean isJobRunning(GObjectRef<GProjectEndpoint> endpoint);

	/**
	 * Creates a new asynchronous job with the provided status.
	 * 
	 * @param status the initial status for the new job
	 * @return a runnable object that can execute the job asynchronously
	 */
	public IGRunnable createAsyncJob(GJobStatus status);
}