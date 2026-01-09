package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchWithResults;
import lombok.Data;

@Data
public class DeepSearchDataSourceStandardState {
	private DeepSearchDataSourceExtractedSearchQueries extractedSearchQueries = null;
	private List<SearchWithResults> queryResults = new ArrayList<SearchWithResults>();
	private int queryResultsIndex = 0;
	private int queryResultsReferenceIndex = 0;
	private int dataSourceIndex = 0;
	private List<DeepSearchDataSourceDocumentResult> cumulatedAnalisys = new ArrayList<DeepSearchDataSourceDocumentResult>();
	private List<SearchResult> navigatedResults = new ArrayList<SearchResult>();

	public Integer totalStepsCount() {
		int total = 0;
		for (SearchWithResults qr : queryResults) {
			total += qr.getResults().size();
		}
		return total;
	}

	public Integer actualStepsCount() {
		int position = queryResultsReferenceIndex;
		for (int i = 0; i < queryResultsIndex; i++) {
			SearchWithResults qr = queryResults.get(i);
			position += qr.getResults().size();
		}
		return position;
	}
}