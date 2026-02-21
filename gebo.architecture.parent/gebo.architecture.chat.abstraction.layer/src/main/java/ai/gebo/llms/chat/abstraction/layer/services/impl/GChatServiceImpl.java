/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GShortModelInfo;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import reactor.core.publisher.Flux;

/**
 * Service implementation for handling chat interactions within the system. It
 * extends {@link AbstractChatService} and implements the {@link IGChatService}
 * interface. AI generated comments
 */
@Service
public class GChatServiceImpl extends AbstractChatService implements IGChatService {

	public GChatServiceImpl(IGChatModelRuntimeConfigurationDao chatModelConfigurations,
			IGToolCallbackSourceRepositoryPattern callbacksRepoPattern, IGPersistentObjectManager persistenceManager,
			IGPromptConfigDao promptsDao, InteractionsContextService interactionsContext,
			IGSecurityService securityService, IGChatResponseParsingFixerServiceRepository fixerServiceRepository,
			IGChatStorageAreaService chatStorageAreaService, LLMGeneratedResourceRepository generatedResourceRepository,
			IGKnowledgebaseVisibilityService knowledgeBaseSecurityService,
			IGChatSessionLifeCycleService chatSessionLifecycleService) {
		super(chatModelConfigurations, callbacksRepoPattern, persistenceManager, promptsDao, interactionsContext,
				securityService, fixerServiceRepository, chatStorageAreaService, generatedResourceRepository,
				knowledgeBaseSecurityService, chatSessionLifecycleService);

	}

	/**
	 * Initiates a chat session based on the given chat request.
	 *
	 * @param request the chat request
	 * @return the chat response
	 * @throws GeboChatException
	 * @throws GeboPersistenceException
	 * @throws IOException
	 * @throws LLMConfigException
	 */
	@Override
	public GeboChatResponse chat(GeboChatRequest request)
			throws GeboChatException, GeboPersistenceException, IOException, LLMConfigException {
		UserInfos user = securityService.getCurrentUser();
		KBContext kbcontext = new KBContext();
		kbcontext.setActualUser(user.getUsername());
		LLMtInteractionContextThreadLocal.Context.set(kbcontext);

		this.chatSessionLifecycleService.ensureChatSessionExists(request);
		IGConfigurableChatModel handler = this.chatSessionLifecycleService.getSessionChatModel(request);
		GeboChatResponse chatResponse = this.chatSessionLifecycleService.createEmptyResponse(request);
		// Retrieve default prompt
		GPromptConfig gprompt = promptsDao.defaultChatPrompt((GBaseChatModelConfig) handler.getConfig(), false);

		// Check if prompt is configured
		if (gprompt == null) {
			throw new GeboChatException("The system has no default prompt configured");
		} else {

			PromptTemplate promptTemplate = null;
			String promptTemplateText = PromptProcessorUtil.processPrompt(gprompt);
			Prompt prompt = null;
			promptTemplate = new PromptTemplate(promptTemplateText);

			prompt = promptTemplate.create();

			LLMChatRequestResources fullRequest = chatSessionLifecycleService.startRequest(request, handler,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);

			IChatRequestContext chatRequestContext = fullRequest.createChatRequestContext();

			chatResponse = callChatClient(handler, prompt, kbcontext, request, chatResponse, chatRequestContext, null);
		}

		// Set response details
		chatResponse.setCalledFunctions(kbcontext.getCalledFunctions());
		if (handler.getConfig() != null && handler.getConfig().getChoosedModel() != null) {
			chatResponse.setUsedChatModelCode(handler.getConfig().getChoosedModel().getCode());
		}
		if (handler.getType() != null) {
			chatResponse.setUsedChatModelProvider(handler.getType().getCode());
		}

		// Update interactions
		this.chatSessionLifecycleService.endRequest(request, chatResponse);
		this.chatSessionLifecycleService.chatRequestCompleted(request, handler);
		// Clean up context
		LLMtInteractionContextThreadLocal.Context.remove();
		return chatResponse;
	}

	/**
	 * Retrieves the list of configured chat models.
	 *
	 * @return a list of short model information
	 */
	@Override
	public List<GShortModelInfo> getModelsConfigurationList() {
		List<GShortModelInfo> models = new ArrayList<GShortModelInfo>();
		List<IGConfigurableChatModel> configurations = chatModelConfigurations.getConfigurations();

		for (IGConfigurableChatModel mconfig : configurations) {
			// Check access rights for each configuration
			if (!securityService.isCanAccess((GBaseChatModelConfig) mconfig.getConfig(), true))
				continue;

			GShortModelInfo object = new GShortModelInfo();
			object.setCode(mconfig.getCode());
			object.setDescription(mconfig.getDescription());
			GBaseModelChoice model = mconfig.getConfig().getChoosedModel();
			object.setModelCode(model != null ? model.getCode() : null);
			object.setModelType(mconfig.getConfig() != null ? mconfig.getConfig().getModelTypeCode() : null);
		}
		return models;
	}

