/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.document.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import tools.jackson.core.exc.StreamWriteException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.ObjectMapper;

import ai.gebo.model.base.TypedInputStream;
import ai.gebo.model.virtualfs.VFilesystemReference;
import jakarta.el.MethodNotFoundException;
import lombok.Data;

/**
 * Represents a document with various attributes and methods to manipulate its
 * content. Includes functionality to manage different types of media fragments.
 * 
 * AI generated comments
 */
@Data
public final class GeboDocument implements JSonDuplicableObject<GeboDocument> {
	public static final String GEBO_FILE_EXTENSION = ".gebo";
	public static final String GEBO_DOCUMENT_CONTENT_TYPE = "text/ai.gebo.document";
	private static final ObjectMapper objectMapper = new ObjectMapper();
	// Unique identifier of the document.
	private String id = null;

	// Identifier for the parent path in the virtual file system.
	private String parentPathId = null;

	// URL associated with the document.
	private String url = null;

	// Name of the document.
	private String name = null;

	// File extension of the document.
	private String extension = null;

	// MIME type of the document content.
	private String contentType = null;

	// Date when the document was created.
	private Date createdDate = null;

	// Date when the document was first processed.
	private Date firstProcessedDate = null;

	// Date when the document was last processed.
	private Date lastProcessedDate = null;

	// Size of the document in bytes.
	private Long size = null;

	// Reference to the parent folder in the virtual file system.
	private VFilesystemReference virtualParentFolderReference = null;

	// Reference to the file in the virtual file system.
	private VFilesystemReference virtualFileReference = null;

	// Date when the original content of the document was last modified.
	private Date originalContentModifiedDate = null;

	// Custom metadata associated with the document.
	private Map<String, Object> customMetaData = new HashMap<>();

	// Additional attributes associated with the document.
	private Map<String, Object> additionalAttributes = new HashMap<>();

	// List of text document fragments.
	private List<GeboTextDocumentFragment> texts = new ArrayList<>();

	// List of image document fragments.
	private List<GeboImageDocumentFragment> images = new ArrayList<>();

	// List of audio document fragments.
	private List<GeboAudioDocumentFragment> audios = new ArrayList<>();

	// List of video document fragments.
	private List<GeboVideoDocumentFragment> videos = new ArrayList<>();

	// List of media document fragments.
	private List<GeboMediaDocumentFragment> media = new ArrayList<>();

	// List of cataloging labels.
	private List<CatalogingLabel> catalogingLabel = new ArrayList<>();

	/**
	 * Adds a media to the document by the parts of its MIME type; see
	 * {@link #addMedia(String, String, byte[])}.
	 */
	public void tryAddMedia(String name, String type, String subtype, String subtypeSuffix, Charset charset,
			byte[] dataAsByteArray) {
		if (type == null || subtype == null) {
			return;
		}
		addMedia(name, type + "/" + subtype, dataAsByteArray);
	}

	/**
	 * Adds a media to the document as a fragment of its kind - an image among the
	 * images, a recording among the audios, a video among the videos, anything else
	 * among the media - holding its bytes encoded in BASE64 and its content type.
	 *
	 * @param name        the name of the media in the document, may be null
	 * @param contentType the MIME type of the media, as image/png
	 * @param data        the bytes of the media
	 * @return the fragment added, or null when there is no type or no bytes
	 */
	public GeboBinaryDocumentFragment addMedia(String name, String contentType, byte[] data) {
		if (contentType == null || contentType.isBlank() || data == null || data.length == 0) {
			return null;
		}
		String type = contentType.trim().toLowerCase(java.util.Locale.ROOT);
		if (type.indexOf(';') > 0) {
			type = type.substring(0, type.indexOf(';')).trim();
		}
		final GeboBinaryDocumentFragment fragment;
		if (type.startsWith("image/")) {
			final GeboImageDocumentFragment image = new GeboImageDocumentFragment();
			images.add(image);
			fragment = image;
		} else if (type.startsWith("audio/")) {
			final GeboAudioDocumentFragment audio = new GeboAudioDocumentFragment();
			audios.add(audio);
			fragment = audio;
		} else if (type.startsWith("video/")) {
			final GeboVideoDocumentFragment video = new GeboVideoDocumentFragment(
					AbstractGeboDocumentFragment.DocumentFragmentType.VIDEO);
			videos.add(video);
			fragment = video;
		} else {
			final GeboMediaDocumentFragment other = new GeboMediaDocumentFragment();
			media.add(other);
			fragment = other;
		}
		fragment.setBytes(name, type, data);
		return fragment;
	}

	static public GeboDocument of(String text) {
		GeboDocument document = new GeboDocument();
		document.getTexts().add(GeboTextDocumentFragment.plainText(text));
		return document;
	}

	public TypedInputStream toTypedInputStream() throws IOException {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		objectMapper.writeValue(bos, this);
		bos.flush();
		final ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		return new TypedInputStream() {
			@Override
			public String getContentType() {

				return GEBO_DOCUMENT_CONTENT_TYPE;
			}

			@Override
			public String getExtension() {

				return GEBO_FILE_EXTENSION;
			}

			@Override
			public InputStream getInputStream() throws UnsupportedOperationException, IOException {

				return bis;
			}
		};
	}
	
}