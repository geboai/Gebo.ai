package ai.gebo.llms.chat.pipelines.service;

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

	public default String getPipelineId() {
		return null;
	}

}
