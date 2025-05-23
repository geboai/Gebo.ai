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
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.multithreading.IGRunnable;
import ai.gebo.architecture.multithreading.IGUserMonitorableRunnable;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobService;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * 
 * Implementation of the IGGeboIngestionJobService interface.
 * This singleton component manages ingestion jobs for content processing,
 * supporting both synchronous and asynchronous job execution.
 */
@Component()
@Scope("singleton")
public class GGeboIngestionJobServiceImpl implements IGGeboIngestionJobService {
	/** Logger for this class */
	static Logger LOGGER = LoggerFactory.getLogger(GGeboIngestionJobServiceImpl.class);
	
	/** Manager for ingestion processes */
	@Autowired
	GeboIngestionManager ingestionManager;
	
	/** Thread manager for executing asynchronous tasks */
	@Autowired
	IGeboThreadManager taskExecutor;
	
	/** Map to track job status by job code */
	static Map<String, GJobStatus> statusMap = new HashMap<String, GJobStatus>();

	/**
	 * Default constructor
	 */
	public GGeboIngestionJobServiceImpl() {

	}

	/**
	 * Inner class implementing IGUserMonitorableRunnable for content reading jobs.
	 * Provides a runnable implementation for asynchronous content ingestion that can be
	 * monitored by users.
	 */
	public class GGeboContentReadingRunnable implements IGUserMonitorableRunnable {
		/** The status of the job being executed */
		GJobStatus status;
		
		/** Consumer of content being processed */
		IGContentConsumer contentConsumer;
		
		/** Flag indicating if the job is currently running */
		boolean running = false;

		/**
		 * Constructor that initializes a new content reading job
		 * 
		 * @param status The job status object for the content reading task
		 */
		public GGeboContentReadingRunnable(GJobStatus status) {
			this.status = status;

		}

		/**
		 * Executes the content reading job and updates its status
		 */
		@Override
		public void run() {
			try {
				running = true;
				completeSyncJob(status);
			} catch (Throwable th) {
				LOGGER.error("While running", th);
			} finally {
				running = false;
			}

		}

		/**
		 * Initiates the shutdown process for this job
		 */
		@Override
		public void startShutdown() {

		}

		/**
		 * Checks if the job is currently running
		 * 
		 * @return true if the job is running, false otherwise
		 */
		@Override
		public boolean isRunning() {

			return running;
		}

		/**
		 * Shuts down the job immediately
		 */
		@Override
		public void shutdown() {

		}

		/**
		 * Gets the time when this job started
		 * 
		 * @return the start time or null if not started
		 */
		@Override
		public Date getStartedTime() {

			return null;
		}

		/**
		 * Gets the unique identifier for this job
		 * 
		 * @return the job's unique code
		 */
		@Override
		public String getJobID() {

			return status.getCode();
		}

		/**
		 * Gets a human-readable description of this job
		 * 
		 * @return the job description
		 */
		@Override
		public String getDescription() {

			return status.getDescription();
		}
	};

	/**
	 * Executes a job asynchronously by submitting it to the thread manager
	 * 
	 * @param status The job status object to be processed
	 * @throws GeboJobServiceException if there is an error starting the job
	 */
	@Override
	public void completeAsyncJob(GJobStatus status) throws GeboJobServiceException {
		taskExecutor.run(createAsyncJob(status));
	}

	/**
	 * Executes a job synchronously and returns the updated job status
	 * 
	 * @param status The job status object to be processed
	 * @return The updated job status after completion
	 * @throws GeboJobServiceException if there is an error during job execution
	 */
	@Override
	public GJobStatus completeSyncJob(final GJobStatus status) throws GeboJobServiceException {

		return ingestionManager.internalReadAndVectorizeContents(status);
	}

	/**
	 * Checks if a job for the specified endpoint is currently running
	 * 
	 * @param endpoint Reference to the project endpoint to check
	 * @return true if a job is running for the endpoint, false otherwise
	 */
	@Override
	public boolean isJobRunning(GObjectRef<GProjectEndpoint> endpoint) {

		return ingestionManager.isJobRunning(endpoint);
	}

	/**
	 * Creates a new asynchronous job for the given job status
	 * 
	 * @param status The job status to create an async job for
	 * @return An IGRunnable implementation that can be executed asynchronously
	 */
	@Override
	public IGRunnable createAsyncJob(GJobStatus status) {

		return new GGeboContentReadingRunnable(status);
	}

}