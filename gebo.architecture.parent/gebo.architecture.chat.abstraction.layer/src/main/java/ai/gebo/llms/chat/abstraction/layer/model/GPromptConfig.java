/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.UUID;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * AI generated comments GPromptConfig class represents the configuration for a
 * prompt used in chat models. It contains various properties that define the
 * characteristics of the prompt.
 */
@Data
public class GPromptConfig extends GBaseObject {
	public static final String PROMPT_USE_STANDARD_CHAT_PROMPT = "standard-chat-prompt";
	public static final String PROMPT_USE_RAG_CHAT_PROMPT = "standard-rag-chat-prompt";
	public static final String PROMPT_USE_DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT = "default-chat-pipeline-routing-decision-prompt";
	private String prompt = null;
	private String langCode = "en";
	private String promptUse = null;
	private String modelProvider = null;
	private String modelName = null;
	private String promptCategory = null;
	private Boolean defaultPrompt = null;
	private Boolean ragPrompt = null;
	private GObjectRef<GBaseChatModelConfig> modelConfigurationReference = null;

	/**
	 * Creates a new GPromptConfig instance with a specified prompt. Generates a
	 * random UUID code and assigns it to the new instance.
	 * 
	 * @param prompt2 the prompt to set in the new configuration.
	 * @return a new instance of GPromptConfig with the specified prompt.
	 */
	public static GPromptConfig of(String prompt2) {
		GPromptConfig cfg = new GPromptConfig();
		cfg.setPrompt(prompt2);
		cfg.setCode(UUID.randomUUID().toString());
		return cfg;
	}

}