package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter.StepEnvironmentType;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchChatResponseEvent;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl
		implements IStreamingOutputChatPipelineService {
	private static final String ERROR_EXECUTING_DEEP_SEARCH = "Error executing deep search";
	private final IGDeepSearchService deepSearchService;
	private final IGeboThreadManager threadManager;
	private final IGChatSessionLifeCycleService chatSessionLifecycleService;
	private static final StepEnvironmentParameter searchedSystems = new StepEnvironmentParameter(
			DefaultRoutingChatPipelineStepServiceImpl.DEEP_SEARCHED_SYSTEMS, StepEnvironmentType.STRING_LIST);
	public static final String DEFAULT_DEEPSEARCH_STREAMING = "default-deepsearch-streaming";
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl.class);

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
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException {
		Object deepSearchedSystems = runtimeData.getSharedEnvironment()
				.get(DefaultRoutingChatPipelineStepServiceImpl.DEEP_SEARCHED_SYSTEMS);
		List<String> deepSearchDataSources = deepSearchedSystems instanceof List list ? list : List.of();
		try {
			
			if (deepSearchDataSources.isEmpty()) {
				// TODO: SEARCH ALL IDS
				deepSearchDataSources.add(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID);
			}
			return deepSearchService.streamNewDeepSearch(runtimeData, sinkUIEmitter, chatModel, serviceModel,
					deepSearchDataSources, 100, 30);

		} catch (LLMConfigException e) {
			LOGGER.error(ERROR_EXECUTING_DEEP_SEARCH, e);
			throw new ChatPipelineException(ERROR_EXECUTING_DEEP_SEARCH, e);
		}

	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {

		return List.of(searchedSystems);
	}

}
