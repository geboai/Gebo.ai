/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

/**
 * When a chat model's request carries a sampling parameter (temperature, top_p), as its
 * provider's integration sends it: the editors enable the parameter's control by it.
 */
public enum GSamplingAvailability {
	/** Sent when set. */
	ALWAYS,
	/** Never sent: the model refuses it, or its provider ignores it. */
	NEVER,
	/** Sent only with the thinking disabled, the model then taking effort none. */
	WITHOUT_THINKING
}
