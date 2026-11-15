/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.model;

/** Why a suspended run was dropped, never to be respawned. */
public enum WaitingConditionDropReason {
	/** The conversation went on with a normal interaction, past a block with no mandatory condition. */
	NEW_INTERACTION,
	/** The conversation suspended again, into a newer block. */
	SUSPENDED_AGAIN,
	/** Its owner, or an administrator, abandoned it. */
	ABANDONED
}
