package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
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
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.FluxSink.OverflowStrategy;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Component
@Scope("singleton")

public class DeepSearchServiceImpl extends BaseLlmsInvokingService implements IGDeepSearchService {

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
	protected final GUserChatContextRepository userChatContextRepository;
	protected final KnowledgeBaseRepository knowledgeBaseRepository;
	protected final IGRagChatService ragChatService;
	protected final IGChatService chatService;
	protected final IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService;
	protected final IGeboThreadManager threadManager;

	final ChatProfilesRepository chatProfilesRepository;

	private final Scheduler deepSearchScheduler;
	private final ExecutorService deepSearchExecutor;

	public DeepSearchServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			DeepSearchDefaultConfig defaultDeepsearchConfig, DeepSearchConfigRepository configRepository,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService,
			DeepSearchRequestRepository requestsRepository,
			DeepSearchDocumentAnalisysResultStepRepository stepsRepository,
			DeepSearchResponseRepository responseRepository, GUserChatContextRepository userChatContextRepository,
			KnowledgeBaseRepository knowledgeBaseRepository, IGRagChatService ragChatService, IGChatService chatService,
			IGKnowledgebaseVisibilityService knowledgeBaseVisibilityService,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			DeepSearchDataSourceDocumentResultRepository dataSourceDocumentResultRepository,
			DeepSearchDataSourceResponseRepository dataSourceResponseRepository,
			ChatProfilesRepository chatProfilesRepository, IGeboThreadManager threadManager) {
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

		this.deepSearchScheduler = threadManager.getScheduler();
	}

	@PreDestroy
	public void shutdown() {
		deepSearchExecutor.shutdown();
	}

	@Override
	public Flux<AbstractDeepSearchEvent> searchAsync(DeepSearchRequest request) throws LLMConfigException {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return Flux.defer(() -> {

			try {

				var sc = SecurityContextHolder.createEmptyContext();
				sc.setAuthentication(auth);
				SecurityContextHolder.setContext(sc);
				final UserInfos userInfos = securityService.getCurrentUser();
				request.setUsername(userInfos.getUsername());

				// NB: qui sei ancora in blocking code (ok, ma verrà eseguito sul
				// deepSearchScheduler grazie a subscribeOn)
				requestsRepository.save(request);

				final DeepSearchConfig data = configRepository.findByDefaultConfig(true);
				final DeepSearchConfig configuration = data != null ? data : defaultDeepsearchConfig;

				final List<GKnowledgeBase> knowledgeBases = knowledgeBaseRepository
						.findAllById(request.getKnowledgeBases());

				final List<IGConfigurableEmbeddingModel> embeddingModels = getEmbeddingModelsListByKnowledgeBases(
						knowledgeBases);

				final GObjectRef<GBaseChatModelConfig> chatModelReference = configuration.getChatModelConfiguration();
				IGConfigurableChatModel finalChatModel;

				finalChatModel = getChatModel(chatModelReference);

				if (finalChatModel == null) {
					DeepSearchErrorEvent errorEvent = new DeepSearchErrorEvent();
					errorEvent.setInputData(request);
					errorEvent.setOutputData(GUserMessage.errorMessage("No chat model defined",
							"No chat model configured for deep search nor default chat model is configured"));
					return Flux.just(errorEvent);
				}

				final DeepsearchWorker worker = runtimeBinder.getImplementationOf(DeepsearchWorker.class);
				final DeepSearchState state = new DeepSearchState();
				final List<AbstractDeepSearchEvent> history = new ArrayList<>();
				final AtomicBoolean cancelled = new AtomicBoolean(false);

				return Flux.<AbstractDeepSearchEvent>create(sink -> {
					sink.onCancel(() -> cancelled.set(true));
					sink.onDispose(() -> cancelled.set(true));

					try {
						AbstractDeepSearchEvent step;
						while (!cancelled.get()) {

							step = worker.nextStep(request, history, state, configuration, userInfos, embeddingModels,
									finalChatModel);

							if (step == null) {
								sink.complete();
								return;
							}

							sink.next(step);
							if (step instanceof DeepSearchDataSourceDocumentResultEvent dsDocumentEvent) {
								if (dsDocumentEvent.getOutputData() != null
										&& dsDocumentEvent.getOutputData().getAnalyzedResult() != null
										&& dsDocumentEvent.getOutputData().getAnalyzedResult().trim().length() > 0
										&& (dsDocumentEvent.getOutputData().getEmptyResult() == null
												|| !dsDocumentEvent.getOutputData().getEmptyResult())) {
									dataSourceDocumentResultRepository.save(dsDocumentEvent.getOutputData());

								}
							}
							if (step instanceof DeepSearchDataSourceProcessedEvent dataSourceProcessedEvent) {
								if (dataSourceProcessedEvent.getOutputData() != null
										&& dataSourceProcessedEvent.getOutputData().getResponse() != null
										&& dataSourceProcessedEvent.getOutputData().getResponse().trim().length() > 0
										&& (dataSourceProcessedEvent.getOutputData().getSearchResultsEmpty() == null
												|| !dataSourceProcessedEvent.getOutputData().getSearchResultsEmpty())) {
									dataSourceResponseRepository.save(dataSourceProcessedEvent.getOutputData());

								}
							}
							if (step instanceof DeepSearchDocumentEvent documentEvent) {
								history.add(step);
								stepsRepository.save(documentEvent.getOutputData());
							}
							if (step instanceof DeepSearchProcessedEvent doneEvent) {
								responseRepository.save(doneEvent.getOutputData());
							}
							if (step instanceof DeepSearchProcessedEvent || step instanceof DeepSearchErrorEvent) {
								sink.complete();
								return;
							}
						}

						// se cancellato: chiudi “pulito” (niente interrupt, niente error)
						sink.complete();

					} catch (Throwable t) {
						// Se vuoi: qui puoi “declassare” eccezioni da interrupt a complete() (ma con
						// questa versione non dovrebbero più arrivare)
						LOGGER.error("Error in searchAsync(...)", t);
						sink.error(t);
					}
				}, OverflowStrategy.BUFFER);
			} catch (Throwable th) {
				LOGGER.error("Error in searchAsync(...) main loop", th);
				DeepSearchErrorEvent errorEvent = new DeepSearchErrorEvent();
				errorEvent.setInputData(request);
				errorEvent.setOutputData(GUserMessage.errorMessage("Exception executing Deep search", th));
				return Flux.just(errorEvent);
			} finally {
				SecurityContextHolder.clearContext();
			}
		})

				.subscribeOn(deepSearchScheduler);
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
		if (data.isPresent()) {
			securityService.checkBeingCreator(data.get());
		}
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
	public Flux<AbstractDeepSearchEvent> searchAsync(GeboChatRequest request) throws LLMConfigException {
		if (request.getId() == null) {
			request.setId(UUID.randomUUID().toString());
		}
		String userChatContextCode = request.getUserChatContextCode();
		if (userChatContextCode == null || userChatContextCode.trim().length() == 0) {
			throw new IllegalStateException("Cannot handle request without referred user context code");
		}
		Optional<GUserChatContext> chatContextData = userChatContextRepository.findById(userChatContextCode);
		if (chatContextData.isEmpty())
			throw new IllegalStateException("Not existent user chat context");
		final GUserChatContext chatContext = chatContextData.get();
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
		DeepSearchRequest deepSearchRequest = new DeepSearchRequest();
		deepSearchRequest.setChatRequestCode(request.getId());
		deepSearchRequest.setKnowledgeBases(knowledgeBasesCodesList);
		deepSearchRequest.setQuery(request.getQuery());
		deepSearchRequest.setUserChatContextCode(userChatContextCode);
		deepSearchRequest.setDeepSearchDataSources(request.getDeepSearchDataSources());
		final GeboChatResponse response = cleanResponse;
		final List<GResponseDocumentRef> documents = new ArrayList<GResponseDocumentRef>();
		response.setDocumentsRef(documents);
		Mono<AbstractDeepSearchEvent> trailingFlux = Mono.fromSupplier(() -> {
			DeepSearchChatResponseEvent responseEvent = new DeepSearchChatResponseEvent();
			responseEvent.setInputData(request);
			responseEvent.setOutputData(response);
			return responseEvent;
		});
		return searchAsync(deepSearchRequest).map(x -> {
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
		final DeepsearchWorker worker = runtimeBinder.getImplementationOf(DeepsearchWorker.class);
		return worker.getDeepSearchActiveHandlers(configuration);
	}

	@Override
	public DeepSearchUISettings getDeepSearchUISettings() {

		return DeepSearchUISettings.of(defaultDeepsearchConfig);
	}

}
