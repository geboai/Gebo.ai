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
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.multithreading.IGRunnable;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.IGGeboIngestionJobService;
import ai.gebo.jobs.services.model.JobSummary;
import ai.gebo.jobs.services.model.JobSummary.AggregatedEvents;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.ContentsBatchProcessedRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.model.base.GObjectRef;

/**
 * Implementation of the Gebo Ingestion Job Queue Service. This service manages
 * the queue of ingestion jobs for processing and vectorizing content. AI
 * generated comments
 */
@Component()
@Scope("singleton")
public class GGeboIngestionJobQueueServiceImpl implements IGGeboIngestionJobQueueService {
	/** The ingestion manager responsible for handling ingestion tasks */
	@Autowired
	GeboIngestionManager ingestionManager;

	/** Map to track the status of jobs by their code */
	Map<String, GJobStatus> statusMap = new HashMap<String, GJobStatus>();

	/** Service for handling ingestion job operations */
	@Autowired
	IGGeboIngestionJobService readingService;

	/** Repository for persisting job status information */
	@Autowired
	JobStatusRepository statusRepository;

	/** Repository for content batch processing data */
	@Autowired
	ContentsBatchProcessedRepository contentsBatchRepo;

	/**
	 * Default constructor
	 */
	public GGeboIngestionJobQueueServiceImpl() {

	}

	/**
	 * Creates a new asynchronous ingestion job for the specified endpoint
	 * 
	 * @param item The project endpoint to create a job for
	 * @return The created job status object
	 * @throws GeboJobServiceException If a job is already running for the endpoint
	 */
	@Override
	public GJobStatus createNewAsyncJob(GProjectEndpoint item) throws GeboJobServiceException {
		if (ingestionManager.isJobRunning(GObjectRef.of(item)))
			throw new GeboJobServiceException("Already running sync on " + item.getCode());
		GJobStatus status = ingestionManager.internalCreateContentsExtractionAndVectorizationStatus(item);
		synchronized (statusMap) {
			statusMap.put(status.getCode(), status);
			readingService.completeAsyncJob(status);

		}
		return status;
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
	public GJobStatus executeSyncJob(GProjectEndpoint item) throws GeboJobServiceException {
		if (readingService.isJobRunning(GObjectRef.of(item)))
			throw new GeboJobServiceException("Already running sync on " + item.getCode());
		GJobStatus status = ingestionManager.internalCreateContentsExtractionAndVectorizationStatus(item);
		return ingestionManager.internalReadAndVectorizeContents(status);
	}

	/**
	 * Retrieves the status of a job by its code
	 * 
	 * @param code The job code to look up
	 * @return The job status object or null if not found
	 * @throws GeboJobServiceException If there's an issue retrieving the status
	 */
	@Override
	public GJobStatus getStatus(String code) throws GeboJobServiceException {
		GJobStatus status = statusMap.get(code);
		if (status == null) {
			Optional<GJobStatus> value = statusRepository.findById(code);
			if (value.isPresent()) {
				status = value.get();
			}
		}
		return status;
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
	 */
	@Override
	public IGRunnable createPublicationRunnable(GObjectRef<GProjectEndpoint> endpoint)
			throws GeboJobServiceException, GeboPersistenceException {
		if (readingService.isJobRunning(endpoint))
			throw new GeboJobServiceException("Already running sync on " + endpoint.getCode());
		GJobStatus status = ingestionManager.internalCreateContentsExtractionAndVectorizationStatus(endpoint);
		synchronized (statusMap) {
			statusMap.put(status.getCode(), status);
		}
		return readingService.createAsyncJob(status);

	}

	/**
	 * Retrieves a summary of a job with optional details
	 * 
	 * @param jobId   The job ID to get summary for
	 * @param details Whether to include detailed vectorization processing data
	 * @return A job summary object or null if job not found
	 * @throws GeboJobServiceException  If there's an issue with the job service
	 * @throws GeboPersistenceException If there's an issue with persistence
	 */
	@Override
	public JobSummary getJobSummary(String jobId, boolean details)
			throws GeboJobServiceException, GeboPersistenceException {
		Optional<GJobStatus> jobOpt = statusRepository.findById(jobId);
		if (jobOpt.isEmpty())
			return null;
		GJobStatus job = jobOpt.get();
		final JobSummary summary = new JobSummary();
		summary.setCode(job.getCode());
		summary.setDescription(job.getDescription());
		Stream<ContentsBatchProcessed> contentsBatchStream = contentsBatchRepo.findByJobId(jobId);
		final Map<String, AggregatedEvents> aggregated = new HashMap<String, JobSummary.AggregatedEvents>();
		contentsBatchStream.forEach(x -> {
			String key = x.getWorkflowType() + "-" + x.getWorkflowId() + "-" + x.getWorkflowStepId();
			AggregatedEvents aggregatedValue = aggregated.get(key);
			if (aggregatedValue == null) {
				aggregatedValue = new AggregatedEvents();
				aggregatedValue.setAggregated(new ContentsBatchProcessed());
				aggregatedValue.getAggregated().setJobId(jobId);
				aggregatedValue.getAggregated().setWorkflowType(x.getWorkflowType());
				aggregatedValue.getAggregated().setWorkflowId(x.getWorkflowId());
				aggregatedValue.getAggregated().setWorkflowStepId(x.getWorkflowStepId());
				aggregated.put(key, aggregatedValue);
				summary.getAggregatedProcessingData().add(aggregatedValue);
			}
			aggregatedValue.getAggregated().incrementBy(x);
			aggregatedValue.getEvents().add(x);

		});

		return summary;
	}

}