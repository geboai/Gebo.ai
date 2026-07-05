/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openrouter.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.openrouter.client.model.ModelArchitecture;
import ai.gebo.openrouter.client.model.OpenRouterModel;
import ai.gebo.openrouter.client.model.OpenRouterModelsFilter;
import ai.gebo.openrouter.client.model.OpenRouterModelsResponse;

/**
 * Plain, instantiable client for the OpenRouter AI HTTP API, built on
 * {@link RestTemplate}.
 *
 * <p>
 * This is deliberately <b>not</b> a Spring bean/service: create it directly with
 * the API key of the caller — {@code new OpenRouterAiClient(apiKey)} — so
 * different keys (e.g. per tenant or per user) can be used from the same
 * application without any shared singleton state.
 * </p>
 *
 * <pre>{@code
 * OpenRouterAiClient client = new OpenRouterAiClient(apiKey);
 * List<OpenRouterModel> models = client.listModels();
 * }</pre>
 *
 * <p>
 * Instances are immutable and thread-safe as long as the supplied
 * {@link RestTemplate} is (the default one is).
 * </p>
 *
 * @see <a href=
 *      "https://openrouter.ai/docs/api/api-reference/models/list-all-models-and-their-properties">OpenRouter
 *      – List models</a>
 *
 *      Gebo.ai comment agent
 */
public class OpenRouterAiClient {

	/** Default base URL of the OpenRouter API. */
	public static final String DEFAULT_BASE_URL = "https://openrouter.ai/api/v1";

	/** Path of the "list models" endpoint, relative to the base URL. */
	private static final String MODELS_PATH = "/models";

	/**
	 * Coarse model "type", expressed as an output modality. OpenRouter has no
	 * dedicated type parameter; a model's type is inferred from what it produces.
	 *
	 * <p>
	 * {@link #serverValue} is non-{@code null} for the modalities the
	 * {@code output_modalities} query parameter accepts (so they can be filtered
	 * server-side); the others ({@code RERANK}, {@code SPEECH}, {@code TRANSCRIPTION})
	 * are only reported in a model's {@code architecture.output_modalities} and must
	 * be filtered client-side.
	 * </p>
	 */
	public enum OutputModality {

		/** Text output — chat/completion models. */
		CHAT("text", "text"),
		/** Vector embeddings — embedding models. */
		EMBEDDINGS("embeddings", "embeddings"),
		/** Image generation models. */
		IMAGE("image", "image"),
		/** Audio output models. */
		AUDIO("audio", "audio"),
		/** Reranking models (client-side filter only). */
		RERANK(null, "rerank"),
		/** Speech synthesis models (client-side filter only). */
		SPEECH(null, "speech"),
		/** Transcription models (client-side filter only). */
		TRANSCRIPTION(null, "transcription");

		private final String serverValue;
		private final String architectureValue;

		OutputModality(String serverValue, String architectureValue) {
			this.serverValue = serverValue;
			this.architectureValue = architectureValue;
		}

		/** @return the {@code output_modalities} query value, or {@code null} if unsupported server-side */
		public String getServerValue() {
			return serverValue;
		}

		/** @return the value as it appears in {@code architecture.output_modalities} */
		public String getArchitectureValue() {
			return architectureValue;
		}
	}

	private final String apiKey;
	private final String baseUrl;
	private final RestTemplate restTemplate;

	/**
	 * Creates a client for the public OpenRouter API using a dedicated
	 * {@link RestTemplate}.
	 *
	 * @param apiKey the OpenRouter API key (sent as a {@code Bearer} token); may be
	 *               {@code null}/blank for endpoints that do not require auth
	 */
	public OpenRouterAiClient(String apiKey) {
		this(apiKey, DEFAULT_BASE_URL, new RestTemplate());
	}

	/**
	 * Creates a client with a custom base URL and/or {@link RestTemplate} (useful
	 * for testing, proxies or a self-hosted gateway).
	 *
	 * @param apiKey       the OpenRouter API key; may be {@code null}/blank
	 * @param baseUrl      base URL of the API; falls back to {@link #DEFAULT_BASE_URL}
	 *                     when {@code null}/blank
	 * @param restTemplate the template to use; a new one is created when {@code null}
	 */
	public OpenRouterAiClient(String apiKey, String baseUrl, RestTemplate restTemplate) {
		this.apiKey = apiKey;
		this.baseUrl = normalizeBaseUrl(baseUrl);
		this.restTemplate = restTemplate != null ? restTemplate : new RestTemplate();
	}

	/**
	 * Calls {@code GET /models} and returns the list of models and their
	 * properties.
	 *
	 * @return the models (never {@code null}; empty when the API returns no data)
	 * @throws OpenRouterClientException if the request fails or the response cannot
	 *                                   be parsed
	 */
	public List<OpenRouterModel> listModels() {
		OpenRouterModelsResponse response = listModelsResponse();
		if (response == null || response.getData() == null) {
			return Collections.emptyList();
		}
		return response.getData();
	}

	/**
	 * Calls {@code GET /models} and returns the raw response envelope (the
	 * {@code data} wrapper), for callers that need the response as-is.
	 *
	 * @return the parsed response, or {@code null} when the body is empty
	 * @throws OpenRouterClientException if the request fails or the response cannot
	 *                                   be parsed
	 */
	public OpenRouterModelsResponse listModelsResponse() {
		return listModelsResponse(null);
	}

