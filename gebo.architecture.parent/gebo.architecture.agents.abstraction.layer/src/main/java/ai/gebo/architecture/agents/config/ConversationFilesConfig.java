/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * How the agents are told of the files the tools of their conversation wrote
 * ({@code ai.gebo.agents.conversation-files.*}): each file with its outline as
 * it is now, within a share of the agent's own budget of tokens - so the room
 * grows with the model's context - and a cap for each file.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.agents.conversation-files")
@Data
public class ConversationFilesConfig {
	/** Whether the agents are told of the files the conversation's tools wrote. */
	private boolean enabled = true;
	/** The most tokens one file's outline takes: a longer one is cut, saying how to read on. */
	private int maxTokensPerFile = 20000;
	/** The share of an agent's budget of tokens the files take at most, from 0 to 1. */
	private double maxShareOfBudget = 0.25;

	/** The tokens the files take at most for an agent with a budget of tokens; 0 when disabled. */
	public int maxTokens(int agentBudget) {
		if (!enabled || agentBudget <= 0) {
			return 0;
		}
		final double share = Math.max(0, Math.min(1, maxShareOfBudget));
		return (int) Math.floor(agentBudget * share);
	}
}
