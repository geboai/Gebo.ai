/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import java.util.List;

import org.springframework.ai.chat.model.ToolContext;

/**
 * Keeps the files the tools of a request wrote, for the whole request: every
 * agent and every step of a run report to the same keeper, which the tool
 * context carries. What it kept goes with the request's answer.
 */
public interface IGConversationFilesKeeper {
	/** The key of the request's keeper in a tools context. */
	String TOOLS_CONTEXT_KEY = "geboConversationFilesKeeper";

	/** The keeper a tools context carries, or null. */
	static IGConversationFilesKeeper of(java.util.Map<String, Object> toolsContext) {
		return toolsContext != null && toolsContext.get(TOOLS_CONTEXT_KEY) instanceof IGConversationFilesKeeper keeper
				? keeper
				: null;
	}

	/**
	 * Records a file a tool wrote, at its version once written: a file written
	 * again keeps its first place and takes the new version.
	 */
	void written(GLibraryFile file);

	/** The files written so far, in the order they were first written. */
	List<GConversationFile> getWritten();

	/**
	 * Reports a file a tool wrote to the keeper the tool context carries:
	 * nothing when it carries none (a call outside a run, a test).
	 */
	static void report(ToolContext context, GLibraryFile file) {
		if (context == null || context.getContext() == null || file == null || file.path() == null
				|| file.path().isBlank()) {
			return;
		}
		for (Object value : context.getContext().values()) {
			if (value instanceof IGConversationFilesKeeper keeper) {
				keeper.written(file);
			}
		}
	}
}
