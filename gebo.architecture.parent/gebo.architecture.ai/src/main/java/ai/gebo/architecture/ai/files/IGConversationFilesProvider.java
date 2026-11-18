/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

/**
 * Files a conversation has outside its libraries - the files uploaded to it,
 * the documents of its knowledge bases - offered as a files location: read
 * only, a tool writes what it makes of them into a writable library.
 */
public interface IGConversationFilesProvider {
	/** The id of the locations this provider gives: what the tools name them by. */
	String getId();

	/** The files of a conversation, or null when it has none here. */
	IGFilesLocation filesOf(String chatSessionCode);
}
