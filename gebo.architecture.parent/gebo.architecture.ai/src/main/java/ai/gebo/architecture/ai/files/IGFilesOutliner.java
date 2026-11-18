/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import java.io.IOException;

/**
 * Outlines files of a kind from their document model - a presentation's
 * slides with their layouts, titles and shape references, a workbook's sheets
 * with their used ranges and headings, a document's sections - so that an agent
 * knows what a file holds and how to address its parts without reading it
 * whole. A library that works on a kind of file outlines it, in whatever
 * location the file is.
 */
public interface IGFilesOutliner {

	/** Whether this outliner knows the kind of the file at a path (by its extension). */
	boolean outlines(String path);

	/**
	 * The outline of a file as it is now, at most about {@code maxTokens} tokens:
	 * a longer outline is cut, saying with which tool and from where to read
	 * on. Null when the file cannot be outlined.
	 */
	String outline(IGFilesLocation location, String path, int maxTokens) throws IOException;
}
