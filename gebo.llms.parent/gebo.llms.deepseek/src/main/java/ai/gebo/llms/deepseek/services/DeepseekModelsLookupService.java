/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.deepseek.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.deepseek.model.GDeepseekChatModelChoice;
import ai.gebo.llms.deepseek.model.GDeepseekChatModelConfig;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.Data;

/**
 * AI generated comments
 * Service responsible for retrieving available models from the Deepseek API.
 * This service is only enabled when the 'deepseekEnabled' property is set to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "deepseekEnabled", havingValue = "true")
@Service
public class DeepseekModelsLookupService {
	/** The URL endpoint for retrieving Deepseek model information */
	public static final String DEEPSEEK_MODELS_URL = "https://api.deepseek.com/models";
	
	/** Service for enriching model choices with metadata */
	@Autowired
	IGModelChoiceMetaInfoEnricherService metaEnricher;
	
	/** Service for accessing Gebo secrets */
	@Autowired
	IGeboSecretsAccessService secretAccess;
	
	/** Service for making REST API calls */
	@Autowired
	RestTemplateWrapperService restTemplateWrapperService;
	// public static List<GDeepseekChatModelChoice> models =
	// GBaseChatModelChoice.of(GDeepseekChatModelChoice.class,
	// org.springframework.ai.deepseek.api.DeepSeekApi.ChatModel.values());

	/**
	 * Data class representing an individual Deepseek model returned by the API
	 */
	@Data
	public static class DeepseekModel {
		private String id = null, object = null, owner = null;
	}

	/**
	 * Data class representing the response from the Deepseek models API endpoint
	 */
	@Data
	public static class DeepseekModelsList {
		private List<DeepseekModel> data = new ArrayList<DeepseekModel>();
		private String object = null;
	}

	/**
	 * Default constructor
	 */
	public DeepseekModelsLookupService() {

	}

	/**
	 * Retrieves the list of available chat models from the Deepseek API.
	 * 
	 * @param config The configuration containing API credentials
	 * @return An OperationStatus containing the list of models or an error message
	 */
	public OperationStatus<List<GDeepseekChatModelChoice>> getChatModels(GDeepseekChatModelConfig config) {
		List<GDeepseekChatModelChoice> models = new ArrayList<>();
		try {
			// Get the secret containing API credentials
			AbstractGeboSecretContent secret = secretAccess.getSecretContentById(config.getApiSecretCode());
			if (secret == null) {
				return OperationStatus.of(new ArrayList<>(), GUserMessage
						.errorMessage("Problem accessing Deepseek Models list", "No valid credentials reachable"));
			}
			if (secret.type() == GeboSecretType.TOKEN) {
				// Use token to authenticate with Deepseek API
				GeboTokenContent tokenContent = (GeboTokenContent) secret;
				Map<String, Object> rawHeaders = new HashMap<>();
				rawHeaders.put("Authorization", "Bearer " + tokenContent.getToken());
				org.springframework.util.MultiValueMap header = org.springframework.util.MultiValueMap
						.fromSingleValue(rawHeaders);

				HttpEntity requestEntity = new HttpEntity<>(header);
				ResponseEntity<DeepseekModelsList> data = restTemplateWrapperService.exchange(DEEPSEEK_MODELS_URL,
						HttpMethod.GET, requestEntity, DeepseekModelsList.class);
				if (data.hasBody()) {
					DeepseekModelsList deepseekModelsList = data.getBody();
					if (deepseekModelsList.getData() != null) {
						// Convert API model responses to GDeepseekChatModelChoice objects
						List<GDeepseekChatModelChoice> deepseekList = deepseekModelsList.getData().stream().map(x -> {
							GDeepseekChatModelChoice choice = new GDeepseekChatModelChoice();
							choice.setCode(x.getId());
							choice.setDescription(x.getId());
							return choice;
						}).toList();
						models.addAll(deepseekList);
					}
				} else {
					return OperationStatus.of(new ArrayList<>(), GUserMessage.errorMessage(
							"Invalid Deepseek models list", "The deepseek provider has not returned any models list"));
				}

			} else {
				return OperationStatus.of(new ArrayList<>(), GUserMessage.errorMessage(
						"Invalid Deepseek credentials format", "Inserted credentials of type:" + secret.type()));
			}
			// Enrich model choices with additional metadata
			metaEnricher.enrichChatModelMetaInfos(DeepseekChatModelConfigurationSupportService.DEEPSEEK_CHAT_MODEL_TYPE, models, (choice) -> {
				ModelMetaInfo meta = new ModelMetaInfo();

				return meta;
			});
			return OperationStatus.of(models);
		} catch (GeboRestIntegrationException integrationException) {
			// Handle REST integration exceptions
			GUserMessage message = restTemplateWrapperService.toMessage(integrationException, "Deepseek provider",
					"Trying to download deepseek llms list");
			return OperationStatus.of(new ArrayList<>(), message);
		} catch (Throwable e) {
			// Handle all other exceptions
			return OperationStatus.of(new ArrayList<>(),
					GUserMessage.errorMessage("Problem accessing Deepseek Models list", e));
		}

	}
}