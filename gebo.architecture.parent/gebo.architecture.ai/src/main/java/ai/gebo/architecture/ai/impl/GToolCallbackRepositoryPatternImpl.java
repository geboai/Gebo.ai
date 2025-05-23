/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.ai.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.model.tool.DefaultToolCallingManager.Builder;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSource;
import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

/**
 * Implementation of the IGToolCallbackSourceRepositoryPattern interface. This class manages a repository
 * of IGToolCallbackSource instances, allowing for retrieval and organization of tool callbacks.
 * 
 * AI generated comments
 */
@Service
public class GToolCallbackRepositoryPatternImpl extends GAbstractImplementationsRepositoryPattern<IGToolCallbackSource>
		implements IGToolCallbackSourceRepositoryPattern {
	
    /** Logger for logging information and errors. */
	static final Logger LOGGER = LoggerFactory.getLogger(GToolCallbackRepositoryPatternImpl.class);

	/**
	 * Constructs a new GToolCallbackRepositoryPatternImpl with the given implementations.
	 * Logs each implementation's ID if provided.
	 * 
	 * @param implementations a list of IGToolCallbackSource implementations, may be null.
	 */
	public GToolCallbackRepositoryPatternImpl(@Autowired(required = false) List<IGToolCallbackSource> implementations) {
		super(implementations);
		if (implementations != null) {
			LOGGER.info(
					"/*********************************************************\nIGFunctionCallbackWrappersFactory implementations:");
			for (IGToolCallbackSource imple : implementations) {
				LOGGER.info(" -" + imple.getId());
			}
			LOGGER.info("*********************************************************/");
		}
	}

	/**
	 * Returns the code value of the IGToolCallbackSource by retrieving its ID.
	 * 
	 * @param x the IGToolCallbackSource instance.
	 * @return the ID of the IGToolCallbackSource.
	 */
	@Override
	public String getCodeValue(IGToolCallbackSource x) {
		return x.getId();
	}

	/**
	 * Retrieves all tool callbacks from the implementations.
	 * 
	 * @return a list of all ToolCallback objects.
	 */
	public List<ToolCallback> getTools() {
		List<ToolCallback> callbacks = new ArrayList<ToolCallback>();
		List<IGToolCallbackSource> implementations = getImplementations();
		List<ToolCallback> allcallbacks = new ArrayList<ToolCallback>();
		for (IGToolCallbackSource factory : implementations) {
			List<ToolCallback> functions = factory.getToolCallbacks();
			if (functions == null) {
				LOGGER.error("Functions returned by => " + factory.getId() + " is a null list instead of an empty one");
			} else {
				allcallbacks.addAll(factory.getToolCallbacks());
			}
		}
		return allcallbacks;
	}

	/**
	 * Retrieves tool callbacks matching a specific list of function names.
	 * 
	 * @param functionNames the list of function names to filter by.
	 * @return a list of ToolCallback objects that match the specified names.
	 */
	public List<ToolCallback> getTools(List<String> functionNames) {
		List<ToolCallback> allcallbacks = getTools();
		return allcallbacks.stream().filter(x -> {
			return functionNames.contains(x.getToolDefinition().name());
		}).toList();
	}

	/**
	 * Retrieves a tree structure of tool categories that pass a given category search predicate.
	 * 
	 * @param categorySearch the predicate to filter tool categories.
	 * @return a list of ToolCategoriesTree objects.
	 */
	@Override
	public List<ToolCategoriesTree> getToolsTree(Predicate<ToolsCategory> categorySearch) {
		List<IGToolCallbackSource> impl = getImplementations();
		final TreeMap<String, ToolCategoriesTree> trees = new TreeMap<String, ToolCategoriesTree>();
		impl.stream().filter(x -> {
			return x.getToolCategory() != null && categorySearch.test(x.getToolCategory());
		}).forEach(y -> {
			if (!trees.containsKey(y.getToolCategory().getCode())) {
				trees.put(y.getToolCategory().getCode(), new ToolCategoriesTree(y.getToolCategory()));
			}
			List<ToolCallback> wrappers = y.getToolCallbacks();
			TreeMap<String, ToolReference> functions = new TreeMap<String, ToolReference>();
			// Map each ToolCallback to its ToolReference by its name
			for (ToolCallback wraps : wrappers) {
				functions.put(wraps.getToolDefinition().name(), new ToolReference(wraps));
			}
			// Add these references to the tree category
			trees.get(y.getToolCategory().getCode()).getToolsReference().addAll(functions.values());
		});
		// Return the list of all ToolCategoriesTree instances
		return new ArrayList<ToolCategoriesTree>(trees.values());
	}

	/**
	 * Resolves and retrieves a ToolCallback by its name.
	 * 
	 * @param name the name of the ToolCallback to find.
	 * @return the matching ToolCallback, or null if not found.
	 */
	@Override
	public ToolCallback resolve(String name) {
		Optional<ToolCallback> first = getTools().stream().filter(x -> x.getToolDefinition().name().equals(name))
				.findAny();
		return first.isPresent() ? first.get() : null;
	}

	/**
	 * Retrieves a filtered tree structure of tool categories containing only enabled functions.
	 * 
	 * @param enabledFunctions a list of enabled function names.
	 * @return a list of ToolCategoriesTree objects with only enabled functions.
	 */
	@Override
	public List<ToolCategoriesTree> getEnabledToolsTree(List<String> enabledFunctions) {
		if (enabledFunctions == null || enabledFunctions.isEmpty())
			return new ArrayList<ToolCategoriesTree>();
		List<ToolCategoriesTree> trees = this.getAllToolsTree();
		List<ToolCategoriesTree> enabledtrees = new ArrayList<ToolCategoriesTree>();
		for (ToolCategoriesTree t : trees) {
			if (t.getToolsReference() != null) {
				List<ToolReference> toremove = new ArrayList<ToolReference>();
				for (ToolReference r : t.getToolsReference()) {
					if (!enabledFunctions.contains(r.getName()))
						toremove.add(r);
				}
				// Remove functions not in enabled list
				for (ToolReference r : toremove) {
					t.getToolsReference().remove(r);
				}
				// Add the tree if it contains any enabled functions
				if (!t.getToolsReference().isEmpty()) {
					enabledtrees.add(t);
				}
			}
		}
		return enabledtrees;
	}

	/**
	 * Creates and returns a ToolCallingManager instance configured with this repository as the callback resolver.
	 * 
	 * @return a new ToolCallingManager instance.
	 */
	@Override
	public ToolCallingManager createToolCallingManager() {
		Builder builder = ToolCallingManager.builder();
		builder.toolCallbackResolver(this);
		return builder.build();
	}
}