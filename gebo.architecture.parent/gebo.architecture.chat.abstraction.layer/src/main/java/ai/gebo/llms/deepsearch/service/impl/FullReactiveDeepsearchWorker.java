package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
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
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.GenerativeFunction;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.LastWork;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.TokensLimitCompute;
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
	private static final String CALLING_LLM_PROBLEM_ON_FINAL_ANALISYS = "CALLING LLM PROBLEM ON FINAL ANALISYS";
	private static final String SORRY_SOMETHING_GONE_WRONG = "Sorry, something gone wrong on last step of the execution";
	private static final String EXCEPTION_ON_EMPTY_RESULTS = "Exception on empty results";
	private static final String CONSOLIDATED_SUMMARY_PROMPT_PARAM = "consolidated";
	private static final String AGENT_DELIVERABLE_COMPLETENESS = "agentDeliverableCompleteness";
	private static final String ERROR_IN_PROCESS = "<!-ERROR-IN-PROCESS->";
	private static final String PARTIAL_ANALISYS_SATISFACTORY = "<IS-COMPLETELY-SATISFACTORY/>";
	private final static Logger LOGGER = LoggerFactory.getLogger(FullReactiveDeepsearchWorker.class);
	private final IGReactiveEnabledDeepSearchDataSourceLookupService enabledDataSourcesLookupService;
	private final DeepSearchDefaultConfig defaultDeepsearchConfig;
	private final IGPromptConfigDao promptsDao;
	private final IGeboThreadManager threadManager;
	private final IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService;
	private final IGChatSessionLifeCycleService sessionLifecycleService;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	protected final IDocumentsChunkService chunkingService;

	public FullReactiveDeepsearchWorker(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, IGeboThreadManager threadManager,
			IGPromptConfigDao promptsDao,
			IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService,
			DeepSearchDefaultConfig defaultDeepsearchConfig,
			IGReactiveDeepSearchDataSourceServiceRepositoryPattern deepSearchDataSourcesRepositoryPattern,
			IGReactiveDynamicDataSourceServicesProvider dataSourcesProvider,
			IGReactiveEnabledDeepSearchDataSourceLookupService enabledDataSourcesLookupService,
			IGChatSessionLifeCycleService sessionLifecycleService, IDocumentsChunkService chunkingService,
			IGDeepSearchConfigProvider deepSearchConfigProvider) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
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
								"Running search on " + handler.getDescription(sampledConfig), "pi pi-file", 3000l,
								NotificationType.INFO);
						return handler.streamPureSearch(minimalChatContext, emitter, chatModel, serviceModel,
								perDataSourceK, sampleTextTokensSize, chunkSessionId);
					} catch (Throwable e) {
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
						emitter.notifyUser("search-ikb", "Running search on internal Knowledge Base", "pi pi-file",
								3000l, NotificationType.INFO);
						return this.internalKnowledgeBaseDeepSearchService.streamPureSearch(minimalChatContext, emitter,
								serviceModel, serviceModel, chunkSessionId, perDataSourceK, sampleTextTokensSize);
					} catch (Throwable e) {
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
		final Map<String, GResponseDocumentRef> results = new Hashtable<>();
		final Map<String, GResponseDocumentRef> docrefs = new Hashtable<>();
		List<Supplier<Flux<Document>>> suppliers = new ArrayList<>();
		for (IGReactiveDeepSearchDataSourceService handler : filtered) {
			Supplier<Flux<Document>> supplier = () -> {
				return runAs.doRunAsWithReturn(() -> {
					try {
						sinkUIEmitter.notifyUser("search-" + handler.getHandlerId(),
								"Running search on " + handler.getDescription(sampledConfig), "pi pi-file", 3000l,
								NotificationType.INFO);
						Flux<DocumentWithSearchResult> fl = handler.streamSearchResults(runtimeData, sinkUIEmitter,
								chatModel, serviceModel, chunkSessionId, globalK);
						return fl.map(x -> {
							if (!results.containsKey(x.getSearchResult().getCode())) {
								GResponseDocumentRef ref = new GResponseDocumentRef(x.getSearchResult());
								results.put(x.getSearchResult().getCode(), ref);
								GInputProcessingEvent processingEvent = new GInputProcessingEvent(ref);
								sinkUIEmitter.next(new GeboChatMessageEnvelope(processingEvent));
							}
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
			Supplier<Flux<Document>> supplier = () -> {
				return runAs.doRunAsWithReturn(() -> {
					try {
						sinkUIEmitter.notifyUser("search-ikb", "Running search on internal Knowledge Base",
								"pi pi-file", 3000l, NotificationType.INFO);
						return this.internalKnowledgeBaseDeepSearchService.streamSearchResults(runtimeData,
								sinkUIEmitter, chatModel, serviceModel, chunkSessionId, globalK).map(doc -> {

									String code = doc.getMetadata() != null
											&& doc.getMetadata().containsKey(DocumentMetaInfos.CONTENT_CODE)
													? doc.getMetadata().get(DocumentMetaInfos.CONTENT_CODE).toString()
													: null;
									if (code != null && !docrefs.containsKey(code)) {
										GResponseDocumentRef ref = new GResponseDocumentRef(doc);
										docrefs.put(code, new GResponseDocumentRef(doc));
										GInputProcessingEvent processingEvent = new GInputProcessingEvent(ref);
										sinkUIEmitter.next(new GeboChatMessageEnvelope(processingEvent));
									}
									return doc;
								});
					} catch (Throwable e) {

						return Flux.empty();
					}
				});
			};
			suppliers.add(supplier);
		}
		if (suppliers.isEmpty()) {
			return Flux.empty();
		}
		final IChatRequestContext context = runtimeData.getRequestResources().createChatRequestContext();
		// prompt template for input document analisys
		final GPromptTemplateConfig cumulativeAnalisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_FILE_ANALISYS_PROMPT);
		// prompt template for final analisys
		final GPromptTemplateConfig finalAnalisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_CONSOLIDATION_PROMPT);
		// prompt template for empty documents
		final GPromptTemplateConfig emptyResponsePrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT);
		// raw tokens budget calculation
		final long tokensBudget = serviceModel.getContextLength() * 2 / 3;
		final GeboChatResponse response = runtimeData.getChatResponse();
		final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		final Flux<String> backupNotFoundDocuments = Flux.defer(() -> {
			Flux<String> outFlux = null;
			try {
				Map<String, Object> params = new HashMap<>();
				params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, "");
				params.put(CONSOLIDATED_SUMMARY_PROMPT_PARAM, "");
				outFlux = callLLMReactive(chatModel, emptyResponsePrompt, context, params);
			} catch (Throwable th) {
				LOGGER.error(EXCEPTION_ON_EMPTY_RESULTS, th);
				outFlux = Flux.just(SORRY_SOMETHING_GONE_WRONG);
			}
			return outFlux;
		});

		Flux<String> resultFlux = null;

		if (suppliers.isEmpty()) {
			resultFlux = backupNotFoundDocuments;
		} else if (suppliers.size() == 1) {
			Flux<Document> documentFlux = suppliers.get(0).get();
			resultFlux = generateDeepSearchFlux(documentFlux, context, runAs, sinkUIEmitter, request,
					cumulativeAnalisysPrompt, emptyResponsePrompt, finalAnalisysPrompt, chatModel, serviceModel);
		} else {
			Flux<List<List<String>>> resultsBuffer = Flux.fromIterable(suppliers).concatMap(x -> {
				Flux<Document> documentFlux = x.get();
				Flux<String> flux = generateDeepSearchFlux(documentFlux, context, runAs, sinkUIEmitter, request,
						cumulativeAnalisysPrompt, emptyResponsePrompt, finalAnalisysPrompt, chatModel, serviceModel);
				return flux.buffer();
			}, suppliers.size()).subscribeOn(Schedulers.boundedElastic(), true).buffer();
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
				if (!documents.isEmpty()) {

					try {
						Map<String, Object> params = new HashMap<>();
						params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, documents);
						params.put(IChatRequestContext.CONSOLIDATED_SUMMARY_PROMPT_PARAM, "");
						out = callLLMReactive(chatModel, finalAnalisysPrompt, context, params);
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
		Flux<Document> documentFlux = null;

		final StringBuffer cumulative = new StringBuffer();
		Flux<GeboChatMessageEnvelope> intermediateStreamingFlux = resultFlux.map(x -> {
			cumulative.append(x);
			return x;
		}).map(piece -> new GeboChatMessageEnvelope<>(piece));
		Flux<GeboChatMessageEnvelope> finalMessages = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				response.setQueryResponse(cumulative.toString());
				ArrayList docs = new ArrayList<>(docrefs.values());
				docs.addAll(results.values());
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
		Flux<GeboChatMessageEnvelope> finalFlux = Flux.concat(intermediateStreamingFlux, finalMessages);
		finalFlux.publishOn(threadManager.getScheduler()).doOnComplete(() -> {
			runAs.doAs(() -> {
				try {
					this.chunkingService.disposeChunkingSession(chunkSessionId);
				} catch (Throwable th) {
				}
				
				try {
					sessionLifecycleService.chatRequestCompleted(request, chatModel);
				} catch (Throwable e) {
					LOGGER.error("Error completing request", e);
				}
			});
		});
		return finalFlux;

	}

	Flux<GeboChatMessageEnvelope> streamChatWithHugeFiles(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) {
		final String chunkSessionId = this.chunkingService
				.createChunkingSession("request:" + runtimeData.getRequestResources().getCurrentRequest().getId());
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();

		DeepSearchConfig configuration = this.deepSearchConfigProvider.get();
		if (configuration == null) {
			configuration = this.defaultDeepsearchConfig;
		}
		final DeepSearchConfig sampledConfig = configuration;
		final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		final GeboChatResponse response = runtimeData.getChatResponse();
		Map<String, GResponseDocumentRef> docrefs = new Hashtable<>();
		Flux<Document> docsFlux = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				try {
					sinkUIEmitter.notifyUser("search-ikb", "Doing analisys on selected documents", "pi pi-file", 3000l,
							NotificationType.INFO);
					AIDocumentsSet aiDoc = runtimeData.getRequestResources().allDocuments();
					return Flux.fromIterable(aiDoc.aiDocumentsList());
				} catch (Throwable e) {

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
		});

		final IChatRequestContext context = runtimeData.getRequestResources().createChatRequestContext();
		// prompt template for input document analisys
		final GPromptTemplateConfig cumulativeAnalisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_FILE_ANALISYS_PROMPT);
		// prompt template for final analisys
		final GPromptTemplateConfig finalAnalisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_CONSOLIDATION_PROMPT);
		// prompt template for empty documents
		final GPromptTemplateConfig emptyResponsePrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT);
		// raw tokens budget calculation

		Flux<String> resultFlux = generateDeepSearchFlux(docsFlux, context, runAs, sinkUIEmitter, request,
				cumulativeAnalisysPrompt, emptyResponsePrompt, finalAnalisysPrompt, chatModel, serviceModel);

		final StringBuffer cumulative = new StringBuffer();
		Flux<GeboChatMessageEnvelope> intermediateStreamingFlux = resultFlux.map(x -> {
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
		Flux<GeboChatMessageEnvelope> finalFlux = Flux.concat(intermediateStreamingFlux, finalMessages);
		finalFlux.publishOn(threadManager.getScheduler()).doOnComplete(() -> {
			runAs.doAs(() -> {
				try {
					this.chunkingService.disposeChunkingSession(chunkSessionId);
				} catch (Throwable th) {
				}

				try {
					sessionLifecycleService.chatRequestCompleted(request, chatModel);
				} catch (Throwable e) {
					LOGGER.error("Error completing request", e);
				}
			});
		});
		return finalFlux;
	}

	private Flux<String> generateDeepSearchFlux(Flux<Document> docsFlux, IChatRequestContext context,
			ReactiveIdentityUtil runAs, ISinkUIEmitter sinkUIEmitter, GeboChatRequest request,
			GPromptTemplateConfig cumulativeAnalisysPrompt, GPromptTemplateConfig emptyResponsePrompt,
			GPromptTemplateConfig finalAnalisysPrompt, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) {
		final long tokensBudget = serviceModel.getContextLength() * 2 / 3;
		final Flux<String> backupNotFoundDocuments = Flux.defer(() -> {
			Flux<String> outFlux = null;
			try {
				Map<String, Object> params = new HashMap<>();
				params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, "");
				params.put(CONSOLIDATED_SUMMARY_PROMPT_PARAM, "");
				outFlux = callLLMReactive(chatModel, emptyResponsePrompt, context, params);
			} catch (Throwable th) {
				sinkUIEmitter.notifyLLMProblems();
				LOGGER.error(EXCEPTION_ON_EMPTY_RESULTS, th);
				outFlux = Flux.just(SORRY_SOMETHING_GONE_WRONG);
			}
			return outFlux;
		});
		GenerativeFunction<Document, String> intermediateProcess = (initialValue, _emitter, documentsList) -> {

			return runAs.doRunAsWithReturnAndException(() -> {
				return callLLMWithDocumentsAndConsolidation(serviceModel, cumulativeAnalisysPrompt, context,
						documentsList, initialValue);
			});

		};
		LastWork<String, String> finalAnalisysWork = (list, _emitter) -> {
			return runAs.doRunAsWithReturnAndException(() -> {
				if (list != null && !list.isEmpty()) {

					Map<String, Object> params = new HashMap<>();
					params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, list);
					params.put(CONSOLIDATED_TEMPLATE_VARIABLE, "");
					params.put(AGENT_DELIVERABLE_COMPLETENESS, request.getUserIntent().name());
					return callLLMReactive(chatModel, finalAnalisysPrompt, context, params);

				} else {
					return backupNotFoundDocuments;
				}
			});
		};

		Predicate<Document> isValidDocument = (document) -> document.isText() && document.getText() != null
				&& document.getText().trim().length() > 0;
		TokensLimitCompute<Document> tokensLimitCompute = (list, budget) -> TokensBudgetCalculator
				.higherThanBudget(list, budget);
		Predicate<String> outOfBandString = (v) -> v == null || v.equals(ERROR_IN_PROCESS);
		Predicate<String> isEndOfProcessingCondition = (text) -> text != null
				&& text.toUpperCase().contains(PARTIAL_ANALISYS_SATISFACTORY);
		Function<String, String> outputShortCutFunction = (text) -> text.replace(PARTIAL_ANALISYS_SATISFACTORY, "");
		Flux<String> resultFlux = null;

		resultFlux = TokensBudgetFluxCoordinator.tokenBudgetCoordinate(docsFlux, sinkUIEmitter, isValidDocument,
				tokensLimitCompute, intermediateProcess, finalAnalisysWork, "", ERROR_IN_PROCESS, outOfBandString,
				ERROR_IN_PROCESS, outOfBandString, isEndOfProcessingCondition, outputShortCutFunction, tokensBudget);
		return resultFlux;
	}
}
