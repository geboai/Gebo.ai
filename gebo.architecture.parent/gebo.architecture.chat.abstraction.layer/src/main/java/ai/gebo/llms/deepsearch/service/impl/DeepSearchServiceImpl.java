package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.config.DeepSearchVariant;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceDocumentResultEvent;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.DeepSearchUISettings;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchDocumentEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchErrorEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.repository.DeepSearchConfigRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchDataSourceDocumentResultRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchDataSourceResponseRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchDocumentAnalisysResultStepRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchRequestRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchResponseRepository;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.llms.deepsearch.service.ReactiveMonitor;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Component
@Scope("singleton")

public class DeepSearchServiceImpl extends BaseLLMSInvokingAndProvidingService implements IGDeepSearchService {

	static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchServiceImpl.class);
	protected final DeepSearchDefaultConfig defaultDeepsearchConfig;
	protected final DeepSearchConfigRepository configRepository;
	protected final IGRuntimeBinder runtimeBinder;
	protected final IGSecurityService securityService;
	protected final DeepSearchRequestRepository requestsRepository;
	protected final DeepSearchDocumentAnalisysResultStepRepository stepsRepository;
	protected final DeepSearchResponseRepository responseRepository;
	protected final DeepSearchDataSourceDocumentResultRepository dataSourceDocumentResultRepository;
	protected final DeepSearchDataSourceResponseRepository dataSourceResponseRepository;
	protected final GUserChatSessionRepository userChatContextRepository;
	protected final KnowledgeBaseRepository knowledgeBaseRepository;
	protected final IGRagChatService ragChatService;
	protected final IGChatService chatService;
	protected final IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;
	protected final IGChatSessionLifeCycleService sessionLifecyCleService;
	protected final IGeboThreadManager threadManager;
	private static final String ERROR_WHILE_RUNNING_DEEP_SEARCH = "Error while running deep search";
	final ChatProfilesRepository chatProfilesRepository;

	private final Scheduler deepSearchScheduler;
	private final ExecutorService deepSearchExecutor;

	public DeepSearchServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			DeepSearchDefaultConfig defaultDeepsearchConfig, DeepSearchConfigRepository configRepository,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService,
			DeepSearchRequestRepository requestsRepository,
			DeepSearchDocumentAnalisysResultStepRepository stepsRepository,
			DeepSearchResponseRepository responseRepository, GUserChatSessionRepository userChatContextRepository,
			KnowledgeBaseRepository knowledgeBaseRepository, IGRagChatService ragChatService, IGChatService chatService,
			IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			DeepSearchDataSourceDocumentResultRepository dataSourceDocumentResultRepository,
			DeepSearchDataSourceResponseRepository dataSourceResponseRepository,
			ChatProfilesRepository chatProfilesRepository, IGeboThreadManager threadManager,

			IGChatSessionLifeCycleService sessionLifecyCleService) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.knowledgeBaseVisibilityService = knowledgeBaseVisibilityService;
		this.chatProfilesRepository = chatProfilesRepository;
		this.chatService = chatService;
		this.configRepository = configRepository;
		this.defaultDeepsearchConfig = defaultDeepsearchConfig;
		this.knowledgeBaseRepository = knowledgeBaseRepository;
		this.runtimeBinder = runtimeBinder;
		this.securityService = securityService;
		this.requestsRepository = requestsRepository;
		this.stepsRepository = stepsRepository;
		this.responseRepository = responseRepository;
		this.userChatContextRepository = userChatContextRepository;
		this.ragChatService = ragChatService;
		this.dataSourceResponseRepository = dataSourceResponseRepository;
		this.dataSourceDocumentResultRepository = dataSourceDocumentResultRepository;
		this.threadManager = threadManager;

		this.deepSearchExecutor = threadManager.getExecutorService();

		this.deepSearchScheduler = threadManager.getBoundedElastic();

		this.sessionLifecyCleService = sessionLifecyCleService;
	}

	@PreDestroy
	public void shutdown() {
		deepSearchExecutor.shutdown();
	}

	protected Flux<AbstractDeepSearchEvent> fullReactivestreamDeepSearch(DeepSearchRequest request,
			AIDocumentsSet allDocuments, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws LLMConfigException {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return Mono.fromCallable(() -> {

			var sc = SecurityContextHolder.createEmptyContext();
			sc.setAuthentication(auth);
			SecurityContextHolder.setContext(sc);

			try {
				final UserInfos userInfos = securityService.getCurrentUser();
				request.setUsername(userInfos.getUsername());
				requestsRepository.save(request);

				final DeepSearchConfig stored = configRepository.findByDefaultConfig(true);
				final DeepSearchConfig configuration = stored != null ? stored : defaultDeepsearchConfig;

				final List<GKnowledgeBase> knowledgeBases = knowledgeBaseRepository
						.findAllById(request.getKnowledgeBases());

				final List<IGConfigurableEmbeddingModel> embeddingModels = getEmbeddingModelsListByKnowledgeBases(
						knowledgeBases);

				if (chatModel == null) {
					return Prepared.error(request, GUserMessage.errorMessage("No chat model defined",
							"No chat model configured for deep search nor default chat model is configured"));
				}

				return Prepared.ok(request, configuration, userInfos, embeddingModels, chatModel, serviceModel, null);

			} finally {
				SecurityContextHolder.clearContext();
			}
		}).subscribeOn(deepSearchScheduler).flatMapMany(prep -> {
			if (prep.errorEvent != null)
				return Flux.just(prep.errorEvent);
			final IDocumentsChunkService chunkService = runtimeBinder.getImplementationOf(IDocumentsChunkService.class);
			final String chunkSessionId = chunkService.createChunkingSession("deepsearch:" + request.getCode());
			final FullReactiveDeepsearchWorker worker = runtimeBinder
					.getImplementationOf(FullReactiveDeepsearchWorker.class);

			Flux<AbstractDeepSearchEvent> flow;
			try {
				flow = worker.streamDeepSearch(prep.request, allDocuments, new ArrayList<>(), new DeepSearchState(),
						prep.configuration, prep.userInfos, prep.embeddingModels, prep.chatModel, prep.serviceModel,
						deepSearchScheduler, chunkSessionId);
				if (flow != null) {
					flow = flow.transform(ReactiveMonitor.monitor("deep-search"));
				}
			} catch (Throwable e) {
				DeepSearchErrorEvent errorEvent = new DeepSearchErrorEvent();
				errorEvent.setInputData(request);
				errorEvent.setOutputData(GUserMessage.errorMessage("Error doing deep search", e));
				flow = Flux.just(errorEvent);
			}
			if (chunkSessionId != null && flow != null) {
				Runnable deleteChunkingSessionRunnable = new Runnable() {
					@Override
					public void run() {
						try {
							chunkService.disposeChunkingSession(chunkSessionId);
						} catch (Throwable th) {
							LOGGER.error("Exception disposing", th);
						}
					}
				};
				flow.doAfterTerminate(deleteChunkingSessionRunnable);
			}
			return flow;
		});
	}

	@Override
	public Flux<AbstractDeepSearchEvent> streamDeepSearch(DeepSearchRequest request) throws LLMConfigException {
		DeepSearchVariant variant = defaultDeepsearchConfig.getUsedVariant() != null
				? defaultDeepsearchConfig.getUsedVariant()
				: DeepSearchVariant.SINGLE_THREAD;
		Flux<AbstractDeepSearchEvent> out = null;

		final DeepSearchConfig data = configRepository.findByDefaultConfig(true);
		final DeepSearchConfig configuration = data != null ? data : defaultDeepsearchConfig;
		IGConfigurableChatModel chatModel = getChatModel(configuration.getChatModelConfiguration());
		IGConfigurableChatModel serviceModel = this.chatModelsConfigDao
				.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		out = this.fullReactivestreamDeepSearch(request, new AIDocumentsSet(), chatModel, serviceModel);

		return out.publishOn(deepSearchScheduler).doOnNext(evt -> persistSideEffects(evt))
				.doOnError(err -> LOGGER.error("DeepSearch stream error", err));
	}

	void persistSideEffects(AbstractDeepSearchEvent step) {
		if (step instanceof DeepSearchDataSourceDocumentResultEvent dsDocumentEvent) {
			if (dsDocumentEvent.getOutputData() != null && dsDocumentEvent.getOutputData().getAnalisysResult() != null
					&& !dsDocumentEvent.getOutputData().getAnalisysResult().trim().isEmpty()
					&& (dsDocumentEvent.getOutputData().getEmptyResult() == null
							|| !dsDocumentEvent.getOutputData().getEmptyResult())) {
				dataSourceDocumentResultRepository.save(dsDocumentEvent.getOutputData());
			}
		}
		if (step instanceof DeepSearchDataSourceProcessedEvent dataSourceProcessedEvent) {
			if (dataSourceProcessedEvent.getOutputData() != null
					&& dataSourceProcessedEvent.getOutputData().getResponse() != null
					&& !dataSourceProcessedEvent.getOutputData().getResponse().trim().isEmpty()
					&& (dataSourceProcessedEvent.getOutputData().getSearchResultsEmpty() == null
							|| !dataSourceProcessedEvent.getOutputData().getSearchResultsEmpty())) {
				dataSourceResponseRepository.save(dataSourceProcessedEvent.getOutputData());
			}
		}
		if (step instanceof DeepSearchDocumentEvent documentEvent) {
			stepsRepository.save(documentEvent.getOutputData());
		}
		if (step instanceof DeepSearchProcessedEvent doneEvent) {
			responseRepository.save(doneEvent.getOutputData());
		}
	}

	private static final class Prepared {
		final DeepSearchRequest request;
		final DeepSearchConfig configuration;
		final UserInfos userInfos;
		final List<IGConfigurableEmbeddingModel> embeddingModels;
		final IGConfigurableChatModel chatModel;
		final IGConfigurableChatModel serviceModel;
		final AbstractDeepSearchEvent errorEvent;

		private Prepared(DeepSearchRequest request, DeepSearchConfig configuration, UserInfos userInfos,
				List<IGConfigurableEmbeddingModel> embeddingModels, IGConfigurableChatModel chatModel,
				IGConfigurableChatModel serviceModel, AbstractDeepSearchEvent errorEvent) {
			this.request = request;
			this.configuration = configuration;
			this.userInfos = userInfos;
			this.embeddingModels = embeddingModels;
			this.chatModel = chatModel;
			this.errorEvent = errorEvent;
			this.serviceModel = serviceModel;
		}

		static Prepared ok(DeepSearchRequest request, DeepSearchConfig configuration, UserInfos userInfos,
				List<IGConfigurableEmbeddingModel> embeddingModels, IGConfigurableChatModel chatModel,
				IGConfigurableChatModel serviceModel, AbstractDeepSearchEvent errorEvent) {
			return new Prepared(request, configuration, userInfos, embeddingModels, chatModel, serviceModel,
					errorEvent);
		}

		static Prepared error(DeepSearchRequest request, GUserMessage msg) {
			DeepSearchErrorEvent e = new DeepSearchErrorEvent();
			e.setInputData(request);
			e.setOutputData(msg);
			return new Prepared(request, null, null, null, null, null, e);
		}
	}

	@Override
	public Page<DeepSearchRequest> myDeepsearchPaged(Pageable pageable) {

		return requestsRepository.findByUsername(securityService.getCurrentUser().getUsername(), pageable);
	}

	@Override
	public List<DeepSearchRequest> allMyDeepsearches() {

		return requestsRepository.findByUsername(securityService.getCurrentUser().getUsername());
	}

	@Override
	public Page<DeepSearchDocumentAnalisysResultStep> analisysDetailsPaged(String deepSearchCode, Pageable pageable) {
		findDeepSearchRequest(deepSearchCode);
		return stepsRepository.findByDeepsearchCode(deepSearchCode, pageable);
	}

	@Override
	public List<DeepSearchDocumentAnalisysResultStep> analisysDetails(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		return stepsRepository.findByDeepsearchCode(deepSearchCode);
	}

	@Override
	public DeepSearchResponse findDeepSearchResponse(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		Optional<DeepSearchResponse> result = responseRepository.findByDeepsearchCode(deepSearchCode);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public DeepSearchRequest findDeepSearchRequest(String deepSearchCode) {
		Optional<DeepSearchRequest> data = requestsRepository.findById(deepSearchCode);
		// if (data.isPresent()) {
		// securityService.checkBeingCreator(data.get());
		// }
		return data.isPresent() ? data.get() : null;
	}

	@Override
	@Transactional
	public void deleteDeepSearch(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		requestsRepository.deleteById(deepSearchCode);
		responseRepository.deleteByDeepsearchCode(deepSearchCode);
		dataSourceDocumentResultRepository.deleteByDeepsearchCode(deepSearchCode);
		dataSourceResponseRepository.deleteByDeepsearchCode(deepSearchCode);
		stepsRepository.deleteByDeepsearchCode(deepSearchCode);
	}

	@Override
	public Flux<AbstractDeepSearchEvent> streamDeepSearch(GeboChatRequest request) throws LLMConfigException {
		if (request.getId() == null) {
			request.setId(UUID.randomUUID().toString());
		}
		String userChatContextCode = request.getUserChatContextCode();
		if (userChatContextCode == null || userChatContextCode.trim().length() == 0) {
			throw new IllegalStateException("Cannot handle request without referred user context code");
		}
		Optional<GUserChatSession> chatContextData = userChatContextRepository.findById(userChatContextCode);
		if (chatContextData.isEmpty())
			throw new IllegalStateException("Not existent user chat context");
		final GUserChatSession chatContext = chatContextData.get();
		GeboChatResponse cleanResponse = null;
		final boolean isRag = chatContext.getRagChat() != null && chatContext.getRagChat();
		List<GKnowledgeBase> visibleKnowledgeBases = knowledgeBaseVisibilityService.allVisibleKnowledgebases();
		List<String> userSelectedKnowledgeBases = request.getChoosedKnowledgeBases();
		List<String> knowledgeBasesCodesList = visibleKnowledgeBases.stream()
				.filter(y -> userSelectedKnowledgeBases != null && userSelectedKnowledgeBases.contains(y.getCode()))
				.map(x -> x.getCode()).toList();
		if (chatContext.getChatProfileCode() != null && isRag) {
			Optional<GChatProfileConfiguration> chatProfileOptional = chatProfilesRepository
					.findById(chatContext.getChatProfileCode());
			if (chatProfileOptional.isPresent()) {
				List<String> knowledgebaseCodes = chatProfileOptional.get().getKnowledgeBaseCodes();
				boolean allAccessible = chatProfileOptional.get().getUserChoosesKnowledgeBases() != null
						&& chatProfileOptional.get().getUserChoosesKnowledgeBases();
				if (!allAccessible && knowledgebaseCodes.size() > 0) {
					var filteredvisibles = knowledgeBaseVisibilityService
							.visiblesAndChildKnowledgebases(knowledgebaseCodes);
					if (filteredvisibles.size() > 0) {
						knowledgeBasesCodesList = filteredvisibles.stream().map(x -> x.getCode()).toList();
					}
				}
			}
		}

		if (isRag) {
			cleanResponse = this.ragChatService.createUnprocessedResponse(request);
			cleanResponse.setQueryResponse("");
			this.ragChatService.addChatInteractionToUserContext(request, cleanResponse, chatContext);
		} else {
			cleanResponse = this.chatService.createUnprocessedResponse(request);
			cleanResponse.setQueryResponse("");
			this.chatService.addChatInteractionToUserContext(request, cleanResponse, chatContext);
		}
		Flux<AbstractDeepSearchEvent> outflux = doStream(request, cleanResponse, knowledgeBasesCodesList, chatContext);
		return outflux.publishOn(deepSearchScheduler).doOnNext(evt -> persistSideEffects(evt))
				.doOnError(err -> LOGGER.error("DeepSearch stream error", err));
	}

	private Flux<AbstractDeepSearchEvent> doStream(GeboChatRequest request, GeboChatResponse response,
			List<String> knowledgeBasesCodesList, GUserChatSession chatContext) throws LLMConfigException {

		DeepSearchRequest deepSearchRequest = new DeepSearchRequest();
		deepSearchRequest.setChatRequestCode(request.getId());
		deepSearchRequest.setKnowledgeBases(knowledgeBasesCodesList);
		deepSearchRequest.setQuery(GeboChatRequest.actualQuery(request));
		deepSearchRequest.setUserChatContextCode(chatContext.getCode());
		deepSearchRequest.setDeepSearchDataSources(request.getDeepSearchDataSources());
		response.setDeepSearchRequestId(deepSearchRequest.getCode());
		final List<GResponseDocumentRef> documents = new ArrayList<GResponseDocumentRef>();

		Flux<AbstractDeepSearchEvent> flux = streamDeepSearch(deepSearchRequest);
		return manageTrailingChatSessionEvents(flux, request, response, chatContext);
	}

	Flux<AbstractDeepSearchEvent> manageTrailingChatSessionEvents(Flux<AbstractDeepSearchEvent> flux,
			GeboChatRequest request, GeboChatResponse response, GUserChatSession chatContext) {
		final List<GResponseDocumentRef> documents = new ArrayList<GResponseDocumentRef>();
		final boolean isRag = chatContext.getRagChat() != null && chatContext.getRagChat();
		Mono<AbstractDeepSearchEvent> trailingFlux = Mono.fromSupplier(() -> {
			response.setDocumentsRef(documents);
			DeepSearchChatResponseEvent responseEvent = new DeepSearchChatResponseEvent();
			responseEvent.setInputData(request);
			responseEvent.setOutputData(response);
			return responseEvent;
		});
		return flux.map(x -> {
			if (x instanceof DeepSearchDocumentEvent documentEvent) {
				// for each document add a reference here
				GDocumentReference currentDocument = documentEvent.getInputData();
				if (!documents.stream().anyMatch(y -> y.getDocumentCode().equals(currentDocument.getCode()))) {
					GResponseDocumentRef reference = new GResponseDocumentRef(currentDocument);
					documents.add(reference);
				}
			}
			if (x instanceof DeepSearchProcessedEvent processedEvent) {
				// completing with chat response in the history
				String responseText = processedEvent.getOutputData() != null
						? processedEvent.getOutputData().getResponse()
						: "";
				response.setQueryResponse(responseText);
				if (isRag) {

					this.ragChatService.addChatInteractionToUserContext(request, response, chatContext);
				} else {
					this.chatService.addChatInteractionToUserContext(request, response, chatContext);

				}
				try {
					this.sessionLifecyCleService.addInteractionToState(chatContext, request, response);
				} catch (GeboChatSessionLifecycleException e) {
					LOGGER.error("Exceptin in trailing event", e);
				}
			}
			return x;
		}).concatWith(trailingFlux);
	}

	@Override
	public List<DeepSearchDataSourceDocumentResult> findDataSourceDocumentResults(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		return dataSourceDocumentResultRepository.findByDeepsearchCode(deepSearchCode);
	}

	@Override
	public List<DeepSearchDataSourceResponse> findDataSourceResponses(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		return dataSourceResponseRepository.findByDeepsearchCode(deepSearchCode);
	}

	@Override
	@Transactional
	public void deleteDeepSearchByUserContextCode(String userContextCode) {
		List<DeepSearchRequest> data = requestsRepository.findByUserChatContextCode(userContextCode);
		for (DeepSearchRequest deepSearchRequest : data) {
			deleteDeepSearch(deepSearchRequest.getCode());
		}
	}

	@Override
	public List<GBaseObject> getDeepSearchActiveHandlers() {
		final DeepSearchConfig data = configRepository.findByDefaultConfig(true);
		final DeepSearchConfig configuration = data != null ? data : defaultDeepsearchConfig;
		final FullReactiveDeepsearchWorker worker = runtimeBinder
				.getImplementationOf(FullReactiveDeepsearchWorker.class);
		return worker.getDeepSearchActiveHandlers(configuration);
	}

	@Override
	public DeepSearchUISettings getDeepSearchUISettings() {

		return DeepSearchUISettings.of(defaultDeepsearchConfig);
	}

	@Override
	public Flux<AbstractDeepSearchEvent> streamDeepSearch(LLMChatRequestResources request,
			GeboChatResponse chatResponse, GUserChatSession userChatContext, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, List<String> deepSearchDataSources) throws LLMConfigException {

		DeepSearchVariant variant = defaultDeepsearchConfig.getUsedVariant() != null
				? defaultDeepsearchConfig.getUsedVariant()
				: DeepSearchVariant.SINGLE_THREAD;
		DeepSearchRequest deepSearchRequest = new DeepSearchRequest();
		deepSearchRequest.setChatRequestCode(request.getLastRequest().getId());
		deepSearchRequest.setKnowledgeBases(createKnowledgeBasesList(request, userChatContext));
		deepSearchRequest.setQuery(GeboChatRequest.actualQuery(request.getLastRequest()));
		deepSearchRequest.setUserChatContextCode(userChatContext.getCode());
		deepSearchRequest.setDeepSearchDataSources(deepSearchDataSources);
		chatResponse.setDeepSearchRequestId(deepSearchRequest.getCode());
		AIDocumentsSet allDocuments = request.allDocuments();
		Flux<AbstractDeepSearchEvent> out = null;

		out = this.fullReactivestreamDeepSearch(deepSearchRequest, allDocuments, chatModel, serviceModel);

		Flux<AbstractDeepSearchEvent> outflux = manageTrailingChatSessionEvents(out, request.getLastRequest(),
				chatResponse, userChatContext);
		return outflux.publishOn(deepSearchScheduler).doOnNext(evt -> persistSideEffects(evt))
				.onErrorResume(Common.commonFallBack(deepSearchRequest));
	}

	private List<String> createKnowledgeBasesList(LLMChatRequestResources request, GUserChatSession userChatContext) {
		List<GKnowledgeBase> visibles = List.of();
		if (userChatContext.getChatProfileCode() != null) {
			Optional<GChatProfileConfiguration> chatProfileOpt = chatProfilesRepository
					.findById(userChatContext.getChatProfileCode());

			if (chatProfileOpt.isPresent()) {
				if (chatProfileOpt.get().getUserChoosesKnowledgeBases() != null
						&& chatProfileOpt.get().getUserChoosesKnowledgeBases()) {
					visibles = knowledgeBaseVisibilityService.allVisibleKnowledgebases();
				} else {
					List<String> kbList = chatProfileOpt.get().getKnowledgeBaseCodes();
					if (kbList != null && !kbList.isEmpty()) {
						visibles = knowledgeBaseVisibilityService.visiblesAndChildKnowledgebases(kbList);
					}
				}
			}
		}
		return visibles.stream().map(x -> x.getCode()).toList();
	}

	public Flux<GeboChatMessageEnvelope> mapToChatFlux(Flux<AbstractDeepSearchEvent> flux,
			Class<? extends AbstractDeepSearchEvent> trailingType) {
		return flux.map(entry -> {
			GeboChatMessageEnvelope _envelope = null;
			if (entry instanceof DeepSearchDocumentEvent documentEvent) {
				_envelope = new GeboChatMessageEnvelope(documentEvent.getOutputData());
			} else if (entry instanceof DeepSearchProcessedEvent processedEvent) {
				GeboChatMessageEnvelope envelop = new GeboChatMessageEnvelope(processedEvent.getOutputData());
				_envelope = envelop;

			} else if (entry instanceof DeepSearchErrorEvent errorEvent) {
				GeboChatMessageEnvelope exceptionEnvelope = new GeboChatMessageEnvelope();
				exceptionEnvelope.setContent(errorEvent.getOutputData());
				_envelope = exceptionEnvelope;
			} else if (entry instanceof DeepSearchChatResponseEvent chatResponseEvent) {
				GeboChatMessageEnvelope envelop = new GeboChatMessageEnvelope(chatResponseEvent.getOutputData());
				_envelope = envelop;
			} else {
				GeboChatMessageEnvelope envelop = new GeboChatMessageEnvelope(entry.getOutputData());
				_envelope = envelop;
			}
			_envelope.setLastMessage(trailingType.isAssignableFrom(entry.getClass()));
			return _envelope;
		}).onErrorResume(exc -> {
			GeboChatMessageEnvelope exceptionEnvelope = new GeboChatMessageEnvelope();
			GUserMessage userMessage = GUserMessage.errorMessage(ERROR_WHILE_RUNNING_DEEP_SEARCH, exc);
			exceptionEnvelope.setContent(userMessage);
			exceptionEnvelope.setLastMessage(true);
			return Flux.just(exceptionEnvelope);
		});
	}

	@Override
	public long getDeepSearchDocumentsCount(String deepSearchCode) {

		return this.dataSourceDocumentResultRepository.countByDeepsearchCode(deepSearchCode)
				+ this.dataSourceDocumentResultRepository.countByDeepsearchCode(deepSearchCode);
	}

}
