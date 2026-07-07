package ai.gebo.llms.openai_compat.services;

import java.util.List;

import org.springframework.ai.model.NoopApiKey;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;

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
import ai.gebo.llms.openai.http.OpenAiClientCustomizer;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIImageModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIImageModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIImageModelTypeConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenericOpenAIAPIImageModelConfigurationSupportService implements
		IGImageModelConfigurationSupportService<ai.gebo.llms.openai_compat.model.GenericOpenAIAPIImageModelChoice, ai.gebo.llms.openai_compat.model.GenericOpenAIAPIImageModelConfig> {
	final GenericOpenAIImageModelTypeConfig type;
	final IGeboSecretsAccessService secretService;
	final IGOpenAIApiUtil openaiApiUtil;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	final ModelsListProviderProxyService modelsListProxyService;
	final ILLMTypeFiltrerRepositoryPattern llmTypeFiltrerRepoPattern;

	class GenericOpenAIConfigurableImageModel
			extends GAbstractConfigurableImageModel<GenericOpenAIAPIImageModelConfig, OpenAiImageModel> {

		@Override
		protected OpenAiImageModel configureModel(GenericOpenAIAPIImageModelConfig config, GImageModelType type)
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
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			String baseUrl = GenericOpenAIAPIImageModelConfigurationSupportService.this.type.getBaseUrl();
			org.springframework.ai.openai.OpenAiImageOptions.Builder imageOptionsBuilder = OpenAiImageOptions.builder();
			if (baseUrl != null) {
				imageOptionsBuilder.baseUrl(baseUrl);
			}
			if (apiKey != null) {
				imageOptionsBuilder.apiKey(apiKey);
			} else {
				imageOptionsBuilder.apiKey(new NoopApiKey());
			}
			if (config.getChoosedModel() != null) {
				imageOptionsBuilder.model(config.getChoosedModel().getCode());
			}
			OpenAiImageOptions options = imageOptionsBuilder.build();
			OpenAiImageModel model = OpenAiImageModel.builder()
					.options(options)
					.httpClientBuilderCustomizer(OpenAiClientCustomizer.from(clientsProvider))
					.build();
			return model;
		}

	}

	@Override
	public String getId() {

		return this.type.getCode();
	}

	@Override
	public GImageModelType getType() {

		return type;
	}

	@Override
	public OperationStatus<List<GenericOpenAIAPIImageModelChoice>> getModelChoices(
			GenericOpenAIAPIImageModelConfig config) {
		// Image providers do not expose a listing endpoint here; presets in the setup
		// library drive the available choices. Return an empty (non-null) list so
		// callers doing a live lookup do not fail.
		return OperationStatus.of(new java.util.ArrayList<GenericOpenAIAPIImageModelChoice>());
	}

	@Override
	public GenericOpenAIAPIImageModelConfig createBaseConfiguration(String presetModel) {
		GenericOpenAIAPIImageModelConfig clean = new GenericOpenAIAPIImageModelConfig();
		clean.setChoosedModel(new GenericOpenAIAPIImageModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("image generation model " + presetModel);
		clean.setDescription("Generic OpenAI API image generation model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;

	}

	@Override
	public OperationStatus<GenericOpenAIAPIImageModelConfig> insertAndConfigure(GenericOpenAIAPIImageModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);

	}

	@Override
	public IGConfigurableImageModel create(GenericOpenAIAPIImageModelConfig config) throws LLMConfigException {
		GenericOpenAIConfigurableImageModel model = new GenericOpenAIConfigurableImageModel();
		model.initialize(config, type);
		return model;
	}

}
