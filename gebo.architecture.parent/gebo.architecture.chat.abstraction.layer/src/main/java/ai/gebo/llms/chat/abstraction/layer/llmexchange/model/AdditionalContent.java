/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * A single piece of content carried alongside a chat request or response,
 * outside of the plain text query/answer.
 */
@Data
public class AdditionalContent {
	@NotNull
	private String contentType;
	@NotNull
	private String name;
	@NotNull
	private String content;
}
