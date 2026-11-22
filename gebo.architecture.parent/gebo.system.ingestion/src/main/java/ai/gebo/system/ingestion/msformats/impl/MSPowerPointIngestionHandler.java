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
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.sl.usermodel.GroupShape;
import org.apache.poi.sl.usermodel.Notes;
import org.apache.poi.sl.usermodel.Placeholder;
import org.apache.poi.sl.usermodel.Shape;
import org.apache.poi.sl.usermodel.ShapeContainer;
import org.apache.poi.sl.usermodel.SimpleShape;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TableShape;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.sl.usermodel.TextShape.TextPlaceholder;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.DocumentTitles;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * Handler for Microsoft PowerPoint presentation formats (.ppt and .pptx).
 * This service extracts the text of each slide and the presentation metadata
 * for ingestion into the system. Every slide with some text gives one document,
 * in the order of the presentation, carrying its slide number the way a PDF
 * page carries its page number. With ai.gebo.images.ingestion-enabled the
 * pictures of the slides are given too, each as a document of its own holding
 * the picture as media with its slide number; no model is called here.
 */
@Service
public class MSPowerPointIngestionHandler extends GAbstractConfiguredHandler {
	/** The line that opens the speaker notes of a slide in its text. */
	static final String NOTES_MARKER = "Speaker notes:";
	/** The separator of the cells of a table row in the text of a slide. */
	static final String CELLS_SEPARATOR = " | ";
	private static final String PPTX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
	private static final String PPT_CONTENT_TYPE = "application/vnd.ms-powerpoint";
	/**
	 * The placeholders that repeat on every slide or notes page and say nothing of
	 * its content: their text is not read.
	 */
	private static final Set<Placeholder> BOILERPLATE_PLACEHOLDERS = EnumSet.of(Placeholder.SLIDE_NUMBER,
			Placeholder.DATETIME, Placeholder.HEADER, Placeholder.FOOTER);

	/**
	 * Constructor for the MSPowerPointIngestionHandler.
	 *
	 * @param dao Data access object for ingestion handler configuration
	 */
	public MSPowerPointIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

	/**
	 * Returns the unique code identifier for this ingestion handler.
	 *
	 * @return The code identifying this handler
	 */
	@Override
	public String getCode() {

		return "MSPowerPointIngestionHandler";
	}

	/**
	 * Tells if a shape is the title of its slide. The two formats do not tell it
	 * the same way, so both the kind of its text and its placeholder are looked at.
	 */
	private static boolean isTitle(Shape<?, ?> shape) {
		if (shape instanceof TextShape<?, ?> text) {
			TextPlaceholder textPlaceholder = text.getTextPlaceholder();
			if (textPlaceholder == TextPlaceholder.TITLE || textPlaceholder == TextPlaceholder.CENTER_TITLE) {
				return true;
			}
			Placeholder placeholder = text.getPlaceholder();
			if (placeholder == Placeholder.TITLE || placeholder == Placeholder.CENTERED_TITLE) {
				return true;
			}
			// a .ppt keeps the kind of a text on its paragraphs
			if (shape instanceof HSLFTextShape ppt) {
				List<HSLFTextParagraph> paragraphs = ppt.getTextParagraphs();
				return !paragraphs.isEmpty() && TextPlaceholder.isTitle(paragraphs.get(0).getRunType());
			}
		}
		return false;
	}

	/**
	 * Tells if a shape is one of the placeholders whose text is not read (slide
	 * number, date, header and footer).
	 */
	private static boolean isBoilerplate(Shape<?, ?> shape) {
		if (shape instanceof SimpleShape<?, ?> simple) {
			Placeholder placeholder = simple.getPlaceholder();
			return placeholder != null && BOILERPLATE_PLACEHOLDERS.contains(placeholder);
		}
		return false;
	}

	/**
	 * Adds a text to the lines of a slide when it is not empty. PowerPoint line
	 * breaks inside a shape become plain new lines.
	 */
	private static void addText(List<String> lines, String text) {
		if (text == null) {
			return;
		}
		String cleaned = text.replace("\r\n", "\n").replace('\r', '\n').replace('\u000b', '\n').trim();
		if (!cleaned.isEmpty()) {
			lines.add(cleaned);
		}
	}

	/**
	 * Adds the rows of a table to the lines of a slide, one line per row with its
	 * cells in order. A row without any text is skipped.
	 */
	private static void addTable(List<String> lines, TableShape<?, ?> table) {
		for (int row = 0; row < table.getNumberOfRows(); row++) {
			List<String> cells = new ArrayList<String>();
			boolean hasText = false;
			for (int column = 0; column < table.getNumberOfColumns(); column++) {
				TableCell<?, ?> cell = table.getCell(row, column);
				String text = cell != null ? cell.getText() : null;
				text = text != null ? text.replace('\r', ' ').replace('\n', ' ').replace('\u000b', ' ').trim() : "";
				hasText = hasText || !text.isEmpty();
				cells.add(text);
			}
			if (hasText) {
				lines.add(String.join(CELLS_SEPARATOR, cells));
			}
		}
	}

