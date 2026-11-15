/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import lombok.AllArgsConstructor;

/**
 * Drops the transient conversations whose run never came back to drop them: a
 * run from outside a chat (an MCP tool, a single A2A agent) opens one for itself
 * and drops it when it is over, unless the node went down in between. A day is
 * longer than any such run.
 */
@Component
@AllArgsConstructor
public class GTransientChatSessionsSweeper {
	private static final Logger LOGGER = LoggerFactory.getLogger(GTransientChatSessionsSweeper.class);
	/** How old a transient conversation is before it is taken as abandoned. */
	static final long ABANDONED_AFTER_MILLIS = 24L * 3600_000L;

	private final IGChatSessionLifeCycleService lifeCycleService;

	@Scheduled(fixedDelay = 900_000L, initialDelay = 300_000L)
	public void sweep() {
		try {
			lifeCycleService
					.dropTransientChatSessionsOpenedBefore(new Date(System.currentTimeMillis() - ABANDONED_AFTER_MILLIS));
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot sweep the abandoned transient chats: {}", e.getMessage());
		}
	}
}
