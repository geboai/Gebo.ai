package ai.gebo.llms.deepsearch.service.impl;

import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GInputProcessingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetCalculator;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.FoldOutcome;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.RollingFold;
import ai.gebo.llms.deepsearch.service.DeepSearchOutputThinking;
import ai.gebo.llms.deepsearch.service.DeepSearchVerdict;
import ai.gebo.llms.deepsearch.service.DocumentNamesShown;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.GenerativeFunction;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.LaneBudget;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.LastWork;
import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.TokensLimitCompute;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultRoutingChatPipelineStepServiceImpl;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.AbstractPureSearchDocumentResultEntry;
import ai.gebo.llms.deepsearch.datasources.model.PureSearchDocumentResultError;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGInternalKnlowledgeBaseRagDeepSearchService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService.DocumentWithSearchResult;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceServiceRepositoryPattern;
import ai.gebo.llms.deepsearch.service.IGReactiveDynamicDataSourceServicesProvider;
import ai.gebo.llms.deepsearch.service.IGReactiveEnabledDeepSearchDataSourceLookupService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class FullReactiveDeepsearchWorker extends BaseLLMSInvokingAndProvidingService {
	private static final String PI_PI_FILE = "pi pi-file";
	private static final String GENERATING_ANALISYS = "Generating analisys..";
	private static final String STREAMING_RESULTS = "StreamingResults";
	private static final String UTF_8 = "UTF-8";
	private static final String CALLING_LLM_PROBLEM_ON_FINAL_ANALISYS = "CALLING LLM PROBLEM ON FINAL ANALISYS";
	private static final String SORRY_SOMETHING_GONE_WRONG = "Sorry, something gone wrong on last step of the execution";
	private static final String EXCEPTION_ON_EMPTY_RESULTS = "Exception on empty results";
	private static final String CONSOLIDATED_SUMMARY_PROMPT_PARAM = "consolidated";
	private static final String AGENT_DELIVERABLE_COMPLETENESS = "agentDeliverableCompleteness";
	private final static Logger LOGGER = LoggerFactory.getLogger(FullReactiveDeepsearchWorker.class);
	private final IGReactiveEnabledDeepSearchDataSourceLookupService enabledDataSourcesLookupService;
	private final DeepSearchDefaultConfig defaultDeepsearchConfig;
	private final IGPromptConfigDao promptsDao;
	private final IGeboThreadManager threadManager;
	private final IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService;
	private final IGChatSessionLifeCycleService sessionLifecycleService;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	protected final IDocumentsChunkService chunkingService;
	// the one deep search map/reduce, the deep search tools' too
	private final DeepSearchAnalysis analysis;

	public FullReactiveDeepsearchWorker(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, IGeboThreadManager threadManager,
			IGPromptConfigDao promptsDao,
			IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService,
			DeepSearchDefaultConfig defaultDeepsearchConfig,
			IGReactiveDeepSearchDataSourceServiceRepositoryPattern deepSearchDataSourcesRepositoryPattern,
			IGReactiveDynamicDataSourceServicesProvider dataSourcesProvider,
			IGReactiveEnabledDeepSearchDataSourceLookupService enabledDataSourcesLookupService,
			IGChatSessionLifeCycleService sessionLifecycleService, IDocumentsChunkService chunkingService,
			IGDeepSearchConfigProvider deepSearchConfigProvider, DeepSearchAnalysis analysis) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.analysis = analysis;
		this.enabledDataSourcesLookupService = enabledDataSourcesLookupService;
		this.defaultDeepsearchConfig = defaultDeepsearchConfig;
		this.promptsDao = promptsDao;
		this.threadManager = threadManager;
		this.internalKnowledgeBaseDeepSearchService = internalKnowledgeBaseDeepSearchService;
		this.deepSearchConfigProvider = deepSearchConfigProvider;
		this.sessionLifecycleService = sessionLifecycleService;
		this.chunkingService = chunkingService;

	}

	Flux<AbstractPureSearchDocumentResultEntry> streamPureSearch(LLMChatRequestResources request,
			MinimalChatContext minimalChatContext, GeboChatRequest geboChatRequest, ISinkUIEmitter emitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, List<String> searchDataSources,
			int perDataSourceK, int globalK, int sampleTextTokensSize, String chunkSessionId) {
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		if (searchDataSources == null || searchDataSources.isEmpty()) {
			if (request.getCurrentRequest().getDeepSearchDataSources() != null
					&& !request.getCurrentRequest().getDeepSearchDataSources().isEmpty()) {
				searchDataSources = request.getCurrentRequest().getDeepSearchDataSources();
			} else
				searchDataSources = List
						.of(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID);
		}
		final List<String> sampledDataSources = searchDataSources;
		DeepSearchConfig configuration = this.deepSearchConfigProvider.get();
		if (configuration == null) {
			configuration = this.defaultDeepsearchConfig;
		}
		final DeepSearchConfig sampledConfig = configuration;
		List<IGReactiveDeepSearchDataSourceService> handlersFullList = this.enabledDataSourcesLookupService
				.enabledDataSources(configuration);
		List<IGReactiveDeepSearchDataSourceService> filtered = handlersFullList.stream()
				.filter(handler -> sampledDataSources != null && sampledDataSources.contains(handler.getHandlerId()))
				.toList();
		List<Supplier<Flux<AbstractPureSearchDocumentResultEntry>>> suppliers = new ArrayList<>();
		for (IGReactiveDeepSearchDataSourceService handler : filtered) {
			Supplier<Flux<AbstractPureSearchDocumentResultEntry>> supplier = () -> {
				return runAs.doRunAsWithReturn(() -> {
					try {
						emitter.notifyUser("search-" + handler.getHandlerId(),
								"Running search on " + handler.getDescription(sampledConfig), PI_PI_FILE, 3000l,
								NotificationType.INFO);
						return handler.streamPureSearch(minimalChatContext, emitter, chatModel, serviceModel,
								perDataSourceK, sampleTextTokensSize, chunkSessionId);
					} catch (Throwable e) {
						LOGGER.error("Error running search on "
								+ (handler != null ? handler.getHandlerId() : "Null handler"), e);
						PureSearchDocumentResultError error = new PureSearchDocumentResultError(null, null,
								GUserMessage.warnMessage("Error running search", e.getMessage()));
						return Flux.just((AbstractPureSearchDocumentResultEntry) error);
					}
				});
			};
			suppliers.add(supplier);
		}
		if (sampledDataSources.contains(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID)) {
			Supplier<Flux<AbstractPureSearchDocumentResultEntry>> supplier = () -> {
				return runAs.doRunAsWithReturn(() -> {
					try {
						emitter.notifyUser("search-ikb", "Running search on internal Knowledge Base", PI_PI_FILE, 3000l,
								NotificationType.INFO);
						return this.internalKnowledgeBaseDeepSearchService.streamPureSearch(minimalChatContext, emitter,
								serviceModel, serviceModel, chunkSessionId, perDataSourceK, sampleTextTokensSize);
					} catch (Throwable e) {
						LOGGER.error("Error running search on internal Knowledge Base", e);
						PureSearchDocumentResultError error = new PureSearchDocumentResultError(null, null,
								GUserMessage.warnMessage("Error running search", e.getMessage()));
						return Flux.just((AbstractPureSearchDocumentResultEntry) error);
					}
				});
			};
			suppliers.add(supplier);
		}
		if (suppliers.isEmpty()) {
			return Flux.empty();
		}

		Flux<AbstractPureSearchDocumentResultEntry> outFlux = Flux.fromIterable(suppliers).concatMap(supplier -> {
			return supplier.get();
		}, suppliers.size()).subscribeOn(threadManager.getBoundedElastic());
		return outFlux;

	}

	List<GBaseObject> getDeepSearchActiveHandlers(DeepSearchConfig configuration) {

		IGConfigurableChatModel chatModel = null;

		if (chatModel == null) {
			chatModel = chatModelsConfigDao.defaultHandler();
		}
		if (chatModel == null)
			return List.of();
		final IGConfigurableChatModel fChatModel = chatModel;
		List<IGReactiveDeepSearchDataSourceService> handlersFullList = this.enabledDataSourcesLookupService
				.enabledDataSources(configuration);
		return handlersFullList.stream().map(x -> {
			GBaseObject ds = new GBaseObject();
			ds.setCode(x.getHandlerId());
			ds.setDescription(x.getDescription(configuration));
			return ds;
		}).toList();
	}

	Flux<GeboChatMessageEnvelope> streamDeepSearch(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			List<String> searchDataSources, int perDataSourceK, int globalK) {
		final String chunkSessionId = this.chunkingService
				.createChunkingSession("request:" + runtimeData.getRequestResources().getCurrentRequest().getId());
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		// the deep search answers the user: the reasoning of what it writes streams to the chat
		final DeepSearchOutputThinking outputThinking = DeepSearchOutputThinking.toChat(sinkUIEmitter);
		if (searchDataSources == null || searchDataSources.isEmpty()) {

			searchDataSources = List.of(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID);
		}
		final List<String> sampledDataSources = searchDataSources;
		DeepSearchConfig configuration = this.deepSearchConfigProvider.get();
		if (configuration == null) {
			configuration = this.defaultDeepsearchConfig;
		}
		final DeepSearchConfig sampledConfig = configuration;
		List<IGReactiveDeepSearchDataSourceService> handlersFullList = this.enabledDataSourcesLookupService
				.enabledDataSources(configuration);
		List<IGReactiveDeepSearchDataSourceService> filtered = handlersFullList.stream()
				.filter(handler -> sampledDataSources != null && sampledDataSources.contains(handler.getHandlerId()))
				.toList();
		final Map<String, DocumentWithSearchResult> resultsByFragmentId = new Hashtable<>();
		final Map<String, Document> docrefsByFragmentId = new Hashtable<>();
		List<Supplier<Flux<Document>>> suppliers = new ArrayList<>();
		boolean containsIKB = false;
		for (IGReactiveDeepSearchDataSourceService handler : filtered) {
			Supplier<Flux<Document>> supplier = () -> {
				return runAs.doRunAsWithReturn(() -> {
					try {
						sinkUIEmitter.notifyUser("search-" + handler.getHandlerId(),
								"Running search on " + handler.getDescription(sampledConfig), PI_PI_FILE, 3000l,
								NotificationType.INFO);
						Flux<DocumentWithSearchResult> fl = handler.streamSearchResults(runtimeData, sinkUIEmitter,
								chatModel, serviceModel, chunkSessionId, globalK)
								.subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
						return fl.map(x -> {

							GResponseDocumentRef ref = new GResponseDocumentRef(x.getSearchResult());
							if (x.getDocument() != null)
								resultsByFragmentId.put(x.getDocument().getId(), x);
							GInputProcessingEvent processingEvent = new GInputProcessingEvent(ref);
							sinkUIEmitter.next(new GeboChatMessageEnvelope(processingEvent));

							return x.getDocument();
						});
					} catch (Throwable e) {
						LOGGER.error("Error in straming", e);
						return Flux.empty();
					}
				});
			};
			suppliers.add(supplier);
		}

		if (sampledDataSources.contains(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID)) {
			containsIKB = true;
			Supplier<Flux<Document>> supplier = () -> {
				return runAs.doRunAsWithReturn(() -> {
					try {
						sinkUIEmitter.notifyUser("search-ikb", "Running search on internal Knowledge Base", PI_PI_FILE,
								3000l, NotificationType.INFO);
						return this.internalKnowledgeBaseDeepSearchService.streamSearchResults(runtimeData,
								sinkUIEmitter, chatModel, serviceModel, chunkSessionId, globalK).map(doc -> {

									String code = doc.getMetadata() != null
											&& doc.getMetadata().containsKey(DocumentMetaInfos.CONTENT_CODE)
													? doc.getMetadata().get(DocumentMetaInfos.CONTENT_CODE).toString()
													: null;
									if (code != null) {
										docrefsByFragmentId.put(doc.getId(), doc);
										GResponseDocumentRef ref = new GResponseDocumentRef(doc);
										GInputProcessingEvent processingEvent = new GInputProcessingEvent(ref);
										sinkUIEmitter.next(new GeboChatMessageEnvelope(processingEvent));
									}
									return doc;
								}).subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
					} catch (Throwable e) {
						LOGGER.error("Error streaming documents to analyze", e);
						return Flux.empty();
					}
				});
			};
			suppliers.add(supplier);
		}
		if (suppliers.isEmpty()) {
			// nothing to search: the chunking session ends here
			disposeChunkingSession(chunkSessionId);
			return Flux.empty();
		}

		// each analysis call is given its documents: the request's own are not added to every call
		final IChatRequestContext context = runtimeData.getRequestResources().createChatRequestContext()
				.cloneWithNewDocumentsList(List.of());
		// prompt template for the final analisys of the documents read directly, or of the
		// analyses of several sources (the analyses are the shared DeepSearchAnalysis's own)
		final GPromptTemplateConfig finalAnalisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_CONSOLIDATION_PROMPT);
		// prompt template for empty documents
		final GPromptTemplateConfig emptyResponsePrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT);
		final GeboChatResponse response = runtimeData.getChatResponse();
		final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		final Map<String, Object> commonParams = new HashMap<>();
		commonParams.put(AGENT_DELIVERABLE_COMPLETENESS,
				request.getUserIntent().name() + ": " + request.getUserIntent().getAgentDeliverableCompleteness());
		final Flux<String> backupNotFoundDocuments = Flux.defer(() -> {
			Flux<String> outFlux = null;
			try {
				try {
					sinkUIEmitter.notifyUser("search-failed", "Cannot find documents to analyze", PI_PI_FILE, 3000l,
							NotificationType.INFO);
				} catch (Throwable th) {
					LOGGER.error("Error notifying user about missing documents", th);
				}
				Map<String, Object> params = new HashMap<>(commonParams);
				params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, "");
				params.put(CONSOLIDATED_SUMMARY_PROMPT_PARAM, "");
				outFlux = outputThinking.text(callLLMReactiveAnswer(chatModel, emptyResponsePrompt, context, params));
			} catch (Throwable th) {
				LOGGER.error(EXCEPTION_ON_EMPTY_RESULTS, th);
				outFlux = Flux.just(SORRY_SOMETHING_GONE_WRONG);
			}
			return outFlux;
		});

		Flux<String> resultFlux = null;
		// the quotations of the whole request, checked against their fragments (best effort)
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		final AtomicLong docsCounter = new AtomicLong(0l);
		final Function<Document, Document> countingMapper = x -> {
			docsCounter.incrementAndGet();
			return x;
		};
		// the fragments the analyses leave unread, and the ones they find relevant
		final Vector<String> unreadFragments = new Vector<>();
		final DeepSearchRelevance relevance = new DeepSearchRelevance();
		if (suppliers.isEmpty()) {
			resultFlux = backupNotFoundDocuments;
		} else if (suppliers.size() == 1) {
			Flux<Document> documentFlux = suppliers.get(0).get().map(countingMapper);
			if (containsIKB) {
				// NOTE: blocking call. Safe only because this method body runs on a boundedElastic
				// worker (DeepSearchServiceImpl wraps it in Flux.defer(..).subscribeOn(boundedElastic)).
				// Never subscribe to this worker directly from a non-blocking/event-loop thread.
				List<Document> documents = documentFlux.buffer().blockFirst();
				if (documents == null || documents.isEmpty()) {
					resultFlux = backupNotFoundDocuments;
				} else {
					int totalTokens = 0;
					for (Document document : documents) {
						if (document.getMetadata().containsKey(DocumentMetaInfos.GEBO_TOKEN_LENGTH) && document
								.getMetadata().get(DocumentMetaInfos.GEBO_TOKEN_LENGTH) instanceof Number t) {
							totalTokens += t.intValue();
						} else {
							totalTokens += ITokensCountable.stringsTokensSize(document.getText());
						}
					}
					// the documents fit the tokens budget's share of the context window
					// (ai.gebo.llms.tokens-budget.factor)
					if (totalTokens <= (int) (chatModel.getContextLength() * BaseLLMSInvokingService.ERRONEUS_TOKEN_LENGTH_ERROR_COEFF)) {
						try {
							try {
								sinkUIEmitter.notifyUser(STREAMING_RESULTS, GENERATING_ANALISYS, PI_PI_FILE, 3000l,
										NotificationType.INFO);
							} catch (Throwable th) {
								LOGGER.error("Error notifying user about analisys generation", th);
							}
							Map<String, Object> params = new HashMap<>(commonParams);
							params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, documents);
							params.put(IChatRequestContext.CONSOLIDATED_SUMMARY_PROMPT_PARAM, "");
							// read directly, with no partial analysis: the answer may quote them
							quotations.addSources(documents);
							resultFlux = DeepSearchVerdict.withoutVerdict(outputThinking
									.text(callLLMReactiveAnswer(chatModel, finalAnalisysPrompt, context, params)));
						} catch (Throwable th) {
							LOGGER.error("Exception on last summary", th);
							resultFlux = Flux.just(SORRY_SOMETHING_GONE_WRONG);
						}
					} else {
						documentFlux = Flux.fromIterable(documents);
					}
				}

			}
			if (resultFlux == null) {

				resultFlux = generateDeepSearchFlux(documentFlux, context, runAs, sinkUIEmitter, request, chatModel, serviceModel,
					unreadFragments, quotations, relevance, outputThinking);
				resultFlux = Flux.concat(resultFlux, Flux.defer(() -> {
					if (docsCounter.get() == 0l) {
						return backupNotFoundDocuments;
					} else {
						return Flux.fromIterable(List.of());
					}
				}));
			}
		} else

		{
			// Analyze data sources concurrently, capped so total concurrent LLM calls stay bounded
			// (maxConcurrentSources * analysisParallelism). flatMap is safe here because the downstream
			// buffer() collects all per-source analyses and the final consolidation is order-insensitive.
			final int maxConcurrentSources = Math.max(1,
					Math.min(this.defaultDeepsearchConfig.getMaxConcurrentSources(), suppliers.size()));
			Flux<List<List<String>>> resultsBuffer = Flux.fromIterable(suppliers).flatMap(x -> {
				Flux<Document> documentFlux = x.get().map(countingMapper);
				// a source's analysis feeds the final one, which is the output
				Flux<String> flux = generateDeepSearchFlux(documentFlux, context, runAs, sinkUIEmitter, request, chatModel, serviceModel,
					unreadFragments, quotations, relevance, DeepSearchOutputThinking.NONE);
				return flux.buffer();
			}, maxConcurrentSources).subscribeOn(Schedulers.boundedElastic(), true).buffer();
			resultFlux = resultsBuffer.map(lists -> {

				List<String> preAnalisys = new ArrayList<>();
				for (List<String> stringAsList : lists) {
					StringBuffer buffer = new StringBuffer();
					for (String s : stringAsList) {
						buffer.append(s);
					}
					preAnalisys.add(buffer.toString());
				}
				return preAnalisys;
			}).concatMap(documents -> {

				Flux<String> out = null;
				if (!documents.isEmpty() && docsCounter.get() > 0l) {

					try {
						try {
							sinkUIEmitter.notifyUser("aggregate-multple-src", "Finalizing multiple sources analisys",
									PI_PI_FILE, 3000l, NotificationType.INFO);
						} catch (Throwable th) {
							LOGGER.error("Error notifying user about multiple sources finalization", th);
						}
						Map<String, Object> params = new HashMap<>(commonParams);
						params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, documents);
						params.put(IChatRequestContext.CONSOLIDATED_SUMMARY_PROMPT_PARAM, "");
						out = DeepSearchVerdict.withoutVerdict(outputThinking
								.text(callLLMReactiveAnswer(chatModel, finalAnalisysPrompt, context, params)));
					} catch (Throwable th) {
						LOGGER.error("Exception on last summary", th);
						out = Flux.just(SORRY_SOMETHING_GONE_WRONG);
					}
				} else {
					out = backupNotFoundDocuments;
				}
				return out;
			});

		}

		final StringBuffer cumulative = new StringBuffer();
		// what the user gets: the quotations the standard way, with no fragment id; the
		// reasoning sent before ended when the text comes
		Flux<GeboChatMessageEnvelope> intermediateStreamingFlux = quotations.render(withOutputThinking(resultFlux,
				outputThinking)).map(x -> {
			cumulative.append(x);
			return x;
		}).map(piece -> new GeboChatMessageEnvelope<>(piece));
		Flux<GeboChatMessageEnvelope> finalMessages = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				response.setQueryResponse(cumulative.toString());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Deep search answer with " + quotations.quotes().size() + " verified quotation(s)");
				}
				for (String fragmentId : unreadFragments) {
					resultsByFragmentId.remove(fragmentId);
					docrefsByFragmentId.remove(fragmentId);
				}
				// the documents of the answer: the ones the analyses found relevant (listed,
				// quoted or named), every one read when they found none
				relevance.recordQuotations(quotations);
				if (relevance.isEmpty()) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Deep search answer documents: no fragment found relevant, the "
								+ resultsByFragmentId.size() + " result(s) and " + docrefsByFragmentId.size()
								+ " document(s) read are given");
					}
				} else {
					final int resultsBefore = resultsByFragmentId.size();
					final int docrefsBefore = docrefsByFragmentId.size();
					resultsByFragmentId.keySet().removeIf(fragmentId -> !relevance.isRelevant(fragmentId));
					docrefsByFragmentId.keySet().removeIf(fragmentId -> !relevance.isRelevant(fragmentId));
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Deep search answer documents: " + relevance.summary() + ", fragments given "
								+ resultsByFragmentId.size() + " of " + resultsBefore + " result(s), "
								+ docrefsByFragmentId.size() + " of " + docrefsBefore + " document(s)");
					}
				}

				Map<String, GResponseDocumentRef> docsMap = new HashMap<>();
				resultsByFragmentId.values().stream().forEach(x -> {
					if (!docsMap.containsKey(x.getSearchResult().getCode())) {
						docsMap.put(x.getSearchResult().getCode(), new GResponseDocumentRef(x.getSearchResult()));
					}

				});
				docrefsByFragmentId.values().stream().forEach(x -> {
					String code = x.getMetadata() != null && x.getMetadata().containsKey(DocumentMetaInfos.CONTENT_CODE)
							? x.getMetadata().get(DocumentMetaInfos.CONTENT_CODE).toString()
							: null;
					docsMap.put(code, new GResponseDocumentRef(x));
				});
				response.setDocumentsRef(new ArrayList<>(docsMap.values()));
				try {
					sessionLifecycleService.endRequest(request, response);
				} catch (Throwable e) {
					LOGGER.error("Error ending request", e);
				}
				GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(response);
				envelope.setLastMessage(true);
				return Flux.fromIterable(List.of(envelope, GeboChatMessageEnvelope.FINAL_MESSAGE));
			});
		});
		Flux<GeboChatMessageEnvelope> finalFlux = Flux.concat(intermediateStreamingFlux, finalMessages)
				.publishOn(threadManager.getScheduler()).doOnComplete(() -> {
					runAs.doAs(() -> {
						try {
							sessionLifecycleService.chatRequestCompleted(request, chatModel);
						} catch (Throwable e) {
							LOGGER.error("Error completing request", e);
						}
					});
				})
				// the chunking session ends however the search ends: completed, failed or cancelled
				.doFinally(signal -> runAs.doAs(() -> disposeChunkingSession(chunkSessionId)));

		return finalFlux.subscribeOn(runAs.wrap(Schedulers.boundedElastic()));

	}

	Flux<GeboChatMessageEnvelope> streamChatWithHugeFiles(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) {
		final String chunkSessionId = this.chunkingService
				.createChunkingSession("request:" + runtimeData.getRequestResources().getCurrentRequest().getId());
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		// the deep search answers the user: the reasoning of what it writes streams to the chat
		final DeepSearchOutputThinking outputThinking = DeepSearchOutputThinking.toChat(sinkUIEmitter);

		DeepSearchConfig configuration = this.deepSearchConfigProvider.get();
		if (configuration == null) {
			configuration = this.defaultDeepsearchConfig;
		}
		final DeepSearchConfig sampledConfig = configuration;
		final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		final GeboChatResponse response = runtimeData.getChatResponse();
		final Map<String, Object> commonParams = new HashMap<>();
		commonParams.put(AGENT_DELIVERABLE_COMPLETENESS,
				request.getUserIntent().name() + ": " + request.getUserIntent().getAgentDeliverableCompleteness());
		// each analysis call is given its pieces: the selected documents are not added to every call
		final IChatRequestContext context = runtimeData.getRequestResources().createChatRequestContext()
				.cloneWithNewDocumentsList(List.of());
		// prompt template for input document analisys: what sizes the pieces of a document
		final GPromptTemplateConfig cumulativeAnalisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_FILE_ANALISYS_PROMPT);
		// a selected document is read whole: it is split into pieces each filling its share of the
		// batch an analysis with no consolidation is given, on the model writing the analyses
		final int pieceTokens = defaultDeepsearchConfig.chunkTokens(Math.max(0,
				computeFragmentBudget("", cumulativeAnalisysPrompt.getTokensSize(), serviceModel.getContextLength(),
						DeepSearchBudgets.knownValues(cumulativeAnalisysPrompt, commonParams, context))));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Huge files analysis: model:" + serviceModel.getCode() + " context:"
					+ serviceModel.getContextLength() + " document pieces of " + pieceTokens + " (tok) at most");
		}
		Map<String, GResponseDocumentRef> docrefs = new Hashtable<>();
		Flux<Document> docsFlux = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				try {
					AIDocumentsSet aiDoc = runtimeData.getRequestResources().allDocuments();
					final List<Document> selected = aiDoc.aiDocumentsList();
					// the documents analysed told by their names
					final String names = DocumentNamesShown.ofFragments(selected);
					sinkUIEmitter.notifyUser("search-ikb",
							"Doing analisys on selected documents" + (names != null ? ": " + names : ""), PI_PI_FILE,
							3000l, NotificationType.INFO);
					return Flux.fromIterable(selected);
				} catch (Throwable e) {
					LOGGER.error("Error streaming selected documents to analyze", e);
					return Flux.empty();
				}
			});
		}).map(doc -> {

			String code = doc.getMetadata() != null && doc.getMetadata().containsKey(DocumentMetaInfos.CONTENT_CODE)
					? doc.getMetadata().get(DocumentMetaInfos.CONTENT_CODE).toString()
					: null;
			if (code != null && !docrefs.containsKey(code)) {
				GResponseDocumentRef ref = new GResponseDocumentRef(doc);
				docrefs.put(code, new GResponseDocumentRef(doc));
				GInputProcessingEvent processingEvent = new GInputProcessingEvent(ref);
				sinkUIEmitter.next(new GeboChatMessageEnvelope(processingEvent));
			}
			return doc;
		}).flatMapIterable(doc -> DocumentPieces.of(doc, pieceTokens));

		Vector<String> discardedFragmentIds = new Vector<>();
		// the quotations of the request, checked against their fragments (best effort)
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		Flux<String> resultFlux = generateDeepSearchFlux(docsFlux, context, runAs, sinkUIEmitter, request, chatModel, serviceModel,
					discardedFragmentIds, quotations, new DeepSearchRelevance(), outputThinking);

		final StringBuffer cumulative = new StringBuffer();
		Flux<GeboChatMessageEnvelope> intermediateStreamingFlux = quotations.render(withOutputThinking(resultFlux,
				outputThinking)).map(x -> {
			cumulative.append(x);
			return x;
		}).map(piece -> new GeboChatMessageEnvelope<>(piece));
		Flux<GeboChatMessageEnvelope> finalMessages = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				response.setQueryResponse(cumulative.toString());
				ArrayList docs = new ArrayList<>(docrefs.values());
				response.setDocumentsRef(docs);
				try {
					sessionLifecycleService.endRequest(request, response);
				} catch (Throwable e) {
					LOGGER.error("Error ending request", e);
				}
				GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(response);
				envelope.setLastMessage(true);
				return Flux.fromIterable(List.of(envelope, GeboChatMessageEnvelope.FINAL_MESSAGE));
			});
		});
		Flux<GeboChatMessageEnvelope> finalFlux = Flux.concat(intermediateStreamingFlux, finalMessages)
				.publishOn(threadManager.getScheduler()).doOnComplete(() -> {
					runAs.doAs(() -> {
						try {
							sessionLifecycleService.chatRequestCompleted(request, chatModel);
						} catch (Throwable e) {
							LOGGER.error("Error completing request", e);
						}
					});
				})
				// the chunking session ends however the answer ends: completed, failed or cancelled
				.doFinally(signal -> runAs.doAs(() -> disposeChunkingSession(chunkSessionId)));
		return finalFlux.subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
	}

	/** The chunking session of a request ended, never failing what ends it. */
	private void disposeChunkingSession(String chunkSessionId) {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Disposing the chunking session " + chunkSessionId);
			}
			this.chunkingService.disposeChunkingSession(chunkSessionId);
		} catch (Throwable th) {
			LOGGER.error("Error disposing chunking session " + chunkSessionId, th);
		}
	}


	/**
	 * The deep search analysis of the fragments (see {@link DeepSearchAnalysis}): sized for
	 * the request's deliverable, the user told when the model fails on empty results.
	 */
	private Flux<String> generateDeepSearchFlux(Flux<Document> docsFlux, IChatRequestContext context,
			ReactiveIdentityUtil runAs, ISinkUIEmitter sinkUIEmitter, GeboChatRequest request,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			Vector<String> discardedFragmentIds, DeepSearchQuotations quotations, DeepSearchRelevance relevance,
			DeepSearchOutputThinking outputThinking) {
		return analysis.analyze(docsFlux, context, runAs, request.getUserIntent(), null, chatModel, serviceModel,
				discardedFragmentIds, sinkUIEmitter, quotations, relevance, null, sinkUIEmitter::notifyLLMProblems,
				"Deep search", outputThinking);
	}

	/**
	 * The output's text, the reasoning sent before it ended when the text comes (an output
	 * folded by blocking calls comes all at once), or when the output ends.
	 */
	private static Flux<String> withOutputThinking(Flux<String> output, DeepSearchOutputThinking outputThinking) {
		return output.doOnNext(outputThinking::answering).concatWith(Flux.defer(() -> {
			outputThinking.ended();
			return Flux.<String>empty();
		}));
	}

}
