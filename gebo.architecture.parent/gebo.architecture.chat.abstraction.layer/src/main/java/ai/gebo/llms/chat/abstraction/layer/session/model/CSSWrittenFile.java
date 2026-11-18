/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.session.model;

import ai.gebo.architecture.ai.files.GConversationFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A file the tools of a request wrote, with the index of that request in its
 * conversation (0 the first): kept by a summarized history for the requests it
 * no longer holds, so the agents still know the files they built.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSSWrittenFile {
	private Integer interactionIndex = null;
	private GConversationFile file = null;
}
