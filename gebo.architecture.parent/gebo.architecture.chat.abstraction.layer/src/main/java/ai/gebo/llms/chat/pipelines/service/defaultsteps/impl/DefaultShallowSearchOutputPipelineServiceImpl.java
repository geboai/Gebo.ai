package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter.StepEnvironmentType;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGDeepSearchDataSourceExecutor;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveEnabledDeepSearchDataSourceLookupService;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class DefaultShallowSearchOutputPipelineServiceImpl implements IStreamingOutputChatPipelineService {
	static final Logger LOGGER = LoggerFactory.getLogger(DefaultShallowSearchOutputPipelineServiceImpl.class);
	static final String CHOOSED_DATASOURCE_HANDLER = "CHOOSED-DATASOURCE-HANDLER";
	static final String DEFAULT_SHALLOW_SEARCH_STREAMING_OUTPUT = "default-shallow-search-streaming-output";
	private static final StepEnvironmentParameter searchedSystemParam = new StepEnvironmentParameter(
			DefaultRoutingChatPipelineStepServiceImpl.SEARCHED_SYSTEM, StepEnvironmentType.STRING_LIST);
	private final IGReactiveEnabledDeepSearchDataSourceLookupService enabledLookupService;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	private final IGDeepSearchDataSourceExecutor executor;

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_SHALLOW_SEARCH_STREAMING_OUTPUT;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException {
		DeepSearchConfig config = deepSearchConfigProvider.get();
		Object _choosedSourceId = runtimeData.getSharedEnvironment()
				.get(DefaultRoutingChatPipelineStepServiceImpl.SEARCHED_SYSTEM);
		String choosedSourceId = null;
		if (_choosedSourceId != null && _choosedSourceId instanceof List list && !list.isEmpty()) {
			choosedSourceId = list.get(0).toString();
		}
		if (_choosedSourceId == null) {
			LOGGER.error("No choosen source passed");
			List<IGReactiveDeepSearchDataSourceService> handlers = enabledLookupService.enabledDataSources(config);
			if (!handlers.isEmpty()) {
				_choosedSourceId = handlers.get(0).getHandlerId();
			}
		}
		MinimalChatContext minimalChatContext = runtimeData.getMinimalChatContext();

		IGReactiveDeepSearchDataSourceService handler = enabledLookupService.enabledDataSourceByCode(choosedSourceId,
				config);
		try {
			return executor.execute(handler, runtimeData.getRequestResources().getCurrentRequest(), minimalChatContext,
					runtimeData.getChatResponse(), chatModel, serviceModel);
		} catch (LLMConfigException | IOException | GeboIngestionException | GeboContentHandlerSystemException
				| SearchServiceException e) {
			throw new ChatPipelineException("Pipeline broken on search", e);
		}
	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {

		return List.of(searchedSystemParam);
	}

}
