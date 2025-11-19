/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatPromptsConfigs;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.ChatModelLimitedRequest;
import ai.gebo.llms.chat.abstraction.layer.model.ChatProfileRuntimeEnvironment;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfoData;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatUserInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GeboTemplatedChatResponse;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileChatModel;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileManagementService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRequestResourcesUsePolicy;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import reactor.core.publisher.Flux;

/**
 * AI generated comments Service implementation for chat interactions using
 * RagChat model.
 */
@Service
public class GRagChatServiceImpl extends AbstractChatService implements IGRagChatService {
	final protected ChatProfilesRepository chatProfilesRepository;
	final protected IGRuntimeChatProfileChatModelDao chatProfileModelsDao;
	final protected IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;
	final protected IGChatProfileManagementService chatProfileManagementService;
	final protected IGChatRequestResourcesUsePolicy requestLimitationPolicy;

	public GRagChatServiceImpl(IGChatModelRuntimeConfigurationDao chatModelConfigurations,
			IGToolCallbackSourceRepositoryPattern callbacksRepoPattern, IGPersistentObjectManager persistenceManager,
			GUserChatContextRepository userContextRepository, GeboChatPromptsConfigs promptConfigs,
			IGPromptConfigDao promptsDao, InteractionsContextService interactionsContext,
			IGSecurityService securityService, IGChatResponseParsingFixerServiceRepository fixerServiceRepository,
			ChatProfilesRepository chatProfilesRepository, IGRuntimeChatProfileChatModelDao chatProfileModelsDao,
			IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService,
			IGChatProfileManagementService chatProfileManagementService,
			IGChatRequestResourcesUsePolicy requestLimitationPolicy) {
		super(chatModelConfigurations, callbacksRepoPattern, persistenceManager, userContextRepository, promptConfigs,
				promptsDao, interactionsContext, securityService, fixerServiceRepository);
		this.chatProfilesRepository = chatProfilesRepository;
		this.chatProfileModelsDao = chatProfileModelsDao;
		this.knowledgeBaseVisibilityService = knowledgeBaseVisibilityService;
		this.chatProfileManagementService = chatProfileManagementService;
		this.requestLimitationPolicy = requestLimitationPolicy;

	}

	/**
	 * Retrieves a list of chat profile configurations accessible by the current
	 * user.
	 *
	 * @return List of GChatProfileConfiguration
	 */
	@Override
	public List<GChatProfileConfiguration> getChatProfiles() {
		List<GChatProfileConfiguration> configurations = chatProfilesRepository.findAll();
		return securityService.filterAccessible(configurations, true);
	}

	/**
	 * Processes a chat request and returns the chat response.
	 *
	 * @param request GeboChatRequest containing the chat details.
	 * @return GeboChatResponse with the generated response.
	 * @throws GeboChatException  if an error occurs during chat processing.
	 * @throws LLMConfigException if an error occurs due to configuration issues.
	 */
	@Override
	public GeboChatResponse chat(GeboChatRequest request) throws GeboChatException, LLMConfigException {
		GeboTemplatedChatResponse<String> generatedResponse = templatedChat(request, String.class);
		return new GeboChatResponse(generatedResponse);
	}

