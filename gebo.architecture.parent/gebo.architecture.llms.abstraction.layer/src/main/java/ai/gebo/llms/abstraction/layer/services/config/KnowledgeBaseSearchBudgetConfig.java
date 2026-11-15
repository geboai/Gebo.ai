/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.abstraction.layer.services.KnowledgeBaseSearchBudget;
import jakarta.annotation.PostConstruct;
import lombok.Data;

/**
 * The room divisor of {@link KnowledgeBaseSearchBudget}: the knowledge base contents a
 * model call may take are the room it leaves divided by it. Set via
 * ai.gebo.llms.knowledge-base-search-room-divisor, a positive number (3 by default).
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.llms")
@Data
public class KnowledgeBaseSearchBudgetConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseSearchBudgetConfig.class);
	private double knowledgeBaseSearchRoomDivisor = KnowledgeBaseSearchBudget.DEFAULT_ROOM_DIVISOR;

	@PostConstruct
	public void apply() {
		KnowledgeBaseSearchBudget.setRoomDivisor(knowledgeBaseSearchRoomDivisor);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("apply() knowledge base search room divisor:" + KnowledgeBaseSearchBudget.roomDivisor());
		}
	}
}
