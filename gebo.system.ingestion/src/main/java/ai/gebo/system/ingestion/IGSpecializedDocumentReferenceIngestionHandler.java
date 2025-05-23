/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.IGReadableContentsFormatHandler;
import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.model.IngestionFileType;

/**
 * AI generated comments
 * This interface defines specialized handlers for document reference ingestion.
 * It extends IGReadableContentsFormatHandler to provide functionality for reading
 * and processing different document formats during the ingestion process.
 */
public interface IGSpecializedDocumentReferenceIngestionHandler extends IGReadableContentsFormatHandler {

	/**
	 * Determines if this handler can manage the provided document reference.
	 *
	 * @param reference the document reference to check
	 * @return true if this handler can manage the content, false otherwise
	 */
	public boolean canManageContent(GDocumentReference reference);

	/**
	 * Gets the unique code identifier for this handler.
	 *
	 * @return the handler's code identifier
	 */
	public String getCode();

	/**
	 * Processes the content from the input stream according to the document reference.
	 *
	 * @param reference the document reference being processed
	 * @param is the input stream containing the document content
	 * @param manageMetaInfo additional metadata for processing
	 * @return a stream of Document objects
	 * @throws GeboIngestionException if there's an error during ingestion
	 * @throws IOException if there's an error reading from the input stream
	 */
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetaInfo) throws GeboIngestionException, IOException;

	/**
	 * Indicates if the handler can automatically tokenize content.
	 * 
	 * @return false by default, subclasses should override if they support auto-tokenization
	 */
	public default boolean isAutoTokenizeCapable() {
		return false;
	}

	/**
	 * Indicates if the content can be hashed.
	 * 
	 * @return true by default, subclasses should override if their content can't be hashed
	 */
	public default boolean isHashbleContent() {
		return true;
	}

	/**
	 * Indicates if the content can be represented as a single message.
	 * 
	 * @return true by default, subclasses should override if needed
	 */
	public default boolean isSingleMessageRappresentable() {
		return true;
	}

	/**
	 * Gets the list of file types this handler can process.
	 * 
	 * @return a list of supported IngestionFileType values
	 */
	public List<IngestionFileType> getHandledFileTypes();

	/**
	 * Implements the content format handler ID from the parent interface.
	 * 
	 * @return the handler's code as its ID
	 */
	@Override
	default String getContentFormatHandlerId() {
		return getCode();
	}

	/**
	 * Indicates if the handler can manage all parts of a document.
	 * 
	 * @return false by default, subclasses should override if they can handle all parts
	 */
	public default boolean isCanManageAllDocumentParts() {
		return false;
	}

	/**
	 * Processes text-only content from the input stream.
	 *
	 * @param reference the document reference being processed
	 * @param is the input stream containing the document content
	 * @param manageMetainfo additional metadata for processing
	 * @return a GeboDocument containing the processed text content
	 * @throws GeboIngestionException if there's an error during ingestion
	 * @throws IOException if there's an error reading from the input stream
	 */
	public GeboDocument handleTextOnlyContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetainfo) throws GeboIngestionException, IOException;

	/**
	 * Default implementation for handling a complete document.
	 * By default, it delegates to the text-only content handler.
	 *
	 * @param reference the document reference being processed
	 * @param is the input stream containing the document content
	 * @param manageMetainfo additional metadata for processing
	 * @return a GeboDocument representing the processed document
	 * @throws GeboIngestionException if there's an error during ingestion
	 * @throws IOException if there's an error reading from the input stream
	 */
	public default GeboDocument handleDocument(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetainfo) throws GeboIngestionException, IOException {
		return this.handleTextOnlyContent(reference, is, manageMetainfo);
	}
}