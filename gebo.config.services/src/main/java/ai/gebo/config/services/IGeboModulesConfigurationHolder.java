/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.config.services;

import java.util.Map;

import ai.gebo.config.model.GeboModuleInfo;

/**
 * AI generated comments
 * Interface representing the configuration holder for Gebo modules.
 * Provides methods to access the full configuration and specific module configurations.
 */
public interface IGeboModulesConfigurationHolder {

    /**
     * Retrieves the full configuration map of Gebo modules.
     * The keys are module IDs and the values are instances of GeboModuleInfo.
     * 
     * @return a map containing the full configuration details for all Gebo modules.
     */
    public Map<String, GeboModuleInfo> getFullConfiguration();

    /**
     * Retrieves the configuration for a specific Gebo module given its unique identifier.
     * 
     * @param messagingModuleId the unique identifier for the messaging module.
     * @return the GeboModuleInfo instance containing configuration details for the specified module.
     */
    public GeboModuleInfo getModuleConfig(String messagingModuleId);
}