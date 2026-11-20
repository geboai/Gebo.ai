/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.system.ingestion.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.util.FileCopyUtils;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.document.model.GeboTextDocumentFragment;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.IGSpecializedDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;
import ai.gebo.system.ingestion.model.SpecialFile;

/**
 * Abstract base class for document ingestion handlers that are configured via
 * database. AI generated comments
 * 
 * This class implements IGSpecializedDocumentReferenceIngestionHandler
 * interface and provides common functionality for specialized document
 * handlers, including configuration management, file type recognition, and
 * metadata processing.
 */
public abstract class GAbstractConfiguredHandler implements IGSpecializedDocumentReferenceIngestionHandler {
	/** Configuration for this handler */
	protected IngestionHandlerConfig config = null;
	/** List of file extensions this handler can process */
	protected List<String> extensions = new ArrayList<String>();
	/** List of content types this handler can process */
	protected List<String> contentTypes = new ArrayList<String>();
	/** List of special filenames this handler can process */
	protected List<String> specialfiles = new ArrayList<String>();
	/** Logger instance for this class */
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * Constructor that initializes the handler with configuration from the
	 * database.
	 * 
	 * @param dao The data access object used to retrieve the handler configuration
	 * @throws IllegalStateException if the handler configuration is not found
	 */
	public GAbstractConfiguredHandler(IGIngestionHandlerConfigDao dao) {
		this.config = dao.findByCode(getCode());
		if (this.config == null)
			throw new IllegalStateException("Unknown hanlder configuration:" + getCode());
		TreeMap<String, Boolean> uniqueExtensions = new TreeMap<String, Boolean>();
		TreeMap<String, Boolean> uniqueContentTypes = new TreeMap<String, Boolean>();
		for (IngestionFileType ft : this.config.getFileTypes()) {
			for (String ext : ft.getExtensions()) {
				uniqueExtensions.put(ext.toLowerCase(), true);
			}
			for (String ct : ft.getContentTypes()) {
				uniqueContentTypes.put(ct.toLowerCase(), true);
			}
			if (ft.getSpecialfiles() != null) {
				for (SpecialFile sf : ft.getSpecialfiles()) {
					if (sf.getFileName() != null)
						specialfiles.add(sf.getFileName());
				}
			}
		}
		this.extensions = new ArrayList<String>(uniqueExtensions.keySet());
		this.contentTypes = new ArrayList<String>(uniqueContentTypes.keySet());
		LOGGER.info("Initialized content handler:" + getCode() + " handing files:" + extensions + " content types:"
				+ contentTypes);
	}

	/**
	 * Returns the list of file extensions that this handler can process.
	 * 
	 * @return List of supported file extensions in lowercase
	 */
	@Override
	public List<String> getHandledExtensions() {
		return extensions;
	}

	/**
	 * Returns the list of content types that this handler can process.
	 * 
	 * @return List of supported content types in lowercase
	 */
	@Override
	public List<String> getHandledContentTypes() {
		return contentTypes;
	}

	/**
	 * Determines if this handler can process the given document reference.
	 * 
	 * @param reference The document reference to check
	 * @return true if this handler can process the document, false otherwise
	 */
	@Override
	public boolean canManageContent(GDocumentReference reference) {
		if (reference.getExtension() != null) {
			if (extensions.contains(reference.getExtension().toLowerCase()))
				return true;
		}
		if (reference.getContentType() != null) {
			if (contentTypes.contains(reference.getContentType().toLowerCase()))
				return true;
		}
		if (reference.getName() != null && specialfiles.contains(reference.getName()))
			return true;
		return false;
	}

	@Override
	public boolean canManageContent(TypedInputStream streamContent) {
		String extension = streamContent != null ? streamContent.getExtension() : null;
		String contentType = streamContent != null ? streamContent.getContentType() : null;
		if (extension != null) {
			if (extensions.contains(extension.toLowerCase()))
				return true;
		}
		if (contentType != null) {
			if (contentTypes.contains(contentType.toLowerCase()))
				return true;
		}

		return false;
	}

