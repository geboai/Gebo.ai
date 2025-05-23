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

/**
 * Gebo.ai comment agent
 * Represents metadata information for a module in the messaging application.
 * Contains a module ID and a list of component metadata information.
 */
public class GModuleMetaInfo {
    // Unique identifier for the messaging module
    private String messagingModuleId = null;
    // List of components associated with the module
    private List<ComponentMetaInfo> components = new ArrayList<ComponentMetaInfo>();

    /**
     * Default constructor for GModuleMetaInfo.
     * Initializes the components list.
     */
    public GModuleMetaInfo() {
    }

    /**
     * Constructs a GModuleMetaInfo with a specific module ID and list of components.
     *
     * @param moduleId the unique identifier for the messaging module
     * @param components the list of components associated with the module
     */
    public GModuleMetaInfo(String moduleId, List<ComponentMetaInfo> components) {
        this.messagingModuleId = moduleId;
        this.components = components;
    }

    /**
     * Retrieves the messaging module ID.
     *
     * @return the unique identifier for the messaging module
     */
    public String getMessagingModuleId() {
        return messagingModuleId;
    }

    /**
     * Sets the messaging module ID.
     *
     * @param messagingModuleId the new unique identifier for the messaging module
     */
    public void setMessagingModuleId(String messagingModuleId) {
        this.messagingModuleId = messagingModuleId;
    }

    /**
     * Retrieves the list of component metadata information.
     *
     * @return the list of components associated with the module
     */
    public List<ComponentMetaInfo> getComponents() {
        return components;
    }

    /**
     * Sets the list of component metadata information.
     *
     * @param components the new list of components associated with the module
     */
    public void setComponents(List<ComponentMetaInfo> components) {
        this.components = components;
    }
}