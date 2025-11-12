package ai.gebo.llms.azure.openai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.azure.openai.model.GAzureOpenAIChatModelConfig;
import ai.gebo.llms.azure.openai.model.GAzureOpenAIEmbeddingModelConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
public class AzureOpenAIConfigFactory {
	@Autowired
	IGeboSecretsAccessService secretService;

	@AllArgsConstructor
	@Getter
	public static class AzureOpenAIBaseConfig {
		private final String apiKey;
		private final String user;
		private final String endpoint;
		private final String model;
	}

	public AzureOpenAIConfigFactory() {

	}

	public AzureOpenAIBaseConfig createClientBulder(String secretKey, String baseUrl, String model)
			throws LLMConfigException {
		if (secretKey != null && secretKey.trim().length() > 0 && baseUrl != null && baseUrl.trim().length() > 0) {
			String apiKey = null;
			String user = null;
			try {
				AbstractGeboSecretContent secret = secretService.getSecretContentById(secretKey);
				if (secret.type() == GeboSecretType.TOKEN) {
					GeboTokenContent token = (GeboTokenContent) secret;
					apiKey = token.getToken();
					user = token.getUser();
				} else {
					throw new LLMConfigException("Azure/OpenAI api can work only with an api key of type TOKEN");
				}

				return new AzureOpenAIBaseConfig(apiKey, user, baseUrl, model);
			} catch (GeboCryptSecretException e) {
				throw new LLMConfigException("Azure/OpenAI api  key configuration gone wrong ", e);
			}
		} else
			throw new LLMConfigException("Azure/OpenAI secretKey=" + secretKey + " and baseUrl=" + baseUrl);

	}

	public AzureOpenAIBaseConfig createClientBulder(GAzureOpenAIChatModelConfig config) throws LLMConfigException {
		if (config.getChoosedModel() == null)
			throw new LLMConfigException("Azure/OpenAI config without model");
		return createClientBulder(config.getApiSecretCode(), config.getBaseUrl(), config.getChoosedModel().getCode());
	}

	public AzureOpenAIBaseConfig createClientBulder(GAzureOpenAIEmbeddingModelConfig config) throws LLMConfigException {
		if (config.getChoosedModel() == null)
			throw new LLMConfigException("Azure/OpenAI config without model");
		return createClientBulder(config.getApiSecretCode(), config.getBaseUrl(), config.getChoosedModel().getCode());
	}

}