	/**
	 * Adds the text of the shapes of a slide, of a notes page or of a group, in
	 * their order: text shapes, tables and the shapes inside groups. Pictures and
	 * any other shape are ignored.
	 *
	 * @param container  The slide, notes page or group holding the shapes
	 * @param skipTitles True when the title shapes were already read
	 * @param lines      The lines of text collected so far
	 */
	private static void addShapes(ShapeContainer<?, ?> container, boolean skipTitles, List<String> lines) {
		for (Shape<?, ?> shape : container.getShapes()) {
			if (isBoilerplate(shape) || (skipTitles && isTitle(shape))) {
				continue;
			}
			if (shape instanceof TableShape<?, ?> table) {
				addTable(lines, table);
			} else if (shape instanceof GroupShape<?, ?> group) {
				addShapes(group, skipTitles, lines);
			} else if (shape instanceof TextShape<?, ?> text) {
				addText(lines, text.getText());
			}
		}
	}

	/**
	 * Gives the text of a slide: its title first, then its text shapes and tables
	 * in their order, at last its speaker notes after the line that marks them.
	 *
	 * @param slide The slide to read
	 * @return The text of the slide, empty when the slide has none
	 */
	static String textOf(Slide<?, ?> slide) {
		List<String> lines = new ArrayList<String>();
		for (Shape<?, ?> shape : slide.getShapes()) {
			if (isTitle(shape) && !isBoilerplate(shape)) {
				addText(lines, ((TextShape<?, ?>) shape).getText());
			}
		}
		addShapes(slide, true, lines);
		Notes<?, ?> notes = slide.getNotes();
		if (notes != null) {
			List<String> notesLines = new ArrayList<String>();
			addShapes(notes, false, notesLines);
			if (!notesLines.isEmpty()) {
				lines.add(NOTES_MARKER);
				lines.addAll(notesLines);
			}
		}
		return String.join("\n", lines);
	}

	/**
	 * Fills the metadata with the title of the first slide when the properties of
	 * the presentation gave none.
	 */
	private static void enrichFromFirstSlide(SlideShow<?, ?> slideShow, Map<String, Object> metadata, String source) {
		if (!DocumentTitles.hasTitle(metadata) && !slideShow.getSlides().isEmpty()) {
			DocumentTitles.putTitle(metadata, slideShow.getSlides().get(0).getTitle(), source);
		}
	}

	/**
	 * Fills the metadata with title, subtitle and author of a .pptx presentation.
	 */
	private static void enrichPptxMetadata(XMLSlideShow slideShow, Map<String, Object> metadata) {
		POIXMLProperties.CoreProperties cp = slideShow.getProperties().getCoreProperties();
		if (cp != null) {
			DocumentTitles.putTitle(metadata, cp.getTitle(), "pptx properties");
			DocumentTitles.putSubtitle(metadata, cp.getSubject(), "pptx properties");
			DocumentTitles.putAuthor(metadata, cp.getCreator(), "pptx properties");
		}
		enrichFromFirstSlide(slideShow, metadata, "pptx first slide");
	}

	/**
	 * Fills the metadata with title, subtitle and author of a .ppt presentation.
	 */
	private static void enrichPptMetadata(HSLFSlideShow slideShow, Map<String, Object> metadata) {
		SummaryInformation summary = slideShow.getSummaryInformation();
		if (summary != null) {
			DocumentTitles.putTitle(metadata, summary.getTitle(), "ppt summary information");
			DocumentTitles.putSubtitle(metadata, summary.getSubject(), "ppt summary information");
			DocumentTitles.putAuthor(metadata, summary.getAuthor(), "ppt summary information");
		}
		enrichFromFirstSlide(slideShow, metadata, "ppt first slide");
	}

	/**
	 * Creates a separate Document object for each slide that has some text, with
	 * the number of the slide in its metadata.
	 *
	 * @param slideShow The presentation to read
	 * @param metadata  Map with the document metadata, copied on every slide
	 * @return The documents of the slides, in the order of the presentation
	 */
	private static List<Document> slidesOf(SlideShow<?, ?> slideShow, Map<String, Object> metadata,
			List<ai.gebo.system.ingestion.EmbeddedPicture> pictures) {
		List<Document> outlist = new ArrayList<Document>();
		int number = 0;
		for (Slide<?, ?> slide : slideShow.getSlides()) {
			number++;
			if (pictures != null) {
				picturesOf(slide, number, pictures);
			}
			String text = textOf(slide);
			if (text != null && !text.isEmpty()) {
				Map<String, Object> clonedMetaData = new HashMap<String, Object>(
						metadata != null ? metadata : new HashMap<String, Object>());
				clonedMetaData.put(DocumentMetaInfos.CONTENT_PAGE, "" + number);
				outlist.add(new Document(text, clonedMetaData));
			}
		}
		return outlist;
	}

