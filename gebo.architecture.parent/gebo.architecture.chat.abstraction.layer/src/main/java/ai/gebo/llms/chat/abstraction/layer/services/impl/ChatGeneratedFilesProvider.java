/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.content.Media;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import ai.gebo.architecture.ai.files.GFolderFilesLocation;
import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.GLibraryFileContent;
import ai.gebo.architecture.ai.files.IGConversationFilesProvider;
import ai.gebo.architecture.ai.files.IGConversationGeneratedFilesStore;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.model.base.TypedInputStream;

/**
 * The files a conversation generated or received - the pictures its models drew, the
 * files its MCP tools and the other agents returned - as a read-only files location
 * of the conversation, as its uploads are: the tools working on files open them
 * there. It is also where such a file is kept when it arrives
 * ({@link IGConversationGeneratedFilesStore}).
 */
@Service
@Order(110)
public class ChatGeneratedFilesProvider implements IGConversationFilesProvider, IGConversationGeneratedFilesStore {
	private final LLMGeneratedResourceRepository generated;
	private final IGGeboConfigService configuration;
	private final IGChatStorageAreaService storageArea;

	public ChatGeneratedFilesProvider(LLMGeneratedResourceRepository generated, IGGeboConfigService configuration,
			@Lazy IGChatStorageAreaService storageArea) {
		this.generated = generated;
		this.configuration = configuration;
		this.storageArea = storageArea;
	}

	@Override
	public String getId() {
		return LOCATION_ID;
	}

	@Override
	public IGFilesLocation filesOf(String chatSessionCode) {
		if (chatSessionCode == null || chatSessionCode.isBlank()) {
			return null;
		}
		return new GeneratedLocation(chatSessionCode);
	}

	@Override
	public GLibraryFile store(String chatSessionCode, String name, String mimeType, byte[] data) throws IOException {
		if (chatSessionCode == null || chatSessionCode.isBlank() || data == null || data.length == 0) {
			throw new IllegalArgumentException("A file is kept in a conversation, and has content");
		}
		final String type = mimeType != null && mimeType.contains("/") ? mimeType : "application/octet-stream";
		String fileName = name != null && !name.isBlank() ? name.replace('\\', '/') : "file";
		fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
		if (!fileName.contains(".")) {
			fileName = fileName + IGConversationGeneratedFilesStore.extensionOf(type);
		}
		final LLMGeneratedResource resource = storageArea.addMedia(
				Media.builder().mimeType(MimeTypeUtils.parseMimeType(type)).data(data).name(fileName).build(),
				chatSessionCode);
		final GeneratedLocation location = new GeneratedLocation(chatSessionCode);
		for (Map.Entry<String, LLMGeneratedResource> entry : location.byName().entrySet()) {
			if (entry.getValue().getCode().equals(resource.getCode())) {
				return location.describe(entry.getKey(), entry.getValue());
			}
		}
		throw new IOException("The file " + fileName + " was kept and is not found among the conversation's files");
	}

	Path sessionFolder(String chatSessionCode) {
		return Path.of(configuration.getGeboWorkDirectory(), GChatStorageAreaServiceImpl.SESSIONS_PATH_PREFIX,
				chatSessionCode).toAbsolutePath().normalize();
	}

	private final class GeneratedLocation implements IGFilesLocation {
		private final String chatSessionCode;

		GeneratedLocation(String chatSessionCode) {
			this.chatSessionCode = chatSessionCode;
		}

		@Override
		public String getId() {
			return LOCATION_ID;
		}

		@Override
		public String getDescription() {
			return "the files this conversation generated or received from its tools and agents (read only)";
		}

		@Override
		public boolean isWritable() {
			return false;
		}

		/** The files by the names the tools know them by, in the order they arrived. */
		Map<String, LLMGeneratedResource> byName() {
			final Map<String, LLMGeneratedResource> named = new LinkedHashMap<>();
			try (Stream<LLMGeneratedResource> all = generated.findByUserContextCode(chatSessionCode)) {
				all.sorted((a, b) -> a.getDateCreated() != null && b.getDateCreated() != null
						? a.getDateCreated().compareTo(b.getDateCreated())
						: 0).forEach(resource -> named.put(uniqueName(named, nameOf(resource)), resource));
			}
			return named;
		}

