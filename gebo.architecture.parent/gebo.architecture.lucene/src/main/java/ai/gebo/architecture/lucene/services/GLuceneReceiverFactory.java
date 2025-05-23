/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.lucene.config.GeboLuceneConfig;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GDocumentMessageFragmentPayload;

/**
 * AI generated comments
 * Factory class responsible for creating message receivers for Lucene indexing tasks.
 * This factory is conditionally enabled based on the property "ai.gebo.lucene.config" being set to "true".
 * It operates as a singleton component to manage the consistent creation of receiver instances.
 */
@Component
@Scope("singleton")
@ConditionalOnProperty(prefix = "ai.gebo.lucene.config", name = "enabled", havingValue = "true")
public class GLuceneReceiverFactory extends GAbstractTimedOutMessageReceiverFactory {

    /**
     * Constructor to initialize GLuceneReceiverFactory with the Lucene indexer config.
     * @param config Configuration object for Lucene settings.
     */
    protected GLuceneReceiverFactory(GeboLuceneConfig config) {
        super(config.getLuceneIndexerContentsReceiver());
    }

    @Autowired
    IGRuntimeBinder binder;

    /**
     * A nested class within GLuceneReceiverFactory which extends GNestedBatchAggregatorMessageReceiver.
     * Responsible for aggregating messages in batches before processing.
     */
    class NestedBatchingReceiver extends GNestedBatchAggregatorMessageReceiver {

        /**
         * Constructor for creating a NestedBatchingReceiver with specified nesting receiver and flush threshold.
         * @param nested The nested IGBatchMessagesReceiver implementation.
         * @param flushThreshold Threshold for determining when to flush the batch.
         */
        public NestedBatchingReceiver(IGBatchMessagesReceiver nested, int flushThreshold) {
            super(nested, flushThreshold);
        }
    }

    /**
     * Creates a new instance of a timed-out message receiver using the provided LuceneService implementation.
     * @return The created IGTimedOutMessageReceiver instance.
     */
    @Override
    public IGTimedOutMessageReceiver create() {

        return new NestedBatchingReceiver(binder.getImplementationOf(LuceneService.class),
                factoryConfig.getFlushThreshold() != null ? factoryConfig.getFlushThreshold() : 10);
    }

    /**
     * Retrieves a list of accepted payload types for this receiver.
     * @return List of accepted payload type class names.
     */
    @Override
    public List<String> getAcceptedPayloadTypes() {

        return List.of(GDocumentMessageFragmentPayload.class.getName());
    }

    /**
     * Indicates whether every payload type is accepted by this receiver.
     * @return false, as not every payload type is accepted.
     */
    @Override
    public boolean isAcceptEveryPayloadType() {

        return false;
    }

    /**
     * Returns the messaging module ID associated with this factory.
     * @return The messaging module ID.
     */
    @Override
    public String getMessagingModuleId() {

        return GStandardModulesConstraints.FULLTEXT_MODULE;
    }

    /**
     * Provides the messaging system ID for the indexing component.
     * @return The messaging system ID for full-text indexing.
     */
    @Override
    public String getMessagingSystemId() {
        return GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT;
    }

    /**
     * Retrieves the component type this factory represents.
     * @return The SystemComponentType of this factory.
     */
    @Override
    public SystemComponentType getComponentType() {

        return SystemComponentType.APPLICATION_COMPONENT;
    }

}