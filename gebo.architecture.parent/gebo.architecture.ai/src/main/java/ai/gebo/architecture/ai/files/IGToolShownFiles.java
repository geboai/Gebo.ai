/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import org.springframework.ai.chat.model.ToolContext;

/**
 * Lets a tool show a file to the model that called it - a picture it extracted, drew
 * or received - so that the model looks at it and does not only read its name. A
 * tool's result is text; the file travels in a message of its own, right after the
 * results of the round's tools, when the model takes files of its type.
 * <p>
 * It is in the tools context of the calls whose model can be shown files; elsewhere
 * there is none and {@link #show(ToolContext, String, String, byte[])} answers null.
 */
public interface IGToolShownFiles {
	String TOOLS_CONTEXT_KEY = "geboToolShownFiles";

	/**
	 * Shows a file to the model after this round of tool calls.
	 *
	 * @return true when the model will see it; false when it does not take files of
	 *         this type, or the round already carries as much as it may
	 */
	boolean show(String name, String mimeType, byte[] data);

	/**
	 * Shows a file to the model of the call a tool runs in.
	 *
	 * @return true when the model will see it, false when it will not, null when the
	 *         call has no way to show files
	 */
	static Boolean show(ToolContext context, String name, String mimeType, byte[] data) {
		if (context == null || context.getContext() == null) {
			return null;
		}
		final Object shown = context.getContext().get(TOOLS_CONTEXT_KEY);
		return shown instanceof IGToolShownFiles files ? files.show(name, mimeType, data) : null;
	}
}
