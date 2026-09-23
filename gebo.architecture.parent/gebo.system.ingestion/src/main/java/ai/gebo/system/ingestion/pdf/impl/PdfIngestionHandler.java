/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.pdf.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * AI generated comments
 * 
 * A service class that handles the ingestion of PDF documents.
 * This handler extracts text and metadata from PDF files for further processing
 * in the Gebo system.
 */
@Service
public class PdfIngestionHandler extends GAbstractConfiguredHandler {

    /**
     * Constructor for PdfIngestionHandler.
     * 
     * @param dao The configuration DAO for the ingestion handler
     */
    public PdfIngestionHandler(IGIngestionHandlerConfigDao dao) {
        super(dao);
    }

    /**
     * Returns the unique code identifier for this handler.
     * 
     * @return String code that identifies this handler
     */
    @Override
    public String getCode() {
        return "PdfIngestionHandler";
    }

    /**
     * Extracts and enriches metadata from a PDF document.
     * Attempts to extract title and subtitle information from PDF metadata.
     * If metadata is not available, it falls back to using the first two non-empty
     * lines of the first page.
     * 
     * @param reader The PDF reader containing the document to extract metadata from
     * @param meta The metadata map to be enriched
     * @throws IOException If there's an error reading the PDF
     */
    private static void enrichPdfMetadata(PdfReader reader, Map<String, Object> meta) throws IOException {
        Map<String, String> info = reader.getInfo();
        if (info != null) {
            String title = info.get(DocumentMetaInfos.TITLE);
            if (title != null && !title.isBlank()) {
                meta.putIfAbsent(DocumentMetaInfos.TITLE, title.trim());
            }
            String subject = info.get("Subject");
            if (subject != null && !subject.isBlank()) {
                meta.putIfAbsent(DocumentMetaInfos.SUBTITLE, subject.trim());
            }
        }
        // Fallback: use first (and second) non‑empty line(s) of page 1
        if (!meta.containsKey(DocumentMetaInfos.TITLE) || !meta.containsKey(DocumentMetaInfos.SUBTITLE)) {
            String pageOne = PdfTextExtractor.getTextFromPage(reader, 1);
            if (pageOne != null) {
                String[] lines = pageOne.split("\r?\n");
                List<String> nonEmpty = new ArrayList<>();
                for (String l : lines) {
                    if (!l.isBlank()) {
                        nonEmpty.add(l.trim());
                        if (nonEmpty.size() >= 2)
                            break;
                    }
                }
                if (!meta.containsKey(DocumentMetaInfos.TITLE) && nonEmpty.size() >= 1) {
                    meta.put(DocumentMetaInfos.TITLE, nonEmpty.get(0));
                }
                if (!meta.containsKey(DocumentMetaInfos.SUBTITLE) && nonEmpty.size() >= 2) {
                    meta.put(DocumentMetaInfos.SUBTITLE, nonEmpty.get(1));
                }
            }
        }
    }

    /**
     * Processes a PDF input stream, extracting text content and metadata.
     * Creates a separate Document object for each page in the PDF.
     * 
     * @param reference The document reference for the PDF
     * @param is The input stream containing the PDF data
     * @param metadata Initial metadata to be included with the document
     * @return A stream of Document objects, one per page of the PDF
     * @throws GeboIngestionException If there's an error during ingestion
     * @throws IOException If there's an error reading the PDF
     */
    @Override
    public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
            throws GeboIngestionException, IOException {
        PdfReader pdfReader = null;
        try {
            pdfReader = new PdfReader(is);
            enrichMetaData(reference, metadata);
            enrichPdfMetadata(pdfReader, metadata);
            int pages = pdfReader.getNumberOfPages();
            List<Document> docs = new ArrayList<Document>();
            for (int i = 1; i <= pages; i++) {
                String text = (PdfTextExtractor.getTextFromPage(pdfReader, i));
                Map<String, Object> clonedMetaData = new HashMap<String, Object>(
                        metadata != null ? metadata : new HashMap());
                clonedMetaData.put(DocumentMetaInfos.CONTENT_PAGE, "" + i);
                Document document = new Document(text, clonedMetaData);
                docs.add(document);
            }

            return docs.stream();
        } finally {
            try {
                pdfReader.close();
            } catch (Throwable t) {
                // Silent close
            }
            try {
                is.close();
            } catch (Throwable t) {
                // Silent close
            }
        }
    }
}