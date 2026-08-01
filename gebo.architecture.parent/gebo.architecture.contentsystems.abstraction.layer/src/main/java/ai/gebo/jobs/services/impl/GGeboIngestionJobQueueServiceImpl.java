/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.jobs.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.IWorkflowStatusHandlerRepositoryPattern;
import ai.gebo.architecture.multithreading.IGRunnable;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.replicator.service.IEntityReplicationService;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.IGGeboIngestionJobService;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.AbstractContentConsumingSessionParam;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.model.base.GObjectRef;
import lombok.AllArgsConstructor;

/**
 * Implementation of the Gebo Ingestion Job Queue Service. This service manages
 * the queue of ingestion jobs for processing and vectorizing content. AI
 * generated comments
 */
@Component()
@Scope("singleton")
@AllArgsConstructor
public class GGeboIngestionJobQueueServiceImpl implements IGGeboIngestionJobQueueService {
	/** The ingestion manager responsible for handling ingestion tasks */
	private final GeboIngestionManager ingestionManager;
	/** Map to track the status of jobs by their code */
	private final Map<String, GJobStatus> statusMap = new HashMap<String, GJobStatus>();
	/** Service for handling ingestion job operations */
	private final IGGeboIngestionJobService readingService;
	/** Repository for persisting job status information */
	private final JobStatusRepository statusRepository;
	private final IWorkflowStatusHandlerRepositoryPattern workflowHandlersRepositoryPattern;
	private final AbstractJobStatusEmitter statusEmitter;
	/** Replicates the freshly-created GJobStatus to tyr; a no-op in the monolith. */
	private final IEntityReplicationService entityReplicationService;

	private final static Logger LOGGER = LoggerFactory.getLogger(GGeboIngestionJobQueueServiceImpl.class);

	/**
	 * Creates a new asynchronous ingestion job for the specified endpoint
	 * 
	 * @param item The project endpoint to create a job for
	 * @return The created job status object
	 * @throws GeboJobServiceException If a job is already running for the endpoint
	 */
	@Override
	public <EndpointType extends GProjectEndpoint, SessionParameterType extends AbstractContentConsumingSessionParam> GJobStatus createNewAsyncJob(
			EndpointType item, SessionParameterType sessionParam, String workflowType, String workflowId)
			throws GeboJobServiceException {
		if (ingestionManager.isJobRunning(GObjectRef.of(item)))
			throw new GeboJobServiceException("Already running sync on " + item.getCode());
		GJobStatus status;
		try {
			status = ingestionManager.internalCreateContentsExtractionAndVectorizationStatus(item, workflowType,
					workflowId);
			entityReplicationService.replicate(status);
			statusEmitter.broadcastStarted(status);			
			synchronized (statusMap) {
				statusMap.put(status.getCode(), status);
				readingService.completeAsyncJob(status, sessionParam);

			}
			return status;
		} catch (GeboPersistenceException e) {
			throw new GeboJobServiceException("Exception on persistence access", e);
		}
	}

	/**
	 * Aborts an asynchronous job by its code
	 * 
	 * @param code The job code to abort
	 * @throws GeboJobServiceException If there's an issue aborting the job
	 */
	@Override
	public void abortAsyncJob(String code) throws GeboJobServiceException {
		// Implementation pending
	}

	/**
	 * Executes a synchronous ingestion job for the specified endpoint
	 * 
	 * @param item The project endpoint to create a job for
	 * @return The job status object after execution
	 * @throws GeboJobServiceException If a job is already running for the endpoint
	 */
	@Override
	public <EndpointType extends GProjectEndpoint, SessionParameterType extends AbstractContentConsumingSessionParam> GJobStatus executeSyncJob(
			EndpointType item, SessionParameterType sessionParam, String workflowType, String workflowId)
			throws GeboJobServiceException {
		if (readingService.isJobRunning(GObjectRef.of(item)))
			throw new GeboJobServiceException("Already running sync on " + item.getCode());
		try {
			GJobStatus status = ingestionManager.internalCreateContentsExtractionAndVectorizationStatus(item,
					workflowType, workflowId);
			entityReplicationService.replicate(status);
			statusEmitter.broadcastStarted(status);
			return ingestionManager.internalReadAndVectorizeContents(status, sessionParam);
		} catch (GeboPersistenceException e) {
			throw new GeboJobServiceException("Exception on persistence access", e);
		}

	}

	/**
	 * Checks if a synchronous job is running for the given endpoint
	 * 
	 * @param item The project endpoint to check
	 * @return true if a job is running, false otherwise
	 */
	@Override
	public boolean isRunningSyncJob(GProjectEndpoint item) {
		GObjectRef<GProjectEndpoint> ref = GObjectRef.of(item);
		return this.ingestionManager.isJobRunning(ref);

	}

	/**
	 * Checks if a synchronous job is running for the given endpoint reference
	 * 
	 * @param endpoint The project endpoint reference to check
	 * @return true if a job is running, false otherwise
	 */
	@Override
	public boolean isRunningSyncJob(GObjectRef<GProjectEndpoint> endpoint) {
		return this.ingestionManager.isJobRunning(endpoint);
	}

	/**
	 * Creates a runnable task for publication of content
	 * 
	 * @param endpoint The project endpoint reference
	 * @return A runnable task for async execution
	 * @throws GeboJobServiceException  If a job is already running
	 * @throws GeboPersistenceException If there's an issue with persistence
	 * @throws ClassNotFoundException
	 */
	@Override
	public <EndpointType extends GProjectEndpoint, SessionParameterType extends AbstractContentConsumingSessionParam> IGRunnable createPublicationRunnable(
			GObjectRef<EndpointType> endpoint, SessionParameterType sessionParam, String workflowType,
			String workflowId) throws GeboJobServiceException, GeboPersistenceException, ClassNotFoundException {
		if (readingService.isJobRunning(endpoint))
			throw new GeboJobServiceException("Already running sync on " + endpoint.getCode());
		GJobStatus status = ingestionManager.internalCreateContentsExtractionAndVectorizationStatus(endpoint,
				workflowType, workflowId);
		entityReplicationService.replicate(status);
		statusEmitter.broadcastStarted(status);
		synchronized (statusMap) {
			statusMap.put(status.getCode(), status);
		}
		return readingService.createAsyncJob(status, sessionParam);

	}

}