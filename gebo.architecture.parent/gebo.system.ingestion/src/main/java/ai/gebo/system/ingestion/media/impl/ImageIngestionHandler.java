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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.system.ingestion.EmbeddedPicture;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * Reads an image file (PNG, JPEG, GIF, WebP) as a document holding the image
 * itself as media, with the file's metadata. It takes a file only while images
 * are taken ({@code ai.gebo.images.ingestion-enabled}): otherwise an image file
 * is, as before, a file no reader takes. No model is called here.
 */
@Service
public class ImageIngestionHandler extends GAbstractConfiguredHandler {
	public static final String CODE = "ImageIngestionHandler";

	public ImageIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

	@Override
	public String getCode() {
		return CODE;
	}

	@Override
	public List<String> getHandledExtensions() {
		return ingestsImages() ? super.getHandledExtensions() : List.of();
	}

	@Override
	public List<String> getHandledContentTypes() {
		return ingestsImages() ? super.getHandledContentTypes() : List.of();
	}

	@Override
	public boolean canManageContent(GDocumentReference reference) {
		return ingestsImages() && super.canManageContent(reference);
	}

	@Override
	public boolean canManageContent(TypedInputStream streamContent) {
		return ingestsImages() && super.canManageContent(streamContent);
	}

	@Override
	public boolean isAutoTokenizeCapable() {
		return false;
	}

	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		enrichMetaData(reference, metadata);
		try {
			final byte[] data = is.readAllBytes();
			final String extension = reference.getExtension() != null ? reference.getExtension().toLowerCase(Locale.ROOT)
					: "";
			// the file is the image: its name is the document's, not one inside it
			return imageDocuments(reference,
					List.of(new EmbeddedPicture("file" + extension, reference.getContentType(), data)), metadata).stream()
					.peek(document -> document.getMetadata()
							.remove(ai.gebo.system.ingestion.config.ImagesIngestionConfig.IMAGE_NAME_METADATA));
		} catch (RuntimeException exc) {
			throw new GeboIngestionException("Exception reading the image " + reference.getName(), exc);
		} finally {
			try {
				is.close();
			} catch (Throwable th) {
			}
		}
	}
}
