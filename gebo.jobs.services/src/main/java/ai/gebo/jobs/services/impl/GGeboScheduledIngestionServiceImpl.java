/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jobs.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.multithreading.IGRunnable;
import ai.gebo.architecture.multithreading.IGRunnableFactory;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.config.GeboConfig;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.IGGeboScheduledSyncronizationService;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.knowledgebase.repositories.UserMessageRepository;
import ai.gebo.model.annotations.SchedulableObject;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * 
 * Implementation of the IGGeboScheduledSyncronizationService interface that handles
 * scheduled ingestion tasks. This singleton component manages scheduling of content reading
 * and vectorization for project endpoints, and maintains job statuses in the system.
 */
@Component
@Scope("singleton")
public class GGeboScheduledIngestionServiceImpl
		implements IGGeboScheduledSyncronizationService, ApplicationListener<ContextRefreshedEvent> {
	static Logger LOGGER = LoggerFactory.getLogger(GGeboScheduledIngestionServiceImpl.class);
	@Autowired
	GeboConfig geboConfig;
	private boolean firstRun = true;
	@Autowired
	IGPersistentObjectManager persistenceManager;
	@Autowired
	IGGeboIngestionJobQueueService jobService;
	@Autowired
	JobStatusRepository jobStatusRepository;
	@Autowired
	UserMessageRepository userMessageRepository;
	@Autowired
	IGSchedulingTimeService publicationSchedulingService;

	/**
	 * Factory class for creating runnable tasks for endpoint processing.
	 * Implements IGRunnableFactory interface to provide custom runnable instances.
	 */
	class RunnableFactory implements IGRunnableFactory {
		final GObjectRef<GProjectEndpoint> endpont;

		/**
		 * Constructor for the RunnableFactory.
		 * 
		 * @param endpoint The project endpoint for which to create runnables
		 */
		public RunnableFactory(GProjectEndpoint endpoint) {
			this.endpont = GObjectRef.of(endpoint);
		}

		/**
		 * Creates a new runnable instance bound to the runtime environment.
		 * 
		 * @param runtimeBinder The runtime binder providing execution context
		 * @return A runnable instance or null if creation failed
		 */
		@Override
		public IGRunnable create(IGRuntimeBinder runtimeBinder) {
			return null;
		}
	}

	/**
	 * Default constructor for the service.
	 */
	public GGeboScheduledIngestionServiceImpl() {
	}

	/**
	 * Schedules updates for all project endpoints.
	 * On first run, it clears any pending jobs from previous executions.
	 * Then processes all project endpoints and manages their publication scheduling.
	 */
	@Override
	public void scheduleUpdates() {
		if (this.firstRun) {
			this.clearOpened();
			this.firstRun = false;
		}

		LOGGER.info("Begin scheduling contents reading and vectorization");
		try {
			List<GProjectEndpoint> endpoints = persistenceManager.findAllExtendingType(GProjectEndpoint.class);
			for (GProjectEndpoint gProjectEndpoint : endpoints) {
				final GObjectRef<GProjectEndpoint> ref = GObjectRef.of(gProjectEndpoint);
				publicationSchedulingService.managePublishScheduling(gProjectEndpoint);
			}
		} catch (Throwable e) {
			LOGGER.error("Main cycle of runContentsReadingAndVectorizing() fails ", e);
		}
		LOGGER.info("End scheduling contents reading and vectorization");
	}

	/**
	 * Cleans up job status logs older than 3 days to prevent database bloat.
	 * Removes both job status entries and associated user messages.
	 */
	private void clearLast3DaysOfLog() {
		LOGGER.info("Begin clearing logs before last 3 days");
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.add(GregorianCalendar.DAY_OF_YEAR, -3);
			Date date = gregorianCalendar.getTime();
			Stream<GJobStatus> stream = jobStatusRepository.findBy(Example.of(new GJobStatus()));
			final List<String> ids = new ArrayList<String>();
			stream.forEach(x -> {
				if (x.getStartDateTime() != null && x.getStartDateTime().before(date)) {
					ids.add(x.getCode());
				}
			});
			jobStatusRepository.deleteAllById(ids);
			userMessageRepository.deleteByJobIdIn(ids);
		} catch (Throwable th) {
			// Silent failure handling
		}
		LOGGER.info("End clearing logs before last 3 days");
	}

	/**
	 * Marks all jobs that were previously in a "processing" state as no longer processing.
	 * This is executed only in non-clustered mode to prevent orphaned job statuses.
	 */
	private void clearOpened() {
		try {
			if (geboConfig != null && geboConfig.getClustered() != null && !geboConfig.getClustered()) {
				Stream<GJobStatusItem> stream = jobStatusRepository.findByProcessingTrue();
				final List<String> ids = new ArrayList<String>();
				stream.forEach(x -> {
					ids.add(x.getCode());
				});
				if (ids.isEmpty())
					return;
				List<GJobStatus> entries = jobStatusRepository.findAllById(ids);
				for (GJobStatus gJobStatus : entries) {
					gJobStatus.setProcessing(false);
				}
				jobStatusRepository.saveAll(entries);
			}
		} catch (Throwable th) {
			LOGGER.error("Error in doFirstRun()", th);
		}
	}

	/**
	 * Handles the Spring context refreshed event by scheduling updates.
	 * This ensures that job scheduling begins when the application context is fully initialized.
	 * 
	 * @param event The context refreshed event
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.scheduleUpdates();
	}
}