	/**
	 * Extracts the text of the slides from a PPTX format presentation.
	 *
	 * @param is       The input stream containing the PPTX presentation
	 * @param metadata Map to store document metadata
	 * @return Stream of Document objects, one per slide with some text
	 * @throws IOException If there's an error reading the input stream
	 */
	/**
	 * The pictures placed on a slide, those inside its groups included; the ones of
	 * its layout and master (a logo, a background) are not the slide's own.
	 */
	private static void picturesOf(org.apache.poi.sl.usermodel.ShapeContainer<?, ?> container, int slideNumber,
			List<ai.gebo.system.ingestion.EmbeddedPicture> pictures) {
		for (Object shape : container.getShapes()) {
			try {
				if (shape instanceof org.apache.poi.sl.usermodel.PictureShape<?, ?> pictureShape) {
					final org.apache.poi.sl.usermodel.PictureData data = pictureShape.getPictureData();
					if (data != null) {
						pictures.add(new ai.gebo.system.ingestion.EmbeddedPicture(pictureShape.getShapeName(),
								data.getContentType(), data.getData(), slideNumber));
					}
				} else if (shape instanceof org.apache.poi.sl.usermodel.GroupShape<?, ?> group) {
					picturesOf(group, slideNumber, pictures);
				}
			} catch (RuntimeException unreadable) {
				// a linked or broken picture: the slide's text is read all the same
			}
		}
	}

	private static Stream<Document> streamPPTXContent(InputStream is, Map<String, Object> metadata,
			List<ai.gebo.system.ingestion.EmbeddedPicture> pictures) throws IOException {
		XMLSlideShow slideShow = null;
		try {
			slideShow = new XMLSlideShow(is);
			enrichPptxMetadata(slideShow, metadata);
			return slidesOf(slideShow, metadata, pictures).stream();
		} finally {
			try {
				slideShow.close();
			} catch (Throwable th) {
			}
			try {
				is.close();
			} catch (Throwable th) {
			}
		}
	}

	/**
	 * Extracts the text of the slides from a PPT format presentation.
	 *
	 * @param is       The input stream containing the PPT presentation
	 * @param metadata Map to store document metadata
	 * @return Stream of Document objects, one per slide with some text
	 * @throws IOException If there's an error reading the input stream
	 */
	private static Stream<Document> streamPPTContent(InputStream is, Map<String, Object> metadata,
			List<ai.gebo.system.ingestion.EmbeddedPicture> pictures) throws IOException {
		HSLFSlideShow slideShow = null;
		try {
			slideShow = new HSLFSlideShow(is);
			enrichPptMetadata(slideShow, metadata);
			return slidesOf(slideShow, metadata, pictures).stream();
		} finally {
			try {
				slideShow.close();
			} catch (Throwable th) {
			}
			try {
				is.close();
			} catch (Throwable th) {
			}
		}
	}

	/**
	 * Documents from remote/web content systems (WebDAV, MCP, Git, ...) are built
	 * via {@code createWebDocumentReference}, which never populates the file
	 * extension - only the content type is reliably known there. Dispatch to this
	 * handler already happened by content-type match, so fall back to it whenever
	 * the extension is unavailable instead of silently dropping the document.
	 */
	private static boolean isPptx(GDocumentReference reference) {
		if (reference.getExtension() != null) {
			return reference.getExtension().equalsIgnoreCase(".pptx");
		}
		return PPTX_CONTENT_TYPE.equalsIgnoreCase(reference.getContentType());
	}

	private static boolean isPpt(GDocumentReference reference) {
		if (reference.getExtension() != null) {
			return reference.getExtension().equalsIgnoreCase(".ppt");
		}
		return PPT_CONTENT_TYPE.equalsIgnoreCase(reference.getContentType());
	}

	/**
	 * Handles the content of a PowerPoint presentation, extracting the text of its
	 * slides based on file extension.
	 *
	 * @param reference Reference to the document being processed
	 * @param is        Input stream containing the document data
	 * @param metadata  Map to store document metadata
	 * @return Stream of Document objects, one per slide with some text
	 * @throws GeboIngestionException If the presentation cannot be read
	 * @throws IOException            If there's an error reading the input stream
	 */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		List<Document> outlist = new ArrayList<Document>();
		enrichMetaData(reference, metadata);
		try {
			// the pictures are collected only when images are taken
			final List<ai.gebo.system.ingestion.EmbeddedPicture> pictures = ingestsImages()
					? new ArrayList<>()
					: null;
			if (isPptx(reference)) {
				// Process PPTX presentation
				return Stream.concat(streamPPTXContent(is, metadata, pictures),
						imageDocuments(reference, pictures, metadata).stream());
			} else if (isPpt(reference)) {
				// Process PPT presentation
				return Stream.concat(streamPPTContent(is, metadata, pictures),
						imageDocuments(reference, pictures, metadata).stream());
			}
		} catch (RuntimeException exc) {
			// POI tells a broken or unreadable presentation with unchecked exceptions
			throw new GeboIngestionException("Exception reading PowerPoint presentation document", exc);
		}
		return outlist.stream();
	}

}
