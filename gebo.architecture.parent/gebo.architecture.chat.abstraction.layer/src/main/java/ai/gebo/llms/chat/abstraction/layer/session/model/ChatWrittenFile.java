/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.session.model;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import ai.gebo.architecture.ai.files.GConversationFile;
import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.architecture.ai.model.AIResource;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A file the tools of a conversation wrote, for the people reading it: where
 * it is, the requests that wrote it and the version the last of them left.
 * Built from the answers of the conversation, each keeping the files its
 * request wrote; whether the file can still be taken away is said by who
 * serves it, from what the conversation holds now.
 */
@Data
@NoArgsConstructor
public class ChatWrittenFile {
	/** The files location the file is in: a library type id, as the tools name it. */
	private String location = null;
	/** The file's path in its location, with '/' separators. */
	private String path = null;
	/** The version the last request writing it left; null when not known. */
	private String version = null;
	/** The requests that wrote it, numbered from 1 as the user sees them, in order. */
	private List<Integer> requests = new ArrayList<>();
	/**
	 * Whether the conversation still holds it as a resource that can be
	 * downloaded on its own; null when not asked.
	 */
	private Boolean downloadable = null;
	/** How the file is now, against the version the tools left; null when not asked. */
	private State state = null;

	/** How a written file is now, against what the tools left. */
	public static enum State {
		/** There, at the version the tools left, or with no version to compare. */
		AS_WRITTEN,
		/** There, at another version: something else than the tools changed it since. */
		CHANGED,
		/** No longer there, or the conversation no longer holds its location. */
		GONE,
		/** Its location could not be read. */
		UNKNOWN
	}

	/** The request that wrote it last, numbered from 1; null when none. */
	public Integer lastRequest() {
		return requests.isEmpty() ? null : requests.get(requests.size() - 1);
	}

	/**
	 * The files the tools of the conversation wrote, each once, in the order
	 * they were first written, with every request that wrote them and the
	 * version the last one left.
	 */
	public static List<ChatWrittenFile> of(GUserChatSession conversation) {
		final Map<String, ChatWrittenFile> files = new LinkedHashMap<>();
		final List<ChatInteractions> interactions = conversation != null ? conversation.getInteractions() : null;
		if (interactions == null) {
			return new ArrayList<>();
		}
		for (int index = 0; index < interactions.size(); index++) {
			final ChatInteractions interaction = interactions.get(index);
			final List<GConversationFile> written = interaction != null && interaction.getResponse() != null
					? interaction.getResponse().getWrittenFiles()
					: null;
			if (written == null) {
				continue;
			}
			final int request = index + 1;
			for (GConversationFile file : written) {
				if (file == null || file.getLocation() == null || file.getPath() == null) {
					continue;
				}
				final ChatWrittenFile entry = files.computeIfAbsent(file.key(), key -> {
					final ChatWrittenFile created = new ChatWrittenFile();
					created.setLocation(file.getLocation());
					created.setPath(file.getPath());
					return created;
				});
				if (!entry.requests.contains(request)) {
					entry.requests.add(request);
				}
				entry.setVersion(file.getVersion());
			}
		}
		return new ArrayList<>(files.values());
	}

	/**
	 * Says of each file whether the conversation still holds it as a resource of
	 * its own that can be downloaded: a resource whose id is the file's path, in
	 * the library instance whose type is the file's location. A file of a
	 * library holding its files as one resource (a working directory, as a
	 * whole) is not one, nor a file no longer there.
	 */
	public static List<ChatWrittenFile> markDownloadable(List<ChatWrittenFile> files,
			Collection<AIToolsAndResourcesLibraryInstance> held) {
		final Set<String> downloadable = new HashSet<>();
		if (held != null) {
			for (AIToolsAndResourcesLibraryInstance instance : held) {
				if (instance == null || instance.getLibraryType() == null || instance.getResources() == null) {
					continue;
				}
				for (AIResource resource : instance.getResources()) {
					if (resource != null && resource.getId() != null && resource.getResourceType() != null
							&& resource.getResourceType().isDownloadable()) {
						downloadable.add(instance.getLibraryType().getId() + ":" + slashed(resource.getId()));
					}
				}
			}
		}
		for (ChatWrittenFile file : files) {
			file.setDownloadable(downloadable.contains(file.getLocation() + ":" + slashed(file.getPath())));
		}
		return files;
	}

	/**
	 * Says of each file how it is now, the way the agents are told of it: the
	 * location (by its id) describes the file at its path, and its version is
	 * compared with the one the tools left. A location the conversation no
	 * longer holds, or a file not there, is gone; a location that fails to
	 * describe it is unknown.
	 */
	public static List<ChatWrittenFile> markState(List<ChatWrittenFile> files,
			Function<String, IGFilesLocation> locations) {
		for (ChatWrittenFile file : files) {
			file.setState(stateOf(file, locations));
		}
		return files;
	}

	private static State stateOf(ChatWrittenFile file, Function<String, IGFilesLocation> locations) {
		final IGFilesLocation location;
		try {
			location = locations != null ? locations.apply(file.getLocation()) : null;
		} catch (RuntimeException unreadable) {
			return State.UNKNOWN;
		}
		if (location == null) {
			return State.GONE;
		}
		final GLibraryFile now;
		try {
			now = location.describe(file.getPath());
		} catch (NoSuchFileException deleted) {
			return State.GONE;
		} catch (IOException | RuntimeException unreadable) {
			return State.UNKNOWN;
		}
		if (now == null) {
			return State.GONE;
		}
		// the same rule as GConversationFilesText: changed only when both versions are known and differ
		return file.getVersion() != null && now.version() != null && !file.getVersion().equals(now.version())
				? State.CHANGED
				: State.AS_WRITTEN;
	}

	private static String slashed(String path) {
		return path.replace('\\', '/');
	}
}
