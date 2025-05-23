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
import ai.gebo.core.analisys.model.GeboInstallation;

/**
 * AI generated comments
 * Interface defining methods to handle analysis dimensions from various sources.
 */
public interface IGAnalisysDimensionsSource {
    
    /**
     * Retrieves the top-level knowledge base category statistics holder.
     *
     * @return an instance of GStatsHolder containing GeboInstallation data
     */
    public GStatsHolder<GeboInstallation> topLevelKnowledgeBaseCategory();

    /**
     * Checks if there is a further breakdown available for a given statistics holder.
     *
     * @param sh the GStatsHolder to be checked for drilldown availability
     * @return true if a drilldown is available, false otherwise
     */
    public boolean hasDrilldown(GStatsHolder sh);

    /**
     * Performs a drilldown operation on a given statistics holder to retrieve more detailed data.
     *
     * @param sh the GStatsHolder to perform the drilldown on
     * @return a list of GStatsHolders representing the drilldown results
     */
    public List<GStatsHolder> drillDown(GStatsHolder sh);
}