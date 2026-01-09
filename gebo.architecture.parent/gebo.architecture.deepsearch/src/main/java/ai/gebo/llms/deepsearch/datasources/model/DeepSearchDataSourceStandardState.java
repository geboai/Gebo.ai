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

}