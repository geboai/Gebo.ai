/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.model;

import java.io.InputStream;

/**
 * A resource of a library instance handed over as a file: what kind of file it
 * is, what to call it, and its bytes. Whoever takes it closes the stream.
 *
 * @param contentType the media type of the file, e.g. {@code application/zip}
 * @param fileName    a name for the file, with its extension
 * @param content     the bytes, read once
 */
public record AIResourceContent(String contentType, String fileName, InputStream content) {
}
