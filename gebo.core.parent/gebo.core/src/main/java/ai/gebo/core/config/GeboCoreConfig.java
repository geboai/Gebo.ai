/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory.TimedOutMessageReceiverFactoryConfig;

/**
 * AI generated comments
 * Configuration class for Gebo Core settings. This class maps properties
 * from 'ai.gebo.core.config' and provides configurations for message
 * receiver factories.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.core.config")
public class GeboCoreConfig {

    /** Configuration for the user messages receiver. */
    private TimedOutMessageReceiverFactoryConfig userMessagesReceiverConfig = new TimedOutMessageReceiverFactoryConfig();

    /** Configuration for the Mongo disposer. */
    private TimedOutMessageReceiverFactoryConfig mongoDisposerConfig = new TimedOutMessageReceiverFactoryConfig();

    /**
     * Default constructor initializing the configurations with default values.
     * Sets up specific configurations for user message receiver and Mongo disposer.
     */
    public GeboCoreConfig() {
        // Set configurations for userMessagesReceiver
        userMessagesReceiverConfig.setUseSenderThread(true); // Enable sender thread usage
        userMessagesReceiverConfig.setPoolCardinality(0); // Set the pool cardinality 
        userMessagesReceiverConfig.setTimeout(10000l); // Set timeout to 10 seconds

        // Set configurations for mongoDisposer
        mongoDisposerConfig.setUseSenderThread(false); // Disable sender thread usage
        mongoDisposerConfig.setPoolCardinality(1); // Set pool cardinality to 1
        mongoDisposerConfig.setTimeout(10000l); // Set timeout to 10 seconds
    }

    /**
     * Retrieves the configuration for user messages receiver.
     * 
     * @return The configuration for user messages receiver.
     */
    public TimedOutMessageReceiverFactoryConfig getUserMessagesReceiverConfig() {
        return userMessagesReceiverConfig;
    }

    /**
     * Sets the configuration for the user messages receiver.
     * 
     * @param receiverConfig The configuration for user messages receiver to set.
     */
    public void setUserMessagesReceiverConfig(TimedOutMessageReceiverFactoryConfig receiverConfig) {
        this.userMessagesReceiverConfig = receiverConfig;
    }

    /**
     * Retrieves the configuration for Mongo disposer.
     * 
     * @return The configuration for Mongo disposer.
     */
    public TimedOutMessageReceiverFactoryConfig getMongoDisposerConfig() {
        return mongoDisposerConfig;
    }

    /**
     * Sets the configuration for the Mongo disposer.
     * 
     * @param mongoDisposerConfig The configuration for Mongo disposer to set.
     */
    public void setMongoDisposerConfig(TimedOutMessageReceiverFactoryConfig mongoDisposerConfig) {
        this.mongoDisposerConfig = mongoDisposerConfig;
    }
}