	/**
	 * Handles the chat process with specified response type, profile, and context.
	 *
	 * @param request        GeboChatRequest object
	 * @param chatProfile    GChatProfileConfiguration object
	 * @param userContext    GUserChatContext object
	 * @param rt             Class type of the response
	 * @param <ResponseType> Generic response type
	 * @return GeboTemplatedChatResponse of specified type
	 * @throws GeboPersistenceException if an error occurs during database
	 *                                  operations.
	 * @throws LLMConfigException       if an error occurs due to configuration
	 *                                  issues.
	 */
	private <ResponseType> GeboTemplatedChatResponse<ResponseType> chat(GeboChatRequest request,
			GChatProfileConfiguration chatProfile, GUserChatContext userContext, Class<ResponseType> rt)
			throws GeboPersistenceException, LLMConfigException {
		// Chat environment setup
		ChatProfileRuntimeEnvironment chatEnvironment = chatProfileManagementService.createChatEnvironment(chatProfile);

		IGConfigurableEmbeddingModel embeddingHandler = chatEnvironment.getEmbeddingModel();
		IGConfigurableChatModel chatHandler = chatEnvironment.getChatModel();
		int contextLength = chatHandler.getContextLength();
		PromptTemplate promptTemplate = chatEnvironment.getPrompt();
		KBContext context = new KBContext();
		context.setActualUser(userContext.getUsername());
		if (embeddingHandler != null) {
			context.setUsedEmbeddingSystem(embeddingHandler.getCode());
		}
		boolean allVisibles = chatProfile.getUserChoosesKnowledgeBases() != null
				&& chatProfile.getUserChoosesKnowledgeBases();

		List<GKnowledgeBase> visibleKnowledgeBases = null;
		if (allVisibles) {
			visibleKnowledgeBases = knowledgeBaseVisibilityService.allVisibleKnowledgebases();
		} else {
			visibleKnowledgeBases = knowledgeBaseVisibilityService
					.visiblesAndChildKnowledgebases(chatProfile.getKnowledgeBaseCodes());
		}
		// Extracts the codes for visible knowledge bases.
		List<String> visibleKnowledgeBaseCodes = visibleKnowledgeBases.stream().map(x -> x.getCode()).toList();
		context.setKnowledgeBasesCodes(visibleKnowledgeBaseCodes);
		LLMtInteractionContextThreadLocal.Context.set(context);

		GeboTemplatedChatResponse<ResponseType> response = new GeboTemplatedChatResponse<ResponseType>();
		response.setQuery(request.getQuery());
		response.setUserChatContextCode(userContext.getCode());
		if (embeddingHandler == null) {
			response.getBackendMessages()
					.add(GUserMessage.errorMessage(
							"No default or specific per knowledgebase embedding model configured",
							"Review the LLMs embeddings configuration"));
		}
		if (chatHandler == null) {
			response.getBackendMessages()
					.add(GUserMessage.errorMessage("No default or specific per knowledgebase chat model configured",
							"Review the LLMs embeddings configuration"));
		}
		if (embeddingHandler != null && chatHandler != null) {
			try {
				// Generates a limited resources request based on policy
				UserInfos user = securityService.getCurrentUser();
				ChatModelLimitedRequest limitedResourcesRequest = requestLimitationPolicy.manageRequest(chatProfile,
						userContext, user, request, embeddingHandler, chatHandler, visibleKnowledgeBaseCodes);
				response.setContextWindowStats(limitedResourcesRequest.getStats());
				// Retrieves historical interactions and document results
				List<ChatInteractions> history = limitedResourcesRequest.getHistory().getValue();
				RagDocumentsCachedDaoResult extractedDocuments = limitedResourcesRequest.getDocuments().getValue();
				List<Document> documentsList = extractedDocuments.aiDocumentsList();
				List<GResponseDocumentRef> docrefs = GResponseDocumentRef.from(extractedDocuments);
				response.setDocumentsRef(docrefs);

				// Prepares and calls the templated chat client with the prompt
				Prompt prompt = promptTemplate.create();
				response = callTemplatedChatClient(chatHandler, prompt, context, request, response, history, docrefs,
						documentsList, rt);

				// Updates response with function calls and stores interaction records
				response.setCalledFunctions(context.getCalledFunctions());
				List<ChatInteractions> interactions = userContext.getInteractions();
				if (interactions == null) {
					interactions = new ArrayList<ChatInteractions>();
				}
				ChatInteractions interaction = new ChatInteractions();
				interaction.setRequest(request);
				interaction.setResponse(response);
				interactions.add(interaction);
				userContext.setInteractions(interactions);
				persistenceManager.update(userContext);
				if (chatHandler.getConfig() != null && chatHandler.getConfig().getChoosedModel() != null) {
					response.setUsedChatModelCode(chatHandler.getConfig().getChoosedModel().getCode());
				}
				if (chatHandler.getType() != null) {
					response.setUsedChatModelProvider(chatHandler.getType().getCode());
				}
				// Clears the context from the thread-local storage
				LLMtInteractionContextThreadLocal.Context.remove();
			} catch (Throwable th) {
				response.getBackendMessages().add(GUserMessage.errorMessage("Error on service provider", th));
				LOGGER.error("Error chat handling", th);
			}
		}
		// Removes thread-local context at the end of processing
		LLMtInteractionContextThreadLocal.Context.remove();
		return response;
	}

