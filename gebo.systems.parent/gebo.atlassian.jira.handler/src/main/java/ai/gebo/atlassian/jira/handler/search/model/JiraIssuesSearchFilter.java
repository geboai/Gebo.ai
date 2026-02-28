package ai.gebo.atlassian.jira.handler.search.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Jira issue search filter")
public class JiraIssuesSearchFilter {
	@JsonPropertyDescription("filters on issue or task or epic attributes")
	private JiraIssueAttributeFilter issuesAttributesFilter = new JiraIssueAttributeFilter();
	@JsonPropertyDescription("filters on people participating the issue lifecycle")
	private JiraPeopleFilter peopleFilter = new JiraPeopleFilter();

}
