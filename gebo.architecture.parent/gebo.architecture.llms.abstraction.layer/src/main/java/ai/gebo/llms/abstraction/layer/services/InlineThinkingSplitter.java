/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Splits the text a model writes into its answer and the reasoning it writes between
 * thinking tags ({@code <think>...</think>} or {@code <thinking>...</thinking>}, any
 * case), as the text streams.
 * <p>
 * Fed chunk by chunk ({@link #next(String)}), then ended ({@link #end()}): a tag split
 * across chunks is held back until it is whole, so no tag is ever given back, in the
 * answer or in the reasoning, and a model may write several blocks. Text outside the
 * blocks is the answer, so a model that writes no tags answers as it streams. A block
 * still open when the text ends is reasoning (the model was cut while reasoning).
 * <p>
 * A block opens with its tag at the start of a line (spaces before it allowed) or right
 * after another block, as models write it: a tag named inside a line of the answer
 * ({@code `<think>`}) is text. A closing tag before any tag is the end of a block the chat
 * template opened in the prompt: on the whole text ({@link #split(String)}) what comes before it is reasoning;
 * while the text streams it has already been given as answer, since nothing tells it
 * apart from an answer before the tag comes, and only the tag is dropped.
 */
public final class InlineThinkingSplitter {
	private static final String[] OPENING = { "<think>", "<thinking>" };
	private static final String[] CLOSING = { "</think>", "</thinking>" };

	/** A part of the text: the answer in it and the reasoning in it, either may be empty. */
	public record Split(String answer, String thinking) {
		public static final Split EMPTY = new Split("", "");

		public boolean isEmpty() {
			return answer.isEmpty() && thinking.isEmpty();
		}
	}

	/** The text not given back yet: a tag may start at its end. */
	private final StringBuilder held = new StringBuilder();
	/** The closing tag of the block open, null when none is. */
	private String closing = null;
	/**
	 * Whether a tag was met, or an opening tag named in the answer: a closing tag then is
	 * text, not the end of a block the chat template opened.
	 */
	private boolean sawTag = false;
	/** Whether the text given back so far ends at the start of a line. */
	private boolean lineStart = true;
	/** The whole text: an orphan closing tag makes the answer before it reasoning. */
	private final boolean whole;
	/** The blocks of reasoning of the whole text, when collected. */
	private final List<String> blocks;
	private final StringBuilder block = new StringBuilder();

	public InlineThinkingSplitter() {
		this(false, null);
	}

	private InlineThinkingSplitter(boolean whole, List<String> blocks) {
		this.whole = whole;
		this.blocks = blocks;
	}

	/** The answer and the reasoning of a chunk, as far as they are known. */
	public Split next(String text) {
		if (text == null || text.isEmpty()) {
			return Split.EMPTY;
		}
		held.append(text);
		return consume(false);
	}

	/**
	 * What is still held once the text ended: reasoning when a block is open, answer
	 * otherwise. The splitter starts over: what comes next starts a new text.
	 */
	public Split end() {
		final Split rest = consume(true);
		closing = null;
		sawTag = false;
		lineStart = true;
		return rest;
	}

	/** Whether a block is open: what comes next is reasoning. */
	public boolean isInsideThinking() {
		return closing != null;
	}

	/** The answer and the reasoning of a whole text. */
	public static Split split(String text) {
		if (text == null || text.isEmpty()) {
			return Split.EMPTY;
		}
		final InlineThinkingSplitter splitter = new InlineThinkingSplitter(true, null);
		splitter.held.append(text);
		return splitter.consume(true);
	}

	/** The reasoning blocks of a whole text, each as the model wrote it, in order. */
	public static List<String> blocks(String text) {
		final List<String> blocks = new ArrayList<>();
		if (text == null || text.isEmpty()) {
			return blocks;
		}
		final InlineThinkingSplitter splitter = new InlineThinkingSplitter(true, blocks);
		splitter.held.append(text);
		splitter.consume(true);
		return blocks;
	}

	/** Gives back what the held text tells: up to a possible tag start, or all of it once ended. */
	private Split consume(boolean ended) {
		final StringBuilder answer = new StringBuilder();
		final StringBuilder thinking = new StringBuilder();
		while (held.length() > 0) {
			final String lower = held.toString().toLowerCase(Locale.ROOT);
			if (closing == null) {
				final int[] open = opening(lower);
				final int close = sawTag ? -1 : earliest(lower, CLOSING)[0];
				final int named = earliest(lower, OPENING)[0];
				if (close >= 0 && (named < 0 || close < named)) {
					// the end of a block the chat template opened: what came before was reasoning
					final int closeWhich = earliest(lower, CLOSING)[1];
					final String before = held.substring(0, close);
					if (whole) {
						thinking.append(answer).append(before);
						collect(answer.toString() + before);
						answer.setLength(0);
					} else {
						answer.append(before);
					}
					held.delete(0, close + CLOSING[closeWhich].length());
					sawTag = true;
					lineStart = true;
					continue;
				}
				if (open[0] >= 0) {
					give(answer, held.substring(0, open[0]));
					held.delete(0, open[0] + OPENING[open[1]].length());
					closing = CLOSING[open[1]];
					sawTag = true;
					continue;
				}
				final int give = held.length() - (ended ? 0 : tagPrefixLength(lower));
				give(answer, held.substring(0, give));
				held.delete(0, give);
				break;
			} else {
				final int end = lower.indexOf(closing);
				if (end >= 0) {
					thinking.append(held, 0, end);
					block.append(held, 0, end);
					held.delete(0, end + closing.length());
					closing = null;
					collect(block.toString());
					block.setLength(0);
					lineStart = true;
					continue;
				}
				final int give = held.length() - (ended ? 0 : tagPrefixLength(lower));
				thinking.append(held, 0, give);
				block.append(held, 0, give);
				held.delete(0, give);
				break;
			}
		}
		if (ended && block.length() > 0) {
			// a block the text ended in
			collect(block.toString());
			block.setLength(0);
		}
		return answer.isEmpty() && thinking.isEmpty() ? Split.EMPTY
				: new Split(answer.toString(), thinking.toString());
	}

	/**
	 * Gives answer text back. An opening tag named in it (inside a line) makes a later
	 * closing tag text too.
	 */
	private void give(StringBuilder answer, String out) {
		answer.append(out);
		if (earliest(out.toLowerCase(Locale.ROOT), OPENING)[0] >= 0) {
			sawTag = true;
		}
		for (int i = 0; i < out.length(); i++) {
			final char c = out.charAt(i);
			if (c == '\n' || c == '\r') {
				lineStart = true;
			} else if (c != ' ' && c != '\t') {
				lineStart = false;
			}
		}
	}

	private void collect(String reasoning) {
		if (blocks != null) {
			blocks.add(reasoning);
		}
	}

	/** The earliest opening tag at the start of a line: {index, which}, index -1 when none. */
	private int[] opening(String lower) {
		int from = 0;
		while (true) {
			final int[] at = earliest(lower.substring(from), OPENING);
			if (at[0] < 0) {
				return at;
			}
			final int index = from + at[0];
			if (startsLine(lower, index)) {
				return new int[] { index, at[1] };
			}
			from = index + 1;
		}
	}

	/** Whether only spaces stand between the start of a line and the index. */
	private boolean startsLine(String lower, int index) {
		for (int i = index - 1; i >= 0; i--) {
			final char c = lower.charAt(i);
			if (c == '\n' || c == '\r') {
				return true;
			}
			if (c != ' ' && c != '\t') {
				return false;
			}
		}
		return lineStart;
	}

	/** The earliest of the tags in the text: {index, which}, index -1 when none is there. */
	private static int[] earliest(String lower, String[] tags) {
		int index = -1;
		int which = -1;
		for (int i = 0; i < tags.length; i++) {
			final int at = lower.indexOf(tags[i]);
			if (at >= 0 && (index < 0 || at < index)) {
				index = at;
				which = i;
			}
		}
		return new int[] { index, which };
	}

	/** The length of the end of the text a tag may start with, held back until more comes. */
	private static int tagPrefixLength(String lower) {
		final int lt = lower.lastIndexOf('<');
		if (lt < 0) {
			return 0;
		}
		final String tail = lower.substring(lt);
		for (String tag : OPENING) {
			if (tag.startsWith(tail)) {
				return tail.length();
			}
		}
		for (String tag : CLOSING) {
			if (tag.startsWith(tail)) {
				return tail.length();
			}
		}
		return 0;
	}
}
