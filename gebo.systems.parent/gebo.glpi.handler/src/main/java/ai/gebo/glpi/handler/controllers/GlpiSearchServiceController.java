/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */


package ai.gebo.glpi.handler.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.search.controller.AggregateRequestBody;
import ai.gebo.architecture.search.controller.BaseNativeSearchController;
import ai.gebo.architecture.search.controller.CustomTemplateParamsRequestBody;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.glpi.handler.impl.GlpiSearchService;
import ai.gebo.glpi.search.api.GlpiSearchFilter;
import ai.gebo.glpi.search.api.GlpiResultsExtractionData;

/**
 * REST surface for the GLPI native search service, exposed to USERS and ADMIN.
 * Backed by {@link GlpiSearchService}; endpoints delegate to the protected
 * {@link BaseNativeSearchController} helpers. The topology-aware
 * {@code GlpiSearchServiceRestClient} on brain mirrors these endpoints.
 */
@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequestMapping("api/users/GlpiSearchServiceController")
public class GlpiSearchServiceController
		extends BaseNativeSearchController<GlpiResultsExtractionData, GlpiSearchFilter> {

	public GlpiSearchServiceController(GlpiSearchService glpiSearchService) {
		super(glpiSearchService);
	}

	@GetMapping("isEnabled")
	public boolean restIsEnabledGlpi() throws SearchServiceException {
		return isEnabled();
	}

	@GetMapping("getId")
	public String restGetIdGlpi() {
		return getId();
	}

	@GetMapping("getDescription")
	public String restGetDescriptionGlpi() {
		return getDescription();
	}

	@GetMapping("getProductId")
	public String restGetProductIdGlpi() {
		return getProductId();
	}

	@GetMapping("getMessagingModuleId")
	public String restGetMessagingModuleIdGlpi() {
		return getMessagingModuleId();
	}

	@GetMapping("getQueriesGenerationPromptUseCode")
	public String restGetQueriesGenerationPromptUseCodeGlpi() {
		return getQueriesGenerationPromptUseCode();
	}

	@GetMapping("getNativePromptTemplateUseCode")
	public String restGetNativePromptTemplateUseCodeGlpi() {
		return getNativePromptTemplateUseCode();
	}

	@GetMapping(value = "getSearchableSystems", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchableSystemMetaData> restGetSearchableSystemsGlpi() throws SearchServiceException {
		return getSearchableSystems();
	}

	@GetMapping(value = "findSystemById", produces = MediaType.APPLICATION_JSON_VALUE)
	public SearchableSystemMetaData restFindSystemByIdGlpi(@RequestParam("systemId") String systemId)
			throws SearchServiceException {
		return findSystemById(systemId);
	}

	@PostMapping(value = "findSystemBySearchResult", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SearchableSystemMetaData restFindSystemBySearchResultGlpi(@RequestBody SearchResult result)
			throws SearchServiceException {
		return findSystemBySearchResult(result);
	}

	@PostMapping(value = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchResult> restSearchGlpi(@RequestBody SearchQuery query, @RequestParam("systemId") String systemId,
			@RequestParam("nEntryLimit") int nEntryLimit,
			@RequestParam(value = SearchCallParameters.CONNECT_TIMEOUT_MILLIS_PARAM, required = false) Integer connectTimeoutMillis,
			@RequestParam(value = SearchCallParameters.READ_TIMEOUT_MILLIS_PARAM, required = false) Integer readTimeoutMillis,
			@RequestParam(value = SearchCallParameters.RETRIES_PARAM, required = false) Integer retries) throws IOException, SearchServiceException {
		return search(query, systemId, nEntryLimit,
				searchCallParameters(connectTimeoutMillis, readTimeoutMillis, retries));
	}

	@PostMapping(value = "nativeSearch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchResult> restNativeSearchGlpi(@RequestBody GlpiSearchFilter query,
			@RequestParam("systemId") String systemId,
			@RequestParam("nEntryLimit") int nEntryLimit,
			@RequestParam(value = SearchCallParameters.CONNECT_TIMEOUT_MILLIS_PARAM, required = false) Integer connectTimeoutMillis,
			@RequestParam(value = SearchCallParameters.READ_TIMEOUT_MILLIS_PARAM, required = false) Integer readTimeoutMillis,
			@RequestParam(value = SearchCallParameters.RETRIES_PARAM, required = false) Integer retries)
			throws IOException, SearchServiceException {
		SearchableSystemMetaData system = findSystemById(systemId);
		return nativeSearch(query, system, nEntryLimit,
				searchCallParameters(connectTimeoutMillis, readTimeoutMillis, retries));
	}

	@GetMapping(value = "getCataloguesListSample", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CatalogueSample> restGetCataloguesListSampleGlpi(@RequestParam("configurationCode") String configurationCode)
			throws SearchServiceException {
		return getCataloguesListSample(configurationCode);
	}

	@GetMapping(value = "getCachedCatalogues", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CatalogueSample> restGetCachedCataloguesGlpi(
			@RequestParam(value = "systemConfigurationCode", required = false) String systemConfigurationCode)
			throws SearchServiceException {
		return systemConfigurationCode != null ? getCachedCatalogues(systemConfigurationCode) : getCachedCatalogues();
	}

	@PostMapping(value = "extractRelatedAnalisysReferences", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesGlpi(@RequestParam("systemId") String systemId,
			@RequestBody GlpiResultsExtractionData extractedData) throws IOException, SearchServiceException {
		return extractRelatedAnalisysReferences(systemId, extractedData);
	}

	@PostMapping(value = "aggregate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GlpiResultsExtractionData restAggregateGlpi(@RequestBody AggregateRequestBody<GlpiResultsExtractionData> body) {
		return aggregate(body.getOldConsolidated(), body.getConsolidated());
	}

	@PostMapping(value = "createCustomTemplateParamsMap", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> restCreateCustomTemplateParamsMapGlpi(@RequestBody CustomTemplateParamsRequestBody body) {
		return createCustomTemplateParamsMap(body.getSearchableSystemMetaData(), body.getCataloguesSample());
	}
}
