/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jobs.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.multithreading.IGRunnable;
import ai.gebo.architecture.multithreading.IGRunnableFactory;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.IGProjectEndpointPublicationProcessRunnableFactory;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * 
 * Implementation of the ProjectEndpointPublicationProcessRunnableFactory interface.
 * This service is responsible for creating runnable factories that process project endpoint publications.
 * It creates runnables that handle the ingestion and publication of project endpoints in the system.
 */
@Service
public class GProjectEndpointPublicationProcessRunnableFactoryImpl
		implements IGProjectEndpointPublicationProcessRunnableFactory {
	/**
	 * Service for managing ingestion job queues, injected by Spring.
	 */
	@Autowired
	IGGeboIngestionJobQueueService ingestionJobQueueService;

	/**
	 * Returns the entity class that this factory handles.
	 * 
	 * @return The GProjectEndpoint class
	 */
	@Override
	public Class<GProjectEndpoint> getEntityClass() {
		return GProjectEndpoint.class;
	}

	/**
	 * Creates a runnable factory for the given project endpoint.
	 * The factory will produce runnables that handle the publication process for the endpoint.
	 * 
	 * @param object The project endpoint to create a factory for
	 * @return A runnable factory that creates publication process runnables
	 */
	@Override
	public IGRunnableFactory createFactory(GProjectEndpoint object) {
		final GObjectRef<GProjectEndpoint> ref = GObjectRef.of(object);
		return new IGRunnableFactory() {

			/**
			 * Creates a runnable that processes the publication of a project endpoint.
			 * Uses the runtime binder to get the ingestion service and creates a publication runnable.
			 * 
			 * @param runtimeBinder The runtime binder to resolve dependencies
			 * @return A runnable that handles the publication process
			 * @throws RuntimeException if there's an error creating the publication runnable
			 */
			@Override
			public IGRunnable create(IGRuntimeBinder runtimeBinder) {
				IGGeboIngestionJobQueueService service = runtimeBinder
						.getImplementationOf(IGGeboIngestionJobQueueService.class);
				try {
					return service.createPublicationRunnable(ref);
				} catch (GeboJobServiceException | GeboPersistenceException e) {
					throw new RuntimeException("Exception creating publication runnable task", e);
				}
			}
		};
	}
}