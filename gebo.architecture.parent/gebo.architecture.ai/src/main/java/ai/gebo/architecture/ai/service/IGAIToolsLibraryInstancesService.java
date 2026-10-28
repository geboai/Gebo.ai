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

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;

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

	/** Drops every instance the conversation holds; the conversation records none. */
	void dropHeldBy(String chatSessionCode);
}
