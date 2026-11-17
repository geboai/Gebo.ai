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
 * A file of a files location: where it is, its path there, and what tells it
 * apart from what it was or will be.
 *
 * @param location    the id of the location holding it: a library type id, or
 *                    the id of a conversation's files provider
 * @param path        its path in the location, folders separated by /
 * @param size        its size in bytes
 * @param modified    when it last changed, ISO-8601, or null when unknown
 * @param version     an opaque token that changes whenever the content does: a
 *                    write naming it fails when the file changed meanwhile
 * @param contentType its media type, or null when unknown
 */
public record GLibraryFile(String location, String path, long size, String modified, String version,
		String contentType) {
}
