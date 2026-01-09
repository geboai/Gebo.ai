package ai.gebo.llms.deepsearch.model;

import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchWithResults;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchResultsStepInfo {
	private final SearchResult actualSearchResult;
	private final SearchWithResults actualResult;

	public boolean isEmpty() {
		return actualResult == null || actualSearchResult == null;
	}
}