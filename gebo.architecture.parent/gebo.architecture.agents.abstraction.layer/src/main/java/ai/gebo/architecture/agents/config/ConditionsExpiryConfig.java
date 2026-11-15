/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.config;

import java.util.Date;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.agents.model.ConditionExpiry;
import lombok.Data;

/**
 * The platform's default expiry of the conditions agents wait for
 * ({@code ai.gebo.agents.conditions.*}), given when neither the waiting tool nor
 * the agent's configuration gives one. Off by default: no minutes, no expiry.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.agents.conditions")
@Data
public class ConditionsExpiryConfig {
	/** Minutes from the question to its expiry; 0 or less, none. */
	private long defaultExpiryMinutes = 0;
	/** Whether the default expiry answers the condition, with no value, so that the run goes on. */
	private boolean defaultExpiryUnblocks = false;

	/** The default expiry of a condition asked at that moment; {@code null} when off. */
	public ConditionExpiry from(Date askedAt) {
		return ConditionExpiry.afterMinutes(askedAt, defaultExpiryMinutes, defaultExpiryUnblocks);
	}
}
