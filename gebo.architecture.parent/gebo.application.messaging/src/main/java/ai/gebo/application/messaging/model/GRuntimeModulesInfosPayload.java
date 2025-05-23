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

import ai.gebo.architecture.patterns.model.GModuleUseInfo;

/**
 * Gebo.ai comment agent
 * Represents the payload containing runtime module information for the messaging application.
 * This class extends the GBaseMessagePayload to incorporate basic message payload properties.
 */
public class GRuntimeModulesInfosPayload extends GBaseMessagePayload {

    // List of GModuleUseInfo objects representing the data part of the payload.
    List<GModuleUseInfo> data = new ArrayList<GModuleUseInfo>();

    /**
     * Constructs an empty GRuntimeModulesInfosPayload.
     */
    public GRuntimeModulesInfosPayload() {

    }
    
    /**
     * Retrieves the list of module usage information.
     *
     * @return List of GModuleUseInfo representing module usage information.
     */
    public List<GModuleUseInfo> getData() {
        return data;
    }

    /**
     * Sets the list of module usage information.
     *
     * @param data List of GModuleUseInfo to be set in the payload.
     */
    public void setData(List<GModuleUseInfo> data) {
        this.data = data;
    }
}