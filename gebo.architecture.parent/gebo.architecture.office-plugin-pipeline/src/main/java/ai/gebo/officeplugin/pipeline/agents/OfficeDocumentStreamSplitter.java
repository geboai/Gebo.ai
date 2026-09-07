/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AdditionalContent;

/**
 * Streaming splitter for the office report writer's output.
 *
 * <p>
 * The office writer is instructed to emit the user-facing chat text as plain
 * running text and to wrap any content meant to be inserted into the document the
 * user is editing in an escape:
 *
 * <pre>
 * &lt;GEBO-DOCUMENT name="intro" contentType="text/markdown"&gt;
 * ## Introduction
 * ...
 * &lt;/GEBO-DOCUMENT&gt;
 * </pre>
 *
 * <p>
 * The LLM streams in arbitrary chunks, so an escape marker can be split across two
 * {@link #accept(String)} calls. This splitter is stateful: it buffers just enough
 * of the tail to recognise a partial {@code &lt;GEBO-DOCUMENT ...&gt;} /
 * {@code &lt;/GEBO-DOCUMENT&gt;} marker across a chunk boundary, returns the chat
 * text to stream to the user, and accumulates the document parts as
 * {@link AdditionalContent}. It is not thread safe: one instance per writer run.
 */
public class OfficeDocumentStreamSplitter {

	public static final String DEFAULT_CONTENT_TYPE = "text/markdown";
	private static final String OPEN_PREFIX = "<GEBO-DOCUMENT";
	private static final String CLOSE_TAG = "</GEBO-DOCUMENT>";

	private static final Pattern OPEN_TAG = Pattern.compile(
			"<GEBO-DOCUMENT((?:\\s+[a-zA-Z][a-zA-Z0-9_-]*\\s*=\\s*\"[^\"]*\")*)\\s*>", Pattern.CASE_INSENSITIVE);
	private static final Pattern ATTR = Pattern.compile("([a-zA-Z][a-zA-Z0-9_-]*)\\s*=\\s*\"([^\"]*)\"");

	private final StringBuilder pending = new StringBuilder();
	private final StringBuilder document = new StringBuilder();
	private final List<AdditionalContent> documents = new ArrayList<>();

	private boolean insideDocument = false;
	private String currentName = null;
	private String currentContentType = DEFAULT_CONTENT_TYPE;
	private int documentSeq = 0;

	/**
	 * Feeds one streamed chunk and returns the chat text that can be safely emitted
	 * to the user now (possibly empty). Document content and any marker still being
	 * assembled are held back internally.
	 */
	public String accept(String chunk) {
		if (chunk == null || chunk.isEmpty()) {
			return "";
		}
		pending.append(chunk);
		StringBuilder chatOut = new StringBuilder();
		boolean progressed = true;
		while (progressed) {
			progressed = false;
			if (!insideDocument) {
				int open = indexOfIgnoreCase(pending, OPEN_PREFIX);
				if (open < 0) {
					// No marker starting; emit everything except a tail that could be the
					// beginning of an open marker split across the boundary.
					int safe = safeEmitLength(pending, OPEN_PREFIX);
					chatOut.append(pending, 0, safe);
					pending.delete(0, safe);
				} else {
					// Emit chat text up to the marker.
					chatOut.append(pending, 0, open);
					pending.delete(0, open);
					Matcher m = OPEN_TAG.matcher(pending);
					if (m.lookingAt()) {
						parseOpenAttributes(m.group(1));
						pending.delete(0, m.end());
						insideDocument = true;
						document.setLength(0);
						progressed = true;
					}
					// else: the open tag is not yet complete in the buffer; wait for more.
				}
			} else {
				int close = indexOfIgnoreCase(pending, CLOSE_TAG);
				if (close < 0) {
					int safe = safeEmitLength(pending, CLOSE_TAG);
					document.append(pending, 0, safe);
					pending.delete(0, safe);
				} else {
					document.append(pending, 0, close);
					pending.delete(0, close + CLOSE_TAG.length());
					flushDocument();
					insideDocument = false;
					progressed = true;
				}
			}
		}
		return chatOut.toString();
	}

