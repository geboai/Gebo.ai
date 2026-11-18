/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;

/**
 * The library types an agent mounts, the one rule every reader of an agent's
 * libraries applies: the run, what an agent is shown to mount, the files a chat
 * accepts for its agents' tools and the startup check of the tool names.
 */
public final class GAgentLibraryTypes {

	private GAgentLibraryTypes() {
	}

	/**
	 * The library types an agent mounts, each once, in this order: the ones its
	 * configuration names; when it mounts every library, every installed one but the
	 * ones mounted only when asked and the ones it leaves out; the ones its service
	 * implies. Leaving a library out changes only what mounting every library adds:
	 * a library named or implied is mounted.
	 *
	 * @param agentConfig the agent configuration, null for none
	 * @param implied     the library types the agent's service implies, null for none
	 * @param installed   the library types the installation has, null for none: read
	 *                    only when the agent mounts every library
	 */
	public static List<String> mountedBy(GAgentConfig agentConfig, Collection<String> implied,
			Collection<AIToolsAndResourcesLibraryType> installed) {
		final Set<String> ids = new LinkedHashSet<>();
		if (agentConfig != null) {
			addAll(ids, agentConfig.getLibraryTypeIds());
			if (Boolean.TRUE.equals(agentConfig.getSubscribeAllLibraries()) && installed != null) {
				final Set<String> excluded = new HashSet<>();
				addAll(excluded, agentConfig.getExcludedLibraryTypeIds());
				for (AIToolsAndResourcesLibraryType type : installed) {
					if (type != null && type.getId() != null && !type.getId().isBlank()
							&& !type.isMountedOnlyWhenAsked() && !excluded.contains(type.getId())) {
						ids.add(type.getId());
					}
				}
			}
		}
		addAll(ids, implied);
		return List.copyOf(ids);
	}

	/**
	 * Whether an agent may mount a library at all: it names one, mounts every library
	 * or its service implies one. False tells for sure that it mounts none.
	 */
	public static boolean mayMountAny(GAgentConfig agentConfig, Collection<String> implied) {
		return (agentConfig != null && (Boolean.TRUE.equals(agentConfig.getSubscribeAllLibraries())
				|| (agentConfig.getLibraryTypeIds() != null && !agentConfig.getLibraryTypeIds().isEmpty())))
				|| (implied != null && !implied.isEmpty());
	}

	private static void addAll(Set<String> ids, Collection<String> values) {
		if (values == null) {
			return;
		}
		for (String id : values) {
			if (id != null && !id.isBlank()) {
				ids.add(id);
			}
		}
	}
}
