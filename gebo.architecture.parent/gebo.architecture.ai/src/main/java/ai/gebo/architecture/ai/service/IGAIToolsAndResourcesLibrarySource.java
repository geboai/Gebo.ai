/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.service;

import java.util.List;

import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.AIResourceContent;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;

/**
 * A source of libraries of tools and resources: bundles an agent is given whole,
 * the tools and the resources those tools work on together - a working
 * directory with its file tools, a sandbox with its code tools, a browser with
 * its page tools.
 * <p>
 * A source manages library types. An instance of a type is created for whoever
 * is to own it, lives until it is dropped, and is what the agents actually work
 * on: its tools are callbacks bound to its resources. The type names its tools
 * and the kinds of resources an instance holds, and what using them implies
 * (reading, writing, running code), so that whoever configures an agent can
 * consent to it before any instance exists.
 */
public interface IGAIToolsAndResourcesLibrarySource {
	public String getId();

	public String getDescription();

	/** The library types this source manages. */
	public List<AIToolsAndResourcesLibraryType> getManagedTypes();

	/**
	 * An instance by its id, for one of its owners; null when there is none, or
	 * none of theirs.
	 */
	public AIToolsAndResourcesLibraryInstance getInstanceById(String id, String owner);

	/** Creates an instance of a type for the given owners. */
	public AIToolsAndResourcesLibraryInstance createInstance(AIToolsAndResourcesLibraryType type, List<String> owners);

	/** Drops an instance: what it held is released, its tools are no more. */
	public void dropInstance(AIToolsAndResourcesLibraryInstance instance);

	/**
	 * The tools of an instance, bound to its resources: the callables behind the
	 * tool references its type declares, one per reference, with the same names.
	 * Called when an agent of a run that holds the instance calls one of them.
	 *
	 * @param instance an instance this source created
	 * @return the callbacks, never null
	 */
	public List<ToolCallback> getToolCallbacks(AIToolsAndResourcesLibraryInstance instance);

	/**
	 * A resource of an instance as a file, for whoever wants to take it away: its
	 * content type, a name for it and its bytes. Only for resources whose type
	 * says so ({@code AIResourceType.downloadable}); a working directory comes as
	 * a zip, a document as itself. The caller closes the stream. Null when the
	 * resource is not one of the instance's, or not one that can be handed over -
	 * which is what this default answers, for a source whose resources never are.
	 *
	 * @param instance   an instance this source created
	 * @param resourceId the id of one of its resources
	 */
	public default AIResourceContent download(AIToolsAndResourcesLibraryInstance instance, String resourceId) {
		return null;
	}

}
