/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.model;

/**
 * Why a resolved condition has no value. Only the platform sets
 * {@link #EXPIRED} and {@link #GONE_ON_WITHOUT}: they resolve a condition even
 * when it is mandatory, which no client may ask through a reaction.
 */
public enum UnansweredBecause {
	/** Whoever answered chose not to: allowed on a condition that is not mandatory. */
	DECLINED,
	/** Its expiry came, and the expiry lets the run go on. */
	EXPIRED,
	/** Its owner, or an administrator, chose to go on without it. */
	GONE_ON_WITHOUT
}
