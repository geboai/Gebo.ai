package ai.gebo.googlesearch.handler.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import lombok.Data;

@Data
public class GoogleSearchResultsExtractionData extends BaseSearchResultsExtractionDataType {
	@Data
	public static class RelevantLink {
		String url = null;
		String title = null;
		String displayText = null;
	}

	private List<RelevantLink> extractedRelevantLinks = new ArrayList();
}