/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.bravesearch.handler.impl;

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
import ai.gebo.bravesearch.handler.config.BraveSearchHandlerConfig;
import ai.gebo.bravesearch.handler.model.BraveApiResponse;
import ai.gebo.bravesearch.handler.model.BraveApiResponse.BraveResult;
import ai.gebo.bravesearch.handler.model.BraveNativeSearchQuery;
import ai.gebo.bravesearch.handler.model.GBraveSearchApiCredentials;
import ai.gebo.bravesearch.handler.repository.GBraveSearchApiCredentialsRepository;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BraveSearchServiceImpl extends AbstractWebSearchServiceImpl<BraveNativeSearchQuery> {
	private static final String BRAVE = "brave";
	private static final String BRAVE_MODULE = "brave-module";
	public static final String BRAVE_SEARCH_SERVICE = "brave-web-search-service";

	private static final GBaseObject SYSTEMTYPE = new GBaseObject();
	private static final GBaseObject SYSTEM = new GBaseObject();

	private static final class BraveSearchSystem extends SearchableSystemMetaData<GBaseObject, GBaseObject> {
	}

	private static final BraveSearchSystem SYSTEM_METADATA = new BraveSearchSystem();
	static {
		SYSTEMTYPE.setCode(BRAVE_SEARCH_SERVICE);
		SYSTEMTYPE.setDescription("Brave web search service");
		SYSTEM.setCode(BRAVE_SEARCH_SERVICE);
		SYSTEM.setDescription("Brave web search service");
		SYSTEM_METADATA.setCode(BRAVE_SEARCH_SERVICE);
		SYSTEM_METADATA.setDescription("Brave web search service");
		SYSTEM_METADATA.setSystemType(SYSTEMTYPE);
		SYSTEM_METADATA.setSystemConfigurationReference(SYSTEM);
	}

	private final BraveSearchApi braveSearchApi;
	private final BraveSearchHandlerConfig braveConfig;
	private final GBraveSearchApiCredentialsRepository repository;
	private final IGeboSecretsAccessService secretAccessService;

	@Override
	public boolean isEnabled() {
		return repository.count() > 0l;
	}

	@Override
	public SearchableSystemMetaData findSystemById(String systemId) {
		return systemId != null && systemId.equalsIgnoreCase(BRAVE_SEARCH_SERVICE) ? SYSTEM_METADATA : null;
	}

	@Override
	public String getMessagingModuleId() {
		return BRAVE_MODULE;
	}

	@Override
	public String getId() {
		return BRAVE_SEARCH_SERVICE;
	}

	@Override
	public List<SearchableSystemMetaData> getSearchableSystems() throws SearchServiceException {
		return List.of(SYSTEM_METADATA);
	}

	private String resolveApiKey() throws SearchServiceException {
		List<GBraveSearchApiCredentials> configs = this.repository.findAll();
		if (configs.isEmpty())
			return null;
		try {
			AbstractGeboSecretContent secret = this.secretAccessService
					.getSecretContentById(configs.get(0).getSecretCode());
			if (secret instanceof GeboTokenContent tokenContent) {
				return tokenContent.getToken();
			}
			throw new SearchServiceException("Brave credentials of the wrong format");
		} catch (GeboCryptSecretException e) {
			throw new SearchServiceException("Error reading brave credentials", e);
		}
	}

	private void mapInto(BraveApiResponse result, List<SearchResult> resultsList) {
		if (result == null || result.getWeb() == null || result.getWeb().getResults() == null)
			return;
		for (BraveResult item : result.getWeb().getResults()) {
			SearchResult searchResult = new SearchResult();
			searchResult.setDescriptiveText(item.getDescription());
			searchResult.setResultReference(new SearchResultReference());
			searchResult.getResultReference().setId(item.getUrl());
			searchResult.getResultReference().setUri(item.getUrl());
			searchResult.getResultReference().setName(item.getTitle());
			searchResult.getResultReference()
					.setTitle(item.getDescription() != null ? item.getDescription() : item.getTitle());
			searchResult.getResultReference().setExtension(tryArgueExtension(item.getUrl()));
			searchResult.getResultReference().setContentType(tryArgueContentType(item.getUrl()));
			searchResult.setSystemConfigurationCode(BRAVE_SEARCH_SERVICE);
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
			mapInto(this.braveSearchApi.callApi(apiKey, query.getQueryText(), nEntryLimit), resultsList);
		} catch (GeboRestIntegrationException e) {
			throw new SearchServiceException("Error accessing brave searches", e);
		}
		return resultsList;
	}

	/** Native path: LLM chose the queries AND the Brave options. */
	@Override
	public List<SearchResult> nativeSearch(BraveNativeSearchQuery query, SearchableSystemMetaData system,
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
				mapInto(this.braveSearchApi.callApi(apiKey, text, nEntryLimit, query.getFreshness(), query.getCountry(),
						query.getSafesearch()), resultsList);
			}
		} catch (GeboRestIntegrationException e) {
			throw new SearchServiceException("Error accessing brave searches", e);
		}
		return resultsList;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {
		return BraveSearchHandlerConfig.BRAVE_SEARCH_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public List<CatalogueSample> getCataloguesListSample(String configurationCode) throws SearchServiceException {
		return List.of(new CatalogueSample(configurationCode, "Search internet with the Brave search engine"));
	}

	@Override
	public String getProductId() {
		return BRAVE;
	}

	@Override
	public Class<BraveNativeSearchQuery> getNativeSearchDataStructureType() {
		return BraveNativeSearchQuery.class;
	}

	@Override
	public String getNativePromptTemplateUseCode() {
		return BraveSearchHandlerConfig.BRAVE_SEARCH_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {
		return Map.of();
	}
}
