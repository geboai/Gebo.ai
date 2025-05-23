/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.ollama.services;

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

import ai.gebo.llms.ollama.model.GOllamaChatModelChoice;
import ai.gebo.llms.ollama.model.GOllamaChatModelConfig;
import ai.gebo.llms.ollama.model.GOllamaEmbeddingModelChoice;
import ai.gebo.llms.ollama.model.GOllamaEmbeddingModelConfig;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * AI generated comments
 * 
 * Service for retrieving available model choices from an Ollama server.
 * This service is only activated when the 'ollamaEnabled' property is set to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "ollamaEnabled", havingValue = "true")
@Service
public class OllamaModelsLookupService {
	static Logger LOGGER = LoggerFactory.getLogger(OllamaModelsLookupService.class);
	@Autowired
	IGeboSecretsAccessService secretService;
	@Autowired
	RestTemplateWrapperService restTemplateWrapper;

	/**
	 * Inner class representing an Ollama model with its details and parameters.
	 */
	public static class Model {
		public Map<String, Object> details = new HashMap<String, Object>();
		public Map<String, Object> params = new HashMap<String, Object>();
		public String name = null, model = null;

	}

	/**
	 * Inner class representing a parameter for the Ollama API show endpoint.
	 */
	public static class ShowParam {
		public ShowParam(String n) {
			this.name = n;
		}

		public String name = null;
	}

	/**
	 * Inner class representing a list of Ollama models returned by the API.
	 */
	public static class ModelsList {
		public List<Model> models = new ArrayList<OllamaModelsLookupService.Model>();
	};

	/**
	 * Default constructor for OllamaModelsLookupService.
	 */
	public OllamaModelsLookupService() {

	}

	/**
	 * Retrieves a list of available chat models from an Ollama server.
	 * 
	 * @param config Configuration containing the base URL for the Ollama server
	 * @return OperationStatus containing a list of GOllamaChatModelChoice objects or error messages
	 */
	public OperationStatus<List<GOllamaChatModelChoice>> getChatModels(GOllamaChatModelConfig config) {
		OperationStatus<List<GOllamaChatModelChoice>> result = new OperationStatus<List<GOllamaChatModelChoice>>();
		try {
			try {
				// Ensure base URL ends with a slash
				String baseUrl = config.getBaseUrl();
				if (baseUrl != null && !baseUrl.endsWith("/")) {
					baseUrl = baseUrl + "/";
				}
				ResponseEntity<ModelsList> entity = restTemplateWrapper.getForEntity(new URI(baseUrl + "api/tags"),
						ModelsList.class);
				if (entity.hasBody()) {
					ModelsList modelsList = entity.getBody();

					List<GOllamaChatModelChoice> out = new ArrayList<GOllamaChatModelChoice>();
					if (modelsList != null && modelsList.models != null) {
						for (Model m : modelsList.models) {
							// Get detailed information for each model
							ResponseEntity<HashMap> infos = restTemplateWrapper
									.postForEntity(new URI(baseUrl + "api/show"), new ShowParam(m.name), HashMap.class);
							HashMap content = infos.getBody();
							GOllamaChatModelChoice choice = new GOllamaChatModelChoice();
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
			} catch (GeboRestIntegrationException e) {
				GUserMessage message = this.restTemplateWrapper.toMessage(e, " Ollama server ", " models list ");
				OperationStatus<List<GOllamaChatModelChoice>> outValue = new OperationStatus<List<GOllamaChatModelChoice>>();
				outValue.getMessages().add(message);
				return outValue;
			} catch (URISyntaxException e) {
				LOGGER.error("Seems that the uri sintax is incorrect", e);
				result.getMessages().add(
						GUserMessage.errorMessage("Seems that the url:" + config.getBaseUrl() + " is incorrect", e));
			} catch (Throwable e) {
				LOGGER.error("Exception while trying to get ollama models list", e);
				result.getMessages().add(GUserMessage
						.errorMessage("Seems that connecting to the url:" + config.getBaseUrl() + " does not work", e));
			}
		} finally {
		}
		return result;
	}

	/**
	 * Retrieves a list of available embedding models from an Ollama server.
	 * 
	 * @param config Configuration containing the base URL for the Ollama server
	 * @return OperationStatus containing a list of GOllamaEmbeddingModelChoice objects or error messages
	 */
	public OperationStatus<List<GOllamaEmbeddingModelChoice>> getEmbeddingModels(GOllamaEmbeddingModelConfig config) {
		OperationStatus<List<GOllamaEmbeddingModelChoice>> result = new OperationStatus<List<GOllamaEmbeddingModelChoice>>();
		try {
			try {
				// Ensure base URL ends with a slash
				String baseUrl = config.getBaseUrl();
				if (baseUrl != null && !baseUrl.endsWith("/")) {
					baseUrl = baseUrl + "/";
				}
				ResponseEntity<ModelsList> entity = restTemplateWrapper.getForEntity(new URI(baseUrl + "api/tags"),
						ModelsList.class);
				if (entity.hasBody()) {
					ModelsList modelsList = entity.getBody();

					List<GOllamaEmbeddingModelChoice> out = new ArrayList<GOllamaEmbeddingModelChoice>();
					if (modelsList != null && modelsList.models != null) {
						for (Model m : modelsList.models) {
							// Get detailed information for each model
							ResponseEntity<HashMap> infos = restTemplateWrapper
									.postForEntity(new URI(baseUrl + "api/show"), new ShowParam(m.name), HashMap.class);
							HashMap content = infos.getBody();
							GOllamaEmbeddingModelChoice choice = new GOllamaEmbeddingModelChoice();
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
			} catch (GeboRestIntegrationException e) {
				GUserMessage message = this.restTemplateWrapper.toMessage(e, " Ollama server ", " models list ");
				OperationStatus<List<GOllamaEmbeddingModelChoice>> outValue = new OperationStatus<List<GOllamaEmbeddingModelChoice>>();
				outValue.getMessages().add(message);
				return outValue;
			} catch (URISyntaxException e) {
				LOGGER.error("Seems that the uri sintax is incorrect", e);
				result.getMessages().add(
						GUserMessage.errorMessage("Seems that the url:" + config.getBaseUrl() + " is incorrect", e));
			} catch (Throwable e) {
				LOGGER.error("Exception while trying to get ollama models list", e);
				result.getMessages().add(GUserMessage
						.errorMessage("Seems that connecting to the url:" + config.getBaseUrl() + " does not work", e));
			}
		} finally {
		}
		return result;
	}

	/**
	 * Recursively encodes keys in a map by replacing dots with dashes.
	 * This is necessary because some frameworks have issues with dot notation in keys.
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