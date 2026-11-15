/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.base.GBaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The respawn of a block by the events that solved it: its code is the block's,
 * so writing it first is the claim, which one instance or thread wins; the
 * others leave the block to it. A failed respawn is claimed again once its
 * backoff is over, a running one once its lease is: the instance running it went
 * down.
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class GConditionDispatch extends GBaseObject {
	private static final long serialVersionUID = 1L;

	public static enum State {
		/** Claimed: a respawn runs, or ran on an instance that went down once the lease is over. */
		RUNNING,
		/** The respawn failed: it is claimed again at {@code nextAttemptAt}. */
		FAILED,
		/** The respawn consumed the block, or found it closed. */
		DONE,
		/** The respawn failed as many times as it may, or cannot run at all. */
		GAVE_UP
	}

	private String sessionCode = null;
	private State state = State.RUNNING;
	private int attempts = 0;
	private Date claimedAt = null;
	private Date leaseUntil = null;
	private Date nextAttemptAt = null;
	private String lastFailure = null;
	/** The request of the last respawn, and the conditions its reaction solved. */
	private String requestId = null;
	private List<String> conditionIds = new ArrayList<String>();
	private Date endedAt = null;
}
