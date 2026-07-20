/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.googledrive.handlers.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.search.controller.AggregateRequestBody;
import ai.gebo.architecture.search.controller.BaseSearchController;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.googledrive.handlers.impl.GoogleDriveSearchService;
import ai.gebo.googledrive.search.api.GoogleDriveResultsExtractionData;

/**
 * REST surface for the Google Drive search service, exposed to USERS and ADMIN.
 * Google Drive is a plain {@link ai.gebo.architecture.search.service.ISearchService}
 * (no native query structure), so this extends {@link BaseSearchController}.
 * Backed by {@link GoogleDriveSearchService}; mirrored by the topology-aware
 * {@code GoogleDriveSearchServiceRestClient} on brain.
 */
@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequestMapping("api/users/GoogleDriveSearchServiceController")
public class GoogleDriveSearchServiceController extends BaseSearchController<GoogleDriveResultsExtractionData> {

	public GoogleDriveSearchServiceController(GoogleDriveSearchService googleDriveSearchService) {
		super(googleDriveSearchService);
	}

	@GetMapping("isEnabled")
	public boolean restIsEnabled() throws SearchServiceException {
		return isEnabled();
	}

	@GetMapping("getId")
	public String restGetId() {
		return getId();
	}

	@GetMapping("getDescription")
	public String restGetDescription() {
		return getDescription();
	}

	@GetMapping("getProductId")
	public String restGetProductId() {
		return getProductId();
	}

	@GetMapping("getMessagingModuleId")
	public String restGetMessagingModuleId() {
		return getMessagingModuleId();
	}

	@GetMapping("getQueriesGenerationPromptUseCode")
	public String restGetQueriesGenerationPromptUseCode() {
		return getQueriesGenerationPromptUseCode();
	}

	@GetMapping(value = "getSearchableSystems", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchableSystemMetaData> restGetSearchableSystems() throws SearchServiceException {
		return getSearchableSystems();
	}

	@GetMapping(value = "findSystemById", produces = MediaType.APPLICATION_JSON_VALUE)
	public SearchableSystemMetaData restFindSystemById(@RequestParam("systemId") String systemId)
			throws SearchServiceException {
		return findSystemById(systemId);
	}

	@PostMapping(value = "findSystemBySearchResult", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SearchableSystemMetaData restFindSystemBySearchResult(@RequestBody SearchResult result)
			throws SearchServiceException {
		return findSystemBySearchResult(result);
	}

	@PostMapping(value = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchResult> restSearch(@RequestBody SearchQuery query, @RequestParam("systemId") String systemId,
			@RequestParam("nEntryLimit") int nEntryLimit) throws IOException, SearchServiceException {
		return search(query, systemId, nEntryLimit);
	}

	@GetMapping(value = "getCataloguesListSample", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CatalogueSample> restGetCataloguesListSample(@RequestParam("configurationCode") String configurationCode)
			throws SearchServiceException {
		return getCataloguesListSample(configurationCode);
	}

	@GetMapping(value = "getCachedCatalogues", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CatalogueSample> restGetCachedCatalogues(
			@RequestParam(value = "systemConfigurationCode", required = false) String systemConfigurationCode)
			throws SearchServiceException {
		return systemConfigurationCode != null ? getCachedCatalogues(systemConfigurationCode) : getCachedCatalogues();
	}

	@PostMapping(value = "extractRelatedAnalisysReferences", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SearchResultAnalisysOutcome restExtractRelatedAnalisysReferences(@RequestParam("systemId") String systemId,
			@RequestBody GoogleDriveResultsExtractionData extractedData) throws IOException, SearchServiceException {
		return extractRelatedAnalisysReferences(systemId, extractedData);
	}

	@PostMapping(value = "aggregate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GoogleDriveResultsExtractionData restAggregate(
			@RequestBody AggregateRequestBody<GoogleDriveResultsExtractionData> body) {
		return aggregate(body.getOldConsolidated(), body.getConsolidated());
	}
}
