package ai.gebo.atlassian.confluence.handler.search.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.KeywordListBuilder;
import lombok.Data;

@Data
@JsonClassDescription("Confluence content search filter (CQL generator input)")
public class ConfluenceContentSearchFilter implements INativeQueryObject {

	@JsonPropertyDescription("Filters on Confluence content attributes")
	private ConfluenceContentAttributeFilter contentAttributesFilter = new ConfluenceContentAttributeFilter();

	@JsonPropertyDescription("Filters on people participating in the content lifecycle")
	private ConfluencePeopleFilter peopleFilter = new ConfluencePeopleFilter();

	@Override
	public List<String> relevantKeywords() {
		KeywordListBuilder builder = new KeywordListBuilder();
		builder.addKeywordsProvider(() -> {
			return contentAttributesFilter.getTitleTerms();
		});
		builder.addKeywordsProvider(() -> {
			return contentAttributesFilter.getTextTerms();
		});
		return builder.getKeywords();

	}
}