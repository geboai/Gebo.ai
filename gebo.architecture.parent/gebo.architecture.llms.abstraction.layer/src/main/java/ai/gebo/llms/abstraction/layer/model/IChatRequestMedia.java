/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import java.io.IOException;

/**
 * A file of a chat request that may go to the model as it is - a picture, a PDF, a
 * recording - besides the text of the request. It is a candidate only: each model
 * call attaches it when the model's configuration declares its modality and the
 * provider's integration carries its type ({@link GModelMediaGuard}), and its bytes
 * are read only then.
 */
public interface IChatRequestMedia {
	/** The file's name as the user knows it. */
	public String getName();

	/** The file's MIME type. */
	public String getMimeType();

	/** The file's size in bytes, -1 when unknown. */
	public long getSize();

	/** The file's bytes, read when it is attached to a model call. */
	public byte[] readBytes() throws IOException;
}
