/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

import java.util.List;

/**
 * Gebo.ai comment agent
 * Abstract factory class for creating message receivers. Implements common functionalities for message receiver factories.
 */
public abstract class GAbstractMessageReceiverFactory implements IGMessageReceiverFactory {
    
    // Configuration settings for the message receiver factory.
    protected final MessageReceiverFactoryConfig factoryConfig;

    /**
     * Configuration class for setting options related to the Message Receiver Factory.
     */
    public static class MessageReceiverFactoryConfig {
        
        // Determines if the sender's thread should be used. Defaults to null if not set.
        private Boolean useSenderThread = null;
        
        // Specifies the number of threads in the pool. Defaults to null if not set.
        private Integer poolCardinality = null;

        /**
         * Returns whether the sender's thread should be used.
         * @return true if the sender's thread should be used, false otherwise
         */
        public Boolean getUseSenderThread() {
            return useSenderThread;
        }

        /**
         * Sets whether the sender's thread should be used.
         * @param useSenderThread - Boolean value to set
         */
        public void setUseSenderThread(Boolean useSenderThread) {
            this.useSenderThread = useSenderThread;
        }

        /**
         * Returns the number of threads in the pool.
         * @return Integer value representing pool cardinality
         */
        public Integer getPoolCardinality() {
            return poolCardinality;
        }

        /**
         * Sets the pool cardinality.
         * @param poolCardinality - Integer value to set
         */
        public void setPoolCardinality(Integer poolCardinality) {
            this.poolCardinality = poolCardinality;
        }
    }

    /**
     * Constructor for initializing the message receiver factory with a given configuration.
     * @param factoryConfig - Configuration object for the factory
     */
    protected GAbstractMessageReceiverFactory(MessageReceiverFactoryConfig factoryConfig) {
        this.factoryConfig = factoryConfig;
    }

    /**
     * Nested abstract class representing a specific type of message receiver.
     * Inherits configuration settings from the enclosing factory class.
     */
    protected abstract class GNestedMessageReceiver implements IGMessageReceiver {

        @Override
        public List<String> getAcceptedPayloadTypes() {
            return GAbstractMessageReceiverFactory.this.getAcceptedPayloadTypes();
        }

        @Override
        public boolean isAcceptEveryPayloadType() {
            return GAbstractMessageReceiverFactory.this.isAcceptEveryPayloadType();
        }

        @Override
        public String getMessagingModuleId() {
            return GAbstractMessageReceiverFactory.this.getMessagingModuleId();
        }

        @Override
        public String getMessagingSystemId() {
            return GAbstractMessageReceiverFactory.this.getMessagingSystemId();
        }

        @Override
        public SystemComponentType getComponentType() {
            return GAbstractMessageReceiverFactory.this.getComponentType();
        }
    }

    /**
     * Returns the pool cardinality, defaulting to 0 if the configuration is not set.
     * @return the number of threads in the pool
     */
    @Override
    public final int getPoolCardinality() {
        return factoryConfig.poolCardinality != null ? factoryConfig.poolCardinality : 0;
    }

    /**
     * Returns whether the sender's thread should be used, defaulting to false if the configuration is not set.
     * @return true if the sender's thread should be used, false otherwise
     */
    @Override
    public final boolean useSenderThread() {
        return factoryConfig.useSenderThread != null ? factoryConfig.useSenderThread : false;
    }
}