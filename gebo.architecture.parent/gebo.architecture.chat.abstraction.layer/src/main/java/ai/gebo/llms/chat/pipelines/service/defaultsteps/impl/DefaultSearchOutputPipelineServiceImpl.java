package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
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
public class DefaultSearchOutputPipelineServiceImpl implements IStreamingOutputChatPipelineService {
	static final String CHOOSED_DATASOURCE_HANDLER = "CHOOSED-DATASOURCE-HANDLER";
	static final String DEFAULT_SEARCH_STREAMING_OUTPUT = "default-search-streaming-output";
	private final IGReactiveEnabledDeepSearchDataSourceLookupService enabledLookupService;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	private final IGDeepSearchDataSourceExecutor executor;

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_SEARCH_STREAMING_OUTPUT;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException {
		String choosedSourceId = (String) runtimeData.getSharedEnvironment().get(CHOOSED_DATASOURCE_HANDLER);
		DeepSearchConfig config = deepSearchConfigProvider.get();
		IGReactiveDeepSearchDataSourceService handler = enabledLookupService.enabledDataSourceByCode(choosedSourceId,
				serviceModel, config, null);
		try {
			return executor.execute(handler, runtimeData.getRequestResources().getLastRequest(),
					runtimeData.getChatResponse(), chatModel, serviceModel, runtimeData.getUserChatContext());
		} catch (LLMConfigException | IOException | GeboIngestionException | GeboContentHandlerSystemException
				| SearchServiceException e) {
			throw new ChatPipelineException("Pipeline broken on search", e);
		}
	}

}
