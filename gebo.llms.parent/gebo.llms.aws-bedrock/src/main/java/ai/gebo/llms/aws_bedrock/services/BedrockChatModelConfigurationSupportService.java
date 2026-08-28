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

import org.springframework.ai.bedrock.converse.BedrockChatOptions;
import org.springframework.ai.bedrock.converse.BedrockProxyChatModel;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisorFactory;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.aws_bedrock.model.GBedrockChatModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockChatModelConfig;
import ai.gebo.model.OperationStatus;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.model.ModelModality;

/**
 * Chat model configuration support for AWS Bedrock, backed by the Spring AI
 * unified Bedrock Converse API ({@link BedrockProxyChatModel}). This single
 * model type transparently serves every Bedrock chat family (Anthropic Claude,
 * Amazon Nova, Meta Llama, Mistral, Cohere Command, AI21 ...) with tool calling
 * and multimodal support.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class BedrockChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GBedrockChatModelChoice, GBedrockChatModelConfig> {

	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("chat-aws-bedrock");
		type.setDescription("Chat models hosted on AWS Bedrock (Converse API)");
		type.setModelConfigurationClass(GBedrockChatModelConfig.class.getName());
	}

	final BedrockCredentialsResolver credentialsResolver;
	final BedrockFoundationModelsLookupService modelsLookupService;
	final IGToolCallbackSourceRepositoryPattern functionsRepo;
	final ModelRuntimeConfigureHandler configureHandler;
	final IGDocumentContentRendererProvider documentContentRenderProvider;
	final IChatModelUsageAdvisorFactory usageAdvisorFactory;
	final ObservationRegistry observationRegistry;

	class BedrockConfigurableChatModel
			extends GAbstractConfigurableChatModel<GBedrockChatModelConfig, BedrockProxyChatModel> {

		public BedrockConfigurableChatModel(IGDocumentContentRendererProvider rendererFactory,
				IGToolCallbackSourceRepositoryPattern toolCallbacksRepository,
				IChatModelUsageAdvisorFactory usageAdvisorFactory, ObservationRegistry observationRegistry) {
			super(rendererFactory, toolCallbacksRepository, usageAdvisorFactory, observationRegistry);
		}

		@Override
		protected BedrockProxyChatModel configureModel(GBedrockChatModelConfig config, GChatModelType type,
				ToolCallingManager toolsCallsManager) throws LLMConfigException {

			AwsCredentialsProvider credentials = credentialsResolver.resolveCredentials(config.getApiSecretCode());
			Region region = credentialsResolver.resolveRegion(config.getApiSecretCode());

			BedrockChatOptions.Builder builder = BedrockChatOptions.builder();
			if (config.getChoosedModel() != null) {
				builder.model(config.getChoosedModel().getCode());
			}
			if (config.getTemperature() != null) {
				builder.temperature(config.getTemperature());
			}
			if (config.getTopP() != null && config.getTopP() > 0) {
				builder.topP(config.getTopP());
			}
			if (config.getMaxGeneratedTokens() != null && config.getMaxGeneratedTokens() > 0) {
				builder.maxTokens(config.getMaxGeneratedTokens());
			}
			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				List<ToolCallback> functions = functionsRepo.getTools(config.getEnabledFunctions());
				builder.toolCallbacks(functions);
			}
			BedrockChatOptions options = builder.build();

			ToolCallingManager toolCallingManager = toolsCallsManager != null ? toolsCallsManager
					: functionsRepo.createToolCallingManager();

			return BedrockProxyChatModel.builder()
					.credentialsProvider(credentials)
					.region(region)
					.options(options)
					.toolCallingManager(toolCallingManager)
					.observationRegistry(observationRegistry)
					.build();
		}

		@Override
		public boolean isSupportsFunctionsCall() {
			return true;
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {
			return new BedrockConfigurableChatModel(rendererFactory, toolCallbacksRepository, usageAdvisorFactory,
					observationRegistry);
		}
	}

	@Override
	public GChatModelType getType() {
		return type;
	}

	@Override
	public IGConfigurableChatModel<GBedrockChatModelConfig> create(GBedrockChatModelConfig config)
			throws LLMConfigException {
		BedrockConfigurableChatModel model = new BedrockConfigurableChatModel(documentContentRenderProvider,
				functionsRepo, usageAdvisorFactory, observationRegistry);
		model.initialize(config, type);
		return model;
	}

	@Override
	public OperationStatus<List<GBedrockChatModelChoice>> getModelChoices(GBedrockChatModelConfig config) {
		return modelsLookupService.listModels(config.getApiSecretCode(), ModelModality.TEXT,
				GBedrockChatModelChoice::new);
	}

	@Override
	public GBedrockChatModelConfig createBaseConfiguration(String presetModel) {
		GBedrockChatModelConfig clean = new GBedrockChatModelConfig();
		clean.setChoosedModel(new GBedrockChatModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("chat model " + presetModel);
		clean.setDescription("AWS Bedrock chat model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GBedrockChatModelConfig> insertAndConfigure(GBedrockChatModelConfig config)
			throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}
}