	/**
	 * Process the request and chat interactions.
	 *
	 * @param request      GeboChatRequest request
	 * @param responseType Response type
	 * @param <T>          Generic type
	 * @return GeboTemplatedChatResponse
	 * @throws GeboChatException
	 * @throws LLMConfigException
	 */
	@Override
	public <T> GeboTemplatedChatResponse<T> templatedChat(GeboChatRequest request, Class<T> responseType)
			throws GeboChatException, LLMConfigException {
		// Ensure the request has a valid chat profile code.
		if (request.getChatProfileCode() == null || request.getChatProfileCode().trim().length() == 0) {
			throw new GeboChatException("Cannot satisfy a request with null chatProfileCode");
		}
		GChatProfileConfiguration chatProfile = null;
		String code = request.getChatProfileCode();
		GUserChatContext userContext = null;

		// Retrieve the chat profile from the repository.
		Optional<GChatProfileConfiguration> profileEntry = chatProfilesRepository.findById(code);
		if (profileEntry.isPresent()) {
			chatProfile = profileEntry.get();
		} else {
			throw new GeboChatException("Unknown chat profile:" + code);
		}
		try {
			// Get the current user's username and manage the chat user context.
			String currentUserName = securityService.getCurrentUser().getUsername();
			if (request.getUserChatContextCode() == null || request.getUserChatContextCode().trim().length() == 0) {
				userContext = new GUserChatContext();
				userContext.setDescription(chatProfile.getDescription());
				userContext.setChatProfileCode(request.getChatProfileCode());
				userContext.setRagChat(true);
				userContext.setChatCreationDateTime(new Date());
				userContext.setUsername(currentUserName);
				userContext = persistenceManager.insert(userContext);
			} else {
				userContext = persistenceManager.findById(GUserChatContext.class, request.getUserChatContextCode());
				if (userContext.getUsername() == null || !userContext.getUsername().equals(currentUserName)) {
					throw new SecurityException("Attempting to access the wrong chat userContext");
				}
			}
			// Initiating chat by calling the chat method with parameters.
			GeboTemplatedChatResponse<T> generatedResponse = this.chat(request, chatProfile, userContext, responseType);
			return generatedResponse;
		} catch (GeboPersistenceException e) {
			throw new GeboChatException("Problems with mongodb", e);
		} finally {
			// No cleanup required here as the context is managed.
		}
	}

	/**
	 * Retrieves chat model meta information given a chat profile code.
	 *
	 * @param chatProfileCode Code of the chat profile
	 * @return GBaseChatModelChoice Chat model choice information
	 */
	@Override
	public GBaseChatModelChoice getChatProfileModelMetaInfos(String chatProfileCode) {
		// Retrieve the runtime chat profile model
		IGChatProfileChatModel runtime = chatProfileModelsDao.findByCode(chatProfileCode);
		return runtime != null && runtime.getChatModel() != null && runtime.getChatModel().getConfig() != null
				&& runtime.getChatModel().getConfig().getChoosedModel() != null
						? (GBaseChatModelChoice) runtime.getChatModel().getConfig().getChoosedModel()
						: null;
	}

	/**
	 * Method signature for templated chat with extended parameters (currently
	 * returns null)
	 *
	 * @param request           GeboChatRequest
	 * @param prompt            Custom prompt
	 * @param customEnvironment Environment map
	 * @param contents          Contents map
	 * @param responseType      Expected response type
	 * @param <T>               Generic type
	 * @return GeboTemplatedChatResponse of type T
	 * @throws GeboChatException
	 * @throws LLMConfigException
	 */
	@Override
	public <T> GeboTemplatedChatResponse<T> templatedChat(GeboChatRequest request, String prompt,
			Map<String, Object> customEnvironment, Map<String, Function<KBContext, Object>> contents,
			Class<T> responseType) throws GeboChatException, LLMConfigException {
		// Returns null as this method is not implemented
		return null;
	}

