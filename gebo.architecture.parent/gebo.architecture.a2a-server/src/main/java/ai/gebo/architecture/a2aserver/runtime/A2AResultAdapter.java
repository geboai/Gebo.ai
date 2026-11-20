/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.a2aserver.runtime;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.a2aproject.sdk.spec.FileWithBytes;
import org.a2aproject.sdk.spec.FilePart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.a2aproject.sdk.spec.DataPart;
import org.a2aproject.sdk.spec.Message;
import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.TextPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;

/**
 * Turns what the platform answers into what an A2A caller receives.
 * <p>
 * An answer here is not only its text. The platform knows which documents it
 * rests on, which tools were operated to reach it and which model wrote it, and
 * an A2A caller that is given the text alone has no way to tell a grounded
 * answer from an invented one. The protocol has room for the rest - a message
 * carries several parts - so the documents and the account of the run travel
 * with the text rather than being dropped at the boundary.
 */
@Service
public class A2AResultAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(A2AResultAdapter.class);
	/** The part naming the documents the answer rests on. */
	public static final String CITATIONS_KIND = "gebo.citations";
	/** The part accounting for how the answer was reached. */
	public static final String RUN_INFO_KIND = "gebo.runInfo";

	/**
	 * The message an A2A caller receives for a finished run: the answer, the
	 * documents it rests on, and the account of the run.
	 */
	public Message messageOf(GeboChatResponse response, String text, String contextId) {
		return messageOf(response, text, contextId, null);
	}

	/**
	 * The same, telling the caller what it sent that was not taken in (an attachment
	 * named by a link, a part of a kind this export does not read): it stands in the
	 * run's account under {@code notTaken}, so that a caller does not believe the
	 * answer rests on something the run never saw.
	 */
	public Message messageOf(GeboChatResponse response, String text, String contextId, List<String> notTaken) {
		final List<Part<?>> parts = new ArrayList<>();
		final String answer = text != null ? text : answerTextOf(response);
		parts.add(new TextPart(answer != null ? answer : ""));
		if (response != null) {
			final DataPart citations = citationsOf(response);
			if (citations != null) {
				parts.add(citations);
			}
			parts.add(runInfoOf(response, notTaken));
			parts.addAll(filesOf(response));
		} else if (notTaken != null && !notTaken.isEmpty()) {
			final Map<String, Object> data = new LinkedHashMap<>();
			data.put("kind", RUN_INFO_KIND);
			data.put("notTaken", new ArrayList<>(notTaken));
			parts.add(new DataPart(data));
		}
		final Message.Builder builder = Message.builder().role(Message.Role.ROLE_AGENT)
				.messageId(UUID.randomUUID().toString()).parts(parts);
		if (contextId != null && !contextId.isBlank()) {
			builder.contextId(contextId);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("A2A answer built with " + parts.size() + " part(s), documents:"
					+ (response != null ? documentsOf(response).size() : 0));
		}
		return builder.build();
	}

	/** The size in bytes a generated file may have to travel in the answer. */
	static final long MAX_FILE_BYTES = 20L * 1024 * 1024;
	/** The size in bytes the files of one answer may have together. */
	static final long MAX_ANSWER_FILES_BYTES = 40L * 1024 * 1024;
	/** Reads the files the run's tools wrote, from where they wrote them; absent in a unit test. */
	@Autowired(required = false)
	private ai.gebo.architecture.ai.files.IGAILibraryFilesService libraryFiles;

	void setLibraryFiles(ai.gebo.architecture.ai.files.IGAILibraryFilesService libraryFiles) {
		this.libraryFiles = libraryFiles;
	}

	/** Reads the files a run generated; absent in a unit test. */
	@Autowired(required = false)
	private IGChatStorageAreaService storageArea;

	void setStorageArea(IGChatStorageAreaService storageArea) {
		this.storageArea = storageArea;
	}

	/**
	 * The files of the run as file parts, bytes included, since the caller has no way
	 * to fetch them from Gebo: what its models generated (the pictures they drew) and
	 * what its tools wrote (a document, a spreadsheet, a file of the working
	 * directory). A file that cannot be read, is larger than {@link #MAX_FILE_BYTES},
	 * or would take the answer over {@link #MAX_ANSWER_FILES_BYTES} is left out and
	 * logged.
	 */
	List<Part<?>> filesOf(GeboChatResponse response) {
		final List<Part<?>> files = new ArrayList<>();
		final java.util.Set<String> names = new java.util.HashSet<>();
		long total = 0;
		if (storageArea != null && response.getGeneratedResources() != null) {
			total = generatedFilesOf(response, files, names);
		}
		writtenFilesOf(response, files, names, total);
		return files;
	}

	/**
	 * The files the run's tools wrote, read from the location each was written to,
	 * as the conversation holds it now. A file of the location of generated files
	 * already sent as a generated one is not sent twice.
	 */
	private void writtenFilesOf(GeboChatResponse response, List<Part<?>> files, java.util.Set<String> names,
			long total) {
		final List<ai.gebo.architecture.ai.files.GConversationFile> written = response.getWrittenFiles();
		final String chatCode = response.getUserChatContextCode();
		if (libraryFiles == null || written == null || written.isEmpty() || chatCode == null) {
			return;
		}
		for (ai.gebo.architecture.ai.files.GConversationFile file : written) {
			if (file == null || file.getLocation() == null || file.getPath() == null) {
				continue;
			}
			final String name = file.getPath().substring(file.getPath().lastIndexOf('/') + 1);
			if (names.contains(name)) {
				continue;
			}
			try {
				final ai.gebo.architecture.ai.files.IGFilesLocation location = libraryFiles.locationOf(chatCode,
						file.getLocation());
				if (location == null) {
					LOGGER.warn("The written file " + file.key() + " is in a location the conversation no longer holds");
					continue;
				}
				final ai.gebo.architecture.ai.files.GLibraryFile described = location.describe(file.getPath());
				if (described == null) {
					continue;
				}
				if (described.size() > MAX_FILE_BYTES || total + described.size() > MAX_ANSWER_FILES_BYTES) {
					LOGGER.warn("The written file " + file.key() + " (" + described.size()
							+ " bytes) is too large to travel in the A2A answer");
					continue;
				}
				final ai.gebo.architecture.ai.files.GLibraryFileContent content = location.read(file.getPath());
				if (content == null || content.content() == null) {
					continue;
				}
				final byte[] bytes;
				try (java.io.InputStream in = content.content().getInputStream()) {
					bytes = in.readAllBytes();
				}
				if (bytes.length > MAX_FILE_BYTES || total + bytes.length > MAX_ANSWER_FILES_BYTES) {
					LOGGER.warn("The written file " + file.key() + " (" + bytes.length
							+ " bytes) is too large to travel in the A2A answer");
					continue;
				}
				String type = content.content().getContentType();
				if (type == null || !type.contains("/")) {
					type = described.contentType() != null && described.contentType().contains("/")
							? described.contentType()
							: "application/octet-stream";
				}
				files.add(new FilePart(new FileWithBytes(type, name, bytes)));
				names.add(name);
				total += bytes.length;
			} catch (Exception e) {
				LOGGER.error("The written file " + file.key() + " cannot be read into the A2A answer", e);
			}
		}
	}

	/** The generated files, added to the list; answers the bytes they take. */
	private long generatedFilesOf(GeboChatResponse response, List<Part<?>> files, java.util.Set<String> names) {
		long total = 0;
		for (LLMGeneratedResource generated : response.getGeneratedResources()) {
			if (generated == null) {
				continue;
			}
			if (generated.getFileSize() != null && generated.getFileSize() > MAX_FILE_BYTES) {
				LOGGER.warn("The generated file " + generated.getFileName() + " (" + generated.getFileSize()
						+ " bytes) is too large to travel in the A2A answer");
				continue;
			}
			try (java.io.InputStream content = storageArea.streamContent(generated)) {
				if (content == null) {
					continue;
				}
				final byte[] bytes = content.readAllBytes();
				final String type = generated.getContentType() != null && generated.getContentType().contains("/")
						? generated.getContentType()
						: "application/octet-stream";
				final String name = generated.getFileName() != null ? generated.getFileName() : generated.getCode();
				if (total + bytes.length > MAX_ANSWER_FILES_BYTES) {
					LOGGER.warn("The generated file " + name + " would take the A2A answer over its size");
					continue;
				}
				files.add(new FilePart(new FileWithBytes(type, name, bytes)));
				names.add(name);
				total += bytes.length;
			} catch (Exception e) {
				LOGGER.error("The generated file " + generated.getFileName() + " cannot be read into the A2A answer", e);
			}
		}
		return total;
	}

	/** A streamed frame: the chunk of text it carries, or null when it carries none. */
	public String chunkOf(GeboChatMessageEnvelope<?> envelope) {
        if (envelope == null) {
            return null;
        }
        final Object content = envelope.getContent();
        if (content instanceof String chunk) {
            return chunk;
        }
        return null;
	}

	/** Whether this frame is the one that ends the answer. */
	public boolean isLast(GeboChatMessageEnvelope<?> envelope) {
		if (envelope == null) {
			return false;
		}
		return envelope.isLastMessage() || envelope.getContent() instanceof GeboChatResponse;
	}

	/** The response a frame carries, when it carries one. */
	public GeboChatResponse responseOf(GeboChatMessageEnvelope<?> envelope) {
		if (envelope != null && envelope.getContent() instanceof GeboChatResponse response) {
			return response;
		}
		return null;
	}

	/** The text of an answer. */
	public String answerTextOf(GeboChatResponse response) {
		return response != null ? response.getQueryResponse() : null;
	}

	/**
	 * The documents the answer rests on, as a structured part: what each one is,
	 * where it lives and which page was read. The contents themselves are not
	 * inlined - a reference the caller can resolve keeps the platform's access
	 * rules in force at the moment it is read.
	 */
	private DataPart citationsOf(GeboChatResponse response) {
		final List<GResponseDocumentRef> documents = documentsOf(response);
		if (documents.isEmpty()) {
			return null;
		}
		final List<Map<String, Object>> cited = new ArrayList<>();
		for (GResponseDocumentRef document : documents) {
			if (document == null) {
				continue;
			}
			final Map<String, Object> one = new LinkedHashMap<>();
			put(one, "uuid", document.getUuid());
			put(one, "documentCode", document.getDocumentCode());
			put(one, "description", document.getDescription());
			put(one, "contentType", document.getContentType());
			put(one, "extension", document.getExtension());
			put(one, "knowledgeBaseCode", document.getKnowledgeBaseCode());
			put(one, "projectCode", document.getProjectCode());
			put(one, "fileType", document.getGeboFileTypeDescription());
			if (document.getReferenceType() != null) {
				one.put("referenceType", document.getReferenceType().toString());
			}
			cited.add(one);
		}
		if (cited.isEmpty()) {
			return null;
		}
		final Map<String, Object> data = new LinkedHashMap<>();
		data.put("kind", CITATIONS_KIND);
		data.put("documents", cited);
		return new DataPart(data);
	}

	/** How the answer was reached: the model that wrote it and the tools operated. */
	private DataPart runInfoOf(GeboChatResponse response, List<String> notTaken) {
		final Map<String, Object> data = new LinkedHashMap<>();
		data.put("kind", RUN_INFO_KIND);
		if (notTaken != null && !notTaken.isEmpty()) {
			data.put("notTaken", new ArrayList<>(notTaken));
		}
		put(data, "chatContextCode", response.getUserChatContextCode());
		put(data, "chatModelCode", response.getUsedChatModelCode());
		put(data, "chatModelProvider", response.getUsedChatModelProvider());
		put(data, "pipelineRouterDecisionCode", response.getPipelineRouterDecisionCode());
		if (response.getCalledFunctions() != null && !response.getCalledFunctions().isEmpty()) {
			final List<String> called = new ArrayList<>();
			response.getCalledFunctions().forEach(function -> {
				if (function != null && function.getFunctionName() != null) {
					called.add(function.getFunctionName());
				}
			});
			if (!called.isEmpty()) {
				data.put("calledFunctions", called);
			}
		}
		if (response.getListedDocumentNames() != null && !response.getListedDocumentNames().isEmpty()) {
			data.put("listedDocumentNames", response.getListedDocumentNames());
		}
		return new DataPart(data);
	}

	/** Every document of an answer: the ones it used and the ones it was given. */
	private List<GResponseDocumentRef> documentsOf(GeboChatResponse response) {
		final List<GResponseDocumentRef> documents = new ArrayList<>();
		if (response == null) {
			return documents;
		}
		if (response.getDocumentsRef() != null) {
			documents.addAll(response.getDocumentsRef());
		}
		if (response.getForcedDocumentsRef() != null) {
			documents.addAll(response.getForcedDocumentsRef());
		}
		return documents;
	}

	private void put(Map<String, Object> data, String key, String value) {
		if (value != null && !value.isBlank()) {
			data.put(key, value);
		}
	}

	/** An empty map, for the places a part needs one. */
	static Map<String, Object> emptyData() {
		return new HashMap<>();
	}
}
