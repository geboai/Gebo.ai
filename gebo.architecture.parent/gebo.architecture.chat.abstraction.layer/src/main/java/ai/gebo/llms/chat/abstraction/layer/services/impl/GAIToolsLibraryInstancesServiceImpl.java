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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.AIResource;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.architecture.ai.service.IGAIToolsLibraryInstancesService;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import lombok.AllArgsConstructor;

/**
 * The library instances a conversation holds, recorded on the conversation
 * itself ({@link GUserChatSession#getLibraryInstanceIds()}) and reclaimed from
 * their sources by whoever enters the conversation to work.
 */
@Service
@AllArgsConstructor
public class GAIToolsLibraryInstancesServiceImpl implements IGAIToolsLibraryInstancesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAIToolsLibraryInstancesServiceImpl.class);

	private final IGAIToolsAndResourcesLibrarySourceRepositoryPattern libraries;
	private final GUserChatSessionRepository conversations;

	/** A managed type and the source managing it. */
	private record Managed(AIToolsAndResourcesLibraryType type, IGAIToolsAndResourcesLibrarySource source) {
	}

	/** Every managed type, by id; a type managed twice is the first one's, and logged. */
	private Map<String, Managed> managed() {
		final Map<String, Managed> byId = new LinkedHashMap<>();
		final List<IGAIToolsAndResourcesLibrarySource> sources = libraries != null ? libraries.getImplementations()
				: null;
		for (IGAIToolsAndResourcesLibrarySource source : sources != null ? sources
				: List.<IGAIToolsAndResourcesLibrarySource>of()) {
			if (source == null) {
				continue;
			}
			List<AIToolsAndResourcesLibraryType> types = null;
			try {
				types = source.getManagedTypes();
			} catch (RuntimeException unreadable) {
				LOGGER.warn("The library types of source:" + source.getId() + " could not be read: " + unreadable);
			}
			for (AIToolsAndResourcesLibraryType type : types != null ? types
					: List.<AIToolsAndResourcesLibraryType>of()) {
				if (type == null || type.getId() == null) {
					continue;
				}
				if (byId.putIfAbsent(type.getId(), new Managed(type, source)) != null) {
					LOGGER.warn("Library type:" + type.getId() + " is managed by source:" + source.getId()
							+ " and by another one already: the first one's is used");
				}
			}
		}
		return byId;
	}

	@Override
	public Map<String, AIToolsAndResourcesLibraryInstance> reclaim(String chatSessionCode,
			Collection<String> libraryTypeIds) {
		final Map<String, AIToolsAndResourcesLibraryInstance> held = new LinkedHashMap<>();
		if (chatSessionCode == null || libraryTypeIds == null || libraryTypeIds.isEmpty()) {
			return held;
		}
		final Optional<GUserChatSession> found = conversations.findById(chatSessionCode);
		if (found.isEmpty()) {
			LOGGER.warn("There is no conversation:" + chatSessionCode + " to hold library instances");
			return held;
		}
		final GUserChatSession conversation = found.get();
		final Map<String, Managed> managed = managed();
		final Map<String, String> ids = new LinkedHashMap<>(
				conversation.getLibraryInstanceIds() != null ? conversation.getLibraryInstanceIds() : Map.of());
		boolean changed = false;
		for (String typeId : libraryTypeIds) {
			if (typeId == null || typeId.isBlank() || held.containsKey(typeId)) {
				continue;
			}
			final Managed library = managed.get(typeId);
			if (library == null) {
				LOGGER.warn("Conversation:" + chatSessionCode + " cannot hold an instance of library type:" + typeId
						+ ": this installation does not have it");
				continue;
			}
			AIToolsAndResourcesLibraryInstance instance = null;
			final String had = ids.get(typeId);
			if (had != null) {
				instance = library.source().getInstanceById(had, chatSessionCode);
				if (instance == null) {
					LOGGER.warn("Conversation:" + chatSessionCode + " held instance:" + had + " of library type:"
							+ typeId + " which is gone: a new one is created");
				}
			}
			if (instance == null) {
				instance = library.source().createInstance(library.type(),
						owners(chatSessionCode, conversation.getUsername()));
				if (instance == null) {
					LOGGER.error("Source:" + library.source().getId() + " created no instance of library type:"
							+ typeId + " for conversation:" + chatSessionCode);
					continue;
				}
				ids.put(typeId, instance.getId());
				changed = true;
				LOGGER.info("Conversation:" + chatSessionCode + " now holds instance:" + instance.getId()
						+ " of library type:" + typeId);
			} else if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Conversation:" + chatSessionCode + " goes on with instance:" + instance.getId()
						+ " of library type:" + typeId);
			}
			held.put(typeId, instance);
		}
		if (changed) {
			conversation.setLibraryInstanceIds(ids);
			conversations.save(conversation);
		}
		return held;
	}

	/** Whose an instance is: the conversation's, and the person's whose conversation it is. */
	private static List<String> owners(String chatSessionCode, String username) {
		final List<String> owners = new ArrayList<>();
		owners.add(chatSessionCode);
		if (username != null && !username.isBlank()) {
			owners.add(username);
		}
		return owners;
	}

	@Override
	public List<String> describe(Map<String, AIToolsAndResourcesLibraryInstance> instances) {
		final List<String> described = new ArrayList<>();
		if (instances == null || instances.isEmpty()) {
			return described;
		}
		final StringBuilder written = new StringBuilder(
				"The libraries of tools and resources at your disposal in this conversation, with the resources each holds:");
		for (AIToolsAndResourcesLibraryInstance instance : instances.values()) {
			if (instance == null) {
				continue;
			}
			written.append("\n- ").append(instance.getLibraryType() != null ? described(instance.getLibraryType())
					: instance.getId());
			if (instance.getDescription() != null && !instance.getDescription().isBlank()) {
				written.append(": ").append(instance.getDescription().trim());
			}
			for (AIResource resource : instance.getResources() != null ? instance.getResources()
					: List.<AIResource>of()) {
				if (resource == null) {
					continue;
				}
				written.append("\n  - ").append(resource.getId());
				if (resource.getResourceType() != null && resource.getResourceType().getDescription() != null) {
					written.append(" (").append(resource.getResourceType().getDescription()).append(")");
				}
				if (resource.getDescription() != null && !resource.getDescription().isBlank()) {
					written.append(": ").append(resource.getDescription().trim());
				}
			}
		}
		described.add(written.toString());
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<LIBRARIES>");
			LOGGER.trace(written.toString());
			LOGGER.trace("</LIBRARIES>");
		}
		return described;
	}

	@Override
	public void dropHeldBy(String chatSessionCode) {
		if (chatSessionCode == null) {
			return;
		}
		final GUserChatSession conversation = conversations.findById(chatSessionCode).orElse(null);
		final Map<String, String> ids = conversation != null ? conversation.getLibraryInstanceIds() : null;
		if (ids == null || ids.isEmpty()) {
			return;
		}
		final Map<String, Managed> managed = managed();
		for (Map.Entry<String, String> held : ids.entrySet()) {
			final Managed library = managed.get(held.getKey());
			if (library == null) {
				LOGGER.warn("Conversation:" + chatSessionCode + " held instance:" + held.getValue()
						+ " of library type:" + held.getKey() + " which nobody manages any more: it cannot be dropped");
				continue;
			}
			try {
				final AIToolsAndResourcesLibraryInstance instance = library.source().getInstanceById(held.getValue(),
						chatSessionCode);
				if (instance == null) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Instance:" + held.getValue() + " of library type:" + held.getKey()
								+ " is gone already");
					}
					continue;
				}
				library.source().dropInstance(instance);
				LOGGER.info("Conversation:" + chatSessionCode + " dropped instance:" + held.getValue()
						+ " of library type:" + held.getKey());
			} catch (RuntimeException notDropped) {
				// the conversation goes anyway: what could not be released is in the log
				LOGGER.error("Instance:" + held.getValue() + " of library type:" + held.getKey()
						+ " held by conversation:" + chatSessionCode + " could not be dropped", notDropped);
			}
		}
		conversation.setLibraryInstanceIds(null);
		conversations.save(conversation);
	}

	private static String described(AIToolsAndResourcesLibraryType type) {
		return type.getDescription() != null && !type.getDescription().isBlank() ? type.getDescription()
				: type.getId();
	}
}
