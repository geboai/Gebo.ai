/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelChoice;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.abstraction.layer.services.IGModelsListProvider;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.OperationStatus;
import ai.gebo.openrouter.client.OpenRouterAiClient;
import ai.gebo.openrouter.client.OpenRouterAiClient.OutputModality;
import ai.gebo.openrouter.client.OpenRouterClientException;
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
					fill(entry, m);
					embeddingModels.add(entry);
				}
				enricherService.enrichEmbeddingModelMetaInfos(providerId, embeddingModels,
						(GBaseEmbeddingModelChoice x) -> new ModelMetaInfo());
				models = new ArrayList(embeddingModels);
			} else if (GBaseChatModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseChatModelChoice> chatModels = new ArrayList<GBaseChatModelChoice>();
				for (OpenRouterModel m : client.listModelsByType(OutputModality.CHAT)) {
					GBaseChatModelChoice entry = (GBaseChatModelChoice) ModelsListCommonUtils.newInstance(choiceType);
					fill(entry, m);
					chatModels.add(entry);
				}
				enricherService.enrichChatModelMetaInfos(providerId, chatModels,
						(GBaseChatModelChoice x) -> new ModelMetaInfo());
				models = new ArrayList(chatModels);
			} else if (GBaseRankerModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseRankerModelChoice> rankerModels = new ArrayList<GBaseRankerModelChoice>();
				for (OpenRouterModel m : client.listModelsByType(OutputModality.RERANK)) {
					GBaseRankerModelChoice entry = (GBaseRankerModelChoice) ModelsListCommonUtils.newInstance(choiceType);
					fill(entry, m);
					rankerModels.add(entry);
				}
				models = new ArrayList(rankerModels);
			} else if (GBaseImageModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseImageModelChoice> imageModels = new ArrayList<GBaseImageModelChoice>();
				for (OpenRouterModel m : client.listModelsByType(OutputModality.IMAGE)) {
					GBaseImageModelChoice entry = (GBaseImageModelChoice) ModelsListCommonUtils.newInstance(choiceType);
					fill(entry, m);
					imageModels.add(entry);
				}
				models = new ArrayList(imageModels);
			} else if (GBaseTranscriptModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseTranscriptModelChoice> transcriptModels = new ArrayList<GBaseTranscriptModelChoice>();
				for (OpenRouterModel m : client.listModelsByType(OutputModality.TRANSCRIPTION)) {
					GBaseTranscriptModelChoice entry = (GBaseTranscriptModelChoice) ModelsListCommonUtils
							.newInstance(choiceType);
					fill(entry, m);
					transcriptModels.add(entry);
				}
				models = new ArrayList(transcriptModels);
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
	private void fill(GBaseModelChoice entry, OpenRouterModel model) {
		entry.setCode(model.getId());
		entry.setDescription(model.getName() != null && !model.getName().isBlank() ? model.getName() : model.getId());
		entry.setContextLength(toInteger(model.getContextLength()));
		entry.setNativeModelMetaInfos(model);
	}

	private static Integer toInteger(Long value) {
		return value != null ? Integer.valueOf(value.intValue()) : null;
	}
}
