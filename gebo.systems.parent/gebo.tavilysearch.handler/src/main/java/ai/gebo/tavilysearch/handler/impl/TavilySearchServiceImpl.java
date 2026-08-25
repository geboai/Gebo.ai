/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.tavilysearch.handler.impl;

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
import ai.gebo.tavilysearch.handler.config.TavilySearchHandlerConfig;
import ai.gebo.tavilysearch.handler.model.GTavilySearchApiCredentials;
import ai.gebo.tavilysearch.handler.model.TavilyApiResponse;
import ai.gebo.tavilysearch.handler.model.TavilyApiResponse.TavilyApiResult;
import ai.gebo.tavilysearch.handler.model.TavilyNativeSearchQuery;
import ai.gebo.tavilysearch.handler.repository.GTavilySearchApiCredentialsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TavilySearchServiceImpl extends AbstractWebSearchServiceImpl<TavilyNativeSearchQuery> {
	private static final String TAVILY = "tavily";
	private static final String TAVILY_MODULE = "tavily-module";
	public static final String TAVILY_SEARCH_SERVICE = "tavily-web-search-service";

	private static final GBaseObject SYSTEMTYPE = new GBaseObject();
	private static final GBaseObject SYSTEM = new GBaseObject();

	private static final class TavilySearchSystem extends SearchableSystemMetaData<GBaseObject, GBaseObject> {
	}

	private static final TavilySearchSystem SYSTEM_METADATA = new TavilySearchSystem();
	static {
		SYSTEMTYPE.setCode(TAVILY_SEARCH_SERVICE);
		SYSTEMTYPE.setDescription("Tavily web search service");
		SYSTEM.setCode(TAVILY_SEARCH_SERVICE);
		SYSTEM.setDescription("Tavily web search service");
		SYSTEM_METADATA.setCode(TAVILY_SEARCH_SERVICE);
		SYSTEM_METADATA.setDescription("Tavily web search service");
		SYSTEM_METADATA.setSystemType(SYSTEMTYPE);
		SYSTEM_METADATA.setSystemConfigurationReference(SYSTEM);
	}

	private final TavilySearchApi tavilySearchApi;
	private final TavilySearchHandlerConfig tavilyConfig;
	private final GTavilySearchApiCredentialsRepository repository;
	private final IGeboSecretsAccessService secretAccessService;

	@Override
	public boolean isEnabled() {
		return repository.count() > 0l;
	}

	@Override
	public SearchableSystemMetaData findSystemById(String systemId) {
		return systemId != null && systemId.equalsIgnoreCase(TAVILY_SEARCH_SERVICE) ? SYSTEM_METADATA : null;
	}

	@Override
	public String getMessagingModuleId() {
		return TAVILY_MODULE;
	}

	@Override
	public String getId() {
		return TAVILY_SEARCH_SERVICE;
	}

	@Override
	public List<SearchableSystemMetaData> getSearchableSystems() throws SearchServiceException {
		return List.of(SYSTEM_METADATA);
	}

	/** Resolves the stored API key of the first configured credential, or null. */
	private String resolveApiKey() throws SearchServiceException {
		List<GTavilySearchApiCredentials> configs = this.repository.findAll();
		if (configs.isEmpty())
			return null;
		try {
			AbstractGeboSecretContent secret = this.secretAccessService
					.getSecretContentById(configs.get(0).getSecretCode());
			if (secret instanceof GeboTokenContent tokenContent) {
				return tokenContent.getToken();
			}
			throw new SearchServiceException("Tavily credentials of the wrong format");
		} catch (GeboCryptSecretException e) {
			throw new SearchServiceException("Error reading tavily credentials", e);
		}
	}

	private void mapInto(TavilyApiResponse result, List<SearchResult> resultsList) {
		if (result == null || result.getResults() == null)
			return;
		for (TavilyApiResult item : result.getResults()) {
			SearchResult searchResult = new SearchResult();
			searchResult.setDescriptiveText(item.getContent());
			searchResult.setResultReference(new SearchResultReference());
			searchResult.getResultReference().setId(item.getUrl());
			searchResult.getResultReference().setUri(item.getUrl());
			searchResult.getResultReference().setName(item.getTitle());
			searchResult.getResultReference().setTitle(item.getTitle() != null ? item.getTitle() : item.getContent());
			searchResult.getResultReference().setExtension(tryArgueExtension(item.getUrl()));
			searchResult.getResultReference().setContentType(tryArgueContentType(item.getUrl()));
			searchResult.setSystemConfigurationCode(TAVILY_SEARCH_SERVICE);
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
			mapInto(this.tavilySearchApi.callApi(apiKey, query.getQueryText(), nEntryLimit), resultsList);
		} catch (GeboRestIntegrationException e) {
			throw new SearchServiceException("Error accessing tavily searches", e);
		}
		return resultsList;
	}

	/**
	 * Native path: the LLM chose the queries AND the Tavily options
	 * (searchDepth/topic/timeRange), applied to every query.
	 */
	@Override
	public List<SearchResult> nativeSearch(TavilyNativeSearchQuery query, SearchableSystemMetaData system,
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
				mapInto(this.tavilySearchApi.callApi(apiKey, text, nEntryLimit, query.getSearchDepth(),
						query.getTopic(), query.getTimeRange()), resultsList);
			}
		} catch (GeboRestIntegrationException e) {
			throw new SearchServiceException("Error accessing tavily searches", e);
		}
		return resultsList;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {
		return TavilySearchHandlerConfig.TAVILY_SEARCH_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public List<CatalogueSample> getCataloguesListSample(String configurationCode) throws SearchServiceException {
		return List.of(new CatalogueSample(configurationCode, "Search internet with the Tavily search engine"));
	}

	@Override
	public String getProductId() {
		return TAVILY;
	}

	@Override
	public Class<TavilyNativeSearchQuery> getNativeSearchDataStructureType() {
		return TavilyNativeSearchQuery.class;
	}

	@Override
	public String getNativePromptTemplateUseCode() {
		return TavilySearchHandlerConfig.TAVILY_SEARCH_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {
		return Map.of();
	}
}
