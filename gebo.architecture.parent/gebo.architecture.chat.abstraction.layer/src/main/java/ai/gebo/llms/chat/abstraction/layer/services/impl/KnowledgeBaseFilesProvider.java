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
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.files.GFolderFilesLocation;
import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.GLibraryFileContent;
import ai.gebo.architecture.ai.files.IGConversationFilesProvider;
import ai.gebo.architecture.ai.files.IGFilesLocation;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.core.contents.security.services.VirtualFilesystemQuery;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.model.base.TypedInputStream;

/**
 * The documents of a conversation's knowledge bases as a read-only files
 * location: a tool reads a spreadsheet or a document of a knowledge base as it
 * was ingested - the original file, not its text - and writes what it makes of
 * it into a writable library.
 * <p>
 * A document is named {@code <uniqueId>/<name>}, or by its uniqueId alone: the
 * id the knowledge base tools give it when they browse or search. The location
 * cannot be listed - a knowledge base may hold millions of documents - so the
 * documents are found with those tools. Only the documents of the
 * conversation's knowledge bases that the user can read are reached.
 */
@Service
@Order(200)
public class KnowledgeBaseFilesProvider implements IGConversationFilesProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseFilesProvider.class);
	public static final String LOCATION_ID = "knowledge-bases";
	/** The largest document read: 50 MB, as the folder locations. */
	static final long MAX_DOCUMENT_SIZE = GFolderFilesLocation.DEFAULT_MAX_FILE_SIZE;

	private final ObjectProvider<IGChatSessionLifeCycleService> sessions;
	private final ObjectProvider<IGKnowledgebaseVisibilityService> visibility;
	private final ObjectProvider<IDocumentsCacheService> documents;

	public KnowledgeBaseFilesProvider(ObjectProvider<IGChatSessionLifeCycleService> sessions,
			ObjectProvider<IGKnowledgebaseVisibilityService> visibility, ObjectProvider<IDocumentsCacheService> documents) {
		this.sessions = sessions;
		this.visibility = visibility;
		this.documents = documents;
	}

	@Override
	public String getId() {
		return LOCATION_ID;
	}

	@Override
	public IGFilesLocation filesOf(String chatSessionCode) {
		if (chatSessionCode == null || chatSessionCode.isBlank() || sessions.getIfAvailable() == null
				|| visibility.getIfAvailable() == null || documents.getIfAvailable() == null) {
			return null;
		}
		final List<String> codes = knowledgeBasesOf(chatSessionCode);
		return codes.isEmpty() ? null : new KnowledgeBasesLocation(codes);
	}

	/** The knowledge bases the conversation's chat profile gives it, as its tools get them. */
	List<String> knowledgeBasesOf(String chatSessionCode) {
		try {
			final GeboChatRequest request = new GeboChatRequest();
			request.setUserChatContextCode(chatSessionCode);
			final List<GKnowledgeBase> bases = sessions.getObject().getSessionAvailableKnowledgeBases(request);
			return bases != null ? bases.stream().map(GKnowledgeBase::getCode).filter(code -> code != null).toList()
					: List.of();
		} catch (Exception unreadable) {
			LOGGER.warn("The knowledge bases of conversation:" + chatSessionCode + " could not be read: " + unreadable);
			return List.of();
		}
	}

	/** The uniqueId a path names: its first part. */
	static Long uniqueIdOf(String path) {
		final String given = path != null ? path.trim().replace('\\', '/') : "";
		final String first = given.startsWith("/") ? given.substring(1) : given;
		final int slash = first.indexOf('/');
		try {
			return Long.valueOf(slash >= 0 ? first.substring(0, slash) : first);
		} catch (NumberFormatException notAnId) {
			throw new IllegalArgumentException("Not a document of the knowledge bases: " + path
					+ " (name it <uniqueId>/<name> or by its uniqueId, as the knowledge base tools give it)");
		}
	}

	private final class KnowledgeBasesLocation implements IGFilesLocation {
		private final List<String> knowledgeBaseCodes;

		KnowledgeBasesLocation(List<String> knowledgeBaseCodes) {
			this.knowledgeBaseCodes = knowledgeBaseCodes;
		}

		@Override
		public String getId() {
			return LOCATION_ID;
		}

		@Override
		public String getDescription() {
			return "the documents of this conversation's knowledge bases (read only): find them with the knowledge"
					+ " base tools, then name one as <uniqueId>/<name>";
		}

		@Override
		public boolean isWritable() {
			return false;
		}

		@Override
		public List<GLibraryFile> list(String folder, boolean recursive) {
			throw new UnsupportedOperationException("The knowledge bases are not listed here: find their documents with"
					+ " the knowledge base tools, then name one as <uniqueId>/<name>");
		}

		private GDocumentReference documentOf(String path) {
			final Long uniqueId = uniqueIdOf(path);
			final List<GDocumentReference> found = visibility.getObject()
					.browseVisibleDocuments(VirtualFilesystemQuery.builder().knowledgeBaseCodes(knowledgeBaseCodes)
							.uniqueIds(List.of(uniqueId)).build(), PageRequest.of(0, 1))
					.getContent();
			return found.isEmpty() ? null : found.get(0);
		}

		private GLibraryFile fileOf(GDocumentReference document, long size) {
			final String name = document.getName() != null ? document.getName() : String.valueOf(document.getUniqueId());
			final long modified = document.getDateModified() != null ? document.getDateModified().getTime() : 0L;
			return new GLibraryFile(LOCATION_ID, document.getUniqueId() + "/" + name, size,
					document.getDateModified() != null ? document.getDateModified().toInstant().toString() : null,
					size + "-" + modified, document.getContentType() != null ? document.getContentType()
							: GFolderFilesLocation.contentTypeOf(name));
		}

		@Override
		public GLibraryFile describe(String path) {
			final GDocumentReference document = documentOf(path);
			return document != null ? fileOf(document, document.getFileSize() != null ? document.getFileSize() : -1L)
					: null;
		}

		@Override
		public GLibraryFileContent read(String path) throws IOException {
			final GDocumentReference document = documentOf(path);
			if (document == null) {
				throw new IllegalArgumentException("There is no document " + path
						+ " among the documents of this conversation's knowledge bases the user can read");
			}
			final TypedInputStream stream;
			try {
				stream = documents.getObject().streamDocument(StreamingPurpose.SERVING, document);
			} catch (IOException failed) {
				throw failed;
			} catch (Exception notStreamed) {
				throw new IOException("The document " + path + " could not be read: " + notStreamed.getMessage(),
						notStreamed);
			}
			if (stream == null) {
				throw new IOException("The document " + path + " has no content to read");
			}
			final byte[] bytes;
			try (InputStream in = stream.getInputStream()) {
				bytes = in.readNBytes((int) MAX_DOCUMENT_SIZE + 1);
			}
			if (bytes.length > MAX_DOCUMENT_SIZE) {
				throw new IllegalArgumentException(
						"The document " + path + " is too large to read here: more than " + MAX_DOCUMENT_SIZE + " bytes");
			}
			final GLibraryFile file = fileOf(document, bytes.length);
			final String contentType = stream.getContentType() != null ? stream.getContentType() : file.contentType();
			final String extension = document.getExtension() != null ? document.getExtension() : stream.getExtension();
			return new GLibraryFileContent(file,
					TypedInputStream.of(new ByteArrayInputStream(bytes), contentType, extension));
		}

		@Override
		public GLibraryFile write(String path, TypedInputStream content, String expectedVersion) {
			throw new UnsupportedOperationException(
					"The knowledge bases are read-only: write the file into a writable library of this conversation");
		}
	}
}
