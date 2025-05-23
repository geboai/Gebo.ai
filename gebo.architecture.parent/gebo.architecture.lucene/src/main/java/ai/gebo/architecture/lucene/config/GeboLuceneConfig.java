/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory.TimedOutMessageReceiverFactoryConfig;
import ai.gebo.architecture.lucene.model.InternalLuceneServerConfig;

/**
 * Gebo.ai comment agent
 * 
 * Configuration class for Gebo Lucene.
 * This class holds the configuration properties related to Lucene indexing 
 * which are typically defined in application properties with the prefix "ai.gebo.lucene.config".
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.lucene.config")
public class GeboLuceneConfig {

    // Indicates if Lucene indexing is enabled
    Boolean enabled = false;

    // Configuration for Lucene indexer message receiver
    private TimedOutMessageReceiverFactoryConfig luceneIndexerContentsReceiver = new TimedOutMessageReceiverFactoryConfig();

    // Configuration for internal Lucene server
    private InternalLuceneServerConfig indexerConfig=new InternalLuceneServerConfig();

    /**
     * Constructor to initialize default values for the Lucene indexer contents receiver.
     */
    public GeboLuceneConfig() {
        // Setting the timeout for message receiver
        luceneIndexerContentsReceiver.setTimeout(30000l);
        // Using the sender thread for processing the messages
        luceneIndexerContentsReceiver.setUseSenderThread(true);
        // Setting the pool cardinality to 1
        luceneIndexerContentsReceiver.setPoolCardinality(1);
        // Setting the flush threshold for the receiver
        luceneIndexerContentsReceiver.setFlushThreshold(10);
    }

    /**
     * Gets the status of the enabled flag.
     * @return Boolean indicating whether Lucene indexing is enabled.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets the status of the enabled flag.
     * @param enabled Boolean to enable or disable Lucene indexing.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the configuration for Lucene indexer message receiver.
     * @return the configuration for timed out message receiver.
     */
    public TimedOutMessageReceiverFactoryConfig getLuceneIndexerContentsReceiver() {
        return luceneIndexerContentsReceiver;
    }

    /**
     * Sets the configuration for Lucene indexer message receiver.
     * @param luceneIndexerContentsReceiver the configuration to set.
     */
    public void setLuceneIndexerContentsReceiver(TimedOutMessageReceiverFactoryConfig luceneIndexerContentsReceiver) {
        this.luceneIndexerContentsReceiver = luceneIndexerContentsReceiver;
    }

    /**
     * Gets the configuration for the internal Lucene server.
     * @return the internal Lucene server configuration.
     */
    public InternalLuceneServerConfig getIndexerConfig() {
        return indexerConfig;
    }

    /**
     * Sets the configuration for the internal Lucene server.
     * @param indexerConfig the internal Lucene server configuration to set.
     */
    public void setIndexerConfig(InternalLuceneServerConfig indexerConfig) {
        this.indexerConfig = indexerConfig;
    }

}