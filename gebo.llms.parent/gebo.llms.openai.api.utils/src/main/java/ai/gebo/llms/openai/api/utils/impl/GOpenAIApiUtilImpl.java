/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai.api.utils.impl;

/**
 * AI generated comments
 * This class implements the OpenAI API utility interface to interact with OpenAI services.
 * It handles model listing operations, authentication, and error handling for both chat and embedding models.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai.api.utils.OpenAIApiException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.openai.integration.client.model.OpenAIModel;
import ai.gebo.openai.integration.client.model.OpenAIResultList;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

@Service
public class GOpenAIApiUtilImpl implements IGOpenAIApiUtil {
	/**
	 * API endpoint path for retrieving the list of available models
	 */
	private static final String MODELS_LIST_PATH = "v1/models";
	
	/**
	 * Data access object for models library
	 */
	final IGModelsLibraryDao libraryDao;
	
	/**
	 * Service for accessing secrets like API keys
	 */
	final IGeboSecretsAccessService secretService;
	
	/**
	 * Service for enriching model metadata
	 */
	final IGModelChoiceMetaInfoEnricherService enricherService;
	
	/**
	 * Service for making REST API calls
	 */
	final RestTemplateWrapperService restTemplateWrapper;

	/**
	 * Constructor for dependency injection
	 * 
	 * @param libraryDao DAO for model library access
	 * @param secretService Service for accessing API keys and other secrets
	 * @param enricherService Service for enriching model metadata
	 * @param restTemplateWrapper Service for making REST API calls
	 */
	public GOpenAIApiUtilImpl(IGModelsLibraryDao libraryDao, IGeboSecretsAccessService secretService,
			IGModelChoiceMetaInfoEnricherService enricherService, RestTemplateWrapperService restTemplateWrapper) {
		this.libraryDao = libraryDao;
		this.secretService = secretService;
		this.enricherService = enricherService;
		this.restTemplateWrapper = restTemplateWrapper;
	}

	/**
	 * Inner class representing a list of OpenAI models
	 */
	public static class OpenAIModelsList extends OpenAIResultList<OpenAIModel> {
	};

	/**
	 * Retrieves available models from OpenAI API
	 * 
	 * @param config the OpenAI API configuration
	 * @return List of OpenAI models
	 * @throws OpenAIApiException if there's an issue with the API request
	 * @throws GeboRestIntegrationException if there's an issue with the REST integration
	 */
	@Override
	public List<OpenAIModel> getModels(OpenAIApiConfig config) throws OpenAIApiException, GeboRestIntegrationException {
		String url = getUrl(config, MODELS_LIST_PATH);
		
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		ResponseEntity<OpenAIModelsList> response;
		
			response = restTemplateWrapper.exchange(url, HttpMethod.GET, request,
					OpenAIModelsList.class);
		
		List<OpenAIModel> result = response.hasBody() ? response.getBody().getData() : new ArrayList<OpenAIModel>();

		return result;
	}

	/**
	 * Creates HTTP headers with authorization for API requests
	 * 
	 * @param config the OpenAI API configuration
	 * @return HttpHeaders with authorization
	 * @throws OpenAIApiException if the API key is missing but mandatory
	 */
	private HttpHeaders getHeaders(OpenAIApiConfig config) throws OpenAIApiException {
		HttpHeaders headers = new HttpHeaders();
		if ((config.getApiKey() == null || config.getApiKey().trim().length() == 0) && config.isApiKeyMandatory())
			throw new OpenAIApiException("Missing apiKey");
		if (config.getApiKey() != null) {
			headers.set("Authorization", "Bearer " + config.getApiKey());
		}
		return headers;
	}

	/**
	 * Constructs the full URL for API requests
	 * 
	 * @param config the OpenAI API configuration
	 * @param relative the relative path to append
	 * @return complete URL string
	 * @throws OpenAIApiException if the base path is missing
	 */
	private String getUrl(OpenAIApiConfig config, String relative) throws OpenAIApiException {
		StringBuffer buffer = new StringBuffer();
		if (config.getBasePath() == null || config.getBasePath().trim().length() == 0)
			throw new OpenAIApiException("Missing basePath");
		buffer.append(config.getBasePath());
		if (!config.getBasePath().endsWith("/")) {
			buffer.append("/");
		}
		buffer.append(relative);
		return buffer.toString();
	}

	/**
	 * Generic method to handle operations that require API key access
	 * 
	 * @param <ReturnType> the type of data to return
	 * @param functionByApiKey function to execute with the API key
	 * @param apiSecretCode the secret code for retrieving the API key
	 * @param apiKeyMandatory whether the API key is required
	 * @return operation status with result or error details
	 */
	private <ReturnType> OperationStatus<ReturnType> doWithRemoteSystem(Function<String, ReturnType> functionByApiKey,
			String apiSecretCode, boolean apiKeyMandatory) {
		if (apiKeyMandatory && (apiSecretCode == null || apiSecretCode.trim().length() == 0)) {
			OperationStatus<ReturnType> os = new OperationStatus<ReturnType>();
			os.getMessages().clear();
			os.getMessages().add(GUserMessage.errorMessage("No apiKey secret specified",
					"Access this function requirest the api key secret being set"));
			return os;
		}
		try {
			String decriptedApiKey = null;
			if (apiSecretCode != null) {
				AbstractGeboSecretContent secret = secretService.getSecretContentById(apiSecretCode);
				if (secret.type() == GeboSecretType.TOKEN) {
					GeboTokenContent content = (GeboTokenContent) secret;
					try {
						decriptedApiKey = content.getToken();

					} catch (Throwable th) {
						OperationStatus<ReturnType> os = new OperationStatus<ReturnType>();
						os.getMessages().clear();
						os.getMessages()
								.add(GUserMessage.errorMessage("Exception while trying to access models list", th));
						return os;
					}
				} else {
					OperationStatus<ReturnType> os = new OperationStatus<ReturnType>();
					os.getMessages().clear();
					os.getMessages().add(GUserMessage.errorMessage("Wrong api key setting",
							"The secret key is not a TOKEN, subsystem misconfigured"));
					return os;
				}
			}
			OperationStatus<ReturnType> os = OperationStatus.of(functionByApiKey.apply(decriptedApiKey));
			os.getMessages().clear();
			os.getMessages().add(GUserMessage.infoMessage("AI remote provider OK",
					"Provider is up and running and successfully configured"));
			return os;

		} catch (GeboCryptSecretException e) {
			OperationStatus<ReturnType> os = new OperationStatus<ReturnType>();
			os.getMessages().clear();
			os.getMessages().add(GUserMessage.errorMessage("Problems in api key retrieve", e));
			return os;
		}
	}

	/**
	 * Retrieves available chat models from OpenAI API
	 * 
	 * @param <ChatModelChoiceType> type parameter for chat model choices
	 * @param type class of ChatModelChoiceType
	 * @param config API configuration
	 * @param modelConfig model configuration
	 * @param defaultMetainfoFactory factory for creating default model metadata
	 * @return operation status with list of chat models or error details
	 */
	@Override
	public <ChatModelChoiceType extends GBaseChatModelChoice> OperationStatus<List<ChatModelChoiceType>> getChatModels(
			Class<ChatModelChoiceType> type, OpenAIApiConfig config, GBaseChatModelConfig modelConfig,
			Function<ChatModelChoiceType, ModelMetaInfo> defaultMetainfoFactory) {

		return doWithRemoteSystem(apiKey -> {
			OpenAIApiConfig apiconfig = new OpenAIApiConfig(config);
			apiconfig.setApiKey(apiKey);
			try {
				List<OpenAIModel> models = getModels(apiconfig);
				List<OpenAIModel> filteredModels = models.stream().filter(x -> {
					return x.getMetaInfos() == null
							|| (x.getMetaInfos().getChatModel() != null && x.getMetaInfos().getChatModel());
				}).toList();

				List<ChatModelChoiceType> list = new ArrayList<ChatModelChoiceType>();
				for (OpenAIModel openAIModel : filteredModels) {
					ChatModelChoiceType entry = type.newInstance();
					entry.setCode(openAIModel.getId());
					if (openAIModel.getMetaInfos() != null) {
						entry.setDescription(openAIModel.getMetaInfos().getDescription());
						entry.setInformativeUrl(openAIModel.getMetaInfos().getInformativeUrl());
						entry.setContextLength(openAIModel.getMetaInfos().getContextLength());

					} else {

					}
					if (entry.getDescription() == null) {
						entry.setDescription(openAIModel.getId());
					}
					list.add(entry);
				}
				this.enricherService.enrichChatModelMetaInfos(config.getProviderId(), list, defaultMetainfoFactory);
				return list;
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}

		}, modelConfig.getApiSecretCode(), config.isApiKeyMandatory());
	}

	/**
	 * Retrieves available embedding models from OpenAI API
	 * 
	 * @param <EmbeddingModelChoiceType> type parameter for embedding model choices
	 * @param type class of EmbeddingModelChoiceType
	 * @param config API configuration
	 * @param modelConfig model configuration
	 * @param defaultMetainfoFactory factory for creating default model metadata
	 * @return operation status with list of embedding models or error details
	 */
	@Override
	public <EmbeddingModelChoiceType extends GBaseEmbeddingModelChoice> OperationStatus<List<EmbeddingModelChoiceType>> getEmbeddingModels(
			Class<EmbeddingModelChoiceType> type, OpenAIApiConfig config, GBaseEmbeddingModelConfig modelConfig,
			Function<EmbeddingModelChoiceType, ModelMetaInfo> defaultMetainfoFactory) {
		return doWithRemoteSystem(apiKey -> {
			OpenAIApiConfig apiconfig = new OpenAIApiConfig(config);
			apiconfig.setApiKey(apiKey);
			if (modelConfig.getBaseUrl() != null) {
				apiconfig.setBasePath(modelConfig.getBaseUrl());
			}
			try {
				List<OpenAIModel> models = getModels(apiconfig);
				List<OpenAIModel> filteredModels = models.stream().filter(x -> {
					return x.getMetaInfos() == null
							|| (x.getMetaInfos().getEmbeddingModel() != null && x.getMetaInfos().getEmbeddingModel());
				}).toList();

				List<EmbeddingModelChoiceType> list = new ArrayList<EmbeddingModelChoiceType>();
				for (OpenAIModel openAIModel : filteredModels) {
					EmbeddingModelChoiceType entry = type.newInstance();
					entry.setCode(openAIModel.getId());
					if (openAIModel.getMetaInfos() != null) {
						entry.setDescription(openAIModel.getMetaInfos().getDescription());
						entry.setInformativeUrl(openAIModel.getMetaInfos().getInformativeUrl());
						entry.setContextLength(openAIModel.getMetaInfos().getContextLength());
						entry.setOptimalTokenizationParam(openAIModel.getMetaInfos().getTokenizingThreashold());
					} else {

					}
					if (entry.getDescription() == null) {
						entry.setDescription(openAIModel.getId());
					}
					list.add(entry);
				}
				this.enricherService.enrichEmbeddingModelMetaInfos(config.getProviderId(), list,
						defaultMetainfoFactory);
				return list;
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}

		}, modelConfig.getApiSecretCode(), config.isApiKeyMandatory());
	}

}