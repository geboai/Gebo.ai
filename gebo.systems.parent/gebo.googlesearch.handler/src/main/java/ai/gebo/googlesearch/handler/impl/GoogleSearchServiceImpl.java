package ai.gebo.googlesearch.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.LinkTypeGuesser;
import ai.gebo.architecture.search.service.TypedInputStream;
import ai.gebo.googlesearch.handler.config.GoogleSearchHandlerConfig;
import ai.gebo.googlesearch.handler.model.GoogleSearchConfig;
import ai.gebo.googlesearch.handler.model.GoogleSearchRequest;
import ai.gebo.googlesearch.handler.model.GoogleSearchResultItem;
import ai.gebo.googlesearch.handler.model.GoogleSearchResults;
import ai.gebo.googlesearch.handler.model.GoogleSearchResultsExtractionData;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GoogleSearchServiceImpl implements ISearchService<GoogleSearchResultsExtractionData> {
	public static final String GOOGLE_SEARCH_SERVICE = "google-search-service";
	private final GoogleSearchConfigDaoImpl googleConfigDao;
	private final GoogleSearchApi googleSearchApi;
	private final GoogleSearchHandlerConfig config;
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

		return "Google Search";
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
					result.setSystemHandlerId(GOOGLE_SEARCH_SERVICE);
					out.add(result);
				}
			}
		} catch (RestClientException | MalformedURLException | UnsupportedEncodingException | URISyntaxException e) {
			throw new IOException("Cannot run google search", e);
		}

		return out;

	}

	private String tryArgueContentType(String link) {
		String contentType = LinkTypeGuesser.tryArgueContentType(link);
		if (contentType == null)
			contentType = "text/html";
		return contentType;
	}

	private String tryArgueExtension(String link) {
		String extension = LinkTypeGuesser.tryArgueExtension(link);
		if (extension == null)
			extension = ".html";
		else
			extension = "." + extension;
		return extension;
	}

	@Override
	public TypedInputStream loadSearchResult(SearchResult result) throws IOException {
		HttpGet request = createGetRequestFor(result);
		CloseableHttpClient client = createClient();
		final HttpResponse response = client.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();

		if (responseCode >= 200 && responseCode < 400) {
			String encoding = getEncoding(response);
			String contentType = "text/html";
			if (response.getEntity() != null && response.getEntity().getContentType() != null) {
				contentType = response.getEntity().getContentType().getValue();
				if (contentType != null) {
					int idx = 0;
					if ((idx = contentType.trim().indexOf(";")) > 0) {
						contentType = contentType.substring(0, idx);
					}
				}
			}
			encoding = "UTF-8";

			return TypedInputStream.of(response.getEntity().getContent(), contentType);

		} else {
			return TypedInputStream.of(InputStream.nullInputStream(), "text/html");

		}

	}

	private String getEncoding(HttpResponse response) {
		Header contentType = response.getFirstHeader("content-type");
		String encoding = "UTF-8";
		if (contentType.getValue() != null) {
			int encodingOffset = contentType.getValue().indexOf("charset");
			if (encodingOffset >= 0) {
				String remaining = contentType.getValue().substring(encodingOffset);
				char buffer[] = remaining.toCharArray();
				for (int index = 0; index < buffer.length; index++) {
					char ch = buffer[index];
					if (Character.isLetter(ch)) {
						encoding = new String(buffer, index, buffer.length - index);
					}
				}
			}
		}
		return encoding;
	}

	private CloseableHttpClient createClient() {
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();
		CookieStore cookieStore = new BasicCookieStore();
		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(cookieStore);

		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
				.setDefaultCookieStore(cookieStore).build();

		return httpClient;
	}

	@Override
	public Class<GoogleSearchResultsExtractionData> getCustomResultsAggregationDataType() {

		return GoogleSearchResultsExtractionData.class;
	}

	@Override
	public GoogleSearchResultsExtractionData aggregate(GoogleSearchResultsExtractionData oldConsolidated,
			GoogleSearchResultsExtractionData cumulated) {
		if (oldConsolidated == null && cumulated == null)
			return new GoogleSearchResultsExtractionData();
		if (cumulated == null)
			return oldConsolidated;
		if (oldConsolidated == null)
			return cumulated;
		GoogleSearchResultsExtractionData newResult = new GoogleSearchResultsExtractionData();
		newResult.setExtractedRelevantContent(cumulated.getExtractedRelevantContent());
		newResult.setContentIsRelevant(cumulated.getContentIsRelevant());
		newResult.getExtractedRelevantLinks().addAll(oldConsolidated.getExtractedRelevantLinks());
		newResult.getExtractedRelevantLinks().addAll(cumulated.getExtractedRelevantLinks());
		return newResult;
	}

	private HttpGet createGetRequestFor(SearchResult result) throws UnsupportedEncodingException {
		HttpGet getRequest = new HttpGet(result.getResultReference().getUri());
		String referer = "google.com";
		getRequest.setHeader("Referer", referer);
		return getRequest;
	}

	@Override
	public List<SearchWithResults> cleanAndRemoveDuplicated(List<SearchWithResults> queryResults) {

		final Map<String, Boolean> unique = new HashMap<String, Boolean>();
		List<SearchWithResults> outList = new ArrayList<SearchWithResults>();
		for (SearchWithResults searchWithResults : queryResults) {
			final SearchWithResults newCopy = new SearchWithResults();
			newCopy.setSearchQuery(searchWithResults.getSearchQuery());
			newCopy.setResults(new ArrayList<SearchResult>());
			searchWithResults.getResults().forEach(x -> {
				if (x.getResultReference() != null && x.getResultReference().getUri() != null) {
					if (!unique.containsKey(x.getResultReference().getUri())) {
						newCopy.getResults().add(x);
						unique.put(x.getResultReference().getUri(), true);
					}
				}
			});
			if (!newCopy.getResults().isEmpty()) {
				outList.add(newCopy);
			}
		}
		return outList;
	}

	@Override
	public String getQueriesExtractionPrompt() {

		return config.getQueryExtractionPrompt();
	}

}
