/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.systems;

/**
 * AI generated comments
 * Represents a reference to a build system, including its type and configuration codes.
 */
public class BuildSystemRef {
    
    // Code representing the type of the build system
    private String buildSystemTypeCode = null;
    
    // Code representing the configuration of the build system
    private String buildSystemConfigCode = null;

    /**
     * Retrieves the code for the build system type.
     * 
     * @return A String representing the build system type code.
     */
    public String getBuildSystemTypeCode() {
        return buildSystemTypeCode;
    }

    /**
     * Sets the code for the build system type.
     * 
     * @param buildSystemTypeCode A String that represents the new build system type code.
     */
    public void setBuildSystemTypeCode(String buildSystemTypeCode) {
        this.buildSystemTypeCode = buildSystemTypeCode;
    }

    /**
     * Retrieves the code for the build system configuration.
     * 
     * @return A String representing the build system configuration code.
     */
    public String getBuildSystemConfigCode() {
        return buildSystemConfigCode;
    }

    /**
     * Sets the code for the build system configuration.
     * 
     * @param buildSystemConfigCode A String that represents the new build system configuration code.
     */
    public void setBuildSystemConfigCode(String buildSystemConfigCode) {
        this.buildSystemConfigCode = buildSystemConfigCode;
    }
}