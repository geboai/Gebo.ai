package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IOutputChatPipelineService;

@Component
public class DefaultOutputChatPipelineServiceImpl extends BaseOutputChatPipelineService
		implements IOutputChatPipelineService {

	public static final String DEFAULT_OUTPUT_STEP = "default-output-step";

	public DefaultOutputChatPipelineServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public StepExecutorType getExecutorType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStepId() {

		return DEFAULT_OUTPUT_STEP;
	}

	@Override
	public GeboChatResponse execute(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		LLMChatRequestResources requestResources=super.integrateWithAISuggestedDocuments(runtimeData);
		return null;
	}

}
