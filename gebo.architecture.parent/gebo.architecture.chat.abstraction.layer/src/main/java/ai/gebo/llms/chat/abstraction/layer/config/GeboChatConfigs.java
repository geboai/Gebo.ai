/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Gebo.ai comment agent
 * 
 * Configuration class for GeboRag settings. This class is used to
 * configure ranges, top K elements, and similarity threshold for
 * the AI chat system. It uses Spring's @Configuration and
 * ConfigurationProperties annotations to map the properties.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.chat")
@Data
public class GeboChatConfigs {
    
    // List of context window length range settings for different strategies
    private List<ContextWindowLengthRangeSettings> ranges = new ArrayList<ContextWindowLengthRangeSettings>();
    
    // Default number of top elements to be considered
    private int defaultTopK = 10;
    
    // Default similarity threshold for comparisons
    private double defaultSimilarityThreshold = 0.50;
    private int leaveLastInteractionsOnHistoryConsolidation = 4;
    private float historicDocumentRelevancyThreashold=0.4f;

    /**
     * Constructor initializes the context window length ranges with
     * predefined settings.
     */
    public GeboChatConfigs() {
        this.ranges.add(new ContextWindowLengthRangeSettings(0, 30.0, 30.0, 10.0, HistoryStrategy.SHORTENQUEUE));
        this.ranges.add(new ContextWindowLengthRangeSettings(8192, 60.0, 30.0, 10.0, HistoryStrategy.SHORTENQUEUE));
        this.ranges.add(new ContextWindowLengthRangeSettings(16000, 60.0, 30.0, 10.0, HistoryStrategy.SHORTENQUEUE));
        this.ranges.add(new ContextWindowLengthRangeSettings(128000, 60.0, 30.0, 10.0, HistoryStrategy.SHORTENQUEUE));
    }

   

}