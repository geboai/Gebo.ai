/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.atlassian.jira.handler.controller;

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
import ai.gebo.atlassian.jira.handler.impl.JiraSearchService;
import ai.gebo.atlassian.jira.search.api.JiraIssuesSearchFilter;
import ai.gebo.atlassian.jira.search.api.JiraResultsExtractionData;

/**
 * REST surface for the Jira native search service, exposed to USERS and ADMIN.
 * Backed by {@link JiraSearchService}; endpoints delegate to the protected
 * {@link BaseNativeSearchController} helpers. The topology-aware
 * {@code JiraSearchServiceRestClient} on brain mirrors these endpoints.
 */
@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequestMapping("api/users/JiraSearchServiceController")
public class JiraSearchServiceController
		extends BaseNativeSearchController<JiraResultsExtractionData, JiraIssuesSearchFilter> {

	public JiraSearchServiceController(JiraSearchService jiraSearchService) {
		super(jiraSearchService);
	}

	@GetMapping("isEnabled")
	public boolean restIsEnabledJira() throws SearchServiceException {
		return isEnabled();
	}

	@GetMapping("getId")
	public String restGetIdJira() {
		return getId();
	}

	@GetMapping("getDescription")
	public String restGetDescriptionJira() {
		return getDescription();
	}

	@GetMapping("getProductId")
	public String restGetProductIdJira() {
		return getProductId();
	}

	@GetMapping("getMessagingModuleId")
	public String restGetMessagingModuleIdJira() {
		return getMessagingModuleId();
	}

	@GetMapping("getQueriesGenerationPromptUseCode")
	public String restGetQueriesGenerationPromptUseCodeJira() {
		return getQueriesGenerationPromptUseCode();
	}

	@GetMapping("getNativePromptTemplateUseCode")
	public String restGetNativePromptTemplateUseCodeJira() {
		return getNativePromptTemplateUseCode();
	}

	@GetMapping(value = "getSearchableSystems", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchableSystemMetaData> restGetSearchableSystemsJira() throws SearchServiceException {
		return getSearchableSystems();
	}

	@GetMapping(value = "findSystemById", produces = MediaType.APPLICATION_JSON_VALUE)
	public SearchableSystemMetaData restFindSystemByIdJira(@RequestParam("systemId") String systemId)
			throws SearchServiceException {
		return findSystemById(systemId);
	}

	@PostMapping(value = "findSystemBySearchResult", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SearchableSystemMetaData restFindSystemBySearchResultJira(@RequestBody SearchResult result)
			throws SearchServiceException {
		return findSystemBySearchResult(result);
	}

	@PostMapping(value = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchResult> restSearchJira(@RequestBody SearchQuery query, @RequestParam("systemId") String systemId,
			@RequestParam("nEntryLimit") int nEntryLimit,
			@RequestParam(value = SearchCallParameters.CONNECT_TIMEOUT_MILLIS_PARAM, required = false) Integer connectTimeoutMillis,
			@RequestParam(value = SearchCallParameters.READ_TIMEOUT_MILLIS_PARAM, required = false) Integer readTimeoutMillis,
			@RequestParam(value = SearchCallParameters.RETRIES_PARAM, required = false) Integer retries) throws IOException, SearchServiceException {
		return search(query, systemId, nEntryLimit,
				searchCallParameters(connectTimeoutMillis, readTimeoutMillis, retries));
	}

	@PostMapping(value = "nativeSearch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SearchResult> restNativeSearchJira(@RequestBody JiraIssuesSearchFilter query,
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
	public List<CatalogueSample> restGetCataloguesListSampleJira(@RequestParam("configurationCode") String configurationCode)
			throws SearchServiceException {
		return getCataloguesListSample(configurationCode);
	}

	@GetMapping(value = "getCachedCatalogues", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CatalogueSample> restGetCachedCataloguesJira(
			@RequestParam(value = "systemConfigurationCode", required = false) String systemConfigurationCode)
			throws SearchServiceException {
		return systemConfigurationCode != null ? getCachedCatalogues(systemConfigurationCode) : getCachedCatalogues();
	}

	@PostMapping(value = "extractRelatedAnalisysReferences", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesJira(@RequestParam("systemId") String systemId,
			@RequestBody JiraResultsExtractionData extractedData) throws IOException, SearchServiceException {
		return extractRelatedAnalisysReferences(systemId, extractedData);
	}

	@PostMapping(value = "aggregate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public JiraResultsExtractionData restAggregateJira(@RequestBody AggregateRequestBody<JiraResultsExtractionData> body) {
		return aggregate(body.getOldConsolidated(), body.getConsolidated());
	}

	@PostMapping(value = "createCustomTemplateParamsMap", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> restCreateCustomTemplateParamsMapJira(@RequestBody CustomTemplateParamsRequestBody body) {
		return createCustomTemplateParamsMap(body.getSearchableSystemMetaData(), body.getCataloguesSample());
	}
}
