/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * Interface for dispatching module contents within a system integration.
 * This interface extends functionality for message emission and reception.
 * 
 * @param <SystemIntegrationType> Type of the content management system integration.
 * @param <ProjectEndpointType> Type of the project endpoint.
 * 
 * AI generated comments
 */
public interface IGIOCModuleContentsDispatcher<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint>
		extends IGMessageEmitter, IGMessageReceiver {
	
    /**
     * Static nested class representing different types of consumers for content, user messages, and error handling.
     */
	public static final class Consumers {
		private final IGContentConsumer contentConsumer;
		private final IGUserMessagesConsumer userMessagesConsumer;
		private final IGContentsAccessErrorConsumer errorConsumer;

        /**
         * Constructor for creating a Consumers object with specified content, user messages, and error consumers.
         *
         * @param contentConsumer the content consumer
         * @param userMessageConsumer the user messages consumer
         * @param errorConsumer the error consumer for handling access errors
         */
		public Consumers(IGContentConsumer contentConsumer, IGUserMessagesConsumer userMessageConsumer,
				IGContentsAccessErrorConsumer errorConsumer) {
			this.contentConsumer = contentConsumer;
			this.userMessagesConsumer = userMessageConsumer;
			this.errorConsumer = errorConsumer;
		}

        /**
         * Gets the content consumer.
         * 
         * @return the content consumer
         */
		public IGContentConsumer getContentConsumer() {
			return contentConsumer;
		}

        /**
         * Gets the user messages consumer.
         *
         * @return the user messages consumer
         */
		public IGUserMessagesConsumer getUserMessagesConsumer() {
			return userMessagesConsumer;
		}

        /**
         * Gets the error consumer for access errors.
         *
         * @return the error consumer
         */
		public IGContentsAccessErrorConsumer getErrorConsumer() {
			return errorConsumer;
		}
	}

    /**
     * Dispatches contents to a given project endpoint based on the provided job status.
     *
     * @param endpoint the project endpoint where contents are dispatched
     * @param jobStatus the job status associated with the dispatch operation
     * @throws GeboContentHandlerSystemException if an error occurs during content handling
     */
	public void dispatchContents(ProjectEndpointType endpoint, GJobStatus jobStatus)
			throws GeboContentHandlerSystemException;

    /**
     * Retrieves the consumers for content dispatch to a specified project endpoint.
     * 
     * @param endpoint the project endpoint 
     * @param jobStatus the job status associated with the consumers
     * @return a Consumers object containing content, user messages, and error consumers
     * @throws GeboContentHandlerSystemException if an error occurs while obtaining consumers
     */
	public Consumers dispatchContentsConsumers(ProjectEndpointType endpoint, GJobStatus jobStatus)
			throws GeboContentHandlerSystemException;

    /**
     * Checks whether a given project endpoint is managed by this dispatcher.
     *
     * @param endpoint the project endpoint to check
     * @return true if the endpoint is managed, false otherwise
     */
	public boolean isManagedEndpoint(GProjectEndpoint endpoint);

}