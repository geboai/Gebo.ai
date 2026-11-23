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

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.rtf.RTFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.DocumentTitles;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * Handler for Rich Text Format documents (.rtf).
 * This service extracts text content and metadata from RTF documents
 * for ingestion into the system. The text is read with the RTF parser of
 * Apache Tika, which decodes the code pages and the unicode escapes of the
 * format; the whole document gives one document, as a Word file does.
 * Pictures are not read.
 */
@Service
public class RtfIngestionHandler extends GAbstractConfiguredHandler {
	/**
	 * Constructor for the RtfIngestionHandler.
	 *
	 * @param dao Data access object for ingestion handler configuration
	 */
	public RtfIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

	/**
	 * Returns the unique code identifier for this ingestion handler.
	 *
	 * @return The code identifying this handler
	 */
	@Override
	public String getCode() {

		return "RtfIngestionHandler";
	}

	/**
	 * Fills the metadata with title, subtitle and author of the information group
	 * of an RTF document, when it has one.
	 */
	private static void enrichRtfMetadata(Metadata rtf, Map<String, Object> metadata) {
		DocumentTitles.putTitle(metadata, rtf.get(TikaCoreProperties.TITLE), "rtf info");
		DocumentTitles.putSubtitle(metadata, rtf.get(TikaCoreProperties.SUBJECT), "rtf info");
		DocumentTitles.putAuthor(metadata, rtf.get(TikaCoreProperties.CREATOR), "rtf info");
	}

	/**
	 * Handles the content of an RTF document, extracting its plain text.
	 *
	 * @param reference Reference to the document being processed
	 * @param is        Input stream containing the document data
	 * @param metadata  Map to store document metadata
	 * @return Stream of Document objects containing the extracted content
	 * @throws GeboIngestionException If the document cannot be read as RTF
	 * @throws IOException            If there's an error reading the input stream
	 */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		List<Document> outlist = new ArrayList<Document>();
		enrichMetaData(reference, metadata);
		try {
			// no limit on the characters read: the whole text of the document is indexed
			BodyContentHandler body = new BodyContentHandler(-1);
			Metadata rtf = new Metadata();
			// Tika 4 parsers take a TikaInputStream; closing it closes the stream it wraps
			try (TikaInputStream tis = TikaInputStream.get(is)) {
				new RTFParser().parse(tis, body, rtf, new ParseContext());
			}
			enrichRtfMetadata(rtf, metadata);
			String text = body.toString();
			if (text != null && !text.trim().isEmpty()) {
				outlist.add(new Document(text.trim(), metadata));
			}
		} catch (TikaException | SAXException | RuntimeException exc) {
			throw new GeboIngestionException("Exception reading RTF document", exc);
		} finally {
			try {
				is.close();
			} catch (Throwable th) {
			}
		}
		return outlist.stream();
	}

}
