/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.text.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.document.model.GeboTextDocumentFragment;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * AI generated comments
 * 
 * Handler for processing plain text documents during ingestion.
 * This service extracts text content from plain text files and converts them
 * to Document objects for further processing in the system.
 */
@Service
public class TextPlainIngestionHandler extends GAbstractConfiguredHandler {

	/**
	 * Constructs a new TextPlainIngestionHandler with the provided configuration DAO.
	 * 
	 * @param dao The data access object for ingestion handler configurations
	 */
	public TextPlainIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

	/**
	 * Returns the unique code identifier for this handler.
	 * 
	 * @return The string code that identifies this handler
	 */
	@Override
	public String getCode() {
		return "TextPlainIngestionHandler";
	}

	/**
	 * Processes the content from the input stream and converts it to Document objects.
	 * Reads the entire input stream as a string and creates a document with the given metadata.
	 * 
	 * @param reference The document reference metadata
	 * @param is The input stream containing the text content
	 * @param metadata The metadata to associate with the document
	 * @return A stream of Document objects created from the input content
	 * @throws GeboIngestionException If an error occurs during ingestion
	 * @throws IOException If an I/O error occurs while reading the content
	 */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			FileCopyUtils.copy(is, bos);
			bos.flush();
			enrichMetaData(reference, metadata);
			Document document = new Document(bos.toString(), metadata);
			return List.of(document).stream();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
				// Silently ignore any errors when closing the stream
			}
		}
	}

	/**
	 * Processes the content for text-only extraction, producing a GeboDocument.
	 * 
	 * @param reference The document reference metadata
	 * @param is The input stream containing the text content
	 * @param manageMetainfo The metadata to associate with the document
	 * @return A GeboDocument containing the extracted text content
	 * @throws GeboIngestionException If an error occurs during ingestion
	 * @throws IOException If an I/O error occurs while reading the content
	 */
	@Override
	public GeboDocument handleTextOnlyContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetainfo) throws GeboIngestionException, IOException {
		return internalTextFileFormatTextOnlyContent(reference, reference.getContentType(), is, manageMetainfo);
	}
}