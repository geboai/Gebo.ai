package ai.gebo.atlassian.jira.handler.search.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Filters on Jira Tasks/Issues attributes")
public class JiraIssueAttributeFilter {
	@JsonClassDescription("Group of keywords search matching policy")
	public static enum TextMatchMode {
		ANY, ALL
	};

	@JsonPropertyDescription("List of jira project codes of the searched tasks/issues")
	List<String> projectCodes = null;
	@JsonPropertyDescription("List of jira issue types of the searched tasks/issues")
	List<String> issuetypeCodes = null;
	@JsonPropertyDescription("List of jira issueKeys of the searched tasks/issues")
	List<String> issueKeys = null;
	@JsonPropertyDescription("List of jira terms for keyword/phrases search contained in the summary of issues/tasks")
	List<String> summaryTerms = null;
	TextMatchMode summaryTermsMatchMode = null;
	@JsonPropertyDescription("List of jira terms for keyword/phrases contained in the description of issues/tasks")
	List<String> descriptionTerms = null;
	TextMatchMode descriptionTermsMatchMode = null;
	@JsonPropertyDescription("List of jira labels contained in the labels field of issues/tasks")
	List<String> labels = null;
	TextMatchMode labelsMatchMode = null;
	@JsonPropertyDescription("List of jira priorities of issues/tasks")
	List<String> priorityCodes = null;
	@JsonPropertyDescription("List of jira status of issues/tasks")
	List<String> statusCodes = null;
	@JsonPropertyDescription("List of affected versions of the artifact the issues/tasks regarding a defect or bug belongs to")
	List<String> affectedVersions = null;
	@JsonPropertyDescription("List of released versions of the artifact the issues/tasks is starting to be included ")
	List<String> fixVersions = null;
}