package ai.gebo.architecture.search.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
public class WebSearchResultsExtractionData extends BaseSearchResultsExtractionDataType {
	@Data
	public static class RelevantLink {
		String url = null;
		String title = null;
		String displayText = null;
	}

	@JsonPropertyDescription("Relevant links to analyze exported from the page content")
	private List<RelevantLink> extractedRelevantLinks = new ArrayList();
	@JsonPropertyDescription("Further analisable search query to add more details to actual informations")
	private List<SearchQuery> extractedRelatedSearches = new ArrayList<SearchQuery>();
}