package ai.gebo.atlassian.jira.search.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.KeywordListBuilder;
import lombok.Data;

@Data
@JsonClassDescription("Jira issue search filter")
public class JiraIssuesSearchFilter implements INativeQueryObject {
	@JsonPropertyDescription("filters on issue or task or epic attributes")
	private JiraIssueAttributeFilter issuesAttributesFilter = new JiraIssueAttributeFilter();
	@JsonPropertyDescription("filters on people participating the issue lifecycle")
	private JiraPeopleFilter peopleFilter = new JiraPeopleFilter();

	@Override
	public List<String> relevantKeywords() {
		KeywordListBuilder builder = new KeywordListBuilder();
		builder.addKeywordsProvider(() -> {
			return issuesAttributesFilter.getDescriptionTerms();
		});
		builder.addKeywordsProvider(() -> {
			return issuesAttributesFilter.getSummaryTerms();
		});
		return builder.getKeywords();

	}

}
