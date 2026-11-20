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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * Reads a recording or a video file as a document holding the file itself as
 * media, with the file's metadata. Each kind is taken only while its switch of
 * the installation is on ({@code ai.gebo.recordings.ingestion-enabled},
 * {@code ai.gebo.videos.ingestion-enabled}): otherwise it is, as before, a file
 * no reader takes. No model is called here.
 */
@Service
public class RecordingsAndVideosIngestionHandler extends GAbstractConfiguredHandler {
	public static final String CODE = "RecordingsAndVideosIngestionHandler";
	private static final Map<String, String> TYPE_OF_EXTENSION = Map.of(".mp3", "audio/mpeg", ".mpga", "audio/mpeg",
			".wav", "audio/wav", ".m4a", "audio/mp4", ".mp4", "video/mp4", ".mpeg", "video/mpeg", ".webm", "video/webm");

	@Value("${ai.gebo.recordings.ingestion-enabled:false}")
	private boolean recordingsEnabled = false;
	@Value("${ai.gebo.videos.ingestion-enabled:false}")
	private boolean videosEnabled = false;
	/** A recording larger than this, in megabytes, is passed over. */
	@Value("${ai.gebo.recordings.max-recording-mb:20}")
	private int maxRecordingMb = 20;
	/** A video larger than this, in megabytes, is passed over. */
	@Value("${ai.gebo.videos.max-video-mb:20}")
	private int maxVideoMb = 20;

	public RecordingsAndVideosIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

	public void setSwitches(boolean recordingsEnabled, boolean videosEnabled) {
		this.recordingsEnabled = recordingsEnabled;
		this.videosEnabled = videosEnabled;
	}

	@Override
	public String getCode() {
		return CODE;
	}

	/** The media type of a file by its declared type, else its extension; null when it is neither kind. */
	private static String mediaType(String contentType, String extension) {
		final String type = contentType != null ? contentType.toLowerCase(Locale.ROOT) : "";
		if (type.startsWith("audio/") || type.startsWith("video/")) {
			return type.contains(";") ? type.substring(0, type.indexOf(';')).trim() : type;
		}
		return extension != null ? TYPE_OF_EXTENSION.get(extension.toLowerCase(Locale.ROOT)) : null;
	}

	private boolean taken(String extensionOrType) {
		if (extensionOrType == null) {
			return false;
		}
		final String value = extensionOrType.toLowerCase(Locale.ROOT);
		final String type = value.startsWith(".") ? TYPE_OF_EXTENSION.get(value) : value;
		if (type == null) {
			return false;
		}
		return type.startsWith("audio/") ? recordingsEnabled : type.startsWith("video/") && videosEnabled;
	}

	@Override
	public List<String> getHandledExtensions() {
		return super.getHandledExtensions().stream().filter(this::taken).toList();
	}

	@Override
	public List<String> getHandledContentTypes() {
		return super.getHandledContentTypes().stream().filter(this::taken).toList();
	}

	@Override
	public boolean canManageContent(GDocumentReference reference) {
		return reference != null && super.canManageContent(reference)
				&& taken(mediaType(reference.getContentType(), reference.getExtension()));
	}

	@Override
	public boolean canManageContent(TypedInputStream streamContent) {
		return streamContent != null && super.canManageContent(streamContent)
				&& taken(mediaType(streamContent.getContentType(), streamContent.getExtension()));
	}

	@Override
	public boolean isAutoTokenizeCapable() {
		return false;
	}

	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		final List<Document> out = new ArrayList<Document>();
		enrichMetaData(reference, metadata);
		try {
			final String type = mediaType(reference.getContentType(), reference.getExtension());
			if (type == null || !taken(type)) {
				return out.stream();
			}
			final long most = (type.startsWith("audio/") ? maxRecordingMb : maxVideoMb) * 1024L * 1024L;
			final byte[] data = is.readNBytes((int) Math.min(Integer.MAX_VALUE - 8L, most + 1));
			if (data.length == 0 || data.length > most) {
				LOGGER.warn("The file " + reference.getName() + " is empty or larger than "
						+ (type.startsWith("audio/") ? maxRecordingMb : maxVideoMb) + " MB: it is not taken");
				return out.stream();
			}
			out.add(Document.builder()
					.media(Media.builder().mimeType(MimeTypeUtils.parseMimeType(type)).data(data).name("media").build())
					.metadata(new HashMap<String, Object>(metadata != null ? metadata : new HashMap<>())).build());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("handleContent(...) " + reference.getCode() + " given as " + type + " media of "
						+ data.length + " byte(s)");
			}
			return out.stream();
		} catch (RuntimeException exc) {
			throw new GeboIngestionException("Exception reading the file " + reference.getName(), exc);
		} finally {
			try {
				is.close();
			} catch (Throwable th) {
			}
		}
	}
}
