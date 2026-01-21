package ai.gebo.llms.chat.pipelines.model;

import java.util.List;

import ai.gebo.architecture.search.model.SearchQuery;

public interface IStepQueryContribution extends IStepContribution {
	public default Long getRenderedTokensLength() {
		return 0l;
	}
	public default StepContributionType getContributionType() {
		return StepContributionType.QUERY_CONTRIBUTION;
	}
	
}
