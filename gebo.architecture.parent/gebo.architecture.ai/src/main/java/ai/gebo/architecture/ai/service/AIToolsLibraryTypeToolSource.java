/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;

/**
 * The tools of one library type, as a tool source an agent configuration names
 * whole: an agent that names the type mounts every tool of it, and none of them
 * one by one.
 * <p>
 * The tools exported here are stand-ins. A library's tools are bound to an
 * instance - the working directory, the sandbox - and an instance exists for a
 * run, not for the registry; so what is registered is one tool per reference
 * the type declares, which finds the run's instance of the type in its tool
 * context ({@link ToolCallbackDeclarationUtil#LIBRARY_INSTANCES_CONTEXT_KEY})
 * and hands the call to the instance's own tool of the same name. A run that
 * holds no instance of the type is told so, in the tool's answer: nothing
 * breaks, the agent learns the library is not available here.
 * <p>
 * One of these is registered per managed type of every library source at
 * start-up (see {@code AIToolsLibraryTypeToolSourcesAssembler}); its id is
 * {@link #sourceIdOf(String)} of the type's id. Mounted only when asked: a
 * library belongs to the agents whose configuration names it.
 */
public class AIToolsLibraryTypeToolSource implements IGToolCallbackSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(AIToolsLibraryTypeToolSource.class);
	/** What the id of the source of a library type starts with. */
	public static final String SOURCE_ID_PREFIX = "ai-tools-library:";
	/** The category every library type's tools are offered under; not knowledge base related. */
	public static final ToolsCategory LIBRARY_CATEGORY = new ToolsCategory("AI_TOOLS_LIBRARIES",
			"Libraries of tools and resources", false);
	/** What a tool with no declared input schema accepts: an object with nothing required. */
	static final String NO_INPUT_SCHEMA = "{\"type\":\"object\",\"properties\":{}}";

	private final AIToolsAndResourcesLibraryType type;
	private final IGAIToolsAndResourcesLibrarySource library;
	private final List<ToolCallback> callbacks;

	public AIToolsLibraryTypeToolSource(AIToolsAndResourcesLibraryType type,
			IGAIToolsAndResourcesLibrarySource library) {
		this.type = type;
		this.library = library;
		this.callbacks = standIns();
	}

	/** The id of the tool source of a library type. */
	public static String sourceIdOf(String libraryTypeId) {
		return SOURCE_ID_PREFIX + libraryTypeId;
	}

	/** Whether a tool source id is the one of a library type. */
	public static boolean isLibraryTypeSource(String sourceId) {
		return sourceId != null && sourceId.startsWith(SOURCE_ID_PREFIX);
	}

	public AIToolsAndResourcesLibraryType getType() {
		return type;
	}

	@Override
	public String getId() {
		return sourceIdOf(type.getId());
	}

	@Override
	public String getDescription() {
		return "Library: " + (type.getDescription() != null && !type.getDescription().isBlank() ? type.getDescription()
				: type.getId());
	}

	@Override
	public ToolsCategory getToolCategory() {
		return LIBRARY_CATEGORY;
	}

	/** Only the agents whose configuration names this library type. */
	@Override
	public boolean isMountedOnlyWhenAsked() {
		return true;
	}

	@Override
	public List<ToolReference> getFullToolReferences() {
		return type.getTools() != null ? List.copyOf(type.getTools()) : List.of();
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		return callbacks;
	}

	/** One stand-in per tool the type declares; a reference with no name is left out and logged. */
	private List<ToolCallback> standIns() {
		final List<ToolCallback> standIns = new ArrayList<>();
		for (ToolReference reference : type.getTools() != null ? type.getTools() : List.<ToolReference>of()) {
			if (reference == null || reference.getName() == null || reference.getName().isBlank()) {
				LOGGER.warn("Library type:" + type.getId() + " of source:" + library.getId()
						+ " declares a tool with no name: it is left out");
				continue;
			}
			standIns.add(new LibraryToolStandIn(reference));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Library type:" + type.getId() + " of source:" + library.getId() + " exports " + standIns.size()
					+ " tool(s) as:" + getId());
		}
		return standIns;
	}

	/**
	 * A tool of the library type, standing in for the one of whichever instance the
	 * run holds.
	 */
	final class LibraryToolStandIn implements ToolCallback {
		private final ToolDefinition definition;

		LibraryToolStandIn(ToolReference reference) {
			final boolean declared = reference.getInputSchema() != null && !reference.getInputSchema().isBlank();
			if (!declared) {
				LOGGER.warn("Tool:" + reference.getName() + " of library type:" + type.getId()
						+ " declares no input schema: it is offered as taking nothing");
			}
			this.definition = ToolDefinition.builder().name(reference.getName())
					.description(reference.getDescription() != null ? reference.getDescription() : reference.getName())
					.inputSchema(declared ? reference.getInputSchema() : NO_INPUT_SCHEMA).build();
		}

		@Override
		public ToolDefinition getToolDefinition() {
			return definition;
		}

		@Override
		public String call(String toolInput) {
			return call(toolInput, null);
		}

		@Override
		public String call(String toolInput, ToolContext toolContext) {
			final String name = definition.name();
			final AIToolsAndResourcesLibraryInstance instance = ToolCallbackDeclarationUtil.libraryInstance(toolContext,
					type.getId());
			if (instance == null) {
				LOGGER.warn("Tool:" + name + " of library type:" + type.getId()
						+ " was called by a run that holds no instance of that library: not available here");
				return "The library \"" + getDescription() + "\" is not available in this run: there is no instance of"
						+ " it to work on, so its tools cannot be used here.";
			}
			final ToolCallback bound = boundTool(instance, name);
			if (bound == null) {
				LOGGER.error("Instance:" + instance.getId() + " of library type:" + type.getId()
						+ " has no tool named:" + name + " although its type declares one");
				return "The tool " + name + " is declared by the library but its instance does not provide it.";
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + name + " of library type:" + type.getId() + " is called on instance:"
						+ instance.getId());
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<LIBRARY_TOOL_INPUT tool=" + name + " instance=" + instance.getId() + ">");
				LOGGER.trace(toolInput);
				LOGGER.trace("</LIBRARY_TOOL_INPUT>");
			}
			final String result = toolContext != null ? bound.call(toolInput, toolContext) : bound.call(toolInput);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<LIBRARY_TOOL_RESULT tool=" + name + " instance=" + instance.getId() + ">");
				LOGGER.trace(result);
				LOGGER.trace("</LIBRARY_TOOL_RESULT>");
			}
			return result;
		}

		/** The instance's own tool of this name, from the library source; null when it has none. */
		private ToolCallback boundTool(AIToolsAndResourcesLibraryInstance instance, String name) {
			final List<ToolCallback> bound = library.getToolCallbacks(instance);
			for (ToolCallback callback : bound != null ? bound : List.<ToolCallback>of()) {
				if (callback != null && callback.getToolDefinition() != null
						&& name.equals(callback.getToolDefinition().name())) {
					return callback;
				}
			}
			return null;
		}
	}
}
