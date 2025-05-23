/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.ai.model;

import org.springframework.ai.tool.ToolCallback;

/**
 * Represents a reference to a tool, including its name, description, and a user UI function description.
 * 
 * Gebo.ai comment agent
 */
public class ToolReference {
    
    // Name of the tool
    private String name = null, 
    // Description of the tool
    description = null, 
    // User interface function description of the tool
    userUIfunctionDescription = null;

    /**
     * Default constructor for ToolReference.
     */
    public ToolReference() {

    }

    /**
     * Constructs a ToolReference object using a ToolCallback.
     * 
     * @param wraps A ToolCallback object that provides tool definition details.
     */
    public ToolReference(ToolCallback wraps) {
        // Initialize name and description using the tool definition provided by the ToolCallback
        this.name = wraps.getToolDefinition().name();
        this.description = wraps.getToolDefinition().description();
    }

    /**
     * Returns the name of the tool.
     * 
     * @return the name of the tool.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the tool.
     * 
     * @param name The name to set for the tool.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the tool.
     * 
     * @return the description of the tool.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the tool.
     * 
     * @param description The description to set for the tool.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the user interface function description of the tool.
     * 
     * @return the user UI function description of the tool.
     */
    public String getUserUIfunctionDescription() {
        return userUIfunctionDescription;
    }

    /**
     * Sets the user interface function description of the tool.
     * 
     * @param userUIfunctionDescription The UI function description to set for the tool.
     */
    public void setUserUIfunctionDescription(String userUIfunctionDescription) {
        this.userUIfunctionDescription = userUIfunctionDescription;
    }

}