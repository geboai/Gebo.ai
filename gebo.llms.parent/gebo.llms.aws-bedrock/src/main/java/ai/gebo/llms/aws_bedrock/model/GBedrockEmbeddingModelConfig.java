/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.model;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;

/**
 * Configuration for AWS Bedrock embedding models (Amazon Titan / Cohere).
 */
public class GBedrockEmbeddingModelConfig extends GBaseEmbeddingModelConfig<GBedrockEmbeddingModelChoice>
		implements IBedrockRegionAware {

	/** AWS region hosting the Bedrock endpoint (e.g. {@code us-east-1}). */
	private String region = null;

	public GBedrockEmbeddingModelConfig() {
	}

	@Override
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
