/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.external;

import java.util.List;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;

/**
 * Gebo.ai comment agent
 * Abstract class that provides a common foundation for external message emitter implementations,
 * adhering to IGExternalMessageEmitter interface.
 */
public abstract class GAbstractExternalMessageEmitter implements IGExternalMessageEmitter {
    // Configuration data for the external emitter
    private ExternalEmitterIfaceData config = null;

    /**
     * Default constructor for GAbstractExternalMessageEmitter.
     */
    public GAbstractExternalMessageEmitter() {
        // Empty constructor
    }

    /**
     * Constructor that accepts an ExternalEmitterIfaceData configuration.
     *
     * @param config the ExternalEmitterIfaceData configuration for the emitter
     */
    public GAbstractExternalMessageEmitter(ExternalEmitterIfaceData config) {
        this.config = config;
    }

    /**
     * Inner class that implements IGMessageEmitter, providing access to system
     * identification and characteristics from the config.
     */
    class NestedEmitter implements IGMessageEmitter {
        @Override
        public String getMessagingSystemId() {
            // Retrieves the messaging system ID from the config
            return config != null ? config.getMessagingSystemId() : null;
        }

        @Override
        public String getMessagingModuleId() {
            // Retrieves the messaging module ID from the config
            return config != null ? config.getMessagingModuleId() : null;
        }

        @Override
        public SystemComponentType getComponentType() {
            // Retrieves the component type from the config
            return config != null ? config.getComponentType() : null;
        }

        @Override
        public List<String> getEmittedPayloadTypes() {
            // Retrieves the list of emitted payload types from the config
            return config != null ? config.getEmittedPayloadTypes() : null;
        }
        
        @Override
        public boolean isLocalSystem() {
            // Always returns false indicating the system is not local
            return false;
        }
    }

    // Instance of NestedEmitter to handle messaging responsibilities
    private final NestedEmitter emitter = new NestedEmitter();

    @Override
    public IGMessageEmitter getEmitter() {
        // Provides access to the nested emitter instance
        return emitter;
    }

    /**
     * Retrieves the current configuration data for the external emitter.
     *
     * @return the current ExternalEmitterIfaceData configuration
     */
    public ExternalEmitterIfaceData getConfig() {
        return config;
    }

    /**
     * Sets the configuration data for the external emitter.
     *
     * @param config the ExternalEmitterIfaceData configuration to set
     */
    public void setConfig(ExternalEmitterIfaceData config) {
        this.config = config;
    }
}