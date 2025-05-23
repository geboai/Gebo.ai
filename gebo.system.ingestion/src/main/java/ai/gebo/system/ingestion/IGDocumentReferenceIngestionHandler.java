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
import java.util.stream.Stream;

import org.springframework.ai.document.Document;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.model.IngestionFileType;

/**
 * AI generated comments
 * 
 * This interface defines the contract for handlers that ingest document references
 * in the Gebo system. It provides methods to check if a document reference can be handled,
 * process document content, and determine supported file types and content formats.
 */
public interface IGDocumentReferenceIngestionHandler {
	/**
	 * Data class that contains the results of document ingestion.
	 * It holds the document stream and flags indicating the nature of the content.
	 */
	public static class IngestionHandlerData {
		Stream<Document> stream = null;

		boolean isHashableContent = false;
		boolean isUnmanagedContent = false;
		boolean singleMessageRappresentable = false;

		/**
		 * Returns the stream of documents.
		 * @return Stream of processed documents
		 */
		public Stream<Document> getStream() {
			return stream;
		}

		/**
		 * Sets the stream of documents.
		 * @param stream The document stream to set
		 */
		public void setStream(Stream<Document> stream) {
			this.stream = stream;
		}

		/**
		 * Checks if the content can be hashed.
		 * @return true if content is hashable, false otherwise
		 */
		public boolean isHashableContent() {
			return isHashableContent;
		}

		/**
		 * Sets whether the content can be hashed.
		 * @param isHashableContent Boolean flag indicating if content is hashable
		 */
		public void setHashableContent(boolean isHashableContent) {
			this.isHashableContent = isHashableContent;
		}

		/**
		 * Checks if the content is unmanaged.
		 * @return true if content is unmanaged, false otherwise
		 */
		public boolean isUnmanagedContent() {
			return isUnmanagedContent;
		}

		/**
		 * Sets whether the content is unmanaged.
		 * @param isUnmanagedContent Boolean flag indicating if content is unmanaged
		 */
		public void setUnmanagedContent(boolean isUnmanagedContent) {
			this.isUnmanagedContent = isUnmanagedContent;
		}

		/**
		 * Checks if the content can be represented as a single message.
		 * @return true if content can be represented as a single message, false otherwise
		 */
		public boolean isSingleMessageRappresentable() {
			return singleMessageRappresentable;
		}

		/**
		 * Sets whether the content can be represented as a single message.
		 * @param singleMessageRappresentable Boolean flag indicating if content is representable as a single message
		 */
		public void setSingleMessageRappresentable(boolean singleMessageRappresentable) {
			this.singleMessageRappresentable = singleMessageRappresentable;
		}
	}

	/**
	 * Determines if this handler can process the given document reference.
	 * @param reference The document reference to check
	 * @return true if the reference can be handled by this ingestion handler, false otherwise
	 */
	public boolean isHandled(GDocumentReference reference);

	/**
	 * Checks if this handler supports the specified content type.
	 * @param contentType The content type to check
	 * @return true if the content type is supported, false otherwise
	 */
	public boolean isHandledContentType(String contentType);

	/**
	 * Checks if this handler supports the specified file extension.
	 * @param extension The file extension to check
	 * @return true if the extension is supported, false otherwise
	 */
	public boolean isHandledExtension(String extension);

	/**
	 * Processes the content from the input stream based on the document reference.
	 * @param reference The document reference containing metadata
	 * @param is The input stream containing the document content
	 * @return IngestionHandlerData containing the processing results
	 * @throws GeboIngestionException If an error occurs during ingestion
	 * @throws IOException If an I/O error occurs
	 */
	public IngestionHandlerData handleContent(GDocumentReference reference, InputStream is)
			throws GeboIngestionException, IOException;

	/**
	 * Returns the list of file types supported by this handler.
	 * @return List of supported ingestion file types
	 */
	public List<IngestionFileType> getHandledFileTypes();

	/**
	 * Returns the list of content types supported by this handler.
	 * @return List of supported content types
	 */
	public List<String> getHandledContentTypes();

	/**
	 * Returns the list of file extensions supported by this handler.
	 * @return List of supported file extensions
	 */
	public List<String> getHandledExtensions();

	/**
	 * Determines if the given document reference can be processed by this handler.
	 * @param docref The document reference to check
	 * @return true if the document reference can be processed, false otherwise
	 */
	public boolean isProcessable(GDocumentReference docref);
	
	/**
	 * Handles text-only content and converts it to a GeboDocument.
	 * @param reference The document reference containing metadata
	 * @param is The input stream containing the text content
	 * @return A GeboDocument representation of the text content
	 * @throws GeboIngestionException If an error occurs during ingestion
	 * @throws IOException If an I/O error occurs
	 */
	public GeboDocument handleTextOnlyContent(GDocumentReference reference, InputStream is)
			throws GeboIngestionException, IOException;
			
	/**
	 * Processes a document from the input stream and creates a GeboDocument.
	 * @param reference The document reference containing metadata
	 * @param is The input stream containing the document content
	 * @return A GeboDocument representation of the processed document
	 * @throws GeboIngestionException If an error occurs during ingestion
	 * @throws IOException If an I/O error occurs
	 */
	public GeboDocument handleDocument(GDocumentReference reference, InputStream is)
			throws GeboIngestionException, IOException;

}