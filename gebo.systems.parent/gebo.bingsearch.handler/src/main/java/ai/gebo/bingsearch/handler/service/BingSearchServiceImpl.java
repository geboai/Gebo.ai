package ai.gebo.bingsearch.handler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.model.WebSearchQueryObject;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.bingsearch.handler.config.BingSearchHandlerConfig;
import ai.gebo.bingsearch.handler.model.BingNewsArticle;
import ai.gebo.bingsearch.handler.model.BingSearchQuery;
import ai.gebo.bingsearch.handler.model.BingSearchResponse;
import ai.gebo.bingsearch.handler.model.BingWebPageResult;
import ai.gebo.bingsearch.handler.model.GBingSearchApiCredentials;
import ai.gebo.bingsearch.handler.model.ResponseFilters;
import ai.gebo.bingsearch.handler.repository.GBingSearchApiCredentialsRepository;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BingSearchServiceImpl extends AbstractWebSearchServiceImpl<WebSearchQueryObject> {
	private static final String BING = "bing";
	private static final String BING_MODULE = "bing-module";
	public static final String BING_SEARCH_SERVICE = "bing-web-search-service";
	private static final GBaseObject SYSTEMTYPE = new GBaseObject();
	private static final GBaseObject SYSTEM = new GBaseObject();

	private static final class BingSearchSystem extends SearchableSystemMetaData<GBaseObject, GBaseObject> {
	}

	private static final BingSearchSystem SYSTEM_METADATA = new BingSearchSystem();
	private static final List<SearchableSystemMetaData> allSystems = List.of(SYSTEM_METADATA);
	static {
		SYSTEMTYPE.setCode(BING_SEARCH_SERVICE);
		SYSTEMTYPE.setDescription("Bing web search service");
		SYSTEM.setCode(BING_SEARCH_SERVICE);
		SYSTEM.setDescription("Google search service");
		SYSTEM_METADATA.setCode(BING_SEARCH_SERVICE);
		SYSTEM_METADATA.setDescription("Bing search service");
		SYSTEM_METADATA.setSystemType(SYSTEMTYPE);
		SYSTEM_METADATA.setSystemConfigurationReference(SYSTEM);
	}
	private final BingSearchApi bingSearchApi;
	private final BingSearchHandlerConfig bingConfig;
	private final GBingSearchApiCredentialsRepository repository;
	private final IGeboSecretsAccessService secretAccessService;

	@Override
	public boolean isEnabled() {

		return repository.count() > 0l;
	}

	@Override
	public SearchableSystemMetaData findSystemById(String systemId) {

		return systemId != null && systemId.equalsIgnoreCase(BING_SEARCH_SERVICE) ? SYSTEM_METADATA : null;
	}

	@Override
	public String getMessagingModuleId() {

		return BING_MODULE;
	}

	@Override
	public String getId() {

		return BING_SEARCH_SERVICE;
	}

	@Override
	public String getDescription() {

		return "Bing web search";
	}

	@Override
	public List<SearchableSystemMetaData> getSearchableSystems() throws SearchServiceException {

		return List.of(SYSTEM_METADATA);
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		List<GBingSearchApiCredentials> configs = this.repository.findAll();
		if (configs.isEmpty())
			return List.of();
		GBingSearchApiCredentials config = configs.get(0);
		List<SearchResult> resultsList = new ArrayList<SearchResult>();
		try {
			AbstractGeboSecretContent secret = this.secretAccessService.getSecretContentById(config.getSecretCode());
			if (secret instanceof GeboTokenContent tokenContent) {
				BingSearchQuery searchOptions = new BingSearchQuery();
				searchOptions.setQuery(query.getQueryText());
				searchOptions.setTopN(nEntryLimit);
				searchOptions.setFilters(List.of(ResponseFilters.WEBPAGES, ResponseFilters.NEWS));
				BingSearchResponse result = this.bingSearchApi.search(tokenContent.getToken(), searchOptions);

				if (result.getNews() != null && result.getNews().getValue() != null) {
					resultsList.addAll(newsResults(result.getNews().getValue()));
				}
				if (result.getWebPages() != null && result.getWebPages().getValue() != null) {
					resultsList.addAll(webPagesResults(result.getWebPages().getValue()));
				}

			} else
				throw new SearchServiceException("Bing credentials of the wrong format");
		} catch (GeboCryptSecretException | GeboRestIntegrationException e) {
			throw new SearchServiceException("Error accessing bing searches", e);
		}
		return resultsList;
	}

	private Collection<? extends SearchResult> webPagesResults(List<BingWebPageResult> value) {
		List<SearchResult> out = new ArrayList<SearchResult>();
		for (BingWebPageResult bingNewsArticle : value) {
			SearchResult result = new SearchResult();
			result.setDescriptiveText(bingNewsArticle.getSnippet());
			result.setResultReference(new SearchResultReference());
			result.getResultReference().setId(bingNewsArticle.getUrl());
			result.getResultReference().setUri(bingNewsArticle.getUrl());
			result.getResultReference().setName(bingNewsArticle.getName());
			result.getResultReference().setTitle(
					bingNewsArticle.getSnippet() != null ? bingNewsArticle.getSnippet() : bingNewsArticle.getName());
			result.getResultReference().setExtension(tryArgueExtension(bingNewsArticle.getUrl()));
			result.getResultReference().setContentType(tryArgueContentType(bingNewsArticle.getUrl()));
			result.setSystemConfigurationCode(BING_SEARCH_SERVICE);
			setOriginOn(result);
			out.add(result);
		}
		return out;
	}

	private Collection<? extends SearchResult> newsResults(List<BingNewsArticle> value) {
		List<SearchResult> out = new ArrayList<SearchResult>();
		for (BingNewsArticle bingNewsArticle : value) {
			SearchResult result = new SearchResult();
			result.setDescriptiveText(bingNewsArticle.getDescription());
			result.setResultReference(new SearchResultReference());
			result.getResultReference().setId(bingNewsArticle.getUrl());
			result.getResultReference().setUri(bingNewsArticle.getUrl());
			result.getResultReference().setName(bingNewsArticle.getName());
			result.getResultReference()
					.setTitle(bingNewsArticle.getDescription() != null ? bingNewsArticle.getDescription()
							: bingNewsArticle.getName());
			result.getResultReference().setExtension(tryArgueExtension(bingNewsArticle.getUrl()));
			result.getResultReference().setContentType(tryArgueContentType(bingNewsArticle.getUrl()));
			result.setSystemConfigurationCode(BING_SEARCH_SERVICE);
			setOriginOn(result);
			out.add(result);
		}
		return out;
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {

		return bingConfig.getQueryExtractionPrompt();
	}

	@Override
	public List<CatalogueSample> getCataloguesListSample(String configurationCode) throws SearchServiceException {

		return List.of(new CatalogueSample(configurationCode, "Search internet with bing search engine"));
	}

	@Override
	public String getProductId() {
		
		return BING;
	}

	@Override
	public Class<WebSearchQueryObject> getNativeSearchDataStructureType() {
		
		return WebSearchQueryObject.class;
	}

	@Override
	public String getNativePromptTemplateUseCode() {
		
		return null;
	}

	@Override
	public Map<String, Object> createCustomTemplateParamsMap(SearchableSystemMetaData searchableSystemMetaData,
			List<CatalogueSample> cataloguesSample) {
		
		return Map.of();
	}

}
