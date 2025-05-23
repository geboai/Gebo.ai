/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.functions;

import java.util.List;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSource;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;

/**
 * Gebo.ai comment agent
 * 
 * The ContentSearchingFunctions class provides functionality for defining callbacks 
 * and references for various content searching tools. It is marked as a Spring 
 * Service and implements the IGToolCallbackSource interface to adhere to AI tool 
 * callback requirements.
 */
@Service
public class ContentSearchingFunctions implements IGToolCallbackSource {

    /**
     * Default constructor for the ContentSearchingFunctions class.
     */
    public ContentSearchingFunctions() {

    }

    /**
     * Retrieves the unique identifier for this tool callback source, 
     * which in this case, is the fully qualified class name.
     *
     * @return the unique identifier as a String
     */
    @Override
    public String getId() {

        return getClass().getName();
    }

    /**
     * Specifies the category of tools that this implementation belongs to.
     *
     * @return the tool category enumeration value
     */
    @Override
    public ToolsCategory getToolCategory() {

        return ToolsCategory.KNOWLEDGE_BASE_VARIOUS_SEARCHES;
    }

    /**
     * Provides a list of tool references that are fully defined by this source.
     * Currently, it returns an empty list.
     *
     * @return a list of ToolReference instances
     */
    @Override
    public List<ToolReference> getFullToolReferences() {

        return List.of();
    }

    /**
     * Delivers a list of tool callbacks associated with this source.
     * Currently, it returns an empty list, indicating no callbacks are defined.
     *
     * @return a list of ToolCallback instances
     */
    @Override
    public List<ToolCallback> getToolCallbacks() {

        return List.of();
    }

}