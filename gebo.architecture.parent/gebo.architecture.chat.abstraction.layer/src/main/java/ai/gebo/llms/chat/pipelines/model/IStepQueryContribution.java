package ai.gebo.llms.chat.pipelines.model;

public interface IStepQueryContribution extends IStepContribution {
	public default Long getRenderedTokensLength() {
		return 0l;
	}
	public default StepContributionType getContributionType() {
		return StepContributionType.QUERY_CONTRIBUTION;
	}
	
}
