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
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.core.messages.GAbstractContentMessageFragmentPayload;
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
import ai.gebo.systems.abstraction.layer.config.ContentSystemsLayerConfiguration;
import ai.gebo.systems.abstraction.layer.config.ContentSystemsLayerConfiguration.StreamingStrategy;

/**
 * This class is responsible for dispatching content from a project endpoint to various modules for further processing
 * such as embedding, vectorization, and indexing.
 * 
 * It manages the entire lifecycle of the content dispatching process, ensuring content consumers are properly initialized and used.
 * 
 * @param <SystemIntegrationType> the type of the content management system
 * @param <ProjectEndpointType> the type of the project endpoint
 * 
 * AI generated comments
 */
public class GIOCModuleContentsDispatcher<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint>
        implements IGIOCModuleContentsDispatcher<SystemIntegrationType, ProjectEndpointType> {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    protected IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType> handler = null;
    protected IGMessageBroker broker = null;
    protected IGContentConsumerFactory consumerFactory = null;
    protected IGContentDispatchingEvaluator evaluator = null;
    protected SendEvaluationPolicy evaluationPolicy = SendEvaluationPolicy.TIMESTAMP_HASH_POLICY;
    protected IGDocumentReferenceEnricherMapFactory mapperFactory = null;
    protected DocumentReferenceSnapshotRepository documentsReferenceSnapshotRepository;
    protected ContentSystemsLayerConfiguration configuration;
    protected DocumentReferenceRepository documentReferenceRepository = null;
    protected VirtualFolderRepository virtualFolderRepository = null;

    /**
     * Builder for creating a singleton instance of the dispatcher with the specified dependencies.
     * 
     * @param <SystemIntegrationType> the type of the content management system
     * @param <ProjectEndpointType> the type of the project endpoint
     */
    public static class SingletonBuilder<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint> {
        protected GIOCModuleContentsDispatcher<SystemIntegrationType, ProjectEndpointType> dispatcher = null;

        /**
         * Constructs the builder with the given components required for the dispatcher.
         */
        public SingletonBuilder(
                IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType> handler,
                IGMessageBroker broker, IGContentConsumerFactory consumerFactory,
                IGContentDispatchingEvaluator evaluator, IGDocumentReferenceEnricherMapFactory mapperFactory,
                DocumentReferenceSnapshotRepository documentsReferenceSnapshotRepository,
                ContentSystemsLayerConfiguration configuration, DocumentReferenceRepository documentReferenceRepository,
                VirtualFolderRepository virtualFolderRepository) {
            this.dispatcher = new GIOCModuleContentsDispatcher(handler, broker, consumerFactory, evaluator,
                    mapperFactory, documentsReferenceSnapshotRepository, configuration, documentReferenceRepository,
                    virtualFolderRepository);
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
            ContentSystemsLayerConfiguration configuration, DocumentReferenceRepository documentReferenceRepository,
            VirtualFolderRepository virtualFolderRepository) {
        this.handler = handler;
        this.broker = broker;
        this.consumerFactory = consumerFactory;
        this.evaluator = evaluator;
        this.mapperFactory = mapperFactory;
        this.documentsReferenceSnapshotRepository = documentsReferenceSnapshotRepository;
        this.configuration = configuration;
        this.documentReferenceRepository = documentReferenceRepository;
        this.virtualFolderRepository = virtualFolderRepository;
        if (configuration != null && configuration.getStrategy() != null) {     
           // Log the current streaming strategy being used
            LOGGER.info("Running " + getClass().getName() + " streaming strategy = " + configuration.getStrategy().name());
        }
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
     * Determines whether this dispatcher is set to accept every payload type or not.
     */
    @Override
    public boolean isAcceptEveryPayloadType() {
        return false;
    }

    /**
     * Processes an incoming message envelope. Checks for a specific payload type and evaluates it.
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
     * Dispatches the contents for the given endpoint and job status, initializing required consumers and ensuring the dispatch lifecycle is correctly completed.
     * 
     * @param endpoint the project endpoint from which to dispatch content
     * @param jobStatus the job status associated with the dispatch action
     * @throws GeboContentHandlerSystemException if any content handling system error occurs
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
     * Creates the content consumers required for dispatching, also handling logging, counters, and messaging for the dispatcher system. 
     * Implements strategies based on configuration for processing the content.
     * 
     * @param endpoint the project endpoint
     * @param jobStatus the job status
     * @return the Consumers object containing the document, user message, and error consumers
     * @throws GeboContentHandlerSystemException if any content handling system error occurs
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
        final IGContentConsumer documentSenderConsumerWrapper = new IGContentConsumer() {
            final Map<String, Integer> docsCounters = new HashMap<String, Integer>();
            final Map<String, Object> handlerCache = new HashMap<String, Object>();
            private long howManyBatchDocuments = 0l, howManyBatchSentToVectorization = 0l,
                    howManyBatchContentsReadingErrors = 0l, howManyBatchPersistendDocuments = 0l;

            @Override
            public void accept(GBaseVersionableObject t) {
                enrichers.getContentItemMapper().apply(t);
                if (t != null && t instanceof GAbstractVirtualFilesystemObject) {
                    GAbstractVirtualFilesystemObject jobObject = (GAbstractVirtualFilesystemObject) t;
                    jobObject.setLastesJobId(jobStatus.getCode());
                }
                if (t instanceof GDocumentReference) {
                    GDocumentReference docref = (GDocumentReference) t;

                    if (docref.getCode() != null) {
                        if (!docsCounters.containsKey(docref.getCode())) {
                            docsCounters.put(docref.getCode(), 1);
                        } else {
                            Integer count = docsCounters.get(docref.getCode());
                            count = count.intValue() + 1;
                            docsCounters.put(docref.getCode(), count);
                            LOGGER.warn("Duplicated code:" + docref.getCode() + " count=" + count);
                        }
                    }
                    howManyBatchDocuments++;
                    // skipped vectorization contents are not sent to vectorization or indexing
                    if (docref.getSkippedVectorizationContent() == null || !docref.getSkippedVectorizationContent()) {
                        try {
                            StreamingStrategy strategy = StreamingStrategy.DIRECT_STREAMING;
                            if (configuration != null && configuration.getStrategy() != null) {
                                strategy = configuration.getStrategy();
                            }
                            switch (strategy) {
                            case DIRECT_STREAMING: {
                                final List<Boolean> boolans = new ArrayList<Boolean>();
                                Stream<GAbstractContentMessageFragmentPayload> stream = evaluator
                                        .stream(evaluationPolicy, docref, handler, handlerCache);
                                if (stream != null) {
                                    final GDocumentReferenceSnapshot snapshot = new GDocumentReferenceSnapshot();
                                    snapshot.setCode(docref.getCode());
                                    snapshot.setModifiedDate(docref.getModificationDate());
                                    snapshot.setTokensCount(0);
                                    snapshot.setFileSize(docref.getFileSize());
                                    final List<String> hashes = new ArrayList<String>();
                                    stream.forEach(x -> {
                                        if (indexingServiceOnline) {
                                            GMessageEnvelope<GAbstractContentMessageFragmentPayload> message = GMessageEnvelope
                                                    .newMessageFrom(GIOCModuleContentsDispatcher.this, x);
                                            message.setTargetModule(GStandardModulesConstraints.FULLTEXT_MODULE);
                                            message.setTargetComponent(
                                                    GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT);
                                            message.getPayload().setJobId(jobStatus.getCode());
                                            enrichers.getContentPayloadMapper().apply(message.getPayload());
                                            broker.accept(message);
                                        }
                                        GMessageEnvelope<GAbstractContentMessageFragmentPayload> message = GMessageEnvelope
                                                .newMessageFrom(GIOCModuleContentsDispatcher.this, x);
                                        message.setTargetModule(GStandardModulesConstraints.VECTORIZATOR_MODULE);
                                        message.setTargetComponent(GStandardModulesConstraints.VECTORIZATION_COMPONENT);
                                        message.getPayload().setJobId(jobStatus.getCode());
                                        snapshot.setTokensCount(
                                                snapshot.getTokensCount() + message.getPayload().getEstimatedTokens());
                                        if (message.getPayload().getHash() != null) {
                                            hashes.add(message.getPayload().getHash());
                                        }
                                        enrichers.getContentPayloadMapper().apply(message.getPayload());
                                        broker.accept(message);
                                        boolans.add(true);

                                    });
                                    if (!boolans.isEmpty()) {
                                        String hash = hashes.isEmpty() ? null
                                                : hashes.size() == 0 ? hashes.get(0) : hashes.toString();
                                        snapshot.setHash(hash);
                                        documentsReferenceSnapshotRepository.save(snapshot);
                                    }
                                    userMessagesConsumer.accept(GUserMessage.successMessage(
                                            "Content: " + docref.getName() + " dispatched for embedding",
                                            "The content:" + docref.getCode()
                                                    + " has been read and its content is dispatched for embedding"));
                                }
                                if (!boolans.isEmpty()) {
                                    howManyBatchSentToVectorization++;

                                }

                            }
                                break;
                            case LAZY_STREAMING: {
                                boolean forwardIngestionHandling = evaluator.isProcessable(evaluationPolicy, docref,
                                        handler, handlerCache);
                                if (forwardIngestionHandling) {
                                    final GDocumentReferenceSnapshot snapshot = new GDocumentReferenceSnapshot();
                                    snapshot.setCode(docref.getCode());
                                    snapshot.setModifiedDate(docref.getModificationDate());
                                    snapshot.setTokensCount(0);
                                    snapshot.setFileSize(docref.getFileSize());
                                    GDocumentReferencePayload payload = new GDocumentReferencePayload();
                                    payload.setDocumentReference(docref);
                                    payload.setEndPoint(endpoint);
                                    payload.setFragmentType(MessageFragmentType.SINGLE_FRAGMENT);
                                    payload.setRequiresEmbeddingHandshake(true);
                                    payload.setRequiresEmbeddingHandshake(true);
                                    payload.setJobId(jobStatus.getCode());
                                    GMessageEnvelope<GDocumentReferencePayload> message = GMessageEnvelope
                                            .newMessageFrom(GIOCModuleContentsDispatcher.this, payload);
                                    message.setTargetModule(GStandardModulesConstraints.VECTORIZATOR_MODULE);
                                    message.setTargetComponent(GStandardModulesConstraints.VECTORIZATION_COMPONENT);
                                    message.getPayload().setJobId(jobStatus.getCode());
                                    enrichers.getContentPayloadMapper().apply(message.getPayload());
                                    broker.accept(message);
                                    howManyBatchSentToVectorization++;
                                }
                            }
                                break;
                            }
                        } catch (Throwable throwable) {
                            howManyBatchContentsReadingErrors++;
                            LOGGER.error("Error handling document:" + t.getCode(), throwable);
                            userMessagesConsumer.accept(GUserMessage
                                    .errorMessage("Content: " + docref.getName() + " handling error", throwable));
                        }
                    }
                }
                try {
                    documentConsumer.accept(t);
                    if (t instanceof GDocumentReference) {
                        howManyBatchPersistendDocuments++;
                    }
                } catch (Throwable throwable) {
                    LOGGER.error("Error handling standard consumer for element:" + t.getCode(), throwable);
                }
                if (howManyBatchSentToVectorization > NBATCH_DOCS_STATSMESSAGE) {
                    GContentsProcessingStatusUpdatePayload payload = new GContentsProcessingStatusUpdatePayload();
                    payload.setJobId(jobStatus.getCode());
                    payload.setHowManyBatchDocuments(howManyBatchDocuments);
                    payload.setHowManyBatchContentsReadingErrors(howManyBatchContentsReadingErrors);
                    payload.setHowManyBatchPersistendDocuments(howManyBatchPersistendDocuments);
                    payload.setHowManyBatchSentToVectorization(howManyBatchSentToVectorization);
                    payload.setLastMessage(false);
                    payload.setTimestamp(new Date());
                    GMessageEnvelope<GContentsProcessingStatusUpdatePayload> cmessage = GMessageEnvelope
                            .newMessageFrom(GIOCModuleContentsDispatcher.this, payload);
                    cmessage.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
                    cmessage.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
                    cmessage.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
                    howManyBatchDocuments = 0l;
                    howManyBatchSentToVectorization = 0l;
                    howManyBatchContentsReadingErrors = 0l;
                    howManyBatchPersistendDocuments = 0l;
                    broker.accept(cmessage);
                }
            }

            @Override
            public void endConsuming() {
                documentConsumer.endConsuming();
                if (!errorConsumer.hasErrors()) {
                    try {
                        Stream<GDocumentReference> documentsToCheckStream = documentReferenceRepository
                                .findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndDeletedFalseAndLastesJobIdNot(
                                        endpoint.getClass().getName(), endpoint.getCode(), jobStatus.getCode());
                        Stream<GVirtualFolder> foltersToCheckStream = virtualFolderRepository
                                .findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndDeletedFalseAndLastesJobIdNot(
                                        endpoint.getClass().getName(), endpoint.getCode(), jobStatus.getCode());
                        Stream<GAbstractVirtualFilesystemObject> documents = documentsToCheckStream.map(x -> x);
                        Stream<GAbstractVirtualFilesystemObject> folders = foltersToCheckStream.map(x -> x);
                        Stream<GAbstractVirtualFilesystemObject> updated = handler.checkUpdatedOrDeleted(endpoint,
                                Stream.concat(documents, folders), errorConsumer);
                        final Map<String, Boolean> deletedDocsCodes = new HashMap<String, Boolean>();
                        updated.forEach((x) -> {
                            x.setLastesJobId(jobStatus.getCode());
                            x.setDateModified(new Date());
                            if (x.getDeleted() == null || !x.getDeleted()) {
                                accept(x);
                            } else if (x instanceof GDocumentReference) {
                                GDocumentReference doc = (GDocumentReference) x;
                                deletedDocsCodes.put(doc.getCode(), true);
                                documentReferenceRepository.save(doc);

                            } else {
                                accept(x);
                            }
                        });
                        if (!deletedDocsCodes.isEmpty()) {
                            GInternalDeletionMessagePayload deletedDocumentPayload = new GInternalDeletionMessagePayload();
                            deletedDocumentPayload.setCodes4deletion(new ArrayList<String>(deletedDocsCodes.keySet()));
                            deletedDocumentPayload.setObjectsType(ObjectType.DOCUMENTREF);
                            GMessageEnvelope<GInternalDeletionMessagePayload> envelope = GMessageEnvelope
                                    .newMessageFrom(GIOCModuleContentsDispatcher.this, deletedDocumentPayload);
                            envelope.setTargetModule(GStandardModulesConstraints.VECTORIZATOR_MODULE);
                            envelope.setTargetComponent(GStandardModulesConstraints.VECTORIZATION_DISPOSE_COMPONENT);
                            broker.accept(envelope);
                        }
                    } catch (Throwable th) {
                        LOGGER.error("Error in deletions check", th);
                    }
                }
                if (LOGGER.isDebugEnabled()) {    
                    // Debug logging of received document information for the dispatcher
                    LOGGER.debug("CONTENT-HANDLER-RECEIVED-DOCUMENTS:" + docsCounters.keySet());
                }
                GContentsProcessingStatusUpdatePayload payload = new GContentsProcessingStatusUpdatePayload();
                payload.setJobId(jobStatus.getCode());
                payload.setHowManyBatchDocuments(howManyBatchDocuments);
                payload.setHowManyBatchContentsReadingErrors(howManyBatchContentsReadingErrors);
                payload.setHowManyBatchPersistendDocuments(howManyBatchPersistendDocuments);
                payload.setHowManyBatchSentToVectorization(howManyBatchSentToVectorization);
                payload.setLastMessage(true);
                payload.setTimestamp(new Date());
                GMessageEnvelope<GContentsProcessingStatusUpdatePayload> cmessage = GMessageEnvelope
                        .newMessageFrom(GIOCModuleContentsDispatcher.this, payload);
                cmessage.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
                cmessage.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
                cmessage.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
                broker.accept(cmessage);
            }
        };

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