/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.service.AIToolsLibraryTypeToolSource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;

/**
 * Registers a tool source per library type once the context is up: what makes
 * a library type something an agent configuration can name among the tool
 * sources, and its tools something the registry knows by name.
 * <p>
 * A tool of a type bearing the name of a tool some other source exports already
 * is logged as an error: the registry resolves a tool by its name, and which of
 * two tools of one name answers would be a toss-up.
 */
@Service
public class AIToolsLibraryTypeToolSourcesAssembler implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AIToolsLibraryTypeToolSourcesAssembler.class);

	private final IGAIToolsAndResourcesLibrarySourceRepositoryPattern libraries;
	private final IGToolCallbackSourceRepositoryPattern tools;

	public AIToolsLibraryTypeToolSourcesAssembler(IGAIToolsAndResourcesLibrarySourceRepositoryPattern libraries,
			IGToolCallbackSourceRepositoryPattern tools) {
		this.libraries = libraries;
		this.tools = tools;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		assemble();
	}

	/** Registers the sources of the library types not registered yet; how many were. */
	public int assemble() {
		final List<IGAIToolsAndResourcesLibrarySource> sources = libraries.getImplementations();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin assemble() " + (sources != null ? sources.size() : 0) + " library source(s)");
		}
		int registered = 0;
		for (IGAIToolsAndResourcesLibrarySource library : sources != null ? sources
				: List.<IGAIToolsAndResourcesLibrarySource>of()) {
			if (library == null) {
				continue;
			}
			final List<AIToolsAndResourcesLibraryType> types;
			try {
				types = library.getManagedTypes();
			} catch (RuntimeException unreadable) {
				LOGGER.error("The library types of source:" + library.getId() + " could not be read", unreadable);
				continue;
			}
			for (AIToolsAndResourcesLibraryType type : types != null ? types
					: List.<AIToolsAndResourcesLibraryType>of()) {
				if (type == null || type.getId() == null || type.getId().isBlank()) {
					LOGGER.error("Library source:" + library.getId() + " manages a type with no id: not registered");
					continue;
				}
				final String sourceId = AIToolsLibraryTypeToolSource.sourceIdOf(type.getId());
				if (tools.findByCode(sourceId) != null) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Library type:" + type.getId() + " is registered already as:" + sourceId);
					}
					continue;
				}
				final AIToolsLibraryTypeToolSource source = new AIToolsLibraryTypeToolSource(type, library);
				warnOfTakenNames(source);
				tools.addImplementation(source);
				registered++;
				LOGGER.info("Library type:" + type.getId() + " (" + type.getDescription() + ") of source:"
						+ library.getId() + " is registered as the tool source:" + sourceId + " with "
						+ source.getToolCallbacks().size() + " tool(s)");
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End assemble() registered " + registered + " library type source(s)");
		}
		return registered;
	}

	/** Says so of every tool of the type whose name another registered source exports already. */
	private void warnOfTakenNames(AIToolsLibraryTypeToolSource source) {
		final Set<String> taken = new HashSet<>();
		for (IGToolCallbackSource other : tools.getImplementations()) {
			if (other == null || other == source) {
				continue;
			}
			try {
				for (ToolCallback callback : other.getToolCallbacks() != null ? other.getToolCallbacks()
						: List.<ToolCallback>of()) {
					if (callback != null && callback.getToolDefinition() != null) {
						taken.add(callback.getToolDefinition().name());
					}
				}
			} catch (RuntimeException unreadable) {
				LOGGER.warn("The tools of source:" + other.getId() + " could not be read: " + unreadable);
			}
		}
		for (ToolCallback callback : source.getToolCallbacks()) {
			if (taken.contains(callback.getToolDefinition().name())) {
				LOGGER.error("Tool:" + callback.getToolDefinition().name() + " of library type:"
						+ source.getType().getId() + " bears the name of a tool another source exports already:"
						+ " the registry resolves tools by name, and which of the two answers is a toss-up");
			}
		}
	}
}
