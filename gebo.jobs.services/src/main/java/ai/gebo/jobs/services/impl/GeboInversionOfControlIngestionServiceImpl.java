/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.jobs.services.impl;

/**
 * This class implements the IGeboInversionOfControlIngestionService interface to manage content consumption
 * for different project endpoints through an inversion of control approach.
 * AI generated comments
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGeboInversionOfControlIngestionService;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.systems.abstraction.layer.IGIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.IGIOCModuleContentsDispatcher.Consumers;
import ai.gebo.systems.abstraction.layer.IGIOCModuleContentsDispatcherRepositoryPattern;

@Service
public class GeboInversionOfControlIngestionServiceImpl implements IGeboInversionOfControlIngestionService {

	/**
	 * Manager responsible for handling ingestion processes
	 */
	@Autowired
	GeboIngestionManager ingestionManager;

	/**
	 * Repository pattern implementation for IOC module contents dispatchers
	 */
	@Autowired
	IGIOCModuleContentsDispatcherRepositoryPattern iocRepositoryPattern;

	/**
	 * Default constructor
	 */
	public GeboInversionOfControlIngestionServiceImpl() {

	}

	/**
	 * Internal class that extends GConsumers to handle specific content consumption
	 * needs
	 */
	static class InternalGConsumers extends GConsumers {

		/**
		 * Method called when consumption process completes
		 */
		@Override
		public void end() {

		}

		/**
		 * Factory method to create an InternalGConsumers instance from a Consumers
		 * object
		 * 
		 * @param c The source Consumers object
		 * @return A new InternalGConsumers instance with properties copied from the
		 *         input
		 */
		static InternalGConsumers of(Consumers c) {
			InternalGConsumers ic = new InternalGConsumers();
			ic.contentConsumer = c.getContentConsumer();
			ic.messagesConsumer = c.getUserMessagesConsumer();
			return ic;
		}
	}

	/**
	 * Retrieves a consumer for the specified project endpoint
	 * 
	 * @param endpoint The project endpoint for which to get a consumer
	 * @return A GConsumers instance for the specified endpoint
	 * @throws GeboJobServiceException If the required handler is not found or
	 *                                 dispatcher throws an exception
	 */
	@Override
	public GConsumers getConsumer(GProjectEndpoint endpoint) throws GeboJobServiceException {
		IGIOCModuleContentsDispatcher handler = iocRepositoryPattern.findByProjectEndpoint(endpoint);
		if (handler == null)
			throw new GeboJobServiceException("Required IGIOCModuleContentsDispatcher not found");
		GJobStatus jobStatus = ingestionManager.internalCreateContentsExtractionAndVectorizationStatus(endpoint,
				GWorkflowType.STANDARD.name(), GStandardWorkflow.INGESTION.name());
		Consumers consumers;
		try {
			consumers = handler.dispatchContentsConsumers(endpoint, jobStatus);
		} catch (GeboContentHandlerSystemException e) {
			throw new GeboJobServiceException("Exception in  handler.dispatchContentsConsumers(...)", e);
		}

		return InternalGConsumers.of(consumers);
	}

}