/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk.ToolCallProgress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A tool call the model is writing, streamed to the user as it comes: the pieces of its
 * arguments, then the call complete, which the tool loop then runs. A provider sending
 * its calls whole gives the complete call only.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GToolCallProgress {
	/** The call id, null when the provider gives none with a piece. */
	private String id = null;
	/** The position of the call in the model's message, null when unknown. */
	private Integer index = null;
	/** The tool called. */
	private String name = null;
	/** The piece of the arguments written since the previous event, or all of them once complete. */
	private String arguments = "";
	/** Whether the call is written whole: its arguments are then all of them. */
	private boolean complete = false;

	public static GToolCallProgress of(ToolCallProgress progress) {
		return new GToolCallProgress(progress.id(), progress.index(), progress.name(), progress.arguments(),
				progress.complete());
	}
}
