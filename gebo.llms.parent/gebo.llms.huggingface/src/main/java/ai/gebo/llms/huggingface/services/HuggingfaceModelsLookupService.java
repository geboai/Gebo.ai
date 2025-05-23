/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.huggingface.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ai.gebo.llms.huggingface.model.GHuggingfaceChatModelChoice;
import ai.gebo.llms.huggingface.model.GHuggingfaceChatModelConfig;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * AI generated comments
 * 
 * Service class for interacting with Huggingface models API.
 * This service is only enabled when the 'huggingfaceEnabled' property is set to 'true'.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "huggingfaceEnabled", havingValue = "true")
@Service
public class HuggingfaceModelsLookupService {
	static Logger LOGGER = LoggerFactory.getLogger(HuggingfaceModelsLookupService.class);
	@Autowired
	IGeboSecretsAccessService secretService;
	@Autowired
	RestTemplateWrapperService restTemplate;

	/**
	 * Inner class representing a Huggingface model.
	 * Contains details, parameters, and identifying information about the model.
	 */
	public static class Model {
		public Map<String, Object> details = new HashMap<String, Object>();
		public Map<String, Object> params = new HashMap<String, Object>();
		public String name = null, model = null;

	}

	/**
	 * Inner class for requesting details about a specific model by name.
	 */
	public static class ShowParam {
		public ShowParam(String n) {
			this.name = n;
		}

		public String name = null;
	}

	/**
	 * Inner class for holding a list of Huggingface models.
	 */
	public static class ModelsList {
		public List<Model> models = new ArrayList<HuggingfaceModelsLookupService.Model>();
	};

	/**
	 * Default constructor for HuggingfaceModelsLookupService.
	 */
	public HuggingfaceModelsLookupService() {

	}

	/**
	 * Retrieves available chat models from the Huggingface API.
	 *
	 * @param config Configuration object containing settings like base URL for the Huggingface API
	 * @return OperationStatus containing a list of available chat models or error messages
	 */
	public OperationStatus<List<GHuggingfaceChatModelChoice>> getChatModels(GHuggingfaceChatModelConfig config) {
		OperationStatus<List<GHuggingfaceChatModelChoice>> result = new OperationStatus<List<GHuggingfaceChatModelChoice>>();
		try {
			try {
				// Ensure base URL ends with a slash
				String baseUrl = config.getBaseUrl();
				if (baseUrl != null && !baseUrl.endsWith("/")) {
					baseUrl = baseUrl + "/";
				}
				ResponseEntity<ModelsList> entity = restTemplate.getForEntity(new URI(baseUrl + "api/tags"),
						ModelsList.class);
				if (entity.hasBody()) {
					ModelsList modelsList = entity.getBody();

					List<GHuggingfaceChatModelChoice> out = new ArrayList<GHuggingfaceChatModelChoice>();
					if (modelsList != null && modelsList.models != null) {
						for (Model m : modelsList.models) {
							// Get detailed information for each model
							ResponseEntity<HashMap> infos = restTemplate.postForEntity(new URI(baseUrl + "api/show"),
									new ShowParam(m.name), HashMap.class);
							HashMap content = infos.getBody();
							GHuggingfaceChatModelChoice choice = new GHuggingfaceChatModelChoice();
							choice.setCode(m.model);
							choice.setDescription(m.name + " " + m.details);
							if (infos.hasBody()) {
								choice.setModelDetails(encodeDotKeys(infos.getBody()));
							}
							out.add(choice);
						}
					}
					result.setResult(out);

				} else {
					result.getMessages().add(GUserMessage.errorMessage("Error getting models list from ollama",
							entity.getStatusCode().toString()));
				}
			} catch (GeboRestIntegrationException exc) {
				GUserMessage message = restTemplate.toMessage(exc, " HuggingFace chat models provider",
						"chat models list");
				result.getMessages().add(message);
			} catch (URISyntaxException e) {
				LOGGER.error("Seems that the uri sintax is incorrect", e);
				result.getMessages().add(
						GUserMessage.errorMessage("Seems that the url:" + config.getBaseUrl() + " is incorrect", e));
			} catch (Throwable e) {
				LOGGER.error("Exception while trying to get ollama models list", e);
				result.getMessages().add(GUserMessage
						.errorMessage("Seems that connecting to the url:" + config.getBaseUrl() + " does not work", e));
			}
		} finally

		{
		}
		return result;
	}

	/**
	 * Encodes map keys by replacing dots with hyphens to avoid issues with dot notation
	 * in certain contexts. Also recursively processes nested maps.
	 *
	 * @param m The map whose keys need to be encoded
	 * @return A new map with encoded keys
	 */
	private Map encodeDotKeys(Map<String, Object> m) {
		HashMap<String, Object> values = new HashMap<String, Object>();
		Set<Entry<String, Object>> entries = m.entrySet();
		for (Entry<String, Object> entry : entries) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				String keyCorrected = key.replace(".", "-");
				if (value instanceof Map) {
					value = encodeDotKeys((Map) value);
				}
				values.put(keyCorrected, value);
			}
		}
		return values;
	}
}