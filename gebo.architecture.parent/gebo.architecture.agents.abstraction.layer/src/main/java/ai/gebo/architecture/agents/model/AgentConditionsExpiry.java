/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The expiry an agent's configuration gives the conditions the agent waits for,
 * unless its waiting tool gives one of its own. No minutes, or none positive,
 * means the configuration gives none.
 */
@Data
@NoArgsConstructor
public class AgentConditionsExpiry implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Minutes from the question to its expiry. */
	private Long expiryMinutes = null;
	/** Whether the expiry answers the condition, with no value, so that the run goes on. */
	private Boolean expiryUnblocks = null;

	/** The expiry of a condition asked at that moment; {@code null} when none is configured. */
	public ConditionExpiry from(Date askedAt) {
		return ConditionExpiry.afterMinutes(askedAt, expiryMinutes, Boolean.TRUE.equals(expiryUnblocks));
	}
}
