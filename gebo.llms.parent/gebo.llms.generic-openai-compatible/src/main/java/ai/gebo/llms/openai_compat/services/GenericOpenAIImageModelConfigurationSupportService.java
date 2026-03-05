package ai.gebo.llms.openai_compat.services;

import java.util.List;

import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GImageModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrerRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai_compat.model.GenericOpenAIImageModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIImageModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIImageModelTypeConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenericOpenAIImageModelConfigurationSupportService implements
		IGImageModelConfigurationSupportService<ai.gebo.llms.openai_compat.model.GenericOpenAIImageModelChoice, ai.gebo.llms.openai_compat.model.GenericOpenAIImageModelConfig> {
	final GenericOpenAIImageModelTypeConfig type;
	final IGeboSecretsAccessService secretService;
	final IGOpenAIApiUtil openaiApiUtil;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	final ModelsListProviderProxyService modelsListProxyService;
	final ILLMTypeFiltrerRepositoryPattern llmTypeFiltrerRepoPattern;

	class GenericOpenAIConfigurableImageModel
			extends GAbstractConfigurableImageModel<GenericOpenAIImageModelConfig, OpenAiImageModel> {

		@Override
		protected OpenAiImageModel configureModel(GenericOpenAIImageModelConfig config, GImageModelType type)
				throws LLMConfigException {
			String apiKey = null;
			String user = null;
			if (config.getApiSecretCode() != null && config.getApiSecretCode().trim().length() > 0) {
				try {
					AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
					if (secret.type() == GeboSecretType.TOKEN) {
						GeboTokenContent token = (GeboTokenContent) secret;
						apiKey = token.getToken();
						user = token.getUser();
					} else {
						throw new LLMConfigException("Generic OpenAI api can work only with an api key of type TOKEN");
					}
				} catch (GeboCryptSecretException e) {
					throw new LLMConfigException("Generic OpenAI api  key configuration gone wrong ", e);
				}
			}
			org.springframework.ai.openai.api.OpenAiApi.Builder apiBuilder = OpenAiApi.builder();
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			RetryTemplate retryTemplate = clientsProvider.getRetryTemplate();
			apiBuilder.restClientBuilder(restClient);
			apiBuilder.webClientBuilder(webClient);
			org.springframework.ai.openai.OpenAiImageOptions.Builder imageOptionsBuilder = OpenAiImageOptions.builder();
			if (config.getChoosedModel() != null) {
				imageOptionsBuilder.model(config.getChoosedModel().getCode());
			}
			org.springframework.ai.openai.api.OpenAiImageApi.Builder builder = OpenAiImageApi.builder();
			builder.apiKey(apiKey);
			builder.restClientBuilder(restClient);
			OpenAiImageApi built = builder.build();
			OpenAiImageOptions options = imageOptionsBuilder.build();
			OpenAiImageModel model = new OpenAiImageModel(built, options, retryTemplate);
			return model;
		}

	}

	@Override
	public GImageModelType getType() {

		return type;
	}

	@Override
	public OperationStatus<List<GenericOpenAIImageModelChoice>> getModelChoices(GenericOpenAIImageModelConfig config) {

		return null;
	}

	@Override
	public GenericOpenAIImageModelConfig createBaseConfiguration(String presetModel) {
		GenericOpenAIImageModelConfig clean = new GenericOpenAIImageModelConfig();
		clean.setChoosedModel(new GenericOpenAIImageModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("image generation model " + presetModel);
		clean.setDescription("Generic OpenAI API image generation model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;

	}

	@Override
	public OperationStatus<GenericOpenAIImageModelConfig> insertAndConfigure(GenericOpenAIImageModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);

	}

	@Override
	public IGConfigurableImageModel create(GenericOpenAIImageModelConfig config) throws LLMConfigException {
		GenericOpenAIConfigurableImageModel model = new GenericOpenAIConfigurableImageModel();
		model.initialize(config, type);
		return model;
	}

}
