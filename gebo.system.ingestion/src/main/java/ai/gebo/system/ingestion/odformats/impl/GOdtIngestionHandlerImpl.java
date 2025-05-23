/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.odformats.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.element.OdfStylableElement;
import org.odftoolkit.odfdom.dom.element.office.OfficeTextElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.dom.element.text.TextTableOfContentElement;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextHeading;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.tables.AbstractTableData;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.IGTableDataHandler;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * AI generated comments
 * 
 * This service handles the ingestion of ODT (OpenDocument Text) files into the system.
 * It extracts text content and tables from ODT documents and converts them into Document objects.
 */
@Service
public class GOdtIngestionHandlerImpl extends GAbstractConfiguredHandler {
	/**
	 * Handler for processing table data extracted from documents
	 */
	@Autowired
	IGTableDataHandler tableRenderer;

	/**
	 * Constructor that initializes the handler with the required configuration DAO
	 * 
	 * @param dao The DAO providing configuration for the ingestion handler
	 */
	public GOdtIngestionHandlerImpl(IGIngestionHandlerConfigDao dao) {
		super(dao);

	}

	/**
	 * Returns the unique code identifier for this ingestion handler
	 * 
	 * @return The handler's code identifier
	 */
	@Override
	public String getCode() {

		return "OdtIngestionHandler";
	}

	/**
	 * Extracts metadata from the ODT document and enriches the provided metadata map
	 * Uses both document metadata and headings as fallbacks
	 * 
	 * @param doc The ODT document to extract metadata from
	 * @param meta The metadata map to update with extracted values
	 */
	private static void enrichOdtMetadata(OdfTextDocument doc, Map<String, Object> meta) {
		try {
			String title = doc.getOfficeMetadata().getTitle();
			if (title != null && !title.isEmpty()) {
				meta.putIfAbsent(DocumentMetaInfos.TITLE, title);
			}
			String subject = doc.getOfficeMetadata().getSubject();
			if (subject != null && !subject.isEmpty()) {
				meta.putIfAbsent(DocumentMetaInfos.SUBTITLE, subject);
			}

			// Fallback to first headings if meta.xml values are absent
			if (!meta.containsKey(DocumentMetaInfos.TITLE) || !meta.containsKey(DocumentMetaInfos.SUBTITLE)) {
				OfficeTextElement textRoot = doc.getContentRoot();
				OdfTextHeading heading = OdfElement.findFirstChildNode(OdfTextHeading.class, textRoot);
				while (heading != null) {
					String levelStr = heading.getAttribute("text:outline-level");
					int level = levelStr != null ? Integer.parseInt(levelStr) : 1;
					if (level == 1 && !meta.containsKey(DocumentMetaInfos.TITLE)) {
						String txt = heading.getTextContent();
						if (txt != null && !txt.isBlank()) {
							meta.put(DocumentMetaInfos.TITLE, txt.trim());
						}
					} else if (level == 2 && !meta.containsKey(DocumentMetaInfos.SUBTITLE)) {
						String txt = heading.getTextContent();
						if (txt != null && !txt.isBlank()) {
							meta.put(DocumentMetaInfos.SUBTITLE, txt.trim());
						}
					}
					if (meta.containsKey(DocumentMetaInfos.TITLE) && meta.containsKey(DocumentMetaInfos.SUBTITLE)) {
						break;
					}
					heading = OdfElement.findNextChildNode(OdfTextHeading.class, heading);
				}
			}
		} catch (Throwable ignored) {
		}

	}

	/**
	 * Processes the ODT document from an input stream and converts its content into Document objects
	 * 
	 * @param reference Document reference information
	 * @param is Input stream containing the ODT document
	 * @param metadata Metadata to be attached to the generated documents
	 * @return Stream of Document objects created from the ODT content
	 * @throws Exception If processing fails
	 */
	private Stream<Document> readFromStream(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws Exception {
		List<Document> documents = new ArrayList<Document>();
		try {
			OdfTextDocument odfdocument = OdfTextDocument.loadDocument(is);
			// https://odftoolkit.org/api/odfdom/index.html
			OfficeTextElement officeText = odfdocument.getContentRoot();
			OdfStylableElement firstChild = OdfElement.findFirstChildNode(OdfStylableElement.class, officeText);
			OdfStylableElement lastChild = firstChild;
			enrichOdtMetadata(odfdocument, metadata);
			if (lastChild != null) {
				StringBuffer stringBuffer = new StringBuffer();
				do {
					/*
					 * if (lastChild instanceof OdfTextHeading) { String text =
					 * lastChild.getTextContent(); // the previus part of the text if present will
					 * be output as a document if (stringBuffer.toString().trim().length() > 0) {
					 * documents.add(new Document(stringBuffer.toString(), metadata)); // starting a
					 * new stringbuffer stringBuffer = new StringBuffer(); } if (text != null) {
					 * stringBuffer.append(text + "\n"); } } else
					 */
					if (lastChild instanceof TextTableOfContentElement) {
						// Skipping the table of contents because is a duplication of contents
					} else if (lastChild instanceof TableTableElement) {
						// Create a document from any accumulated text before processing the table
						if (stringBuffer.toString().trim().length() > 0) {
							documents.add(new Document(stringBuffer.toString(), metadata));
							// starting a new stringbuffer
							stringBuffer = new StringBuffer();
						}
						TableTableElement tableElement = (TableTableElement) lastChild;
						AbstractTableData table = renderTable(tableElement);
						Stream<Document> tableDocuments = tableRenderer.handleContent(table, metadata);
						if (tableDocuments != null) {
							documents.addAll(tableDocuments.toList());
						}
					} else {
						String text = lastChild.getTextContent();
						if (text != null && text.trim().length() > 0) {
							stringBuffer.append(text + "\n");
						}
					}
				} while ((lastChild = OdfElement.findNextChildNode(OdfStylableElement.class, lastChild)) != null);
				// Create a final document from any remaining text
				if (stringBuffer.toString().trim().length() > 0) {
					documents.add(new Document(stringBuffer.toString(), metadata));
				}
			}
			return documents.stream();
		} finally {
			try {
				is.close();
			} catch (Throwable th) {
				// Silent close
			}
		}
	}

	/**
	 * Converts an ODF table element into an AbstractTableData representation
	 * 
	 * @param tableElement The ODF table element to process
	 * @return An AbstractTableData representing the table contents
	 */
	private static AbstractTableData renderTable(TableTableElement tableElement) {
		AbstractTableData tableData = new OpenDocumentTableData(tableElement);
		return tableData;
	}

	/**
	 * Main entry point for handling ODT document content
	 * Extracts content from the document and converts it to Document objects
	 * 
	 * @param reference Reference to the document being processed
	 * @param is Input stream containing the document data
	 * @param metadata Metadata to be included with the document content
	 * @return Stream of Document objects created from the document content
	 * @throws GeboIngestionException If ingestion processing fails
	 * @throws IOException If I/O operations fail
	 */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		try {
			enrichMetaData(reference, metadata);
			return readFromStream(reference, is, metadata);
		} catch (Exception e) {
			throw new GeboIngestionException("Exception reading ODT document", e);
		}

	}

}