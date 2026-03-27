package ai.gebo.llms.deepsearch.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
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
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceDocumentResultEvent;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
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
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.llms.deepsearch.service.ReactiveMonitor;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
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
	protected final IGChatSessionLifeCycleService sessionLifecycleService;
	protected final IGDeepSearchConfigProvider configProvider;
	protected final IGeboThreadManager threadManager;
	private static final String ERROR_WHILE_RUNNING_DEEP_SEARCH = "Error while running deep search";
	final ChatProfilesRepository chatProfilesRepository;

	private final Scheduler deepSearchScheduler;
	private final ExecutorService deepSearchExecutor;
	private final ConcurrentHashMap<String, Sinks.One<Void>> activeSearchSignals = new ConcurrentHashMap<>();

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

			IGChatSessionLifeCycleService sessionLifecyCleService, IGDeepSearchConfigProvider configProvider) {
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
		this.sessionLifecycleService = sessionLifecyCleService;
		this.configProvider = configProvider;
	}

	@PreDestroy
	public void shutdown() {
		deepSearchExecutor.shutdown();
	}

	protected Flux<AbstractDeepSearchEvent> executeStreamDeepSearch(final ReactiveIdentityUtil runAs,
			DeepSearchRequest request, MinimalChatContext minimalChatContext, AIDocumentsSet allDocuments,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws LLMConfigException, GeboChatSessionLifecycleException {
		final List<GKnowledgeBase> knowledgeBases = this.sessionLifecycleService
				.getSessionAvailableKnowledgeBases(minimalChatContext.getCurrentRequest());
		final List<IGConfigurableEmbeddingModel> embeddingModels = this.sessionLifecycleService
				.getSessionEmbeddingModels(minimalChatContext.getCurrentRequest());
		final UserInfos userInfos = securityService.getCurrentUser();
		request.setUsername(userInfos.getUsername());
		final DeepSearchConfig configuration = this.configProvider.get();
		requestsRepository.save(request);
		return Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				final IDocumentsChunkService chunkService = runtimeBinder
						.getImplementationOf(IDocumentsChunkService.class);
				final String chunkSessionId = chunkService.createChunkingSession("deepsearch:" + request.getCode());
				final FullReactiveDeepsearchWorker worker = runtimeBinder
						.getImplementationOf(FullReactiveDeepsearchWorker.class);

				Flux<AbstractDeepSearchEvent> flow;
				try {
					flow = worker.streamDeepSearch(request, minimalChatContext, allDocuments, new ArrayList<>(),
							configuration, userInfos, embeddingModels, chatModel, serviceModel, deepSearchScheduler,
							chunkSessionId);
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
								runAs.doAsWithException(() -> {
									chunkService.disposeChunkingSession(chunkSessionId);
								});
							} catch (Throwable th) {
								LOGGER.error("Exception disposing", th);
							}
						}
					};
					flow.doAfterTerminate(deleteChunkingSessionRunnable);
				}
				return flow;
			});
		}).subscribeOn(deepSearchScheduler);

	}

	@Override
	public Flux<AbstractDeepSearchEvent> streamDeepSearch(DeepSearchRequest request)
			throws LLMConfigException, GeboChatSessionLifecycleException {
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		Flux<AbstractDeepSearchEvent> out = null;

		final DeepSearchConfig data = configRepository.findByDefaultConfig(true);
		final DeepSearchConfig configuration = data != null ? data : defaultDeepsearchConfig;
		IGConfigurableChatModel chatModel = getChatModel(null);
		IGConfigurableChatModel serviceModel = this.chatModelsConfigDao
				.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		out = this.executeStreamDeepSearch(runAs, request, new MinimalChatContext(), new AIDocumentsSet(),
				chatModel, serviceModel);

		return out.publishOn(deepSearchScheduler).doOnNext(evt -> persistSideEffects(runAs, evt))
				.doOnError(err -> LOGGER.error("DeepSearch stream error", err));
	}

	public void persistSideEffects(ReactiveIdentityUtil runAs, AbstractDeepSearchEvent step) {
		runAs.doAs(() -> {
			if (step instanceof DeepSearchDataSourceDocumentResultEvent dsDocumentEvent) {
				if (dsDocumentEvent.getOutputData() != null
						&& dsDocumentEvent.getOutputData().getAnalisysResult() != null
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
		});
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
	public Flux<AbstractDeepSearchEvent> streamDeepSearch(GeboChatRequest request)
			throws LLMConfigException, GeboChatSessionLifecycleException, GeboPersistenceException, IOException {
		if (request.getId() == null) {
			request.setId(UUID.randomUUID().toString());
		}
		// TODO: COMPLETE THIS, IT DOES NOT SELECT PROPERLY EVENTUAL DATA SOURCES
		sessionLifecycleService.ensureChatSessionExists(request);
		IGConfigurableChatModel model = sessionLifecycleService.getSessionChatModel(request);
		IGConfigurableChatModel serviceModel = this.chatModelsConfigDao
				.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		GeboChatResponse cleanResponse = sessionLifecycleService.createEmptyResponse(request);
		LLMChatRequestResources llmRequest = sessionLifecycleService.startRequest(request, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
		List<GKnowledgeBase> kbList = sessionLifecycleService.getSessionAvailableKnowledgeBases(request);
		List<String> knowledgeBasesCodesList = kbList.stream().map(x -> x.getCode()).toList();

		Sinks.One<Void> stopSignal = Sinks.one();
		activeSearchSignals.put(request.getId(), stopSignal);

		MinimalChatContext minimalChatContext = this.sessionLifecycleService.getMinimalChatContext(request,
				serviceModel.getContextLength() / 3);
		Flux<AbstractDeepSearchEvent> outflux = streamDeepSearch(llmRequest, minimalChatContext, cleanResponse, model,
				serviceModel, List.of());
		return outflux;
	}

	public Flux<AbstractDeepSearchEvent> manageTrailingChatSessionEvents(ReactiveIdentityUtil runAs,
			Flux<AbstractDeepSearchEvent> flux, GeboChatRequest request, GeboChatResponse response) {
		final List<GResponseDocumentRef> documents = new ArrayList<GResponseDocumentRef>();

		Mono<AbstractDeepSearchEvent> trailingFlux = Mono.fromSupplier(() -> {
			return runAs.doRunAsWithReturn(() -> {
				response.setDocumentsRef(documents);
				DeepSearchChatResponseEvent responseEvent = new DeepSearchChatResponseEvent();
				responseEvent.setInputData(request);
				responseEvent.setOutputData(response);
				return responseEvent;
			});
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

				try {
					runAs.doAsWithException(() -> {
						this.sessionLifecycleService.endRequest(request, response);
					});

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
			MinimalChatContext minimalChatContext, GeboChatResponse chatResponse, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, List<String> deepSearchDataSources)
			throws LLMConfigException, GeboChatSessionLifecycleException {
		DeepSearchRequest deepSearchRequest = new DeepSearchRequest();
		deepSearchRequest.setChatRequestCode(request.getCurrentRequest().getId());
		deepSearchRequest.setKnowledgeBases(createKnowledgeBasesList(request));
		deepSearchRequest.setQuery(GeboChatRequest.actualQuery(request.getCurrentRequest()));
		deepSearchRequest.setUserChatContextCode(request.getCurrentRequest().getUserChatContextCode());
		deepSearchRequest.setDeepSearchDataSources(deepSearchDataSources);
		deepSearchRequest.setUserIntent(request.getCurrentRequest().getUserIntent());
		chatResponse.setDeepSearchRequestId(deepSearchRequest.getCode());
		AIDocumentsSet allDocuments = request.allDocuments();
		Flux<AbstractDeepSearchEvent> out = null;
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		out = this.executeStreamDeepSearch(runAs, deepSearchRequest, minimalChatContext, allDocuments, chatModel,
				serviceModel);

		Flux<AbstractDeepSearchEvent> outflux = manageTrailingChatSessionEvents(runAs, out, request.getCurrentRequest(),
				chatResponse);
		return outflux.publishOn(deepSearchScheduler).doOnNext(evt -> persistSideEffects(runAs, evt))
				.onErrorResume(Common.commonFallBack(deepSearchRequest));
	}

	private List<String> createKnowledgeBasesList(LLMChatRequestResources request)
			throws GeboChatSessionLifecycleException {
		List<GKnowledgeBase> visibles = this.sessionLifecycleService
				.getSessionAvailableKnowledgeBases(request.getCurrentRequest());

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

	@Override
	public void stopDeepSearch(String deepSearchRequestId) {
		Sinks.One<Void> signal = activeSearchSignals.get(deepSearchRequestId);
		if (signal != null) {
			signal.tryEmitValue(null);
			LOGGER.info("Deep search stopped by user: " + deepSearchRequestId);
		}
	}

}
