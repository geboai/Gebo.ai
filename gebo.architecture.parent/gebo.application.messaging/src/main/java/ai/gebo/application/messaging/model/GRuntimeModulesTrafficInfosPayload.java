/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.patterns.model.GModuleTrafficInfo;

/**
 * Gebo.ai comment agent
 * 
 * Represents a payload containing traffic information for runtime modules.
 */
public class GRuntimeModulesTrafficInfosPayload extends GBaseMessagePayload {
    
    // List to store module traffic information data
    List<GModuleTrafficInfo> data = new ArrayList<GModuleTrafficInfo>();

    /**
     * Default constructor for GRuntimeModulesTrafficInfosPayload.
     */
    public GRuntimeModulesTrafficInfosPayload() {
        
    }

    /**
     * Gets the module traffic information data.
     * 
     * @return a list of GModuleTrafficInfo data
     */
    public List<GModuleTrafficInfo> getData() {
        return data;
    }

    /**
     * Sets the module traffic information data.
     * 
     * @param data the new list of GModuleTrafficInfo data to set
     */
    public void setData(List<GModuleTrafficInfo> data) {
        this.data = data;
    }

}