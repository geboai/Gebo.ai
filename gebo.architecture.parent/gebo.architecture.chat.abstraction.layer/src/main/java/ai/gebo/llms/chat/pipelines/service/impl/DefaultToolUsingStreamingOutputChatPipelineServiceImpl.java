package ai.gebo.llms.chat.pipelines.service.impl;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import reactor.core.publisher.Flux;

public class DefaultToolUsingStreamingOutputChatPipelineServiceImpl implements IStreamingOutputChatPipelineService {

	public static final String DEFAULT_TOOL_USING_STREAMING = "default-tool-using-streaming";

	public DefaultToolUsingStreamingOutputChatPipelineServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public StepExecutorType getExecutorType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStepId() {
		
		return DEFAULT_TOOL_USING_STREAMING;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		// TODO Auto-generated method stub
		return null;
	}

}
