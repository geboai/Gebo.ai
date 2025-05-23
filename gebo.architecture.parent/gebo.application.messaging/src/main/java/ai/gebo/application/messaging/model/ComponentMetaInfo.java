/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.model;

/**
 * Gebo.ai comment agent
 * The ComponentMetaInfo class holds metadata information pertaining to a messaging component.
 * It includes identifiers for the messaging system and module, and flags indicating whether the component
 * acts as a receiver, emitter, or is part of a local system.
 */
public class ComponentMetaInfo {
    // Identifier for the messaging system
    private String messagingSystemId = null;

    // Identifier for the messaging module
    private String messagingModuleId = null;

    // Flag indicating if the component can receive messages
    private boolean receiver = false;

    // Flag indicating if the component can emit messages
    private boolean emitter = false;

    // Flag indicating if the component is part of the local system
    private boolean localSystem = false;

    /**
     * Default constructor for ComponentMetaInfo, initializes the object with default values.
     */
    public ComponentMetaInfo() {
    }

    /**
     * Parameterized constructor for ComponentMetaInfo.
     * 
     * @param messagingModuleId Identifier for the messaging module.
     * @param messagingSystemId Identifier for the messaging system.
     * @param receiver          Indicates if the component can receive messages.
     * @param emitter           Indicates if the component can emit messages.
     */
    public ComponentMetaInfo(String messagingModuleId, String messagingSystemId, boolean receiver, boolean emitter) {
        this.messagingModuleId = messagingModuleId;
        this.messagingSystemId = messagingSystemId;
        this.receiver = receiver;
        this.emitter = emitter;
    }

    /**
     * Gets the identifier for the messaging system.
     * 
     * @return the messaging system ID.
     */
    public String getMessagingSystemId() {
        return messagingSystemId;
    }

    /**
     * Sets the identifier for the messaging system.
     * 
     * @param messagingSystemId the messaging system ID to set.
     */
    public void setMessagingSystemId(String messagingSystemId) {
        this.messagingSystemId = messagingSystemId;
    }

    /**
     * Gets the identifier for the messaging module.
     * 
     * @return the messaging module ID.
     */
    public String getMessagingModuleId() {
        return messagingModuleId;
    }

    /**
     * Sets the identifier for the messaging module.
     * 
     * @param messagingModuleId the messaging module ID to set.
     */
    public void setMessagingModuleId(String messagingModuleId) {
        this.messagingModuleId = messagingModuleId;
    }

    /**
     * Checks if the component is a receiver.
     * 
     * @return true if the component can receive messages, false otherwise.
     */
    public boolean isReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver status of the component.
     * 
     * @param receiver true if the component should be a receiver, false otherwise.
     */
    public void setReceiver(boolean receiver) {
        this.receiver = receiver;
    }

    /**
     * Checks if the component is an emitter.
     * 
     * @return true if the component can emit messages, false otherwise.
     */
    public boolean isEmitter() {
        return emitter;
    }

    /**
     * Sets the emitter status of the component.
     * 
     * @param emitter true if the component should be an emitter, false otherwise.
     */
    public void setEmitter(boolean emitter) {
        this.emitter = emitter;
    }

    /**
     * Checks if the component is part of the local system.
     * 
     * @return true if the component is local, false otherwise.
     */
    public boolean isLocalSystem() {
        return localSystem;
    }

    /**
     * Sets the local system status of the component.
     * 
     * @param localSystem true if the component should be part of the local system, false otherwise.
     */
    public void setLocalSystem(boolean localSystem) {
        this.localSystem = localSystem;
    }
}