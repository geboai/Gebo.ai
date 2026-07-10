/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GRankerModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.aws_bedrock.model.GBedrockRankerModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockRankerModelConfig;
import ai.gebo.ranker.model.RankerModel;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeClient;

/**
 * Reranking model configuration support for AWS Bedrock, plugged into the native
 * platform ranker abstraction. Reranking is served through the Bedrock Agent
 * Runtime {@code Rerank} operation (Amazon Rerank / Cohere Rerank).
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class BedrockRankerModelConfigurationSupportService
		implements IGRankerModelConfigurationSupportService<GBedrockRankerModelChoice, GBedrockRankerModelConfig> {

	static final GRankerModelType type = new GRankerModelType();
	static {
		type.setCode("ranker-aws-bedrock");
		type.setDescription("Reranking models hosted on AWS Bedrock (Amazon Rerank / Cohere Rerank)");
		type.setModelConfigurationClass(GBedrockRankerModelConfig.class.getName());
	}

	/** Known Bedrock reranking foundation models. */
	static final String[] KNOWN_RERANK_MODELS = new String[] { "amazon.rerank-v1:0", "cohere.rerank-v3-5:0" };

	final BedrockCredentialsResolver credentialsResolver;
	final ModelRuntimeConfigureHandler configureHandler;

	class BedrockConfigurableRankerModel implements IGConfigurableRankerModel<GBedrockRankerModelConfig> {

		private GBedrockRankerModelConfig config;
		private GRankerModelType modelType;
		private BedrockAgentRuntimeClient client;
		private RankerModel rankerModel;

		@Override
		public String getCode() {
			return config != null ? config.getCode() : null;
		}

		@Override
		public String getDescription() {
			return config != null ? config.getDescription() : null;
		}

		@Override
		public GRankerModelType getType() {
			return modelType;
		}

		@Override
		public void initialize(GBedrockRankerModelConfig config, GRankerModelType type) throws LLMConfigException {
			this.modelType = type;
			reconfigure(config);
		}

		@Override
		public void reconfigure(GBedrockRankerModelConfig config) throws LLMConfigException {
			if (config.getChoosedModel() == null || config.getChoosedModel().getCode() == null) {
				throw new LLMConfigException("AWS Bedrock ranker requires a chosen model id");
			}
			this.config = config;
			AwsCredentialsProvider credentials = credentialsResolver.resolveCredentials(config.getApiSecretCode());
			Region region = credentialsResolver.resolveRegion(config.getApiSecretCode());
			this.client = BedrockAgentRuntimeClient.builder().region(region).credentialsProvider(credentials).build();
			String modelArn = toModelArn(region.id(), config.getChoosedModel().getCode());
			this.rankerModel = new BedrockRankerModel(client, modelArn);
		}

		@Override
		public GBedrockRankerModelConfig getConfig() {
			return config;
		}

		@Override
		public void delete() throws LLMConfigException {
			if (client != null) {
				try {
					client.close();
				} catch (Throwable t) {
					// ignore
				}
				client = null;
			}
			rankerModel = null;
		}

		@Override
		public RankerModel getRankerModel() {
			return rankerModel;
		}
	}

	/**
	 * Builds the foundation model ARN required by the Rerank API from a plain model
	 * id, or returns the value unchanged when it already is an ARN.
	 */
	static String toModelArn(String regionId, String modelId) {
		if (modelId.startsWith("arn:")) {
			return modelId;
		}
		return "arn:aws:bedrock:" + regionId + "::foundation-model/" + modelId;
	}

	@Override
	public GRankerModelType getType() {
		return type;
	}

	@Override
	public IGConfigurableRankerModel create(GBedrockRankerModelConfig config) throws LLMConfigException {
		BedrockConfigurableRankerModel model = new BedrockConfigurableRankerModel();
		model.initialize(config, type);
		return model;
	}

	@Override
	public OperationStatus<List<GBedrockRankerModelChoice>> getRawModelChoices(GBedrockRankerModelConfig config) {
		List<GBedrockRankerModelChoice> choices = new ArrayList<>();
		for (String modelId : KNOWN_RERANK_MODELS) {
			GBedrockRankerModelChoice choice = new GBedrockRankerModelChoice();
			choice.setCode(modelId);
			choice.setDescription(modelId);
			choices.add(choice);
		}
		return OperationStatus.of(choices);
	}

	@Override
	public GBedrockRankerModelConfig createBaseConfiguration(String presetModel) {
		GBedrockRankerModelConfig clean = new GBedrockRankerModelConfig();
		clean.setChoosedModel(new GBedrockRankerModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("AWS Bedrock ranker model " + presetModel);
		clean.setDescription("AWS Bedrock ranker model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GBedrockRankerModelConfig> insertAndConfigure(GBedrockRankerModelConfig config)
			throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}
}
