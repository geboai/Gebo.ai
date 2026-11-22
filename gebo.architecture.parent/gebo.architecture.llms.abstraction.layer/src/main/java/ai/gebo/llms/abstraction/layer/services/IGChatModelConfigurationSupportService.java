/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.services;

import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.GModelCarriedMediaTypes;
import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import ai.gebo.model.OperationStatus;
import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent
 * 
 * Defines the contract for a service that supports the configuration of chat
 * models. This interface extends {@code IGModelConfigurationSupportService}
 * with specified types for chat model configurations and choices.
 *
 * @param <ModelChoice> The type of model choice extending
 *                      {@code GBaseChatModelChoice}.
 * @param <ModelConfig> The type of model configuration extending
 *                      {@code GBaseChatModelConfig}.
 */
public interface IGChatModelConfigurationSupportService<ModelChoice extends GBaseChatModelChoice, ModelConfig extends GBaseChatModelConfig>
		extends IGModelConfigurationSupportService<GChatModelType, ModelChoice, ModelConfig, IGConfigurableChatModel> {

	public IGConfigurableChatModel create(ModelConfig confing) throws LLMConfigException;

	/**
	 * As {@link IGModelConfigurationSupportService#insertAndConfigureModel}, and a
	 * configuration that does not say what the model accepts as input takes it from the
	 * chosen model's metadata ({@link #inputModalitiesOf(GBaseModelChoice)}).
	 */
	@Override
	public default OperationStatus<ModelConfig> insertAndConfigureModel(ModelConfig config, @NotNull String modelCode)
			throws GeboPersistenceException, LLMConfigException {
		OperationStatus<List<ModelChoice>> choices = getModelChoices(config);
		if (!choices.isHasErrorMessages() && choices.getResult() != null && (!choices.getResult().isEmpty())) {
			Optional<ModelChoice> choice = choices.getResult().stream()
					.filter(x -> x.getCode().equalsIgnoreCase(modelCode)).findFirst();
			if (choice.isPresent()) {
				config.setChoosedModel(choice.get());
				if (config.getInputModalities() == null) {
					config.setInputModalities(inputModalitiesOf(choice.get()));
				}
				return insertAndConfigure(config);
			} else {
				return OperationStatus.ofError("The model " + modelCode + " has not been found",
						"The list of models returned by the server does not include " + modelCode);
			}
		}
		return OperationStatus.of(null, choices.getMessages());
	}

	/**
	 * What a model accepts as input as its metadata tells - its provider's models api
	 * or the models library; null when they do not know, which means text only.
	 */
	public static List<GModelInputModality> inputModalitiesOf(GBaseModelChoice choice) {
		return choice != null && choice.getMetaInfos() != null ? choice.getMetaInfos().knownInputModalities() : null;
	}


	/**
	 * What this provider's integration can deliver to a model besides text, see
	 * {@link GModelCarriedMediaTypes}: the MIME types a request of the model may carry.
	 * It may depend on the model, when the provider calls its models through different
	 * apis. Nothing by default, so a provider that states nothing never receives a file.
	 *
	 * @param modelCode the model the requests name, may be null when none is chosen yet
	 */
	public default GModelCarriedMediaTypes carriedMediaTypes(String modelCode) {
		return GModelCarriedMediaTypes.none();
	}
}
