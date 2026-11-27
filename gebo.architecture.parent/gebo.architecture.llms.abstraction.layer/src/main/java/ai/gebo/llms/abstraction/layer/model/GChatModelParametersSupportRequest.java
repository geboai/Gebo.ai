/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import java.util.Map;

import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * The model a chat model editor asks the parameters of: its type, its code, and what
 * the provider's models api told of it, as the chosen model carries it (its metadata,
 * and the provider's own details, e.g. Anthropic's thinking capabilities or Ollama's
 * /api/show ones, kept on the choice).
 */
@Getter
@Setter
public class GChatModelParametersSupportRequest {
	/** The chat model type code, e.g. "chatgpt-OpenAI". */
	private String modelTypeCode = null;
	/** The model the requests name. */
	private String modelCode = null;
	/** The metadata of the chosen model, may be null. */
	private ModelMetaInfo metaInfos = null;
	/** The provider's details of the chosen model, may be null. */
	private Map<String, Object> modelDetails = null;
}