	/**
	 * Retrieves the meta information associated with a specific chat model.
	 *
	 * @param modelCode the code for the model
	 * @return the chat model choice meta information
	 */
	@Override
	public GBaseChatModelChoice getChatModelMetaInfo(String modelCode) {
		IGConfigurableChatModel runtime = chatModelConfigurations.findByCode(modelCode);
		return runtime != null && runtime.getConfig() != null && runtime.getConfig().getChoosedModel() != null
				? (GBaseChatModelChoice) runtime.getConfig().getChoosedModel()
				: null;
	}

	/**
	 * Retrieves the capabilities of a model provider for a given model code.
	 *
	 * @param modelCode the code of the model
	 * @return the capabilities of the model provider
	 */
	@Override
	public ModelProviderCapabilities getProviderCapabilities(String modelCode) {
		IGConfigurableChatModel model = chatModelConfigurations.findByCode(modelCode);
		if (model != null) {
			GBaseChatModelConfig c = (GBaseChatModelConfig) model.getConfig();
			List<String> functions = c.getEnabledFunctions();
			ModelProviderCapabilities cap = new ModelProviderCapabilities(model.getCode(), model.isSupportsTranscript(),
					model.isSupportsSpeech(), model.isSupportsStructuredOutput(), model.isSupportsFunctionsCall(),
					callbacksRepoPattern.getEnabledToolsTree(functions));
			return cap;
		}
		return null;
	}

	/**
	 * Transcribes the input stream using the specified model code.
	 *
	 * @param is        the input stream
	 * @param modelCode the code of the model to use for transcription
	 * @return the transcribed text
	 * @throws LLMConfigException if there is a problem with the model configuration
	 * @throws IOException
	 */
	@Override
	public String transcript(InputStream is, String modelCode) throws LLMConfigException, IOException {
		IGConfigurableChatModel model = chatModelConfigurations.findByCode(modelCode);
		if (model != null) {
			return model.getTranscriptModel().call(is);
		}
		throw new LLMConfigException("Chat model:" + modelCode + " does not exist");
	}

	/**
	 * Converts text to speech using the specified model code.
	 *
	 * @param text      the text to convert
	 * @param modelCode the code of the model to use for conversion
	 * @return an input stream with the speech data
	 * @throws LLMConfigException if there is a problem with the model configuration
	 */
	@Override
	public InputStream speech(String text, String modelCode) throws LLMConfigException {
		IGConfigurableChatModel model = chatModelConfigurations.findByCode(modelCode);
		if (model != null) {
			return model.getSpeechModel().call(text);
		}
		throw new LLMConfigException("Chat model:" + modelCode + " does not exist");
	}

	/**
	 * Streams chat interaction responses to the client.
	 *
	 * @param request the chat request
	 * @return a Flux of chat message envelopes
	 */
	@Override
	public Flux<GeboChatMessageEnvelope> streamChat(GeboChatRequest request) {

		try {
			// Retrieve current user's name and model configuration
			String currentUserName = securityService.getCurrentUser().getUsername();
			String modelCode = request.getChatModelCode();

			// Initialize context for interaction
			KBContext kbcontext = new KBContext();
			kbcontext.setActualUser(currentUserName);
			LLMtInteractionContextThreadLocal.Context.set(kbcontext);
			chatSessionLifecycleService.ensureChatSessionExists(request);
			GeboChatResponse gresponse = chatSessionLifecycleService.createEmptyResponse(request);
			IGConfigurableChatModel handler = chatSessionLifecycleService.getSessionChatModel(request);
			// Retrieve default prompt
			GPromptConfig gprompt = promptsDao.defaultChatPrompt((GBaseChatModelConfig) handler.getConfig(), false);
			if (gprompt == null) {
				throw new GeboChatException("The system has no default prompt configured");
			} else {
				int contextWindowSize = handler.getContextLength();
				// Prepare prompt and context for streaming
				PromptTemplate promptTemplate = null;
				String promptTemplateText = PromptProcessorUtil.processPrompt(gprompt);
				Prompt prompt = null;
				promptTemplate = new PromptTemplate(promptTemplateText);
				prompt = promptTemplate.create();
				LLMChatRequestResources fullRequest = chatSessionLifecycleService.startRequest(request, handler,
						LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);

				IChatRequestContext chatRequestContext = fullRequest.createChatRequestContext();
				return streamChatClient(handler, prompt, kbcontext, request, gresponse, chatRequestContext,
						fullRequest.getTokensSize() > contextWindowSize / 3, contextWindowSize / 3, null);
			}
		} catch (Throwable e) {
			// Handle exceptions and prepare error response as a Flux
			LOGGER.error("Exception in streamChat(...)", e);
			GeboChatMessageEnvelope<GeboChatResponse> responseEnvelope = new GeboChatMessageEnvelope<GeboChatResponse>();
			GeboChatResponse response = new GeboChatResponse();
			response.setUserChatContextCode(request.getUserChatContextCode());
			response.getBackendMessages().add(GUserMessage.errorMessage("Chat system error", e));
			responseEnvelope.setContent(response);
			responseEnvelope.setLastMessage(true);
			return Flux.just(responseEnvelope);
		}
	}

}