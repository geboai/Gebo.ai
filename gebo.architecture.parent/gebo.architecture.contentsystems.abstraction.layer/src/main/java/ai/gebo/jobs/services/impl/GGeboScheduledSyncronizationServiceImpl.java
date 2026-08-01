/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.jobs.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.messages.GRescheduleProjectEndpointMessagePayload;
import ai.gebo.jobs.services.IGGeboScheduledSyncronizationService;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * Implementation of the IGGeboScheduledSyncronizationService interface that
 * handles scheduled ingestion tasks. This singleton component manages
 * scheduling of content reading and vectorization for project endpoints, and
 * maintains job statuses in the system.
 */
@Component
@Scope("singleton")
@AllArgsConstructor
public class GGeboScheduledSyncronizationServiceImpl
		implements IGGeboScheduledSyncronizationService, ApplicationListener<ContextRefreshedEvent> {
	static Logger LOGGER = LoggerFactory.getLogger(GGeboScheduledSyncronizationServiceImpl.class);
	private final IGPersistentObjectManager persistenceManager;
	private final IGMessageBroker messageBroker;
	private final IMessageEnvelopeFactory envelopeFactory;
	// See GAbstractSystemsArchitectureController.jobLaunchManager for why this
	// bean (rather than a dedicated emitter) is used as the reschedule request's
	// "from" identity.
	private final AbstractJobLaunchManager jobLaunchManager;

	
	

	/**
	 * Schedules updates for all project endpoints. On first run, it clears any
	 * pending jobs from previous executions. Then processes all project endpoints
	 * and manages their publication scheduling.
	 */
	@Override
	public void scheduleUpdates() {
		

		LOGGER.info("Begin scheduling contents reading and vectorization");
		try {
			List<GProjectEndpoint> endpoints = persistenceManager.findAllExtendingType(GProjectEndpoint.class);
			for (GProjectEndpoint gProjectEndpoint : endpoints) {
				GRescheduleProjectEndpointMessagePayload payload = new GRescheduleProjectEndpointMessagePayload();
				payload.setCentralizedProjectEndpoint(GCentralizedProjectEndpoint.of(gProjectEndpoint));
				GMessageEnvelope<GRescheduleProjectEndpointMessagePayload> envelope = envelopeFactory
						.newMessageFrom(jobLaunchManager, payload);
				envelope.setTargetModule(GStandardModulesConstraints.SCHEDULER_MODULE);
				envelope.setTargetComponent(GStandardModulesConstraints.SCHEDULER_COMPONENT);
				messageBroker.accept(envelope);
			}
		} catch (Throwable e) {
			LOGGER.error("Main cycle of runContentsReadingAndVectorizing() fails ", e);
		}
		LOGGER.info("End scheduling contents reading and vectorization");
	}

	
	/**
	 * Handles the Spring context refreshed event by scheduling updates. This
	 * ensures that job scheduling begins when the application context is fully
	 * initialized.
	 * 
	 * @param event The context refreshed event
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.scheduleUpdates();
	}

}