	/**
	 * Signals end of stream. Emits any remaining buffered text as chat when it is not
	 * an open document, or closes an unterminated document defensively, and returns
	 * the final trailing chat text.
	 */
	public String complete() {
		String tail = "";
		if (insideDocument) {
			// Unterminated document escape: keep what was gathered as a document part so
			// nothing is lost, and produce no extra chat text.
			document.append(pending);
			pending.setLength(0);
			flushDocument();
			insideDocument = false;
		} else {
			tail = pending.toString();
			pending.setLength(0);
		}
		return tail;
	}

	public List<AdditionalContent> getDocuments() {
		return documents;
	}

	public boolean hasDocuments() {
		return !documents.isEmpty();
	}

	private void flushDocument() {
		String body = document.toString();
		// Trim a single leading/trailing newline introduced by the escape formatting.
		body = stripEdgeNewlines(body);
		AdditionalContent content = new AdditionalContent();
		documentSeq++;
		content.setName(currentName != null && !currentName.isBlank() ? currentName : "document-" + documentSeq);
		content.setContentType(currentContentType != null && !currentContentType.isBlank() ? currentContentType
				: DEFAULT_CONTENT_TYPE);
		content.setContent(body);
		documents.add(content);
		document.setLength(0);
		currentName = null;
		currentContentType = DEFAULT_CONTENT_TYPE;
	}

	private void parseOpenAttributes(String attributesBlock) {
		currentName = null;
		currentContentType = DEFAULT_CONTENT_TYPE;
		if (attributesBlock == null) {
			return;
		}
		Matcher a = ATTR.matcher(attributesBlock);
		while (a.find()) {
			String key = a.group(1).toLowerCase();
			String value = a.group(2);
			if ("name".equals(key)) {
				currentName = value;
			} else if ("contenttype".equals(key) || "content-type".equals(key)) {
				currentContentType = value;
			}
		}
	}

	/**
	 * Length of buffer that can be emitted without risking cutting a marker that is
	 * only partially present at the tail: if a suffix of the buffer is a prefix of
	 * {@code marker}, that suffix is held back.
	 */
	private static int safeEmitLength(CharSequence buffer, String marker) {
		int max = Math.min(buffer.length(), marker.length() - 1);
		for (int keep = max; keep > 0; keep--) {
			if (regionMatchesIgnoreCase(buffer, buffer.length() - keep, marker, 0, keep)) {
				return buffer.length() - keep;
			}
		}
		return buffer.length();
	}

	private static int indexOfIgnoreCase(CharSequence buffer, String marker) {
		int limit = buffer.length() - marker.length();
		for (int i = 0; i <= limit; i++) {
			if (regionMatchesIgnoreCase(buffer, i, marker, 0, marker.length())) {
				return i;
			}
		}
		return -1;
	}

	private static boolean regionMatchesIgnoreCase(CharSequence buffer, int bufferOffset, String other, int otherOffset,
			int len) {
		if (bufferOffset < 0 || bufferOffset + len > buffer.length()) {
			return false;
		}
		for (int i = 0; i < len; i++) {
			char c1 = Character.toLowerCase(buffer.charAt(bufferOffset + i));
			char c2 = Character.toLowerCase(other.charAt(otherOffset + i));
			if (c1 != c2) {
				return false;
			}
		}
		return true;
	}

	private static String stripEdgeNewlines(String value) {
		int start = 0;
		int end = value.length();
		if (start < end && (value.charAt(start) == '\n' || value.charAt(start) == '\r')) {
			if (value.charAt(start) == '\r' && start + 1 < end && value.charAt(start + 1) == '\n') {
				start += 2;
			} else {
				start += 1;
			}
		}
		if (end > start && value.charAt(end - 1) == '\n') {
			end--;
			if (end > start && value.charAt(end - 1) == '\r') {
				end--;
			}
		}
		return value.substring(start, end);
	}
}
