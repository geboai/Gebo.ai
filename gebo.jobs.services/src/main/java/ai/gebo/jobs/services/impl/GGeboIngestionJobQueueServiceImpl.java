/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.jobs.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandlerRepositoryPattern;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.architecture.multithreading.IGRunnable;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.IGGeboIngestionJobService;
import ai.gebo.jobs.services.model.JobSummary;
import ai.gebo.jobs.services.model.JobWorkflowStepSummary;
import ai.gebo.jobs.services.model.JobWorkflowStepSummaryTimeSlotStats;
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
	@Autowired
	IWorkflowStatusHandlerRepositoryPattern workflowHandlersRepositoryPattern;

	private final static long STATS_TIME_SLOT =  1000*60;
	private final static Logger LOGGER = LoggerFactory.getLogger(GGeboIngestionJobQueueServiceImpl.class);

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
	public GJobStatus createNewAsyncJob(GProjectEndpoint item, String workflowType, String workflowId)
			throws GeboJobServiceException {
		if (ingestionManager.isJobRunning(GObjectRef.of(item)))
			throw new GeboJobServiceException("Already running sync on " + item.getCode());
		GJobStatus status = ingestionManager.internalCreateContentsExtractionAndVectorizationStatus(item, workflowType,
				workflowId);
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
	public GJobStatus executeSyncJob(GProjectEndpoint item, String workflowType, String workflowId)
			throws GeboJobServiceException {
		if (readingService.isJobRunning(GObjectRef.of(item)))
			throw new GeboJobServiceException("Already running sync on " + item.getCode());
		GJobStatus status = ingestionManager.internalCreateContentsExtractionAndVectorizationStatus(item, workflowType,
				workflowId);
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
	public IGRunnable createPublicationRunnable(GObjectRef<GProjectEndpoint> endpoint, String workflowType,
			String workflowId) throws GeboJobServiceException, GeboPersistenceException {
		if (readingService.isJobRunning(endpoint))
			throw new GeboJobServiceException("Already running sync on " + endpoint.getCode());
		GJobStatus status = ingestionManager.internalCreateContentsExtractionAndVectorizationStatus(endpoint,
				workflowType, workflowId);
		synchronized (statusMap) {
			statusMap.put(status.getCode(), status);
		}
		return readingService.createAsyncJob(status);

	}

	private static String of(String s) {
		return s != null ? s.trim().toLowerCase() : "";
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
	public JobSummary getJobSummary(String jobId) throws GeboJobServiceException, GeboPersistenceException {
		Optional<GJobStatus> jobOpt = statusRepository.findById(jobId);
		if (jobOpt.isEmpty())
			return null;
		GJobStatus job = jobOpt.get();
		final JobSummary summary = new JobSummary();
		summary.setCode(job.getCode());
		summary.setDescription(job.getDescription());
		String workflowType = job.getWorkflowType();
		String workflowId = job.getWorkflowId();
		summary.setWorkflowType(workflowType);
		summary.setWorkflowId(workflowId);
		List<IWorkflowStatusHandler> handler = workflowHandlersRepositoryPattern
				.findByWorkflowsTypeAndWorkflowId(GWorkflowType.valueOf(workflowType), workflowId);
		if (!handler.isEmpty()) {
			ComputedWorkflowResult status = handler.get(0).computeWorkflowStatus(jobId, workflowType, workflowId);
			summary.setWorkflowStatus(status);
			final List<ComputedWorkflowStatus> itemslist = handler.get(0).items(status.getRootStatus());
			final TreeMap<Long, Map<String, JobWorkflowStepSummaryTimeSlotStats>> stats = new TreeMap<Long, Map<String, JobWorkflowStepSummaryTimeSlotStats>>();
			Stream<ContentsBatchProcessed> stream = contentsBatchRepo.findByJobId(jobId);
			List<ContentsBatchProcessed> list = stream.toList();
			list.forEach(processed -> {
				Date timestamp = processed.getTimestamp();
				if (timestamp != null) {
					long minutesFloorStart = (timestamp.getTime() / STATS_TIME_SLOT) * STATS_TIME_SLOT;
					long minutesFloorEnd = minutesFloorStart + STATS_TIME_SLOT;
					Long key = new Long(minutesFloorStart);
					if (!stats.containsKey(key)) {
						stats.put(key, new HashMap<String, JobWorkflowStepSummaryTimeSlotStats>());
					}
					String stepKey = of(jobId) + "-" + of(processed.getWorkflowType()) + "-"
							+ of(processed.getWorkflowId()) + "-" + processed.getWorkflowStepId();
					if (!stats.get(key).containsKey(stepKey)) {
						JobWorkflowStepSummaryTimeSlotStats statsEntry = new JobWorkflowStepSummaryTimeSlotStats();
						statsEntry.setStartDateTime(new Date(minutesFloorStart));
						statsEntry.setEndDateTime(new Date(minutesFloorEnd));
						stats.get(key).put(stepKey, statsEntry);
					}
					stats.get(key).get(stepKey).incrementBy(processed);
				} else {
					LOGGER.error("Entry without timestamp");
				}
			});
			if (stats.firstKey() != null && stats.lastKey() != null) {
				long startDateTime = stats.firstKey();
				long endDateTime = stats.lastKey();
				for (ComputedWorkflowStatus item : itemslist) {
					String stepKey = of(jobId) + "-" + of(item.getWorkflowType()) + "-" + of(item.getWorkflowId()) + "-"
							+ of(item.getWorkflowStepId());
					JobWorkflowStepSummary stepSummary = new JobWorkflowStepSummary();
					stepSummary.setWorkflowType(item.getWorkflowType());
					stepSummary.setWorkflowId(item.getWorkflowId());
					stepSummary.setWorkflowStepId(item.getWorkflowStepId());
					for (long actualDateTime = startDateTime; actualDateTime <= endDateTime; actualDateTime += STATS_TIME_SLOT) {
						Map<String, JobWorkflowStepSummaryTimeSlotStats> entry = stats.get(new Long(actualDateTime));
						JobWorkflowStepSummaryTimeSlotStats slot = null;
						if (entry != null && entry.containsKey(stepKey)) {
							slot = entry.get(stepKey);
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("date=" + new Date(actualDateTime) + "=>" + slot.toString());
							}
						} else {
							slot = new JobWorkflowStepSummaryTimeSlotStats();
							slot.setStartDateTime(new Date(actualDateTime));
							slot.setEndDateTime(new Date(actualDateTime + STATS_TIME_SLOT));
						}
						stepSummary.getTimesamples().add(slot);
					}
					summary.getWorkflowStepsSummaries().add(stepSummary);
				}

			}
		}
		//LOGGER.info("Summary=>"+summary.getWorkflowStepsSummaries());
		return summary;
	}

}