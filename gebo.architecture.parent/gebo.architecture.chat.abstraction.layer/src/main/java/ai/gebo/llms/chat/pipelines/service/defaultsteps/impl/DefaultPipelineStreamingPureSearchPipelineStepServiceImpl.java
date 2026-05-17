package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.LLMInputDocument;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GInputProcessingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptsParametersCacheService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IDataSourcesCatalogsService;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.datasources.model.AbstractPureSearchDocumentResultEntry;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;
import ai.gebo.llms.deepsearch.datasources.model.PureSearchDocumentResultError;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class DefaultPipelineStreamingPureSearchPipelineStepServiceImpl extends BaseLLMSInvokingService
		implements IStreamingOutputChatPipelineService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DefaultPipelineStreamingPureSearchPipelineStepServiceImpl.class);
	public static final String SEARCHED_SYSTEMS = "searchedSystems";
	public static final String PURE_SEARCH_STREAMING_SERVICE = "PURE_SEARCH_STREAMING_SERVICE";
	private final IDataSourcesCatalogsService deepSearchDataSourcesCatalogsService;
	private final IGChatSessionLifeCycleService chatSessionLifecycleService;
	private final IGPromptConfigDao promptsDao;
	private final IGPromptsParametersCacheService promptsParamsCacheService;
	private final IGDeepSearchService deepSearchService;
	private final IGRankerService rankerService;

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return PURE_SEARCH_STREAMING_SERVICE;
	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {

		return List.of();
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException, GeboChatException,
			IOException {
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final int perDataSourceK = 200;
		final int globalK = 100;
		final int textSampleTokensSize = 4096;
		final GeboChatResponse response = runtimeData.getChatResponse();
		sinkUIEmitter.notifyUser("selectDataSources", "Selecting sarch data sources", "pi pi-search", 3000l,
				NotificationType.INFO);
		Flux<List<String>> dataSources = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				List<String> sources = this.chooseDataSources(runtimeData, serviceModel);
				return Flux.just(sources);
			});
		});
		Flux<AbstractPureSearchDocumentResultEntry> resultsFlux = dataSources.concatMap((dataSourcesValues) -> {
			return runAs.doRunAsWithReturn(() -> {

				Flux<AbstractPureSearchDocumentResultEntry> documentsFlux;
				try {
					documentsFlux = this.deepSearchService.streamPureSearch(runtimeData.getRequestResources(),
							runtimeData.getMinimalChatContext(), runtimeData.getRequestResources().getCurrentRequest(),
							sinkUIEmitter, chatModel, serviceModel, dataSourcesValues, perDataSourceK, globalK,
							textSampleTokensSize);
					return documentsFlux;
				} catch (Throwable e) {
					LOGGER.error("Exception while running the search", e);
					PureSearchDocumentResultError error = new PureSearchDocumentResultError(null, null,
							GUserMessage.warnMessage("Exception while running the search", e.getMessage()));
					return Flux.just(error);
				}
			});
		});
		Vector<AbstractPureSearchDocumentResultEntry> container = new Vector<>();

		Flux<GeboChatMessageEnvelope> chatMessageEnvironmentFlux = resultsFlux.map(entry -> {
			if (entry.getDocument() != null) {
				container.add(entry);
			}
			GeboChatMessageEnvelope envelope = createInputProcessingEnvelope(entry);
			return envelope;
		});
		Flux<GeboChatMessageEnvelope> rankingFlux = this.createRankingFlux(runAs, sinkUIEmitter, runtimeData, container,
				globalK);
		Flux<GeboChatMessageEnvelope> trailingFlux = this.createTrailingFlux(runAs, response, sinkUIEmitter,
				runtimeData, container, chatModel);
		return Flux.concat(chatMessageEnvironmentFlux, rankingFlux, trailingFlux).onErrorResume(exc -> {
			final String msg = "Error while streaming chat respose";
			LOGGER.error(msg, exc);
			GeboChatMessageEnvelope<GUserMessage> exceptionEnvelope = new GeboChatMessageEnvelope<GUserMessage>();
			GUserMessage userMessage = GUserMessage.errorMessage(msg, exc);
			exceptionEnvelope.setContent(userMessage);
			return Flux.just(exceptionEnvelope);
		}).doOnComplete(() -> {
			runAs.doAs(() -> {
				try {
					this.chatSessionLifecycleService.endRequest(runtimeData.getRequestResources().getCurrentRequest(),
							response);
				} catch (Throwable th) {
					LOGGER.error("Error saving user context", th);
				} finally {

				}
				try {
					this.chatSessionLifecycleService
							.chatRequestCompleted(runtimeData.getRequestResources().getCurrentRequest(), chatModel);
				} catch (GeboChatSessionLifecycleException | LLMConfigException | IOException e) {
					LOGGER.error("Error closing response flux with chatSessionLifecycle code", e);
				}
			});
		});
	}

	private GeboChatMessageEnvelope createInputProcessingEnvelope(AbstractPureSearchDocumentResultEntry entry) {
		if (entry instanceof PureSearchDocumentResultError error) {
			return new GeboChatMessageEnvelope(error.getUserMessage());
		}
		return new GeboChatMessageEnvelope(new GInputProcessingEvent(GResponseDocumentRef.of(entry.getDocument())));
	}

	private Flux<GeboChatMessageEnvelope> createRankingFlux(ReactiveIdentityUtil runAs, ISinkUIEmitter sinkUIEmitter,
			ChatPipelineExecutionRuntimeData runtimeData, Vector<AbstractPureSearchDocumentResultEntry> container,
			int globalK) {

		Flux<AbstractPureSearchDocumentResultEntry> rankedFlux = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				List<AbstractPureSearchDocumentResultEntry> rankedEntries = new ArrayList<>();
				if (this.rankerService.isRankerConfigured()) {
					sinkUIEmitter.notifyUser("rankingDocuments", "Ranking documents", "pi pi-ai", 3000l,
							NotificationType.INFO);
					String query = GeboChatRequest.actualQuery(runtimeData.getRequestResources().getCurrentRequest());
					Map<String, AbstractPureSearchDocumentResultEntry> mappedByDocumentId = new HashMap<>();
					List<Document> documents = new ArrayList<>();
					try {
						for (AbstractPureSearchDocumentResultEntry entry : container) {
							Document document = new Document(UUID.randomUUID().toString(), entry.getSampleText(),
									Map.of());
							mappedByDocumentId.put(document.getId(), entry);
							documents.add(document);
						}

						List<Document> ranked = this.rankerService.call(documents, query, globalK);
						for (Document rank : ranked) {
							AbstractPureSearchDocumentResultEntry entry = mappedByDocumentId.get(rank.getId());
							if (entry != null) {
								rankedEntries.add(entry);
							}
						}
					} catch (Throwable th) {
						List<AbstractPureSearchDocumentResultEntry> trunked = container.subList(0, globalK);
						rankedEntries.addAll(trunked);
					}

				} else {
					List<AbstractPureSearchDocumentResultEntry> trunked = container.subList(0, globalK);
					rankedEntries.addAll(trunked);
				}
				container.clear();
				container.addAll(rankedEntries);
				return Flux.fromIterable(rankedEntries);
			});
		});
		return rankedFlux.map(this::createEnvelope);
	}

	private Flux<GeboChatMessageEnvelope> createTrailingFlux(ReactiveIdentityUtil runAs, GeboChatResponse response,
			ISinkUIEmitter sinkUIEmitter, ChatPipelineExecutionRuntimeData runtimeData,
			Vector<AbstractPureSearchDocumentResultEntry> ranked, IGConfigurableChatModel chatModel) {

		Flux<GeboChatMessageEnvelope> firstFullResponseSending = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {

				List<GResponseDocumentRef> docsRefs = ranked.stream().filter(x -> x.getDocument() != null)
						.map(y -> GResponseDocumentRef.of(y.getDocument())).toList();
				response.setDocumentsRef(docsRefs);
				GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(response);
				envelope.setLastMessage(false);
				return Flux.just(envelope);
			});
		});
		Flux<String> reactiveLLMOutput = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				Stream<LLMInputDocument> inputStream = ranked.stream().map(entry -> {
					LLMInputDocument inputDocument = createInputDocument(entry.getDocument(), entry.getSampleText());
					return inputDocument;
				}).filter(x -> x != null).sequential();
				MinimalChatContext minimalContext = runtimeData.getMinimalChatContext();

				String prompt = promptsDao
						.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_PURE_SEARCH_SUMMARY_PROMPT).getPrompt();
				Map<String, Object> params = CommonChatPromptParamsUtil.preparePromptParameters(minimalContext);
				String query = GeboChatRequest.actualQuery(minimalContext.getCurrentRequest());
				try {
					return super.callLLMReactive(chatModel, prompt, query, params, inputStream);
				} catch (Throwable th) {
					LOGGER.error("Error whill calling callLLMReactive", th);
					throw new RuntimeException(th.getMessage(), th);
				}
			});
		});
		StringBuffer buffer = new StringBuffer();
		Flux<GeboChatMessageEnvelope> streamingText = reactiveLLMOutput.map(string -> {
			buffer.append(string);
			return new GeboChatMessageEnvelope(string);
		});
		Flux<GeboChatMessageEnvelope> trailingEnd = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				List<GeboChatMessageEnvelope> trailings = new ArrayList<>();
				response.setQueryResponse(buffer.toString());
				GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(response);
				envelope.setLastMessage(true);
				trailings.add(envelope);
				trailings.add(GeboChatMessageEnvelope.FINAL_MESSAGE);
				return Flux.fromIterable(trailings);
			});
		});

		return Flux.concat(firstFullResponseSending, streamingText, trailingEnd);
	}

	private LLMInputDocument createInputDocument(IGComponentOriginatedDocument document, String sampleText) {
		if (document instanceof ai.gebo.architecture.search.model.SearchResult actualSearchResultToLoad) {
			LLMInputDocument cInput = new LLMInputDocument(actualSearchResultToLoad.getResultReference().getName(),
					actualSearchResultToLoad.getResultReference().getUri(),
					actualSearchResultToLoad.getResultReference().getTitle(), sampleText);
			return cInput;
		}
		if (document instanceof GDocumentReference reference) {
			String title = reference.getName();
			Map<String, Object> metaInfos = reference.getCustomMetaInfos();
			if (metaInfos != null && metaInfos.containsKey(DocumentMetaInfos.TITLE)) {
				title = (String) metaInfos.get(DocumentMetaInfos.TITLE);
			}
			LLMInputDocument cInput = new LLMInputDocument(reference.getName(), reference.getUri(), title, sampleText);
			return cInput;
		}
		return null;
	}

	private GeboChatMessageEnvelope createEnvelope(AbstractPureSearchDocumentResultEntry entry) {

		return new GeboChatMessageEnvelope(GResponseDocumentRef.of(entry.getDocument()));
	}

	private List<String> chooseDataSources(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel serviceModel) {
		GPromptTemplateConfig _prompt = this.promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_PURE_SEARCH_CHOSE_DATASOURCES_PROMPT);
		final String prompt = _prompt.getPrompt();
		Supplier<Map<String, Object>> paramsProvider = () -> {
			try {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Begin Calculating router params to be cached");
				}
				List<GKnowledgeBase> knowledgeBases = this.chatSessionLifecycleService
						.getSessionAvailableKnowledgeBases(runtimeData.getRequestResources().getCurrentRequest());
				Map<String, Object> templateParams = new HashMap<String, Object>();

				String deepSearchDataSources = deepSearchDataSourcesListPromptPart(knowledgeBases);
				templateParams.put(DefaultPipelineSharedPromptPlaceholders.DEEP_SEARCH_DATA_SOURCES_TEMPLATE_PARAM,
						deepSearchDataSources);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End Calculating router params to be cached");
				}
				return templateParams;
			} catch (Throwable th) {
				LOGGER.error("Error in template params loader", th);
				throw new RuntimeException("Error in template params loader", th);
			}
		};
		String rewrited_query = GeboChatRequest.actualQuery(runtimeData.getRequestResources().getCurrentRequest());
		Map<String, Object> params = CommonChatPromptParamsUtil
				.preparePromptParameters(runtimeData.getMinimalChatContext());
		params.put(DefaultRoutingChatPipelineStepServiceImpl.REWRITTEN_QUERY_TEMPLATE_PARAM, rewrited_query);
		final Map<String, Object> cachedParams = this.promptsParamsCacheService.lookupCache(
				GeboPromptsLibrary.DEFAULT_PIPELINE_PURE_SEARCH_CHOSE_DATASOURCES_PROMPT,
				runtimeData.getRequestResources().getCurrentRequest().getUserChatContextCode(),
				DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP, 120000, paramsProvider);
		params.putAll(cachedParams);
		int variablesTokensSize = ITokensCountable.tokensSize(params);
		int promtTokensSize = ITokensCountable.stringsTokensSize(prompt);
		int documentsTokenBudget = serviceModel.getContextLength() - (variablesTokensSize + promtTokensSize);

		String documents = documentsTokenBudget > 0
				? RoutingPromptUtil.documentsPromptPart(runtimeData.getRequestResources(), documentsTokenBudget)
				: "";
		params.put("documents", documents);
		Map<String, List<String>> toBeSearched = this.callLLMRepeatableFieldEntryOutput(serviceModel, prompt,
				rewrited_query, params, List.of(SEARCHED_SYSTEMS));

		return toBeSearched.containsKey(SEARCHED_SYSTEMS) ? toBeSearched.get(SEARCHED_SYSTEMS)
				: List.of(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID);
	}

	private String deepSearchDataSourcesListPromptPart(List<GKnowledgeBase> knowledgeBases) {
		StringBuffer buffer = new StringBuffer();
		List<DeepSearchDataSourceMetaInfos> dataSources = deepSearchDataSourcesCatalogsService
				.getActiveDeepSearchDataSourceMetaInfos();
		buffer.append(RoutingPromptUtil.dataSourcesListPromptPart(dataSources, knowledgeBases));
		return buffer.toString();
	}

}
