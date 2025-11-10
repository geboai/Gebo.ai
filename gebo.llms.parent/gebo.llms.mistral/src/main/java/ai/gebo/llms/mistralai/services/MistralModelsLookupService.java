package ai.gebo.llms.mistralai.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.mistralai.model.GMistralChatModelChoice;
import ai.gebo.llms.mistralai.model.GMistralChatModelConfig;
import ai.gebo.llms.mistralai.model.GMistralEmbeddingModelChoice;
import ai.gebo.llms.mistralai.model.GMistralEmbeddingModelConfig;
import ai.gebo.llms.mistralai.model.MistralBaseModelCard;
import ai.gebo.llms.mistralai.model.MistralBaseModelCards;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "mistralAIEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class MistralModelsLookupService {
	final IGeboSecretsAccessService secretService;
	final RestTemplateWrapperService restTemplateWrapperService;
	private final static String MISTRAL_MODELS_URL = "https://api.mistral.ai/v1/models";

	private HttpHeaders createHeader(GBaseModelConfig config) throws LLMConfigException {
		String apiKey = null;
		String user = null;
		if (config.getApiSecretCode() == null || config.getApiSecretCode().trim().length() == 0)
			throw new LLMConfigException("Mistral AI api cannot work without needed api key configuration");
		try {
			AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
			if (secret.type() == GeboSecretType.TOKEN) {
				GeboTokenContent token = (GeboTokenContent) secret;
				apiKey = token.getToken();
				user = token.getUser();
			} else {
				throw new LLMConfigException("Mistral AI api can work only with an api key of type TOKEN");
			}
		} catch (GeboCryptSecretException e) {
			throw new LLMConfigException("Mistral AI api  key configuration gone wrong ", e);
		}
		final String finalApiKey = apiKey;
		return new HttpHeaders() {
			{
				// Prepares the 'Authorization' header
				String authHeader = "Bearer " + finalApiKey;
				// Sets the 'Authorization' header
				set("Authorization", authHeader);
			}
		};
	}

	private OperationStatus<List<MistralBaseModelCard>> invokeModels(GBaseModelConfig config) {
		try {
			HttpHeaders header = createHeader(config);
			HttpEntity<MistralBaseModelCards> entity = new HttpEntity<MistralBaseModelCards>(header);

			MistralBaseModelCards cards = restTemplateWrapperService.exchangeAndReturn(MISTRAL_MODELS_URL,
					HttpMethod.GET, entity, MistralBaseModelCards.class);

			return OperationStatus.of(cards.getData());

		} catch (GeboRestIntegrationException e) {
			GUserMessage message = restTemplateWrapperService.toMessage(e, "Mistral provider", "models list");
			return OperationStatus.of(null, message);
		} catch (Throwable e) {
			return OperationStatus.of(e);
		} finally {
		}
	}

	public OperationStatus<List<GMistralChatModelChoice>> getChatModelChoices(GMistralChatModelConfig config) {
		OperationStatus<List<MistralBaseModelCard>> models = invokeModels(config);
		if (models.isHasErrorMessages()) {
			return OperationStatus.of(null, models.getMessages());
		} else if (models.getResult() != null) {
			return OperationStatus.of(models.getResult().stream().filter(x -> x.getCapabilities() != null
					&& x.getCapabilities().getCompletion_chat() != null && x.getCapabilities().getCompletion_chat())
					.map(filtered -> {
						GMistralChatModelChoice choice = new GMistralChatModelChoice();
						choice.setModelCard(filtered);
						choice.setCode(filtered.getId());
						choice.setDescription(
								filtered.getDescription() != null ? filtered.getId() + " " + filtered.getDescription()
										: filtered.getId());
						if (filtered.getMax_context_length() != null) {
							choice.setContextLength(filtered.getMax_context_length());
						}
						choice.setMetaInfos(new ModelMetaInfo());
						choice.getMetaInfos().setContextLength(filtered.getMax_context_length());
						choice.getMetaInfos().setChatModel(true);
						choice.getMetaInfos().setProviderId("mistral.ai");
						return choice;
					}).toList());
		}
		return OperationStatus.ofError("Mistral Ai service error", "Mistral AI did not return a valid list of models");
	}

	public OperationStatus<List<GMistralEmbeddingModelChoice>> getEmbeddingModelsChoices(
			GMistralEmbeddingModelConfig config) {
		OperationStatus<List<MistralBaseModelCard>> models = invokeModels(config);
		if (models.isHasErrorMessages()) {
			return OperationStatus.of(null, models.getMessages());
		} else if (models.getResult() != null) {
			return OperationStatus.of(models.getResult().stream()
					.filter(x -> x.getCapabilities() != null && (x.getCapabilities().getCompletion_chat() == null
							&& !x.getCapabilities().getCompletion_chat() && x.getId() != null
							&& x.getId().toLowerCase().indexOf("embed") >= 0))
					.map(filtered -> {
						GMistralEmbeddingModelChoice choice = new GMistralEmbeddingModelChoice();
						choice.setModelCard(filtered);
						choice.setCode(filtered.getId());
						choice.setDescription(
								filtered.getDescription() != null ? filtered.getId() + " " + filtered.getDescription()
										: filtered.getId());
						if (filtered.getMax_context_length() != null) {
							choice.setContextLength(filtered.getMax_context_length());
						}
						choice.setMetaInfos(new ModelMetaInfo());
						choice.getMetaInfos().setContextLength(filtered.getMax_context_length());
						choice.getMetaInfos().setChatModel(true);
						choice.getMetaInfos().setProviderId("mistral.ai");
						return choice;
					}).toList());
		}
		return OperationStatus.ofError("Mistral Ai service error", "Mistral AI did not return a valid list of models");
	}

}
