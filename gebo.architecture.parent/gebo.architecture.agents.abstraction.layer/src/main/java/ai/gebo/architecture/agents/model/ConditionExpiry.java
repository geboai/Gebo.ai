/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * When a condition an agent waits for expires, and what its expiry does. Set by
 * software only, never by a model: the waiting tool's own code, the agent's
 * configuration, or the platform's default. When {@link #expiryUnblocks} the
 * expiry answers the condition, even a mandatory one, with no value, and the run
 * goes on; otherwise the condition keeps waiting and its owner is told, until
 * someone goes on without it or abandons the run.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionExpiry implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date expiresAt = null;
	private boolean expiryUnblocks = false;

	/**
	 * The expiry that many minutes from a moment; {@code null} for no moment or no
	 * positive number of minutes.
	 */
	public static ConditionExpiry afterMinutes(Date from, Long minutes, boolean expiryUnblocks) {
		if (from == null || minutes == null || minutes <= 0) {
			return null;
		}
		return new ConditionExpiry(new Date(from.getTime() + minutes * 60_000L), expiryUnblocks);
	}

	/** Whether it is due at that moment. */
	public boolean isDue(Date now) {
		return expiresAt != null && now != null && !now.before(expiresAt);
	}
}
