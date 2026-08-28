package ai.gebo.architecture.search.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.model.WebSearchQueryObject;
import ai.gebo.architecture.search.model.WebSearchResultsExtractionData;
import ai.gebo.architecture.search.model.WebSearchResultsExtractionData.RelevantLink;
import ai.gebo.model.base.TypedInputStream;

/**
 * Base for every web-search connector. Generic over the native query type
 * {@code N} so a provider that exposes richer, engine-specific options (Tavily
 * search depth, Brave freshness, SearXNG categories, ...) can declare its own
 * {@link INativeQueryObject} subtype for the LLM to fill via structured output,
 * while plain providers keep using {@link WebSearchQueryObject}. The default
 * {@link #nativeSearch} treats the native object as a bag of query strings
 * ({@link INativeQueryObject#relevantKeywords()}); providers that carry options
 * override it to pass them through.
 */
public abstract class AbstractWebSearchServiceImpl<N extends INativeQueryObject>
		implements INativeSearchService<WebSearchResultsExtractionData, N> {

	/**
	 * Generic, provider-agnostic description for every web-search provider. The
	 * pipeline surfaces web search to the routing LLM and the deep-search menu as a
	 * single "Web search" choice, regardless of which provider (Google, Brave,
	 * SerpApi, Tavily, SearXNG, ...) is actually configured behind it. Individual
	 * providers must not override {@link #getDescription()}.
	 */
	public static final String WEB_SEARCH_DESCRIPTION = "Web search";

	/**
	 * Provider-agnostic name and description of the web-search LLM tool. Only one
	 * web-search provider is active at a time, so every provider exposes the same
	 * generic tool to the model: the chatbot decides to "search the web" without
	 * ever seeing which vendor (Google, Brave, SerpApi, Tavily, SearXNG, ...) is
	 * configured behind it.
	 */
	public static final String WEB_SEARCH_TOOL_NAME = "searchWeb";
	public static final String WEB_SEARCH_TOOL_DESCRIPTION = "Search the public web for current, external information relevant to the user's question, and return the most relevant results.";

	protected int SocketTimeout = 20000;
	protected int ConnectTimeout = 10000;

	@Override
	public String getDescription() {
		return WEB_SEARCH_DESCRIPTION;
	}

	protected String tryArgueContentType(String link) {
		String contentType = LinkTypeGuesser.tryArgueContentType(link);
		if (contentType == null)
			contentType = "text/html";
		return contentType;
	}

	protected String tryArgueExtension(String link) {
		String extension = LinkTypeGuesser.tryArgueExtension(link);
		if (extension == null)
			extension = ".html";
		else
			extension = "." + extension;
		return extension;
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId,
			WebSearchResultsExtractionData extractedData) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		if (extractedData.getExtractedRelevantLinks() != null) {
			for (RelevantLink item : extractedData.getExtractedRelevantLinks()) {
				String link = item.getUrl();
				String title = item.getTitle();
				String name = item.getDisplayText();
				SearchResult result = new SearchResult();
				result.setDescriptiveText(title);
				result.setResultReference(new SearchResultReference());
				result.getResultReference().setId(link);
				result.getResultReference().setUri(link);
				result.getResultReference().setName(name);
				result.getResultReference().setTitle(title);
				result.getResultReference().setExtension(tryArgueExtension(link));
				result.getResultReference().setContentType(tryArgueContentType(link));
				result.setSystemConfigurationCode(getId());
				setOriginOn(result);
				results.add(result);
			}
		}
		return new SearchResultAnalisysOutcome(extractedData.getExtractedRelatedSearches(), results);
	}

	@Override
	public WebSearchResultsExtractionData aggregate(WebSearchResultsExtractionData oldConsolidated,
			WebSearchResultsExtractionData cumulated) {

		WebSearchResultsExtractionData newResult = new WebSearchResultsExtractionData();
		newResult = basicAggregate(oldConsolidated, cumulated, newResult);
		if (oldConsolidated != null) {
			newResult.getExtractedRelevantLinks().addAll(oldConsolidated.getExtractedRelevantLinks());
			newResult.getExtractedRelatedSearches().addAll(oldConsolidated.getExtractedRelatedSearches());

		}
		if (cumulated != null) {
			newResult.getExtractedRelevantLinks().addAll(cumulated.getExtractedRelevantLinks());
			newResult.getExtractedRelatedSearches().addAll(cumulated.getExtractedRelatedSearches());
		}
		return newResult;
	}

	@Override
	public Class<WebSearchResultsExtractionData> getCustomResultsAggregationDataType() throws SearchServiceException {

		return WebSearchResultsExtractionData.class;
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

			return TypedInputStream.of(response.getEntity().getContent(), contentType, tryArgueExtension(result));

		} else {
			return TypedInputStream.of(InputStream.nullInputStream(), "text/html", ".html");

		}

	}

	private String tryArgueExtension(SearchResult result) {
		String url = result.getResultReference() != null ? result.getResultReference().getUri() : null;
		return url == null ? null : tryArgueExtension(url);
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

	public AbstractWebSearchServiceImpl() {
		super();
	}

	@Override
	public List<SearchResult> nativeSearch(N query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {

		List<SearchResult> results = new ArrayList<>();
		if (query != null && query.relevantKeywords() != null && !query.relevantKeywords().isEmpty()) {
			for (String text : query.relevantKeywords()) {
				SearchQuery searchQuery = new SearchQuery();
				searchQuery.setQueryText(text);
				List<SearchResult> res = search(searchQuery, system, nEntryLimit);
				if (res != null) {
					results.addAll(res);
				}
			}
		}
		return results;
	}

	private CloseableHttpClient createClient() {
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH)
				.setSocketTimeout(SocketTimeout).setConnectTimeout(ConnectTimeout).build();
		CookieStore cookieStore = new BasicCookieStore();
		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(cookieStore);

		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
				.setDefaultCookieStore(cookieStore).build();

		return httpClient;
	}

	private HttpGet createGetRequestFor(SearchResult result) throws UnsupportedEncodingException {
		HttpGet getRequest = new HttpGet(result.getResultReference().getUri());
		String referer = "google.com";
		getRequest.setHeader("Referer", referer);
		return getRequest;
	}

}