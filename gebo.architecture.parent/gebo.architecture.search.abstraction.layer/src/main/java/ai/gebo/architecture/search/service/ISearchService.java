package ai.gebo.architecture.search.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.model.WebSearchResultsExtractionData;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.model.base.TypedInputStream;

public interface ISearchService<CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType> {
	public static final String SYSTEM_TYPE_CODE_CONFIG_CODE_SEPARATOR = "<->";

	public boolean isEnabled() throws SearchServiceException;

	public default SearchableSystemMetaData findSystemBySearchResult(SearchResult result)
			throws SearchServiceException {
		final String prologue = getMessagingModuleId() + "." + getMessagingSystemId()
				+ ISearchService.SYSTEM_TYPE_CODE_CONFIG_CODE_SEPARATOR;
		final String systemId = getMessagingModuleId() + "." + getMessagingSystemId()
				+ ISearchService.SYSTEM_TYPE_CODE_CONFIG_CODE_SEPARATOR + result.getSystemConfigurationCode();
		List<SearchableSystemMetaData> systems = getSearchableSystems();
		if (systems == null || systems.isEmpty()) {
			return null;
		}
		Optional<SearchableSystemMetaData> found = systems.stream()
				.filter(x -> (x.getCode()).equals(systemId)).findFirst();
		if (found.isEmpty()) {
			return null;
		} else {
			return found.get();
		}

	}

	public SearchableSystemMetaData findSystemById(String systemId) throws SearchServiceException;

	public String getMessagingModuleId();

	public default String getMessagingSystemId() {
		return getId();
	}

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

	public default CustomSearchResultExtractionDataType basicAggregate(
			CustomSearchResultExtractionDataType oldConsolidated, CustomSearchResultExtractionDataType consolidated,
			CustomSearchResultExtractionDataType newResult) {
		if (oldConsolidated == null && consolidated == null)
			return newResult;
		if (consolidated == null)
			return oldConsolidated;
		if (oldConsolidated == null)
			return consolidated;
		newResult.setContentIsRelevant(false);
		if (consolidated.getContentIsRelevant() != null && consolidated.getContentIsRelevant()) {
			newResult.setExtractedRelevantContent(consolidated.getExtractedRelevantContent());
			newResult.setContentIsRelevant(consolidated.getContentIsRelevant());
		} else {
			if (oldConsolidated != null && oldConsolidated.getContentIsRelevant() != null
					&& oldConsolidated.getContentIsRelevant()) {
				newResult.setExtractedRelevantContent(oldConsolidated.getExtractedRelevantContent());
				newResult.setContentIsRelevant(oldConsolidated.getContentIsRelevant());
			}
		}
		return newResult;
	}

	public String getQueriesGenerationPromptUseCode();

	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			CustomSearchResultExtractionDataType extractedData);

	public default void setOriginOn(SearchResult sr) {
		if (sr != null) {
			sr.setOriginComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));
			setOriginOn(sr.getChilds());
		}
	}

	public List<CatalogueSample> getCataloguesListSample(String configurationCode) throws SearchServiceException;

	public default void setOriginOn(List<SearchResult> sr) {
		if (sr != null) {
			sr.forEach(this::setOriginOn);
		}
	}

}
