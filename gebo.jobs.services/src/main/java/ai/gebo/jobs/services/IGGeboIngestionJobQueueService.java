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
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.model.JobSummary;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * 
 * Interface for managing Gebo ingestion job queues, providing functionality to
 * create, track, and abort ingestion jobs. This service handles both
 * synchronous and asynchronous job processing for project endpoints.
 */
public interface IGGeboIngestionJobQueueService {
	/**
	 * Creates a new asynchronous ingestion job for the specified project endpoint.
	 * 
	 * @param item         The project endpoint to be processed
	 * @param workflowType TODO
	 * @param workflowId   TODO
	 * @return The status of the newly created job
	 * @throws GeboJobServiceException If job creation fails
	 */
	public GJobStatus createNewAsyncJob(GProjectEndpoint item, String workflowType, String workflowId)
			throws GeboJobServiceException;

	/**
	 * Aborts an asynchronous job that is currently running.
	 * 
	 * @param code The unique identifier of the job to abort
	 * @throws GeboJobServiceException If the job cannot be aborted
	 */
	public void abortAsyncJob(String code) throws GeboJobServiceException;

	/**
	 * Executes a synchronous ingestion job for the specified project endpoint. This
	 * method will block until the job completes.
	 * 
	 * @param item         The project endpoint to be processed
	 * @param workflowType TODO
	 * @param workflowId   TODO
	 * @return The final status of the executed job
	 * @throws GeboJobServiceException If job execution fails
	 */
	public GJobStatus executeSyncJob(GProjectEndpoint item, String workflowType, String workflowId)
			throws GeboJobServiceException;

	/**
	 * Checks if a synchronous job is currently running for the specified project
	 * endpoint.
	 * 
	 * @param item The project endpoint to check
	 * @return true if a job is running, false otherwise
	 */
	public boolean isRunningSyncJob(GProjectEndpoint item);

	/**
	 * Retrieves the current status of a job.
	 * 
	 * @param code The unique identifier of the job
	 * @return The current status of the job
	 * @throws GeboJobServiceException If the status cannot be retrieved
	 */
	public GJobStatus getStatus(String code) throws GeboJobServiceException;

	/**
	 * Checks if a synchronous job is currently running for the project endpoint
	 * referenced.
	 * 
	 * @param endpoint Reference to the project endpoint to check
	 * @return true if a job is running, false otherwise
	 */
	public boolean isRunningSyncJob(GObjectRef<GProjectEndpoint> endpoint);

	/**
	 * Creates a runnable task for publication processing of the specified endpoint.
	 * 
	 * @param endpoint     Reference to the project endpoint to be processed
	 * @param workflowType TODO
	 * @param workflowId   TODO
	 * @return A runnable task that can be submitted to an executor
	 * @throws GeboJobServiceException  If the runnable cannot be created
	 * @throws GeboPersistenceException If there are persistence-related issues
	 */
	public IGRunnable createPublicationRunnable(GObjectRef<GProjectEndpoint> endpoint, String workflowType,
			String workflowId) throws GeboJobServiceException, GeboPersistenceException;

	/**
	 * Retrieves detailed information about a specific job. By default, includes all
	 * detailed information.
	 * 
	 * @param jobId The unique identifier of the job
	 * @return A summary of the job with detailed information
	 * @throws GeboJobServiceException  If the job summary cannot be retrieved
	 * @throws GeboPersistenceException If there are persistence-related issues
	 */
	public JobSummary getJobSummary(String jobId) throws GeboJobServiceException, GeboPersistenceException;
}