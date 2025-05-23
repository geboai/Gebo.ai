/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.maven.buildsystem.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.knlowledgebase.model.systems.GBuildSystem;

/**
 * AI generated comments
 * Configuration class for Maven build systems. This class is annotated with
 * Spring's @Configuration to indicate that it contains configuration beans.
 * It is mapped to the properties with the prefix "ai.gebo.maven.buildsystems" 
 * from external configuration files.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.maven.buildsystems")
public class MavenBuildSystemsConfiguration {
    
    // List of GBuildSystem configurations initialized as an empty ArrayList
    private List<GBuildSystem<GBaseMavenBuildSystemConfig>> systems = new ArrayList<GBuildSystem<GBaseMavenBuildSystemConfig>>();

    /**
     * Default constructor for MavenBuildSystemsConfiguration.
     */
    public MavenBuildSystemsConfiguration() {

    }

    /**
     * Returns the list of GBuildSystem configurations.
     * 
     * @return List of build system configurations.
     */
    public List<GBuildSystem<GBaseMavenBuildSystemConfig>> getSystems() {
        return systems;
    }

    /**
     * Sets the list of GBuildSystem configurations.
     * 
     * @param systems List of build system configurations to set.
     */
    public void setSystems(List<GBuildSystem<GBaseMavenBuildSystemConfig>> systems) {
        this.systems = systems;
    }

}