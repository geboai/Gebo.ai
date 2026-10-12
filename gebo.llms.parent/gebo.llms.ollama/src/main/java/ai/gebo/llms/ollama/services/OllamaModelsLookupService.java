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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrerRepositoryPattern;
import ai.gebo.llms.ollama.model.GOllamaChatModelChoice;
import ai.gebo.llms.ollama.model.GOllamaChatModelConfig;
import ai.gebo.llms.ollama.model.GOllamaEmbeddingModelChoice;
import ai.gebo.llms.ollama.model.GOllamaEmbeddingModelConfig;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * Service for retrieving available model choices from an Ollama server. This
 * service is only activated when the 'ollamaEnabled' property is set to true.
 */
@Service
@AllArgsConstructor
public class OllamaModelsLookupService {
	static Logger LOGGER = LoggerFactory.getLogger(OllamaModelsLookupService.class);
	final IGeboSecretsAccessService secretService;
	final RestTemplateWrapperService restTemplateWrapper;
	final ILLMTypeFiltrerRepositoryPattern llmTypeFiltrerRepoPattern;

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
			this.model = n;
		}

		// "model" is the current field, "name" the one servers before it read
		public String name = null, model = null;
	}

	/**
	 * Inner class representing a list of Ollama models returned by the API.
	 */
	public static class ModelsList {
		public List<Model> models = new ArrayList<OllamaModelsLookupService.Model>();
	};

	/**
	 * Retrieves a list of available chat models from an Ollama server.
	 * 
	 * @param config Configuration containing the base URL for the Ollama server
	 * @return OperationStatus containing a list of GOllamaChatModelChoice objects
	 *         or error messages
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
							Map<String, Object> show = show(baseUrl, m);
							if (!offers(show, CHAT_CAPABILITY))
								continue;
							GOllamaChatModelChoice choice = new GOllamaChatModelChoice();
							choice.setCode(m.model);
							choice.setDescription(describe(m));
							if (show != null) {
								choice.setModelDetails(encodeDotKeys(show));
							}
							choice.setMetaInfos(metaInfos(m, show));
							choice.setContextLength(choice.getMetaInfos().getContextLength());
							choice.setSupportsFunctionCalls(choice.getMetaInfos().getSupportsFunctionCalls());
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
		return byNameWhenUnknown(result,
				r -> llmTypeFiltrerRepoPattern.filterChatModels(OllamaChatModelConfigurationSupportService.type, r));
	}

	/**
	 * Retrieves a list of available embedding models from an Ollama server.
	 * 
	 * @param config Configuration containing the base URL for the Ollama server
	 * @return OperationStatus containing a list of GOllamaEmbeddingModelChoice
	 *         objects or error messages
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
							Map<String, Object> show = show(baseUrl, m);
							if (!offers(show, EMBEDDING_CAPABILITY))
								continue;
							GOllamaEmbeddingModelChoice choice = new GOllamaEmbeddingModelChoice();
							choice.setCode(m.model);
							choice.setDescription(describe(m));
							if (show != null) {
								choice.setModelDetails(encodeDotKeys(show));
							}
							choice.setMetaInfos(metaInfos(m, show));
							choice.setContextLength(choice.getMetaInfos().getContextLength());
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
		return byNameWhenUnknown(result, r -> llmTypeFiltrerRepoPattern
				.filterEmbeddingModels(OllamaEmbeddingModelConfigurationSupportService.type, r));
	}

	/**
	 * Recursively encodes keys in a map by replacing dots with dashes. This is
	 * necessary because some frameworks have issues with dot notation in keys.
	 * 
	 * @param m The map whose keys need to be encoded
	 * @return A new map with encoded keys
	 */
	static final String CHAT_CAPABILITY = "completion", EMBEDDING_CAPABILITY = "embedding";
	static final String INFORMATIVE_URL = "https://ollama.com/library";

	/**
	 * The /api/show answer of a model, or null when it cannot be read: one model the
	 * server cannot describe must not cost the whole list.
	 */
	private Map<String, Object> show(String baseUrl, Model m) {
		try {
			ResponseEntity<HashMap> infos = restTemplateWrapper.postForEntity(new URI(baseUrl + "api/show"),
					new ShowParam(m.name), HashMap.class);
			return infos.hasBody() ? infos.getBody() : null;
		} catch (Throwable e) {
			LOGGER.warn("Cannot read the details of the ollama model " + m.name + ": " + e.getMessage());
			return null;
		}
	}

	/**
	 * The capabilities /api/show reports (completion, tools, vision, thinking,
	 * embedding, insert), null when the server does not report them.
	 */
	static List<String> capabilities(Map<String, Object> show) {
		if (show != null && show.get("capabilities") instanceof List<?> list) {
			return list.stream().map(String::valueOf).toList();
		}
		return null;
	}

	/**
	 * Whether a model offers a capability; true when its capabilities are unknown,
	 * the model code then decides as before.
	 */
	static boolean offers(Map<String, Object> show, String capability) {
		List<String> capabilities = capabilities(show);
		return capabilities == null || capabilities.contains(capability);
	}

	/**
	 * The context length /api/show reports in model_info as {@code <arch>.context_length}.
	 */
	static Integer contextLength(Map<String, Object> show) {
		if (show != null && show.get("model_info") instanceof Map<?, ?> info) {
			for (Entry<?, ?> entry : info.entrySet()) {
				if (String.valueOf(entry.getKey()).endsWith(".context_length")
						&& entry.getValue() instanceof Number number) {
					return number.intValue();
				}
			}
		}
		return null;
	}

	/** The model name with its size and quantization, from /api/tags details. */
	static String describe(Model m) {
		List<String> traits = new ArrayList<>();
		if (m.details != null) {
			for (String key : List.of("parameter_size", "quantization_level")) {
				Object value = m.details.get(key);
				if (value != null && !String.valueOf(value).isBlank())
					traits.add(String.valueOf(value));
			}
		}
		String name = m.name != null ? m.name : m.model;
		return traits.isEmpty() ? name : name + " (" + String.join(", ", traits) + ")";
	}

	static ai.gebo.llms.models.metainfos.ModelMetaInfo metaInfos(Model m, Map<String, Object> show) {
		ai.gebo.llms.models.metainfos.ModelMetaInfo meta = new ai.gebo.llms.models.metainfos.ModelMetaInfo();
		meta.setProviderId(OllamaChatModelConfigurationSupportService.type.getProviderId());
		meta.setModelId(m.model);
		meta.setDescription(describe(m));
		meta.setInformativeUrl(INFORMATIVE_URL);
		// The model's own window: what a request gets also depends on the num_ctx the
		// server runs it with
		meta.setContextLength(contextLength(show));
		List<String> capabilities = capabilities(show);
		if (capabilities != null) {
			meta.setChatModel(capabilities.contains(CHAT_CAPABILITY));
			meta.setEmbeddingModel(capabilities.contains(EMBEDDING_CAPABILITY));
			meta.setSupportsFunctionCalls(capabilities.contains("tools"));
			meta.setSupportsVision(capabilities.contains("vision"));
			meta.setSupportsReasoning(capabilities.contains("thinking"));
		}
		return meta;
	}

	/**
	 * Applies the name based type filter to the models whose capabilities the server
	 * did not report: the ones it reported are already sorted by them, and an
	 * embedding model without "embed" in its name (bge-m3, all-minilm ...) must not be
	 * dropped from the embedding list.
	 */
	private static <C extends ai.gebo.llms.abstraction.layer.model.GBaseModelChoice> OperationStatus<List<C>> byNameWhenUnknown(
			OperationStatus<List<C>> result,
			java.util.function.Function<OperationStatus<List<C>>, OperationStatus<List<C>>> nameFilter) {
		if (result.getResult() == null)
			return nameFilter.apply(result);
		List<C> known = new ArrayList<>();
		List<C> unknown = new ArrayList<>();
		for (C choice : result.getResult()) {
			boolean reported = choice.getMetaInfos() != null && (choice.getMetaInfos().getChatModel() != null
					|| choice.getMetaInfos().getEmbeddingModel() != null);
			(reported ? known : unknown).add(choice);
		}
		OperationStatus<List<C>> filtered = nameFilter.apply(OperationStatus.of(unknown, result.getMessages()));
		List<C> out = new ArrayList<>(known);
		if (filtered.getResult() != null)
			out.addAll(filtered.getResult());
		return OperationStatus.of(out, filtered.getMessages());
	}

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