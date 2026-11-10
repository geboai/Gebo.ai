/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.model;

import java.util.Date;

import ai.gebo.architecture.agents.model.ConditionTarget;

import lombok.Data;
import lombok.NoArgsConstructor;

/** A condition a suspended run waits for, as the waiting conditions panel shows it. */
@Data
@NoArgsConstructor
public class WaitingConditionView {
	private String conditionId = null;
	/** The id of the translator that shows it and reads its answers. */
	private String conditionType = null;
	private String statement = null;
	private boolean mandatory = true;
	/** Whether an answer came for it already, the run waiting for the others. */
	private boolean solved = false;
	/** Who answered it, when it is solved. */
	private String solvedBy = null;
	private Date expiresAt = null;
	/** Whether its expiry answers it, with no value, so that the run goes on. */
	private boolean expiryUnblocks = false;
	/** Whether it is past its expiry and still waits: someone has to go on without it or abandon the run. */
	private boolean expired = false;
	/** Whether this is a human in the loop request handled as a process, shown in the agents processes area. */
	private boolean humanInTheLoopProcessTreatment = false;
	/** Who or what it is waited from: only they may answer it, or an administrator; null for the usual answerers. */
	private ConditionTarget target = null;
}
