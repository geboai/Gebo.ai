package ai.gebo.atlassian.confluence.cloud.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CloudConfluenceSearchPageResponseSearchResult {
	@Data
	public static class CloudConfluenceSearchResultContent extends CloudConfluenceListItem {

		CloudConfluenceSpacesListItem space = null;
	}

	@Data
	public static class CloudConfluenceSearchResult {
		private String title = null, url = null, entityType = null;
		private Double score = null;
		private CloudConfluenceSearchResultContent content = null;
	}

	private List<CloudConfluenceSearchResult> results = new ArrayList<CloudConfluenceSearchPageResponseSearchResult.CloudConfluenceSearchResult>();
}
