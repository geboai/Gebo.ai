package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IInputChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
@Component
public class DefaultInputChatPipelineStepServiceImpl implements IInputChatPipelineStepService {

	public static final String DEFAULT_INPUT_STEP = "default-input-step";

	@Override
	public IChatPipelineStepRuntimeData execute(ChatPipelineExecutionRuntimeData input,
			ISinkUIEmitter emitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		
		return IChatPipelineStepRuntimeData.VoidRetun(DEFAULT_INPUT_STEP);
	}

	@Override
	public StepExecutorType getExecutorType() {
		
		return StepExecutorType.PROGRAMMATIC;
	}

	@Override
	public String getStepId() {
		
		return DEFAULT_INPUT_STEP;
	}

	

}
