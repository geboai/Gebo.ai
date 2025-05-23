/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.odformats.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.odftoolkit.odfdom.doc.OdfPresentationDocument;
import org.odftoolkit.odfdom.dom.element.draw.DrawPageElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawTextBoxElement;
import org.odftoolkit.odfdom.dom.element.office.OfficePresentationElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.incubator.doc.draw.OdfDrawFrame;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.IGTableDataHandler;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * AI generated comments
 * 
 * A service implementation for handling OpenDocument Presentation (ODP) files.
 * This class processes ODP documents for content extraction and metadata enrichment.
 */
@Service
public class GOdpIngestionHandlerImpl extends GAbstractConfiguredHandler {
	@Autowired
	IGTableDataHandler tableRenderer;

	/**
	 * Constructor that initializes the handler with configuration data access object.
	 * 
	 * @param dao The data access object for handler configuration
	 */
	public GOdpIngestionHandlerImpl(IGIngestionHandlerConfigDao dao) {
		super(dao);

	}

	/**
	 * Returns the unique code identifier for this handler.
	 * 
	 * @return The string code that identifies this handler
	 */
	@Override
	public String getCode() {

		return "OdpIngestionHandler";
	}

	/**
	 * Extracts table data from an ODF element.
	 * 
	 * @param element The ODF element to search for tables
	 * @return A list of OpenDocumentTableData objects representing tables found
	 */
	private static List<OpenDocumentTableData> getTablesChilds(OdfElement element) {
		List<OpenDocumentTableData> tabs = new ArrayList<OpenDocumentTableData>();
		TableTableElement tElement = OdfElement.findFirstChildNode(TableTableElement.class, element);
		if (tElement != null) {
			do {
				tabs.add(new OpenDocumentTableData(tElement));
			} while ((tElement = OdfElement.findNextChildNode(TableTableElement.class, tElement)) != null);
		}
		return tabs;
	}

	/**
	 * Enriches metadata with information extracted from the ODP document.
	 * Attempts to extract title and subtitle from metadata or from the first slide.
	 * 
	 * @param doc The ODP document to extract metadata from
	 * @param metadata The metadata map to be enriched
	 */
	private static void enrichOdpMetadata(OdfPresentationDocument doc, Map<String, Object> metadata) {
		try {
			String metaTitle = doc.getOfficeMetadata().getTitle();
			if (metaTitle != null && !metaTitle.isEmpty()) {
				metadata.putIfAbsent(DocumentMetaInfos.TITLE, metaTitle);
			}
			String metaSubject = doc.getOfficeMetadata().getSubject();
			if (metaSubject != null && !metaSubject.isEmpty()) {
				metadata.putIfAbsent(DocumentMetaInfos.SUBTITLE, metaSubject);
			}

			// Fallback: first slide's title box
			if (!metadata.containsKey("title")) {
				OfficePresentationElement pres = doc.getContentRoot();
				DrawPageElement slide = OdfElement.findFirstChildNode(DrawPageElement.class, pres);
				if (slide != null) {
					OdfDrawFrame frame = OdfElement.findFirstChildNode(OdfDrawFrame.class, slide);
					while (frame != null) {
						DrawTextBoxElement tb = OdfElement.findFirstChildNode(DrawTextBoxElement.class, frame);
						if (tb != null) {
							OdfTextParagraph p = OdfElement.findFirstChildNode(OdfTextParagraph.class, tb);
							if (p != null) {
								String text = p.getTextContent();
								if (text != null && !text.isBlank()) {
									metadata.put(DocumentMetaInfos.TITLE, text.trim());
									// look for subtitle – very next paragraph if any
									OdfTextParagraph next = OdfElement.findNextChildNode(OdfTextParagraph.class, p);
									if (next != null && !next.getTextContent().isBlank()) {
										metadata.put(DocumentMetaInfos.SUBTITLE, next.getTextContent().trim());
									}
									break;
								}
							}
						}
						frame = OdfElement.findNextChildNode(OdfDrawFrame.class, frame);
					}
				}
			}
		} catch (Throwable ignored) {
			// Silently ignore any exceptions during metadata extraction
		}
	}

