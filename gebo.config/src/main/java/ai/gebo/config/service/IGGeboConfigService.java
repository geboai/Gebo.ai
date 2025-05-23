/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.config.service;

import ai.gebo.config.GeboConfig;

/**
 * AI generated comments
 * Interface representing the configuration service for Gebo.
 * Provides methods to access aspects of the Gebo configuration such as home directory,
 * work directory, and configuration status.
 */
public interface IGGeboConfigService {

    /**
     * Gets the path to the Gebo home directory.
     * 
     * @return a String representing the Gebo home directory path.
     */
    public String getGeboHome();

    /**
     * Gets the path to the Gebo work directory.
     * 
     * @return a String representing the Gebo work directory path.
     */
    public String getGeboWorkDirectory();

    /**
     * Retrieves the Gebo configuration details.
     * 
     * @return a GeboConfig object containing the configuration details.
     */
    public GeboConfig getGeboConfig();

    /**
     * Checks if the Gebo home directory is set.
     * 
     * @return true if the Gebo home directory is set, false otherwise.
     */
    public boolean isGeboHomeSet();

    /**
     * Checks if the Gebo work directory is set.
     * 
     * @return true if the Gebo work directory is set, false otherwise.
     */
    public boolean isGeboWorkDirectorySet();
}