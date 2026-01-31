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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.abstraction.layer.services.IGModelsListProvider;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * Service that provides a list of available models from X.ai (formerly known as
 * xAI). This service implements the IGModelsListProvider interface to fetch
 * embedding and language models from X.ai's API.
 */
@Service
@AllArgsConstructor
public class XaiEmbeddingModelsListProviderService implements IGModelsListProvider {
	public final static String XAI_MODELS_LIST = "XaiModelsList";
	private final static String XAI_EMBEDDING_LIST = "https://api.x.ai/v1/embedding-models";
	private final static String XAI_LLMS_LIST = "https://api.x.ai/v1/language-models";
	final IGModelChoiceMetaInfoEnricherService enricherService;
	final RestTemplateWrapperService restTemplateWrapper;

	/**
	 * Returns the unique identifier for this model provider service.
	 * 
	 * @return The identifier string for X.ai models list
	 */
	@Override
	public String getId() {

		return XAI_MODELS_LIST;
	}

	/**
	 * Data class representing the structure of embedding model information returned
	 * by X.ai API.
	 */
	public static class XaiEmbeddingModelData {
		public Long created = null;
		public String id = null;
		public List<String> input_modalities = null;
		public String object;
		public String owned_by = null;
		public Double prompt_image_token_price = null;
		public Double prompt_text_token_price = null;
		public String version = null;
	}

	/**
	 * Data class representing the structure of language model information returned
	 * by X.ai API.
	 */
	public static class XaiLlmsData {
		public Double completion_text_token_price = null;
		public Long created = null;
		public String id = null;
		public List<String> input_modalities = null;
		public String object;
		public List<String> output_modalities = null;
		public String owned_by = null;
		public Double prompt_image_token_price = null;
		public Double prompt_text_token_price = null;

	}

	/**
	 * Container class for a list of X.ai embedding models.
	 */
	public static class XaiEmbeddingModelDataList {
		public List<XaiEmbeddingModelData> models = new ArrayList<XaiEmbeddingModelsListProviderService.XaiEmbeddingModelData>();
	};

	/**
	 * Container class for a list of X.ai language models.
	 */
	public static class XaiLlmsDataList {
		public List<XaiLlmsData> models = new ArrayList<XaiEmbeddingModelsListProviderService.XaiLlmsData>();
	}

	/**
	 * Fetches available models from X.ai based on the requested model type.
	 * Supports both embedding models and chat/language models.
	 * 
	 * @param <ModelChoice> The type of model choice to return
	 * @param <ModelConfig> The type of model configuration
	 * @param providerId    The provider identifier
	 * @param config        The model configuration
	 * @param clearApiKey   The API key for X.ai
	 * @param choiceType    The class of the model choice
	 * @return An OperationStatus containing a list of available models or error
	 *         information
	 */
	@Override
	public <ModelChoice extends GBaseModelChoice, ModelConfig extends GBaseModelConfig<ModelChoice>, ModelType extends GModelType> OperationStatus<List<ModelChoice>> geModels(
			String providerId, ModelConfig config, String clearApiKey, Class<ModelChoice> choiceType, ModelType type) {
		List<ModelChoice> models = new ArrayList<ModelChoice>();
		try {
			if (GBaseEmbeddingModelChoice.class.isAssignableFrom(choiceType)) {
				List<GBaseEmbeddingModelChoice> embeddingmodels = new ArrayList<GBaseEmbeddingModelChoice>();

				HttpEntity<String> request = new HttpEntity<String>(ModelsListCommonUtils.getHeaders(clearApiKey));
				ResponseEntity<XaiEmbeddingModelDataList> response = restTemplateWrapper.exchange(XAI_EMBEDDING_LIST,
						HttpMethod.GET, request, XaiEmbeddingModelDataList.class);
				XaiEmbeddingModelDataList result = response.hasBody() ? response.getBody()
						: new XaiEmbeddingModelDataList();
				if (result.models != null) {
					for (XaiEmbeddingModelData m : result.models) {
						GBaseEmbeddingModelChoice entry = (GBaseEmbeddingModelChoice) ModelsListCommonUtils.newInstance(choiceType);
						entry.setCode(m.id);
						entry.setDescription(m.id);
						embeddingmodels.add(entry);
					}
				}
				enricherService.enrichEmbeddingModelMetaInfos(providerId, embeddingmodels,
						(GBaseEmbeddingModelChoice x) -> {
							return new ModelMetaInfo();
						});
				models = new ArrayList(embeddingmodels);
			} else if (GBaseChatModelChoice.class.isAssignableFrom(choiceType)) {

				HttpEntity<String> request = new HttpEntity<String>(ModelsListCommonUtils.getHeaders(clearApiKey));
				ResponseEntity<XaiLlmsDataList> response = restTemplateWrapper.exchange(XAI_LLMS_LIST, HttpMethod.GET,
						request, XaiLlmsDataList.class);
				XaiLlmsDataList result = response.hasBody() ? response.getBody() : new XaiLlmsDataList();
				if (result.models != null) {
					List<GBaseChatModelChoice> chatmodels = new ArrayList<GBaseChatModelChoice>();
					for (XaiLlmsData m : result.models) {
						GBaseChatModelChoice entry = (GBaseChatModelChoice) ModelsListCommonUtils.newInstance(choiceType);
						entry.setCode(m.id);
						entry.setDescription(m.id);
						chatmodels.add(entry);
					}
					enricherService.enrichChatModelMetaInfos(providerId, chatmodels, (GBaseChatModelChoice x) -> {
						return new ModelMetaInfo();
					});
					models = new ArrayList(chatmodels);
				}
			} else
				throw new RuntimeException("This service does not handle=>" + choiceType.getName());
		} catch (GeboRestIntegrationException e) {
			GUserMessage message = this.restTemplateWrapper.toMessage(e, " XaI provider ", " models list ");
			OperationStatus<List<ModelChoice>> outValue = new OperationStatus<List<ModelChoice>>();
			outValue.getMessages().add(message);
			return outValue;
		} finally {
		}
		return OperationStatus.of(models);
	}

}