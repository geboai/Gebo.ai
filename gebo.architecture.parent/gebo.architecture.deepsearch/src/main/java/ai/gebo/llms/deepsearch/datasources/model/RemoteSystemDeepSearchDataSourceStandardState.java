package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RemoteSystemDeepSearchDataSourceStandardState {
	private ExtractedSearchQueries extractedSearchQueries = new ExtractedSearchQueries();
	private List<SearchResults> queryResults = new ArrayList<SearchResults>();
	private int queryResultsIndex = 0;
	private int queryResultsReferenceIndex = 0;
	private List<AnalyzedSearchResult> cumulatedAnalisys = new ArrayList<AnalyzedSearchResult>();

}