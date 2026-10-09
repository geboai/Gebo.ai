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
@Service
public class DeepseekModelsLookupService {
	/** The default Deepseek api base url, the one the chat models use too */
	public static final String DEEPSEEK_BASE_URL = "https://api.deepseek.com";
	/** The URL endpoint for retrieving Deepseek model information */
	public static final String DEEPSEEK_MODELS_URL = DEEPSEEK_BASE_URL + "/models";
	public static final String INFORMATIVE_URL = "https://api-docs.deepseek.com/quick_start/pricing";
	/** Keys of the model details */
	public static final String EFFORT_LEVELS = "effortLevels", DEFAULT_EFFORT = "defaultEffort";
	
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
		private String id = null, object = null, owned_by = null, name = null;
		private Integer context_window = null, max_output_tokens = null;
		private List<String> input_modalities = null, output_modalities = null;
		private DeepseekEffort effort = null;
	}

	/**
	 * The reasoning effort levels a model accepts, and the one a request gets when it
	 * names none
	 */
	@Data
	public static class DeepseekEffort {
		private List<String> supported_levels = null;
		private String default_level = null;
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
				ResponseEntity<DeepseekModelsList> data = restTemplateWrapperService.exchange(
						modelsUrl(config.getBaseUrl()), HttpMethod.GET, requestEntity, DeepseekModelsList.class);
				if (data.hasBody()) {
					DeepseekModelsList deepseekModelsList = data.getBody();
					if (deepseekModelsList.getData() != null) {
						// Convert API model responses to GDeepseekChatModelChoice objects
						List<GDeepseekChatModelChoice> deepseekList = deepseekModelsList.getData().stream()
								.filter(x -> x.getId() != null).map(DeepseekModelsLookupService::toChoice).toList();
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
			metaEnricher.enrichChatModelMetaInfos(DeepseekChatModelConfigurationSupportService.type.getProviderId(), models, (choice) -> {
				ModelMetaInfo meta = new ModelMetaInfo();
				meta.setInformativeUrl(INFORMATIVE_URL);
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

	/**
	 * The models endpoint under the configured base url, the chat models' one, or the
	 * default when none is configured.
	 */
	static String modelsUrl(String baseUrl) {
		if (baseUrl == null || baseUrl.isBlank())
			return DEEPSEEK_MODELS_URL;
		String base = baseUrl.trim();
		while (base.endsWith("/"))
			base = base.substring(0, base.length() - 1);
		return base + "/models";
	}

	/**
	 * A choice carrying what the models api tells of the model: its name, context
	 * window, output limit, image input and reasoning effort levels.
	 */
	static GDeepseekChatModelChoice toChoice(DeepseekModel model) {
		GDeepseekChatModelChoice choice = new GDeepseekChatModelChoice();
		choice.setCode(model.getId());
		String name = model.getName() != null && !model.getName().isBlank() ? model.getName() : null;
		choice.setDescription(name != null && !name.equalsIgnoreCase(model.getId())
				? name + " (" + model.getId() + ")"
				: model.getId());
		ModelMetaInfo meta = new ModelMetaInfo();
		meta.setProviderId(DeepseekChatModelConfigurationSupportService.type.getProviderId());
		meta.setModelId(model.getId());
		meta.setChatModel(true);
		meta.setEmbeddingModel(false);
		meta.setDescription(choice.getDescription());
		meta.setInformativeUrl(INFORMATIVE_URL);
		// Every current Deepseek model takes tools, the api has no field for it
		meta.setSupportsFunctionCalls(true);
		meta.setContextLength(model.getContext_window());
		meta.setMaxOutputToken(model.getMax_output_tokens());
		if (model.getInput_modalities() != null)
			meta.setSupportsVision(model.getInput_modalities().contains("image"));
		if (model.getEffort() != null) {
			List<String> levels = model.getEffort().getSupported_levels() != null
					? List.copyOf(model.getEffort().getSupported_levels())
					: List.of();
			meta.setSupportsReasoning(!levels.isEmpty());
			choice.getModelDetails().put(EFFORT_LEVELS, new ArrayList<>(levels));
			if (model.getEffort().getDefault_level() != null)
				choice.getModelDetails().put(DEFAULT_EFFORT, model.getEffort().getDefault_level());
		}
		choice.setMetaInfos(meta);
		choice.setContextLength(meta.getContextLength());
		choice.setInformativeUrl(INFORMATIVE_URL);
		choice.setSupportsFunctionCalls(true);
		return choice;
	}
}
