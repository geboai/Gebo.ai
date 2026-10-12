/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.messages.services;

import java.util.Date;

import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.system.messages.model.SystemMessageAudience;

/**
 * What a module publishes as a system message.
 *
 * @param source      the publishing module, e.g. "backup"
 * @param key         the publisher's identifier for the message within its source;
 *                    publishing again under the same source and key replaces it
 * @param severity    the message severity
 * @param summary     a short title
 * @param detail      the full text, may be null
 * @param audience    who the message is routed to
 * @param dismissible whether users can hide it
 * @param expiresAt   when it stops being shown, null for until retracted
 */
public record SystemMessagePublication(String source, String key, MsgServerity severity, String summary,
		String detail, SystemMessageAudience audience, boolean dismissible, Date expiresAt) {
}
