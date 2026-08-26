/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.serpapisearch.handler.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.serpapisearch.handler.config.SerpapiSearchHandlerConfig;
import ai.gebo.serpapisearch.handler.model.GSerpapiSearchApiCredentials;
import ai.gebo.serpapisearch.handler.model.SerpapiApiResponse;
import ai.gebo.serpapisearch.handler.model.SerpapiApiResponse.SerpapiOrganicResult;
import ai.gebo.serpapisearch.handler.model.SerpapiNativeSearchQuery;
import ai.gebo.serpapisearch.handler.repository.GSerpapiSearchApiCredentialsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SerpapiSearchServiceImpl extends AbstractWebSearchServiceImpl<SerpapiNativeSearchQuery> {
	private static final String SERPAPI = "serpapi";
	private static final String SERPAPI_MODULE = "serpapi-module";
	public static final String SERPAPI_SEARCH_SERVICE = "serpapi-web-search-service";

	private static final GBaseObject SYSTEMTYPE = new GBaseObject();
	private static final GBaseObject SYSTEM = new GBaseObject();

	private static final class SerpapiSearchSystem extends SearchableSystemMetaData<GBaseObject, GBaseObject> {
	}

	private static final SerpapiSearchSystem SYSTEM_METADATA = new SerpapiSearchSystem();
	static {
		SYSTEMTYPE.setCode(SERPAPI_SEARCH_SERVICE);
		SYSTEMTYPE.setDescription("SerpApi web search service");
		SYSTEM.setCode(SERPAPI_SEARCH_SERVICE);
		SYSTEM.setDescription("SerpApi web search service");
		SYSTEM_METADATA.setCode(SERPAPI_SEARCH_SERVICE);
		SYSTEM_METADATA.setDescription("SerpApi web search service");
		SYSTEM_METADATA.setSystemType(SYSTEMTYPE);
		SYSTEM_METADATA.setSystemConfigurationReference(SYSTEM);
	}

	private final SerpapiSearchApi serpapiSearchApi;
	private final SerpapiSearchHandlerConfig serpapiConfig;
	private final GSerpapiSearchApiCredentialsRepository repository;
	private final IGeboSecretsAccessService secretAccessService;

	@Override
	public boolean isEnabled() {
		return repository.count() > 0l;
	}

	@Override
	public SearchableSystemMetaData findSystemById(String systemId) {
		return systemId != null && systemId.equalsIgnoreCase(SERPAPI_SEARCH_SERVICE) ? SYSTEM_METADATA : null;
	}

	@Override
	public String getMessagingModuleId() {
		return SERPAPI_MODULE;
	}

	@Override
	public String getId() {
		return SERPAPI_SEARCH_SERVICE;
	}

	@Override
	public List<SearchableSystemMetaData> getSearchableSystems() throws SearchServiceException {
		return List.of(SYSTEM_METADATA);
	}

	private String resolveApiKey() throws SearchServiceException {
		List<GSerpapiSearchApiCredentials> configs = this.repository.findAll();
		if (configs.isEmpty())
			return null;
		try {
			AbstractGeboSecretContent secret = this.secretAccessService
					.getSecretContentById(configs.get(0).getSecretCode());
			if (secret instanceof GeboTokenContent tokenContent) {
				return tokenContent.getToken();
			}
			throw new SearchServiceException("SerpApi credentials of the wrong format");
		} catch (GeboCryptSecretException e) {
			throw new SearchServiceException("Error reading serpapi credentials", e);
		}
	}

	private void mapInto(SerpapiApiResponse result, List<SearchResult> resultsList) {
		if (result == null || result.getOrganic_results() == null)
			return;
		for (SerpapiOrganicResult item : result.getOrganic_results()) {
			SearchResult searchResult = new SearchResult();
			searchResult.setDescriptiveText(item.getSnippet());
			searchResult.setResultReference(new SearchResultReference());
			searchResult.getResultReference().setId(item.getLink());
			searchResult.getResultReference().setUri(item.getLink());
			searchResult.getResultReference().setName(item.getTitle());
			searchResult.getResultReference().setTitle(item.getSnippet() != null ? item.getSnippet() : item.getTitle());
			searchResult.getResultReference().setExtension(tryArgueExtension(item.getLink()));
			searchResult.getResultReference().setContentType(tryArgueContentType(item.getLink()));
			searchResult.setSystemConfigurationCode(SERPAPI_SEARCH_SERVICE);
			setOriginOn(searchResult);
			resultsList.add(searchResult);
		}
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		String apiKey = resolveApiKey();
		if (apiKey == null)
			return List.of();
		List<SearchResult> resultsList = new ArrayList<SearchResult>();
		try {
			mapInto(this.serpapiSearchApi.callApi(apiKey, query.getQueryText(), nEntryLimit, null, null, null, null),
					resultsList);
		} catch (GeboRestIntegrationException e) {
			throw new SearchServiceException("Error accessing serpapi searches", e);
		}
		return resultsList;
	}

	/** Native path: LLM chose the queries AND the SerpApi options. */
	@Override
	public List<SearchResult> nativeSearch(SerpapiNativeSearchQuery query, SearchableSystemMetaData system,
			int nEntryLimit) throws IOException, SearchServiceException {
		if (query == null || query.getSearchedTexts() == null || query.getSearchedTexts().isEmpty())
			return List.of();
		String apiKey = resolveApiKey();
		if (apiKey == null)
			return List.of();
		List<SearchResult> resultsList = new ArrayList<SearchResult>();
		try {
			for (String text : query.getSearchedTexts()) {
				if (text == null || text.isBlank())
					continue;
				mapInto(this.serpapiSearchApi.callApi(apiKey, text, nEntryLimit, query.getEngine(), query.getGl(),
						query.getHl(), query.getTbs()), resultsList);
			}
		} catch (GeboRestIntegrationException e) {
			throw new SearchServiceException("Error accessing serpapi searches", e);
		}
		return resultsList;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {
		return SerpapiSearchHandlerConfig.SERPAPI_SEARCH_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public List<CatalogueSample> getCataloguesListSample(String configurationCode) throws SearchServiceException {
		return List.of(new CatalogueSample(configurationCode, "Search internet through SerpApi (real search engine results)"));
	}

	@Override
	public String getProductId() {
		return SERPAPI;
	}

	@Override
	public Class<SerpapiNativeSearchQuery> getNativeSearchDataStructureType() {
		return SerpapiNativeSearchQuery.class;
	}

	@Override
	public String getNativePromptTemplateUseCode() {
		return SerpapiSearchHandlerConfig.SERPAPI_SEARCH_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {
		return Map.of();
	}
}
