/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import java.time.Duration;
import java.util.List;

import org.springframework.ai.bedrock.cohere.BedrockCohereEmbeddingModel;
import org.springframework.ai.bedrock.cohere.api.CohereEmbeddingBedrockApi;
import org.springframework.ai.bedrock.titan.BedrockTitanEmbeddingModel;
import org.springframework.ai.bedrock.titan.api.TitanEmbeddingBedrockApi;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.util.JacksonUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.aws_bedrock.model.GBedrockEmbeddingModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockEmbeddingModelConfig;
import ai.gebo.model.OperationStatus;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.model.ModelModality;
import tools.jackson.databind.json.JsonMapper;

/**
 * Embedding model configuration support for AWS Bedrock, backed by the Spring AI
 * {@link BedrockTitanEmbeddingModel} (Amazon Titan) and
 * {@link BedrockCohereEmbeddingModel} (Cohere) integrations. The proper backend
 * is selected from the chosen model id.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class BedrockEmbeddingModelConfigurationSupportService implements
		IGEmbeddingModelConfigurationSupportService<GBedrockEmbeddingModelChoice, GBedrockEmbeddingModelConfig> {

	static final GEmbeddingModelType type = new GEmbeddingModelType();
	static {
		type.setCode("embedding-aws-bedrock");
		type.setDescription("Embedding models hosted on AWS Bedrock (Amazon Titan / Cohere)");
		type.setModelConfigurationClass(GBedrockEmbeddingModelConfig.class.getName());
	}

	static final Duration API_TIMEOUT = Duration.ofMinutes(2);

	final BedrockCredentialsResolver credentialsResolver;
	final BedrockFoundationModelsLookupService modelsLookupService;
	final IGVectorStoreFactoryProvider storeFactoryProvider;
	final ModelRuntimeConfigureHandler configureHandler;

	class BedrockConfigurableEmbeddingModel
			extends GAbstractConfigurableEmbeddingModel<GBedrockEmbeddingModelConfig, EmbeddingModel> {

		public BedrockConfigurableEmbeddingModel() {
			super(storeFactoryProvider);
		}

		@Override
		protected EmbeddingModel configureModel(GBedrockEmbeddingModelConfig config, GEmbeddingModelType type)
				throws LLMConfigException {
			if (config.getChoosedModel() == null || config.getChoosedModel().getCode() == null) {
				throw new LLMConfigException("AWS Bedrock embedding model requires a chosen model id");
			}
			String modelId = config.getChoosedModel().getCode();
			AwsCredentialsProvider credentials = credentialsResolver.resolveCredentials(config.getApiSecretCode());
			Region region = credentialsResolver.resolveRegion(config.getRegion());
			JsonMapper jsonMapper = JacksonUtils.getDefaultJsonMapper();

			if (modelId.startsWith("cohere.")) {
				CohereEmbeddingBedrockApi api = new CohereEmbeddingBedrockApi(modelId, credentials, region, jsonMapper,
						API_TIMEOUT);
				return new BedrockCohereEmbeddingModel(api);
			}
			// Amazon Titan embeddings (amazon.titan-embed-*) and default fallback
			TitanEmbeddingBedrockApi api = new TitanEmbeddingBedrockApi(modelId, credentials, region, jsonMapper,
					API_TIMEOUT);
			return new BedrockTitanEmbeddingModel(api, ObservationRegistry.NOOP);
		}
	}

	@Override
	public GEmbeddingModelType getType() {
		return type;
	}

	@Override
	public IGConfigurableEmbeddingModel<GBedrockEmbeddingModelConfig> create(GBedrockEmbeddingModelConfig config)
			throws LLMConfigException {
		BedrockConfigurableEmbeddingModel model = new BedrockConfigurableEmbeddingModel();
		model.initialize(config, type);
		return model;
	}

	@Override
	public OperationStatus<List<GBedrockEmbeddingModelChoice>> getModelChoices(GBedrockEmbeddingModelConfig config) {
		return modelsLookupService.listModels(config.getApiSecretCode(), config.getRegion(), ModelModality.EMBEDDING,
				GBedrockEmbeddingModelChoice::new);
	}

	@Override
	public GBedrockEmbeddingModelConfig createBaseConfiguration(String presetModel) {
		GBedrockEmbeddingModelConfig clean = new GBedrockEmbeddingModelConfig();
		clean.setChoosedModel(new GBedrockEmbeddingModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("embedding model " + presetModel);
		clean.setDescription("AWS Bedrock embedding model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GBedrockEmbeddingModelConfig> insertAndConfigure(GBedrockEmbeddingModelConfig config)
			throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}
}
