/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.controllers.model;

import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.model.base.GObjectRef;

/**
 * Gebo.ai comment agent
 * 
 * Represents a configuration entry which holds a configuration object of type T.
 * It also maintains a reference to the object using GObjectRef for reference management.
 * 
 * T the type of the configuration, which extends GBaseModelConfig
 */
public class ConfigurationEntry<T extends GBaseModelConfig> {

    // Holds the configuration of type T
    private T configuration = null;

    // An object reference for the configuration
    private GObjectRef<T> objectReference = null;

    /**
     * Constructs a ConfigurationEntry with the specified configuration.
     * Initializes the object reference using the configuration.
     * 
     * @param configuration the configuration object of type T
     */
    public ConfigurationEntry(T configuration) {
        this.configuration = configuration;
        this.objectReference = GObjectRef.of(configuration);
    }

    /**
     * Returns the current configuration object.
     * 
     * @return the configuration of type T
     */
    public T getConfiguration() {
        return configuration;
    }

    /**
     * Sets the configuration object to the specified value.
     * 
     * @param configuration the new configuration object of type T
     */
    public void setConfiguration(T configuration) {
        this.configuration = configuration;
    }

    /**
     * Returns the object reference of the configuration.
     * 
     * @return the object reference of type GObjectRef of T type
     */
    public GObjectRef<T> getObjectReference() {
        return objectReference;
    }

    /**
     * Sets the object reference to the specified value.
     * 
     * @param objectReference the new object reference of type GObjectRef of T type
     */
    public void setObjectReference(GObjectRef<T> objectReference) {
        this.objectReference = objectReference;
    }
}