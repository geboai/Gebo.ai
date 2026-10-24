/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * How many tokens of knowledge base contents a model call may take: the one capping
 * formula of the knowledge base searches, the tools' and the chats' alike.
 * <p>
 * A model call's room is what its messages leave of its context length, of which
 * {@link GAbstractConfigurableChatModel#TOOLS_ROOM_SHARE} goes to contents, the rest
 * to the answer; the knowledge base contents take that room divided by the room
 * divisor ({@code ai.gebo.llms.knowledge-base-search-room-divisor}, {@value #DEFAULT_ROOM_DIVISOR}
 * by default), never more than the room.
 */
public final class KnowledgeBaseSearchBudget {
	private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseSearchBudget.class);
	/** The share of the room the knowledge base contents may take: a third by default. */
	public static final double DEFAULT_ROOM_DIVISOR = 3.0d;
	/** The configured room divisor, set by {@code KnowledgeBaseSearchBudgetConfig}. */
	private static volatile double roomDivisor = DEFAULT_ROOM_DIVISOR;

	private KnowledgeBaseSearchBudget() {
	}

	/** The room divisor in force. */
	public static double roomDivisor() {
		return roomDivisor;
	}

	/** Sets the room divisor in force; one not a positive finite number leaves the default. */
	public static void setRoomDivisor(double divisor) {
		roomDivisor = validDivisor(divisor, "ai.gebo.llms.knowledge-base-search-room-divisor");
	}

	/**
	 * The divisor when a positive finite number, {@link #DEFAULT_ROOM_DIVISOR} otherwise,
	 * {@code property} naming it in the warning.
	 */
	public static double validDivisor(double divisor, String property) {
		if (divisor > 0 && !Double.isInfinite(divisor)) {
			return divisor;
		}
		LOGGER.warn(property + " " + divisor + " is not a positive number, " + DEFAULT_ROOM_DIVISOR + " is used");
		return DEFAULT_ROOM_DIVISOR;
	}

	/** The contents a room of {@code room} tokens gives: the room divided by {@code divisor}, never more than it. */
	public static int shareOfRoom(int room, double divisor) {
		final int left = Math.max(0, room);
		return (int) Math.max(0, Math.min(left, Math.floor(left / divisor)));
	}

	/**
	 * The room a model call of {@code contextLength} leaves to contents when its
	 * messages already take {@code usedTokens}.
	 */
	public static int roomOfContext(int contextLength, long usedTokens) {
		return (int) Math.max(0, Math.min(Integer.MAX_VALUE,
				(long) ((contextLength - Math.max(0, usedTokens)) * GAbstractConfigurableChatModel.TOOLS_ROOM_SHARE)));
	}

	/**
	 * The knowledge base contents a model call of {@code contextLength} may take when its
	 * messages already take {@code usedTokens}, with the room divisor in force.
	 */
	public static int forContext(int contextLength, long usedTokens) {
		final int room = roomOfContext(contextLength, usedTokens);
		final int tokens = shareOfRoom(room, roomDivisor);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("forContext(" + contextLength + ", " + usedTokens + ") room:" + room + " (tok) divisor:"
					+ roomDivisor + " knowledge base contents at most:" + tokens + " (tok)");
		}
		return tokens;
	}
}
