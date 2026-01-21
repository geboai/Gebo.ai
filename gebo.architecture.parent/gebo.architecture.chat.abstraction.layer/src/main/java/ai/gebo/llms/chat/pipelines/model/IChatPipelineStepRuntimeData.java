package ai.gebo.llms.chat.pipelines.model;

import java.util.List;

public interface IChatPipelineStepRuntimeData {
	public String getStepId();
	public List<IStepContribution> getContextEnrichingContribution();
}
