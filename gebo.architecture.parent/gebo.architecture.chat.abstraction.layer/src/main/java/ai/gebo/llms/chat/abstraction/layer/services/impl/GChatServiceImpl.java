/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GShortModelInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GeboTemplatedChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.services.IGSecurityService;
import reactor.core.publisher.Flux;

/**
 * Service implementation for handling chat interactions within the system.
 * It extends {@link AbstractChatService} and implements the {@link IGChatService} interface.
 * AI generated comments
 */
@Service
public class GChatServiceImpl extends AbstractChatService implements IGChatService {
	
	@Autowired
	protected IGChatModelRuntimeConfigurationDao chatModelConfigurations;
	
	@Autowired
	protected IGPromptConfigDao promptsDao;
	
	@Autowired
	protected InteractionsContextService interactionsContext;
	
	@Autowired
	protected IGSecurityService securityService;
	
	@Autowired
	protected IGChatResponseParsingFixerServiceRepository fixerServiceRepository;

	/**
	 * Default constructor.
	 */
	public GChatServiceImpl() {

	}

	/**
	 * Handles the chat interaction using the specified chat model handler, user context, request, and response type.
	 * This is a generic method supporting different response types.
	 *
	 * @param <T> the type parameter for response type
	 * @param handler the chat model handler
	 * @param userContext the user chat context
	 * @param request the chat request object
	 * @param responseType the class type of the expected response
	 * @return a templated chat response
	 * @throws GeboPersistenceException if persistence fails
	 * @throws GeboChatException if there is an error in chat processing
	 * @throws LLMConfigException if there is a configuration error
	 */
	private <T> GeboTemplatedChatResponse<T> chat(IGConfigurableChatModel handler, GUserChatContext userContext,
			GeboChatRequest request, Class<T> responseType)
			throws GeboPersistenceException, GeboChatException, LLMConfigException {
		KBContext kbcontext = new KBContext();
		kbcontext.setActualUser(userContext.getUsername());
		LLMtInteractionContextThreadLocal.Context.set(kbcontext);
		GeboTemplatedChatResponse<T> gresponse = new GeboTemplatedChatResponse<T>();
		gresponse.setQuery(request.getQuery());

		// Initialize interactions list if null
		List<ChatInteractions> interactions = userContext.getInteractions();
		if (interactions == null) {
			interactions = new ArrayList<GUserChatContext.ChatInteractions>();
		}
		gresponse.setUserChatContextCode(userContext.getCode());

		// Retrieve default prompt
		GPromptConfig gprompt = promptsDao.defaultPrompt((GBaseChatModelConfig) handler.getConfig(), false);

		// Check if prompt is configured
		if (gprompt == null) {
			throw new GeboChatException("The system has no default prompt configured");
		} else {
			BeanOutputConverter<T> outputConverter = new BeanOutputConverter<T>(responseType);
			List<Document> context = new ArrayList<Document>();
			context.add(interactionsContext.interactionsAsDocument(userContext, handler.getContextLength()));
			PromptTemplate promptTemplate = null;
			String promptTemplateText = PromptProcessorUtil.processPrompt(gprompt);
			Prompt prompt = null;
			promptTemplate = new PromptTemplate(promptTemplateText);

			prompt = promptTemplate.create();

			gresponse = callTemplatedChatClient(handler, prompt, kbcontext, request, gresponse, interactions, List.of(),
					context, responseType);
		}

		// Set response details
		gresponse.setCalledFunctions(kbcontext.getCalledFunctions());
		if (handler.getConfig() != null && handler.getConfig().getChoosedModel() != null) {
			gresponse.setUsedChatModelCode(handler.getConfig().getChoosedModel().getCode());
		}
		if (handler.getType() != null) {
			gresponse.setUsedChatModelProvider(handler.getType().getCode());
		}

		// Update interactions
		ChatInteractions interaction = new ChatInteractions();
		interaction.request = request;
		interaction.response = gresponse;
		interactions.add(interaction);
		userContext.setInteractions(interactions);
		persistenceManager.update(userContext);

		// Clean up context
		LLMtInteractionContextThreadLocal.Context.remove();
		return gresponse;
	}

