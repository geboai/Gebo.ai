/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.files.GConversationFile;
import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.GLibraryFileContent;
import ai.gebo.architecture.ai.files.IGAILibraryFilesService;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;

/**
 * The files a run in a conversation leaves, with their bytes, for a caller that
 * has no way to fetch them from Gebo (an A2A caller, an MCP client): what the
 * run's models generated (the pictures they drew) and what its tools wrote (a
 * document, a spreadsheet, a file of the working directory), read from where
 * the conversation holds them. A file that cannot be read, is larger than
 * {@link #MAX_FILE_BYTES}, or would take the answer over
 * {@link #MAX_FILES_BYTES} is left out and logged; a file written to the
 * location of generated files and already there as a generated one comes once.
 */
@Service
public class ChatRunFiles {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatRunFiles.class);
	/** The size in bytes a file may have to travel in an answer. */
	public static final long MAX_FILE_BYTES = 20L * 1024 * 1024;
	/** The size in bytes the files of one answer may have together. */
	public static final long MAX_FILES_BYTES = 40L * 1024 * 1024;

	/** A file of a run: its name, its media type (never null) and its bytes. */
	public record RunFile(String name, String mimeType, byte[] bytes) {
	}

	/** Reads the files a run generated; absent in a unit test. */
	@Autowired(required = false)
	private IGChatStorageAreaService storageArea;
	/** Reads the files the run's tools wrote, from where they wrote them; absent in a unit test. */
	@Autowired(required = false)
	private IGAILibraryFilesService libraryFiles;

	public void setStorageArea(IGChatStorageAreaService storageArea) {
		this.storageArea = storageArea;
	}

	public void setLibraryFiles(IGAILibraryFilesService libraryFiles) {
		this.libraryFiles = libraryFiles;
	}

	/**
	 * The response a frame of a run carries, with the files the run wrote and generated.
	 * The run keeps those on its own response (the tools report what they write there,
	 * the image models what they draw), which is not always the object the last frame
	 * carries: a caller reading the frames would otherwise answer without them. The
	 * files of both are merged, each once; the carried response is returned, or the
	 * run's one when no frame carried a response.
	 *
	 * @param carried  the response the last frame of the run carried, may be null
	 * @param ofTheRun the response the run filled in, may be null
	 */
	public static GeboChatResponse withFilesOfTheRun(GeboChatResponse carried, GeboChatResponse ofTheRun) {
		if (carried == null) {
			return ofTheRun;
		}
		if (ofTheRun == null || ofTheRun == carried) {
			return carried;
		}
		if (ofTheRun.getWrittenFiles() != null && !ofTheRun.getWrittenFiles().isEmpty()) {
			final java.util.LinkedHashMap<String, ai.gebo.architecture.ai.files.GConversationFile> written = new java.util.LinkedHashMap<>();
			for (List<ai.gebo.architecture.ai.files.GConversationFile> list : List.of(
					carried.getWrittenFiles() != null ? carried.getWrittenFiles()
							: List.<ai.gebo.architecture.ai.files.GConversationFile>of(),
					ofTheRun.getWrittenFiles())) {
				for (ai.gebo.architecture.ai.files.GConversationFile file : list) {
					if (file != null) {
						written.putIfAbsent(file.key(), file);
					}
				}
			}
			carried.setWrittenFiles(new ArrayList<>(written.values()));
		}
		if (ofTheRun.getGeneratedResources() != null && !ofTheRun.getGeneratedResources().isEmpty()) {
			final List<LLMGeneratedResource> generated = carried.getGeneratedResources() != null
					? new ArrayList<>(carried.getGeneratedResources())
					: new ArrayList<>();
			final Set<String> codes = new HashSet<>();
			generated.forEach(x -> codes.add(x != null ? x.getCode() : null));
			for (LLMGeneratedResource resource : ofTheRun.getGeneratedResources()) {
				if (resource != null && (resource.getCode() == null || codes.add(resource.getCode()))) {
					generated.add(resource);
				}
			}
			carried.setGeneratedResources(generated);
		}
		return carried;
	}

	public List<RunFile> of(GeboChatResponse response) {
		final List<RunFile> files = new ArrayList<>();
		if (response == null) {
			return files;
		}
		final Set<String> names = new HashSet<>();
		long total = 0;
		if (storageArea != null && response.getGeneratedResources() != null) {
			total = generated(response, files, names);
		}
		written(response, files, names, total);
		return files;
	}

	private long generated(GeboChatResponse response, List<RunFile> files, Set<String> names) {
		long total = 0;
		for (LLMGeneratedResource generated : response.getGeneratedResources()) {
			if (generated == null) {
				continue;
			}
			if (generated.getFileSize() != null && generated.getFileSize() > MAX_FILE_BYTES) {
				LOGGER.warn("The generated file " + generated.getFileName() + " (" + generated.getFileSize()
						+ " bytes) is too large to travel in the answer");
				continue;
			}
			try (InputStream content = storageArea.streamContent(generated)) {
				if (content == null) {
					continue;
				}
				final byte[] bytes = content.readAllBytes();
				final String type = generated.getContentType() != null && generated.getContentType().contains("/")
						? generated.getContentType()
						: "application/octet-stream";
				final String name = generated.getFileName() != null ? generated.getFileName() : generated.getCode();
				if (bytes.length > MAX_FILE_BYTES || total + bytes.length > MAX_FILES_BYTES) {
					LOGGER.warn("The generated file " + name + " would take the answer over its size");
					continue;
				}
				files.add(new RunFile(name, type, bytes));
				names.add(name);
				total += bytes.length;
			} catch (Exception e) {
				LOGGER.error("The generated file " + generated.getFileName() + " cannot be read into the answer", e);
			}
		}
		return total;
	}

	private void written(GeboChatResponse response, List<RunFile> files, Set<String> names, long total) {
		final List<GConversationFile> written = response.getWrittenFiles();
		final String chatCode = response.getUserChatContextCode();
		if (libraryFiles == null || written == null || written.isEmpty() || chatCode == null) {
			return;
		}
		for (GConversationFile file : written) {
			if (file == null || file.getLocation() == null || file.getPath() == null) {
				continue;
			}
			final String name = file.getPath().substring(file.getPath().lastIndexOf('/') + 1);
			if (names.contains(name)) {
				continue;
			}
			try {
				final IGFilesLocation location = libraryFiles.locationOf(chatCode, file.getLocation());
				if (location == null) {
					LOGGER.warn("The written file " + file.key() + " is in a location the conversation no longer holds");
					continue;
				}
				final GLibraryFile described = location.describe(file.getPath());
				if (described == null) {
					continue;
				}
				if (described.size() > MAX_FILE_BYTES || total + described.size() > MAX_FILES_BYTES) {
					LOGGER.warn("The written file " + file.key() + " (" + described.size()
							+ " bytes) is too large to travel in the answer");
					continue;
				}
				final GLibraryFileContent content = location.read(file.getPath());
				if (content == null || content.content() == null) {
					continue;
				}
				final byte[] bytes;
				try (InputStream in = content.content().getInputStream()) {
					bytes = in.readAllBytes();
				}
				if (bytes.length > MAX_FILE_BYTES || total + bytes.length > MAX_FILES_BYTES) {
					LOGGER.warn("The written file " + file.key() + " (" + bytes.length
							+ " bytes) is too large to travel in the answer");
					continue;
				}
				String type = content.content().getContentType();
				if (type == null || !type.contains("/")) {
					type = described.contentType() != null && described.contentType().contains("/")
							? described.contentType()
							: "application/octet-stream";
				}
				files.add(new RunFile(name, type, bytes));
				names.add(name);
				total += bytes.length;
			} catch (Exception e) {
				LOGGER.error("The written file " + file.key() + " cannot be read into the answer", e);
			}
		}
	}
}
