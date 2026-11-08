/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.agents.conditionevents.config.ConditionEventsConfig;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsService;

/**
 * What no delivery triggers: an event that came before its block is matched once
 * the block is written, a block with only optional conditions is respawned once
 * it settled, a failed respawn is tried again after its backoff, and pending
 * events expire.
 */
@Component
public class ConditionEventsSweeper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionEventsSweeper.class);
	private final IConditionEventsService service;
	private final ConditionEventsConfig config;

	public ConditionEventsSweeper(IConditionEventsService service, ConditionEventsConfig config) {
		this.service = service;
		this.config = config;
	}

	@Scheduled(fixedDelayString = "${ai.gebo.agents.conditionevents.sweepPeriodMillis:30000}", initialDelayString = "${ai.gebo.agents.conditionevents.sweepInitialDelayMillis:60000}")
	public void sweep() {
		if (!config.isEnabled()) {
			return;
		}
		try {
			service.sweep();
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot sweep the condition events: {}", e.getMessage());
		}
	}
}
