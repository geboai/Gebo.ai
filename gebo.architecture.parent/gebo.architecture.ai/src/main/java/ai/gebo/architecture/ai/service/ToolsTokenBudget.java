/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.ai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;

import ai.gebo.architecture.ai.model.ITokensCountable;

/**
 * The room a model call leaves to the results of the tools it calls: the results
 * pile up in the call's messages, so every tool result takes it out of it.
 * <p>
 * Every model call made through the configurable chat models
 * ({@code GAbstractConfigurableChatModel.prepareCall}) with tools and a known context
 * length carries one in its tools context under {@value #TOOLS_CONTEXT_KEY}, sized on
 * what its messages and its tools' definitions leave of the context; an agent can
 * share a smaller one through the request context
 * ({@code IChatRequestContext.withToolsRoom}), the model call then capping it to its
 * own room. Every tool result is taken out of it by the tool wrapper
 * ({@code RunAsToolCallback}, see {@link #admit(String, String, String)}), cut to what
 * is left when larger.
 * <p>
 * A tool returning contents reads it to size its work ({@link #grantFor(ToolContext, int)},
 * {@link #fitText(String, int)}, {@link #fitItems(List, int, Function)}) and never takes
 * its result out of it itself: the wrapper does, once.
 */
public final class ToolsTokenBudget {
	private static final Logger LOGGER = LoggerFactory.getLogger(ToolsTokenBudget.class);
	public static final String TOOLS_CONTEXT_KEY = "geboToolsTokenBudget";
	/**
	 * Below this room a content tool does not do its work (a search, a reading, an
	 * analysis): what it would return would not be worth the call.
	 */
	public static final int MIN_USEFUL_TOKENS = 500;
	/** Below this room a result is not cut but replaced by a message saying so. */
	static final int MIN_TRUNCATED_RESULT_TOKENS = 64;
	/** The cuts tried to bring a text in its room. */
	static final int MAX_TRUNCATION_ATTEMPTS = 4;

	private final AtomicInteger left;

	public ToolsTokenBudget(int tokens) {
		this.left = new AtomicInteger(Math.max(0, tokens));
	}

