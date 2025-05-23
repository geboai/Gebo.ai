/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.ai;

import java.util.List;

import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;

/**
 * Gebo.ai comment agent
 * Represents a source for tool callbacks within the AI architecture.
 * This interface provides methods to retrieve tool category, tool references, and tool callbacks.
 */
public interface IGToolCallbackSource {
    
    /**
     * Retrieves the unique identifier of this tool callback source.
     *
     * @return The ID of the tool callback source.
     */
    public String getId();

    /**
     * Retrieves the category of tools associated with this source.
     *
     * @return The tools category.
     */
    public ToolsCategory getToolCategory();

    /**
     * Retrieves the complete list of tool references.
     *
     * @return A list of ToolReference objects representing all tool references.
     */
    public List<ToolReference> getFullToolReferences();

    /**
     * Retrieves a list of active tool references by filtering the complete list.
     * Active tools are those that have corresponding callbacks.
     *
     * @return A list of active ToolReference objects.
     */
    public default List<ToolReference> getActiveToolReferences() {
        List<ToolReference> functionsMeta = getFullToolReferences();
        List<ToolCallback> wrappers = getToolCallbacks();

        // Filters tool references to include only those that have matching tool callbacks
        return functionsMeta.stream().filter(x -> {
            return wrappers.stream().anyMatch(y -> {
                return x.getName().equals(y.getToolDefinition().name());
            });
        }).toList();
    }

    /**
     * Retrieves the list of tool callbacks associated with this source.
     *
     * @return A list of ToolCallback objects.
     */
    public List<ToolCallback> getToolCallbacks();
}