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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GChatUploadFileTypes;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatAgentsToolsDescriber;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatUploadFileTypesService;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * The files a chat accepts as uploads: the content handlers' ones, then the ones the
 * libraries of the chat's agents work on. The agents of a chat are the ones of the
 * network it would run on, as {@link IGChatAgentsToolsDescriber#libraryTypeIds} tells;
 * without the agents layer there are none.
 */
@Service
public class GChatUploadFileTypesServiceImpl implements IGChatUploadFileTypesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GChatUploadFileTypesServiceImpl.class);
	private final ObjectProvider<IGChatAgentsToolsDescriber> agentsToolsDescriber;
	private final ObjectProvider<IGAIToolsAndResourcesLibrarySourceRepositoryPattern> librarySources;
	private final IGIngestionHandlerConfigDao contentHandlers;
	private final GUserChatSessionRepository sessions;
	private final ChatProfilesRepository profiles;
	private final IGSecurityService securityService;

	public GChatUploadFileTypesServiceImpl(ObjectProvider<IGChatAgentsToolsDescriber> agentsToolsDescriber,
			ObjectProvider<IGAIToolsAndResourcesLibrarySourceRepositoryPattern> librarySources,
			IGIngestionHandlerConfigDao contentHandlers, GUserChatSessionRepository sessions,
			ChatProfilesRepository profiles, IGSecurityService securityService) {
		this.agentsToolsDescriber = agentsToolsDescriber;
		this.librarySources = librarySources;
		this.contentHandlers = contentHandlers;
		this.sessions = sessions;
		this.profiles = profiles;
		this.securityService = securityService;
	}

	@Override
	public Set<String> toolsFileTypes(GChatProfileConfiguration profile) {
		final IGChatAgentsToolsDescriber describer = agentsToolsDescriber.getIfAvailable();
		final IGAIToolsAndResourcesLibrarySourceRepositoryPattern sources = librarySources.getIfAvailable();
		if (describer == null || sources == null) {
			return Set.of();
		}
		final List<String> libraryTypeIds = describer.libraryTypeIds(profile);
		if (libraryTypeIds == null || libraryTypeIds.isEmpty()) {
			return Set.of();
		}
		final Set<String> extensions = new LinkedHashSet<>();
		for (IGAIToolsAndResourcesLibrarySource source : sources.getImplementations()) {
			try {
				final List<AIToolsAndResourcesLibraryType> types = source.getManagedTypes();
				if (types == null) {
					continue;
				}
				for (AIToolsAndResourcesLibraryType type : types) {
					if (type != null && libraryTypeIds.contains(type.getId()) && type.getUploadFileTypes() != null) {
						type.getUploadFileTypes().stream().map(GChatUploadFileTypesServiceImpl::extension)
								.filter(x -> x != null).forEach(extensions::add);
					}
				}
			} catch (RuntimeException unreadable) {
				LOGGER.warn("The library types of source:" + source.getId() + " could not be read: " + unreadable);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("toolsFileTypes(...) profile:" + (profile != null ? profile.getCode() : null) + " libraries:"
					+ libraryTypeIds + " extensions:" + extensions);
		}
		return extensions;
	}

	@Override
	public Set<String> toolsFileTypesOfSession(String userSessionCode) {
		final Optional<GUserChatSession> session = userSessionCode != null ? sessions.findById(userSessionCode)
				: Optional.empty();
		if (session.isEmpty()) {
			return Set.of();
		}
		return toolsFileTypes(profileOf(session.get().getChatProfileCode()));
	}

	@Override
	public GChatUploadFileTypes acceptedFileTypes(String userSessionCode, String chatProfileCode) {
		final Set<String> tools;
		if (userSessionCode != null && !userSessionCode.isBlank()) {
			final Optional<GUserChatSession> session = sessions.findById(userSessionCode);
			if (session.isEmpty()) {
				throw new IllegalArgumentException("The session " + userSessionCode + " does not exist");
			}
			securityService.checkBeingCreatorOrGranted(session.get(), AclGrantType.WRITE);
			tools = toolsFileTypes(profileOf(session.get().getChatProfileCode()));
		} else {
			tools = toolsFileTypes(profileOf(chatProfileCode));
		}
		final Set<String> chat = chatFileTypes();
		final List<String> toolsOnly = new ArrayList<>();
		for (String extension : tools) {
			if (!chat.contains(extension)) {
				toolsOnly.add(extension);
			}
		}
		return new GChatUploadFileTypes(new ArrayList<>(chat), toolsOnly);
	}

	/** The extensions the content handlers read, with the dot, lower case. */
	private Set<String> chatFileTypes() {
		final Set<String> extensions = new LinkedHashSet<>();
		for (IngestionHandlerConfig handler : contentHandlers.getConfigurations()) {
			if (handler.getFileTypes() == null) {
				continue;
			}
			for (IngestionFileType type : handler.getFileTypes()) {
				if (type.getExtensions() != null) {
					type.getExtensions().stream().map(GChatUploadFileTypesServiceImpl::extension).filter(x -> x != null)
							.forEach(extensions::add);
				}
			}
		}
		return extensions;
	}

	private GChatProfileConfiguration profileOf(String chatProfileCode) {
		return chatProfileCode != null && !chatProfileCode.isBlank() ? profiles.findById(chatProfileCode).orElse(null)
				: null;
	}

	/** An extension as the uploads keep it: with the dot, lower case; null for a blank one. */
	static String extension(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		final String trimmed = value.trim().toLowerCase(Locale.ROOT);
		return trimmed.startsWith(".") ? trimmed : "." + trimmed;
	}
}