	/**
	 * Retrieves the model capabilities of a chat profile provider.
	 *
	 * @param chatProfileCode Code identifying the chat profile
	 * @return ModelProviderCapabilities Information about the model capabilities
	 * @throws LLMConfigException Thrown if an issue arises with LLM Configuration
	 */
	@Override
	public ModelProviderCapabilities getProfileProviderModelCapabilities(String chatProfileCode)
			throws LLMConfigException {
		// Find the chat profile by its code
		Optional<GChatProfileConfiguration> profile = chatProfilesRepository.findById(chatProfileCode);
		if (profile.isPresent()) {
			IGChatProfileChatModel chatProfileModel = chatProfileModelsDao.getChatModel(profile.get());
			if (chatProfileModel != null) {
				IGConfigurableChatModel model = chatProfileModel.getChatModel();
				GBaseChatModelConfig c = (GBaseChatModelConfig) model.getConfig();
				List<String> functions = c.getEnabledFunctions();
				ModelProviderCapabilities cap = new ModelProviderCapabilities(model.getCode(),
						model.isSupportsTranscript(), model.isSupportsSpeech(), model.isSupportsStructuredOutput(),
						model.isSupportsFunctionsCall(), callbacksRepoPattern.getEnabledToolsTree(functions));
				return cap;
			}
		}
		// Return null if the profile was not found or capabilities couldn't be
		// determined
		return null;
	}

	/**
	 * Retrieves chat model user information by chat profile code.
	 *
	 * @param chatProfileCode Code of the chat profile
	 * @return GeboChatUserInfo Information about the user
	 * @throws GeboPersistenceException If there's an issue accessing the data store
	 * @throws LLMConfigException       If there's a configuration error
	 */
	@Override
	public GeboChatUserInfo getChatModelUserInfoByChatProfileCode(String chatProfileCode)
			throws GeboPersistenceException, LLMConfigException {
		// Retrieve chat profile based on code
		Optional<GChatProfileConfiguration> profile = chatProfilesRepository.findById(chatProfileCode);
		if (profile.isPresent()) {
			IGChatProfileChatModel runtime = chatProfileModelsDao.getChatModel(profile.get());
			IGConfigurableChatModel chatModel = runtime.getChatModel();
			GBaseChatModelConfig config = (GBaseChatModelConfig) chatModel.getConfig();
			GBaseChatModelChoice modelChoice = (GBaseChatModelChoice) config.getChoosedModel();
			List<ToolCategoriesTree> functionsTree = callbacksRepoPattern
					.getEnabledToolsTree(runtime.getChatProfile().getEnabledFunctions());
			List<String> kbCodes = runtime.getChatProfile().getKnowledgeBaseCodes();
			List<GKnowledgeBase> list = knowledgeBaseVisibilityService.visiblesAndChildKnowledgebases(kbCodes);
			// Builds a list of GBaseObject from visible knowledge bases
			List<GBaseObject> kbases = list.stream().map(x -> {
				GBaseObject b = new GBaseObject();
				b.setCode(x.getCode());
				b.setDescription(x.getDescription());
				return b;
			}).toList();
			return new GeboChatUserInfo(config.getModelTypeCode(), modelChoice, kbases, functionsTree);
		} else
			throw new RuntimeException("Unknown chat profile code" + chatProfileCode);
	}

