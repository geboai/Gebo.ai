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

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A suspended run waiting for a human in the loop or an external condition, as
 * the waiting conditions panel shows it: whose conversation it is, since when it
 * waits, for what, and whether it is stuck past an expiry.
 */
@Data
@NoArgsConstructor
public class WaitingBlockView {
	private String blockCode = null;
	private String conversationCode = null;
	/** The owner of the conversation, who may answer, go on without or abandon it. */
	private String owner = null;
	private String networkCode = null;
	private Date suspendedAt = null;
	/** Whether a mandatory condition holds the conversation. */
	private boolean mandatory = true;
	/** Whether it suspended with nobody watching the run: a respawn by events, A2A, MCP. */
	private boolean unwatched = false;
	/** The earliest expiry of the conditions it still waits for; null for none. */
	private Date earliestExpiryAt = null;
	/** Whether one of its conditions is past its expiry and still waits. */
	private boolean expired = false;
	/**
	 * Whether at least one of the conditions is a human in the loop request handled
	 * as a process: the block is shown to its owner in the agents processes area.
	 */
	private boolean humanInTheLoopProcessTreatment = false;
	/**
	 * Whether the user listing it may open the conversation: its owner, anyone it
	 * is shared with, an administrator. A user a request is waited from may not.
	 */
	private boolean conversationAccessible = true;
	/**
	 * How many conditions the run still waits for that the listing does not show:
	 * the ones not handled as a human in the loop process, such as a clarification
	 * answered in the conversation. Zero where every condition is shown.
	 */
	private int otherConditionsWaiting = 0;
	private List<WaitingConditionView> conditions = new ArrayList<WaitingConditionView>();
}
