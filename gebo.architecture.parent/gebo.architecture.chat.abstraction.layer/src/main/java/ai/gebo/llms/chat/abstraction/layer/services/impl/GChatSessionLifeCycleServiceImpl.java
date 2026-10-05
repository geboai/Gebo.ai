package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRulesService;
import ai.gebo.llms.chat.abstraction.layer.model.GChatRule;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatAnswerFeedbackRepository;
import ai.gebo.llms.chat.abstraction.layer.model.GChatAnswerFeedback;
import ai.gebo.llms.chat.abstraction.layer.model.ChatAnswerFeedbackRating;
import java.util.Map;
import java.util.HashMap;
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
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.access.DocumentContentStreamerException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.ClientChatCallUtil;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatSessionLifeCycleConfig;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatUIServerConfig;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.knlowledgebase.model.contents.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInfoData;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGShrinkedChatSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSRelevantShrinkedDocument;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSReferredContentList;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSfRelevantShrinkedDocumentList;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;
import ai.gebo.security.model.UserInfos;
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
	private final IGChatSessionStateShrinkerService shrinkerService;
	private final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao;
	private final IMessageEnvelopeFactory envelopeFactory;
	private final GeboChatUIServerConfig uiServerConfig;
	private final ChatAnswerFeedbackRepository answerFeedbackRepository;
	private final IGChatRulesService rulesService;
	private static final int MAX_RULES_PER_REQUEST = 20;
	private static final int MAX_RULES_LENGTH = 6000;

	@NoArgsConstructor

	@AllArgsConstructor
	static class CacheEntry {
		GUserChatSession session = null;
		ChatFullSessionState full = null;
		ShrinkedChatSessionState shrinked = null;
		String sessionCode = null;
		long startedAt = 0;
		volatile long releasedAt = 0;
	}

	// Requests between startRequest and endRequest / releaseRequest, by request id.
	static final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<String, CacheEntry>();
	// A request still unfinished after this long was abandoned without being released.
	static final long ABANDONED_REQUEST_AGE_MS = 30 * 60 * 1000L;
	// Released requests stay reachable this long: a step may end its request after the stream terminated.
	static final long RELEASED_REQUEST_GRACE_MS = 5 * 60 * 1000L;
	/**
	 * Notified each time a request ends with its interaction saved (see
	 * {@link #endRequest(GeboChatRequest, GeboChatResponse)}): a chat title asked for
	 * before the interaction is saved waits on it.
	 */
	private static final Object INTERACTION_SAVED = new Object();
	/** Longest wait of a chat title for the chat's first interaction to be saved. */
	static final long TITLE_INTERACTION_WAIT_MILLIS = 20000L;
	/**
	 * Interval of the re-reads of the session while waiting: the request may be served
	 * by another node of a cluster, whose saves are not notified here.
	 */
	static final long TITLE_INTERACTION_POLL_MILLIS = 500L;

	@Override
	public void createChatSession(GeboChatRequest request)
			throws GeboChatSessionLifecycleException, GeboPersistenceException {

		if (request.getUserChatContextCode() == null || request.getUserChatContextCode().trim().length() == 0) {
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
			LOGGER.debug("Created chat {} for user:{} profile:{} model:{}", s.getCode(), s.getUsername(),
					s.getChatProfileCode(), s.getChatModelCode());
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
			LOGGER.error("Error deleting session contents for " + code, e);
		}
		LOGGER.debug("Removing chat {} with its full and compact states and stored contents", code);
		this.sessionRepository.deleteById(code);
	}

	@Override
	public LLMChatRequestResources startRequest(GeboChatRequest request, IGConfigurableChatModel targetChatModel,
			LLMRequestGenerationPolicy policy) throws GeboChatSessionLifecycleException, IOException {
		GUserChatSession context = get(request.getUserChatContextCode());
		refuseConcurrentRequest(request);
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		boolean addInteraction = false;
		Optional<ChatInteractions> alredyInInteraction = context.getInteractions().stream()
				.filter(x -> x.getRequest().getId().equals(request.getId())).findFirst();
		addInteraction = alredyInInteraction.isEmpty();
		ChatInteractions interaction = addInteraction ? new ChatInteractions() : alredyInInteraction.get();
		interaction.setRequest(request);
		interaction.setRequestNTokens(ITokensCountable.stringsTokensSize(request.getQuery()));
		if (addInteraction)
			context.getInteractions().add(interaction);
		int index = currentInteractionIndex(context, request);
		List<GResponseDocumentRef> forcedDocumentsRef = request.getForcedDocumentsRef();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"startRequest chat:{} request:{} interaction:{} ({}) forcedRefs:{} forcedCodes:{} uploads:{} model:{} contextLength:{}",
					context.getCode(), request.getId(), index, addInteraction ? "new" : "restarted",
					forcedDocumentsRef != null ? forcedDocumentsRef.size() : 0,
					request.getForcedRequestDocuments() != null ? request.getForcedRequestDocuments().size() : 0,
					request.getUserUploadedContents() != null ? request.getUserUploadedContents().size() : 0,
					targetChatModel != null ? targetChatModel.getCode() : null,
					targetChatModel != null ? targetChatModel.getContextLength() : null);
		}
		List<String> documentsList = request.getForcedRequestDocuments();
		List<UserUploadedContent> uploadedContents = request.getUserUploadedContents();
		this.fullSessionStateService.addRequestToState(state, request, index);
		this.shrinkedSessionStateService.addRequestToState(shrinked, request, index);
		if (forcedDocumentsRef != null && !forcedDocumentsRef.isEmpty()) {
			// Rich references are authoritative when present: unlike the flat code list
			// they can carry external search results (nestedSearchResult) that no internal
			// document code can address. The chatWithExternalFiles gate is enforced here,
			// on the server, so an external ref received while the feature is disabled is
			// silently skipped rather than trusted.
			for (GResponseDocumentRef ref : forcedDocumentsRef) {
				if (ref == null) {
					continue;
				}
				if (ref.getNestedSearchResult() != null) {
					if (!uiServerConfig.isChatWithExternalFiles()) {
						continue;
					}
					AIDocumentReferenceItem ingested;
					try {
						ingested = this.documentsCacheService.retrieve(ref.getNestedSearchResult());
					} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
							| GeboIngestionException | DocumentContentStreamerException e) {
						throw new GeboChatSessionLifecycleException("Exception in ingesting external search result", e);
					}
					if (ingested == null) {
						continue;
					}
					GDocumentReference displayReference = toDisplayReference(ref);
					state = this.fullSessionStateService.addChatWithDocumentToState(state, displayReference, ingested,
							index);
					shrinked = this.shrinkedSessionStateService.addChatWithDocumentToState(shrinked, displayReference,
							ingested, index);
				} else if (ref.getDocumentCode() != null) {
					GDocumentReference doc = documentsRepository.findById(ref.getDocumentCode()).orElse(null);
					if (doc == null) {
						continue;
					}
					AIDocumentReferenceItem ingested;
					try {
						ingested = this.documentsCacheService.retrieve(doc);
					} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
							| GeboIngestionException | DocumentContentStreamerException e) {
						throw new GeboChatSessionLifecycleException("Exception in ingesting docs", e);
					}
					state = this.fullSessionStateService.addChatWithDocumentToState(state, doc, ingested, index);
					shrinked = this.shrinkedSessionStateService.addChatWithDocumentToState(shrinked, doc, ingested,
							index);
				}
			}
		} else if (documentsList != null && !documentsList.isEmpty()) {
			// Back-compat path for older clients that populate only the flat code list.
			List<GDocumentReference> docs = documentsRepository.findAllById(documentsList);
			for (GDocumentReference doc : docs) {
				AIDocumentReferenceItem ingested = null;
				try {
					ingested = this.documentsCacheService.retrieve(doc);
				} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
						| GeboIngestionException | DocumentContentStreamerException e) {
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("startRequest chat:{} request:{} full:{} compact:{} tokens, budget:{} toBeShrinked:{} target:{}",
					context.getCode(), request.getId(), state.getTokensSize(), shrinked.getTokensSize(), budget,
					shrinked.isToBeShrinked(), shrinked.getTargetTokenBudget());
		}
		CacheEntry cacheEntry = new CacheEntry(context, state, shrinked, context.getCode(),
				System.currentTimeMillis(), 0);
		this.cache.put(request.getId(), cacheEntry);
		return budgetedResources(request, context, state, shrinked, budget, policy);
	}

	@Override
	public List<GResponseDocumentRef> resolveResponseDocumentRefs(List<String> codes) {
		List<GResponseDocumentRef> out = new ArrayList<GResponseDocumentRef>();
		if (codes != null && !codes.isEmpty()) {
			for (GDocumentReference doc : documentsRepository.findAllById(codes)) {
				out.add(new GResponseDocumentRef(doc));
			}
		}
		return out;
	}

	/**
	 * Builds a transient {@link GDocumentReference} used only as the display handle
	 * for an external search result in the chat session state - the actual content
	 * comes from the ingested {@link AIDocumentReferenceItem}. This mirrors the
	 * "fake reference" idiom the platform already uses to route search results
	 * through GDocumentReference-typed layers.
	 */
	private GDocumentReference toDisplayReference(GResponseDocumentRef ref) {
		GDocumentReference reference = new GDocumentReference();
		reference.setCode(ref.getDocumentCode());
		reference.setName(ref.getName());
		reference.setContentType(ref.getContentType());
		reference.setExtension(ref.getExtension());
		reference.setRootKnowledgebaseCode(ref.getKnowledgeBaseCode());
		reference.setParentProjectCode(ref.getProjectCode());
		return reference;
	}

	private void refuseConcurrentRequest(GeboChatRequest request) throws GeboChatSessionLifecycleException {
		long now = System.currentTimeMillis();
		int cached = cache.size();
		cache.entrySet().removeIf(x -> now - x.getValue().startedAt > ABANDONED_REQUEST_AGE_MS
				|| (x.getValue().releasedAt > 0 && now - x.getValue().releasedAt > RELEASED_REQUEST_GRACE_MS));
		if (LOGGER.isDebugEnabled() && cache.size() != cached) {
			LOGGER.debug("Forgot {} abandoned or released requests, {} still in progress", cached - cache.size(),
					cache.size());
		}
		for (Map.Entry<String, CacheEntry> running : cache.entrySet()) {
			if (running.getValue().releasedAt == 0
					&& running.getValue().sessionCode.equals(request.getUserChatContextCode())
					&& !running.getKey().equals(request.getId())) {
				LOGGER.debug("Refusing request {}: request {} is still running in chat {}", request.getId(),
						running.getKey(), request.getUserChatContextCode());
				throw new GeboChatSessionLifecycleException(
						"Another request is still running in the chat " + request.getUserChatContextCode());
			}
		}
	}

	@Override
	public void releaseRequest(GeboChatRequest request) {
		CacheEntry entry = request != null && request.getId() != null ? cache.get(request.getId()) : null;
		if (entry != null) {
			entry.releasedAt = System.currentTimeMillis();
			LOGGER.debug("Released request {} of chat {} before it was ended", request.getId(), entry.sessionCode);
		} else if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("releaseRequest({}): already ended or never started", request != null ? request.getId() : null);
		}
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

	private LLMChatRequestResources budgetedResources(GeboChatRequest request, GUserChatSession context,
			ChatFullSessionState state, ShrinkedChatSessionState shrinked, int budget,
			LLMRequestGenerationPolicy policy) {
		LLMChatRequestResources resources;
		String source;
		if (state.getTokensSize() < budget) {
			resources = state.createChatRequestResources(policy);
			source = "full state";
		} else if (shrinked.getTokensSize() < budget) {
			resources = shrinked.createChatRequestResources(policy);
			source = "compact state";
		} else {
			resources = applyGenerationPolicy(shrinked, budget, policy);
			source = "compact state trimmed by " + policy;
		}
		resources.setAnswerFeedbackNotes(answerFeedbackNotes(request.getUserChatContextCode()));
		resources.setRulesToFollow(rulesToFollow(context));
		resources.setAvailableKnowledgeBaseCodes(availableKnowledgeBaseCodes(request));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Resources for request {} of chat {} from the {}: {} tokens of budget {}, feedback notes:{} rules:{}",
					request.getId(), context.getCode(), source, resources.getTokensSize(), budget,
					resources.getAnswerFeedbackNotes().size(), resources.getRulesToFollow().size());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Request {} feedback notes by request id: {}", request.getId(), resources.getAnswerFeedbackNotes());
			LOGGER.trace("Request {} rules to follow: {}", request.getId(), resources.getRulesToFollow());
		}
		return resources;
	}

	/**
	 * The codes of the knowledge bases of the chat, as its chat profile gives them (see
	 * {@link #getSessionAvailableKnowledgeBases(GeboChatRequest)}), for the tools of the
	 * request: none when the chat has no profile, and when they cannot be read.
	 */
	private List<String> availableKnowledgeBaseCodes(GeboChatRequest request) {
		try {
			final List<GKnowledgeBase> knowledgeBases = getSessionAvailableKnowledgeBases(request);
			final List<String> codes = knowledgeBases != null
					? knowledgeBases.stream().map(GKnowledgeBase::getCode).filter(code -> code != null).distinct()
							.toList()
					: List.of();
			LOGGER.debug("Request {} of chat {}: its tools get {} knowledge base(s)", request.getId(),
					request.getUserChatContextCode(), codes.size());
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Request {} knowledge bases for the tools: {}", request.getId(), codes);
			}
			return codes;
		} catch (GeboChatSessionLifecycleException | RuntimeException e) {
			LOGGER.error("Cannot read the knowledge bases of chat " + request.getUserChatContextCode()
					+ ": the tools of request " + request.getId() + " get none", e);
			return List.of();
		}
	}

	private List<String> rulesToFollow(GUserChatSession context) {
		List<String> rules = new ArrayList<String>();
		int length = 0;
		for (GChatRule rule : rulesService.getApplicableRules(context.getCode(), context.getChatProfileCode(),
				context.getPipelineCode())) {
			if (rules.size() >= MAX_RULES_PER_REQUEST || length + rule.getText().length() > MAX_RULES_LENGTH) {
				LOGGER.debug("Chat {}: rules beyond {} or {} characters are left out", context.getCode(),
						MAX_RULES_PER_REQUEST, MAX_RULES_LENGTH);
				break;
			}
			rules.add(rule.getText());
			length += rule.getText().length();
		}
		return rules;
	}

	private Map<String, String> answerFeedbackNotes(String userChatContextCode) {
		Map<String, String> notes = new HashMap<String, String>();
		for (GChatAnswerFeedback feedback : answerFeedbackRepository.findByUserChatContextCode(userChatContextCode)) {
			boolean negative = feedback.getRating() == ChatAnswerFeedbackRating.NEGATIVE;
			if (negative || feedback.getComment() != null) {
				notes.put(feedback.getRequestId(), "\n\n[The user rated this answer as "
						+ (negative ? "not satisfying" : "good")
						+ (feedback.getComment() != null ? ", commenting: \"" + feedback.getComment() + "\"" : "")
						+ "]");
			}
		}
		return notes;
	}

	private LLMChatRequestResources applyGenerationPolicy(ShrinkedChatSessionState shrinked, int budget,
			LLMRequestGenerationPolicy policy) {
		if (policy == LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET) {
			return shrinked.createChatRequestResources(policy);
		}
		// What does not fit is left out of this request only: the session state keeps it.
		ShrinkedChatSessionState trimmed = shrinked.copyForTrimming();
		int before = trimmed.getTokensSize();
		int removed = 0;
		for (CSSfRelevantShrinkedDocumentList relevant : List.of(trimmed.getRelevantChatWithDocuments(),
				trimmed.getRelevantRetrievedDocuments(), trimmed.getRelevantUploadedDocuments(),
				trimmed.getRelevantLlmGeneratedDocuments())) {
			while (!relevant.isEmpty() && trimmed.getTokensSize() >= budget) {
				CSSRelevantShrinkedDocument dropped = relevant.remove(0);
				removed++;
				if (LOGGER.isTraceEnabled()) {
					LOGGER.trace("Trimming chat {}: left out summarized {} document {} ({} tokens)",
							shrinked.getUserChatContextCode(), dropped.getDocumentOrigin(),
							dropped.getDocumentReference(), dropped.getTokensSize());
				}
			}
		}
		for (CSSReferredContentList<?> latest : List.of(trimmed.getLatestRequestsRetrievedDocuments(),
				trimmed.getLatestRequestsChatWithDocuments(), trimmed.getLatestRequestsUploadedDocuments(),
				trimmed.getLatestRequestsLlmGeneratedDocuments())) {
			while (!latest.getData().isEmpty() && trimmed.getTokensSize() >= budget) {
				if (LOGGER.isTraceEnabled()) {
					CSSInteractionReferredContent<?> dropped = latest.getData().get(0);
					LOGGER.trace("Trimming chat {}: left out document {} of interaction {} ({} tokens)",
							shrinked.getUserChatContextCode(),
							dropped.getAiDocument() != null ? dropped.getAiDocument().getCode() : null,
							dropped.getInteractionIndex(), dropped.getTokensSize());
				}
				latest.getData().remove(0);
				removed++;
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Trimmed chat {} from {} to {} tokens (budget {}) leaving out {} documents",
					shrinked.getUserChatContextCode(), before, trimmed.getTokensSize(), budget, removed);
		}
		return trimmed.createChatRequestResources(policy);
	}

	private static int currentInteractionIndex(GUserChatSession context, GeboChatRequest request) {
		List<ChatInteractions> interactions = context.getInteractions();
		if (interactions == null || interactions.isEmpty()) {
			return 0;
		}
		for (int i = interactions.size() - 1; i >= 0; i--) {
			GeboChatRequest r = interactions.get(i).getRequest();
			if (r != null && r.getId() != null && r.getId().equals(request.getId())) {
				return i;
			}
		}
		return interactions.size() - 1;
	}

	private int getTokensBudget(IGConfigurableChatModel targetChatModel) {
		double contextWindow = targetChatModel.getContextLength();
		double maximumTokenBudget = lifeCycleConfig.getMaximumContextWindowFullFillCoeff() * contextWindow;
		if (lifeCycleConfig.getMaximumContextWindowTokenUsed() != null
				&& maximumTokenBudget > lifeCycleConfig.getMaximumContextWindowTokenUsed().doubleValue()) {
			maximumTokenBudget = lifeCycleConfig.getMaximumContextWindowTokenUsed().doubleValue();
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Token budget for model {} ({} context): {}", targetChatModel.getCode(), contextWindow,
					(int) maximumTokenBudget);
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
		int index = currentInteractionIndex(context, request);

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

		return budgetedResources(request, context, state, shrinked, budget, policy);

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

		return budgetedResources(request, context, state, shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources addChatWithDocument(GeboChatRequest request, GDocumentReference reference,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		AIDocumentReferenceItem data = null;
		int index = currentInteractionIndex(context, request);

		try {
			data = this.documentsCacheService.retrieve(reference);
		} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
				| GeboIngestionException | DocumentContentStreamerException e) {
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

		return budgetedResources(request, context, state, shrinked, budget, policy);
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

		return budgetedResources(request, context, state, shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources addRetrievedDocuments(GeboChatRequest request, AIDocumentsSet retrieved,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		int index = currentInteractionIndex(context, request);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Adding {} retrieved documents to interaction {} of chat {}",
					retrieved != null ? retrieved.getDocumentItems().size() : 0, index, context.getCode());
		}
		state = this.fullSessionStateService.addRetrievedDocumentsToState(state, retrieved, index);

		shrinked = this.shrinkedSessionStateService.addRetrievedDocumentsToState(shrinked, retrieved, index);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}

		return budgetedResources(request, context, state, shrinked, budget, policy);
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

		return budgetedResources(request, context, state, shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources addLLMGenerated(GeboChatRequest request, LLMGeneratedResource resource,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		int index = currentInteractionIndex(context, request);

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

			return budgetedResources(request, context, state, shrinked, budget, policy);
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
		int index = currentInteractionIndex(context, request);

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
		// a chat title asked for while this request was answered can now be written
		synchronized (INTERACTION_SAVED) {
			INTERACTION_SAVED.notifyAll();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"endRequest chat:{} request:{} saved interaction {}: generated resources:{} history full:{} compact:{} entries, full:{} compact:{} tokens, toBeShrinked:{}; {} requests in progress",
					context.getCode(), request.getId(), currentInteractionIndex(context, request),
					generated != null ? generated.size() : 0,
					state.getChatHistory().getValue().getInteractions().size(),
					shrinked.getChatHistory().getLatestEntries().getInteractions().size(), state.getTokensSize(),
					shrinked.getTokensSize(), shrinked.isToBeShrinked(), cache.size());
		}
	}

	@Override
	public void chatRequestCompleted(GeboChatRequest request, IGConfigurableChatModel targetChatModel)
			throws GeboChatSessionLifecycleException, LLMConfigException, IOException {

		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService
				.retrieveState(request.getUserChatContextCode());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("chatRequestCompleted chat:{} request:{} compact:{} tokens toBeShrinked:{}",
					request.getUserChatContextCode(), request.getId(), shrinked != null ? shrinked.getTokensSize() : null,
					shrinked != null && shrinked.isToBeShrinked());
		}
		if (shrinked != null && shrinked.isToBeShrinked()) {
			requestShrink(request.getUserChatContextCode(), getTargetShrinkResize(targetChatModel));
		}
	}

	private void requestShrink(String sessionCode, int tokensBudget) {
		LOGGER.debug("Queueing the shrink of chat {} to {} tokens", sessionCode, tokensBudget);
		SessionShrinkRequestPayload checkPayload = new SessionShrinkRequestPayload();
		checkPayload.setTokensBudget(tokensBudget);
		checkPayload.setUserChatSessionCode(sessionCode);
		GMessageEnvelope<SessionShrinkRequestPayload> envelope = envelopeFactory.newMessageFrom(this, checkPayload);
		envelope.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
		envelope.setTargetComponent(SessionShrinkMessagesReceiver.SESSION_SHRINKER);
		this.broker.accept(envelope);
	}

	@Override
	public GUserChatInfo branchChatSession(String sessionCode, String requestId)
			throws GeboChatSessionLifecycleException, GeboPersistenceException {
		GUserChatSession source = get(sessionCode);
		List<ChatInteractions> interactions = source.getInteractions() != null ? source.getInteractions()
				: List.of();
		int branchIndex = -1;
		for (int i = 0; i < interactions.size() && branchIndex < 0; i++) {
			GeboChatRequest r = interactions.get(i).getRequest();
			if (r != null && requestId != null && requestId.equals(r.getId())) {
				branchIndex = i;
			}
		}
		if (branchIndex < 0) {
			throw new GeboChatSessionLifecycleException(
					"Request " + requestId + " is not part of the chat " + sessionCode);
		}
		ChatFullSessionState sourceFull = retrieveAndCheck(sessionCode);

		GUserChatSession branch = new GUserChatSession();
		branch.setUsername(securityService.getCurrentUser().getUsername());
		branch.setDescription((source.getDescription() != null ? source.getDescription() : "Chat") + " (branch)");
		branch.setChatCreationDateTime(new Date());
		branch.setContextCode(source.getContextCode());
		branch.setChatProfileCode(source.getChatProfileCode());
		branch.setModelReference(source.getModelReference());
		branch.setRagChat(source.getRagChat());
		branch.setPipelineCode(source.getPipelineCode());
		branch.setChatModelCode(source.getChatModelCode());
		branch.setChoosedKnowledgeBases(source.getChoosedKnowledgeBases());
		branch.setInteractions(new ArrayList<>(interactions.subList(0, branchIndex + 1)));
		branch = persistenceManager.insert(branch);
		String branchCode = branch.getCode();
		for (ChatInteractions interaction : branch.getInteractions()) {
			if (interaction.getRequest() != null) {
				interaction.getRequest().setUserChatContextCode(branchCode);
			}
			if (interaction.getResponse() != null) {
				interaction.getResponse().setUserChatContextCode(branchCode);
			}
		}
		sessionRepository.save(branch);

		ChatFullSessionState full = new ChatFullSessionState();
		full.setUserChatContextCode(branchCode);
		full.getChatHistory().getValue().getInteractions()
				.addAll(historyUpTo(sourceFull.getChatHistory().getValue().getInteractions(), requestId, branchIndex));
		copyUpTo(sourceFull.getChatWithDocuments().getValue(), full.getChatWithDocuments().getValue(), branchIndex);
		copyUpTo(sourceFull.getRetrievedDocuments().getValue(), full.getRetrievedDocuments().getValue(), branchIndex);
		copyUpTo(sourceFull.getUploadedDocuments().getValue(), full.getUploadedDocuments().getValue(), branchIndex);
		copyUpTo(sourceFull.getLlmGeneratedDocuments().getValue(), full.getLlmGeneratedDocuments().getValue(),
				branchIndex);
		this.fullSessionStateService.save(full);

		// The source summaries may cover exchanges after the branch point: start unsummarized.
		ShrinkedChatSessionState shrinked = new ShrinkedChatSessionState();
		shrinked.setUserChatContextCode(branchCode);
		for (CSSSimplefiedInteraction entry : full.getChatHistory().getValue().getInteractions()) {
			shrinked.getChatHistory().getLatestEntries().getInteractions().add((CSSSimplefiedInteraction) entry.clone());
		}
		shrinked.setLatestRequestsChatWithDocuments(new CSSReferredContentList<>(full.getChatWithDocuments().getValue()));
		shrinked.setLatestRequestsRetrievedDocuments(new CSSReferredContentList<>(full.getRetrievedDocuments().getValue()));
		shrinked.setLatestRequestsUploadedDocuments(new CSSReferredContentList<>(full.getUploadedDocuments().getValue()));
		shrinked.setLatestRequestsLlmGeneratedDocuments(
				new CSSReferredContentList<>(full.getLlmGeneratedDocuments().getValue()));
		GeboChatRequest branchRequest = new GeboChatRequest();
		branchRequest.setUserChatContextCode(branchCode);
		IGConfigurableChatModel model = getSessionChatModel(branchRequest);
		if (model != null && shrinked.getTokensSize() >= getTokensBudget(model)) {
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(getTargetShrinkResize(model));
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (shrinked.isToBeShrinked()) {
			requestShrink(branchCode, shrinked.getTargetTokenBudget());
		}
		int copiedRules = rulesService.copyChatRules(sessionCode, branchCode).size();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"Branched chat {} at request {} (interaction {}) into {}: {} interactions, {} history entries, {} documents, {} rules, {} tokens, shrink queued:{}",
					sessionCode, requestId, branchIndex, branchCode, branch.getInteractions().size(),
					full.getChatHistory().getValue().getInteractions().size(),
					full.getChatWithDocuments().getValue().getData().size()
							+ full.getRetrievedDocuments().getValue().getData().size()
							+ full.getUploadedDocuments().getValue().getData().size()
							+ full.getLlmGeneratedDocuments().getValue().getData().size(),
					copiedRules, shrinked.getTokensSize(), shrinked.isToBeShrinked());
		}
		return new GUserChatInfoData(branch);
	}

	private static List<CSSSimplefiedInteraction> historyUpTo(List<CSSSimplefiedInteraction> history,
			String requestId, int branchIndex) {
		int last = -1;
		for (int i = 0; i < history.size(); i++) {
			if (requestId.equals(history.get(i).getRequestId())) {
				last = i;
			}
		}
		if (last < 0) {
			last = Math.min(branchIndex, history.size() - 1);
		}
		List<CSSSimplefiedInteraction> out = new ArrayList<>();
		for (int i = 0; i <= last; i++) {
			out.add((CSSSimplefiedInteraction) history.get(i).clone());
		}
		return out;
	}

	private static <T> void copyUpTo(CSSReferredContentList<T> from, CSSReferredContentList<T> to, int branchIndex) {
		if (from != null) {
			from.getData().stream().filter(x -> x.getInteractionIndex() <= branchIndex).forEach(x -> to.getData().add(x));
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
		GUserChatSession context = session(request);
		ChatFullSessionState full = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		boolean addInteraction = false;
		Optional<ChatInteractions> alredyInInteraction = context.getInteractions().stream()
				.filter(x -> x.getRequest().getId().equals(request.getId())).findFirst();
		addInteraction = alredyInInteraction.isEmpty();
		ChatInteractions interaction = addInteraction ? new ChatInteractions() : alredyInInteraction.get();
		interaction.setRequest(request);
		interaction.setRequestNTokens(ITokensCountable.stringsTokensSize(request.getQuery()));

		if (addInteraction)
			context.getInteractions().add(interaction);

		if (shrinked.getCurrentRequest() != null && request.getId() != null
				&& shrinked.getCurrentRequest().getId().equals(request.getId())) {
			shrinked.setCurrentRequest(request);
		} else
			throw new GeboChatSessionLifecycleException(
					"Logical problem, the request id to update does not match the actual one");
		if (full.getCurrentRequest().getValue() != null && full.getCurrentRequest().getValue().getId() != null
				&& request.getId() != null && full.getCurrentRequest().getValue().getId().equals(request.getId())) {
			full.getCurrentRequest().setValue(request);
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
	public GChatProfileConfiguration getSessionChatProfile(GeboChatRequest request)
			throws GeboChatSessionLifecycleException {
		GUserChatSession session = get(request.getUserChatContextCode());
		GChatProfileConfiguration profile = session != null && session.getChatProfileCode() != null
				? this.chatProfilesRepository.findById(session.getChatProfileCode()).orElse(null)
				: null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getSessionChatProfile(...) session:" + request.getUserChatContextCode() + " profile:"
					+ (profile != null ? profile.getCode() : null));
		}
		return profile;
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
	public GUserChatInfo createCleanChatByModelCode(String modelCode, String pipelineCode)
			throws GeboPersistenceException {
		IGConfigurableChatModel model = this.chatModelsDao.findByCode(modelCode);
		if (model != null)
			return createCleanChatByModel(model, pipelineCode);
		throw new IllegalStateException("The model :" + modelCode + " does not exist");
	}

	@Override
	public GUserChatInfo createCleanChatByDefaultModel(String pipelineCode) throws GeboPersistenceException {
		IGConfigurableChatModel model = this.chatModelsDao.defaultHandler();
		if (model == null) {
			throw new IllegalStateException("No default chat model is configured");
		}
		return createCleanChatByModel(model, pipelineCode);
	}

	@Override
	public GUserChatInfo createCleanChatByModel(IGConfigurableChatModel chatModel, String pipelineCode)
			throws GeboPersistenceException {
		UserInfos user = securityService.getCurrentUser();
		GUserChatSession userContext = new GUserChatSession();
		userContext.setChatModelCode(chatModel.getCode());
		userContext.setPipelineCode(pipelineCode != null && !pipelineCode.isBlank() ? pipelineCode : null);
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
	public GUserChatInfo createCleanChatByChatProfileCode(String chatProfileCode, String contextCode,
			String pipelineCode) throws GeboPersistenceException {
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
			userContext.setPipelineCode(pipelineCode != null && !pipelineCode.isBlank() ? pipelineCode : null);
			userContext.setContextCode(contextCode != null && !contextCode.isBlank() ? contextCode : null);
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
		GUserChatSession context = waitForFirstInteraction(id);

		data = new GUserChatInfoData(context);
		GPromptTemplateConfig prompt = this.promptsDao.findByPromptUse(GeboPromptsLibrary.SUMMARIZE_CHAT_DESCRIPTION);
		IGConfigurableChatModel handler = chatModelsDao.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		try {
			if (handler != null && context.getInteractions() != null && !context.getInteractions().isEmpty()) {
				IChatRequestContext ccontext = context.createChatRequestContext();
				String content = handler.textResponse(prompt, cache, ccontext);
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
		if (context.getInteractions() == null || context.getInteractions().isEmpty()) {
			LOGGER.warn("No chat title suggested for chat:" + id + ": it has no saved interaction");
		}
		return data;
	}

	/**
	 * The chat session once it has a saved interaction to name the chat after. The
	 * user interface asks for the title as soon as the first answer is streamed, which
	 * can be before the handler that streamed it ends the request and saves the
	 * interaction: the session is re-read, when a request ends here or every
	 * {@value #TITLE_INTERACTION_POLL_MILLIS} ms, for at most
	 * {@value #TITLE_INTERACTION_WAIT_MILLIS} ms.
	 */
	private GUserChatSession waitForFirstInteraction(String id) throws GeboChatSessionLifecycleException {
		GUserChatSession context = get(id);
		final long deadline = System.currentTimeMillis() + TITLE_INTERACTION_WAIT_MILLIS;
		long remaining = TITLE_INTERACTION_WAIT_MILLIS;
		if (LOGGER.isDebugEnabled() && (context.getInteractions() == null || context.getInteractions().isEmpty())) {
			LOGGER.debug("Chat title of chat:" + id + " waits for its first interaction to be saved");
		}
		while ((context.getInteractions() == null || context.getInteractions().isEmpty()) && remaining > 0) {
			try {
				synchronized (INTERACTION_SAVED) {
					INTERACTION_SAVED.wait(Math.min(remaining, TITLE_INTERACTION_POLL_MILLIS));
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
			context = get(id);
			remaining = deadline - System.currentTimeMillis();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Chat title of chat:" + id + " waited "
					+ (TITLE_INTERACTION_WAIT_MILLIS - Math.max(0, remaining)) + " ms, interactions:"
					+ (context.getInteractions() != null ? context.getInteractions().size() : 0));
		}
		return context;
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

	@Override
	public MinimalChatContext getMinimalChatContext(GeboChatRequest request, int tokensBudget)
			throws GeboChatSessionLifecycleException {
		GUserChatSession context = session(request);
		ChatFullSessionState state = full(request);
		ShrinkedChatSessionState shrinked = shrink(request);
		CSSConsolidatedChatHistory history = shrinked.getChatHistory();
		MinimalChatContext mc = new MinimalChatContext();
		mc.setChatHistory(history);
		mc.setCurrentRequest(request);
		final List<String> knowledgeBaseCodes = availableKnowledgeBaseCodes(request);
		mc.setAvailableKnowledgeBaseCodes(knowledgeBaseCodes);
		if (tokensBudget >= mc.getTokensSize()) {
			LOGGER.debug("Minimal context of chat {} fits as is: {} tokens of {}", request.getUserChatContextCode(),
					mc.getTokensSize(), tokensBudget);
			return mc;
		}
		LOGGER.debug("Minimal context of chat {} needs shrinking: {} tokens of {}", request.getUserChatContextCode(),
				mc.getTokensSize(), tokensBudget);

		try {
			mc = this.shrinkerService.shrinkedMinimalContext(request.getUserChatContextCode(), mc, tokensBudget);
			mc.setCurrentRequest(request);
			mc.setAvailableKnowledgeBaseCodes(knowledgeBaseCodes);
			return mc;
		} catch (LLMConfigException | IOException e) {
			throw new GeboChatSessionLifecycleException("Error shrinking state", e);
		}
	}

	protected List<IGConfigurableEmbeddingModel> getEmbeddingModelsListByKnowledgeBases(
			List<GKnowledgeBase> knowledgeBases) {
		return this.getEmbeddingModelsListByKnowledgeBases(knowledgeBases, true);
	}

	protected List<IGConfigurableEmbeddingModel> getEmbeddingModelsListByKnowledgeBases(
			List<GKnowledgeBase> knowledgeBases, boolean includeDefaultEmbeddingModel) {
		List<IGConfigurableEmbeddingModel> embeddingModels = new ArrayList<IGConfigurableEmbeddingModel>();
		IGConfigurableEmbeddingModel defaultEmbeddingModel = embeddingModelsRuntimeDao.defaultHandler();
		if (defaultEmbeddingModel != null && includeDefaultEmbeddingModel) {
			embeddingModels.add(defaultEmbeddingModel);
		}

		knowledgeBases.stream().map(x -> x.getEmbeddingModelReferences()).filter(y -> y != null && !y.isEmpty())
				.forEach(modelsList -> {
					modelsList.forEach(modelReference -> {
						IGConfigurableEmbeddingModel model = embeddingModelsRuntimeDao
								.findByModelReference(modelReference);
						if (model != null && model != defaultEmbeddingModel) {
							if (!embeddingModels.stream()
									.anyMatch(x -> (x == model || model.getCode().equals(x.getCode())))) {
								embeddingModels.add(model);
							}
						}
					});
				});
		return embeddingModels;
	}

	@Override
	public List<IGConfigurableEmbeddingModel> getSessionEmbeddingModels(GeboChatRequest request)
			throws GeboChatSessionLifecycleException {
		List<GKnowledgeBase> knowledgeBases = getSessionAvailableKnowledgeBases(request);
		List<IGConfigurableEmbeddingModel> embeddingModels = getEmbeddingModelsListByKnowledgeBases(knowledgeBases,
				true);

		return embeddingModels;
	}
}
