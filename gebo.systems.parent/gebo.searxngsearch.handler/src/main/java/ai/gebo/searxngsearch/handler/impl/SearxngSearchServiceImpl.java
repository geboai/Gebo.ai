/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.searxngsearch.handler.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
import ai.gebo.searxngsearch.handler.config.SearxngSearchHandlerConfig;
import ai.gebo.searxngsearch.handler.model.GSearxngSearchApiCredentials;
import ai.gebo.searxngsearch.handler.model.SearxngApiResponse;
import ai.gebo.searxngsearch.handler.model.SearxngApiResponse.SearxngApiResult;
import ai.gebo.searxngsearch.handler.model.SearxngNativeSearchQuery;
import ai.gebo.searxngsearch.handler.repository.GSearxngSearchApiCredentialsRepository;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SearxngSearchServiceImpl extends AbstractWebSearchServiceImpl<SearxngNativeSearchQuery> {
	private static final String SEARXNG = "searxng";
	private static final String SEARXNG_MODULE = "searxng-module";
	public static final String SEARXNG_SEARCH_SERVICE = "searxng-web-search-service";

	private static final GBaseObject SYSTEMTYPE = new GBaseObject();
	private static final GBaseObject SYSTEM = new GBaseObject();

	private static final class SearxngSearchSystem extends SearchableSystemMetaData<GBaseObject, GBaseObject> {
	}

	private static final SearxngSearchSystem SYSTEM_METADATA = new SearxngSearchSystem();
	static {
		SYSTEMTYPE.setCode(SEARXNG_SEARCH_SERVICE);
		SYSTEMTYPE.setDescription("SearXNG web search service");
		SYSTEM.setCode(SEARXNG_SEARCH_SERVICE);
		SYSTEM.setDescription("SearXNG web search service");
		SYSTEM_METADATA.setCode(SEARXNG_SEARCH_SERVICE);
		SYSTEM_METADATA.setDescription("SearXNG web search service");
		SYSTEM_METADATA.setSystemType(SYSTEMTYPE);
		SYSTEM_METADATA.setSystemConfigurationReference(SYSTEM);
	}

	private final SearxngSearchApi searxngSearchApi;
	private final SearxngSearchHandlerConfig searxngConfig;
	private final GSearxngSearchApiCredentialsRepository repository;
	private final IGeboSecretsAccessService secretAccessService;

	@Override
	public boolean isEnabled() {
		return repository.count() > 0l;
	}

	@Override
	public SearchableSystemMetaData findSystemById(String systemId) {
		return systemId != null && systemId.equalsIgnoreCase(SEARXNG_SEARCH_SERVICE) ? SYSTEM_METADATA : null;
	}

	@Override
	public String getMessagingModuleId() {
		return SEARXNG_MODULE;
	}

	@Override
	public String getId() {
		return SEARXNG_SEARCH_SERVICE;
	}

	@Override
	public String getDescription() {
		return "SearXNG web search";
	}

	@Override
	public List<SearchableSystemMetaData> getSearchableSystems() throws SearchServiceException {
		return List.of(SYSTEM_METADATA);
	}

	/** The first configured SearXNG credential, or null if none. */
	private GSearxngSearchApiCredentials firstCredential() {
		List<GSearxngSearchApiCredentials> configs = this.repository.findAll();
		return configs.isEmpty() ? null : configs.get(0);
	}

	/** Resolves the optional bearer token stored for a credential. */
	private String resolveApiKey(GSearxngSearchApiCredentials config) throws SearchServiceException {
		if (!StringUtils.hasText(config.getSecretCode()))
			return null;
		try {
			AbstractGeboSecretContent secret = this.secretAccessService.getSecretContentById(config.getSecretCode());
			if (secret instanceof GeboTokenContent tokenContent) {
				return tokenContent.getToken();
			}
			throw new SearchServiceException("SearXNG credentials of the wrong format");
		} catch (GeboCryptSecretException e) {
			throw new SearchServiceException("Error reading searxng credentials", e);
		}
	}

	private void mapInto(SearxngApiResponse result, int limit, List<SearchResult> resultsList) {
		if (result == null || result.getResults() == null)
			return;
		for (SearxngApiResult item : result.getResults()) {
			if (resultsList.size() >= limit) {
				break;
			}
			SearchResult searchResult = new SearchResult();
			searchResult.setDescriptiveText(item.getContent());
			searchResult.setResultReference(new SearchResultReference());
			searchResult.getResultReference().setId(item.getUrl());
			searchResult.getResultReference().setUri(item.getUrl());
			searchResult.getResultReference().setName(item.getTitle());
			searchResult.getResultReference().setTitle(item.getContent() != null ? item.getContent() : item.getTitle());
			searchResult.getResultReference().setExtension(tryArgueExtension(item.getUrl()));
			searchResult.getResultReference().setContentType(tryArgueContentType(item.getUrl()));
			searchResult.setSystemConfigurationCode(SEARXNG_SEARCH_SERVICE);
			setOriginOn(searchResult);
			resultsList.add(searchResult);
		}
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		GSearxngSearchApiCredentials config = firstCredential();
		if (config == null)
			return List.of();
		List<SearchResult> resultsList = new ArrayList<SearchResult>();
		try {
			String apiKey = resolveApiKey(config);
			mapInto(this.searxngSearchApi.callApi(config.getBaseUrl(), apiKey, query.getQueryText(), nEntryLimit),
					nEntryLimit, resultsList);
		} catch (GeboRestIntegrationException e) {
			throw new SearchServiceException("Error accessing searxng searches", e);
		}
		return resultsList;
	}

	/** Native path: LLM chose the queries AND the SearXNG options. */
	@Override
	public List<SearchResult> nativeSearch(SearxngNativeSearchQuery query, SearchableSystemMetaData system,
			int nEntryLimit) throws IOException, SearchServiceException {
		if (query == null || query.getSearchedTexts() == null || query.getSearchedTexts().isEmpty())
			return List.of();
		GSearxngSearchApiCredentials config = firstCredential();
		if (config == null)
			return List.of();
		List<SearchResult> resultsList = new ArrayList<SearchResult>();
		try {
			String apiKey = resolveApiKey(config);
			for (String text : query.getSearchedTexts()) {
				if (text == null || text.isBlank())
					continue;
				mapInto(this.searxngSearchApi.callApi(config.getBaseUrl(), apiKey, text, nEntryLimit,
						query.getCategories(), query.getTimeRange(), query.getLanguage()), nEntryLimit, resultsList);
			}
		} catch (GeboRestIntegrationException e) {
			throw new SearchServiceException("Error accessing searxng searches", e);
		}
		return resultsList;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {
		return SearxngSearchHandlerConfig.SEARXNG_SEARCH_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public List<CatalogueSample> getCataloguesListSample(String configurationCode) throws SearchServiceException {
		return List.of(new CatalogueSample(configurationCode, "Search internet with a SearXNG meta-search instance"));
	}

	@Override
	public String getProductId() {
		return SEARXNG;
	}

	@Override
	public Class<SearxngNativeSearchQuery> getNativeSearchDataStructureType() {
		return SearxngNativeSearchQuery.class;
	}

	@Override
	public String getNativePromptTemplateUseCode() {
		return SearxngSearchHandlerConfig.SEARXNG_SEARCH_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {
		return Map.of();
	}
}
