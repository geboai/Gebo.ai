/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.maven.buildsystem.handler.config;

import ai.gebo.knlowledgebase.model.systems.GAbstractBuildSystemConfig;

/**
 * AI generated comments
 *
 * Represents the configuration for the base Maven build system.
 * Extends from GAbstractBuildSystemConfig to inherit common build system configurations.
 */
public class GBaseMavenBuildSystemConfig extends GAbstractBuildSystemConfig {
    
    /** Path to the Maven home directory. */
    private String mavenHome = null;
    
    /** Path to the Java home directory. */
    private String javaHome = null;
    
    /** 
     * Default constructor for GBaseMavenBuildSystemConfig. 
     * Initializes a new instance of the configuration class.
     */
    public GBaseMavenBuildSystemConfig() {
        // No specific initialization required at this moment
    }

    /**
     * Retrieves the Maven home directory path.
     *
     * @return the mavenHome as a String.
     */
    public String getMavenHome() {
        return mavenHome;
    }

    /**
     * Sets the Maven home directory path.
     *
     * @param mavenHome a String representing the new Maven home path to be set.
     */
    public void setMavenHome(String mavenHome) {
        this.mavenHome = mavenHome;
    }

    /**
     * Retrieves the Java home directory path.
     *
     * @return the javaHome as a String.
     */
    public String getJavaHome() {
        return javaHome;
    }

    /**
     * Sets the Java home directory path.
     *
     * @param javaHome a String representing the new Java home path to be set.
     */
    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }
    
}