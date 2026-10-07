/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GThinkingEvent;

/**
 * The reasoning of a streamed answer, as events for the user: a model gives its
 * reasoning either in a field of its own, each chunk carrying all of it so far, or in its
 * text between thinking tags. The reasoning is sent in fragments of at least
 * {@value #FRAGMENT_CHARS} characters (or up to a line end), not one tiny piece per chunk,
 * and a completing event tells it ended when the answer starts.
 */
final class ThinkingStream {
	private static final Logger LOGGER = LoggerFactory.getLogger(ThinkingStream.class);
	/** The least characters an event carries, unless a line ends. */
	static final int FRAGMENT_CHARS = 100;
	private static final String[] TAGS = { "<think>", "</think>", "<thinking>", "</thinking>" };
	/** The reasoning of the field so far, as the last chunk gave it. */
	private String accumulated = "";
	private final StringBuilder pending = new StringBuilder();
	private final StringBuilder whole = new StringBuilder();
	private boolean active = false;
	/** All the reasoning written so far, every round of it. */
	private long written = 0;

	/** Events for the reasoning field of a chunk: all the reasoning so far. */
	List<GThinkingEvent> reasoning(String soFar) {
		if (soFar == null || soFar.isEmpty()) {
			return List.of();
		}
		// the same reasoning grown, or a new one (a later round of the answer)
		final String delta = soFar.startsWith(accumulated) ? soFar.substring(accumulated.length()) : soFar;
		accumulated = soFar;
		return add(delta);
	}

	/** Events for text written between thinking tags. */
	List<GThinkingEvent> inline(String text) {
		if (text == null || text.isEmpty()) {
			return List.of();
		}
		String clean = text;
		for (String tag : TAGS) {
			clean = clean.replace(tag, "");
		}
		return add(clean);
	}

	/** The events ending the reasoning, when one is going on: what is pending, then the completion. */
	List<GThinkingEvent> complete() {
		if (!active) {
			return List.of();
		}
		final List<GThinkingEvent> out = new ArrayList<>();
		if (pending.length() > 0) {
			out.add(new GThinkingEvent(pending.toString(), false));
			pending.setLength(0);
		}
		out.add(new GThinkingEvent("", true));
		active = false;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("complete() reasoning of " + whole.length() + " character(s) streamed");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<STREAMED_REASONING>");
			LOGGER.trace(whole.toString());
			LOGGER.trace("</STREAMED_REASONING>");
		}
		whole.setLength(0);
		return out;
	}

	/** The characters of reasoning the model wrote so far, every round of it. */
	long writtenChars() {
		return written;
	}

	private List<GThinkingEvent> add(String delta) {
		if (delta.isEmpty()) {
			return List.of();
		}
		if (!active && LOGGER.isDebugEnabled()) {
			LOGGER.debug("add(...) the model is reasoning: its reasoning is streamed");
		}
		active = true;
		written += delta.length();
		pending.append(delta);
		whole.append(delta);
		if (pending.length() >= FRAGMENT_CHARS || delta.indexOf('\n') >= 0) {
			final GThinkingEvent event = new GThinkingEvent(pending.toString(), false);
			pending.setLength(0);
			return List.of(event);
		}
		return List.of();
	}
}
