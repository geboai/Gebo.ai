package ai.gebo.architecture.search.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SearchResultAnalisysOutcome {
	final List<SearchQuery> searchQueries;
	final List<SearchResult> relatedResults;

	public boolean isEmpty() {
		return (searchQueries == null || searchQueries.isEmpty())
				&& (relatedResults == null || relatedResults.isEmpty());
	}
}
