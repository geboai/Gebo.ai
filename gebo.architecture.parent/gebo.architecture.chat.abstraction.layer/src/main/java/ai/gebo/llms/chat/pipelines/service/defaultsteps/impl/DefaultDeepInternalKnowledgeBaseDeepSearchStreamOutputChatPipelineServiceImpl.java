package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GInputProcessingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetCalculator;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.GenerativeFunction;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.LastWork;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.TokensLimitCompute;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IInternalKnowledgeLLMAssistedRetrieveService;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.repository.DeepSearchRequestRepository;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class DefaultDeepInternalKnowledgeBaseDeepSearchStreamOutputChatPipelineServiceImpl
		extends BaseLLMSInvokingService implements IStreamingOutputChatPipelineService {

	private static final String ERROR_IN_PROCESS = "<!-ERROR-IN-PROCESS->";
	private static final String PARTIAL_ANALISYS_SATISFACTORY = "<IS-COMPLETELY-SATISFACTORY/>";
	public static final String DEFAULT_DEEPRAG_STREAMING = "default-deeprag-streaming";
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DefaultDeepInternalKnowledgeBaseDeepSearchStreamOutputChatPipelineServiceImpl.class);

	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	private final IGChatSessionLifeCycleService sessionLifecycleService;
	private final IGSecurityService securityService;
	private final IDocumentsChunkService chunkingService;
	private final IGeboThreadManager threadManager;
	private final IGPromptConfigDao promptsDao;
	private final DeepSearchRequestRepository deepSearchRequestRepository;
	private final IInternalKnowledgeLLMAssistedRetrieveService assistedSearch;

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_DEEPRAG_STREAMING;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException {
		try {
			// allocate util to runAs actual user's identity
			ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
			// get the context for llm calls
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
			final Map<String, GResponseDocumentRef> docrefs = new Hashtable<>();
			GenerativeFunction<Document, String> intermediateProcess = (initialValue, emitter, documentsList) -> {
				return runAs.doRunAsWithReturnAndException(() -> {
					return callLLMWithDocumentsAndConsolidation(serviceModel, cumulativeAnalisysPrompt, context,
							documentsList, initialValue);
				});

			};
			LastWork<String, String> finalAnalisysWork = (list, emitter) -> {
				return runAs.doRunAsWithReturnAndException(() -> {
					if (list != null && !list.isEmpty()) {
						Map<String, Object> params = new HashMap<>();
						params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, list);
						params.put(CONSOLIDATED_TEMPLATE_VARIABLE, "");
						params.put("agentDeliverableCompleteness", request.getUserIntent().name());
						return callLLMReactive(chatModel, finalAnalisysPrompt, context, params);
					} else {
						return callLLMReactive(chatModel, finalAnalisysPrompt, context, Map.of());
					}
				});
			};
			Flux<Document> chatWithDocumentsFlux = runtimeData.getRequestResources().getChatWithDocuments() != null
					? Flux.fromIterable(runtimeData.getRequestResources().getChatWithDocuments().aiDocumentsList())
					: Flux.empty();
			Flux<Document> searchFlux = assistedSearch.doDocumentsRetrieve(runtimeData.getMinimalChatContext(),
					serviceModel, LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET, 50)
					.flatMap(x -> {
						List<Document> docsList = x.aiDocumentsList();
						docsList.forEach(doc -> {
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
						});
						return Flux.fromIterable(docsList);
					});
			Flux<Document> documentFlux = Flux.concat(searchFlux, chatWithDocumentsFlux);
			Predicate<Document> isValidDocument = (document) -> document.isText() && document.getText() != null
					&& document.getText().trim().length() > 0;
			TokensLimitCompute<Document> tokensLimitCompute = (list, budget) -> TokensBudgetCalculator
					.higherThanBudget(list, budget);
			Predicate<String> outOfBandString = (v) -> v == null || v.equals(ERROR_IN_PROCESS);
			Predicate<String> isEndOfProcessingCondition = (text) -> text != null
					&& text.toUpperCase().contains(PARTIAL_ANALISYS_SATISFACTORY);
			Function<String, String> outputShortCutFunction = (text) -> text.replace(PARTIAL_ANALISYS_SATISFACTORY, "");
			Flux<String> resultFlux = TokensBudgetFluxCoordinator.tokenBudgetCoordinate(documentFlux, sinkUIEmitter,
					isValidDocument, tokensLimitCompute, intermediateProcess, finalAnalisysWork, "", ERROR_IN_PROCESS,
					outOfBandString, ERROR_IN_PROCESS, outOfBandString, isEndOfProcessingCondition,
					outputShortCutFunction, tokensBudget);
			final StringBuffer cumulative = new StringBuffer();
			Flux<GeboChatMessageEnvelope> intermediateStreamingFlux = resultFlux.map(x -> {
				cumulative.append(x);
				return x;
			}).map(piece -> new GeboChatMessageEnvelope<>(piece));
			Flux<GeboChatMessageEnvelope> finalMessages = Flux.defer(() -> {
				return runAs.doRunAsWithReturn(() -> {
					response.setQueryResponse(cumulative.toString());
					response.setDocumentsRef(new ArrayList<>(docrefs.values()));
					GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(response);
					envelope.setLastMessage(true);
					return Flux.fromIterable(List.of(envelope, GeboChatMessageEnvelope.FINAL_MESSAGE));
				});
			});
			Flux<GeboChatMessageEnvelope> finalFlux = Flux.concat(intermediateStreamingFlux, finalMessages);
			finalFlux.publishOn(threadManager.getScheduler()).doOnComplete(() -> {
				runAs.doAs(() -> {

					try {
						sessionLifecycleService.endRequest(request, response);
					} catch (Throwable e) {
						LOGGER.error("Error ending request", e);
					}
					try {
						sessionLifecycleService.chatRequestCompleted(request, chatModel);
					} catch (Throwable e) {
						LOGGER.error("Error completing request", e);
					}
				});
			});
			return finalFlux;
		} catch (Throwable e) {
			LOGGER.error("Error calling internal knowledge base executor", e);
			GUserMessage errorMessage = GUserMessage.errorMessage("Cannot execute internal knowledge base deep search",
					e);
			return Flux.just(new GeboChatMessageEnvelope(errorMessage));
		}

	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {

		return List.of();
	}

}
