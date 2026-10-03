/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * The room a model call leaves to the results of the tools it calls: the results
 * pile up in the call's messages, so a tool returning contents takes them out of
 * it. An agent shares one through the tools context of each of its model calls,
 * sized on its own token budget (see {@code agentTokenBudget}) less the
 * placeholders it renders; the content tools never return more than what is left.
 * A model call without one leaves the tools to their own sizes.
 */
public final class ToolsTokenBudget {
	private static final Logger LOGGER = LoggerFactory.getLogger(ToolsTokenBudget.class);
	public static final String TOOLS_CONTEXT_KEY = "geboToolsTokenBudget";

	private final AtomicInteger left;

	public ToolsTokenBudget(int tokens) {
		this.left = new AtomicInteger(Math.max(0, tokens));
	}

	/**
	 * The given context, its model call leaving {@code tokens} to the tools' results.
	 * A null context is returned as it is.
	 */
	public static IChatRequestContext sharedThrough(IChatRequestContext context, int tokens) {
		if (context == null) {
			return null;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The tools' results of this model call may take " + Math.max(0, tokens) + " token(s)");
		}
		return ToolsContextSharing.with(context, TOOLS_CONTEXT_KEY, new ToolsTokenBudget(tokens));
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
		if (toolContext == null || toolContext.getContext() == null) {
			return null;
		}
		return toolContext.getContext().get(TOOLS_CONTEXT_KEY) instanceof ToolsTokenBudget budget ? budget : null;
	}

	public int left() {
		return left.get();
	}

	/** What a tool wanting {@code wanted} tokens may return. */
	public int grant(int wanted) {
		return Math.max(0, Math.min(wanted, left.get()));
	}

	/** Takes what a tool returned out of the room left. */
	public void consume(int used) {
		final int remaining = left.updateAndGet(current -> Math.max(0, current - Math.max(0, used)));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("A tool returned " + used + " token(s), " + remaining + " left to the tools of this model call");
		}
	}
}
