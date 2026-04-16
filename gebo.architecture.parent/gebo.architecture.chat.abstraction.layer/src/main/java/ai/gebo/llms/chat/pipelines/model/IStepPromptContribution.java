package ai.gebo.llms.chat.pipelines.model;

public interface IStepPromptContribution extends IStepContribution {
	public default StepContributionType getContributionType() {
		return StepContributionType.PROMPT_CONTRIBUTION;
	}
	public String getPromptTemplatePlaceholderId();
	public String render();
}