		private static String nameOf(LLMGeneratedResource resource) {
			String name = resource.getFileName() != null && !resource.getFileName().isBlank() ? resource.getFileName()
					: resource.getCode();
			name = name.replace('\\', '/');
			name = name.substring(name.lastIndexOf('/') + 1);
			final String extension = resource.getExtension();
			if (extension != null && !name.toLowerCase().endsWith(extension.toLowerCase())) {
				name = name + extension;
			} else if (extension == null && !name.contains(".")) {
				// a file stored without an extension, as the generated pictures were
				name = name + IGConversationGeneratedFilesStore
						.extensionOf(GChatStorageAreaServiceImpl.mimeTypeOf(resource));
			}
			return name;
		}

		private static String uniqueName(Map<String, ?> taken, String name) {
			if (!taken.containsKey(name)) {
				return name;
			}
			final int dot = name.lastIndexOf('.');
			final String base = dot > 0 ? name.substring(0, dot) : name;
			final String extension = dot > 0 ? name.substring(dot) : "";
			for (int index = 2;; index++) {
				final String candidate = base + " (" + index + ")" + extension;
				if (!taken.containsKey(candidate)) {
					return candidate;
				}
			}
		}

		private Path fileOf(LLMGeneratedResource resource) {
			final Path folder = sessionFolder(chatSessionCode);
			final Path file = folder
					.resolve(resource.getCode() + (resource.getExtension() != null ? resource.getExtension() : ""))
					.normalize();
			if (!file.startsWith(folder)) {
				throw new IllegalArgumentException("Not a file of this conversation: " + resource.getFileName());
			}
			return file;
		}

		GLibraryFile describe(String name, LLMGeneratedResource resource) throws IOException {
			final Path file = fileOf(resource);
			if (!Files.isRegularFile(file)) {
				return null;
			}
			final var modified = Files.getLastModifiedTime(file);
			final long size = Files.size(file);
			String contentType = GChatStorageAreaServiceImpl.mimeTypeOf(resource);
			if (contentType == null) {
				contentType = GFolderFilesLocation.contentTypeOf(name);
			}
			return new GLibraryFile(LOCATION_ID, name, size, modified.toString(),
					size + "-" + modified.toInstant().getEpochSecond() + "." + modified.toInstant().getNano(),
					contentType);
		}

		@Override
		public List<GLibraryFile> list(String folder, boolean recursive) throws IOException {
			if (folder != null && !folder.isBlank() && !folder.trim().equals("/")) {
				throw new IllegalArgumentException("The generated files have no folders");
			}
			final List<GLibraryFile> files = new ArrayList<>();
			for (Map.Entry<String, LLMGeneratedResource> entry : byName().entrySet()) {
				final GLibraryFile file = describe(entry.getKey(), entry.getValue());
				if (file != null) {
					files.add(file);
				}
			}
			return files;
		}

		@Override
		public GLibraryFile describe(String path) throws IOException {
			final LLMGeneratedResource resource = path != null ? byName().get(path.trim()) : null;
			return resource != null ? describe(path.trim(), resource) : null;
		}

		@Override
		public GLibraryFileContent read(String path) throws IOException {
			final Map<String, LLMGeneratedResource> named = byName();
			final LLMGeneratedResource resource = path != null ? named.get(path.trim()) : null;
			final GLibraryFile file = resource != null ? describe(path.trim(), resource) : null;
			if (file == null) {
				throw new IllegalArgumentException("There is no generated file named " + path
						+ " in this conversation; there are " + named.keySet());
			}
			final byte[] bytes = Files.readAllBytes(fileOf(resource));
			final String extension = file.path().contains(".") ? file.path().substring(file.path().lastIndexOf('.'))
					: null;
			return new GLibraryFileContent(file,
					TypedInputStream.of(new ByteArrayInputStream(bytes), file.contentType(), extension));
		}

		@Override
		public GLibraryFile write(String path, TypedInputStream content, String expectedVersion) {
			throw new UnsupportedOperationException(
					"The generated files are read-only: write the file into a writable library of this conversation");
		}
	}
}
