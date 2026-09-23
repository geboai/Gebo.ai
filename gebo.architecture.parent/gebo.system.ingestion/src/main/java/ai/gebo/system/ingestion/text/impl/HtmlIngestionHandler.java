/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.text.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * HTML Ingestion Handler Service for processing HTML content.
 * This handler extracts text from HTML documents for further processing.
 * AI generated comments
 */
@Service
public class HtmlIngestionHandler extends GAbstractConfiguredHandler {

    /**
     * Constructor for HtmlIngestionHandler.
     * 
     * @param dao Data access object for ingestion handler configuration
     */
	public HtmlIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

    /**
     * Returns the code identifier for this handler.
     * 
     * @return String identifier for this handler
     */
	@Override
	public String getCode() {
		return "HtmlIngestionHandler";
	}

    /**
     * Processes HTML content into Document objects.
     * Extracts text from the HTML body tag using JSoup and creates a Document with the extracted text.
     * 
     * @param reference Reference to the document being processed
     * @param is InputStream containing the HTML content
     * @param metadata Map of metadata associated with the document
     * @return Stream of Document objects created from the HTML content
     * @throws GeboIngestionException If an error occurs during ingestion
     * @throws IOException If an I/O error occurs
     */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		try {
			enrichMetaData(reference, metadata);
			List<Document> docs = new ArrayList<Document>();
			String text = Jsoup.parse(is, "UTF-8", "").select("body").text();
			docs.add(new Document(text, metadata));
			return docs.stream();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
                // Silently ignore exceptions on close
			}
		}
	}

    /**
     * Processes HTML content into a GeboDocument with text-only content.
     * 
     * @param reference Reference to the document being processed
     * @param is InputStream containing the HTML content
     * @param manageMetainfo Map of metadata associated with the document
     * @return GeboDocument containing the processed text-only content
     * @throws GeboIngestionException If an error occurs during ingestion
     * @throws IOException If an I/O error occurs
     */
	@Override
	public GeboDocument handleTextOnlyContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetainfo) throws GeboIngestionException, IOException {
		return internalTextFileFormatTextOnlyContent(reference, "text/html", is, manageMetainfo);
	}
}