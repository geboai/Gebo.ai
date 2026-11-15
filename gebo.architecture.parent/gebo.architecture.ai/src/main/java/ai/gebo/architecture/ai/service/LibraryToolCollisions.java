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
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.LibraryToolCollision;
import ai.gebo.architecture.ai.model.ToolReference;

/**
 * Finds the collisions of the tools of the libraries an agent works with, among them
 * and against the tools it mounts from the tool sources. It reads declarations only:
 * no run, no instance and no call to a tool source is needed.
 */
public final class LibraryToolCollisions {
	/** A tool an agent mounts from a tool source. */
	public record MountedSourceTool(String name, String sourceId, String sourceDescription) {
	}

	private LibraryToolCollisions() {
	}

	/**
	 * @param libraries the library types of the agent, in the order they are bound:
	 *                  between two of them the first one's tool stays
	 * @param mounted   the tools the agent mounts from the tool sources
	 * @return the collisions, by library and tool order; empty when there are none
	 */
	public static List<LibraryToolCollision> find(List<AIToolsAndResourcesLibraryType> libraries,
			Collection<MountedSourceTool> mounted) {
		final List<LibraryToolCollision> collisions = new ArrayList<>();
		if (libraries == null || libraries.isEmpty()) {
			return collisions;
		}
		final Map<String, MountedSourceTool> fromSources = new LinkedHashMap<>();
		for (MountedSourceTool tool : mounted != null ? mounted : List.<MountedSourceTool>of()) {
			if (tool != null && tool.name() != null) {
				fromSources.putIfAbsent(tool.name(), tool);
			}
		}
		final Map<String, AIToolsAndResourcesLibraryType> firstLibraryOfTool = new LinkedHashMap<>();
		final Set<String> seenTypes = new HashSet<>();
		for (AIToolsAndResourcesLibraryType library : libraries) {
			if (library == null || library.getTools() == null || !seenTypes.add(library.getId())) {
				continue;
			}
			for (ToolReference reference : library.getTools()) {
				if (reference == null || reference.getName() == null) {
					continue;
				}
				final String name = reference.getName();
				final AIToolsAndResourcesLibraryType first = firstLibraryOfTool.putIfAbsent(name, library);
				if (first == null) {
					final MountedSourceTool other = fromSources.get(name);
					if (other != null) {
						collisions.add(new LibraryToolCollision(name, library.getId(), library.getDescription(),
								other.sourceId(), null, other.sourceDescription()));
					}
				} else if (first != library) {
					collisions.add(new LibraryToolCollision(name, first.getId(), first.getDescription(), null,
							library.getId(), library.getDescription()));
				}
			}
		}
		return collisions;
	}
}
