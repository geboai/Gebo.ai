/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.aws_bedrock.model.GBedrockEmbeddingModelConfig;

/**
 * MongoDB repository for AWS Bedrock embedding model configurations.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
public interface BedrockEmbeddingModelConfigRepository extends IGBaseMongoDBRepository<GBedrockEmbeddingModelConfig> {
	@Override
	default Class<GBedrockEmbeddingModelConfig> getManagedType() {
		return GBedrockEmbeddingModelConfig.class;
	}
}
