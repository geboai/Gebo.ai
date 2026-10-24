/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.a2aserver.runtime;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.a2aproject.sdk.spec.DataPart;
import org.a2aproject.sdk.spec.FileContent;
import org.a2aproject.sdk.spec.FilePart;
import org.a2aproject.sdk.spec.FileWithBytes;
import org.a2aproject.sdk.spec.FileWithUri;
import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.TextPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

/**
 * What an A2A caller sent in, read into the shapes the platform works with.
 * <p>
 * A message is not only its text. A caller can attach a file for the run to
 * work on, and can state in a data part what it wants the run to look at - the
 * knowledge bases, the documents. Everything but the text used to be dropped
 * here, which meant an A2A caller could ask about a document it had no way to
 * hand over.
 * <p>
 * Nothing in here decides anything: it reads the parts, and the caller of this
 * class attaches what it found to the request through the chat's own services,
 * so an attachment arrives exactly as it arrives from a chat.
 */
@Getter
public final class A2AInboundResources {
	private static final Logger LOGGER = LoggerFactory.getLogger(A2AInboundResources.class);
	/** The data part by which a caller says which knowledge bases to work on. */
	public static final String KNOWLEDGE_BASES_KEY = "knowledgeBases";
	/** The data part by which a caller names documents the run must take in. */
	public static final String DOCUMENTS_KEY = "documents";

	/** What the caller asked, in words. */
	private final String text;
	/** The files it attached, ready to be stored. */
	private final List<MultipartFile> files;
	/** The knowledge bases it asked the run to work on, when it said. */
	private final List<String> knowledgeBases;
	/** The documents it named for the run to take in, when it named any. */
	private final List<String> documents;
	/** What was sent that could not be taken in, for the caller to be told. */
	private final List<String> notTaken;

	private A2AInboundResources(String text, List<MultipartFile> files, List<String> knowledgeBases,
			List<String> documents, List<String> notTaken) {
		this.text = text;
		this.files = files;
		this.knowledgeBases = knowledgeBases;
		this.documents = documents;
		this.notTaken = notTaken;
	}

	/** Whether anything beyond the text came in. */
	public boolean hasResources() {
		return !files.isEmpty() || !knowledgeBases.isEmpty() || !documents.isEmpty();
	}

	/**
	 * Reads the parts of an inbound message.
	 *
	 * @param parts the parts as the caller sent them
	 */
	public static A2AInboundResources of(List<Part<?>> parts) {
		final StringBuilder text = new StringBuilder();
		final List<MultipartFile> files = new ArrayList<>();
		final List<String> knowledgeBases = new ArrayList<>();
		final List<String> documents = new ArrayList<>();
		final List<String> notTaken = new ArrayList<>();
		if (parts == null) {
			return new A2AInboundResources("", files, knowledgeBases, documents, notTaken);
		}
		for (Part<?> part : parts) {
			if (part instanceof TextPart textPart) {
				if (text.length() > 0) {
					text.append("\n");
				}
				text.append(textPart.text());
			} else if (part instanceof FilePart filePart) {
				readFile(filePart, files, notTaken);
			} else if (part instanceof DataPart dataPart) {
				readData(dataPart, knowledgeBases, documents, notTaken);
			} else if (part != null) {
				notTaken.add("a part of kind " + part.getClass().getSimpleName());
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("A2A message read: " + text.length() + " character(s) of text, " + files.size()
					+ " file(s), " + knowledgeBases.size() + " knowledge base(s), " + documents.size()
					+ " document(s), " + notTaken.size() + " thing(s) not taken in");
		}
		return new A2AInboundResources(text.toString(), files, knowledgeBases, documents, notTaken);
	}

	/**
	 * A file the caller attached. Only the bytes it sent are taken: a file named by
	 * a link would have this installation fetch whatever that link points at, which
	 * is a decision for whoever runs it and not something to do quietly.
	 */
	private static void readFile(FilePart part, List<MultipartFile> files, List<String> notTaken) {
		final FileContent content = part.file();
		if (content instanceof FileWithBytes withBytes) {
			try {
				final byte[] bytes = Base64.getDecoder().decode(withBytes.bytes());
				final String name = withBytes.name() != null ? withBytes.name() : "a2a-attachment";
				files.add(new InMemoryMultipartFile(name, withBytes.mimeType(), bytes));
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("A2A attachment taken in: " + name + " of " + bytes.length + " byte(s), type "
							+ withBytes.mimeType());
				}
			} catch (IllegalArgumentException notBase64) {
				LOGGER.warn("An A2A attachment was not readable: its bytes are not base64", notBase64);
				notTaken.add("the attachment " + withBytes.name() + ", whose bytes could not be read");
			}
		} else if (content instanceof FileWithUri withUri) {
			LOGGER.warn("An A2A attachment names a link rather than sending its bytes: " + withUri.uri()
					+ "; it is not fetched");
			notTaken.add("the attachment " + withUri.name() + ", which names a link rather than sending its content");
		} else if (content != null) {
			notTaken.add("an attachment of an unknown kind");
		}
	}

	/** What a caller stated about the run: which knowledge bases, which documents. */
	private static void readData(DataPart part, List<String> knowledgeBases, List<String> documents,
			List<String> notTaken) {
		final Object data = part.data();
		if (!(data instanceof Map<?, ?> stated)) {
			notTaken.add("a data part that is not a set of values");
			return;
		}
		addStrings(stated.get(KNOWLEDGE_BASES_KEY), knowledgeBases);
		addStrings(stated.get(DOCUMENTS_KEY), documents);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<A2A_STATED_DATA>");
			LOGGER.trace(String.valueOf(new LinkedHashMap<>(stated)));
			LOGGER.trace("</A2A_STATED_DATA>");
		}
	}

	private static void addStrings(Object value, List<String> into) {
		if (value instanceof List<?> many) {
			for (Object one : many) {
				if (one != null && !String.valueOf(one).isBlank()) {
					into.add(String.valueOf(one));
				}
			}
		} else if (value != null && !String.valueOf(value).isBlank()) {
			into.add(String.valueOf(value));
		}
	}

	/**
	 * A file that is already in memory, for the upload service, which speaks in
	 * multipart files because that is how a browser sends one.
	 */
	static final class InMemoryMultipartFile implements MultipartFile {
		private final String name;
		private final String contentType;
		private final byte[] bytes;

		InMemoryMultipartFile(String name, String contentType, byte[] bytes) {
			this.name = name;
			this.contentType = contentType;
			this.bytes = bytes;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getOriginalFilename() {
			return name;
		}

		@Override
		public String getContentType() {
			return contentType;
		}

		@Override
		public boolean isEmpty() {
			return bytes == null || bytes.length == 0;
		}

		@Override
		public long getSize() {
			return bytes != null ? bytes.length : 0;
		}

		@Override
		public byte[] getBytes() {
			return bytes != null ? bytes : new byte[0];
		}

		@Override
		public InputStream getInputStream() {
			return new ByteArrayInputStream(getBytes());
		}

		@Override
		public void transferTo(java.io.File destination) throws IOException, IllegalStateException {
			try (OutputStream out = Files.newOutputStream(destination.toPath())) {
				out.write(getBytes());
			}
		}

		@Override
		public String toString() {
			return "InMemoryMultipartFile[" + name + ", " + getSize() + " bytes, "
					+ (contentType != null ? contentType : "unknown type") + "]";
		}

		/** What this file says of itself, for the logs. */
		String described() {
			return new String(("" + name).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
		}
	}
}
