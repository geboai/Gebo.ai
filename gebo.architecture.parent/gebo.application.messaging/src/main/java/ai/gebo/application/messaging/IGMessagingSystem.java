/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

/**
 * Gebo.ai comment agent
 * 
 * Represents an interface for the messaging system within the application. 
 * It defines methods to retrieve identifiers for the messaging module and system, 
 * and provides details about the component type as well as the complete ID.
 */
public interface IGMessagingSystem {

    /**
     * Determines if the messaging system is a local system.
     * 
     * @return always returns true, indicating a local system.
     */
    public default boolean isLocalSystem() {
        return true;
    }

    /**
     * Retrieves the identifier for the messaging module.
     * 
     * @return the ID of the messaging module as a String.
     */
    public String getMessagingModuleId();

    /**
     * Retrieves the identifier for the messaging system.
     * 
     * @return the ID of the messaging system as a String.
     */
    public String getMessagingSystemId();

    /**
     * Gets the type of component represented by this system.
     * 
     * @return the component type as a SystemComponentType.
     */
    public SystemComponentType getComponentType();

    /**
     * Constructs a complete identifier using both the messaging module 
     * and system identifiers.
     * 
     * @return the complete ID as a concatenated String in the format 
     * "moduleId.systemId".
     */
    public default String getCompleteId() {
        return getMessagingModuleId() + "." + getMessagingSystemId();
    }
}