	/**
	 * Calls {@code GET /models} applying the given server-side {@code filter} and
	 * returns the matching models.
	 *
	 * @param filter the filter to apply; {@code null} means no filtering
	 * @return the matching models (never {@code null})
	 * @throws OpenRouterClientException if the request fails or the response cannot
	 *                                   be parsed
	 */
	public List<OpenRouterModel> listModels(OpenRouterModelsFilter filter) {
		OpenRouterModelsResponse response = listModelsResponse(filter);
		if (response == null || response.getData() == null) {
			return Collections.emptyList();
		}
		return response.getData();
	}

	/**
	 * Calls {@code GET /models} applying the given server-side {@code filter} and
	 * returns the raw response envelope.
	 *
	 * @param filter the filter to apply; {@code null} means no filtering
	 * @return the parsed response, or {@code null} when the body is empty
	 * @throws OpenRouterClientException if the request fails or the response cannot
	 *                                   be parsed
	 */
	public OpenRouterModelsResponse listModelsResponse(OpenRouterModelsFilter filter) {
		URI uri = buildModelsUri(filter);
		HttpEntity<Void> request = new HttpEntity<>(authHeaders());
		try {
			ResponseEntity<OpenRouterModelsResponse> response = restTemplate.exchange(uri, HttpMethod.GET, request,
					OpenRouterModelsResponse.class);
			return response.getBody();
		} catch (RestClientException ex) {
			throw new OpenRouterClientException("Failed to list OpenRouter models from " + uri, ex);
		}
	}

	/**
	 * Convenience: lists models of the given "type" (output modality).
	 *
	 * <p>
	 * When the modality is one the server understands (e.g. {@link OutputModality#CHAT}
	 * or {@link OutputModality#EMBEDDINGS}) it is pushed down as the
	 * {@code output_modalities} query parameter. In every case the result is also
	 * filtered client-side against each model's
	 * {@code architecture.output_modalities}, which is the only way to select types
	 * the server does not expose (e.g. {@link OutputModality#RERANK}).
	 * </p>
	 *
	 * @param modality the desired model type; {@code null} returns all models
	 * @return the matching models (never {@code null})
	 */
	public List<OpenRouterModel> listModelsByType(OutputModality modality) {
		if (modality == null) {
			return listModels();
		}
		OpenRouterModelsFilter filter = null;
		if (modality.getServerValue() != null) {
			filter = new OpenRouterModelsFilter();
			filter.setOutputModalities(List.of(modality.getServerValue()));
		}
		List<OpenRouterModel> models = listModels(filter);
		List<OpenRouterModel> filtered = new ArrayList<>();
		for (OpenRouterModel model : models) {
			if (hasOutputModality(model, modality.getArchitectureValue())) {
				filtered.add(model);
			}
		}
		return filtered;
	}

	private static boolean hasOutputModality(OpenRouterModel model, String architectureValue) {
		ModelArchitecture architecture = model != null ? model.getArchitecture() : null;
		List<String> modalities = architecture != null ? architecture.getOutputModalities() : null;
		return modalities != null && modalities.contains(architectureValue);
	}

	private URI buildModelsUri(OpenRouterModelsFilter filter) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + MODELS_PATH);
		if (filter != null) {
			addParam(builder, "category", filter.getCategory());
			addCsvParam(builder, "output_modalities", filter.getOutputModalities());
			addCsvParam(builder, "input_modalities", filter.getInputModalities());
			addParam(builder, "q", filter.getQuery());
			addParam(builder, "sort", filter.getSort());
			addCsvParam(builder, "supported_parameters", filter.getSupportedParameters());
			addParam(builder, "arch", filter.getArch());
			addCsvParam(builder, "providers", filter.getProviders());
			addParam(builder, "context", filter.getMinContextLength());
			addParam(builder, "min_price", filter.getMinPrice());
			addParam(builder, "max_price", filter.getMaxPrice());
		}
		return builder.build().encode().toUri();
	}

	private static void addParam(UriComponentsBuilder builder, String name, Object value) {
		if (value == null) {
			return;
		}
		String text = value.toString();
		if (!text.isBlank()) {
			builder.queryParam(name, text);
		}
	}

	private static void addCsvParam(UriComponentsBuilder builder, String name, List<String> values) {
		if (values == null || values.isEmpty()) {
			return;
		}
		List<String> cleaned = new ArrayList<>();
		for (String value : values) {
			if (value != null && !value.isBlank()) {
				cleaned.add(value.trim().toLowerCase(Locale.ROOT));
			}
		}
		if (!cleaned.isEmpty()) {
			builder.queryParam(name, String.join(",", cleaned));
		}
	}

	/**
	 * Builds the request headers, adding the {@code Authorization: Bearer} header
	 * when an API key is set.
	 *
	 * @return the headers to send
	 */
	private HttpHeaders authHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		if (apiKey != null && !apiKey.isBlank()) {
			headers.setBearerAuth(apiKey);
		}
		return headers;
	}

	/**
	 * @return the configured base URL (without a trailing slash)
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	private static String normalizeBaseUrl(String baseUrl) {
		String effective = (baseUrl != null && !baseUrl.isBlank()) ? baseUrl.trim() : DEFAULT_BASE_URL;
		while (effective.endsWith("/")) {
			effective = effective.substring(0, effective.length() - 1);
		}
		return effective;
	}
}
