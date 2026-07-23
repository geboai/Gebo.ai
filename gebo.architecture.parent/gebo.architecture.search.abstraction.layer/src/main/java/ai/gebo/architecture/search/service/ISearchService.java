package ai.gebo.architecture.search.service;

import java.io.IOException;
import java.util.ArrayList;
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
		Optional<SearchableSystemMetaData> found = systems.stream().filter(x -> (x.getCode()).equals(systemId))
				.findFirst();
		if (found.isEmpty()) {
			return null;
		} else {
			return found.get();
		}

	}

	public SearchableSystemMetaData findSystemById(String systemId) throws SearchServiceException;

	public String getMessagingModuleId();

	public String getProductId();

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
			CustomSearchResultExtractionDataType extractedData) throws IOException, SearchServiceException;

	public default void setOriginOn(SearchResult sr) {
		if (sr != null) {
			sr.setOriginComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));
			setOriginOn(sr.getChilds());
		}
	}

	public List<CatalogueSample> getCataloguesListSample(String configurationCode) throws SearchServiceException;

	/**
	 * Returns the catalogues of a single configured system, preferring a cached
	 * snapshot over live sampling. The default implementation has no cache and simply
	 * delegates to the live {@link #getCataloguesListSample(String)}; search services
	 * whose catalogues are worth caching (e.g. the content/virtual-filesystem ones)
	 * override this to serve a persisted, periodically refreshed snapshot and avoid
	 * remote sampling on every call.
	 *
	 * @param systemConfigurationCode the configured system whose catalogues are wanted
	 */
	public default List<CatalogueSample> getCachedCatalogues(String systemConfigurationCode)
			throws SearchServiceException {
		return getCataloguesListSample(systemConfigurationCode);
	}

	/**
	 * Returns the cached catalogues of <em>all</em> the systems this service exposes,
	 * by composing {@link #getCachedCatalogues(String)} over {@link #getSearchableSystems()}.
	 * Because it builds on the per-system method, a service that overrides only that
	 * primitive to add caching automatically gets a cached global listing too.
	 */
	public default List<CatalogueSample> getCachedCatalogues() throws SearchServiceException {
		List<CatalogueSample> all = new ArrayList<>();
		List<SearchableSystemMetaData> systems = getSearchableSystems();
		if (systems != null) {
			for (SearchableSystemMetaData system : systems) {
				if (system != null) {
					all.addAll(getCachedCatalogues(system.getCode()));
				}
			}
		}
		return all;
	}

	public default void setOriginOn(List<SearchResult> sr) {
		if (sr != null) {
			sr.forEach(this::setOriginOn);
		}
	}

}
