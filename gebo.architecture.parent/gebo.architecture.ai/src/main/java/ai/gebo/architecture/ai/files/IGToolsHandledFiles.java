/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.ai.chat.model.ToolContext;

/**
 * Whoever keeps, for a run, the files the tools of the libraries handled: the
 * ones they listed, read, wrote, copied or moved. An answer naming one of them
 * names a file of this run, not one it never saw - a run checking that its
 * answer cites only what it read counts them as read.
 * <p>
 * The keeper travels in the tool context; a library's tools report to it with
 * {@link #report(ToolContext, Collection)}, without knowing who keeps them.
 */
public interface IGToolsHandledFiles {

	/** Records the paths of files a tool handled, as the tool named them. */
	void addHandled(Collection<String> paths);

	/** The paths recorded so far. */
	List<String> getHandledNames();

	/**
	 * Reports the files a tool handled to the keeper the tool context carries:
	 * nothing when it carries none (a call outside a run, a test).
	 */
	static void report(ToolContext context, Collection<String> paths) {
		if (context == null || context.getContext() == null || paths == null || paths.isEmpty()) {
			return;
		}
		final List<String> given = new ArrayList<>();
		for (String path : paths) {
			if (path != null && !path.isBlank()) {
				given.add(path.trim().replace('\\', '/'));
			}
		}
		if (given.isEmpty()) {
			return;
		}
		for (Object value : context.getContext().values()) {
			if (value instanceof IGToolsHandledFiles keeper) {
				keeper.addHandled(given);
			}
		}
	}

	/** {@link #report(ToolContext, Collection)} for a few paths. */
	static void report(ToolContext context, String... paths) {
		if (paths != null) {
			report(context, Arrays.asList(paths));
		}
	}
}
