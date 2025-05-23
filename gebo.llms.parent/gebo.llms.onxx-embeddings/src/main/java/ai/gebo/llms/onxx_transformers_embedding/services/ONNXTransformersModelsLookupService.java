/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.onxx_transformers_embedding.services;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelChoice;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelConfig;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * AI generated comments
 * Service responsible for retrieving ONNX Transformers models from an external API.
 * This service looks up available transformer models and their details from a
 * configured endpoint.
 */
@Service
public class ONNXTransformersModelsLookupService {
	static Logger LOGGER = LoggerFactory.getLogger(ONNXTransformersModelsLookupService.class);
	@Autowired
	IGeboSecretsAccessService secretService;
	@Autowired
	RestTemplateWrapperService restTemplateWrapper;

	/**
	 * Internal class representing a model with its details and parameters.
	 */
	public static class Model {
		public Map<String, Object> details = new HashMap<String, Object>();
		public Map<String, Object> params = new HashMap<String, Object>();
		public String name = null, model = null;

	}

	/**
	 * Class used to request specific model details by name.
	 */
	public static class ShowParam {
		public ShowParam(String n) {
			this.name = n;
		}

		public String name = null;
	}

	/**
	 * Container class for the list of models returned by the API.
	 */
	public static class ModelsList {
		public List<Model> models = new ArrayList<ONNXTransformersModelsLookupService.Model>();
	};

	/**
	 * Default constructor for the ONNXTransformersModelsLookupService.
	 */
	public ONNXTransformersModelsLookupService() {

	}

	/**
	 * Retrieves available embedding models from the configured ONNX Transformers endpoint.
	 * 
	 * @param config Configuration containing the base URL for the API endpoint
	 * @return OperationStatus containing list of available model choices or error messages
	 */
	public OperationStatus<List<GONNXTransformersEmbeddingModelChoice>> getEmbeddingModels(
			GONNXTransformersEmbeddingModelConfig config) {
		OperationStatus<List<GONNXTransformersEmbeddingModelChoice>> result = new OperationStatus<List<GONNXTransformersEmbeddingModelChoice>>();
		try {
			try {
				// Ensure the base URL ends with a slash
				String baseUrl = config.getBaseUrl();
				if (baseUrl != null && !baseUrl.endsWith("/")) {
					baseUrl = baseUrl + "/";
				}
				ResponseEntity<ModelsList> entity = restTemplateWrapper.getForEntity(new URI(baseUrl + "api/tags"),
						ModelsList.class);
				if (entity.hasBody()) {
					ModelsList modelsList = entity.getBody();

					List<GONNXTransformersEmbeddingModelChoice> out = new ArrayList<GONNXTransformersEmbeddingModelChoice>();
					if (modelsList != null && modelsList.models != null) {
						for (Model m : modelsList.models) {
							// Get detailed information for each model
							ResponseEntity<HashMap> infos = restTemplateWrapper
									.postForEntity(new URI(baseUrl + "api/show"), new ShowParam(m.name), HashMap.class);
							HashMap content = infos.getBody();
							GONNXTransformersEmbeddingModelChoice choice = new GONNXTransformersEmbeddingModelChoice();
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
				LOGGER.error("Exception in rest call", exc);
				GUserMessage msg = restTemplateWrapper.toMessage(exc, "ONNXTransformers ", "models access");
				result.getMessages().add(msg);
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
	 * Recursively encodes keys in a map by replacing dot characters with hyphens.
	 * This is necessary because some systems can't handle dot notation in keys.
	 * 
	 * @param m The map containing keys to be encoded
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