package ai.gebo.llms.mistralai.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrerRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.mistralai.model.GMistralChatModelChoice;
import ai.gebo.llms.mistralai.model.GMistralChatModelConfig;
import ai.gebo.llms.mistralai.model.GMistralEmbeddingModelChoice;
import ai.gebo.llms.mistralai.model.GMistralEmbeddingModelConfig;
import ai.gebo.llms.mistralai.model.MistralBaseModelCard;
import ai.gebo.llms.mistralai.model.MistralBaseModelCards;
import ai.gebo.llms.mistralai.model.MistralModelCapabilities;
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

@Service
@AllArgsConstructor
public class MistralModelsLookupService {
	final IGeboSecretsAccessService secretService;
	final RestTemplateWrapperService restTemplateWrapperService;
	final ILLMTypeFiltrerRepositoryPattern llmTypeFiltrerRepoPattern;
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
		OperationStatus<List<GMistralChatModelChoice>> result = null;
		if (models.isHasErrorMessages()) {
			result = OperationStatus.of(null, models.getMessages());
		} else if (models.getResult() != null) {
			result = OperationStatus.of(models.getResult().stream().filter(MistralModelsLookupService::offersChat)
					.map(filtered -> {
						GMistralChatModelChoice choice = new GMistralChatModelChoice();
						choice.setModelCard(filtered);
						fill(choice, filtered, true);
						choice.setSupportsFunctionCalls(choice.getMetaInfos().getSupportsFunctionCalls());
						return choice;
					}).toList());
		}
		return llmTypeFiltrerRepoPattern.filterChatModels(MistralChatModelConfigurationSupportService.type, result);
	}

	public OperationStatus<List<GMistralEmbeddingModelChoice>> getEmbeddingModelsChoices(
			GMistralEmbeddingModelConfig config) {
		OperationStatus<List<GMistralEmbeddingModelChoice>> result = null;
		OperationStatus<List<MistralBaseModelCard>> models = invokeModels(config);
		if (models.isHasErrorMessages()) {
			result = OperationStatus.of(null, models.getMessages());
		} else if (models.getResult() != null) {
			result = OperationStatus.of(models.getResult().stream()
					.filter(card -> !Boolean.TRUE.equals(card.getArchived())).map(filtered -> {
						GMistralEmbeddingModelChoice choice = new GMistralEmbeddingModelChoice();
						choice.setModelCard(filtered);
						fill(choice, filtered, false);
						return choice;
					}).toList());
		}
		return llmTypeFiltrerRepoPattern.filterEmbeddingModels(MistralEmbeddingModelConfigurationSupportService.type,
				result);
	}

	static final String INFORMATIVE_URL = "https://docs.mistral.ai/getting-started/models/";

	/**
	 * Whether a card is a chat model: its capabilities say so when the api reports
	 * them (OCR, moderation, transcription, speech and FIM only models do not chat);
	 * archived fine-tuned models are left out.
	 */
	static boolean offersChat(MistralBaseModelCard card) {
		if (Boolean.TRUE.equals(card.getArchived()))
			return false;
		return card.getCapabilities() == null || card.getCapabilities().getCompletion_chat() == null
				|| card.getCapabilities().getCompletion_chat();
	}

	/**
	 * Fills a choice from its model card: context length, the capabilities the api
	 * reports, and the deprecation date with the replacement model.
	 */
	static void fill(ai.gebo.llms.abstraction.layer.model.GBaseModelChoice choice, MistralBaseModelCard card,
			boolean chat) {
		choice.setCode(card.getId());
		String description = card.getName() != null && !card.getName().isBlank() && !card.getName().equals(card.getId())
				? card.getId() + " " + card.getName()
				: card.getId();
		if (card.getDescription() != null && !card.getDescription().isBlank())
			description = card.getId() + " " + card.getDescription();
		ModelMetaInfo meta = new ModelMetaInfo();
		meta.setProviderId(MistralChatModelConfigurationSupportService.type.getProviderId());
		meta.setModelId(card.getId());
		meta.setChatModel(chat);
		meta.setEmbeddingModel(!chat);
		meta.setContextLength(card.getMax_context_length());
		meta.setInformativeUrl(INFORMATIVE_URL);
		MistralModelCapabilities capabilities = card.getCapabilities();
		if (capabilities != null) {
			meta.setSupportsFunctionCalls(capabilities.getFunction_calling());
			meta.setSupportsVision(capabilities.getVision());
			meta.setSupportsReasoning(capabilities.getReasoning());
		}
		if (card.getDeprecation() != null && !card.getDeprecation().isBlank()) {
			meta.setDeprecated(true);
			meta.setRetirementDate(card.getDeprecation());
			meta.setReplacementModel(card.getDeprecation_replacement_model());
			String date = card.getDeprecation().length() >= 10 ? card.getDeprecation().substring(0, 10)
					: card.getDeprecation();
			description += " (deprecated " + date
					+ (card.getDeprecation_replacement_model() != null
							? ", replaced by " + card.getDeprecation_replacement_model()
							: "")
					+ ")";
		}
		meta.setDescription(description);
		choice.setDescription(description);
		choice.setContextLength(card.getMax_context_length());
		choice.setInformativeUrl(INFORMATIVE_URL);
		choice.setMetaInfos(meta);
	}
}
