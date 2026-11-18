/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A file a tool of a conversation wrote: the files location it is in (a
 * library type id, or a conversation files provider's id), its path there, and
 * its version once written. Kept with the answer of the request that wrote it,
 * so that the agents of the next requests know what the conversation built
 * and whether it changed since.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GConversationFile {
	/** The files location the file is in, as the tools name it. */
	private String location = null;
	/** The file's path in its location, with '/' separators. */
	private String path = null;
	/** The file's version once written ({@link GLibraryFile#version()}); null when not known. */
	private String version = null;

	/** The key of the file, the same for every version: its location and path. */
	public String key() {
		return location + ":" + path;
	}
}
