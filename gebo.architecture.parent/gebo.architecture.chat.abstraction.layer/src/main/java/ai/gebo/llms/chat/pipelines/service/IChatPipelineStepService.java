package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.chat.pipelines.model.ModelUseInfos;

public interface IChatPipelineStepService {
	public static enum StepExecutorType {
		PROGRAMMATIC, LLM
	}

	public static enum StepType {
		INPUT, ROUTING, ENRICHING, OUTPUT
	}

	public StepType getStepType();

	public StepExecutorType getExecutorType();

	public String getStepId();

	/** Whether this step uses its chat model and what for: the admin configures its tools from this. */
	public ModelUseInfos getChatModelUseInfos();

	/** Whether this step uses its service model and what for: the admin configures its tools from this. */
	public ModelUseInfos getServiceModelUseInfos();

	public default String getPipelineId() {
		return null;
	}

}
