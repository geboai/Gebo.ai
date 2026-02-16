package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.ClientChatCallUtil;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatSessionLifeCycleConfig;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfoData;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.chat.abstraction.layer.services.IGShrinkedChatSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSReferredContentList;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSfRelevantShrinkedDocumentList;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.system.ingestion.GeboIngestionException;
import io.jsonwebtoken.security.SecurityException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@Scope("singleton")
@AllArgsConstructor
public class GChatSessionLifeCycleServiceImpl implements IGChatSessionLifeCycleService, IGMessageEmitter {
	private final static Logger LOGGER = LoggerFactory.getLogger(GChatSessionLifeCycleServiceImpl.class);
	private final SessionShrinkMessagesReceiver sessionShrinkMessagesReceiver;
	static final String SESSION_LIFE_CYCLE_SERVICE = "sessionLifeCycleService";
	private final IGChatFullSessionStateService fullSessionStateService;
	private final IGShrinkedChatSessionStateService shrinkedSessionStateService;
	private final GUserChatSessionRepository sessionRepository;
	private final GeboChatSessionLifeCycleConfig lifeCycleConfig;
	private final DocumentReferenceRepository documentsRepository;
	private final ChatProfilesRepository chatProfilesRepository;
	private final IGChatStorageAreaService chatAreaStorageSession;
	private final IGAIDocumentsCacheService documentsCacheService;
	private final IGPersistentObjectManager persistenceManager;
	private final IGSecurityService securityService;
	private final IGMessageBroker broker;
	private final IGChatModelRuntimeConfigurationDao chatModelsDao;
	private final IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;
	private final IGPromptConfigDao promptsDao;

	@NoArgsConstructor

	@AllArgsConstructor
	static class CacheEntry {
		GUserChatSession session = null;
		ChatFullSessionState full = null;
		ShrinkedChatSessionState shrinked = null;
	}

	static Hashtable<String, CacheEntry> cache = new Hashtable<String, GChatSessionLifeCycleServiceImpl.CacheEntry>();

	@Override
	public void createChatSession(GeboChatRequest request)
			throws GeboChatSessionLifecycleException, GeboPersistenceException {

		if (request.getUserChatContextCode() == null && request.getUserChatContextCode().trim().length() == 0) {
			UserInfos user = securityService.getCurrentUser();
			GUserChatSession s = new GUserChatSession();
			s.setUsername(user.getUsername());
			s.setChatProfileCode(request.getChatProfileCode());
			s.setChatModelCode(request.getChatModelCode());
			if (request.getChatProfileCode() != null) {
				Optional<GChatProfileConfiguration> data = this.chatProfilesRepository
						.findById(request.getChatProfileCode());
				s.setRagChat(data.isPresent());
				if (data.isPresent()) {
					s.setDescription(data.get().getDescription() + " new chat");
				}
			} else {
				s.setDescription("New chat of " + user.getUsername());
			}
			s = this.persistenceManager.insert(s);
			request.setUserChatContextCode(s.getCode());
			if (s.getInteractions() != null && !s.getInteractions().isEmpty()) {
				throw new GeboChatSessionLifecycleException("Cannot create a session for user context=>"
						+ request.getUserChatContextCode() + " that already has interactions");
			} else {
				ChatFullSessionState data = fullSessionStateService.retrieveState(request.getUserChatContextCode());
				if (data != null)
					throw new GeboChatSessionLifecycleException("Cannot create a session for user context=>"
							+ request.getUserChatContextCode() + " that already has one");
			}
			ChatFullSessionState data = new ChatFullSessionState();
			data.setUserChatContextCode(request.getUserChatContextCode());
			this.fullSessionStateService.save(data);
			ShrinkedChatSessionState shrinked = new ShrinkedChatSessionState();
			shrinked.setUserChatContextCode(request.getUserChatContextCode());
			this.shrinkedSessionStateService.save(shrinked);
		} else
			throw new GeboChatSessionLifecycleException(
					"Creating session on a request where session id is already referred");
	}