	/**
	 * The tokens an agent's model call leaves to its tools: the agent's budget less the
	 * placeholders the call renders.
	 */
	public static int leftForTools(int agentBudget, Map<String, Object> params) {
		int placeholders = 0;
		if (params != null) {
			for (Object value : params.values()) {
				if (value != null) {
					placeholders += ITokensCountable.stringsTokensSize(value.toString());
				}
			}
		}
		final int left = Math.max(0, agentBudget - placeholders);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("leftForTools(...) agent budget:" + agentBudget + " placeholders:" + placeholders
					+ " (tok) left to the tools:" + left + " (tok)");
		}
		return left;
	}

	/** The budget of the model call the tool runs in, null when it has none. */
	public static ToolsTokenBudget from(ToolContext toolContext) {
		return toolContext != null ? from(toolContext.getContext()) : null;
	}

	/** The budget a tools context carries, null when it carries none. */
	public static ToolsTokenBudget from(Map<String, Object> toolsContext) {
		return toolsContext != null && toolsContext.get(TOOLS_CONTEXT_KEY) instanceof ToolsTokenBudget budget ? budget
				: null;
	}

	/**
	 * The tokens a tool wanting {@code wanted} may return in the model call it runs in:
	 * {@code wanted} when the call has no room set (the tool's own limits apply), never
	 * more than the room left otherwise.
	 */
	public static int grantFor(ToolContext toolContext, int wanted) {
		final ToolsTokenBudget budget = from(toolContext);
		return budget != null ? budget.grant(wanted) : wanted;
	}

	/** True when the model call has a room set and less than {@link #MIN_USEFUL_TOKENS} is left in it. */
	public static boolean noUsefulRoom(ToolContext toolContext) {
		final ToolsTokenBudget budget = from(toolContext);
		return budget != null && budget.left() < MIN_USEFUL_TOKENS;
	}

	public int left() {
		return left.get();
	}

	/** What a tool wanting {@code wanted} tokens may return. */
	public int grant(int wanted) {
		return Math.max(0, Math.min(wanted, left.get()));
	}

	/** Takes what a tool call put in the model call out of the room left. */
	public void consume(int used) {
		final int remaining = left.updateAndGet(current -> Math.max(0, current - Math.max(0, used)));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("A tool call took " + used + " token(s), " + remaining + " left to the tools of this model call");
		}
	}

	/** Never more than {@code tokens} left: the room of the model call this budget is shared with. */
	public void capTo(int tokens) {
		final int before = left.get();
		final int capped = left.updateAndGet(current -> Math.min(current, Math.max(0, tokens)));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("capTo(" + tokens + ") shared budget of " + before + " (tok), now " + capped + " (tok)");
		}
	}

	/**
	 * The result of a tool call as the model call can take it: whole when it fits the
	 * room left with the call's input, otherwise cut to it and marked with its real
	 * size, or replaced by a message saying so when too little is left to cut it. The
	 * input and what is returned are taken out of the room.
	 */
	public String admit(String toolName, String toolInput, String result) {
		final int inputTokens = toolInput != null ? ITokensCountable.stringsTokensSize(toolInput) : 0;
		final int resultTokens = result != null ? ITokensCountable.stringsTokensSize(result) : 0;
		final int room = left.get() - inputTokens;
		if (resultTokens <= room) {
			consume(inputTokens + resultTokens);
			return result;
		}
		final String truncated = room >= MIN_TRUNCATED_RESULT_TOKENS ? truncate(result, room, resultTokens) : null;
		final String admitted = truncated != null ? truncated
				: "The result of the tool " + toolName + " (" + resultTokens
						+ " tokens) does not fit the room left in the context: answer with what is already known.";
		final int admittedTokens = ITokensCountable.stringsTokensSize(admitted);
		LOGGER.warn("The result of the tool " + toolName + " (" + resultTokens + " tokens) is over the " + Math.max(0, room)
				+ " token(s) left in its model call's context, " + admittedTokens + " token(s) of it returned");
		consume(inputTokens + admittedTokens);
		return admitted;
	}

	/**
	 * The text in {@code maxTokens}: whole when it fits, otherwise cut and marked with
	 * its real size; empty when {@code maxTokens} is too small to hold a cut.
	 */
	public static String fitText(String text, int maxTokens) {
		if (text == null) {
			return null;
		}
		final int tokens = ITokensCountable.stringsTokensSize(text);
		if (tokens <= maxTokens) {
			return text;
		}
		final String truncated = maxTokens >= MIN_TRUNCATED_RESULT_TOKENS ? truncate(text, maxTokens, tokens) : null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("fitText(...) " + tokens + " (tok) cut to " + maxTokens + " (tok)");
		}
		return truncated != null ? truncated : "";
	}

	/**
	 * The first items, in order, whose renderings (what the model will read of each,
	 * e.g. their JSON) fit together in {@code maxTokens}: a list is cut between whole
	 * items, never inside one.
	 */
	public static <T> List<T> fitItems(List<T> items, int maxTokens, Function<T, String> rendering) {
		if (items == null || items.isEmpty()) {
			return items;
		}
		final List<T> kept = new ArrayList<>();
		long used = 0;
		for (T item : items) {
			final int tokens = ITokensCountable.stringsTokensSize(rendering.apply(item));
			if (used + tokens > maxTokens) {
				break;
			}
			used += tokens;
			kept.add(item);
		}
		if (kept.size() < items.size()) {
			LOGGER.warn("fitItems(...) " + kept.size() + " of " + items.size() + " item(s) fit " + maxTokens
					+ " (tok), the others are left out");
		}
		return kept;
	}

	/**
	 * The text cut to about the room, marked with its real size; cut again until the
	 * whole of it, mark included, is in the room. Null when it could not be brought in.
	 */
	static String truncate(String text, int room, int textTokens) {
		final double charsPerToken = textTokens > 0 ? ((double) text.length()) / textTokens : 4.0d;
		int allowance = room;
		for (int attempt = 0; attempt < MAX_TRUNCATION_ATTEMPTS && allowance > 0; attempt++) {
			final String marker = "...(truncated: about " + allowance + " of " + textTokens
					+ " tokens shown, the rest does not fit the room left in the context)";
			final int markerTokens = ITokensCountable.stringsTokensSize(marker);
			final int end = Math.max(0, Math.min(text.length(), (int) ((allowance - markerTokens) * charsPerToken)));
			final String cut = text.substring(0, end) + marker;
			final int overshoot = ITokensCountable.stringsTokensSize(cut) - room;
			if (overshoot <= 0) {
				return cut;
			}
			allowance -= overshoot;
		}
		return null;
	}
}
