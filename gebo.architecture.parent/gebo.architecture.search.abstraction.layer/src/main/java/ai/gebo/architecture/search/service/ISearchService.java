package ai.gebo.architecture.search.service;

import java.io.IOException;
import java.util.List;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;

public interface ISearchService<CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType> {
	public boolean isEnabled() throws SearchServiceException;

	public SearchableSystemMetaData findSystemById(String systemId) throws SearchServiceException;

	public String getId();

	public String getDescription();

	public List<SearchableSystemMetaData> getSearchableSystems() throws SearchServiceException;

	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException;

	public default List<SearchResult> search(SearchQuery query, String systemId, int nEntryLimit)
			throws IOException, SearchServiceException {
		SearchableSystemMetaData system = findSystemById(systemId);
		if (system != null) {
			return search(query, system, nEntryLimit);
		} else
			return null;
	}

	public TypedInputStream loadSearchResult(SearchResult result) throws IOException, SearchServiceException;

	public Class<CustomSearchResultExtractionDataType> getCustomResultsAggregationDataType()
			throws SearchServiceException;

	public CustomSearchResultExtractionDataType aggregate(CustomSearchResultExtractionDataType oldConsolidated,
			CustomSearchResultExtractionDataType consolidated);

	public List<SearchWithResults> cleanAndRemoveDuplicated(List<SearchWithResults> queryResults);

	public String getQueriesExtractionPrompt();

	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			CustomSearchResultExtractionDataType extractedData);

}
