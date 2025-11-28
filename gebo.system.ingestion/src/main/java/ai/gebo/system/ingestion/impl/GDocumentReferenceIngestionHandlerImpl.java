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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGSpecializedDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.model.IngestionFileType;

/**
 * Implementation of the document reference ingestion handler interface. This
 * service manages the ingestion of document references by delegating to
 * specialized handlers based on content type and file extension.
 * 
 * AI generated comments
 */
@Service
public class GDocumentReferenceIngestionHandlerImpl implements IGDocumentReferenceIngestionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GDocumentReferenceIngestionHandlerImpl.class);
	protected final SpecializedHandlerRepositoryPattern repoPattern;

	/**
	 * Map of content types that can be handled by this ingestion handler
	 */
	protected final TreeMap<String, Boolean> handledContentTypes = new TreeMap<String, Boolean>();

	/**
	 * Map of file extensions that can be handled by this ingestion handler
	 */
	protected final TreeMap<String, Boolean> handledExtensions = new TreeMap<String, Boolean>();

	/**
	 * Constructor that initializes the handler with a repository of specialized
	 * handlers. Collects all supported content types and extensions from the
	 * specialized handlers.
	 * 
	 * @param repoPattern Repository containing specialized document reference
	 *                    ingestion handlers
	 */
	public GDocumentReferenceIngestionHandlerImpl(SpecializedHandlerRepositoryPattern repoPattern) {
		this.repoPattern = repoPattern;
		List<IGSpecializedDocumentReferenceIngestionHandler> impls = repoPattern.getImplementations();
		for (IGSpecializedDocumentReferenceIngestionHandler handler : impls) {
			List<String> contentTypes = handler.getHandledContentTypes();
			for (String ct : contentTypes) {
				handledContentTypes.put(ct, true);
			}
			List<String> extensions = handler.getHandledExtensions();
			for (String ct : extensions) {
				handledExtensions.put(ct, true);
			}
		}

	}

	/**
	 * Extracts metadata from the document reference and creates a map of metadata
	 * properties.
	 * 
	 * @param reference The document reference containing metadata
	 * @return A map containing the metadata properties
	 */
	private Map<String, Object> manageMetaInfo(GDocumentReference reference) {
		Map<String, Object> meta = new HashMap<String, Object>();
		if (reference.getCode() != null) {
			meta.put(DocumentMetaInfos.CONTENT_CODE, reference.getCode());
		}
		if (reference.getDescription() != null) {
			meta.put(DocumentMetaInfos.CONTENT_DESCRIPTION, reference.getDescription());
		}
		if (reference.getContentType() != null) {
			meta.put(DocumentMetaInfos.CONTENT_TYPE, reference.getContentType());
		}
		if (reference.getParentProjectCode() != null) {
			meta.put(DocumentMetaInfos.PROJECT_CODE, reference.getParentProjectCode());
		}
		if (reference.getRootKnowledgebaseCode() != null) {
			meta.put(DocumentMetaInfos.KNOWLEDGEBASE_CODE, reference.getRootKnowledgebaseCode());
		}
		if (reference.getUri() != null) {
			meta.put(DocumentMetaInfos.CONTENT_ORIGINAL_URL, reference.getUri());
		}
		if (reference.getExtension() != null) {
			meta.put(DocumentMetaInfos.CONTENT_EXTENSION, reference.getExtension());
		}
		if (reference.getName() != null) {
			meta.put(DocumentMetaInfos.GEBO_FILE_NAME, reference.getName());
			meta.put("file_name", reference.getName());
		}
		if (reference.getReferenceType() != null) {
			meta.put(DocumentMetaInfos.GEBO_REFERENCE_TYPE, reference.getReferenceType().name());
		}
		if (reference.getRelativePath() != null) {
			meta.put(DocumentMetaInfos.GEBO_FILE_RELATIVE_PATH, reference.getRelativePath());
		}
		if (reference.getNestedInArchive() == null || !reference.getNestedInArchive()) {
			if (reference.getAbsolutePath() != null) {
				meta.put(DocumentMetaInfos.GEBO_FILE_FULLPATH, reference.getAbsolutePath());
			}
		} else {
			if (reference.getAbsoluteArchivePath() != null && reference.getArchiveInternalPath() != null) {
				meta.put(DocumentMetaInfos.GEBO_ARCHIVE_FULLPATH, reference.getAbsoluteArchivePath());
				meta.put(DocumentMetaInfos.GEBO_ARCHIVE_INTERNALPATH, reference.getArchiveInternalPath());
			}
		}
		return meta;

	}

	/**
	 * Handles the content of a document reference by finding an appropriate
	 * specialized handler and delegating the processing to it.
	 * 
	 * @param reference The document reference to process
	 * @param is        The input stream containing the document content
	 * @return IngestionHandlerData containing the processed content and metadata
	 * @throws GeboIngestionException If an error occurs during ingestion
	 * @throws IOException            If an I/O error occurs
	 */
	@Override
	public IngestionHandlerData handleContent(GDocumentReference reference, InputStream is)
			throws GeboIngestionException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin handleContent([" + reference.getCode() + ",...)");
		}
		Stream<Document> outContents = Stream.of();
		IGSpecializedDocumentReferenceIngestionHandler handler = repoPattern.findByCanManage(reference);
		IngestionHandlerData data = new IngestionHandlerData();
		if (handler != null) {

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Specific content handler=>" + handler.getCode());
			}
			data.setSingleMessageRappresentable(handler.isSingleMessageRappresentable());
			data.setHashableContent(handler.isHashbleContent());
			outContents = handler.handleContent(reference, is, manageMetaInfo(reference));
		} else {
			data.setUnmanagedContent(true);
		}
		data.setStream(outContents);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End handleContent([" + reference.getCode() + ",...)");
		}
		return data;
	}

	/**
	 * Retrieves a list of all file types that can be handled by the registered
	 * specialized handlers.
	 * 
	 * @return A list of supported ingestion file types
	 */
	@Override
	public List<IngestionFileType> getHandledFileTypes() {
		List<IngestionFileType> fts = new ArrayList<IngestionFileType>();
		List<IGSpecializedDocumentReferenceIngestionHandler> impls = repoPattern.getImplementations();
		for (IGSpecializedDocumentReferenceIngestionHandler handler : impls) {
			fts.addAll(handler.getHandledFileTypes());

		}
		return fts;
	}

	/**
	 * Returns a list of all content types that can be handled by this ingestion
	 * handler.
	 * 
	 * @return A list of supported content types
	 */
	@Override
	public List<String> getHandledContentTypes() {

		return new ArrayList<String>(handledContentTypes.keySet());
	}

	/**
	 * Returns a list of all file extensions that can be handled by this ingestion
	 * handler.
	 * 
	 * @return A list of supported file extensions
	 */
	@Override
	public List<String> getHandledExtensions() {

		return new ArrayList<String>(handledExtensions.keySet());
	}

	/**
	 * Checks if a document reference can be handled based on its content type or
	 * extension.
	 * 
	 * @param reference The document reference to check
	 * @return true if the document can be handled, false otherwise
	 */
	@Override
	public boolean isHandled(GDocumentReference reference) {
		String ct = reference.getContentType();
		String ext = reference.getExtension();
		return isHandledContentType(ct) || isHandledExtension(ext);
	}

	/**
	 * Checks if a specific content type can be handled by this ingestion handler.
	 * 
	 * @param contentType The content type to check
	 * @return true if the content type can be handled, false otherwise
	 */
	@Override
	public boolean isHandledContentType(String contentType) {
		if (contentType == null || contentType.trim().length() == 0)
			return false;
		return this.handledContentTypes.containsKey(contentType.toLowerCase().trim());
	}

	/**
	 * Checks if a specific file extension can be handled by this ingestion handler.
	 * 
	 * @param extension The file extension to check
	 * @return true if the extension can be handled, false otherwise
	 */
	@Override
	public boolean isHandledExtension(String extension) {
		if (extension == null || extension.trim().length() == 0)
			return false;
		return this.handledExtensions.containsKey(extension.toLowerCase().trim());
	}

	/**
	 * Checks if a document reference is processable by this handler. Currently,
	 * it's an alias for isHandled().
	 * 
	 * @param docref The document reference to check
	 * @return true if the document can be processed, false otherwise
	 */
	@Override
	public boolean isProcessable(GDocumentReference docref) {
		return isHandled(docref);
	}

	/**
	 * Processes a document's content as text only by finding an appropriate
	 * specialized handler and delegating the processing to it.
	 * 
	 * @param reference The document reference to process
	 * @param is        The input stream containing the document content
	 * @return A GeboDocument containing the processed text content, or null if no
	 *         handler is available
	 * @throws GeboIngestionException If an error occurs during ingestion
	 * @throws IOException            If an I/O error occurs
	 */
	@Override
	public GeboDocument handleTextOnlyContent(GDocumentReference reference, InputStream is)
			throws GeboIngestionException, IOException {
		IGSpecializedDocumentReferenceIngestionHandler handler = repoPattern.findByCanManage(reference);
		if (handler == null)
			return null;
		else
			return handler.handleTextOnlyContent(reference, is, manageMetaInfo(reference));
	}

	/**
	 * Processes a document by finding an appropriate specialized handler and
	 * delegating the processing to it.
	 * 
	 * @param reference The document reference to process
	 * @param is        The input stream containing the document content
	 * @return A GeboDocument containing the processed document, or null if no
	 *         handler is available
	 * @throws GeboIngestionException If an error occurs during ingestion
	 * @throws IOException            If an I/O error occurs
	 */
	@Override
	public GeboDocument handleDocument(GDocumentReference reference, InputStream is)
			throws GeboIngestionException, IOException {

		IGSpecializedDocumentReferenceIngestionHandler handler = repoPattern.findByCanManage(reference);
		if (handler == null)
			return null;
		else
			return handler.handleDocument(reference, is, manageMetaInfo(reference));
	}

}