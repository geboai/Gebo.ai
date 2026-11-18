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

/**
 * The files locations a conversation reaches: those of the library instances it
 * holds whose sources hold files ({@link IGAILibraryFilesSource}), then those of
 * the conversation's files providers ({@link IGConversationFilesProvider}).
 */
public interface IGAILibraryFilesService {
	/** Every location of a conversation, libraries first; empty when it has none. */
	List<IGFilesLocation> locationsOf(String chatSessionCode);

	/**
	 * A location of a conversation by its id.
	 *
	 * @throws IllegalArgumentException naming the locations there are, when there is
	 *                                  none of that id
	 */
	IGFilesLocation locationOf(String chatSessionCode, String locationId);
}