	/**
	 * Streams chat messages as a Flux of GeboChatMessageEnvelope.
	 *
	 * @param request GeboChatRequest
	 * @return Flux of GeboChatMessageEnvelope
	 * @throws GeboChatException  If an error occurs during chat streaming
	 * @throws LLMConfigException If a configuration error occurs
	 */
	@Override
	public Flux<GeboChatMessageEnvelope> streamChat(GeboChatRequest request)
			throws GeboChatException, LLMConfigException {
		// Ensure request has valid chat profile code
		if (request.getChatProfileCode() == null || request.getChatProfileCode().trim().length() == 0) {
			throw new GeboChatException("Cannot satisfy a request with null chatProfileCode");
		}
		GChatProfileConfiguration chatProfile = null;
		String code = request.getChatProfileCode();
		GUserChatContext userContext = null;

		// Retrieve chat profile from repository by its code
		Optional<GChatProfileConfiguration> profileEntry = chatProfilesRepository.findById(code);
		if (profileEntry.isPresent()) {
			chatProfile = profileEntry.get();
		} else {
			throw new GeboChatException("Unknown chat profile:" + code);
		}
		try {
			// Establish user context for the chat stream
			UserInfos user = securityService.getCurrentUser();
			String currentUserName = user.getUsername();
			if (request.getUserChatContextCode() == null || request.getUserChatContextCode().trim().length() == 0) {
				userContext = new GUserChatContext();
				userContext.setDescription(chatProfile.getDescription());
				userContext.setChatProfileCode(request.getChatProfileCode());
				userContext.setRagChat(true);
				userContext.setChatCreationDateTime(new Date());
				userContext.setUsername(currentUserName);
				userContext = persistenceManager.insert(userContext);
			} else {
				userContext = persistenceManager.findById(GUserChatContext.class, request.getUserChatContextCode());
				if (userContext.getUsername() == null || !userContext.getUsername().equals(currentUserName)) {
					throw new SecurityException("Attempting to access the wrong chat userContext");
				}
			}
			// Returns the chat stream for the request, profile and context
			return this.streamChat(request, chatProfile, userContext, user);
		} catch (GeboPersistenceException e) {
			throw new GeboChatException("Problems with mongodb", e);
		} finally {
			// No specific post-processing required
		}

	}

	/**
	 * Helper method to stream chat with specified profile and user context.
	 *
	 * @param request     GeboChatRequest
	 * @param chatProfile GChatProfileConfiguration
	 * @param userContext GUserChatContext
	 * @param user
	 * @return Flux of GeboChatMessageEnvelope
	 * @throws LLMConfigException If configuration is not properly set
	 */
	private Flux<GeboChatMessageEnvelope> streamChat(GeboChatRequest request, GChatProfileConfiguration chatProfile,
			GUserChatContext userContext, UserInfos user) throws LLMConfigException {
		// Set up chat environment and models
		ChatProfileRuntimeEnvironment chatEnvironment = chatProfileManagementService.createChatEnvironment(chatProfile);

		IGConfigurableEmbeddingModel embeddingHandler = chatEnvironment.getEmbeddingModel();
		IGConfigurableChatModel chatHandler = chatEnvironment.getChatModel();
		int contextLength = chatHandler.getContextLength();
		PromptTemplate promptTemplate = chatEnvironment.getPrompt();
		KBContext context = new KBContext();
		context.setActualUser(userContext.getUsername());
		if (embeddingHandler != null) {
			context.setUsedEmbeddingSystem(embeddingHandler.getCode());
		}
		boolean allVisibles = chatProfile.getUserChoosesKnowledgeBases() != null
				&& chatProfile.getUserChoosesKnowledgeBases();

		List<GKnowledgeBase> visibleKnowledgeBases = null;
		if (allVisibles) {
			visibleKnowledgeBases = knowledgeBaseVisibilityService.allVisibleKnowledgebases();
		} else {
			visibleKnowledgeBases = knowledgeBaseVisibilityService
					.visiblesAndChildKnowledgebases(chatProfile.getKnowledgeBaseCodes());
		}
		List<String> visibleKnowledgeBaseCodes = visibleKnowledgeBases.stream().map(x -> x.getCode()).toList();
		context.setKnowledgeBasesCodes(visibleKnowledgeBaseCodes);
		LLMtInteractionContextThreadLocal.Context.set(context);
		GeboChatResponse response = new GeboChatResponse();
		response.setQuery(request.getQuery());
		response.setUserChatContextCode(userContext.getCode());
		if (embeddingHandler == null) {
			response.getBackendMessages()
					.add(GUserMessage.errorMessage(
							"No default or specific per knowledgebase embedding model configured",
							"Review the LLMs embeddings configuration"));
		}
		if (chatHandler == null) {
			response.getBackendMessages()
					.add(GUserMessage.errorMessage("No default or specific per knowledgebase chat model configured",
							"Review the LLMs embeddings configuration"));
		}
		if (embeddingHandler != null && chatHandler != null) {
			try {

				ChatModelLimitedRequest limitedResourcesRequest = requestLimitationPolicy.manageRequest(chatProfile,
						userContext, user, request, embeddingHandler, chatHandler, visibleKnowledgeBaseCodes);
				response.setContextWindowStats(limitedResourcesRequest.getStats());
				if (chatHandler.getConfig() != null && chatHandler.getConfig().getChoosedModel() != null) {
					response.setUsedChatModelCode(chatHandler.getConfig().getChoosedModel().getCode());
				}
				if (chatHandler.getType() != null) {
					response.setUsedChatModelProvider(chatHandler.getType().getCode());
				}
				List<ChatInteractions> history = limitedResourcesRequest.getHistory().getValue();
				RagDocumentsCachedDaoResult extractedDocuments = limitedResourcesRequest.getDocuments().getValue();
				RagDocumentsCachedDaoResult contextDocuments = limitedResourcesRequest.getContextDocuments().getValue();
				RagDocumentsCachedDaoResult uploadedDocuments = limitedResourcesRequest.getUploadedDocuments()
						.getValue();
				List<GResponseDocumentRef> docrefs = GResponseDocumentRef.from(extractedDocuments);
				List<Document> contextdocs = contextDocuments != null ? contextDocuments.aiDocumentsList() : List.of();
				if (uploadedDocuments != null) {
					List<Document> uploaded = uploadedDocuments.aiDocumentsList();
					contextdocs = new ArrayList<>(contextdocs);
					contextdocs.addAll(uploaded);
				}
				response.setDocumentsRef(docrefs);
				request.setDocuments(extractedDocuments);
				Prompt prompt = promptTemplate.create();
				return streamChatClient(chatHandler, prompt, context, request, response, userContext, history,
						contextdocs);
			} catch (Throwable th) {
				response.getBackendMessages().add(GUserMessage.errorMessage("Error on service provider", th));
				LOGGER.error("Error chat handling", th);
				GeboChatMessageEnvelope<GeboChatResponse> envelope = new GeboChatMessageEnvelope<GeboChatResponse>();

				envelope.setContent(response);
				envelope.setLastMessage(true);
				return Flux.just(envelope);
			}
		} else
			throw new LLMConfigException("No chat handler and/or embedding handler");

	}