	private GUserChatSession get(String id) throws GeboChatSessionLifecycleException {
		if (id == null || id.trim().length() == 0) {
			throw new GeboChatSessionLifecycleException("session id is null");
		}
		GUserChatSession session = this.sessionRepository.findById(id)
				.orElseThrow(GeboChatSessionLifecycleException::new);
		this.securityService.checkBeingCreator(session);
		return session;
	}

	@Override
	public void removeChatSession(String code) throws GeboChatSessionLifecycleException {
		GUserChatSession session = get(code);
		this.fullSessionStateService.deleteState(code);
		this.shrinkedSessionStateService.deleteState(code);
		try {
			this.chatAreaStorageSession.deleteSessionContents(code);
		} catch (IOException e) {

		}
		this.sessionRepository.deleteById(code);
	}

	@Override
	public LLMChatRequestResources startRequest(GeboChatRequest request, IGConfigurableChatModel targetChatModel,
			LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException, IOException {
		GUserChatSession context = get(request.getUserChatContextCode());
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;
		boolean addInteraction = false;
		Optional<ChatInteractions> alredyInInteraction = context.getInteractions().stream()
				.filter(x -> x.getRequest().getId().equals(request.getId())).findFirst();
		addInteraction = alredyInInteraction.isEmpty();
		ChatInteractions interaction = addInteraction ? new ChatInteractions() : alredyInInteraction.get();
		interaction.setRequest(request);
		interaction.setRequestNTokens(ITokensCountable.stringsTokensSize(request.getQuery()));
		if (addInteraction)
			context.getInteractions().add(interaction);

		List<String> documentsList = request.getForcedRequestDocuments();
		List<UserUploadedContent> uploadedContents = request.getUserUploadedContents();
		this.fullSessionStateService.addRequestToState(state, request, index);
		this.shrinkedSessionStateService.addRequestToState(shrinked, request, index);
		if (documentsList != null && !documentsList.isEmpty()) {
			List<GDocumentReference> docs = documentsRepository.findAllById(documentsList);
			for (GDocumentReference doc : docs) {
				AIDocumentReferenceItem ingested = null;
				try {
					ingested = this.documentsCacheService.retrieve(doc);
				} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
						| GeboIngestionException e) {
					throw new GeboChatSessionLifecycleException("Exception in ingesting docs", e);
				}
				state = this.fullSessionStateService.addChatWithDocumentToState(state, doc, ingested, index);
				shrinked = this.shrinkedSessionStateService.addChatWithDocumentToState(shrinked, doc, ingested, index);
			}
		}
		if (uploadedContents != null) {
			for (UserUploadedContent userUploadedContent : uploadedContents) {
				List<Document> ingested = this.chatAreaStorageSession.getIngestedContentsOf(userUploadedContent);
				AIDocumentsSet docset = AIDocumentsSet.from(ingested);
				if (!docset.getDocumentItems().isEmpty()) {
					AIDocumentReferenceItem data = docset.getDocumentItems().get(0);
					state = this.fullSessionStateService.addUploadedDocumentToState(state, userUploadedContent, data,
							index);
					shrinked = this.shrinkedSessionStateService.addUploadedDocumentToState(shrinked,
							userUploadedContent, data, index);
				}
			}
		}

		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		CacheEntry cacheEntry = new CacheEntry(context, state, shrinked);
		this.cache.put(request.getId(), cacheEntry);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	private CacheEntry getCache(GeboChatRequest r) throws GeboChatSessionLifecycleException {
		CacheEntry entry = this.cache.get(r.getId());
		if (entry == null)
			throw new GeboChatSessionLifecycleException("Cannot retrieve from cache request id=" + r.getId());
		return entry;
	}

	private GUserChatSession session(GeboChatRequest r) throws GeboChatSessionLifecycleException {
		return getCache(r).session;
	}

	private ChatFullSessionState full(GeboChatRequest r) throws GeboChatSessionLifecycleException {
		return getCache(r).full;
	}

	private ShrinkedChatSessionState shrink(GeboChatRequest r) throws GeboChatSessionLifecycleException {
		return getCache(r).shrinked;
	}

	private int getTargetShrinkResize(IGConfigurableChatModel targetChatModel) {
		double contextWindow = targetChatModel.getContextLength();
		double shrinkResize = lifeCycleConfig.getSessionShrinkResizeContextWindowCoeff() * contextWindow;
		if (lifeCycleConfig.getMinimumShrinkResizeTargetTokens() != null
				&& shrinkResize < lifeCycleConfig.getMinimumShrinkResizeTargetTokens().doubleValue()) {
			shrinkResize = lifeCycleConfig.getMinimumShrinkResizeTargetTokens().doubleValue();
		}
		return (int) shrinkResize;
	}

	private LLMChatRequestResources applyGenerationPolicy(ShrinkedChatSessionState shrinked, int budget,
			LLMRequestGenerationPolicy policy) {
		switch (policy) {
		case ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET: {
			return shrinked.createChatRequestResources(policy);
		}

		default: {

			LLMChatRequestResources data = shrinked.createChatRequestResources(policy);
			if (removeRelevantPastContentsProgressively(shrinked, shrinked.getRelevantChatWithDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeRelevantPastContentsProgressively(shrinked, shrinked.getRelevantRetrievedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeRelevantPastContentsProgressively(shrinked, shrinked.getRelevantUploadedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeRelevantPastContentsProgressively(shrinked, shrinked.getRelevantLlmGeneratedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeLatestDocumentsProgressively(shrinked, shrinked.getLatestRequestsRetrievedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeLatestDocumentsProgressively(shrinked, shrinked.getLatestRequestsChatWithDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeLatestDocumentsProgressively(shrinked, shrinked.getLatestRequestsUploadedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeLatestDocumentsProgressively(shrinked, shrinked.getLatestRequestsLlmGeneratedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			return shrinked.createChatRequestResources(policy);
		}

		}

	}

	private boolean removeLatestDocumentsProgressively(ShrinkedChatSessionState shrinked,
			CSSReferredContentList<?> cssReferredContentList, int budget) {
		cssReferredContentList = new CSSReferredContentList(cssReferredContentList);
		do {
			if (!cssReferredContentList.getData().isEmpty())
				cssReferredContentList.getData().remove(0);
		} while (!cssReferredContentList.getData().isEmpty() && budget > shrinked.getTokensSize());
		return budget > shrinked.getTokensSize();
	}

	private boolean removeRelevantPastContentsProgressively(ShrinkedChatSessionState shrinked,
			CSSfRelevantShrinkedDocumentList relevantRetrievedDocuments, int budget) {
		relevantRetrievedDocuments = new CSSfRelevantShrinkedDocumentList(relevantRetrievedDocuments);
		do {
			if (!relevantRetrievedDocuments.isEmpty())
				relevantRetrievedDocuments.remove(0);
		} while (!relevantRetrievedDocuments.isEmpty() && budget > shrinked.getTokensSize());
		return budget > shrinked.getTokensSize();
	}

	private int getTokensBudget(IGConfigurableChatModel targetChatModel) {
		double contextWindow = targetChatModel.getContextLength();
		double maximumTokenBudget = lifeCycleConfig.getMaximumContextWindowFullFillCoeff() * contextWindow;
		if (lifeCycleConfig.getMaximumContextWindowTokenUsed() != null
				&& maximumTokenBudget > lifeCycleConfig.getMaximumContextWindowTokenUsed().doubleValue()) {
			maximumTokenBudget = lifeCycleConfig.getMaximumContextWindowTokenUsed().doubleValue();
		}
		return (int) maximumTokenBudget;
	}

	@Override
	public LLMChatRequestResources addUploadedDocument(GeboChatRequest request, UserUploadedContent content,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException, IOException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;
		index = index > 0 ? index - 1 : 0;

		List<Document> ingested = this.chatAreaStorageSession.getIngestedContentsOf(content);
		AIDocumentsSet docset = AIDocumentsSet.from(ingested);
		if (!docset.getDocumentItems().isEmpty()) {
			AIDocumentReferenceItem data = docset.getDocumentItems().get(0);
			state = this.fullSessionStateService.addUploadedDocumentToState(state, content, data, index);
			shrinked = this.shrinkedSessionStateService.addUploadedDocumentToState(shrinked, content, data, index);
		}
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}

		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);

	}

	@Override
	public LLMChatRequestResources removeUploadedDocument(GeboChatRequest request, UserUploadedContent content,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		state = this.fullSessionStateService.removeUploadedDocumentToState(state, content);
		shrinked = this.shrinkedSessionStateService.removeUploadedDocumentToState(shrinked, content);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}

		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources addChatWithDocument(GeboChatRequest request, GDocumentReference reference,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		AIDocumentReferenceItem data = null;
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;

		try {
			data = this.documentsCacheService.retrieve(reference);
		} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
				| GeboIngestionException e) {
			throw new GeboChatSessionLifecycleException("Exception in ingesting docs", e);
		}
		state = this.fullSessionStateService.addChatWithDocumentToState(state, reference, data, index);
		shrinked = this.shrinkedSessionStateService.addChatWithDocumentToState(shrinked, reference, data, index);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}

		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources removeChatWithDocument(GeboChatRequest request, GDocumentReference reference,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		state = this.fullSessionStateService.removeChatWithDocumentToState(state, reference);
		shrinked = this.shrinkedSessionStateService.removeChatWithDocumentToState(shrinked, reference);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}

		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources addRetrievedDocuments(GeboChatRequest request, AIDocumentsSet retrieved,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;
		index = index > 0 ? index - 1 : 0;

		state = this.fullSessionStateService.addRetrievedDocumentsToState(state, retrieved, index);

		shrinked = this.shrinkedSessionStateService.addRetrievedDocumentsToState(shrinked, retrieved, index);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}

		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources removeRetrievedDocuments(GeboChatRequest request, AIDocumentsSet retrieved,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		state = this.fullSessionStateService.removeRetrievedDocumentsToState(state, retrieved);
		shrinked = this.shrinkedSessionStateService.removeRetrievedDocumentsToState(shrinked, retrieved);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}

		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources addLLMGenerated(GeboChatRequest request, LLMGeneratedResource resource,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;
		index = index > 0 ? index - 1 : 0;

		try {
			List<Document> docs = this.chatAreaStorageSession.getIngestedContentsOf(resource);
			AIDocumentsSet docset = AIDocumentsSet.from(docs);
			if (docset != null && !docset.getDocumentItems().isEmpty()) {
				AIDocumentReferenceItem ingested = docset.getDocumentItems().get(0);
				state = this.fullSessionStateService.addLLMGeneratedDocumntsToState(state, resource, ingested, index);
				shrinked = this.shrinkedSessionStateService.addLLMGeneratedDocumntsToState(shrinked, resource, ingested,
						index);
			}
			int budget = getTokensBudget(targetChatModel);
			if (shrinked.getTokensSize() >= budget) {
				int targetTokenBudget = getTargetShrinkResize(targetChatModel);
				shrinked.setToBeShrinked(true);
				shrinked.setTargetTokenBudget(targetTokenBudget);
			}

			if (state.getTokensSize() < budget) {
				return state.createChatRequestResources(policy);
			}
			if (shrinked.getTokensSize() < budget) {
				return shrinked.createChatRequestResources(policy);
			}
			return applyGenerationPolicy(shrinked, budget, policy);
		} catch (IOException | GeboContentHandlerSystemException | GeboIngestionException e) {
			throw new GeboChatSessionLifecycleException("Exception ingesting a generated resource", e);
		}

	}

	@Override
	public void endRequest(GeboChatRequest request, GeboChatResponse response)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;

		List<LLMGeneratedResource> generated = response.getGeneratedResources();
		if (generated != null) {
			for (LLMGeneratedResource llmGeneratedResource : generated) {
				List<Document> docs = null;
				try {
					docs = this.chatAreaStorageSession.getIngestedContentsOf(llmGeneratedResource);
				} catch (IOException | GeboContentHandlerSystemException | GeboIngestionException e) {
					throw new GeboChatSessionLifecycleException("Exception ingesting a generated resource", e);
				}
				AIDocumentsSet docset = AIDocumentsSet.from(docs);

				if (docset != null && !docset.getDocumentItems().isEmpty()) {
					AIDocumentReferenceItem ingested = docset.getDocumentItems().get(0);
					state = this.fullSessionStateService.addLLMGeneratedDocumntsToState(state, llmGeneratedResource,
							ingested, index);
					shrinked = this.shrinkedSessionStateService.addLLMGeneratedDocumntsToState(shrinked,
							llmGeneratedResource, ingested, index);
				}

			}
		}
		state = this.fullSessionStateService.addInteractionToState(state, request, response, index);
		shrinked = this.shrinkedSessionStateService.addInteractionToState(shrinked, request, response, index);
		this.fullSessionStateService.save(state);
		this.shrinkedSessionStateService.save(shrinked);
		boolean addInteraction = false;
		Optional<ChatInteractions> alredyInInteraction = context.getInteractions().stream()
				.filter(x -> x.getRequest().getId().equals(request.getId())).findFirst();
		addInteraction = alredyInInteraction.isEmpty();
		ChatInteractions interaction = addInteraction ? new ChatInteractions() : alredyInInteraction.get();
		interaction.setRequest(request);
		interaction.setRequestNTokens(ITokensCountable.stringsTokensSize(request.getQuery()));
		interaction.setResponse(response);
		interaction.setResponseNTokens(ITokensCountable.stringsTokensSize(response.getQueryResponse()));
		if (addInteraction)
			context.getInteractions().add(interaction);
		sessionRepository.save(context);
		this.cache.remove(request.getId());
	}

	@Override
	public void chatRequestCompleted(GeboChatRequest request, IGConfigurableChatModel targetChatModel)
			throws GeboChatSessionLifecycleException, LLMConfigException, IOException {

		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService
				.retrieveState(request.getUserChatContextCode());
		if (shrinked != null && shrinked.isToBeShrinked()) {
			String code = request.getUserChatContextCode();
			int budgetSize = getTargetShrinkResize(targetChatModel);
			SessionShrinkRequestPayload checkPayload = new SessionShrinkRequestPayload();
			checkPayload.setTokensBudget(budgetSize);
			checkPayload.setUserChatSessionCode(code);
			GMessageEnvelope<SessionShrinkRequestPayload> envelope = GMessageEnvelope.newMessageFrom(this,
					checkPayload);
			envelope.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
			envelope.setTargetComponent(SessionShrinkMessagesReceiver.SESSION_SHRINKER);
			this.broker.accept(envelope);
		}
	}

	@Override
	public void ensureChatSessionExists(GeboChatRequest request)
			throws GeboChatSessionLifecycleException, GeboPersistenceException {
		if (!isSessionExisting(request))
			createChatSession(request);
	}

	private ChatFullSessionState retrieveAndCheck(String id) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(id);
		if (state == null)
			throw new GeboChatSessionLifecycleException("session " + id + " does not exist");
		return state;
	}

	private ChatFullSessionState retrieveAndCheck(GUserChatSession ctx) throws GeboChatSessionLifecycleException {
		return this.retrieveAndCheck(ctx.getCode());
	}

	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.CORE_MODULE;
	}

	@Override
	public String getMessagingSystemId() {

		return SESSION_LIFE_CYCLE_SERVICE;
	}

	@Override
	public SystemComponentType getComponentType() {

		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {

		return List.of(SessionShrinkRequestPayload.class.getName());
	}

	@Override
	public void updateRequest(GeboChatRequest request) throws GeboChatSessionLifecycleException {
		GUserChatSession context = get(request.getUserChatContextCode());
		boolean addInteraction = false;
		Optional<ChatInteractions> alredyInInteraction = context.getInteractions().stream()
				.filter(x -> x.getRequest().getId().equals(request.getId())).findFirst();
		addInteraction = alredyInInteraction.isEmpty();
		ChatInteractions interaction = addInteraction ? new ChatInteractions() : alredyInInteraction.get();
		interaction.setRequest(request);
		interaction.setRequestNTokens(ITokensCountable.stringsTokensSize(request.getQuery()));

		if (addInteraction)
			context.getInteractions().add(interaction);
		sessionRepository.save(context);
		ChatFullSessionState full = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		if (shrinked.getCurrentRequest() != null && request.getId() != null
				&& shrinked.getCurrentRequest().getId().equals(request.getId())) {
			shrinked.setCurrentRequest(request);
			this.shrinkedSessionStateService.save(shrinked);
		} else
			throw new GeboChatSessionLifecycleException(
					"Logical problem, the request id to update does not match the actual one");
		if (full.getCurrentRequest().getValue() != null && full.getCurrentRequest().getValue().getId() != null
				&& request.getId() != null && full.getCurrentRequest().getValue().getId().equals(request.getId())) {
			full.getCurrentRequest().setValue(request);
			this.fullSessionStateService.save(full);

		} else
			throw new GeboChatSessionLifecycleException(
					"Logical problem, the request id to update does not match the actual one");

	}

	@Override
	public boolean isSessionExisting(GeboChatRequest request) {
		boolean isNull = request.getUserChatContextCode() == null
				|| request.getUserChatContextCode().trim().length() == 0;
		if (isNull)
			return false;
		return this.sessionRepository.findById(request.getUserChatContextCode()).isPresent();
	}

	@Override
	public List<GKnowledgeBase> getSessionAvailableKnowledgeBases(GeboChatRequest request)
			throws GeboChatSessionLifecycleException {
		List<GKnowledgeBase> out = new ArrayList<GKnowledgeBase>();
		GUserChatSession session = get(request.getUserChatContextCode());
		if (session.getChatProfileCode() != null) {
			Optional<GChatProfileConfiguration> profileOpt = this.chatProfilesRepository
					.findById(session.getChatProfileCode());
			if (profileOpt.isPresent()) {
				List<String> knowledgeBases = profileOpt.get().getKnowledgeBaseCodes();
				if (knowledgeBases == null)
					knowledgeBases = List.of();
				Boolean allUserVisibles = profileOpt.get().getUserChoosesKnowledgeBases();
				allUserVisibles = allUserVisibles != null && allUserVisibles;
				if (!allUserVisibles) {
					out = this.knowledgeBaseVisibilityService.visiblesAndChildKnowledgebases(knowledgeBases);
				} else
					out = this.knowledgeBaseVisibilityService.allVisibleKnowledgebases();
			}
		}
		return out;
	}

	@Override
	public GeboChatResponse createEmptyResponse(GeboChatRequest request) throws GeboChatSessionLifecycleException {
		if (isSessionExisting(request)) {
			GeboChatResponse response = new GeboChatResponse();
			response.setId(UUID.randomUUID().toString());
			response.setUserChatContextCode(request.getUserChatContextCode());
			response.setQuery(request.getQuery());
			return response;
		}
		throw new GeboChatSessionLifecycleException("Cannot create a response without a chat session");
	}

	@Override
	public GUserChatInfo createCleanChatByModelCode(String modelCode) throws GeboPersistenceException {
		IGConfigurableChatModel model = this.chatModelsDao.findByCode(modelCode);
		if (model != null)
			return createCleanChatByModel(model);
		throw new IllegalStateException("The model :" + modelCode + " does not exist");
	}

	@Override
	public GUserChatInfo createCleanChatByModel(IGConfigurableChatModel chatModel) throws GeboPersistenceException {
		UserInfos user = securityService.getCurrentUser();
		GUserChatSession userContext = new GUserChatSession();
		userContext.setChatModelCode(chatModel.getCode());
		String description = "Chat with "
				+ (chatModel.getConfig() != null && chatModel.getConfig().getChoosedModel() != null
						? chatModel.getConfig().getChoosedModel().getCode()
						: " chat bot");
		userContext.setDescription(description);
		userContext.setUsername(user.getUsername());
		userContext = persistenceManager.insert(userContext);
		ChatFullSessionState data = new ChatFullSessionState();
		data.setUserChatContextCode(userContext.getCode());
		this.fullSessionStateService.save(data);
		ShrinkedChatSessionState shrinked = new ShrinkedChatSessionState();
		shrinked.setUserChatContextCode(userContext.getCode());
		this.shrinkedSessionStateService.save(shrinked);
		GUserChatInfoData _data = new GUserChatInfoData(userContext);
		return _data;
	}

	@Override
	public IGConfigurableChatModel getSessionChatModel(GeboChatRequest request)
			throws GeboChatSessionLifecycleException {
		IGConfigurableChatModel model = null;
		String chatProfileCode = request.getChatProfileCode();
		String chatModelCode = request.getChatModelCode();
		if (request.getUserChatContextCode() != null) {
			GUserChatSession session = this.get(request.getUserChatContextCode());
			chatProfileCode = session.getChatProfileCode();
			if (session.getChatModelCode() != null && chatModelCode == null) {
				chatModelCode = session.getChatModelCode();
			}
		}
		if (chatProfileCode != null) {
			Optional<GChatProfileConfiguration> profileOpt = this.chatProfilesRepository.findById(chatProfileCode);
			if (profileOpt.isPresent()) {
				GChatProfileConfiguration profile = profileOpt.get();
				boolean canAccess = securityService.isCanAccess(profile, true);
				if (!canAccess)
					throw new SecurityException("The actual user cannot use the chat profile:" + chatProfileCode);
				if (profile.getChatModelReference() != null) {
					model = this.chatModelsDao.findByModelReference(profile.getChatModelReference());
				}
			}
		}
		if (model == null && chatModelCode != null) {
			model = this.chatModelsDao.findByCode(chatModelCode);
		}
		if (model == null) {
			model = this.chatModelsDao.defaultHandler();
		}
		return model;
	}

	@Override
	public GUserChatInfo createCleanChatByChatProfileCode(String chatProfileCode) throws GeboPersistenceException {
		Optional<GChatProfileConfiguration> profileOpt = this.chatProfilesRepository.findById(chatProfileCode);
		if (profileOpt.isPresent()) {
			UserInfos user = this.securityService.getCurrentUser();
			GChatProfileConfiguration profile = profileOpt.get();
			boolean canAccess = securityService.isCanAccess(profile, true);
			if (!canAccess)
				throw new SecurityException("The actual user cannot use the chat profile:" + chatProfileCode);
			GUserChatSession userContext = new GUserChatSession();
			userContext.setRagChat(true);
			userContext.setChatProfileCode(chatProfileCode);
			userContext.setDescription(profile.getDescription());
			userContext.setUsername(user.getUsername());
			userContext = persistenceManager.insert(userContext);
			ChatFullSessionState data = new ChatFullSessionState();
			data.setUserChatContextCode(userContext.getCode());
			this.fullSessionStateService.save(data);
			ShrinkedChatSessionState shrinked = new ShrinkedChatSessionState();
			shrinked.setUserChatContextCode(userContext.getCode());
			this.shrinkedSessionStateService.save(shrinked);
			GUserChatInfoData _data = new GUserChatInfoData(userContext);
			return _data;
		}
		throw new IllegalStateException("Chat profile: " + chatProfileCode + " does not exist");
	}

	@Override
	public GUserChatInfo suggestChatDescription(String id) throws GeboChatSessionLifecycleException {
		GUserChatInfoData data = null;
		GUserChatSession context = get(id);

		data = new GUserChatInfoData(context);
		GPromptConfig prompt = this.promptsDao.findByPromptUse(GeboPromptsLibrary.SUMMARIZE_CHAT_DESCRIPTION);
		IGConfigurableChatModel handler = chatModelsDao.defaultHandler();
		try {
			if (handler != null && context.getInteractions() != null && !context.getInteractions().isEmpty()) {
				String content = handler.getChatClient().prompt(prompt.getPrompt())
						.user(context.getInteractions().get(0).getRequest().getQuery()).call().content();
				String pureText = ClientChatCallUtil.removeThinking(content);
				data.setDescription(pureText);
				context.setDescription(pureText);
				this.sessionRepository.save(context);
				return data;
			}
		} catch (Throwable th) {
			LOGGER.error("Exception in suggestChatDescription", th);
			return data;
		}
		return data;
	}

	@Override
	public void persist(GeboChatRequest request) throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		context.setDateModified(new Date());
		this.sessionRepository.save(context);
		this.fullSessionStateService.save(state);
		this.shrinkedSessionStateService.save(shrinked);
	}
}