	/**
	 * Processes an ODP document input stream and converts it to a stream of Document objects.
	 * Extracts text content from each slide and applies metadata.
	 * 
	 * @param is The input stream containing the ODP document
	 * @param manageMetaInfo The metadata map to be included in the documents
	 * @return A stream of Document objects extracted from the ODP
	 * @throws Exception If an error occurs during processing
	 */
	private static Stream<Document> streamContent(InputStream is, Map<String, Object> manageMetaInfo) throws Exception {
		Stream<Document> documents = Stream.of();
		OdfPresentationDocument document = OdfPresentationDocument.loadDocument(is);
		OfficePresentationElement presentation = document.getContentRoot();
		DrawPageElement element = OdfElement.findFirstChildNode(DrawPageElement.class, presentation);
		enrichOdpMetadata(document, manageMetaInfo);
		if (element != null) {
			do {
				List<OpenDocumentTableData> tabs = new ArrayList<OpenDocumentTableData>();
				OdfDrawFrame child = OdfElement.findFirstChildNode(OdfDrawFrame.class, element);
				StringBuffer sb = new StringBuffer();
				if (child != null) {
					do {

						DrawTextBoxElement tb = OdfElement.findFirstChildNode(DrawTextBoxElement.class, child);
						if (tb != null) {

							do {
								OdfTextParagraph paragraph = OdfElement.findFirstChildNode(OdfTextParagraph.class, tb);
								if (paragraph != null) {
									do {
										String _text = paragraph.getTextContent();
										if (_text != null && _text.trim().length() > 0) {
											sb.append(_text);
										}
									} while ((paragraph = OdfElement.findNextChildNode(OdfTextParagraph.class,
											paragraph)) != null);
								}
							} while ((tb = OdfElement.findNextChildNode(DrawTextBoxElement.class, tb)) != null);
						}
						/*
						 * if (child instanceof TableTableElement) { TableTableElement tableElement =
						 * (TableTableElement) child; OpenDocumentTableData table = new
						 * OpenDocumentTableData(tableElement); tabs.add(table); } else { String _text =
						 * child.getTextContent(); if (_text != null && _text.trim().length() > 0) {
						 * sb.append(_text); } }
						 */
					} while ((child = OdfElement.findNextChildNode(OdfDrawFrame.class, child)) != null);

				}
				String _content = sb.toString();
				if (_content != null && _content.trim().length() > 0) {
					documents = Stream.concat(documents, Stream.of(new Document(_content, manageMetaInfo)));
				}
				/*
				 * for (OpenDocumentTableData tbd : tabs) { documents=Stream.concat(documents,
				 * tbd.streamRows()); }
				 */
			} while ((element = OdfElement.findNextChildNode(DrawPageElement.class, element)) != null);
		}
		return documents;
	}

	/**
	 * Main method for processing ODP documents. Takes a reference and input stream,
	 * processes the content, and returns a stream of Document objects.
	 * 
	 * @param reference Document reference for the ODP file
	 * @param is Input stream containing the ODP document
	 * @param manageMetaInfo Metadata map to be included in the documents
	 * @return A stream of Document objects extracted from the ODP
	 * @throws GeboIngestionException If an error occurs during processing
	 */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetaInfo) throws GeboIngestionException {
		try {
			enrichMetaData(reference, manageMetaInfo);
			return streamContent(is, manageMetaInfo);
		} catch (Exception exc) {
			throw new GeboIngestionException("Exception reading ODP presentation document", exc);
		} finally {
			try {
				is.close();
			} catch (Throwable th) {
				// Silently ignore close exceptions
			}
		}

	}

}