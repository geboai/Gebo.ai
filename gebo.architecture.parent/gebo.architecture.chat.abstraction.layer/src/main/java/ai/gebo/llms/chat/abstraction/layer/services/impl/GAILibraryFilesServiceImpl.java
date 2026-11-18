/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.files.IGAILibraryFilesService;
import ai.gebo.architecture.ai.files.IGAILibraryFilesSource;
import ai.gebo.architecture.ai.files.IGConversationFilesProvider;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.architecture.ai.service.IGAIToolsLibraryInstancesService;

/**
 * The files locations of a conversation: one for each library instance it holds
 * whose source holds files, then the ones its files providers give it.
 */
@Service
public class GAILibraryFilesServiceImpl implements IGAILibraryFilesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAILibraryFilesServiceImpl.class);

	private final IGAIToolsLibraryInstancesService instances;
	private final IGAIToolsAndResourcesLibrarySourceRepositoryPattern libraries;
	private final ObjectProvider<IGConversationFilesProvider> providers;

	public GAILibraryFilesServiceImpl(IGAIToolsLibraryInstancesService instances,
			IGAIToolsAndResourcesLibrarySourceRepositoryPattern libraries,
			ObjectProvider<IGConversationFilesProvider> providers) {
		this.instances = instances;
		this.libraries = libraries;
		this.providers = providers;
	}

	/** The source managing each type, the first one when two do. */
	private Map<String, IGAIToolsAndResourcesLibrarySource> sourcesByType() {
		final Map<String, IGAIToolsAndResourcesLibrarySource> byType = new LinkedHashMap<>();
		final List<IGAIToolsAndResourcesLibrarySource> sources = libraries != null ? libraries.getImplementations()
				: null;
		for (IGAIToolsAndResourcesLibrarySource source : sources != null ? sources
				: List.<IGAIToolsAndResourcesLibrarySource>of()) {
			if (source == null) {
				continue;
			}
			try {
				for (AIToolsAndResourcesLibraryType type : source.getManagedTypes() != null ? source.getManagedTypes()
						: List.<AIToolsAndResourcesLibraryType>of()) {
					if (type != null && type.getId() != null) {
						byType.putIfAbsent(type.getId(), source);
					}
				}
			} catch (RuntimeException unreadable) {
				LOGGER.warn("The library types of source:" + source.getId() + " could not be read: " + unreadable);
			}
		}
		return byType;
	}

	@Override
	public List<IGFilesLocation> locationsOf(String chatSessionCode) {
		final List<IGFilesLocation> locations = new ArrayList<>();
		if (chatSessionCode == null || chatSessionCode.isBlank()) {
			return locations;
		}
		final Map<String, AIToolsAndResourcesLibraryInstance> held = instances.heldBy(chatSessionCode);
		if (held != null && !held.isEmpty()) {
			final Map<String, IGAIToolsAndResourcesLibrarySource> sources = sourcesByType();
			for (Map.Entry<String, AIToolsAndResourcesLibraryInstance> entry : held.entrySet()) {
				if (!(sources.get(entry.getKey()) instanceof IGAILibraryFilesSource files) || entry.getValue() == null) {
					continue;
				}
				try {
					final IGFilesLocation location = files.filesOf(entry.getValue());
					if (location != null) {
						locations.add(location);
					}
				} catch (RuntimeException unreadable) {
					LOGGER.warn("The files of instance:" + entry.getValue().getId() + " of library type:" + entry.getKey()
							+ " could not be reached: " + unreadable);
				}
			}
		}
		providers.orderedStream().forEach(provider -> {
			try {
				final IGFilesLocation location = provider.filesOf(chatSessionCode);
				if (location != null) {
					locations.add(location);
				}
			} catch (RuntimeException unreadable) {
				LOGGER.warn("The files of provider:" + provider.getId() + " for conversation:" + chatSessionCode
						+ " could not be reached: " + unreadable);
			}
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("locationsOf(" + chatSessionCode + ") -> "
					+ locations.stream().map(IGFilesLocation::getId).toList());
		}
		return locations;
	}

	@Override
	public IGFilesLocation locationOf(String chatSessionCode, String locationId) {
		final List<IGFilesLocation> locations = locationsOf(chatSessionCode);
		if (locationId != null) {
			for (IGFilesLocation location : locations) {
				if (locationId.trim().equals(location.getId())) {
					return location;
				}
			}
		}
		throw new IllegalArgumentException("There is no files location " + locationId + " in this conversation; there are "
				+ locations.stream().map(IGFilesLocation::getId).toList());
	}
}
