package ai.gebo.atlassian.confluence.onpremise.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OnPremiseConfluenceSearchPageResponseSearchResult {
	@Data
	public static class OnPremiseConfluenceSearchResultContent extends OnPremiseConfluenceListItem {

		OnPremiseConfluenceSpacesListItem space = null;
	}

	@Data
	public static class OnPremiseConfluenceSearchResult {
		private String title = null, url = null, entityType = null;
		private Double score = null;
		private OnPremiseConfluenceSearchResultContent content = null;
	}

	private List<OnPremiseConfluenceSearchResult> results = new ArrayList<OnPremiseConfluenceSearchResult>();
}
