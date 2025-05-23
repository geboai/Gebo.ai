/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.impl;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractMessageReceiverFactory;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GInternalDeletionMessagePayload;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.core.config.GeboCoreConfig;
import ai.gebo.core.messages.GDeletedKnowledgeBasePayload;
import ai.gebo.core.messages.GDeletedProjectEndpointPayload;
import ai.gebo.core.messages.GDeletedProjectPayload;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;

/**
 * Factory for creating instances of {@link DisposeMongoContentsMessageReceiver}.
 * Handles the setup for message receivers that will process disposal of data 
 * based on certain message payloads.
 * AI generated comments
 */
@Component
@Scope("singleton")
public class GDisposeMongoContentsMessageReceiverFactoryImpl extends GAbstractMessageReceiverFactory {

    @Autowired
    BeanFactory beanFactory;

    /**
     * Constructor that initializes the factory with configurations.
     * 
     * @param config Configuration settings for Mongo disposer.
     */
    protected GDisposeMongoContentsMessageReceiverFactoryImpl(GeboCoreConfig config) {
        super(config.getMongoDisposerConfig());
    }

    /**
     * Message receiver implementation that processes various payload types for
     * disposing data from Mongo by deleting it from repositories.
     */
    public class DisposeMongoContentsMessageReceiver implements IGMessageReceiver {
        VirtualFolderRepository vfolderRepository;
        DocumentReferenceRepository docRepository;

        /**
         * Constructs a message receiver with the specified repositories.
         * 
         * @param vfolderRepository Repository to handle virtual folder operations.
         * @param docRepository Repository to handle document reference operations.
         */
        DisposeMongoContentsMessageReceiver(VirtualFolderRepository vfolderRepository,
                DocumentReferenceRepository docRepository) {
            this.vfolderRepository = vfolderRepository;
            this.docRepository = docRepository;
        }

        /**
         * Gets the list of accepted payload types for this receiver.
         * 
         * @return List of accepted payload type names.
         */
        @Override
        public List<String> getAcceptedPayloadTypes() {
            return GDisposeMongoContentsMessageReceiverFactoryImpl.this.getAcceptedPayloadTypes();
        }

        /**
         * Indicates whether every payload type is accepted by this receiver.
         * 
         * @return {@code false}, as only specific types are processed.
         */
        @Override
        public boolean isAcceptEveryPayloadType() {
            return false;
        }

        /**
         * Gets the module identifier this receiver belongs to.
         * 
         * @return Messaging module identifier.
         */
        @Override
        public String getMessagingModuleId() {
            return GDisposeMongoContentsMessageReceiverFactoryImpl.this.getMessagingModuleId();
        }

        /**
         * Gets the system identifier this receiver is part of.
         * 
         * @return Messaging system identifier.
         */
        @Override
        public String getMessagingSystemId() {
            return GDisposeMongoContentsMessageReceiverFactoryImpl.this.getMessagingSystemId();
        }

        /**
         * Provides the component type of this receiver.
         * 
         * @return The component type of the receiver, indicating its role in the system.
         */
        @Override
        public SystemComponentType getComponentType() {
            return GDisposeMongoContentsMessageReceiverFactoryImpl.this.getComponentType();
        }

        /**
         * Processes the received message by deleting entries from repositories
         * based on the type of payload provided in the message.
         * 
         * @param msg The message envelope containing the payload.
         */
        @Override
        public void accept(GMessageEnvelope msg) {
            // Handle specific payload types and delete corresponding data
            if (msg.getPayload() instanceof GDeletedProjectEndpointPayload) {
                GDeletedProjectEndpointPayload payload = (GDeletedProjectEndpointPayload) msg.getPayload();
                vfolderRepository.deleteByProjectEndpoint(payload.getEndpoint());
                docRepository.deleteByProjectEndpoint(payload.getEndpoint());
            } else if (msg.getPayload() instanceof GInternalDeletionMessagePayload) {
                GInternalDeletionMessagePayload payload = (GInternalDeletionMessagePayload) msg.getPayload();
                switch (payload.getObjectsType()) {
                case DOCUMENTREF: {
                    docRepository.deleteAllById(payload.getCodes4deletion());
                }
                    break;
                case VIRTUALFOLDER: {
                    vfolderRepository.deleteAllById(payload.getCodes4deletion());
                }
                    break;
                }
            } else if (msg.getPayload() instanceof GDeletedProjectPayload) {
                GDeletedProjectPayload payload = (GDeletedProjectPayload) msg.getPayload();
                vfolderRepository.deleteByParentProjectCode(payload.getProject().getCode());
                docRepository.deleteByParentProjectCode(payload.getProject().getCode());
            } else if (msg.getPayload() instanceof GDeletedKnowledgeBasePayload) {
                GDeletedKnowledgeBasePayload payload = (GDeletedKnowledgeBasePayload) msg.getPayload();
                vfolderRepository.deleteByRootKnowledgebaseCode(payload.getKnowledgeBase().getCode());
                docRepository.deleteByRootKnowledgebaseCode(payload.getKnowledgeBase().getCode());
            }
        }
    }

    /**
     * Retrieves the module identifier for the messaging context.
     * 
     * @return The core module identifier.
     */
    @Override
    public String getMessagingModuleId() {
        return GStandardModulesConstraints.CORE_MODULE;
    }

    /**
     * Retrieves the system identifier for where this factory's message
     * receiver operates.
     * 
     * @return The system identifier for Mongo document disposal.
     */
    @Override
    public String getMessagingSystemId() {
        return GStandardModulesConstraints.MONGO_DISPOSE_DOCUMENTS_COMPONENT;
    }

    /**
     * Returns the type of system component.
     * 
     * @return The type representing an application component.
     */
    @Override
    public SystemComponentType getComponentType() {
        return SystemComponentType.APPLICATION_COMPONENT;
    }

    /**
     * Lists the accepted payload types that this factory's receiver can process.
     * 
     * @return A list of fully qualified names of payload classes.
     */
    @Override
    public List<String> getAcceptedPayloadTypes() {
        return List.of(GDeletedProjectEndpointPayload.class.getName(), GInternalDeletionMessagePayload.class.getName(),
                GDeletedProjectPayload.class.getName(), GDeletedKnowledgeBasePayload.class.getName());
    }

    /**
     * Determines if the receiver accepts all types of payloads.
     * 
     * @return Always {@code false}, as only specific payload types are accepted.
     */
    @Override
    public boolean isAcceptEveryPayloadType() {
        return false;
    }

    /**
     * Creates a new instance of {@link DisposeMongoContentsMessageReceiver} using beans from the
     * provided {@link BeanFactory}.
     * 
     * @return A new instance of the message receiver.
     */
    @Override
    public IGMessageReceiver create() {
        DisposeMongoContentsMessageReceiver receiver = new DisposeMongoContentsMessageReceiver(
                beanFactory.getBean(VirtualFolderRepository.class),
                beanFactory.getBean(DocumentReferenceRepository.class));
        return receiver;
    }

}