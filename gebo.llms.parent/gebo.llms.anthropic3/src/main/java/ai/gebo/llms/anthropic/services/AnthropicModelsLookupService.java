/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.anthropic.services;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.anthropic.AnthropicSetup;
import org.springframework.stereotype.Service;

import com.anthropic.client.AnthropicClient;
import com.anthropic.errors.PermissionDeniedException;
import com.anthropic.errors.UnauthorizedException;
import com.anthropic.models.beta.models.BetaModelInfo;
import com.anthropic.models.beta.models.ModelListParams;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.anthropic.http.AnthropicClientCustomizer;
import ai.gebo.llms.anthropic.model.AnthropicThinkingSupport;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelChoice;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelConfig;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;

/**
 * Looks up the Anthropic models through the Models API
 * ({@code GET /v1/models} and {@code GET /v1/models/{model_id}}).
 * <p>
 * The list carries what each model is: its context window, its output limit, its
 * capabilities and its lifecycle. The thinking a model accepts is also kept in a
 * cache, so that configuring a chat model for a request does not call the api.
 */
@Service
public class AnthropicModelsLookupService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnthropicModelsLookupService.class);

	static final String MODEL_TYPE_CODE = "chat-anthropic";

	/**
	 * Offered while no api key is configured yet, when the Models API cannot be asked:
	 * the current lineup, so a first configuration starts from a model that exists.
	 * With a key the list always comes from the api.
	 */
	static final List<String> NO_KEY_MODELS = List.of("claude-opus-5-5", "claude-sonnet-5-5", "claude-haiku-5-5",
			"claude-fable-5-1");

	/** How long the thinking a model accepts is trusted before asking the api again. */
	static final Duration THINKING_SUPPORT_TTL = Duration.ofHours(6);

	/** How long a failed lookup is remembered, not to ask the api on every request. */
	static final Duration FAILED_LOOKUP_TTL = Duration.ofMinutes(10);

	/** Page size of the list: the api caps it at 1000, one page holds the catalogue. */
	static final long LIST_PAGE_SIZE = 1000;

	private record CachedThinkingSupport(AnthropicThinkingSupport support, Instant expiresAt) {
	}

	final IGModelChoiceMetaInfoEnricherService metaEnricher;
	final IGeboSecretsAccessService secretService;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ObservationRegistry observationRegistry;
	private final Map<String, CachedThinkingSupport> thinkingSupportCache = new ConcurrentHashMap<>();

	public AnthropicModelsLookupService(IGModelChoiceMetaInfoEnricherService metaEnricher,
			IGeboSecretsAccessService secretService, IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory,
			ObservationRegistry observationRegistry) {
		this.metaEnricher = metaEnricher;
		this.secretService = secretService;
		this.serviceClientsProviderFactory = serviceClientsProviderFactory;
		this.observationRegistry = observationRegistry;
	}

	/**
	 * Retrieves the Anthropic chat models the configured api key can use.
	 * <p>
	 * Without an api key the current lineup is offered with a warning. With one, the
	 * list comes from the Models API and a refused key or an unreachable api is an
	 * error: the fast setup validates a new key with this very call, so falling back
	 * to a static list would accept any key.
	 *
	 * @param config The Anthropic configuration to use
	 * @return An OperationStatus containing the list of available Anthropic chat models
	 */
	public OperationStatus<List<GAnthropicChatModelChoice>> getChatModels(GAnthropicChatModelConfig config) {
		if (config == null || config.getApiSecretCode() == null || config.getApiSecretCode().isBlank()) {
			return OperationStatus.of(noKeyModels(), GUserMessage.warnMessage("Anthropic models",
					"No api key is configured yet: the current Anthropic models are offered without asking the Models API"));
		}
		String apiKey;
		try {
			apiKey = resolveApiKey(config);
		} catch (LLMConfigException e) {
			return OperationStatus.ofError("Anthropic api key", e.getMessage());
		}
		try {
			List<GAnthropicChatModelChoice> models = listModels(apiKey, config.getBaseUrl());
			enrich(models);
			return OperationStatus.of(models);
		} catch (UnauthorizedException | PermissionDeniedException e) {
			return OperationStatus.ofError("Invalid credentials",
					"Anthropic refused the configured api key: " + e.getMessage());
		} catch (Throwable th) {
			LOGGER.error("Cannot list the Anthropic models", th);
			return OperationStatus.ofError("Problem retrieving the Anthropic models list", th.getMessage());
		}
	}

	/**
	 * The thinking a model accepts, from the cache the model lists fill, or asked to
	 * the Models API when the cache does not know the model.
	 *
	 * @return the support, or null when it cannot be known: the caller keeps a
	 *         behaviour that does not depend on it
	 */
	public AnthropicThinkingSupport getThinkingSupport(String apiKey, String baseUrl, String modelId) {
		if (modelId == null || modelId.isBlank())
			return null;
		CachedThinkingSupport cached = thinkingSupportCache.get(modelId);
		if (cached != null && cached.expiresAt().isAfter(Instant.now()))
			return cached.support();
		AnthropicThinkingSupport support = null;
		try {
			AnthropicClient client = newClient(apiKey, baseUrl);
			try {
				support = AnthropicModelInfoMapper.thinkingSupport(client.beta().models().retrieve(modelId));
			} finally {
				client.close();
			}
		} catch (Throwable th) {
			LOGGER.warn("Cannot read the capabilities of the Anthropic model " + modelId + ": " + th.getMessage());
		}
		thinkingSupportCache.put(modelId, new CachedThinkingSupport(support,
				Instant.now().plus(support != null ? THINKING_SUPPORT_TTL : FAILED_LOOKUP_TTL)));
		return support;
	}

	/**
	 * The clear api key of a configuration, which must be a TOKEN secret.
	 */
	public String resolveApiKey(GBaseModelConfig config) throws LLMConfigException {
		if (config.getApiSecretCode() == null || config.getApiSecretCode().trim().length() == 0)
			throw new LLMConfigException("Anthropic api cannot work without needed api key configuration");
		try {
			AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
			if (secret.type() == GeboSecretType.TOKEN) {
				return ((GeboTokenContent) secret).getToken();
			}
			throw new LLMConfigException("Anthropic api can work only with an api key of type TOKEN");
		} catch (GeboCryptSecretException e) {
			throw new LLMConfigException("Anthropic api  key configuration gone wrong ", e);
		}
	}

	List<GAnthropicChatModelChoice> listModels(String apiKey, String baseUrl) {
		List<GAnthropicChatModelChoice> models = new ArrayList<>();
		AnthropicClient client = newClient(apiKey, baseUrl);
		try {
			ModelListParams params = ModelListParams.builder().limit(LIST_PAGE_SIZE).build();
			Instant expiresAt = Instant.now().plus(THINKING_SUPPORT_TTL);
			for (BetaModelInfo info : client.beta().models().list(params).autoPager()) {
				GAnthropicChatModelChoice choice = AnthropicModelInfoMapper.toChoice(info);
				if (choice.getCode() == null)
					continue;
				models.add(choice);
				AnthropicThinkingSupport support = AnthropicThinkingSupport.readFrom(choice.getModelDetails());
				if (support != null)
					thinkingSupportCache.put(choice.getCode(), new CachedThinkingSupport(support, expiresAt));
			}
		} finally {
			client.close();
		}
		return models;
	}

	/**
	 * A client wired like the chat models' one: same timeout, same retry interceptor.
	 */
	protected AnthropicClient newClient(String apiKey, String baseUrl) {
		IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(MODEL_TYPE_CODE);
		String url = baseUrl != null && !baseUrl.isBlank() ? baseUrl : null;
		return AnthropicSetup.setupSyncClient(url, apiKey,
				Duration.ofMillis(clientsProvider.getClientConfig().getReadTimeoutMs()), 0, null, Map.of(),
				observationRegistry, null, null, List.of(AnthropicClientCustomizer.from(clientsProvider)));
	}

	private List<GAnthropicChatModelChoice> noKeyModels() {
		List<GAnthropicChatModelChoice> models = new ArrayList<>();
		for (String code : NO_KEY_MODELS) {
			GAnthropicChatModelChoice choice = new GAnthropicChatModelChoice();
			choice.setCode(code);
			choice.setDescription(code);
			models.add(choice);
		}
		enrich(models);
		return models;
	}

	private void enrich(List<GAnthropicChatModelChoice> models) {
		metaEnricher.enrichChatModelMetaInfos(AnthropicModelInfoMapper.PROVIDER_ID, models, (choice) -> {
			ModelMetaInfo meta = new ModelMetaInfo();
			meta.setInformativeUrl(AnthropicModelInfoMapper.INFORMATIVE_URL);
			return meta;
		});
	}
}
