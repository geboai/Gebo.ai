package ai.gebo.atlassian.confluence.handler.impl.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.atlassian.confluence.handler.search.model.ConfluenceAdditionalSearchFilter;
import lombok.Data;
@Data
public class ConfluenceResultsExtractionData extends BaseSearchResultsExtractionDataType {
	@JsonPropertyDescription("Fill this data structure to suggest additional search to enrich the actual confluence analisys")
	private ConfluenceAdditionalSearchFilter additionalConfluenceSearchIdeas = null;

}
