/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.multilanguage.support.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Gebo.ai comment agent
 * Configuration class for resource folder settings in the application.
 * This class is marked as a Spring configuration component and binds
 * the properties defined with the prefix 'ai.gebo.multilanguage.config'
 * in the application's configuration file.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.multilanguage.config")
public class RCFolderConfig {

    // Indicates whether the classpath should be used for resources.
    private boolean classPathMode = false;

    // Prefix path for resource folders.
    private String folderPrefix = null;

    /**
     * Returns the class path mode status.
     * @return true if class path mode is enabled, false otherwise.
     */
    public boolean isClassPathMode() {
        return classPathMode;
    }

    /**
     * Sets the class path mode status.
     * @param classPathMode a boolean indicating the desired class path mode.
     */
    public void setClassPathMode(boolean classPathMode) {
        this.classPathMode = classPathMode;
    }

    /**
     * Gets the folder prefix used for resource configuration.
     * @return a string representing the folder prefix.
     */
    public String getFolderPrefix() {
        return folderPrefix;
    }

    /**
     * Sets the folder prefix used for resource configuration.
     * @param folderPrefix a string representing the desired folder prefix.
     */
    public void setFolderPrefix(String folderPrefix) {
        this.folderPrefix = folderPrefix;
    }
    
}