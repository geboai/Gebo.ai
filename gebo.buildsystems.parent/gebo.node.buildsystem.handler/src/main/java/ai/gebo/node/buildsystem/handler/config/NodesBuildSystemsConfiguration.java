/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.node.buildsystem.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.knlowledgebase.model.systems.GBuildSystem;

/**
 * AI generated comments
 * Configuration class for managing node build systems.
 * It is annotated with {@code @Configuration} to indicate that it's a source of bean definitions.
 * The {@code @ConfigurationProperties} annotation is used to bind external configuration properties to this class.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.node.buildsystems")
public class NodesBuildSystemsConfiguration {

    /**
     * A list of build systems configurations.
     */
    private List<GBuildSystem<GNodeBuildSystemsConfig>> systems = new ArrayList<GBuildSystem<GNodeBuildSystemsConfig>>();

    /**
     * Default constructor.
     */
    public NodesBuildSystemsConfiguration() {
        // Default constructor for initialization purposes
    }

    /**
     * Retrieves the list of build systems configurations.
     * 
     * @return a list of {@code GBuildSystem<GNodeBuildSystemsConfig>} objects.
     */
    public List<GBuildSystem<GNodeBuildSystemsConfig>> getSystems() {
        return systems;
    }

    /**
     * Sets the list of build systems configurations.
     * 
     * @param systems a list of {@code GBuildSystem<GNodeBuildSystemsConfig>} objects to set.
     */
    public void setSystems(List<GBuildSystem<GNodeBuildSystemsConfig>> systems) {
        this.systems = systems;
    }

}