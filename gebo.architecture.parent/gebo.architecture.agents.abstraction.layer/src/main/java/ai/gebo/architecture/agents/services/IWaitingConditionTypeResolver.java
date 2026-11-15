/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;

/**
 * Tells the type of a condition an agent waits for: the id of the translator that
 * shows it to the client and reads the answers to it. The translators live above
 * the agents layer, so this is implemented there and handed to the suspension
 * handler, which keeps the type with the condition when the block is written: the
 * answer is then read by the translator the question was shown with, whatever
 * translators are registered when it comes.
 */
public interface IWaitingConditionTypeResolver {

	/**
	 * The type of the condition, {@code null} when the condition is {@code null} or
	 * no type can be told.
	 */
	String typeOf(AgentSuspendedWaitingCondition<?, ?> condition);
}
