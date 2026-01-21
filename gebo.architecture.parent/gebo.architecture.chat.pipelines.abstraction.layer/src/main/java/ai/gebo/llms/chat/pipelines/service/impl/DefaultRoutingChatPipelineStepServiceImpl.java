package ai.gebo.llms.chat.pipelines.service.impl;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
@Component
public class DefaultRoutingChatPipelineStepServiceImpl implements IRoutingChatPipelineStepService {

	public static final String DEFAULT_ROUTING_STEP = "default-routing-step";

	public DefaultRoutingChatPipelineStepServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public StepExecutorType getExecutorType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStepId() {

		return DEFAULT_ROUTING_STEP;
	}

	

	@Override
	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		String candidateOutput = runtimeData.isStreamingOutput()
				? DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT
				: DefaultOutputChatPipelineServiceImpl.DEFAULT_OUTPUT_STEP;
		
		return null;
	}

}
