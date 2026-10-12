/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGProgressNotifier;

/**
 * Lets the search and deep search tools tell the user what they are doing while
 * they work (the search they run, the documents they read, the analysis progress):
 * the calling agent shares its user notifier through the tools context of its model
 * calls ({@link #sharedThrough(IChatRequestContext, IGProgressNotifier)}), the
 * tools notify through it.
 * <p>
 * Without a notifier in the tools context (an agent that does not share one, or is
 * not allowed to notify the user) the tools work silently.
 */
public final class ToolsProgress {
	private static final Logger LOGGER = LoggerFactory.getLogger(ToolsProgress.class);
	/** Tools context key carrying the notifier. */
	public static final String TOOLS_CONTEXT_KEY = "geboToolsProgress";
	/** Longest text of a search shown in a notification. */
	static final int MAX_SHOWN_TEXT = 120;

	private ToolsProgress() {
	}

	/**
	 * The given request context, its tools context also carrying the notifier: every
	 * other value is still read from the given context.
	 */
	public static IChatRequestContext sharedThrough(IChatRequestContext context, IGProgressNotifier notifier) {
		return ToolsContextSharing.with(context, TOOLS_CONTEXT_KEY, notifier);
	}

	/** The notifier a tool context carries, or {@link IGProgressNotifier#NONE}. */
	public static IGProgressNotifier from(ToolContext toolContext) {
		if (toolContext == null || toolContext.getContext() == null) {
			return IGProgressNotifier.NONE;
		}
		return toolContext.getContext().get(TOOLS_CONTEXT_KEY) instanceof IGProgressNotifier notifier ? notifier
				: IGProgressNotifier.NONE;
	}

	/** Tells the user the message, when the tool context carries a notifier; never fails the tool. */
	public static void notify(ToolContext toolContext, String message) {
		final IGProgressNotifier notifier = from(toolContext);
		if (notifier == IGProgressNotifier.NONE) {
			return;
		}
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Notifying the user of a tool progress");
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<TOOL_PROGRESS>");
				LOGGER.trace(message);
				LOGGER.trace("</TOOL_PROGRESS>");
			}
			notifier.notifyProgress(UUID.randomUUID().toString(), message);
		} catch (RuntimeException e) {
			LOGGER.warn("Tool progress notification failed: " + e);
		}
	}

	/** The text shown in a notification: on one line, cut to {@value #MAX_SHOWN_TEXT} characters. */
	static String shown(String text) {
		if (text == null) {
			return "";
		}
		final String oneLine = text.replaceAll("\\s+", " ").trim();
		return oneLine.length() > MAX_SHOWN_TEXT ? oneLine.substring(0, MAX_SHOWN_TEXT - 3) + "..." : oneLine;
	}
}
