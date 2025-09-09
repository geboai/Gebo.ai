/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.jobs.services.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.model.JobSummary;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * 
 * Controller for managing job operations in the Gebo platform. This class
 * provides REST endpoints for creating, monitoring, and aborting jobs. Access
 * is restricted to users with the 'ADMIN' role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/JobLauncherController")
public class JobLauncherController {
	/** Logger for the JobLauncherController class */
	static Logger LOGGER = LoggerFactory.getLogger(JobLauncherController.class);
	/** Service for managing ingestion job queues */
	@Autowired
	IGGeboIngestionJobQueueService jobQueueService;
	/** Manager for persistent objects */
	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	/** Service for scheduling operations */
	@Autowired
	IGSchedulingTimeService schedulingService;
	/** Factory for creating runnable tasks for entity processing */
	@Autowired
	IGEntityProcessingRunnableFactoryRepositoryPattern runnableFactory;

	/**
	 * Default constructor for JobLauncherController
	 */
	public JobLauncherController() {

	}

	/**
	 * Creates a new job for a project endpoint. If the endpoint is not published,
	 * it will be published first. Checks if there's already a running job for the
	 * endpoint before creating a new one.
	 * 
	 * @param endpoint Reference to the project endpoint to create a job for
	 * @return Operation status with the job status or warning message if job is
	 *         already running
	 * @throws GeboJobServiceException  If there's an error in the job service
	 * @throws GeboPersistenceException If there's an error accessing persistent
	 *                                  data
	 */
	@PostMapping(value = "createJob", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJobStatus> createJob(@RequestBody GObjectRef<GProjectEndpoint> endpoint)
			throws GeboJobServiceException, GeboPersistenceException {

		GProjectEndpoint endpointObject = persistentObjectManager.findByReference(endpoint, GProjectEndpoint.class);
		if (endpointObject.getPublished() == null || !endpointObject.getPublished()) {
			endpointObject.setPublished(true);
			persistentObjectManager.update(endpointObject);
			schedulingService.managePublishScheduling(endpointObject);
		}
		if (jobQueueService.isRunningSyncJob(endpointObject)) {
			OperationStatus<GJobStatus> status = new OperationStatus<GJobStatus>();
			status.getMessages().clear();
			status.getMessages()
					.add(GUserMessage.warnMessage("This project item is already under contents reading/vectorization",
							"Let's view the status in the log page or try relaunch publishing later"));
			return status;
		} else
			return OperationStatus.of(jobQueueService.createNewAsyncJob(endpointObject, GWorkflowType.STANDARD.name(),
					GStandardWorkflow.INGESTION.name()));
	}

	/**
	 * Retrieves the status of a job by its job code.
	 * 
	 * @param jobCode The unique identifier of the job
	 * @return The current status of the requested job
	 * @throws GeboJobServiceException If there's an error retrieving the job status
	 */
	@GetMapping(value = "getJobStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public GJobStatus getJobStatus(@RequestParam("jobCode") String jobCode) throws GeboJobServiceException {
		GJobStatus retValue = jobQueueService.getStatus(jobCode);
		return retValue;
	}

	/**
	 * Aborts a running job.
	 * 
	 * @param jobCode The unique identifier of the job to abort
	 * @throws GeboJobServiceException If there's an error aborting the job
	 */
	@GetMapping(value = "abortJob")
	public void abortJob(@RequestParam("jobCode") String jobCode) throws GeboJobServiceException {
		this.jobQueueService.abortAsyncJob(jobCode);
	}

	/**
	 * Inner class representing whether a project endpoint has running jobs.
	 */
	public static class HasRunningJobs {
		/** Reference to the project endpoint */
		public GObjectRef<GProjectEndpoint> endpoint = null;
		/** Flag indicating if there are running jobs for the endpoint */
		public boolean hasRunningJobs = false;
	}

	/**
	 * Checks if a project endpoint has any running jobs.
	 * 
	 * @param endpoint Reference to the project endpoint to check
	 * @return An object containing the endpoint reference and a flag indicating if
	 *         it has running jobs
	 */
	@PostMapping(value = "getHasRunningJobs", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public HasRunningJobs getHasRunningJobs(@RequestBody GObjectRef<GProjectEndpoint> endpoint) {
		HasRunningJobs rj = new HasRunningJobs();
		rj.endpoint = endpoint;
		rj.hasRunningJobs = jobQueueService.isRunningSyncJob(endpoint);
		return rj;
	}

	/**
	 * Retrieves a summary of a job including optional detailed statistics.
	 * 
	 * @param jobId        The unique identifier of the job
	 * @param statsDetails Flag indicating whether to include detailed statistics
	 * @return A summary of the job
	 * @throws GeboJobServiceException  If there's an error retrieving the job
	 *                                  summary
	 * @throws GeboPersistenceException If there's an error accessing persistent
	 *                                  data
	 */
	@GetMapping(value = "getJobSummary", produces = MediaType.APPLICATION_JSON_VALUE)
	public JobSummary getJobSummary(@RequestParam("jobCode") String jobId,
			@RequestParam("statsDetails") Boolean statsDetails)
			throws GeboJobServiceException, GeboPersistenceException {
		return jobQueueService.getJobSummary(jobId, statsDetails != null && statsDetails);
	}
}