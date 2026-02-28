package ai.gebo.atlassian.confluence.handler.search.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Confluence content search filter (CQL generator input)")
public class ConfluenceContentSearchFilter {

	@JsonPropertyDescription("Filters on Confluence content attributes")
	private ConfluenceContentAttributeFilter contentAttributesFilter = new ConfluenceContentAttributeFilter();

	@JsonPropertyDescription("Filters on people participating in the content lifecycle")
	private ConfluencePeopleFilter peopleFilter = new ConfluencePeopleFilter();
}