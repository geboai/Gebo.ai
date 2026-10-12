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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.bedrock.converse.BedrockChatOptions;
import org.springframework.ai.bedrock.converse.BedrockProxyChatModel;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.aws_bedrock.http.BedrockClientCustomizer;
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
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
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
@Service
@AllArgsConstructor
public class BedrockChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GBedrockChatModelChoice, GBedrockChatModelConfig> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BedrockChatModelConfigurationSupportService.class);

	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("chat-aws-bedrock");
		type.setDescription("Chat models hosted on AWS Bedrock (Converse API)");
		type.setModelConfigurationClass(GBedrockChatModelConfig.class.getName());
		type.setProviderId("aws-bedrock");
	}

	final BedrockCredentialsResolver credentialsResolver;
	/**
	 * Supplies the configured connect/read timeouts and retry budget
	 * ({@code ai.gebo.llms.default.clients.config}); the AWS SDK defaults are
	 * tighter and are not otherwise overridable from configuration.
	 */
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final BedrockFoundationModelsLookupService modelsLookupService;
	final IGToolCallbackSourceRepositoryPattern functionsRepo;
	final ModelRuntimeConfigureHandler configureHandler;
	final IGDocumentContentRendererProvider documentContentRenderProvider;
	final IChatModelUsageAdvisorFactory usageAdvisorFactory;
	final ObservationRegistry observationRegistry;
	/** The library the parameter rules of the chat models are read from. */
	final IGModelsLibraryDao modelsLibrary;

	class BedrockConfigurableChatModel
			extends GAbstractConfigurableChatModel<GBedrockChatModelConfig, BedrockProxyChatModel> {

		public BedrockConfigurableChatModel(IGDocumentContentRendererProvider rendererFactory,
				IGToolCallbackSourceRepositoryPattern toolCallbacksRepository,
				IChatModelUsageAdvisorFactory usageAdvisorFactory, ObservationRegistry observationRegistry, IGModelsLibraryDao modelsLibrary) {
			super(rendererFactory, toolCallbacksRepository, usageAdvisorFactory, observationRegistry, modelsLibrary);
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
				// The condition above tests the tool names that were REQUESTED. getTools filters
				// the callbacks actually available by those names, so it can return fewer - or
				// none at all, when the source that exports them failed. Configuring the model
				// from the request rather than from what resolved leaves it declaring tools it
				// will never send.
				if (functions != null && !functions.isEmpty()) {
					builder.toolCallbacks(functions);
				} else {
					LOGGER.warn("Chat model " + config.getCode() + " enables "
							+ config.getEnabledFunctions().size()
							+ " tool(s) but none of them resolved to a callback, so it is configured"
							+ " without tools: " + config.getEnabledFunctions());
				}
			}
			BedrockChatOptions options = builder.build();

			ToolCallingManager toolCallingManager = toolsCallsManager != null ? toolsCallsManager
					: functionsRepo.createToolCallingManager();

			// The builder defaults asyncReadTimeout and socketTimeout to 30s and
			// connectionTimeout to 5s, which cut long streamed generations well before
			// the configured response timeout.
			java.time.Duration requestTimeout = BedrockClientCustomizer
					.requestTimeout(serviceClientsProviderFactory.get(type.getCode()));
			java.time.Duration connectTimeout = BedrockClientCustomizer
					.connectTimeout(serviceClientsProviderFactory.get(type.getCode()));
			BedrockProxyChatModel.Builder modelBuilder = BedrockProxyChatModel.builder()
					.credentialsProvider(credentials)
					.region(region)
					.timeout(requestTimeout)
					.asyncReadTimeout(requestTimeout)
					.socketTimeout(requestTimeout)
					.connectionTimeout(connectTimeout)
					.connectionAcquisitionTimeout(connectTimeout)
					.options(options)
					.toolCallingManager(toolCallingManager)
					.observationRegistry(observationRegistry);
			// The thinking the configuration asks for, on the models whose efforts the
			// parameter rules know (Claude): Spring AI's options cannot carry it, the
			// model's own clients add it to each request (BedrockClaudeThinking)
			String effort = parameterRules != null && parameterRules.knowsEfforts()
					? reasoningEffortFor(config.getThinking())
					: null;
			if (effort != null) {
				software.amazon.awssdk.core.document.Document thinking = BedrockClaudeThinking.fields(effort);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Chat model {} thinking {} sent as {}", config.getCode(), config.getThinking(), thinking);
				}
				modelBuilder
						.bedrockRuntimeClient(BedrockClaudeThinking.syncClient(credentials, region, requestTimeout,
								connectTimeout, thinking))
						.bedrockRuntimeAsyncClient(BedrockClaudeThinking.asyncClient(credentials, region,
								requestTimeout, connectTimeout, thinking));
			} else if (config.getThinking() != null && LOGGER.isDebugEnabled()) {
				LOGGER.debug("Chat model {} thinking {}: nothing sent, the model's rules list no matching effort",
						config.getCode(), config.getThinking());
			}
			return modelBuilder.build();
		}

		@Override
		public boolean isSupportsFunctionsCall() {
			return true;
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {
			return new BedrockConfigurableChatModel(rendererFactory, toolCallbacksRepository, usageAdvisorFactory,
					observationRegistry, modelsLibrary);
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
				functionsRepo, usageAdvisorFactory, observationRegistry, modelsLibrary);
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
