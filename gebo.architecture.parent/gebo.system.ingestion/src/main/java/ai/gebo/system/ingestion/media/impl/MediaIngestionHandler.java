/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion.media.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.IGMediaTextExtractor;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * Reads pictures, recordings and videos with a model: a picture gives the description
 * of what it shows and the text it carries, a recording or a video the transcript of
 * what is said ({@link IGMediaTextExtractor}). One document a file, marked as written
 * by a model.
 * <p>
 * It takes a file only while its capability is on: with picture descriptions off a
 * picture is, as before, a file no reader takes, and the same holds for recordings
 * and transcription.
 */
@Service
public class MediaIngestionHandler extends GAbstractConfiguredHandler {
	public static final String CODE = "MediaIngestionHandler";
	private final ObjectProvider<IGMediaTextExtractor> extractor;

	public MediaIngestionHandler(IGIngestionHandlerConfigDao dao, ObjectProvider<IGMediaTextExtractor> extractor) {
		super(dao);
		this.extractor = extractor;
	}

	@Override
	public String getCode() {
		return CODE;
	}

	private IGMediaTextExtractor extractor() {
		try {
			return extractor != null ? extractor.getIfAvailable() : null;
		} catch (RuntimeException unavailable) {
			return null;
		}
	}

	/** Whether a name or a MIME type of this handler is a picture's. */
	public static boolean isPicture(String extensionOrType) {
		if (extensionOrType == null) {
			return false;
		}
		final String value = extensionOrType.toLowerCase(Locale.ROOT);
		return value.startsWith("image/") || List.of(".png", ".jpg", ".jpeg", ".gif", ".webp").contains(value);
	}

	/** Whether the capability a file of this extension or type needs is on. */
	private boolean reads(String extensionOrType) {
		final IGMediaTextExtractor media = extractor();
		if (media == null || extensionOrType == null) {
			return false;
		}
		return isPicture(extensionOrType) ? media.describesPictures() : media.transcribes();
	}

	@Override
	public List<String> getHandledExtensions() {
		return super.getHandledExtensions().stream().filter(this::reads).toList();
	}

	@Override
	public List<String> getHandledContentTypes() {
		return super.getHandledContentTypes().stream().filter(this::reads).toList();
	}

	@Override
	public boolean canManageContent(GDocumentReference reference) {
		if (reference == null) {
			return false;
		}
		final String extension = reference.getExtension() != null ? reference.getExtension().toLowerCase(Locale.ROOT)
				: null;
		if (extension != null && extensions.contains(extension)) {
			return reads(extension);
		}
		final String type = reference.getContentType() != null ? reference.getContentType().toLowerCase(Locale.ROOT)
				: null;
		return type != null && contentTypes.contains(type) && reads(type);
	}

	@Override
	public boolean canManageContent(TypedInputStream streamContent) {
		if (streamContent == null) {
			return false;
		}
		final String extension = streamContent.getExtension() != null
				? streamContent.getExtension().toLowerCase(Locale.ROOT)
				: null;
		if (extension != null && extensions.contains(extension)) {
			return reads(extension);
		}
		final String type = streamContent.getContentType() != null
				? streamContent.getContentType().toLowerCase(Locale.ROOT)
				: null;
		return type != null && contentTypes.contains(type) && reads(type);
	}

	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		final List<Document> out = new ArrayList<Document>();
		enrichMetaData(reference, metadata);
		final IGMediaTextExtractor media = extractor();
		try {
			if (media == null) {
				return out.stream();
			}
			final String extension = reference.getExtension() != null ? reference.getExtension() : "";
			final boolean picture = isPicture(extension) || isPicture(reference.getContentType());
			final String type = reference.getContentType() != null ? reference.getContentType()
					: java.net.URLConnection.guessContentTypeFromName("file" + extension);
			if (reference.getFileSize() != null && reference.getFileSize() > media.maxFileBytes()) {
				LOGGER.warn("The file " + reference.getName() + " (" + reference.getFileSize()
						+ " bytes) is larger than a model is given, it is not read");
				return out.stream();
			}
			final String text;
			if (picture) {
				final byte[] data = is.readNBytes((int) Math.min(Integer.MAX_VALUE - 8, media.maxFileBytes() + 1));
				if (data.length > media.maxFileBytes()) {
					LOGGER.warn("The picture " + reference.getName() + " is larger than a model is given, it is not read");
					return out.stream();
				}
				text = media.describePicture(reference.getName(), type, data);
			} else {
				text = media.transcribe(reference.getName(), type, is);
			}
			if (text != null && !text.trim().isEmpty()) {
				metadata.put(IGMediaTextExtractor.MODEL_WRITTEN_METADATA, picture ? "description" : "transcript");
				out.add(new Document(text.trim(), metadata));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("handleContent(...) " + reference.getCode() + " " + (picture ? "described" : "transcribed")
						+ ": " + (text != null ? text.length() : 0) + " character(s)");
			}
		} catch (RuntimeException exc) {
			throw new GeboIngestionException("Exception reading " + reference.getName() + " with a model", exc);
		} finally {
			try {
				is.close();
			} catch (Throwable th) {
			}
		}
		return out.stream();
	}
}
