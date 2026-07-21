package ai.gebo.atlassian.jira.search.api;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.atlassian.jira.search.api.JiraAdditionalSearchFilter;
import lombok.Data;
@Data
public class JiraResultsExtractionData extends BaseSearchResultsExtractionDataType {
	@JsonPropertyDescription("Fill this data structure to suggest additional search to enrich the actual jira issues analisys")
	private JiraAdditionalSearchFilter additionalJiraSearchIdeas = null;

}