	/**
	 * Gets the file type configuration for the given document reference.
	 * 
	 * @param reference The document reference to get the file type for
	 * @return The matching IngestionFileType or null if not found
	 */
	/** Whether the images of the documents are taken, and within which limits; absent in a unit test. */
	@org.springframework.beans.factory.annotation.Autowired(required = false)
	private ai.gebo.system.ingestion.config.ImagesIngestionConfig imagesConfig;

	public void setImagesConfig(ai.gebo.system.ingestion.config.ImagesIngestionConfig imagesConfig) {
		this.imagesConfig = imagesConfig;
	}

	/** Whether the images of the documents are taken (ai.gebo.images.ingestion-enabled); else they are passed over as before. */
	protected boolean ingestsImages() {
		return imagesConfig != null && imagesConfig.isIngestionEnabled();
	}

	/**
	 * The images found in a document, each as a document of its own holding the
	 * image as media, with the metadata of the document's texts, the page or slide it
	 * is on when known and its name. Nothing when images are not taken. No model is
	 * called here: the image is given as it is. An image is passed over when it is
	 * not a PNG, JPEG, GIF or WebP picture, is smaller or larger than the limits, was
	 * already taken in this document (a logo on every page), or comes after the most
	 * images taken of one document.
	 */
	protected List<org.springframework.ai.document.Document> imageDocuments(GDocumentReference reference,
			List<ai.gebo.system.ingestion.EmbeddedPicture> pictures, Map<String, Object> metadata) {
		final List<org.springframework.ai.document.Document> images = new ArrayList<>();
		if (pictures == null || pictures.isEmpty() || !ingestsImages()) {
			return images;
		}
		final java.util.Set<String> seen = new java.util.HashSet<>();
		int passedOver = 0;
		for (ai.gebo.system.ingestion.EmbeddedPicture picture : pictures) {
			final String type = picture != null ? picture.readableType() : null;
			if (type == null || picture.data() == null || picture.data().length < imagesConfig.getMinImageBytes()
					|| picture.data().length > imagesConfig.getMaxImageBytes()
					|| !seen.add(picture.data().length + ":" + java.util.Arrays.hashCode(picture.data()))) {
				continue;
			}
			if (images.size() >= imagesConfig.getMaxImagesPerDocument()) {
				passedOver++;
				continue;
			}
			final Map<String, Object> cloned = new java.util.HashMap<>(
					metadata != null ? metadata : new java.util.HashMap<>());
			if (picture.page() != null) {
				cloned.put(ai.gebo.model.DocumentMetaInfos.CONTENT_PAGE, "" + picture.page());
			}
			if (picture.name() != null && !picture.name().isBlank()) {
				cloned.put(ai.gebo.system.ingestion.config.ImagesIngestionConfig.IMAGE_NAME_METADATA, picture.name());
			}
			images.add(org.springframework.ai.document.Document.builder()
					.media(org.springframework.ai.content.Media.builder()
							.mimeType(org.springframework.util.MimeTypeUtils.parseMimeType(type)).data(picture.data())
							.name("image").build())
					.metadata(cloned).build());
		}
		final String of = reference != null ? reference.getCode() : "a document";
		if (passedOver > 0) {
			LOGGER.warn(passedOver + " image(s) of " + of + " were not taken: only "
					+ imagesConfig.getMaxImagesPerDocument() + " of a document are");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("imageDocuments(" + of + ") -> " + images.size() + " of " + pictures.size()
					+ " picture(s) found");
		}
		return images;
	}

	protected IngestionFileType getFileType(GDocumentReference reference) {
		Optional<IngestionFileType> found = config.getFileTypes().stream().filter(x -> {
			if (reference.getExtension() != null) {
				return x.getExtensions().contains(reference.getExtension().toLowerCase());
			}
			return false;
		}).findFirst();
		return found.isPresent() ? found.get() : null;
	}

