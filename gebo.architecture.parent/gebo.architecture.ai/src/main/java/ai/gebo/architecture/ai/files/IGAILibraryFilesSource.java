/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;

/**
 * What a library source holding files implements on top of
 * {@link IGAIToolsAndResourcesLibrarySource}: an instance's files as a
 * {@link IGFilesLocation}, so that the tools of other libraries bound to the
 * same agent work on them too.
 */
public interface IGAILibraryFilesSource {
	/**
	 * The files of an instance of a type this source manages, or null when the
	 * instance holds none. The location's id is the instance's library type id.
	 */
	IGFilesLocation filesOf(AIToolsAndResourcesLibraryInstance instance);
}
