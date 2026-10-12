/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.search.config.SearchCallsConfig;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.model.base.TypedInputStream;

/**
 * Base REST controller for an {@link ISearchService}. It holds the backing
 * service (constructor-injected) and re-exposes every {@code ISearchService}
 * method with {@code protected} visibility, so a concrete {@code @RestController}
 * subclass (living in the owning content-handler module) can map the
 * network-meaningful ones to endpoints for USERS/ADMIN without re-deriving the
 * delegation. This base carries no web annotations of its own, keeping the
 * search abstraction layer free of spring-web.
 *
 * @param <CustomSearchResultExtractionDataType> the connector's extraction data type
 */
public abstract class BaseSearchController<CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType> {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected final ISearchService<CustomSearchResultExtractionDataType> searchService;

	protected BaseSearchController(ISearchService<CustomSearchResultExtractionDataType> searchService) {
		this.searchService = searchService;
	}

	private SearchCallsConfig searchCallsConfig = null;

	/** The timeouts and retries of this node's search calls (see {@link SearchCallsConfig}). */
	@Autowired(required = false)
	public void setSearchCallsConfig(SearchCallsConfig searchCallsConfig) {
		this.searchCallsConfig = searchCallsConfig;
	}

	/** The parameters this node searches and downloads search results with. */
	protected SearchCallParameters searchCallParameters() {
		return SearchCallParameters.of(searchCallsConfig);
	}

	/**
	 * The parameters a remote caller searches with: those it sent (see
	 * {@link SearchCallParameters#CONNECT_TIMEOUT_MILLIS_PARAM} and the others), this
	 * node's for those it did not send.
	 */
	protected SearchCallParameters searchCallParameters(Integer connectTimeoutMillis, Integer readTimeoutMillis,
			Integer retries) {
		final SearchCallParameters parameters = searchCallParameters().overriddenBy(connectTimeoutMillis,
				readTimeoutMillis, retries);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("searchCallParameters(...) sent connect:" + connectTimeoutMillis + " ms read:"
					+ readTimeoutMillis + " ms retries:" + retries + " applied:" + parameters);
		}
		return parameters;
	}

	protected boolean isEnabled() throws SearchServiceException {
		return searchService.isEnabled();
	}

	protected String getId() {
		return searchService.getId();
	}

	protected String getDescription() {
		return searchService.getDescription();
	}

	protected String getProductId() {
		return searchService.getProductId();
	}

	protected String getMessagingModuleId() {
		return searchService.getMessagingModuleId();
	}

	protected String getMessagingSystemId() {
		return searchService.getMessagingSystemId();
	}

	protected String getQueriesGenerationPromptUseCode() {
		return searchService.getQueriesGenerationPromptUseCode();
	}

	protected List<SearchableSystemMetaData> getSearchableSystems() throws SearchServiceException {
		return searchService.getSearchableSystems();
	}

	protected SearchableSystemMetaData findSystemById(String systemId) throws SearchServiceException {
		return searchService.findSystemById(systemId);
	}

	protected SearchableSystemMetaData findSystemBySearchResult(SearchResult result) throws SearchServiceException {
		return searchService.findSystemBySearchResult(result);
	}

	protected List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		return searchService.search(query, system, nEntryLimit, searchCallParameters());
	}

	protected List<SearchResult> search(SearchQuery query, String systemId, int nEntryLimit)
			throws IOException, SearchServiceException {
		return searchService.search(query, systemId, nEntryLimit, searchCallParameters());
	}

	protected List<SearchResult> search(SearchQuery query, String systemId, int nEntryLimit,
			SearchCallParameters parameters) throws IOException, SearchServiceException {
		return searchService.search(query, systemId, nEntryLimit, parameters);
	}

	protected TypedInputStream loadSearchResult(SearchResult result) throws IOException, SearchServiceException {
		return searchService.loadSearchResult(result, searchCallParameters());
	}

	protected Class<CustomSearchResultExtractionDataType> getCustomResultsAggregationDataType()
			throws SearchServiceException {
		return searchService.getCustomResultsAggregationDataType();
	}

	protected CustomSearchResultExtractionDataType aggregate(CustomSearchResultExtractionDataType oldConsolidated,
			CustomSearchResultExtractionDataType consolidated) {
		return searchService.aggregate(oldConsolidated, consolidated);
	}

	protected CustomSearchResultExtractionDataType basicAggregate(CustomSearchResultExtractionDataType oldConsolidated,
			CustomSearchResultExtractionDataType consolidated, CustomSearchResultExtractionDataType newResult) {
		return searchService.basicAggregate(oldConsolidated, consolidated, newResult);
	}

	protected SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			CustomSearchResultExtractionDataType extractedData) throws IOException, SearchServiceException {
		return searchService.extractRelatedAnalisysReferences(systemId, extractedData);
	}

	protected List<CatalogueSample> getCataloguesListSample(String configurationCode) throws SearchServiceException {
		return searchService.getCataloguesListSample(configurationCode);
	}

	protected List<CatalogueSample> getCachedCatalogues(String systemConfigurationCode) throws SearchServiceException {
		return searchService.getCachedCatalogues(systemConfigurationCode);
	}

	protected List<CatalogueSample> getCachedCatalogues() throws SearchServiceException {
		return searchService.getCachedCatalogues();
	}
}
