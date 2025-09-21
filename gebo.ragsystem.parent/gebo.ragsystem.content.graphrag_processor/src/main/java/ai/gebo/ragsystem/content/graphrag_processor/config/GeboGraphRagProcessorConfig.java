/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.ragsystem.content.graphrag_processor.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory.TimedOutMessageReceiverFactoryConfig;
import lombok.Data;

/**
 * AI generated comments
 * 
 * Configuration class for the Gebo document cache and chunking system. This
 * class defines properties that control the behavior of the vectorization
 * process, including message size limits and receiver configurations.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.graphrag.processor")
@Data
public class GeboGraphRagProcessorConfig {
	@Data
	public static class GraphRagCustomReceiverConfig extends TimedOutMessageReceiverFactoryConfig {
		long minimumDelayBetweenRequests = -1;
		int concurrentGraphExtractionWorkers = 4;
	}

	/**
	 * Maximum cumulative size of messages in bytes before processing (default: 1MB)
	 */
	long maximumMessagesCumulatedBytesThreshold = 1024 * 1024;
	private List<String> discardedExtensions = new ArrayList<String>(List.of(".xls", ".xlsx", ".ods"));

	/**
	 * Configuration for the vectorization message receiver with timeout
	 * capabilities
	 */
	GraphRagCustomReceiverConfig graphRagProcessorReceiverConfig = new GraphRagCustomReceiverConfig();

	/**
	 * Default constructor that initializes the configuration objects with default
	 * values. Sets up pool cardinality, threading behavior, timeout values, and
	 * flush thresholds.
	 */
	public GeboGraphRagProcessorConfig() {

		graphRagProcessorReceiverConfig.setPoolCardinality(1);
		graphRagProcessorReceiverConfig.setUseSenderThread(true);
		graphRagProcessorReceiverConfig.setTimeout(5000l);
		graphRagProcessorReceiverConfig.setFlushThreshold(10);
	}

}