/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.AIResourceContent;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.ToolsCategory;

/**
 * The instances of the libraries of tools and resources a conversation holds.
 * <p>
 * A library - a working directory with its file tools, a sandbox with its code
 * tools - is instantiated for a conversation and kept by it: the conversation
 * records the id of every instance it holds, by library type, and every agent
 * entering its execution in that conversation reclaims those instances, so
 * that at every step every agent's tools work on the same pool of resources.
 * The instances go when the conversation goes.
 */
public interface IGAIToolsLibraryInstancesService {
	/** The category the tools of a library are shown under; not knowledge base related. */
	ToolsCategory LIBRARY_CATEGORY = new ToolsCategory("AI_TOOLS_LIBRARIES", "Libraries of tools and resources", false);

	/**
	 * The instances the conversation holds of the given library types, by type id:
	 * the ones it holds already, and one created now for every type it holds none
	 * of, which the conversation then records. A type this installation does not
	 * manage is left out and logged.
	 *
	 * @param chatSessionCode the conversation
	 * @param libraryTypeIds  the types an agent entering its execution works with
	 * @return the instances, by library type id; empty when there are no types
	 */
	Map<String, AIToolsAndResourcesLibraryInstance> reclaim(String chatSessionCode, Collection<String> libraryTypeIds);

	/**
	 * The libraries and the resources each holds, written for the agents to read:
	 * a tool is called on something, and the something has to be known. Empty when
	 * there are no instances.
	 */
	List<String> describe(Map<String, AIToolsAndResourcesLibraryInstance> instances);

	/**
	 * The tools of the given instances, as their sources bind them to each instance's
	 * resources: what an agent holding them mounts on its model for this run, and the
	 * only way a tool of a library reaches a model. Two libraries declaring a tool of
	 * the same name is an error, logged as such: the tool of the instance coming first
	 * stays. An instance whose source cannot give its tools is left out and logged.
	 *
	 * @param instances the instances an agent holds, by library type id, as
	 *                  {@link #reclaim(String, Collection)} gives them
	 * @return the tools, in the order of the instances; empty when there are none
	 */
	List<ToolCallback> toolsOf(Map<String, AIToolsAndResourcesLibraryInstance> instances);

	/**
	 * The library types managed by this installation among the given ones, in their
	 * order, each once: what they declare - their tools first of all - can be read
	 * with no run and no instance, by whoever shows what a configuration mounts. A
	 * type this installation does not manage is left out and logged.
	 *
	 * @param libraryTypeIds the types an agent configuration names
	 * @return the types; empty when there are none
	 */
	List<AIToolsAndResourcesLibraryType> typesOf(Collection<String> libraryTypeIds);

	/**
	 * The instances the conversation holds now, by library type id, as they are:
	 * nothing is created, an instance that is gone is left out. For whoever looks
	 * at what a conversation has - its resources, which of them can be taken away.
	 */
	Map<String, AIToolsAndResourcesLibraryInstance> heldBy(String chatSessionCode);

	/**
	 * A resource of an instance the conversation holds, as a file, from the source
	 * managing its type; null when the conversation holds no such instance, the
	 * instance has no such resource, or the resource's type is not downloadable.
	 * The caller closes the stream.
	 */
	AIResourceContent download(String chatSessionCode, String libraryTypeId, String resourceId);

	/** Drops every instance the conversation holds; the conversation records none. */
	void dropHeldBy(String chatSessionCode);
}
