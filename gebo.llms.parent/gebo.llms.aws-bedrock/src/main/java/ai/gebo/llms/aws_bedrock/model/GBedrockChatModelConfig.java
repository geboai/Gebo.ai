/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.model;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;

/**
 * Configuration for AWS Bedrock chat models, served through the unified Bedrock
 * Converse API (Anthropic Claude, Amazon Nova, Meta Llama, Mistral, Cohere
 * Command, AI21 ...). The AWS region and credentials are both carried by the
 * referenced {@code AWS_CONNECTION} secret, so no region is stored here.
 */
public class GBedrockChatModelConfig extends GBaseChatModelConfig<GBedrockChatModelChoice> {

	public GBedrockChatModelConfig() {
	}
}
