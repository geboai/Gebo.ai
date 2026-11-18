/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.client.rest.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.ai.files.IGAILibraryFilesService;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.architecture.ai.model.AIResourceContent;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.service.IGAIToolsLibraryInstancesService;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatWrittenFile;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.security.services.IGSecurityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

/**
 * The libraries of tools and resources a conversation holds, for the people
 * reading it: what the agents have at their disposal - a working directory, a
 * sandbox - and the resources of it they can take away as a file. Type
 * agnostic: which resources can be downloaded is declared by their types, and
 * the file comes from the library's own source.
 */
@PreAuthorize("hasAnyRole('USER','ADMIN','APPLICATION')")
@RestController
@RequestMapping(path = "api/users/GeboChatLibrariesController")
@AllArgsConstructor
public class GeboChatLibrariesController {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboChatLibrariesController.class);

	private final IGAIToolsLibraryInstancesService libraries;
	private final GUserChatSessionRepository conversations;
	private final IGSecurityService securityService;
	private final ObjectProvider<IGAILibraryFilesService> files;

	/** The conversation, once the current user may read it. */
	private GUserChatSession readable(String chatCode) {
		final GUserChatSession conversation = conversations.findById(chatCode).orElse(null);
		if (conversation == null) {
			throw new IllegalArgumentException("There is no conversation: " + chatCode);
		}
		securityService.checkBeingCreatorOrGranted(conversation, AclGrantType.READ);
		return conversation;
	}

	/** The library instances the conversation holds, with their resources. */
	@GetMapping(value = "getChatLibraryInstance", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AIToolsAndResourcesLibraryInstance> getChatLibraryInstance(@RequestParam("chatCode") String chatCode) {
		readable(chatCode);
		final List<AIToolsAndResourcesLibraryInstance> held = new ArrayList<>(libraries.heldBy(chatCode).values());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getChatLibraryInstance(" + chatCode + ") -> " + held.size() + " instance(s)");
		}
		return held;
	}

	/**
	 * The files the tools of the conversation wrote, each once, with the requests
	 * that wrote them, whether the conversation still holds it as a resource
	 * that can be downloaded on its own, and how it is now against what the
	 * tools left (as the agents are told of it).
	 */
	@GetMapping(value = "getChatWrittenFiles", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ChatWrittenFile> getChatWrittenFiles(@RequestParam("chatCode") String chatCode) {
		final GUserChatSession conversation = readable(chatCode);
		final List<ChatWrittenFile> written = ChatWrittenFile.markDownloadable(ChatWrittenFile.of(conversation),
				libraries.heldBy(chatCode).values());
		if (!written.isEmpty()) {
			ChatWrittenFile.markState(written, locationsOf(chatCode));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getChatWrittenFiles(" + chatCode + ") -> " + written.size() + " file(s)");
		}
		return written;
	}

	/**
	 * The files locations the conversation holds, by id, read once: a location
	 * no longer held is not there, so its files are gone; when they cannot be
	 * read at all, every file's state is unknown.
	 */
	private Function<String, IGFilesLocation> locationsOf(String chatCode) {
		final IGAILibraryFilesService service = files.getIfAvailable();
		if (service == null) {
			return id -> {
				throw new IllegalStateException("No library files service");
			};
		}
		try {
			final Map<String, IGFilesLocation> byId = new HashMap<>();
			for (IGFilesLocation location : service.locationsOf(chatCode)) {
				if (location != null && location.getId() != null) {
					byId.putIfAbsent(location.getId(), location);
				}
			}
			return byId::get;
		} catch (RuntimeException unreadable) {
			LOGGER.warn("The files locations of conversation " + chatCode + " could not be read: "
					+ unreadable.getMessage());
			return id -> {
				throw unreadable;
			};
		}
	}

	/**
	 * A resource of a library instance the conversation holds, as a file: a
	 * working directory as a zip, a document as itself. Only the resources whose
	 * type says they can be downloaded; anything else is 404.
	 */
	@GetMapping(value = "downloadChatLibraryResource")
	public void downloadChatLibraryResource(@RequestParam("chatCode") String chatCode,
			@RequestParam("libraryTypeId") String libraryTypeId, @RequestParam("resourceId") String resourceId,
			HttpServletResponse response) throws IOException {
		readable(chatCode);
		final AIResourceContent content = libraries.download(chatCode, libraryTypeId, resourceId);
		if (content == null || content.content() == null) {
			LOGGER.warn("downloadChatLibraryResource(" + chatCode + ", " + libraryTypeId + ", " + resourceId
					+ "): nothing to download");
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "There is nothing to download");
			return;
		}
		final String fileName = content.fileName() != null ? content.fileName().replace("\"", "") : "resource";
		response.setContentType(
				content.contentType() != null ? content.contentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		// a page served from another origin (the development server) reads the file
		// name only when the header is exposed to it
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
		long written = 0;
		try (InputStream in = content.content()) {
			written = in.transferTo(response.getOutputStream());
		}
		response.flushBuffer();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("downloadChatLibraryResource(" + chatCode + ", " + libraryTypeId + ", " + resourceId + ") -> "
					+ fileName + " (" + content.contentType() + ", " + written + " byte(s))");
		}
	}
}
