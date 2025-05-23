/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.InputStreamResource;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.document.model.GeboTextDocumentFragment;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGSpecializedDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.config.GeboContentReadingConfig;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * AI generated comments
 * 
 * A fallback document ingestion handler that uses Apache Tika to process various document types
 * that aren't covered by more specialized handlers. It dynamically discovers supported MIME types
 * and file extensions by parsing Tika's configuration file.
 */
//TODO: VIEW CONSEQUENCE OF INTEGRATING IT BEFORE LET IT RUN
//@Service
public class GTikaFailBackContentHandler implements IGSpecializedDocumentReferenceIngestionHandler {
	static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GTikaFailBackContentHandler.class);
	final GeboContentReadingConfig contentReaderConfig;
	private static final String TIKA_CONFIGURATION = "/org/apache/tika/mime/tika-mimetypes.xml";
	static List<String> handledExtensions = new ArrayList<>();
	static List<String> handledContenttypes = new ArrayList<>();
	static List<IngestionFileType> ingestionTypes = new ArrayList<>();
	static boolean initializedRuntime = false;

	/**
	 * Initializes the static configuration for Tika file type support.
	 * Reads the Tika configuration file to discover supportable MIME types and file extensions
	 * that are not already handled by other specialized ingestion handlers.
	 * 
	 * @param contentReaderConfig The configuration containing handlers and their supported file types
	 */
	static void initializeStaticRuntime(GeboContentReadingConfig contentReaderConfig) {
		if (!initializedRuntime) {
			TreeMap<String, Boolean> externallySupportedMimeTypes = new TreeMap<>();
			TreeMap<String, Boolean> externallySupportedExtensions = new TreeMap<>();
			for (IngestionHandlerConfig h : contentReaderConfig.getHandlers()) {
				for (IngestionFileType ft : h.getFileTypes()) {
					for (String ct : ft.getContentTypes()) {
						externallySupportedMimeTypes.put(ct, true);
					}
					for (String ex : ft.getExtensions()) {
						externallySupportedExtensions.put(ex, true);
					}
				}
			}
			InputStream is = GTikaFailBackContentHandler.class.getResourceAsStream(TIKA_CONFIGURATION);
			if (is == null) {
				LOGGER.error(TIKA_CONFIGURATION + " resource is not found, so TIKA FAILBACK WILL NOT WORK");
			} else {
				try {
					org.w3c.dom.Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
					NodeList mimeTypes = document.getDocumentElement().getElementsByTagName("mime-type");
					for (int i = 0; i < mimeTypes.getLength(); i++) {
						Node thisMime = mimeTypes.item(i);
						if (thisMime instanceof Element) {
							Element mime = (Element) thisMime;
							List<String> contentType = new ArrayList<>();
							List<String> extensions = new ArrayList<>();
							// primary mime type
							String primaryContentType = mime.getAttribute("type");
							if (primaryContentType != null) {
								boolean alreadySupportedExplicitly = externallySupportedMimeTypes
										.containsKey(primaryContentType);
								if (!externallySupportedMimeTypes.containsKey(primaryContentType))
									contentType.add(primaryContentType);

								// Secondary mime types
								NodeList aliases = mime.getElementsByTagName("alias");
								for (int j = 0; j < aliases.getLength(); j++) {
									Node n = aliases.item(j);
									if (n instanceof Element) {
										Element a = (Element) n;
										String secondaryMime = a.getAttribute("type");
										if (secondaryMime != null) {
											alreadySupportedExplicitly = alreadySupportedExplicitly
													|| externallySupportedMimeTypes.containsKey(secondaryMime);
											if (!externallySupportedMimeTypes.containsKey(secondaryMime))
												contentType.add(secondaryMime);
										}
									}
								}
								if (!contentType.isEmpty()) {
									
									NodeList globs = mime.getElementsByTagName("glob");
									for (int j = 0; j < globs.getLength(); j++) {
										Node globNode = globs.item(j);
										if (globNode instanceof Element) {
											Element glob = (Element) globNode;
											String pattern = glob.getAttribute("pattern");
											if (pattern != null) {
												pattern = pattern.replace("*", "");
												alreadySupportedExplicitly = alreadySupportedExplicitly
														|| externallySupportedExtensions.containsKey(pattern);
												if (!externallySupportedExtensions.containsKey(pattern))
													extensions.add(pattern);

											}
										}
									}
									if (alreadySupportedExplicitly) {
										LOGGER.info("Support for:" + primaryContentType
												+ " already on specific handler, will not be overridden");
										continue;
									}
									LOGGER.info("Configuring TIKA failback for types:" + contentType);
									IngestionFileType ingestionFileType = new IngestionFileType();
									ingestionFileType.setFileTypeId(primaryContentType);
									ingestionFileType.setExtensions(extensions);
									ingestionFileType.setContentTypes(contentType);
									NodeList comments = mime.getElementsByTagName("_comment");
									StringBuffer description = new StringBuffer();
									for (int c = 0; c < comments.getLength(); c++) {
										Node _comment = comments.item(c);
										if (_comment instanceof Element) {
											Element _c = (Element) _comment;
											if (_c.getTextContent() != null) {
												description.append(_c.getTextContent());
											}
										}
									}
									ingestionFileType.setDescription(description.toString());
									handledContenttypes.addAll(contentType);
									handledExtensions.addAll(extensions);
									ingestionTypes.add(ingestionFileType);
								}
							}

						}
					}
					LOGGER.info("Tika failback for logical file types:" + ingestionTypes.size());
					LOGGER.info("Tika content types:" + handledContenttypes);
					LOGGER.info("Tika extensions:" + handledExtensions);
				} catch (SAXException | IOException | ParserConfigurationException e) {
					LOGGER.error(TIKA_CONFIGURATION + " parsing whas unsuccessfull", e);
				}
				initializedRuntime = true;
			}
		}
	}

	/**
	 * Constructor for the Tika fallback handler.
	 * Initializes the static runtime configuration based on provided content reader config.
	 * 
	 * @param contentReaderConfig The configuration for content reading capabilities
	 */
	public GTikaFailBackContentHandler(GeboContentReadingConfig contentReaderConfig) {
		this.contentReaderConfig = contentReaderConfig;
		initializeStaticRuntime(contentReaderConfig);
	}

	/**
	 * Returns all file extensions that this handler can process.
	 * 
	 * @return List of file extensions supported by this handler
	 */
	@Override
	public List<String> getHandledExtensions() {
		return handledExtensions;
	}

	/**
	 * Returns all content types that this handler can process.
	 * 
	 * @return List of content types supported by this handler
	 */
	@Override
	public List<String> getHandledContentTypes() {
		return handledContenttypes;
	}

	/**
	 * Determines if this handler can process the given document reference.
	 * 
	 * @param reference The document reference to check
	 * @return true if this handler can process the document, false otherwise
	 */
	@Override
	public boolean canManageContent(GDocumentReference reference) {
		return (reference.getContentType() != null && handledContenttypes.contains(reference.getContentType()))
				|| (reference.getExtension() != null && handledExtensions.contains(reference.getExtension()));
	}

	/**
	 * Returns the unique identifier code for this handler.
	 * 
	 * @return The unique identifier code
	 */
	@Override
	public String getCode() {
		return "tika-failback-handler";
	}

	/**
	 * Processes the input stream associated with the document reference and converts it into Documents.
	 * Uses Tika to extract content and metadata from the input stream.
	 * 
	 * @param reference The document reference to process
	 * @param is The input stream containing the document content
	 * @param manageMetaInfo Additional metadata to include
	 * @return Stream of processed Document objects
	 * @throws GeboIngestionException If an error occurs during ingestion
	 * @throws IOException If an I/O error occurs
	 */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetaInfo) throws GeboIngestionException, IOException {
		TikaDocumentReader reader = new TikaDocumentReader(new InputStreamResource(is));
		List<Document> list = reader.get();
		List<Document> processed = new ArrayList<>();
		if (list != null) {
			for (Document document : list) {
				HashMap<String, Object> newMeta = new HashMap<>();
				if (document.getMetadata() != null) {
					newMeta.putAll(document.getMetadata());
				}
				if (manageMetaInfo != null)
					newMeta.putAll(manageMetaInfo);
				if (document.isText()) {
					Document out = new Document(document.getId(), document.getText(), newMeta);
					processed.add(out);
				} else if (document.getMedia() != null) {
					Document out = new Document(document.getId(), document.getMedia(), newMeta);
					processed.add(out);
				}
			}
		}
		return processed.stream();
	}

	/**
	 * Returns the file types that this handler can process.
	 * 
	 * @return List of ingestion file types
	 */
	@Override
	public List<IngestionFileType> getHandledFileTypes() {
		return ingestionTypes;
	}

	/**
	 * Converts a document reference to a GeboDocument with metadata.
	 * 
	 * @param reference The document reference to convert
	 * @param manageMetainfo Additional metadata to include
	 * @return A new GeboDocument instance
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
	 * Processes the document and extracts only its text content.
	 * 
	 * @param reference The document reference to process
	 * @param is The input stream containing the document content
	 * @param manageMetainfo Additional metadata to include
	 * @return A GeboDocument containing the extracted text
	 * @throws GeboIngestionException If an error occurs during ingestion
	 * @throws IOException If an I/O error occurs
	 */
	@Override
	public GeboDocument handleTextOnlyContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetainfo) throws GeboIngestionException, IOException {
		GeboDocument doc = toDocument(reference, manageMetainfo);
		Stream<Document> docstream = handleContent(reference, is, manageMetainfo);
		final StringBuffer buffer = new StringBuffer();
		docstream.forEach(x -> {
			if (x.isText()) {
				buffer.append(x.getText());
			}
		});
		doc.getTexts().add(GeboTextDocumentFragment.plainText(buffer.toString()));
		return doc;
	}
}