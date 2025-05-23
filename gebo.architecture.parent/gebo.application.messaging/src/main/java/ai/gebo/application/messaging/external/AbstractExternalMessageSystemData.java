/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.external;

import ai.gebo.application.messaging.SystemComponentType;

/**
 * Gebo.ai comment agent
 * AbstractExternalMessageSystemData encapsulates the information related
 * to an external messaging system which includes the messaging module ID,
 * messaging system ID, and the type of system component.
 */
public class AbstractExternalMessageSystemData {

    // Identifier for the messaging module
    private String messagingModuleId = null;

    // Identifier for the messaging system
    private String messagingSystemId = null;

    // The type of system component
    private SystemComponentType componentType = null;

    /**
     * Gets the messaging module ID.
     * 
     * @return the messaging module ID
     */
    public String getMessagingModuleId() {
        return messagingModuleId;
    }

    /**
     * Sets the messaging module ID.
     * 
     * @param messagingModuleId the new messaging module ID
     */
    public void setMessagingModuleId(String messagingModuleId) {
        this.messagingModuleId = messagingModuleId;
    }

    /**
     * Gets the messaging system ID.
     * 
     * @return the messaging system ID
     */
    public String getMessagingSystemId() {
        return messagingSystemId;
    }

    /**
     * Sets the messaging system ID.
     * 
     * @param messagingSystemId the new messaging system ID
     */
    public void setMessagingSystemId(String messagingSystemId) {
        this.messagingSystemId = messagingSystemId;
    }

    /**
     * Gets the component type of the system.
     * 
     * @return the component type
     */
    public SystemComponentType getComponentType() {
        return componentType;
    }

    /**
     * Sets the component type of the system.
     * 
     * @param componentType the new component type
     */
    public void setComponentType(SystemComponentType componentType) {
        this.componentType = componentType;
    }
}