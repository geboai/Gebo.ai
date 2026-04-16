package ai.gebo.llms.chat.pipelines.model;

public interface IStepContextContribution extends IStepContribution {
	public default StepContributionType getContributionType() {
		return StepContributionType.CONTEXT_CONTRIBUTION;
	}

	

	public String render();

}
