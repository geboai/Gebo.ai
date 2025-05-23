/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.msformats.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * AI generated comments
 * 
 * Handler for Microsoft Word document formats (.doc and .docx).
 * This service extracts text content and metadata from Word documents
 * for ingestion into the system.
 */
@Service
public class MSWordIngestionHandler extends GAbstractConfiguredHandler {
    /**
     * Constructor for the MSWordIngestionHandler.
     * 
     * @param dao Data access object for ingestion handler configuration
     */
	public MSWordIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

    /**
     * Returns the unique code identifier for this ingestion handler.
     * 
     * @return The code identifying this handler
     */
	@Override
	public String getCode() {

		return "MSDocumentsIngestionHandler";
	}

	/**
	 * Popola la mappa metadata con titolo e sottotitolo da un XWPFDocument.
	 */
	private static void enrichDocxMetadata(XWPFDocument doc, Map<String, Object> metadata) {
		POIXMLProperties.CoreProperties cp = doc.getProperties().getCoreProperties();
		if (cp != null) {
			if (cp.getTitle() != null && !cp.getTitle().isEmpty()) {
				metadata.putIfAbsent(DocumentMetaInfos.TITLE, cp.getTitle());
			}
			if (cp.getSubject() != null && !cp.getSubject().isEmpty()) {
				metadata.putIfAbsent(DocumentMetaInfos.SUBTITLE, cp.getSubject());
			}
		}
		// fall‑back: cerca stile "Title" / "Subtitle" nelle prime righe
		for (XWPFParagraph p : doc.getParagraphs()) {
			String style = p.getStyle();
			if (style == null) {
				continue;
			}
			if ("Title".equalsIgnoreCase(style) && !metadata.containsKey(DocumentMetaInfos.TITLE)) {
				metadata.put(DocumentMetaInfos.TITLE, p.getText());
			} else if ("Subtitle".equalsIgnoreCase(style) && !metadata.containsKey(DocumentMetaInfos.SUBTITLE)) {
				metadata.put(DocumentMetaInfos.SUBTITLE, p.getText());
			}
			if (metadata.containsKey(DocumentMetaInfos.TITLE) && metadata.containsKey(DocumentMetaInfos.SUBTITLE)) {
				break;
			}
		}
	}

	/**
	 * Popola la mappa metadata con titolo e sottotitolo da un HWPFDocument.
	 */
	private static void enrichDocMetadata(HWPFDocument doc, Map<String, Object> metadata) {
		if (doc.getSummaryInformation() != null) {
			if (doc.getSummaryInformation().getTitle() != null && !doc.getSummaryInformation().getTitle().isEmpty()) {
				metadata.putIfAbsent(DocumentMetaInfos.TITLE, doc.getSummaryInformation().getTitle());
			}
			if (doc.getSummaryInformation().getSubject() != null
					&& !doc.getSummaryInformation().getSubject().isEmpty()) {
				metadata.putIfAbsent(DocumentMetaInfos.SUBTITLE, doc.getSummaryInformation().getSubject());
			}
		}
		// fall‑back: cerca paragrafo con stile "Title" / "Subtitle"
		Range rng = doc.getRange();
		for (int i = 0; i < rng.numParagraphs(); i++) {
			Paragraph p = rng.getParagraph(i);
			String style = p.getStyleIndex() == 0 ? null
					: doc.getStyleSheet().getStyleDescription(p.getStyleIndex()).getName();
			if (style == null)
				continue;
			if ("Title".equalsIgnoreCase(style) && !metadata.containsKey(DocumentMetaInfos.TITLE)) {
				metadata.put(DocumentMetaInfos.TITLE, p.text());
			} else if ("Subtitle".equalsIgnoreCase(style) && !metadata.containsKey(DocumentMetaInfos.SUBTITLE)) {
				metadata.put(DocumentMetaInfos.SUBTITLE, p.text());
			}
			if (metadata.containsKey(DocumentMetaInfos.TITLE) && metadata.containsKey(DocumentMetaInfos.SUBTITLE)) {
				break;
			}
		}
	}

    /**
     * Extracts text content from a DOCX format document.
     * 
     * @param is The input stream containing the DOCX document
     * @param metadata Map to store document metadata
     * @return Stream of Document objects containing the extracted content
     * @throws IOException If there's an error reading the input stream
     */
	private static Stream<Document> streamDOCXContent(InputStream is, Map<String, Object> metadata) throws IOException {
		XWPFDocument document = null;
		XWPFWordExtractor extractor = null;

		List<Document> outlist = new ArrayList<Document>();
		try {

			document = new XWPFDocument(is);

			extractor = new XWPFWordExtractor(document);
			enrichDocxMetadata(document, metadata);
			String text = extractor.getText();
			if (text != null && !text.isEmpty()) {
				outlist.add(new Document(text, metadata));
			}
		} finally {
			try {
				extractor.close();
			} catch (Throwable th) {
			}
			try {
				document.close();
			} catch (Throwable th) {
			}
			try {
				is.close();
			} catch (Throwable th) {
			}
		}
		return outlist.stream();
	}

    /**
     * Extracts text content from a DOC format document.
     * 
     * @param is The input stream containing the DOC document
     * @param metadata Map to store document metadata
     * @return Stream of Document objects containing the extracted content
     * @throws IOException If there's an error reading the input stream
     */
	private static Stream<Document> streamDOCContent(InputStream is, Map<String, Object> metadata) throws IOException {
		List<Document> outlist = new ArrayList<Document>();
		HWPFDocument document = null;
		WordExtractor extractor = null;
		try {

			document = new HWPFDocument(is);
			extractor = new WordExtractor(document);
			enrichDocMetadata(document, metadata);
			String text = extractor.getText();
			if (text != null && !text.isEmpty()) {
				outlist.add(new Document(text, metadata));
			}
		} finally {
			try {
				extractor.close();
			} catch (Throwable th) {
			}
			try {
				document.close();
			} catch (Throwable th) {
			}
			try {
				is.close();
			} catch (Throwable th) {
			}
		}
		return outlist.stream();
	}

    /**
     * Handles the content of a Word document, extracting text based on file extension.
     * 
     * @param reference Reference to the document being processed
     * @param is Input stream containing the document data
     * @param metadata Map to store document metadata
     * @return Stream of Document objects containing the extracted content
     * @throws GeboIngestionException If there's an error during ingestion
     * @throws IOException If there's an error reading the input stream
     */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		List<Document> outlist = new ArrayList<Document>();
		enrichMetaData(reference, metadata);
		if (reference.getExtension() != null && reference.getExtension().equalsIgnoreCase(".docx")) {
			// Process DOCX document
			return streamDOCXContent(is, metadata);
		} else if (reference.getExtension() != null && reference.getExtension().equalsIgnoreCase(".doc")) {
			// Process DOC document
			return streamDOCContent(is, metadata);
		}
		return outlist.stream();
	}

}