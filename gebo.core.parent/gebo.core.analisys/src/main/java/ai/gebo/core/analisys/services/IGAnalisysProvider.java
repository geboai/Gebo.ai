/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.analisys.services;

import java.util.List;

import ai.gebo.core.analisys.model.GStatsHolder;

/**
 * AI generated comments
 * Interface defining the structure of an analysis provider
 * for GStatsHolder data.
 */
public interface IGAnalisysProvider {
	
    /**
     * Retrieves the unique identifier of the analysis provider.
     *
     * @return A String representing the unique ID of the provider.
     */
	public String getId();
	
    /**
     * Computes analytics based on the provided list of GStatsHolder objects.
     * The specific analytics implementation will be provided by the implementing class.
     *
     * @param holder A List of GStatsHolder objects containing statistical data for analysis.
     */
	public void compute(List<GStatsHolder> holder);
}