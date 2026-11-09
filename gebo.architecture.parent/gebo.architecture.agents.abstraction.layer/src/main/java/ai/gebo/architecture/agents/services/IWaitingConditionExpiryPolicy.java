/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.services;

import java.util.Date;

import ai.gebo.architecture.agents.model.AgentConditionsExpiry;
import ai.gebo.architecture.agents.model.ConditionExpiry;

/**
 * Tells the expiry of a condition an agent waits for, from software only: the
 * waiting tool's own, else the agent's configuration's, else the platform's
 * default, else none. A model never sets it.
 */
public interface IWaitingConditionExpiryPolicy {

	/**
	 * @param setByTheTool the expiry the waiting tool's code gave the condition, or
	 *                     {@code null}
	 * @param ofTheAgent   the expiry the agent's configuration gives, or
	 *                     {@code null}
	 * @param askedAt      when the condition was asked
	 * @return the expiry, {@code null} for none
	 */
	ConditionExpiry expiryOf(ConditionExpiry setByTheTool, AgentConditionsExpiry ofTheAgent, Date askedAt);
}
