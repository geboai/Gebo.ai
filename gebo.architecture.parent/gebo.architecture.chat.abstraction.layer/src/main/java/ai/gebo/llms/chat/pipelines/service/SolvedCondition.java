/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.llms.chat.pipelines.service;

import java.util.Date;

/**
 * A condition of an open block an answer already solves.
 *
 * @param conditionId  the condition
 * @param sourceSystem the system the answer came from: {@code CHAT}, an ERP...
 * @param actor        who delivered it, when known
 * @param solvedAt     when it was taken for the condition
 */
public record SolvedCondition(String conditionId, String sourceSystem, String actor, Date solvedAt) {
}
