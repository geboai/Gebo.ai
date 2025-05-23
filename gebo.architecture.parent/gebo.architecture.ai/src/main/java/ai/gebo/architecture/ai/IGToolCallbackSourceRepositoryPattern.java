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
import java.util.function.Predicate;

import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.resolution.ToolCallbackResolver;

import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * Interface defining a repository pattern for managing tool callbacks and resolving them.
 * Extends the generic repository pattern and tool callback resolver interfaces.
 */
public interface IGToolCallbackSourceRepositoryPattern
        extends IGImplementationsRepositoryPattern<IGToolCallbackSource>, ToolCallbackResolver {

    /**
     * Retrieves a list of all tool callbacks available in the repository.
     * 
     * @return A list of ToolCallback instances.
     */
    public List<ToolCallback> getTools();

    /**
     * Retrieves tool callbacks that match the given function names.
     * 
     * @param functionNames A list of function names to filter the tool callbacks.
     * @return A list of ToolCallback instances matching the specified function names.
     */
    public List<ToolCallback> getTools(List<String> functionNames);

    /**
     * Retrieves a tree structure of tool categories, filtered by the provided category search predicate.
     * 
     * @param categorySearch A predicate to filter tool categories.
     * @return A list of ToolCategoriesTree instances that match the filter criteria.
     */
    public List<ToolCategoriesTree> getToolsTree(Predicate<ToolsCategory> categorySearch);

    /**
     * Retrieves a tree structure of enabled tool categories based on a list of enabled function names.
     * 
     * @param enabledFunctions A list of enabled function names.
     * @return A list of ToolCategoriesTree instances containing only enabled tools.
     */
    public List<ToolCategoriesTree> getEnabledToolsTree(List<String> enabledFunctions);

    /**
     * Retrieves a tree structure of all tool categories without any filters.
     * 
     * @return A list of all ToolCategoriesTree instances.
     */
    public default List<ToolCategoriesTree> getAllToolsTree() {
        return getToolsTree(x -> {
            return true;
        });
    }

    /**
     * Retrieves a tree structure of all tool categories with optional filtering based on RAG context functions.
     * 
     * @param ragContextFunctions Boolean flag indicating if RAG context functions should be included.
     * @return A list of ToolCategoriesTree instances based on the filtering criteria.
     */
    public default List<ToolCategoriesTree> getAllToolsTree(boolean ragContextFunctions) {
        return getToolsTree(x -> {
            if (ragContextFunctions)
                return true;
            return x.getKnowledgeBaseRelative() == null || x.getKnowledgeBaseRelative() == false;
        });
    }

    /**
     * Creates a ToolCallingManager instance responsible for managing tool calls.
     * 
     * @return A ToolCallingManager instance.
     */
    public ToolCallingManager createToolCallingManager();
}