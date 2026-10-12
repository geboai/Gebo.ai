/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.ingestion;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Element;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.incubator.meta.OdfOfficeMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.model.DocumentMetaInfos;
import lombok.experimental.UtilityClass;

/**
 * The title, the subtitle and the author of a document, kept in its metadata as
 * {@link DocumentMetaInfos#TITLE}, {@link DocumentMetaInfos#SUBTITLE} and
 * {@link DocumentMetaInfos#AUTHOR}: what the format handlers find in the file. A value already there is kept (the content
 * handler's, or the one of a more reliable source read first), a blank one is never
 * set, the white space is collapsed.
 */
@UtilityClass
public class DocumentTitles {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentTitles.class);
	private static final Pattern WHITE_SPACE = Pattern.compile("\\s+");
	/** A markdown ATX heading: its level (the #s) and its text, without the closing #s. */
	private static final Pattern MARKDOWN_HEADING = Pattern.compile("^ {0,3}(#{1,6})\\s+(.*?)(?:\\s+#+)?\\s*$");
	/** The underline making the line above a markdown level 1 (setext) heading. */
	private static final Pattern MARKDOWN_TITLE_UNDERLINE = Pattern.compile("^ {0,3}=+\\s*$");
	private static final Pattern MARKDOWN_FENCE = Pattern.compile("^ {0,3}(```|~~~).*");
	private static final Pattern FRONT_MATTER_FIELD = Pattern.compile("^(title|subtitle|description|author)\\s*:\\s*(.*)$",
			Pattern.CASE_INSENSITIVE);
	private static final String FRONT_MATTER_DELIMITER = "---";
	/** What Office's PDF printing puts before the file name it gives as the title. */
	private static final Pattern OFFICE_PRINT_PREFIX = Pattern.compile("^Microsoft\\s+(Word|PowerPoint|Excel)\\s+-\\s+",
			Pattern.CASE_INSENSITIVE);
	/** The extension of the office file a PDF was printed from, at the end of its title. */
	private static final Pattern OFFICE_FILE_EXTENSION = Pattern.compile("(\\.|_)(docx?|pptx?|xlsx?)$",
			Pattern.CASE_INSENSITIVE);

	/** The text trimmed, its white space collapsed; null when it has none. */
	public static String clean(Object text) {
		if (text == null) {
			return null;
		}
		final String cleaned = WHITE_SPACE.matcher(text.toString()).replaceAll(" ").trim();
		return cleaned.isEmpty() ? null : cleaned;
	}

	public static boolean hasTitle(Map<String, Object> meta) {
		return meta != null && clean(meta.get(DocumentMetaInfos.TITLE)) != null;
	}

	public static boolean hasSubtitle(Map<String, Object> meta) {
		return meta != null && clean(meta.get(DocumentMetaInfos.SUBTITLE)) != null;
	}

	public static boolean hasAuthor(Map<String, Object> meta) {
		return meta != null && clean(meta.get(DocumentMetaInfos.AUTHOR)) != null;
	}

	/**
	 * Sets the author unless the metadata has one already (the content handler's
	 * first): none when the source tells none.
	 *
	 * @param source where the author comes from, for the logs
	 * @return whether it was set
	 */
	public static boolean putAuthor(Map<String, Object> meta, Object candidate, String source) {
		return put(meta, DocumentMetaInfos.AUTHOR, candidate, source);
	}

	/**
	 * Sets the title unless the metadata has one already.
	 *
	 * @param source where the title comes from, for the logs
	 * @return whether it was set
	 */
	public static boolean putTitle(Map<String, Object> meta, Object candidate, String source) {
		return put(meta, DocumentMetaInfos.TITLE, candidate, source);
	}

	/**
	 * Sets the subtitle unless the metadata has one already.
	 *
	 * @param source where the subtitle comes from, for the logs
	 * @return whether it was set
	 */
	public static boolean putSubtitle(Map<String, Object> meta, Object candidate, String source) {
		return put(meta, DocumentMetaInfos.SUBTITLE, candidate, source);
	}

	private static boolean put(Map<String, Object> meta, String key, Object candidate, String source) {
		if (meta == null) {
			return false;
		}
		final String value = clean(candidate);
		if (value == null) {
			return false;
		}
		final Object document = meta.get(DocumentMetaInfos.CONTENT_CODE);
		if (clean(meta.get(key)) != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(key + " of " + document + " from " + source + " not used, it has one already");
			}
			return false;
		}
		meta.put(key, value);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(key + " of " + document + " from " + source);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace(key + " of " + document + ": " + value);
		}
		return true;
	}

	/**
	 * The title a PDF's document information gives, without what the program that
	 * made the PDF added to it: the {@code Microsoft Word - } prefix and the extension
	 * of the office file printed. Null when what is left is not a title (blank or a
	 * single character).
	 */
	public static String pdfInfoTitle(String title) {
		String cleaned = clean(title);
		if (cleaned == null) {
			return null;
		}
		cleaned = clean(OFFICE_FILE_EXTENSION.matcher(OFFICE_PRINT_PREFIX.matcher(cleaned).replaceFirst(""))
				.replaceFirst(""));
		if (cleaned == null || cleaned.length() < 2) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("pdf info title not used, it is no title once cleaned");
			}
			return null;
		}
		return cleaned;
	}

	/**
	 * An html page's title: its {@code <title>}, else its {@code og:title}, else its
	 * first {@code <h1>}; its description as the subtitle; its author meta.
	 */
	public static void fromHtml(org.jsoup.nodes.Document page, Map<String, Object> meta) {
		if (page == null) {
			return;
		}
		putTitle(meta, page.title(), "html <title>");
		putTitle(meta, attribute(page.selectFirst("meta[property=og:title]"), "content"), "html og:title");
		final Element h1 = page.selectFirst("h1");
		putTitle(meta, h1 != null ? h1.text() : null, "html <h1>");
		putSubtitle(meta, attribute(page.selectFirst("meta[name=description]"), "content"), "html description");
		putAuthor(meta, attribute(page.selectFirst("meta[name=author]"), "content"), "html author");
	}

	private static String attribute(Element element, String name) {
		return element != null ? element.attr(name) : null;
	}

	/**
	 * A markdown text's title: the {@code title} of its front matter, else its first
	 * level 1 heading; the {@code subtitle} (or {@code description}) of its front
	 * matter, else its first level 2 heading, as the subtitle; the {@code author} of its
	 * front matter. Fenced code is skipped.
	 */
	public static void fromMarkdown(String text, Map<String, Object> meta) {
		if (text == null || text.isEmpty()) {
			return;
		}
		final String[] lines = (text.startsWith("﻿") ? text.substring(1) : text).split("\\r?\\n", -1);
		final int body = frontMatter(lines, meta);
		boolean fenced = false;
		String previous = null;
		for (int i = body; i < lines.length && !(hasTitle(meta) && hasSubtitle(meta)); i++) {
			final String line = lines[i];
			if (MARKDOWN_FENCE.matcher(line).matches()) {
				fenced = !fenced;
				previous = null;
				continue;
			}
			if (fenced) {
				continue;
			}
			final Matcher heading = MARKDOWN_HEADING.matcher(line);
			if (heading.matches()) {
				final int level = heading.group(1).length();
				if (level == 1) {
					putTitle(meta, heading.group(2), "markdown heading");
				} else if (level == 2) {
					putSubtitle(meta, heading.group(2), "markdown heading");
				}
				previous = null;
				continue;
			}
			if (previous != null && MARKDOWN_TITLE_UNDERLINE.matcher(line).matches()) {
				putTitle(meta, previous, "markdown heading");
				previous = null;
				continue;
			}
			previous = line.isBlank() ? null : line;
		}
	}

	/**
	 * Reads the title and the subtitle of a markdown front matter.
	 *
	 * @return the first line after it, 0 when the text has none
	 */
	private static int frontMatter(String[] lines, Map<String, Object> meta) {
		if (lines.length == 0 || !FRONT_MATTER_DELIMITER.equals(lines[0].trim())) {
			return 0;
		}
		int end = -1;
		for (int i = 1; i < lines.length && end < 0; i++) {
			final String line = lines[i].trim();
			if (FRONT_MATTER_DELIMITER.equals(line) || "...".equals(line)) {
				end = i;
			}
		}
		if (end < 0) {
			// a thematic break, not a front matter
			return 0;
		}
		for (int i = 1; i < end; i++) {
			final Matcher field = FRONT_MATTER_FIELD.matcher(lines[i]);
			if (field.matches()) {
				final String value = unquoted(field.group(2));
				if ("title".equalsIgnoreCase(field.group(1))) {
					putTitle(meta, value, "markdown front matter");
				} else if ("author".equalsIgnoreCase(field.group(1))) {
					putAuthor(meta, value, "markdown front matter");
				} else {
					putSubtitle(meta, value, "markdown front matter");
				}
			}
		}
		return end + 1;
	}

	private static String unquoted(String value) {
		final String trimmed = value.trim();
		if (trimmed.length() >= 2 && (trimmed.startsWith("\"") && trimmed.endsWith("\"")
				|| trimmed.startsWith("'") && trimmed.endsWith("'"))) {
			return trimmed.substring(1, trimmed.length() - 1);
		}
		return trimmed;
	}

	/** A spreadsheet's title, subject and author, from its document properties. */
	public static void fromWorkbook(Workbook workbook, Map<String, Object> meta) {
		try {
			if (workbook instanceof XSSFWorkbook xlsx) {
				final POIXMLProperties properties = xlsx.getProperties();
				final POIXMLProperties.CoreProperties core = properties != null ? properties.getCoreProperties() : null;
				if (core != null) {
					putTitle(meta, core.getTitle(), "xlsx properties");
					putSubtitle(meta, core.getSubject(), "xlsx properties");
					putAuthor(meta, core.getCreator(), "xlsx properties");
				}
			} else if (workbook instanceof HSSFWorkbook xls) {
				final SummaryInformation summary = xls.getSummaryInformation();
				if (summary != null) {
					putTitle(meta, summary.getTitle(), "xls summary information");
					putSubtitle(meta, summary.getSubject(), "xls summary information");
					putAuthor(meta, summary.getAuthor(), "xls summary information");
				}
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot read the title of the spreadsheet " + meta.get(DocumentMetaInfos.CONTENT_CODE) + ": "
					+ e.getMessage());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("spreadsheet title failure", e);
			}
		}
	}

	/** An OpenDocument's title, subject and author, from its meta.xml. */
	public static void fromOdfDocument(OdfDocument document, Map<String, Object> meta, String source) {
		try {
			final OdfOfficeMeta office = document != null ? document.getOfficeMetadata() : null;
			if (office != null) {
				putTitle(meta, office.getTitle(), source);
				putSubtitle(meta, office.getSubject(), source);
				// who created it, else who last saved it
				putAuthor(meta, office.getInitialCreator(), source);
				putAuthor(meta, office.getCreator(), source);
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot read the title of the document " + meta.get(DocumentMetaInfos.CONTENT_CODE) + " from "
					+ source + ": " + e.getMessage());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("OpenDocument title failure", e);
			}
		}
	}
}
