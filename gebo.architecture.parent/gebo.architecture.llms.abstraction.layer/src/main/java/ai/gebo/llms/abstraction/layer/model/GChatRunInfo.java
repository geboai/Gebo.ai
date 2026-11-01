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
 * What a model call knows of the run it serves, beside the request itself:
 * where the run comes from and whether somebody is waiting for its answer.
 *
 * @param origin   {@code CHAT}, {@code HARNESS}, {@code A2A}, {@code MCP},
 *                 {@code ROBOT}...; null when unknown
 * @param attended whether a person is waiting for the answer; null when
 *                 unknown
 */
public record GChatRunInfo(String origin, Boolean attended) {
	public static final String CHAT = "CHAT";

	public static GChatRunInfo of(String origin, Boolean attended) {
		return new GChatRunInfo(origin, attended);
	}

	public boolean isAttended() {
		return attended == null || attended.booleanValue();
	}
}
