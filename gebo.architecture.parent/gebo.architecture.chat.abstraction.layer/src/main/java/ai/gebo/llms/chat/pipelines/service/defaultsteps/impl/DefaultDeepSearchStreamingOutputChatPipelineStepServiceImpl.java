package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.BaseOutputChatPipelineService.DocumentsEnrichDecision;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import reactor.core.publisher.Flux;

@Component
public class DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl extends BaseOutputChatPipelineService
		implements IStreamingOutputChatPipelineService {
	private final IGDeepSearchService deepSearchService;
	private final IGeboThreadManager threadManager;
	public static final String DEFAULT_DEEPSEARCH_STREAMING = "default-deepsearch-streaming";

	public DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl(IGAIDocumentsCacheService documentsCacheService,
			IGChatStorageAreaService chatStorageAreaService, DocumentReferenceRepository docreferenceRepo,
			UserUploadContentServerSideRepository uploadsRepo, LLMGeneratedResourceRepository generatedRepo,
			IGChatSessionLifeCycleService chatSessionLifecycleService, ChatPipelinesConfiguration configuration,
			IGPromptConfigDao promptsDao, IGDocumentsSearchService searchesService, IGeboThreadManager threadManager,
			IGDeepSearchService deepSearchService) {

		super(documentsCacheService, chatStorageAreaService, docreferenceRepo, uploadsRepo, generatedRepo,
				chatSessionLifecycleService, configuration, promptsDao, searchesService);
		this.deepSearchService = deepSearchService;
		this.threadManager = threadManager;
	}

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_DEEPSEARCH_STREAMING;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException {

		try {
			Flux<DocumentsEnrichDecision> enrichDecision = super.doDocumentsRetrieve(runtimeData, serviceModel,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);

			Flux<AbstractDeepSearchEvent> flux = enrichDecision.concatMap(ed -> {
				List<String> aiChoosedDataSources = ed.getSearchesDecisions().getDeepSearchDataSources();
				try {
					return deepSearchService.streamDeepSearch(ed.getRequestResources(), runtimeData.getChatResponse(),
							chatModel, serviceModel, aiChoosedDataSources);
				} catch (LLMConfigException | GeboChatSessionLifecycleException e) {
					String msg = "Nested exception in deferred stream creation";
					throw new RuntimeException(msg, e);
				}
			});
			Flux<GeboChatMessageEnvelope> mapped = deepSearchService.mapToChatFlux(flux,
					DeepSearchChatResponseEvent.class);
			flux.doOnComplete(() -> {
				try {
					this.chatSessionLifecycleService
							.chatRequestCompleted(runtimeData.getRequestResources().getLastRequest(), chatModel);
				} catch (GeboChatSessionLifecycleException | LLMConfigException | IOException e) {
					LOGGER.error("Exceptinin deep search streaming pipeline handler", e);
				}
			});
			mapped.subscribeOn(threadManager.getScheduler());
			return mapped;
		} catch (LLMConfigException | FullTextException e) {
			throw new ChatPipelineException("Error executing deep search", e);
		}

	}

}
