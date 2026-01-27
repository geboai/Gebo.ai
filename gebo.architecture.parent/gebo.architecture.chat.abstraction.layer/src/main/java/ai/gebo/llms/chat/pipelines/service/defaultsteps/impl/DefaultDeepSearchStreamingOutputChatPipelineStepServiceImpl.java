package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl extends BaseOutputChatPipelineService
		implements IStreamingOutputChatPipelineService {
	private final IGDeepSearchService deepSearchService;
	public static final String DEFAULT_DEEPSEARCH_STREAMING = "default-deepsearch-streaming";

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
			return deepSearchService.mapToChatFlux(flux, DeepSearchChatResponseEvent.class);
		} catch (LLMConfigException e) {
			throw new ChatPipelineException("Error executing deep search", e);
		}

	}

}
