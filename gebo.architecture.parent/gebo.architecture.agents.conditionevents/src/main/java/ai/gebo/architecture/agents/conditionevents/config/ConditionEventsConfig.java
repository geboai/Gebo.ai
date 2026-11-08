/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * How the events solving the conditions of suspended networks of agents are
 * kept and turned into respawns ({@code ai.gebo.agents.conditionevents.*}).
 * The sweep period is read by the sweeper's schedule
 * ({@code ai.gebo.agents.conditionevents.sweepPeriodMillis}, default 30 s).
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.agents.conditionevents")
@Data
public class ConditionEventsConfig {
	public static final long SECOND = 1000L;
	public static final long MINUTE = 60L * SECOND;
	public static final long HOUR = 60L * MINUTE;

	/** Whether the sweeper works; deliveries are taken either way. */
	private boolean enabled = true;
	/** How long an event no block waits for yet is kept before it expires. */
	private long pendingTimeToLiveMillis = 24L * HOUR;
	/** The wait before the first retry of a failed respawn; it doubles at each failure. */
	private long retryBackoffMillis = 30L * SECOND;
	/** How many times the respawn of a block is tried. */
	private int maxAttempts = 5;
	/**
	 * How long a block with no mandatory condition waits for more of its conditions
	 * after the first is solved, before it is respawned with what it has.
	 */
	private long optionalSettleMillis = 60L * SECOND;
	/**
	 * How long a claimed respawn is left to the instance that claimed it: past it,
	 * the instance is taken as gone down and another claims the block. Longer than
	 * any respawn.
	 */
	private long dispatchLeaseMillis = HOUR;
	/**
	 * The authority of the accounts that may deliver to any session, the run still
	 * being the session owner's; the owner may always deliver to its own sessions.
	 */
	private String applicationAuthority = "APPLICATION";
}
