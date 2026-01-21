package ai.gebo.llms.chat.pipelines.model;

public interface IStepContribution {
	public StepContributionType getContributionType();

	public Long getRenderedTokensLength();
}
