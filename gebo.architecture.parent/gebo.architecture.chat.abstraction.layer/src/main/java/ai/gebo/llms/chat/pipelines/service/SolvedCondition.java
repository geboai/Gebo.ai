/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.llms.chat.pipelines.service;

import java.util.Date;

import ai.gebo.architecture.agents.model.ConditionSource;

/**
 * A condition of an open block an answer already solves.
 *
 * @param conditionId  the condition
 * @param sourceSystem the system the answer came from: {@code CHAT}, an ERP...
 * @param actor        who delivered it, when known
 * @param solvedAt     when it was taken for the condition
 * @param source       who or what solved it, when known
 */
public record SolvedCondition(String conditionId, String sourceSystem, String actor, Date solvedAt,
		ConditionSource source) {
	/** What the person answering is shown of the source. */
	public static final String YOU = "you";

	public SolvedCondition(String conditionId, String sourceSystem, String actor, Date solvedAt) {
		this(conditionId, sourceSystem, actor, solvedAt, null);
	}

	/**
	 * How the conversation's user is shown who solved it: "you" for its own
	 * answers, the source's label for another, the system with no source.
	 */
	public String solvedByLabel() {
		if (source == null) {
			return sourceSystem;
		}
		return source.isConversationUser() ? YOU : source.label();
	}
}