	public GUserChatInfo suggestChatDescription(String id) throws GeboChatException, LLMConfigException {
		GUserChatInfoData data = null;
		Optional<GUserChatContext> repodata = this.userContextRepository.findById(id);
		if (repodata.isPresent()) {
			GUserChatContext context = repodata.get();
			data = new GUserChatInfoData(context);
			GPromptConfig prompt = this.promptConfigs.getSummarizeChatDescriptionPrompt();
			IGConfigurableChatModel handler = chatModelConfigurations.defaultHandler();
			try {
				if (context.getChatProfileCode() != null) {
					Optional<GChatProfileConfiguration> profileOpt = this.chatProfilesRepository
							.findById(context.getChatProfileCode());
					if (profileOpt.isPresent()) {
						GChatProfileConfiguration profile = profileOpt.get();
						GObjectRef<GBaseChatModelConfig> modelReference = profile.getChatModelReference();
						if (modelReference != null) {
							IGConfigurableChatModel refHandler = chatModelConfigurations
									.findByModelReference(modelReference);
							if (refHandler != null) {
								handler = refHandler;
							}
						}
					}
				}
				if (handler != null && context.getInteractions() != null && !context.getInteractions().isEmpty()) {
					String content = handler.getChatClient().prompt(prompt.getPrompt())
							.user(context.getInteractions().get(0).getRequest().getQuery()).call().content();
					data.setDescription(content);
					context.setDescription(content);
					this.userContextRepository.save(context);
					return data;
				}
			} catch (Throwable th) {
				LOGGER.error("Exception in suggestChatDescription", th);
				return data;
			}
		} else
			throw new RuntimeException("Id is not found");
		return data;
	}

}