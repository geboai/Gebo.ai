package ai.gebo.llms.chat.pipelines.model;

import java.util.List;

public interface IChatPipelineStepRuntimeData {
	public String getStepId();

	public List<IStepContribution> getContextEnrichingContribution();

	public static IChatPipelineStepRuntimeData VoidRetun(final String stepId) {
		return new IChatPipelineStepRuntimeData() {

			@Override
			public String getStepId() {

				return stepId;
			}

			@Override
			public List<IStepContribution> getContextEnrichingContribution() {

				return List.of();
			}
		};
	}
}