	/**
	 * Enriches metadata with file type information.
	 * 
	 * @param reference The document reference
	 * @param meta      The metadata map to enrich
	 */
	protected void enrichMetaData(GDocumentReference reference, Map<String, Object> meta) {
		IngestionFileType fileType = getFileType(reference);

		if (fileType != null) {
			if (fileType.getFileTypeId() != null)
				meta.put(DocumentMetaInfos.GEBO_FILE_TYPE_ID, fileType.getFileTypeId());
			if (fileType.getTreatAs() != null)
				meta.put(DocumentMetaInfos.GEBO_FILE_TREAT_AS, fileType.getTreatAs());
			if (fileType.getDescription() != null)
				meta.put(DocumentMetaInfos.GEBO_FILE_TYPE_DESCRIPTION, fileType.getDescription());
		}
	}

	/** Marker for the start of metadata block in document processing */
	static final String METADATA_BLOCK = "[METADATA]";
	/** Marker for the start of content block in document processing */
	static final String CONTENT_BLOCK = "[CONTENT]";

	/**
	 * Returns all file types that this handler is configured to process.
	 * 
	 * @return List of supported ingestion file types
	 */
	@Override
	public List<IngestionFileType> getHandledFileTypes() {
		List<IngestionFileType> ingestionft = new ArrayList<IngestionFileType>();
		ingestionft.addAll(this.config.getFileTypes());
		return ingestionft;
	}

	/**
	 * Processes an input stream containing text-only content into a GeboDocument.
	 * 
	 * @param reference      The document reference
	 * @param is             The input stream containing the document content
	 * @param manageMetainfo Metadata map for the document
	 * @return A GeboDocument containing the processed text content
	 * @throws GeboIngestionException If there is an error during ingestion
	 * @throws IOException            If there is an I/O error
	 */
	@Override
	public GeboDocument handleTextOnlyContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetainfo) throws GeboIngestionException, IOException {
		GeboDocument doc = toDocument(reference, manageMetainfo);
		final StringBuffer sb = new StringBuffer();
		Stream<Document> stream = handleContent(reference, is, manageMetainfo);
		if (stream != null) {
			stream.forEach(x -> {
				if (x.isText() && x.getText() != null && x.getText().trim().length() > 0)
					doc.getTexts().add(GeboTextDocumentFragment.plainText(x.getText()));
			});
		}

		return doc;
	}

	/**
	 * Converts a document reference to a GeboDocument with metadata.
	 * 
	 * @param reference      The document reference
	 * @param manageMetainfo Metadata map for the document
	 * @return A new GeboDocument with basic properties set
	 */
	protected GeboDocument toDocument(GDocumentReference reference, Map<String, Object> manageMetainfo) {
		GeboDocument doc = new GeboDocument();
		doc.setId(reference.getCode());
		doc.setParentPathId(reference.getParentVirtualFolderCode());
		doc.setContentType(reference.getContentType());
		doc.setName(reference.getName());
		doc.setExtension(reference.getExtension());
		doc.setFirstProcessedDate(reference.getDateCreated());
		doc.setLastProcessedDate(reference.getDateModified());
		doc.setOriginalContentModifiedDate(reference.getModificationDate());
		doc.getCustomMetaData().putAll(manageMetainfo);
		doc.setSize(reference.getFileSize());
		return doc;
	}

	/**
	 * Processes a text file in a specific internal format.
	 * 
	 * @param reference                 The document reference
	 * @param uniqueFragmentContentType The content type to assign to the text
	 *                                  fragment
	 * @param is                        The input stream containing the document
	 *                                  content
	 * @param manageMetainfo            Metadata map for the document
	 * @return A GeboDocument containing the processed text content
	 * @throws GeboIngestionException If there is an error during ingestion
	 * @throws IOException            If there is an I/O error
	 */
	protected GeboDocument internalTextFileFormatTextOnlyContent(GDocumentReference reference,
			String uniqueFragmentContentType, InputStream is, Map<String, Object> manageMetainfo)
			throws GeboIngestionException, IOException {
		GeboDocument doc = toDocument(reference, manageMetainfo);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		FileCopyUtils.copy(is, bos);
		bos.flush();
		GeboTextDocumentFragment textFragment = new GeboTextDocumentFragment();
		textFragment.setContentType(uniqueFragmentContentType);
		textFragment.setContent(bos.toString("UTF-8"));
		doc.getTexts().add(textFragment);
		return doc;
	}
}