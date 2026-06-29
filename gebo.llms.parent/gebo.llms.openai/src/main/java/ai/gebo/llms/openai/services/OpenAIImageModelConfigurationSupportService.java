package ai.gebo.llms.openai.services;

import java.util.List;

import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GImageModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai.http.OpenAiClientCustomizer;
import ai.gebo.llms.openai.model.GOpenAIImageModelChoice;
import ai.gebo.llms.openai.model.GOpenAIImageModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "openAIEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class OpenAIImageModelConfigurationSupportService
		implements IGImageModelConfigurationSupportService<GOpenAIImageModelChoice, GOpenAIImageModelConfig> {
	static GImageModelType type = new GImageModelType();
	static {
		type.setCode("image-generation-OpenAI");
		type.setDescription("image generation service hosted on OpenAI");
		type.setModelConfigurationClass(GOpenAIImageModelConfig.class.getName());
	}
	final IGeboSecretsAccessService secretService;
	final IGOpenAIApiUtil openaiApiUtil;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;

	class OpenAIConfigurableImageModel
			extends GAbstractConfigurableImageModel<GOpenAIImageModelConfig, OpenAiImageModel> {

		@Override
		protected OpenAiImageModel configureModel(GOpenAIImageModelConfig config, GImageModelType type)
				throws LLMConfigException {
			String apiKey = null;
			String user = null;
			if (config.getApiSecretCode() == null || config.getApiSecretCode().trim().length() == 0)
				throw new LLMConfigException("OpenAI api cannot work without needed api key configuration");
			try {
				AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
				if (secret.type() == GeboSecretType.TOKEN) {
					GeboTokenContent token = (GeboTokenContent) secret;
					apiKey = token.getToken();
					user = token.getUser();
				} else {
					throw new LLMConfigException("OpenAI api can work only with an api key of type TOKEN");
				}
			} catch (GeboCryptSecretException e) {
				throw new LLMConfigException("OpenAI api  key configuration gone wrong ", e);
			}
			org.springframework.ai.openai.OpenAiImageOptions.Builder imageOptionsBuilder = OpenAiImageOptions.builder()
					.apiKey(apiKey);
			if (config.getChoosedModel() != null) {
				imageOptionsBuilder.model(config.getChoosedModel().getCode());
			}
			OpenAiImageOptions options = imageOptionsBuilder.build();
			OpenAiImageModel model = OpenAiImageModel.builder()
					.options(options)
					.httpClientBuilderCustomizer(OpenAiClientCustomizer.from(serviceClientsProviderFactory.get(getCode())))
					.build();
			return model;
		}

	}

	@Override
	public GImageModelType getType() {

		return type;
	}

	@Override
	public OperationStatus<List<GOpenAIImageModelChoice>> getModelChoices(GOpenAIImageModelConfig config) {

		return null;
	}

	@Override
	public GOpenAIImageModelConfig createBaseConfiguration(String presetModel) {
		GOpenAIImageModelConfig clean = new GOpenAIImageModelConfig();
		clean.setChoosedModel(new GOpenAIImageModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("image generation model " + presetModel);
		clean.setDescription("OpenAI image generation model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;

	}

	@Override
	public OperationStatus<GOpenAIImageModelConfig> insertAndConfigure(GOpenAIImageModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);

	}

	@Override
	public IGConfigurableImageModel create(GOpenAIImageModelConfig config) throws LLMConfigException {
		OpenAIConfigurableImageModel model = new OpenAIConfigurableImageModel();
		model.initialize(config, type);
		return model;
	}

}
