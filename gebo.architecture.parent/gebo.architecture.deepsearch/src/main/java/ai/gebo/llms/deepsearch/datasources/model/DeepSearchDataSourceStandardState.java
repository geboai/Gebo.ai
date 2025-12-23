package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DeepSearchDataSourceStandardState {
	private DeepSearchDataSourceExtractedSearchQueries extractedSearchQueries = null;
	private List<DeepSearchDataSourceSearchResults> queryResults = new ArrayList<DeepSearchDataSourceSearchResults>();
	private int queryResultsIndex = 0;
	private int queryResultsReferenceIndex = 0;
	private int dataSourceIndex = 0;
	private List<DeepSearchDataSourceDocumentResult> cumulatedAnalisys = new ArrayList<DeepSearchDataSourceDocumentResult>();

}