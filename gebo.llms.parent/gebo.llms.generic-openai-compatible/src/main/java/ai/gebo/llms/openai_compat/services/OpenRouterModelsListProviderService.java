/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelChice;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelChoice;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.abstraction.layer.services.IGModelsListProvider;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.OperationStatus;
import ai.gebo.openrouter.client.OpenRouterAiClient;
import ai.gebo.openrouter.client.OpenRouterAiClient.OutputModality;
import ai.gebo.openrouter.client.OpenRouterClientException;
import ai.gebo.openrouter.client.model.ModelPricing;
import ai.gebo.openrouter.client.model.OpenRouterModel;
import lombok.AllArgsConstructor;

/**
 * Lists the models exposed by the OpenRouter.ai aggregator, using the plain
 * {@link OpenRouterAiClient} to call {@code GET /models} and mapping the result
 * to the Gebo model-choice types.
 *
 * <p>
 * It follows the same shape as {@code RegoloAIModelsListProviderService}: a
 * single {@code geModels} entry point that branches on the requested
 * {@code choiceType} and returns the matching models. OpenRouter has no explicit
 * "model type" concept — a model's type is derived from its
 * {@code architecture.output_modalities} — so each branch selects the models
 * through {@link OpenRouterAiClient#listModelsByType(OutputModality)}:
 * </p>
 * <ul>
 * <li>chat &rarr; {@link OutputModality#CHAT} (text output)</li>
 * <li>embedding &rarr; {@link OutputModality#EMBEDDINGS}</li>
 * <li>ranker &rarr; {@link OutputModality#RERANK}</li>
 * <li>image &rarr; {@link OutputModality#IMAGE}</li>
 * <li>transcript &rarr; {@link OutputModality#TRANSCRIPTION}</li>
 * </ul>
 *
 * <p>
 * The client is instantiated per call with the caller's {@code clearApiKey}, so
 * no key state is shared between invocations.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
@AllArgsConstructor
public class OpenRouterModelsListProviderService implements IGModelsListProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenRouterModelsListProviderService.class);
	/** OpenRouter publishes every price in USD. */
	static final String OPENROUTER_MODEL_PAGE = "https://openrouter.ai/";
	private static final String OPENROUTER_CURRENCY = "USD";

	private static final String OPENROUTER_AI_MODELS_LIST = "openrouter-ai-models-list";

	final IGModelChoiceMetaInfoEnricherService enricherService;

	@Override
	public String getId() {
		return OPENROUTER_AI_MODELS_LIST;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <ModelChoice extends GBaseModelChoice, ModelConfig extends GBaseModelConfig<ModelChoice>, ModelType extends GModelType> OperationStatus<List<ModelChoice>> geModels(
			String providerId, ModelConfig config, String clearApiKey, Class<ModelChoice> choiceType,
			ModelType modelType) {
		List<ModelChoice> models = new ArrayList<ModelChoice>();
		try {
			OpenRouterAiClient client = new OpenRouterAiClient(clearApiKey);
			if (GBaseEmbeddingModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseEmbeddingModelChoice> embeddingModels = new ArrayList<GBaseEmbeddingModelChoice>();
				for (OpenRouterModel m : client.listModelsByType(OutputModality.EMBEDDINGS)) {
					GBaseEmbeddingModelChoice entry = (GBaseEmbeddingModelChoice) ModelsListCommonUtils
							.newInstance(choiceType);
					fill(entry, m, providerId);
					embeddingModels.add(entry);
				}
				enricherService.enrichEmbeddingModelMetaInfos(providerId, embeddingModels,
						(GBaseEmbeddingModelChoice x) -> new ModelMetaInfo());
				models = new ArrayList(embeddingModels);
			} else if (GBaseChatModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseChatModelChoice> chatModels = new ArrayList<GBaseChatModelChoice>();
				for (OpenRouterModel m : client.listModelsByType(OutputModality.CHAT)) {
					GBaseChatModelChoice entry = (GBaseChatModelChoice) ModelsListCommonUtils.newInstance(choiceType);
					fill(entry, m, providerId);
					chatModels.add(entry);
				}
				enricherService.enrichChatModelMetaInfos(providerId, chatModels,
						(GBaseChatModelChoice x) -> new ModelMetaInfo());
				models = new ArrayList(chatModels);
			} else if (GBaseRankerModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseRankerModelChoice> rankerModels = new ArrayList<GBaseRankerModelChoice>();
				for (OpenRouterModel m : client.listModelsByType(OutputModality.RERANK)) {
					GBaseRankerModelChoice entry = (GBaseRankerModelChoice) ModelsListCommonUtils.newInstance(choiceType);
					fill(entry, m, providerId);
					rankerModels.add(entry);
				}
				models = new ArrayList(rankerModels);
			} else if (GBaseImageModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseImageModelChoice> imageModels = new ArrayList<GBaseImageModelChoice>();
				for (OpenRouterModel m : client.listModelsByType(OutputModality.IMAGE)) {
					GBaseImageModelChoice entry = (GBaseImageModelChoice) ModelsListCommonUtils.newInstance(choiceType);
					fill(entry, m, providerId);
					imageModels.add(entry);
				}
				models = new ArrayList(imageModels);
			} else if (GBaseTranscriptModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseTranscriptModelChoice> transcriptModels = new ArrayList<GBaseTranscriptModelChoice>();
				for (OpenRouterModel m : client.listModelsByType(OutputModality.TRANSCRIPTION)) {
					GBaseTranscriptModelChoice entry = (GBaseTranscriptModelChoice) ModelsListCommonUtils
							.newInstance(choiceType);
					fill(entry, m, providerId);
					transcriptModels.add(entry);
				}
				models = new ArrayList(transcriptModels);
			} else if (GBaseTextToSpeachModelChice.class.isAssignableFrom(choiceType)) {
				List<GBaseTextToSpeachModelChice> ttsModels = new ArrayList<GBaseTextToSpeachModelChice>();
				for (OpenRouterModel m : client.listModelsByType(OutputModality.SPEECH)) {
					GBaseTextToSpeachModelChice entry = (GBaseTextToSpeachModelChice) ModelsListCommonUtils
							.newInstance(choiceType);
					fill(entry, m, providerId);
					ttsModels.add(entry);
				}
				models = new ArrayList(ttsModels);
			} else {
				throw new RuntimeException("This service does not handle=>" + choiceType.getName());
			}
		} catch (OpenRouterClientException e) {
			return OperationStatus.ofError("Problem retrieving OpenRouter.ai models list", e.getLocalizedMessage());
		}
		return OperationStatus.of(models);
	}

	/**
	 * Populates the common model-choice fields from an OpenRouter model entry.
	 *
	 * @param entry the target model choice
	 * @param model the source OpenRouter model
	 */
	static void fill(GBaseModelChoice entry, OpenRouterModel model, String providerId) {
		entry.setCode(model.getId());
		String description = model.getName() != null && !model.getName().isBlank() ? model.getName() : model.getId();
		if (model.getExpirationDate() != null && !model.getExpirationDate().isBlank()) {
			description += " (leaves OpenRouter on " + model.getExpirationDate() + ")";
		}
		entry.setDescription(description);
		// Audio models (transcription, speech) advertise a context length of 0
		Integer contextLength = toInteger(model.getContextLength());
		entry.setContextLength(contextLength != null && contextLength > 0 ? contextLength : null);
		entry.setNativeModelMetaInfos(model);
		// A transcription model's prompt price is per second of audio, not per token
		if (!(entry instanceof GBaseTranscriptModelChoice)) {
			entry.setPricingConditions(getPricingConditions(model));
		}
		entry.setInformativeUrl(OPENROUTER_MODEL_PAGE + model.getId());
		entry.setMetaInfos(metaInfos(model, providerId, entry));
	}

	/**
	 * What the models endpoint tells of a model: output limit of its top provider,
	 * the request parameters it supports (tools, structured outputs, reasoning), image
	 * input, and the date it leaves OpenRouter.
	 */
	static ModelMetaInfo metaInfos(OpenRouterModel model, String providerId, GBaseModelChoice entry) {
		ModelMetaInfo meta = new ModelMetaInfo();
		meta.setProviderId(providerId);
		meta.setModelId(model.getId());
		meta.setDescription(entry.getDescription());
		meta.setInformativeUrl(entry.getInformativeUrl());
		meta.setContextLength(entry.getContextLength());
		if (model.getTopProvider() != null) {
			meta.setMaxOutputToken(toInteger(model.getTopProvider().getMaxCompletionTokens()));
		}
		List<String> parameters = model.getSupportedParameters();
		if (parameters != null) {
			meta.setSupportsFunctionCalls(parameters.contains("tools"));
			meta.setSupportsStructuredOutput(parameters.contains("structured_outputs"));
			meta.setSupportsReasoning(parameters.contains("reasoning"));
		}
		if (model.getArchitecture() != null && model.getArchitecture().getInputModalities() != null) {
			meta.setSupportsVision(model.getArchitecture().getInputModalities().contains("image"));
		}
		if (model.getExpirationDate() != null && !model.getExpirationDate().isBlank()) {
			meta.setDeprecated(true);
			meta.setRetirementDate(model.getExpirationDate());
		}
		return meta;
	}

	/**
	 * The pay per use prices OpenRouter publishes with the model: USD per single
	 * prompt and completion token, and per request, as decimal strings. A negative
	 * value marks a dynamically priced model (e.g. {@code openrouter/auto}) and is
	 * treated as unknown.
	 * <p>
	 * Best effort: the prices only pre-fill what the user can set in the model
	 * configuration, so a failure here is logged and leaves the pricing unset, it
	 * never keeps the model from being listed.
	 */
	private static GModelPricingConditions getPricingConditions(OpenRouterModel model) {
		try {
			ModelPricing prices = model.getPricing();
			if (prices == null) {
				return null;
			}
			GModelPricingConditions pricing = GModelPricingConditions.fromPerTokenPrices(
					parsePrice(model, prices.getPrompt()), parsePrice(model, prices.getCompletion()),
					parsePrice(model, prices.getRequest()), OPENROUTER_CURRENCY);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("OpenRouter model=" + model.getId() + " pricing=" + pricing);
			}
			return pricing;
		} catch (Throwable e) {
			LOGGER.error("Cannot read the pricing of OpenRouter model=" + model.getId()
					+ ", it is listed without pricing", e);
			return null;
		}
	}

	private static Double parsePrice(OpenRouterModel model, String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		try {
			return Double.valueOf(new BigDecimal(value.trim()).doubleValue());
		} catch (NumberFormatException e) {
			LOGGER.error("Unparsable price=" + value + " for OpenRouter model=" + model.getId()
					+ ", treated as unknown");
			return null;
		}
	}

	private static Integer toInteger(Long value) {
		return value != null ? Integer.valueOf(value.intValue()) : null;
	}
}
