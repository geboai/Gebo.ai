package ai.gebo.atlassian.jira.handler.search.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.atlassian.jira.handler.search.model.JiraIssueAttributeFilter.TextMatchMode;
import lombok.Data;

@Data
@JsonClassDescription("Additional search related to the actual analyzed text")
public class JiraAdditionalSearchFilter {
	@JsonPropertyDescription("List of jira issueKeys of the searched tasks/issues")
	List<String> issueKeys = null;
	@JsonPropertyDescription("List of jira terms for keyword/phrases search contained in the summary of issues/tasks")
	List<String> summaryTerms = null;
	TextMatchMode summaryTermsMatchMode = null;
	@JsonPropertyDescription("List of jira terms for keyword/phrases contained in the description of issues/tasks")
	List<String> descriptionTerms = null;
	TextMatchMode descriptionTermsMatchMode = null;
}
