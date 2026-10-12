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
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;

/**
 * AI generated comments
 * 
 * Service class responsible for handling XML document ingestion.
 * This handler processes XML files by treating them as text-based content
 * and delegates to the parent class for common text handling operations.
 */
@Service
public class XmlIngestionHandler extends TextPlainIngestionHandler {

	/**
	 * Constructor for the XmlIngestionHandler.
	 * 
	 * @param dao The data access object that provides configuration information for ingestion handlers
	 */
	public XmlIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

	/**
	 * Returns the unique identifier code for this ingestion handler.
	 * 
	 * @return String representation of the handler code
	 */
	@Override
	public String getCode() {
		return "XmlIngestionHandler";
	}

	/**
	 * Processes an XML document from an input stream.
	 * Delegates to the parent class implementation with the specific MIME type for XML.
	 * 
	 * @param reference The document reference information
	 * @param is The input stream containing the XML document content
	 * @param manageMetainfo Map containing metadata information for the document
	 * @return A GeboDocument containing the processed content
	 * @throws GeboIngestionException If there is an error during the ingestion process
	 * @throws IOException If there is an I/O error reading from the input stream
	 */
	@Override
	public GeboDocument handleTextOnlyContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetainfo) throws GeboIngestionException, IOException {

		return internalTextFileFormatTextOnlyContent(reference, "text/xml", is, manageMetainfo);
	}
}