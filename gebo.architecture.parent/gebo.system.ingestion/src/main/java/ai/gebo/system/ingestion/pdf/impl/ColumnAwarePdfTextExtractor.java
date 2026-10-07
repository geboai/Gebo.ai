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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.LineSegment;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;

/**
 * The text of a PDF page, its columns read one after the other. The position-ordered
 * extraction reads a page line by line across its whole width: on a page in columns it
 * joins the lines of the columns side by side. Here each page's text is measured along
 * the direction most of its characters run in (a page may be rotated): when a gutter
 * runs through almost every line, with a fair share of the text on each side, the
 * columns are extracted one after the other, each with the position-ordered extraction;
 * any other page is extracted exactly as the position-ordered extraction does.
 * <p>
 * The detection is per page: a page whose columns start below a full-width part longer
 * than {@value #MAX_CROSSING_SHARE} of its lines, or whose text runs in several
 * directions, is extracted as the position-ordered extraction does.
 */
public final class ColumnAwarePdfTextExtractor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ColumnAwarePdfTextExtractor.class);
	/** Fewer lines than this: no column detection. */
	static final int MIN_LINES = 8;
	/** Share of the lines a gutter may be crossed by (titles, headers, footers). */
	static final double MAX_CROSSING_SHARE = 0.05;
	/** The least width of a gutter, in points. */
	static final float MIN_GUTTER = 6f;
	/** Share of the characters each column must hold, else no columns (a margin note, a table cell). */
	static final double MIN_COLUMN_SHARE = 0.15;
	/** The tolerance grouping text chunks into a line, in points. */
	static final float LINE_TOLERANCE = 2f;

	private ColumnAwarePdfTextExtractor() {
	}

	/** The text of the page, its columns read one after the other. */
	public static String textOfPage(PdfReader reader, int page) throws IOException {
		final Collector collector = new Collector();
		new PdfReaderContentParser(reader).processContent(page, collector);
		final float[] direction = collector.direction();
		final List<float[]> columns = columns(collector.projected(direction));
		if (columns.size() <= 1) {
			return PdfTextExtractor.getTextFromPage(reader, page);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("textOfPage(...) page:" + page + " read in " + columns.size() + " columns, text direction:("
					+ direction[0] + "," + direction[1] + ")");
		}
		final StringBuilder out = new StringBuilder();
		for (float[] column : columns) {
			final RenderFilter inColumn = new RenderFilter() {
				@Override
				public boolean allowText(TextRenderInfo info) {
					final LineSegment base = info.getBaseline();
					final float x = (base.getStartPoint().get(Vector.I1) + base.getEndPoint().get(Vector.I1)) / 2f;
					final float y = (base.getStartPoint().get(Vector.I2) + base.getEndPoint().get(Vector.I2)) / 2f;
					final float along = x * direction[0] + y * direction[1];
					return along >= column[0] && along < column[1];
				}
			};
			out.append(PdfTextExtractor.getTextFromPage(reader, page,
					new FilteredTextRenderListener(new LocationTextExtractionStrategy(), inColumn)));
			out.append('\n');
		}
		return out.toString();
	}

	/** A text chunk: x0..x1 along the page's text direction, y across it. */
	static final class Chunk {
		float x0, x1, y;
		int chars;
		float sx, sy, ex, ey;
	}

	/** The text chunks of a page with their baselines. */
	static final class Collector implements RenderListener {
		final List<Chunk> chunks = new ArrayList<>();

		@Override
		public void renderText(TextRenderInfo info) {
			final String text = info.getText();
			if (text == null || text.trim().isEmpty()) {
				return;
			}
			final LineSegment base = info.getBaseline();
			final Chunk chunk = new Chunk();
			chunk.sx = base.getStartPoint().get(Vector.I1);
			chunk.sy = base.getStartPoint().get(Vector.I2);
			chunk.ex = base.getEndPoint().get(Vector.I1);
			chunk.ey = base.getEndPoint().get(Vector.I2);
			chunk.chars = text.trim().length();
			chunks.add(chunk);
		}

		@Override
		public void beginTextBlock() {
		}

		@Override
		public void endTextBlock() {
		}

		@Override
		public void renderImage(ImageRenderInfo info) {
		}

		/** The direction most characters run along, rounded to a right angle (unit vector). */
		float[] direction() {
			final Map<String, int[]> votes = new HashMap<>();
			final Map<String, float[]> directions = new HashMap<>();
			for (Chunk chunk : chunks) {
				final float dx = chunk.ex - chunk.sx, dy = chunk.ey - chunk.sy;
				final float length = (float) Math.hypot(dx, dy);
				if (length < 0.5f) {
					continue;
				}
				final int ax = Math.round(dx / length), ay = Math.round(dy / length);
				final String key = ax + "," + ay;
				votes.computeIfAbsent(key, k -> new int[1])[0] += chunk.chars;
				directions.put(key, new float[] { ax, ay });
			}
			String best = null;
			for (Map.Entry<String, int[]> vote : votes.entrySet()) {
				if (best == null || vote.getValue()[0] > votes.get(best)[0]) {
					best = vote.getKey();
				}
			}
			return best != null ? directions.get(best) : new float[] { 1, 0 };
		}

		/** The chunks running along the direction, with their extent along it and their place across it. */
		List<Chunk> projected(float[] d) {
			final List<Chunk> out = new ArrayList<>();
			for (Chunk chunk : chunks) {
				final float dx = chunk.ex - chunk.sx, dy = chunk.ey - chunk.sy;
				final float length = (float) Math.hypot(dx, dy);
				if (length < 0.5f || Math.abs(dx / length - d[0]) > 0.1f || Math.abs(dy / length - d[1]) > 0.1f) {
					continue;
				}
				final float a = chunk.sx * d[0] + chunk.sy * d[1], b = chunk.ex * d[0] + chunk.ey * d[1];
				chunk.x0 = Math.min(a, b);
				chunk.x1 = Math.max(a, b);
				chunk.y = -chunk.sx * d[1] + chunk.sy * d[0];
				out.add(chunk);
			}
			return out;
		}
	}

	/** The columns' ranges along the text direction, in reading order; one range when the page has no gutter. */
	static List<float[]> columns(List<Chunk> chunks) {
		final List<float[]> single = new ArrayList<>();
		single.add(new float[] { -Float.MAX_VALUE, Float.MAX_VALUE });
		if (chunks.isEmpty()) {
			return single;
		}
		float minX = Float.MAX_VALUE, maxX = -Float.MAX_VALUE;
		for (Chunk chunk : chunks) {
			minX = Math.min(minX, chunk.x0);
			maxX = Math.max(maxX, chunk.x1);
		}
		final Map<Integer, List<Chunk>> lines = new HashMap<>();
		for (Chunk chunk : chunks) {
			lines.computeIfAbsent(Math.round(chunk.y / LINE_TOLERANCE), k -> new ArrayList<>()).add(chunk);
		}
		if (lines.size() < MIN_LINES) {
			return single;
		}
		// how many lines cover each point along the text direction
		final int bins = (int) Math.ceil(maxX - minX) + 1;
		final int[] crossing = new int[bins];
		for (List<Chunk> line : lines.values()) {
			final boolean[] covered = new boolean[bins];
			for (Chunk chunk : line) {
				for (int b = Math.max(0, (int) (chunk.x0 - minX)); b <= (int) (chunk.x1 - minX) && b < bins; b++) {
					covered[b] = true;
				}
			}
			for (int b = 0; b < bins; b++) {
				if (covered[b]) {
					crossing[b]++;
				}
			}
		}
		final int maxCrossing = (int) Math.max(1, Math.floor(lines.size() * MAX_CROSSING_SHARE));
		// the gutters: stretches few lines cross, wide enough, strictly inside the text
		final List<float[]> gutters = new ArrayList<>();
		int start = -1;
		for (int b = 0; b <= bins; b++) {
			final boolean low = b < bins && crossing[b] <= maxCrossing;
			if (low && start < 0) {
				start = b;
			} else if (!low && start >= 0) {
				if (start > 0 && b < bins && b - start >= MIN_GUTTER) {
					gutters.add(new float[] { minX + start, minX + b });
				}
				start = -1;
			}
		}
		if (gutters.isEmpty()) {
			return single;
		}
		final List<float[]> columns = new ArrayList<>();
		float from = -Float.MAX_VALUE;
		for (float[] gutter : gutters) {
			final float cut = (gutter[0] + gutter[1]) / 2f;
			columns.add(new float[] { from, cut });
			from = cut;
		}
		columns.add(new float[] { from, Float.MAX_VALUE });
		int totalChars = 0;
		for (Chunk chunk : chunks) {
			totalChars += chunk.chars;
		}
		for (float[] column : columns) {
			int chars = 0;
			for (Chunk chunk : chunks) {
				final float middle = (chunk.x0 + chunk.x1) / 2f;
				if (middle >= column[0] && middle < column[1]) {
					chars += chunk.chars;
				}
			}
			if (chars < totalChars * MIN_COLUMN_SHARE) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("columns(...) a gutter leaves a column with " + chars + " of " + totalChars
							+ " character(s): read as one column");
				}
				return single;
			}
		}
		return columns;
	}
}
