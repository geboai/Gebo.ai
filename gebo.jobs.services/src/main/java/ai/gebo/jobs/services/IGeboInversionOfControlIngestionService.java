/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jobs.services;

import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;

/**
 * AI generated comments
 * Interface defining a service that follows Inversion of Control pattern for content ingestion.
 * This service provides access to various consumers that handle content, messages, and errors.
 */
public interface IGeboInversionOfControlIngestionService {
    /**
     * Abstract class that encapsulates the different types of consumers
     * used in the content ingestion process.
     */
	public static abstract class GConsumers {
		/** Consumer for handling content */
		protected IGContentConsumer contentConsumer = null;
		/** Consumer for handling user messages */
		protected IGUserMessagesConsumer messagesConsumer = null;
		/** Consumer for handling content access errors */
		protected IGContentsAccessErrorConsumer errorConsumer=null;
		
		/**
         * Returns the content consumer instance.
         * @return The content consumer
         */
		public IGContentConsumer getContentConsumer() {
			return contentConsumer;
		}

		/**
         * Returns the user messages consumer instance.
         * @return The messages consumer
         */
		public IGUserMessagesConsumer getMessagesConsumer() {
			return messagesConsumer;
		}
		
		/**
         * Abstract method to clean up or finalize consumers.
         */
		public abstract void end();

		/**
         * Returns the content access error consumer instance.
         * @return The error consumer
         */
		public IGContentsAccessErrorConsumer getErrorConsumer() {
			return errorConsumer;
		}
	}

	/**
     * Gets the appropriate consumer for a given project endpoint.
     * 
     * @param endpoint The project endpoint for which to get consumers
     * @return A GConsumers instance containing the appropriate consumers
     * @throws GeboJobServiceException If there is an error getting the consumers
     */
	public GConsumers getConsumer(GProjectEndpoint endpoint) throws GeboJobServiceException;
}