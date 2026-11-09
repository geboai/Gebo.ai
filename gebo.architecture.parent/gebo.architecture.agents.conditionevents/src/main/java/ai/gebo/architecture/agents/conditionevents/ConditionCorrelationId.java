/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents;

/**
 * What correlates an event to the condition it solves: the session the
 * suspended network of agents serves (its chat session code) and the id of the
 * condition an agent waits for, which stays the same when the run suspends again.
 * An event carries it in either of two forms, or both:
 * <ul>
 * <li>merged in one correlation id, {@code <sessionCode>:<conditionId>};</li>
 * <li>as the pair, session code and condition id.</li>
 * </ul>
 * The merged form is split at its last {@value #SEPARATOR}: a condition id is a
 * UUID and never holds one, so a session code may.
 */
public record ConditionCorrelationId(String sessionCode, String conditionId) {
	public static final char SEPARATOR = ':';
	public static final String NOTHING_TO_CORRELATE = "The event names no condition: give a correlation id, or a session code and a condition id";
	public static final String NOT_A_CORRELATION_ID = "Not a correlation id (<sessionCode>:<conditionId>): ";
	public static final String FORMS_DISAGREE = "The correlation id and the session code and condition id given name different conditions: ";

	public ConditionCorrelationId {
		if (isBlank(sessionCode) || isBlank(conditionId)) {
			throw new IllegalArgumentException(NOTHING_TO_CORRELATE);
		}
		sessionCode = sessionCode.strip();
		conditionId = conditionId.strip();
	}

	/** The merged form. */
	public String composed() {
		return sessionCode + SEPARATOR + conditionId;
	}

	/** The pair a merged correlation id stands for. */
	public static ConditionCorrelationId parse(String correlationId) {
		if (isBlank(correlationId)) {
			throw new IllegalArgumentException(NOTHING_TO_CORRELATE);
		}
		final String id = correlationId.strip();
		final int at = id.lastIndexOf(SEPARATOR);
		if (at <= 0 || at == id.length() - 1) {
			throw new IllegalArgumentException(NOT_A_CORRELATION_ID + correlationId);
		}
		return new ConditionCorrelationId(id.substring(0, at), id.substring(at + 1));
	}

	/**
	 * The condition an event names, from whichever forms it carries; when it carries
	 * both they must name the same condition.
	 *
	 * @throws IllegalArgumentException when it names none, or two different ones
	 */
	public static ConditionCorrelationId of(String correlationId, String sessionCode, String conditionId) {
		final boolean pairGiven = !isBlank(sessionCode) || !isBlank(conditionId);
		if (isBlank(correlationId)) {
			return new ConditionCorrelationId(sessionCode, conditionId);
		}
		final ConditionCorrelationId merged = parse(correlationId);
		if (pairGiven && !merged.equals(new ConditionCorrelationId(sessionCode, conditionId))) {
			throw new IllegalArgumentException(FORMS_DISAGREE + correlationId + " / " + sessionCode + ", " + conditionId);
		}
		return merged;
	}

	@Override
	public String toString() {
		return composed();
	}

	private static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
