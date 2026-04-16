package ai.gebo.googlesearch.handler.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.model.WebSearchResultsExtractionData;
import ai.gebo.architecture.search.model.WebSearchResultsExtractionData.RelevantLink;
import ai.gebo.architecture.search.service.AbstractWebSearchServiceImpl;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.googlesearch.handler.config.GoogleSearchHandlerConfig;
import ai.gebo.googlesearch.handler.model.GoogleSearchConfig;
import ai.gebo.googlesearch.handler.model.GoogleSearchRequest;
import ai.gebo.googlesearch.handler.model.GoogleSearchResultItem;
import ai.gebo.googlesearch.handler.model.GoogleSearchResults;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GoogleSearchServiceImpl extends AbstractWebSearchServiceImpl {
	private static final String GOOGLE = "google";
	public static final String GOOGLE_SEARCH_SERVICE = "google-search-service";
	private final GoogleSearchConfigDaoImpl googleConfigDao;
	private final GoogleSearchApi googleSearchApi;
	private final RestTemplateWrapperService restTemplateWrapper;
	private static final GBaseObject SYSTEMTYPE = new GBaseObject();
	private static final GBaseObject SYSTEM = new GBaseObject();

	private static final class GoogleSearchSystem extends SearchableSystemMetaData<GBaseObject, GBaseObject> {

	}

	private static final GoogleSearchSystem SYSTEM_METADATA = new GoogleSearchSystem();
	private static final List<SearchableSystemMetaData> allSystems = List.of(SYSTEM_METADATA);
	static {
		SYSTEMTYPE.setCode(GOOGLE_SEARCH_SERVICE);
		SYSTEMTYPE.setDescription("Google search service");
		SYSTEM.setCode(GOOGLE_SEARCH_SERVICE);
		SYSTEM.setDescription("Google search service");
		SYSTEM_METADATA.setCode(GOOGLE_SEARCH_SERVICE);
		SYSTEM_METADATA.setDescription("Google search service");
		SYSTEM_METADATA.setSystemType(SYSTEMTYPE);
		SYSTEM_METADATA.setSystemConfigurationReference(SYSTEM);
	}

	@Override
	public boolean isEnabled() {

		return !googleConfigDao.getConfigurations().isEmpty();
	}

	@Override
	public SearchableSystemMetaData findSystemById(String systemId) {

		return systemId != null && systemId.equalsIgnoreCase(GOOGLE_SEARCH_SERVICE) ? SYSTEM_METADATA : null;
	}

	@Override
	public String getId() {

		return GOOGLE_SEARCH_SERVICE;
	}

	@Override
	public String getDescription() {

		return "Google Web Search";
	}

	@Override
	public List<SearchableSystemMetaData> getSearchableSystems() {

		return isEnabled() ? allSystems : List.of();
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException {
		List<GoogleSearchConfig> configs = googleConfigDao.getConfigurations();
		if (configs.isEmpty())
			return List.of();
		GoogleSearchConfig config = configs.get(0);
		GoogleSearchRequest searchQuery = new GoogleSearchRequest();
		searchQuery.setQuery(query.getQueryText());
		searchQuery.setTopN(nEntryLimit);
		List<SearchResult> out = new ArrayList<SearchResult>();
		try {
			GoogleSearchResults data = googleSearchApi.search(config.getApiKey(), config.getCustomSearchEngineId(),
					searchQuery);
			if (data != null && data.getItems() != null) {
				for (GoogleSearchResultItem item : data.getItems()) {
					String link = item.getLink();
					String title = item.getTitle();
					String name = item.getDisplayLink();
					String snippet = item.getSnippet();
					if (title == null) {
						title = name;
					}
					SearchResult result = new SearchResult();
					result.setDescriptiveText(snippet);
					result.setResultReference(new SearchResultReference());
					result.getResultReference().setId(link);
					result.getResultReference().setUri(link);
					result.getResultReference().setName(name);
					result.getResultReference().setTitle(title);
					result.getResultReference().setExtension(tryArgueExtension(link));
					result.getResultReference().setContentType(tryArgueContentType(link));
					result.setSystemConfigurationCode(GOOGLE_SEARCH_SERVICE);
					setOriginOn(result);
					out.add(result);
				}
			}
		} catch (RestClientException | MalformedURLException | UnsupportedEncodingException | URISyntaxException e) {
			throw new IOException("Cannot run google search", e);
		}

		return out;

	}

	@Override
	public String getQueriesGenerationPromptUseCode() {

		return GoogleSearchHandlerConfig.GOOGLE_SEARCH_QUERY_EXTRACTION_PROMPT;
	}

	@Override
	public String getMessagingModuleId() {

		return "google-search-module";
	}

	@Override
	public List<CatalogueSample> getCataloguesListSample(String configurationCode) {
		return List.of(new CatalogueSample(configurationCode,
				"All publicly available catalogues can be searched over internet using google searches"));

	}

	@Override
	public String getProductId() {
		 
		return GOOGLE;
	}

}
