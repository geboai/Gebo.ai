/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.systems.abstraction.layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GInternalDeletionMessagePayload;
import ai.gebo.application.messaging.model.GInternalDeletionMessagePayload.ObjectType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.core.messages.GAbstractContentMessageFragmentPayload.MessageFragmentType;
import ai.gebo.core.messages.GContentEmbeddingHandshakePayload;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentMessageFragmentPayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.core.messages.GRawContentMessageFragmentPayload;
import ai.gebo.core.messages.GUserMessagePayload;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GDocumentReferenceSnapshot;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseVersionableObject;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator.SendEvaluationPolicy;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory.EnricherMappers;

/**
 * This class is responsible for dispatching content from a project endpoint to
 * various modules for further processing such as embedding, vectorization, and
 * indexing.
 * 
 * It manages the entire lifecycle of the content dispatching process, ensuring
 * content consumers are properly initialized and used.
 * 
 * @param <SystemIntegrationType> the type of the content management system
 * @param <ProjectEndpointType>   the type of the project endpoint
 * 
 *                                AI generated comments
 */
public class GIOCModuleContentsDispatcher<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint>
		implements IGIOCModuleContentsDispatcher<SystemIntegrationType, ProjectEndpointType> {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected final IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType> handler;
	protected final IGMessageBroker broker;
	protected final IGContentConsumerFactory consumerFactory;
	protected final IGContentDispatchingEvaluator evaluator;
	protected SendEvaluationPolicy evaluationPolicy = SendEvaluationPolicy.TIMESTAMP_HASH_POLICY;
	protected final IGDocumentReferenceEnricherMapFactory mapperFactory;
	protected final DocumentReferenceSnapshotRepository documentsReferenceSnapshotRepository;
	protected final DocumentReferenceRepository documentReferenceRepository;
	protected final VirtualFolderRepository virtualFolderRepository;
	protected final IWorkflowRouter workflowRouter;

	/**
	 * Builder for creating a singleton instance of the dispatcher with the
	 * specified dependencies.
	 * 
	 * @param <SystemIntegrationType> the type of the content management system
	 * @param <ProjectEndpointType>   the type of the project endpoint
	 */
	public static class SingletonBuilder<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint> {
		protected GIOCModuleContentsDispatcher<SystemIntegrationType, ProjectEndpointType> dispatcher = null;

		/**
		 * Constructs the builder with the given components required for the dispatcher.
		 */
		public SingletonBuilder(IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType> handler,
				IGMessageBroker broker, IGContentConsumerFactory consumerFactory,
				IGContentDispatchingEvaluator evaluator, IGDocumentReferenceEnricherMapFactory mapperFactory,
				DocumentReferenceSnapshotRepository documentsReferenceSnapshotRepository,
				DocumentReferenceRepository documentReferenceRepository,
				VirtualFolderRepository virtualFolderRepository, IWorkflowRouter workflowRouter) {
			this.dispatcher = new GIOCModuleContentsDispatcher(handler, broker, consumerFactory, evaluator,
					mapperFactory, documentsReferenceSnapshotRepository, documentReferenceRepository,
					virtualFolderRepository, workflowRouter);
		}

		/**
		 * Returns the dispatcher instance.
		 * 
		 * @return the dispatcher instance
		 */
		protected GIOCModuleContentsDispatcher<SystemIntegrationType, ProjectEndpointType> getDispatcher() {
			return dispatcher;
		}
	}

	/**
	 * Constructs an instance of the dispatcher with the specified components.
	 */
	public GIOCModuleContentsDispatcher(
			IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType> handler,
			IGMessageBroker broker, IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory,
			DocumentReferenceSnapshotRepository documentsReferenceSnapshotRepository,
			DocumentReferenceRepository documentReferenceRepository, VirtualFolderRepository virtualFolderRepository,
			IWorkflowRouter workflowRouter) {
		this.handler = handler;
		this.broker = broker;
		this.consumerFactory = consumerFactory;
		this.evaluator = evaluator;
		this.mapperFactory = mapperFactory;
		this.documentsReferenceSnapshotRepository = documentsReferenceSnapshotRepository;
		this.workflowRouter = workflowRouter;
		this.documentReferenceRepository = documentReferenceRepository;
		this.virtualFolderRepository = virtualFolderRepository;

	}

	/**
	 * Returns a list of payload type names that this dispatcher can emit.
	 */
	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of(GDocumentMessageFragmentPayload.class.getName(),
				GRawContentMessageFragmentPayload.class.getName(), GUserMessagePayload.class.getName(),
				GContentsProcessingStatusUpdatePayload.class.getName(), GDocumentReferencePayload.class.getName(),
				GInternalDeletionMessagePayload.class.getName());
	}

	/**
	 * Returns the identifier of the messaging module used by this dispatcher.
	 */
	@Override
	public String getMessagingModuleId() {
		return handler.getMessagingModuleId();
	}

	/**
	 * Returns the identifier of the messaging system used by this dispatcher.
	 */
	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.MODULE_IOC_DISPATCHER_COMPONENT;
	}

	/**
	 * Returns the type of system component corresponding to this dispatcher.
	 */
	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * Returns a list of payload type names that this dispatcher can accept.
	 */
	@Override
	public List<String> getAcceptedPayloadTypes() {
		return List.of(GContentEmbeddingHandshakePayload.class.getName());
	}

	/**
	 * Determines whether this dispatcher is set to accept every payload type or
	 * not.
	 */
	@Override
	public boolean isAcceptEveryPayloadType() {
		return false;
	}

	/**
	 * Processes an incoming message envelope. Checks for a specific payload type
	 * and evaluates it.
	 * 
	 * @param t the message envelope being accepted
	 */
	@Override
	public void accept(GMessageEnvelope t) {
		if (t.getPayload() instanceof GContentEmbeddingHandshakePayload) {
			evaluator.evaluateContentHandshake((GContentEmbeddingHandshakePayload) t.getPayload());
		}
	}

	/**
	 * Dispatches the contents for the given endpoint and job status, initializing
	 * required consumers and ensuring the dispatch lifecycle is correctly
	 * completed.
	 * 
	 * @param endpoint  the project endpoint from which to dispatch content
	 * @param jobStatus the job status associated with the dispatch action
	 * @throws GeboContentHandlerSystemException if any content handling system
	 *                                           error occurs
	 */
	@Override
	public void dispatchContents(ProjectEndpointType endpoint, GJobStatus jobStatus)
			throws GeboContentHandlerSystemException {
		Consumers consumers = dispatchContentsConsumers(endpoint, jobStatus);
		try {
			this.handler.consume(endpoint, consumers.getContentConsumer(), consumers.getUserMessagesConsumer(),
					consumers.getErrorConsumer());
		} finally {
			consumers.getContentConsumer().endConsuming();
			consumers.getUserMessagesConsumer().endConsuming();
		}
	}

	static private final long NBATCH_DOCS_STATSMESSAGE = 50;

	/**
	 * Creates the content consumers required for dispatching, also handling
	 * logging, counters, and messaging for the dispatcher system. Implements
	 * strategies based on configuration for processing the content.
	 * 
	 * @param endpoint  the project endpoint
	 * @param jobStatus the job status
	 * @return the Consumers object containing the document, user message, and error
	 *         consumers
	 * @throws GeboContentHandlerSystemException if any content handling system
	 *                                           error occurs
	 */
	@Override
	public Consumers dispatchContentsConsumers(final ProjectEndpointType endpoint, final GJobStatus jobStatus)
			throws GeboContentHandlerSystemException {
		final IGContentsAccessErrorConsumer errorConsumer = IGContentsAccessErrorConsumer.defaultImplementation();
		final boolean indexingServiceOnline = broker.checkReceivingComponentPresent(
				GStandardModulesConstraints.FULLTEXT_MODULE, GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT);
		final IGUserMessagesConsumer userMessagesConsumer = new IGUserMessagesConsumer() {

			@Override
			public void accept(GUserMessage t) {
				t.setJobId(jobStatus.getCode());
				GMessageEnvelope<GUserMessagePayload> message = GMessageEnvelope
						.newMessageFrom(GIOCModuleContentsDispatcher.this, new GUserMessagePayload());
				message.getPayload().setUserMessage(t);
				message.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
				message.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
				broker.accept(message);
			}

			@Override
			public void endConsuming() {

			}
		};

		final EnricherMappers enrichers = mapperFactory.mappers(endpoint);
		final IGContentConsumer documentConsumer = consumerFactory.create(endpoint);
			
		final IGContentConsumer documentSenderConsumerWrapper = new GIOCContentConsumer<SystemIntegrationType, ProjectEndpointType>(
				enrichers, jobStatus, evaluator, handler, endpoint, workflowRouter, this, userMessagesConsumer,
				errorConsumer, documentConsumer, broker, documentReferenceRepository, virtualFolderRepository);

		return new Consumers(documentSenderConsumerWrapper, userMessagesConsumer, errorConsumer);
	}

	/**
	 * Determines if the given endpoint is managed by this handler.
	 * 
	 * @param endpoint the project endpoint to check
	 * @return true if the handler manages the endpoint, false otherwise
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return handler.isManagedEndpoint(endpoint);
	}

}