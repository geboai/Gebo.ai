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
import java.util.List;
import java.util.Optional;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatUserInfo;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileChatModel;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.constraints.NotNull;
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
	final protected IGDocumentsSearchService ragSearchService;

	public GRagChatServiceImpl(IGChatModelRuntimeConfigurationDao chatModelConfigurations,
			IGToolCallbackSourceRepositoryPattern callbacksRepoPattern, IGPersistentObjectManager persistenceManager,
			IGPromptConfigDao promptsDao, InteractionsContextService interactionsContext,
			IGSecurityService securityService, IGChatResponseParsingFixerServiceRepository fixerServiceRepository,
			IGChatStorageAreaService chatStorageAreaService, LLMGeneratedResourceRepository generatedResourceRepository,
			IGKnowledgebaseVisibilityService knowledgeBaseSecurityService,
			IGChatSessionLifeCycleService chatSessionLifecycleService, IGDocumentsSearchService ragSearchService,
			IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService,
			ChatProfilesRepository chatProfilesRepository, IGRuntimeChatProfileChatModelDao chatProfileModelsDao) {

		super(chatModelConfigurations, callbacksRepoPattern, persistenceManager, promptsDao, interactionsContext,
				securityService, fixerServiceRepository, chatStorageAreaService, generatedResourceRepository,
				knowledgeBaseSecurityService, chatSessionLifecycleService);
		this.chatProfilesRepository = chatProfilesRepository;
		this.chatProfileModelsDao = chatProfileModelsDao;
		this.knowledgeBaseVisibilityService = knowledgeBaseVisibilityService;
		this.ragSearchService = ragSearchService;
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
	 * @throws GeboChatException        if an error occurs during chat processing.
	 * @throws LLMConfigException       if an error occurs due to configuration
	 *                                  issues.
	 * @throws GeboPersistenceException
	 * @throws IOException
	 * @throws FullTextException
	 */
	@Override
	public GeboChatResponse chat(GeboChatRequest request)
			throws GeboChatException, LLMConfigException, GeboPersistenceException, IOException, FullTextException {

		UserInfos user = securityService.getCurrentUser();
		KBContext kbcontext = new KBContext();
		kbcontext.setActualUser(user.getUsername());
		LLMtInteractionContextThreadLocal.Context.set(kbcontext);

		this.chatSessionLifecycleService.ensureChatSessionExists(request);
		IGConfigurableChatModel handler = this.chatSessionLifecycleService.getSessionChatModel(request);
		GeboChatResponse chatResponse = this.chatSessionLifecycleService.createEmptyResponse(request);
		String modelCode = handler != null && handler.getConfig() != null
				&& handler.getConfig().getChoosedModel() != null ? handler.getConfig().getChoosedModel().getCode()
						: null;
		// Retrieve default prompt
		GPromptTemplateConfig gprompt = promptsDao.defaultChatPrompt(modelCode, false);

		// Check if prompt is configured
		if (gprompt == null) {
			throw new GeboChatException("The system has no default prompt configured");
		} else {

			LLMChatRequestResources fullRequest = chatSessionLifecycleService.startRequest(request, handler,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
			List<GKnowledgeBase> knowledgeBases = chatSessionLifecycleService
					.getSessionAvailableKnowledgeBases(request);
			SemanticSearchMetaDataFilter semanticSearchMetaDataFilter = new SemanticSearchMetaDataFilter();
			FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter = new FullTextSearchMetaDataFilter();
			List<String> kbs = knowledgeBases.stream().map(x -> x.getCode()).toList();
			if (securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED
					&& !securityService.isCurrentUserAdmin()) {
				List<Integer> aclAliases = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ)
						.getAllOwnedAclAliases();
				fullTextSearchMetaDataFilter.setAclAliases(aclAliases);
				semanticSearchMetaDataFilter.setAclAliases(aclAliases);
			}
			fullTextSearchMetaDataFilter.setKnowledgebaseCodes(kbs);
			semanticSearchMetaDataFilter.setKnowledgeBasesCodes(kbs);

			AIDocumentsSet retrieved = this.ragSearchService.search(request, semanticSearchMetaDataFilter,
					fullTextSearchMetaDataFilter, handler.getContextLength() / 3);
			fullRequest = chatSessionLifecycleService.addRetrievedDocuments(request, retrieved, handler,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);

			IChatRequestContext chatRequestContext = fullRequest.createChatRequestContext();

			chatResponse = callChatClient(handler, gprompt, kbcontext, request, chatResponse, chatRequestContext,
					retrieved);
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
	 * @throws GeboChatException        If an error occurs during chat streaming
	 * @throws LLMConfigException       If a configuration error occurs
	 * @throws GeboPersistenceException
	 * @throws IOException
	 * @throws FullTextException
	 */
	@Override
	public Flux<GeboChatMessageEnvelope> streamChat(GeboChatRequest request)
			throws GeboChatException, LLMConfigException, GeboPersistenceException, IOException, FullTextException {
		UserInfos user = securityService.getCurrentUser();
		KBContext kbcontext = new KBContext();
		kbcontext.setActualUser(user.getUsername());
		LLMtInteractionContextThreadLocal.Context.set(kbcontext);

		this.chatSessionLifecycleService.ensureChatSessionExists(request);
		List<GKnowledgeBase> knowledgeBases = chatSessionLifecycleService.getSessionAvailableKnowledgeBases(request);
		SemanticSearchMetaDataFilter semanticSearchMetaDataFilter = new SemanticSearchMetaDataFilter();
		FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter = new FullTextSearchMetaDataFilter();
		List<String> kbs = knowledgeBases.stream().map(x -> x.getCode()).toList();
		if (securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED
				&& !securityService.isCurrentUserAdmin()) {
			List<Integer> aclAliases = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ)
					.getAllOwnedAclAliases();
			fullTextSearchMetaDataFilter.setAclAliases(aclAliases);
			semanticSearchMetaDataFilter.setAclAliases(aclAliases);
		}
		fullTextSearchMetaDataFilter.setKnowledgebaseCodes(kbs);
		semanticSearchMetaDataFilter.setKnowledgeBasesCodes(kbs);
		IGConfigurableChatModel handler = this.chatSessionLifecycleService.getSessionChatModel(request);
		LLMChatRequestResources fullRequest = chatSessionLifecycleService.startRequest(request, handler,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		GeboChatResponse response = this.chatSessionLifecycleService.createEmptyResponse(request);
		AIDocumentsSet extractedDocuments = ragSearchService.search(request, semanticSearchMetaDataFilter,
				fullTextSearchMetaDataFilter, handler.getContextLength() / 3);
		List<GResponseDocumentRef> docrefs = GResponseDocumentRef.from(extractedDocuments);
		response.setDocumentsRef(docrefs);
		fullRequest = this.chatSessionLifecycleService.addRetrievedDocuments(request, extractedDocuments, handler,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		String modelCode = handler != null && handler.getConfig() != null
				&& handler.getConfig().getChoosedModel() != null ? handler.getConfig().getChoosedModel().getCode()
						: null;
		GPromptTemplateConfig prompt = this.promptsDao.defaultChatPrompt(modelCode, true);
		// Returns the chat stream for the request, profile and context
		return this.streamChatClient(handler, prompt, kbcontext, request, response,
				fullRequest.createChatRequestContext(), false, 0, extractedDocuments);

	}

	@Override
	public List<GKnowledgeBase> getVisibleKnowledgeBasesByProfileCode(@NotNull String profileCode) {
		Optional<GChatProfileConfiguration> chatProfileData = this.chatProfilesRepository.findById(profileCode);
		if (chatProfileData.isEmpty())
			throw new RuntimeException("Chat profile does not exist");
		final GChatProfileConfiguration chatProfile = chatProfileData.get();
		boolean canAccess = securityService.isCanAccess(chatProfile, true);
		if (!canAccess)
			throw new SecurityException("Trying to access wrong chat profile");
		List<GKnowledgeBase> allVisibles = getVisibleKnowledgeBases();
		if (chatProfile.getUserChoosesKnowledgeBases() == null || !chatProfile.getUserChoosesKnowledgeBases()) {
			if (chatProfile.getKnowledgeBaseCodes() != null && !chatProfile.getKnowledgeBaseCodes().isEmpty()) {
				return allVisibles.stream().filter(x -> chatProfile.getKnowledgeBaseCodes().contains(x.getCode()))
						.toList();
			} else {
				return List.of();
			}
		} else {
			return allVisibles;
		}

	}

}