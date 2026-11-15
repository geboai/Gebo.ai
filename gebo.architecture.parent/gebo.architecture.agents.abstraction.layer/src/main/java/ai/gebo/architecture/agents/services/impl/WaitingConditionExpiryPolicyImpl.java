/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.config.ConditionsExpiryConfig;
import ai.gebo.architecture.agents.model.AgentConditionsExpiry;
import ai.gebo.architecture.agents.model.ConditionExpiry;
import ai.gebo.architecture.agents.services.IWaitingConditionExpiryPolicy;

/**
 * The waiting tool's expiry, else the agent configuration's, else the platform's
 * default, else none.
 */
@Service
public class WaitingConditionExpiryPolicyImpl implements IWaitingConditionExpiryPolicy {
	private final ConditionsExpiryConfig defaults;

	public WaitingConditionExpiryPolicyImpl(@Autowired(required = false) ConditionsExpiryConfig defaults) {
		this.defaults = defaults;
	}

	@Override
	public ConditionExpiry expiryOf(ConditionExpiry setByTheTool, AgentConditionsExpiry ofTheAgent, Date askedAt) {
		if (setByTheTool != null && setByTheTool.getExpiresAt() != null) {
			return setByTheTool;
		}
		final ConditionExpiry configured = ofTheAgent != null ? ofTheAgent.from(askedAt) : null;
		if (configured != null) {
			return configured;
		}
		return defaults != null ? defaults.from(askedAt) : null;
	}
}
