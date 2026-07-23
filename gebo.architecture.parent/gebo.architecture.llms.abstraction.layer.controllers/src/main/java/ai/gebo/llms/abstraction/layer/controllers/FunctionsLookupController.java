/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.ai.service.IGExternalToolCallback;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.model.base.GLookupEntry;
import lombok.AllArgsConstructor;

/**
 * AI generated comments Controller to handle admin functions lookup operations.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')") // Ensures that only users with the 'ADMIN' role can access the methods in this
									// controller.
@RequestMapping("api/admin/FunctionsLookupController") // Defines the base URL path for the controller.
@AllArgsConstructor
public class FunctionsLookupController {
	private final IGToolCallbackSourceRepositoryPattern repoPattern; // Repository pattern to manage tool callback
																		// sources.

	/**
	 * Retrieves a list of all function entries.
	 * 
	 * @return A list of GLookupEntry objects representing all functions.
	 */
	@GetMapping(value = "getAllFunctions", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GLookupEntry> getAllFunctions() {
		// Map each tool to its lookup entry and return the list.
		return this.repoPattern.getTools().stream().map(x -> {
			GLookupEntry entry = new GLookupEntry();
			entry.setCode(x.getToolDefinition().name()); // Set the code from tool definition name.
			entry.setDescription(x.getToolDefinition().description()); // Set the description from tool definition.
			return entry;
		}).toList();
	}

	@GetMapping(value = "getAllLocalFunctions", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GLookupEntry> getAllLocalFunctions() {
		// Map each tool to its lookup entry and return the list.
		return this.repoPattern.getTools().stream().filter(y -> !(y instanceof IGExternalToolCallback)).map(x -> {
			GLookupEntry entry = new GLookupEntry();
			entry.setCode(x.getToolDefinition().name()); // Set the code from tool definition name.
			entry.setDescription(x.getToolDefinition().description()); // Set the description from tool definition.
			return entry;
		}).toList();
	}

	/***
	 * Retrieves a tree structure of tool categories, filtered by the RAG context
	 * function parameter.
	 * 
	 * @param ragContextFunctions If null, returns all functions. If true, includes
	 *                            all functions. If false, includes only functions
	 *                            that do not require a knowledge base context.
	 * @return A list of ToolCategoriesTree objects based on the filter criteria.
	 */
	@GetMapping(value = "getAllFunctionsTree", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ToolCategoriesTree> getAllFunctionsTree(
			@RequestParam(value = "ragContextFunctions", required = false) Boolean ragContextFunctions) {

		return repoPattern.getToolsTree(x -> {
			if (ragContextFunctions == null)
				return true;
			if (ragContextFunctions)
				return true;
			return x.getKnowledgeBaseRelative() == null || x.getKnowledgeBaseRelative() == false;
		});
	}

	/***
	 * Retrieves a tree structure of tool categories, filtered by the RAG context
	 * function parameter, exposing only local tools. Any tool whose callback is an
	 * {@link IGExternalToolCallback} is excluded from the returned tree, and any
	 * category left without local tools is dropped.
	 *
	 * @param ragContextFunctions If null or true, includes all (local) functions.
	 *                            If false, includes only local functions that do
	 *                            not require a knowledge base context.
	 * @return A list of ToolCategoriesTree objects whose leaves are guaranteed not
	 *         to be IGExternalToolCallback instances.
	 */
	@GetMapping(value = "getAllLocalFunctionsTree", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ToolCategoriesTree> getAllLocalFunctionsTree(
			@RequestParam(value = "ragContextFunctions", required = false) Boolean ragContextFunctions) {

		// Build the full category tree honoring the RAG context filter.
		List<ToolCategoriesTree> tree = repoPattern.getToolsTree(x -> {
			if (ragContextFunctions == null)
				return true;
			if (ragContextFunctions)
				return true;
			return x.getKnowledgeBaseRelative() == null || x.getKnowledgeBaseRelative() == false;
		});

		// Collect the names of all local (non-external) tools so we can prune the
		// leaves.
		Set<String> localToolNames = this.repoPattern.getTools().stream()
				.filter(t -> !(t instanceof IGExternalToolCallback)).map(t -> t.getToolDefinition().name())
				.collect(Collectors.toSet());

		// Keep only leaves referring to local tools, and drop categories left empty.
		// Calling jsonClone() to avoid changing shared model
		return tree.stream().map(x -> x.jsonClone()).peek(category -> {
			if (category.getToolsReference() != null) {
				category.getToolsReference().removeIf(ref -> !localToolNames.contains(ref.getName()));
			}
		}).filter(category -> category.getToolsReference() != null && !category.getToolsReference().isEmpty()).toList();
	}

}