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

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.files.GFolderFilesLocation;
import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.GLibraryFileContent;
import ai.gebo.architecture.ai.files.IGConversationFilesProvider;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.model.base.TypedInputStream;

/**
 * The files uploaded to a conversation, read only, each by the name it was
 * uploaded with: a tool reads an uploaded spreadsheet or document from here and
 * writes what it makes of it into a writable library. Two uploads of the same
 * name are told apart by a number after the name ({@code data (2).xlsx}).
 * <p>
 * The conversation is the one the tools run for: whoever runs in it may read
 * what was uploaded to it, so no user check is made here, the tool runs having
 * no user of their own when they run in the background.
 */
@Service
@Order(100)
public class ChatUploadsFilesProvider implements IGConversationFilesProvider {
	public static final String LOCATION_ID = "uploads";

	private final UserUploadContentServerSideRepository uploads;
	private final IGGeboConfigService configuration;

	public ChatUploadsFilesProvider(UserUploadContentServerSideRepository uploads, IGGeboConfigService configuration) {
		this.uploads = uploads;
		this.configuration = configuration;
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
		return new UploadsLocation(chatSessionCode);
	}

	/** The folder the conversation's uploads are kept in, as the storage area keeps them. */
	Path sessionFolder(String chatSessionCode) {
		return Path.of(configuration.getGeboWorkDirectory(), GChatStorageAreaServiceImpl.SESSIONS_PATH_PREFIX,
				chatSessionCode).toAbsolutePath().normalize();
	}

	private final class UploadsLocation implements IGFilesLocation {
		private final String chatSessionCode;

		UploadsLocation(String chatSessionCode) {
			this.chatSessionCode = chatSessionCode;
		}

		@Override
		public String getId() {
			return LOCATION_ID;
		}

		@Override
		public String getDescription() {
			return "the files the user uploaded to this conversation (read only)";
		}

		@Override
		public boolean isWritable() {
			return false;
		}

		/** The uploads by the name the tools see, in upload order. */
		private Map<String, UserUploadContentServerSide> byName() {
			final Map<String, UserUploadContentServerSide> named = new LinkedHashMap<>();
			try (Stream<UserUploadContentServerSide> all = uploads.findByUserContextCode(chatSessionCode)) {
				all.filter(upload -> upload.getRelativeFilePath() != null)
						.sorted((a, b) -> a.getDateCreated() != null && b.getDateCreated() != null
								? a.getDateCreated().compareTo(b.getDateCreated())
								: 0)
						.forEach(upload -> named.put(uniqueName(named, nameOf(upload)), upload));
			}
			return named;
		}

		private static String nameOf(UserUploadContentServerSide upload) {
			String name = upload.getFileName() != null && !upload.getFileName().isBlank() ? upload.getFileName()
					: upload.getRelativeFilePath();
			name = name.replace('\\', '/');
			return name.substring(name.lastIndexOf('/') + 1);
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

		private Path fileOf(UserUploadContentServerSide upload) {
			final Path folder = sessionFolder(chatSessionCode);
			final Path file = folder.resolve(upload.getRelativeFilePath()).normalize();
			if (!file.startsWith(folder)) {
				throw new IllegalArgumentException("Not an upload of this conversation: " + upload.getFileName());
			}
			return file;
		}

		private GLibraryFile describe(String name, UserUploadContentServerSide upload) throws IOException {
			final Path file = fileOf(upload);
			if (!Files.isRegularFile(file)) {
				return null;
			}
			final var modified = Files.getLastModifiedTime(file);
			final long size = Files.size(file);
			final String contentType = upload.getContentType() != null ? upload.getContentType()
					: GFolderFilesLocation.contentTypeOf(name);
			return new GLibraryFile(LOCATION_ID, name, size, modified.toString(),
					size + "-" + modified.toInstant().getEpochSecond() + "." + modified.toInstant().getNano(),
					contentType);
		}

		@Override
		public List<GLibraryFile> list(String folder, boolean recursive) throws IOException {
			if (folder != null && !folder.isBlank() && !folder.trim().equals("/")) {
				throw new IllegalArgumentException("The uploads have no folders");
			}
			final List<GLibraryFile> files = new ArrayList<>();
			for (Map.Entry<String, UserUploadContentServerSide> entry : byName().entrySet()) {
				final GLibraryFile file = describe(entry.getKey(), entry.getValue());
				if (file != null) {
					files.add(file);
				}
			}
			return files;
		}

		@Override
		public GLibraryFile describe(String path) throws IOException {
			final UserUploadContentServerSide upload = path != null ? byName().get(path.trim()) : null;
			return upload != null ? describe(path.trim(), upload) : null;
		}

		@Override
		public GLibraryFileContent read(String path) throws IOException {
			final Map<String, UserUploadContentServerSide> named = byName();
			final UserUploadContentServerSide upload = path != null ? named.get(path.trim()) : null;
			final GLibraryFile file = upload != null ? describe(path.trim(), upload) : null;
			if (file == null) {
				throw new IllegalArgumentException(
						"There is no upload named " + path + " in this conversation; there are " + named.keySet());
			}
			final byte[] bytes = Files.readAllBytes(fileOf(upload));
			final String extension = upload.getExtension() != null ? upload.getExtension()
					: file.path().contains(".") ? file.path().substring(file.path().lastIndexOf('.')) : null;
			return new GLibraryFileContent(file,
					TypedInputStream.of(new ByteArrayInputStream(bytes), file.contentType(), extension));
		}

		@Override
		public GLibraryFile write(String path, TypedInputStream content, String expectedVersion) {
			throw new UnsupportedOperationException(
					"The uploads are read-only: write the file into a writable library of this conversation");
		}
	}
}
