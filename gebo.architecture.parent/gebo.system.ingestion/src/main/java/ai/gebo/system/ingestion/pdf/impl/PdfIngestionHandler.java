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
import ai.gebo.system.ingestion.DocumentTitles;
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
    /** The title, the subject and the author in the PDF document information dictionary. */
    static final String PDF_INFO_TITLE = "Title", PDF_INFO_SUBJECT = "Subject", PDF_INFO_AUTHOR = "Author";
    private final PdfIngestionConfig config;

    /**
     * Constructor for PdfIngestionHandler.
     * 
     * @param dao The configuration DAO for the ingestion handler
     * @param config Whether a page in columns is read column by column
     */
    public PdfIngestionHandler(IGIngestionHandlerConfigDao dao, PdfIngestionConfig config) {
        super(dao);
        this.config = config;
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
    static void enrichPdfMetadata(PdfReader reader, Map<String, Object> meta) throws IOException {
        Map<String, String> info = reader.getInfo();
        if (info != null) {
            // the keys of the document information dictionary, as the PDF names them
            DocumentTitles.putTitle(meta, DocumentTitles.pdfInfoTitle(info.get(PDF_INFO_TITLE)), "pdf info");
            DocumentTitles.putSubtitle(meta, info.get(PDF_INFO_SUBJECT), "pdf info");
            DocumentTitles.putAuthor(meta, info.get(PDF_INFO_AUTHOR), "pdf info");
        }
        // Fallback: use first (and second) non‑empty line(s) of page 1
        if (!DocumentTitles.hasTitle(meta) || !DocumentTitles.hasSubtitle(meta)) {
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
                if (nonEmpty.size() >= 1) {
                    DocumentTitles.putTitle(meta, nonEmpty.get(0), "pdf first page");
                }
                if (nonEmpty.size() >= 2) {
                    DocumentTitles.putSubtitle(meta, nonEmpty.get(1), "pdf first page");
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
    /** The resolution a page without text is rendered at to be given as an image. */
    static final float PAGE_AS_IMAGE_DPI = 110f;
    /** The pages of one document rendered at most: the limit of images of a document is applied after. */
    static final int MAX_RENDERED_PAGES = 200;

    /**
     * Opens a PDF for rendering with the PDFBox the application carries: PDFBox 3 opens it
     * with {@code Loader.loadPDF}, PDFBox 2 with {@code PDDocument.load}. The application
     * may carry either (another library of it brings PDFBox 2), so both are looked up at
     * run time; rendering itself ({@code PDFRenderer.renderImageWithDPI}) is the same in
     * both.
     */
    static org.apache.pdfbox.pdmodel.PDDocument loadForRendering(byte[] pdf) throws IOException {
        java.lang.reflect.Method open;
        try {
            open = Class.forName("org.apache.pdfbox.Loader").getMethod("loadPDF", byte[].class);
        } catch (ClassNotFoundException | NoSuchMethodException pdfbox2) {
            try {
                open = org.apache.pdfbox.pdmodel.PDDocument.class.getMethod("load", byte[].class);
            } catch (NoSuchMethodException none) {
                throw new IOException("No PDFBox able to open a PDF is available", none);
            }
        }
        try {
            return (org.apache.pdfbox.pdmodel.PDDocument) open.invoke(null, (Object) pdf);
        } catch (java.lang.reflect.InvocationTargetException failed) {
            final Throwable cause = failed.getCause();
            if (cause instanceof IOException io) {
                throw io;
            }
            throw new IOException("The PDF cannot be opened for rendering", cause);
        } catch (IllegalAccessException denied) {
            throw new IOException("The PDF cannot be opened for rendering", denied);
        }
    }

    @Override
    public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
            throws GeboIngestionException, IOException {
        PdfReader pdfReader = null;
        org.apache.pdfbox.pdmodel.PDDocument rendered = null;
        // set when the PDF cannot be opened for rendering: the other pages are not tried
        boolean renderingUnavailable = false;
        try {
            // while images are taken the bytes are kept: a page without text (a scan, a full
            // page picture) is rendered and given as an image, like any picture of a document
            final byte[] pdf = ingestsImages() ? is.readAllBytes() : null;
            final List<ai.gebo.system.ingestion.EmbeddedPicture> pagesAsImages = new ArrayList<>();
            pdfReader = pdf != null ? new PdfReader(pdf) : new PdfReader(is);
            enrichMetaData(reference, metadata);
            enrichPdfMetadata(pdfReader, metadata);
            int pages = pdfReader.getNumberOfPages();
            // a page in columns read column by column, any other page as before
            final boolean columnAware = config == null || config.isColumnAwareExtraction();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("handleContent(...) " + reference.getCode() + " pages:" + pages + " column aware:"
                        + columnAware);
            }
            List<Document> docs = new ArrayList<Document>();
            for (int i = 1; i <= pages; i++) {
                String text = columnAware ? ColumnAwarePdfTextExtractor.textOfPage(pdfReader, i)
                        : PdfTextExtractor.getTextFromPage(pdfReader, i);
                Map<String, Object> clonedMetaData = new HashMap<String, Object>(
                        metadata != null ? metadata : new HashMap());
                clonedMetaData.put(DocumentMetaInfos.CONTENT_PAGE, "" + i);
                Document document = new Document(text, clonedMetaData);
                docs.add(document);
                if (pdf != null && !renderingUnavailable && (text == null || text.trim().isEmpty())
                        && pagesAsImages.size() < MAX_RENDERED_PAGES) {
                    try {
                        if (rendered == null) {
                            rendered = loadForRendering(pdf);
                        }
                        final java.awt.image.BufferedImage image = new org.apache.pdfbox.rendering.PDFRenderer(rendered)
                                .renderImageWithDPI(i - 1, PAGE_AS_IMAGE_DPI);
                        final java.io.ByteArrayOutputStream png = new java.io.ByteArrayOutputStream();
                        javax.imageio.ImageIO.write(image, "png", png);
                        pagesAsImages.add(new ai.gebo.system.ingestion.EmbeddedPicture(null, "image/png",
                                png.toByteArray(), i));
                    } catch (IOException | RuntimeException | LinkageError unreadable) {
                        // a page that cannot be rendered is left out: the text of the document, and
                        // the other documents read with it, are given all the same
                        LOGGER.warn("Page " + i + " of " + reference.getCode()
                                + " has no text and could not be rendered as an image: " + unreadable);
                        if (rendered == null) {
                            renderingUnavailable = true;
                        }
                    }
                }
            }
            docs.addAll(imageDocuments(reference, pagesAsImages, metadata));
            return docs.stream();
        } finally {
            try {
                if (rendered != null) {
                    rendered.close();
                }
            } catch (Throwable t) {
                // Silent close
            }
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