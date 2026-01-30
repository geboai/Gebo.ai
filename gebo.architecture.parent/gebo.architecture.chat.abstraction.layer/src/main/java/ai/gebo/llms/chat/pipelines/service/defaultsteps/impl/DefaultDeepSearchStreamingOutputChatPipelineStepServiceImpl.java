package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import lombok.AllArgsConstructor;
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
			IGDeepSearchService deepSearchService, IGeboThreadManager threadManager) {
		super(documentsCacheService, chatStorageAreaService, docreferenceRepo, uploadsRepo, generatedRepo);
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
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		LLMChatRequestResources request = super.integrateWithAISuggestedDocuments(runtimeData);
		List<String> aiChoosedDataSources = DefaultPipelineSharedEnvironmentUtil
				.getAISuggestedDeepSearchDataSources(runtimeData);
		try {
			Flux<AbstractDeepSearchEvent> flux = deepSearchService.streamDeepSearch(request,
					runtimeData.getChatResponse(), runtimeData.getUserChatContext(), aiChoosedDataSources);
			Flux<GeboChatMessageEnvelope> mapped = deepSearchService.mapToChatFlux(flux,
					DeepSearchChatResponseEvent.class);
			mapped.subscribeOn(threadManager.getScheduler());
			return mapped;
		} catch (LLMConfigException e) {
			throw new ChatPipelineException("Error executing deep search", e);
		}

	}

}
