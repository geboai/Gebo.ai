/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import java.util.List;

import org.springframework.ai.image.ImageModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GImageModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.aws_bedrock.model.GBedrockImageModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockImageModelConfig;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.model.ModelModality;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

/**
 * Image generation model configuration support for AWS Bedrock (Amazon Nova
 * Canvas / Titan Image / Stability), served through the AWS SDK
 * {@code InvokeModel} operation wrapped by {@link BedrockImageModel}.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class BedrockImageModelConfigurationSupportService
		implements IGImageModelConfigurationSupportService<GBedrockImageModelChoice, GBedrockImageModelConfig> {

	static final GImageModelType type = new GImageModelType();
	static {
		type.setCode("image-generation-aws-bedrock");
		type.setDescription("Image generation models hosted on AWS Bedrock");
		type.setModelConfigurationClass(GBedrockImageModelConfig.class.getName());
	}

	final BedrockCredentialsResolver credentialsResolver;
	final BedrockFoundationModelsLookupService modelsLookupService;
	final ModelRuntimeConfigureHandler configureHandler;

	class BedrockConfigurableImageModel
			extends GAbstractConfigurableImageModel<GBedrockImageModelConfig, ImageModel> {

		@Override
		protected ImageModel configureModel(GBedrockImageModelConfig config, GImageModelType type)
				throws LLMConfigException {
			if (config.getChoosedModel() == null || config.getChoosedModel().getCode() == null) {
				throw new LLMConfigException("AWS Bedrock image model requires a chosen model id");
			}
			AwsCredentialsProvider credentials = credentialsResolver.resolveCredentials(config.getApiSecretCode());
			Region region = credentialsResolver.resolveRegion(config.getRegion());
			BedrockRuntimeClient client = BedrockRuntimeClient.builder().region(region)
					.credentialsProvider(credentials).build();
			return new BedrockImageModel(client, config.getChoosedModel().getCode(), config.getHeight(),
					config.getWidth(), config.getCfgScale(), config.getSeed());
		}
	}

	@Override
	public GImageModelType getType() {
		return type;
	}

	@Override
	public IGConfigurableImageModel create(GBedrockImageModelConfig config) throws LLMConfigException {
		BedrockConfigurableImageModel model = new BedrockConfigurableImageModel();
		model.initialize(config, type);
		return model;
	}

	@Override
	public OperationStatus<List<GBedrockImageModelChoice>> getModelChoices(GBedrockImageModelConfig config) {
		return modelsLookupService.listModels(config.getApiSecretCode(), config.getRegion(), ModelModality.IMAGE,
				GBedrockImageModelChoice::new);
	}

	@Override
	public GBedrockImageModelConfig createBaseConfiguration(String presetModel) {
		GBedrockImageModelConfig clean = new GBedrockImageModelConfig();
		clean.setChoosedModel(new GBedrockImageModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("image generation model " + presetModel);
		clean.setDescription("AWS Bedrock image generation model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GBedrockImageModelConfig> insertAndConfigure(GBedrockImageModelConfig config)
			throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}
}