	/**
	 * Initiates a chat session based on the given chat request.
	 *
	 * @param request the chat request
	 * @return the chat response
	 */
	@Override
	public GeboChatResponse chat(GeboChatRequest request) {
		GeboTemplatedChatResponse<String> generatedResponse = templatedChat(request, String.class);
		return new GeboChatResponse(generatedResponse);
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
	 * Initiates a templated chat process.
	 *
	 * @param <T> the type parameter for response type
	 * @param request the chat request
	 * @param responseType the class type of the expected response
	 * @return a templated chat response
	 */
	@Override
	public <T> GeboTemplatedChatResponse<T> templatedChat(GeboChatRequest request, Class<T> responseType) {
		GUserChatContext userContext = null;

		try {
			// Retrieve current user's name and model configuration
			String currentUserName = securityService.getCurrentUser().getUsername();
			String modelCode = request.getChatModelCode();
			IGConfigurableChatModel handler = null;

			if (request.getUserChatContextCode() == null || request.getUserChatContextCode().trim().length() == 0) {
				handler = chatModelConfigurations.findByCode(modelCode);

				// Initialize new user context
				userContext = new GUserChatContext();
				userContext.setDescription("New chat with system=>" + modelCode);
				userContext.setModelReference(GObjectRef.of((GBaseChatModelConfig) handler.getConfig()));
				userContext.setRagChat(false);
				userContext.setChatCreationDateTime(new Date());
				userContext.setUsername(currentUserName);
				userContext.setChatModelCode(modelCode);
				userContext = persistenceManager.insert(userContext);
			} else {
				userContext = persistenceManager.findById(GUserChatContext.class, request.getUserChatContextCode());

				// Security check for user context access
				if (userContext.getUsername() == null || !userContext.getUsername().equals(currentUserName)) {
					throw new SecurityException("Attempting to access the wrong chat userContext");
				}
				modelCode = userContext.getChatModelCode();
				handler = chatModelConfigurations.findByCode(modelCode);
			}

			// Process chat interaction
			GeboTemplatedChatResponse<T> response = chat(handler, userContext, request, responseType);
			response.getBackendMessages().add(GUserMessage.successMessage("OK!", "Chat system running correctly"));
			return response;

		} catch (Throwable e) {
			// Handle exceptions and prepare error response
			GeboTemplatedChatResponse<T> response = new GeboTemplatedChatResponse<T>();
			response.setUserChatContextCode(userContext != null ? userContext.getCode() : null);
			response.getBackendMessages().add(GUserMessage.errorMessage("Chat system error", e));
			return response;
		}
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
	 * Provides a templated chat implementation that is currently not configured.
	 * 
	 * @param <T> the type parameter for response type
	 * @param request the chat request
	 * @param prompt the prompt for the chat
	 * @param customEnvironment custom environment variables
	 * @param contents custom contents
	 * @param responseType the class type of the expected response
	 * @return always returns null as it is not implemented
	 * @throws GeboChatException if there is an error in chat processing
	 * @throws LLMConfigException if there is a configuration error
	 */
	@Override
	public <T> GeboTemplatedChatResponse<T> templatedChat(GeboChatRequest request, String prompt,
			Map<String, Object> customEnvironment, Map<String, Function<KBContext, Object>> contents,
			Class<T> responseType) throws GeboChatException, LLMConfigException {
		// Method is not implemented, return null
		return null;
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
	 * @param is the input stream
	 * @param modelCode the code of the model to use for transcription
	 * @return the transcribed text
	 * @throws LLMConfigException if there is a problem with the model configuration
	 */
	@Override
	public String transcript(InputStream is, String modelCode) throws LLMConfigException {
		IGConfigurableChatModel model = chatModelConfigurations.findByCode(modelCode);
		if (model != null) {
			return model.getTranscriptModel().call(is);
		}
		throw new LLMConfigException("Chat model:" + modelCode + " does not exist");
	}

	/**
	 * Converts text to speech using the specified model code.
	 *
	 * @param text the text to convert
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
		GUserChatContext userContext = null;

		try {
			// Retrieve current user's name and model configuration
			String currentUserName = securityService.getCurrentUser().getUsername();
			String modelCode = request.getChatModelCode();
			IGConfigurableChatModel handler = null;

			if (request.getUserChatContextCode() == null || request.getUserChatContextCode().trim().length() == 0) {
				handler = chatModelConfigurations.findByCode(modelCode);

				// Initialize new user context
				userContext = new GUserChatContext();
				userContext.setDescription("New chat with system=>" + modelCode);
				userContext.setModelReference(GObjectRef.of((GBaseChatModelConfig) handler.getConfig()));
				userContext.setRagChat(false);
				userContext.setChatCreationDateTime(new Date());
				userContext.setUsername(currentUserName);
				userContext.setChatModelCode(modelCode);
				userContext = persistenceManager.insert(userContext);
			} else {
				userContext = persistenceManager.findById(GUserChatContext.class, request.getUserChatContextCode());

				// Security check for user context access
				if (userContext.getUsername() == null || !userContext.getUsername().equals(currentUserName)) {
					throw new SecurityException("Attempting to access the wrong chat userContext");
				}
				modelCode = userContext.getChatModelCode();
				handler = chatModelConfigurations.findByCode(modelCode);
			}

			// Initialize context for interaction
			KBContext kbcontext = new KBContext();
			kbcontext.setActualUser(userContext.getUsername());
			LLMtInteractionContextThreadLocal.Context.set(kbcontext);
			GeboChatResponse gresponse = new GeboChatResponse();
			gresponse.setQuery(request.getQuery());
			gresponse.setUserChatContextCode(userContext.getCode());

			// Retrieve default prompt
			GPromptConfig gprompt = promptsDao.defaultPrompt((GBaseChatModelConfig) handler.getConfig(), false);
			if (gprompt == null) {
				throw new GeboChatException("The system has no default prompt configured");
			} else {

				// Prepare prompt and context for streaming
				List<Document> context = new ArrayList<Document>();
				context.add(interactionsContext.interactionsAsDocument(userContext, handler.getContextLength()));
				PromptTemplate promptTemplate = null;
				String promptTemplateText = PromptProcessorUtil.processPrompt(gprompt);
				Prompt prompt = null;
				promptTemplate = new PromptTemplate(promptTemplateText);
				prompt = promptTemplate.create();
				return streamChatClient(handler, prompt, kbcontext, request, gresponse, userContext, null, null,
						context);
			}
		} catch (Throwable e) {
			// Handle exceptions and prepare error response as a Flux
			GeboChatMessageEnvelope<GeboChatResponse> responseEnvelope = new GeboChatMessageEnvelope<GeboChatResponse>();
			GeboChatResponse response = new GeboChatResponse();
			response.setUserChatContextCode(userContext != null ? userContext.getCode() : null);
			response.getBackendMessages().add(GUserMessage.errorMessage("Chat system error", e));
			responseEnvelope.setContent(response);
			responseEnvelope.setLastMessage(true);
			return Flux.just(responseEnvelope);
		}
	}
}