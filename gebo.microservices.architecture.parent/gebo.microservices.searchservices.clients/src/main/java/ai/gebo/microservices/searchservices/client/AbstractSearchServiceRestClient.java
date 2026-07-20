/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.searchservices.client;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.architecture.documents.access.DocumentContentStreamerException;
import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.search.controller.AggregateRequestBody;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.model.base.TypedInputStream;

/**
 * Topology-aware REST client base for a per-connector {@link ISearchService}
 * hosted on its own content microservice (jira/confluence/sharepoint/googledrive).
 * Mirrors the secrets/ACL cluster-client pattern: the target base url is resolved
 * per call from {@link GeboMicroserviceUrlResolver} (so it follows the
 * deployment's load-balancer/gateway/direct strategy), the caller's bearer token
 * is forwarded on the calling thread, and every method presents the same
 * synchronous {@code ISearchService} contract consumers already depend on.
 *
 * <p>
 * Three methods are answered locally rather than remoted:
 * {@link #getCustomResultsAggregationDataType()} returns the connector's shared
 * extraction {@code Class} (supplied by the concrete subclass);
 * {@link #loadSearchResult(SearchResult)} streams through the chunker's cache
 * ({@link IGDocumentContentStreamer}) rather than a dedicated search endpoint,
 * because a {@link SearchResult} is an
 * {@link ai.gebo.model.base.IGComponentOriginatedDocument} the documents-cache
 * already knows how to fetch and cache; and the static metadata getters are
 * memoized to avoid re-fetching immutable values on every deep-search iteration.
 * </p>
 *
 * @param <C> the connector's extraction data type
 */
public abstract class AbstractSearchServiceRestClient<C extends BaseSearchResultsExtractionDataType>
		implements ISearchService<C> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSearchServiceRestClient.class);

	protected static final ParameterizedTypeReference<List<SearchResult>> SEARCH_RESULT_LIST = new ParameterizedTypeReference<List<SearchResult>>() {
	};
	protected static final ParameterizedTypeReference<List<SearchableSystemMetaData>> SYSTEM_LIST = new ParameterizedTypeReference<List<SearchableSystemMetaData>>() {
	};
	protected static final ParameterizedTypeReference<List<CatalogueSample>> CATALOGUE_LIST = new ParameterizedTypeReference<List<CatalogueSample>>() {
	};

	protected final WebClient webClient;
	protected final GeboMicroserviceUrlResolver urlResolver;
	protected final IGeboCallerTokenPropagator tokenPropagator;
	protected final IGDocumentContentStreamer documentContentStreamer;
	protected final String microserviceId;
	protected final String basePath;
	protected final Class<C> extractionType;
	private final GeboTtlCache metadataCache;

	protected AbstractSearchServiceRestClient(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, IGDocumentContentStreamer documentContentStreamer,
			String microserviceId, String basePath, Class<C> extractionType, GeboTtlCache metadataCache) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.tokenPropagator = tokenPropagator;
		this.documentContentStreamer = documentContentStreamer;
		this.microserviceId = microserviceId;
		this.basePath = trimSlashes(basePath);
		this.extractionType = extractionType;
		this.metadataCache = metadataCache;
	}

	// ---- locally answered ---------------------------------------------------

	@Override
	public Class<C> getCustomResultsAggregationDataType() throws SearchServiceException {
		return extractionType;
	}

	@Override
	public TypedInputStream loadSearchResult(SearchResult result) throws IOException, SearchServiceException {
		try {
			return documentContentStreamer.streamContent(StreamingPurpose.INGESTING, result);
		} catch (DocumentContentStreamerException e) {
			throw new SearchServiceException("Failed to stream search result via the documents cache", e);
		}
	}

	// ---- static metadata (memoized) ----------------------------------------

	@Override
	public String getId() {
		return metadataCache.get(key("getId"), () -> callUnchecked("getId",
				() -> webClient.get().uri(uri("getId")).headers(this::applyCallerToken).retrieve()
						.bodyToMono(String.class).block()));
	}

	@Override
	public String getDescription() {
		return metadataCache.get(key("getDescription"), () -> callUnchecked("getDescription",
				() -> webClient.get().uri(uri("getDescription")).headers(this::applyCallerToken).retrieve()
						.bodyToMono(String.class).block()));
	}

	@Override
	public String getProductId() {
		return metadataCache.get(key("getProductId"), () -> callUnchecked("getProductId",
				() -> webClient.get().uri(uri("getProductId")).headers(this::applyCallerToken).retrieve()
						.bodyToMono(String.class).block()));
	}

	@Override
	public String getMessagingModuleId() {
		return metadataCache.get(key("getMessagingModuleId"), () -> callUnchecked("getMessagingModuleId",
				() -> webClient.get().uri(uri("getMessagingModuleId")).headers(this::applyCallerToken).retrieve()
						.bodyToMono(String.class).block()));
	}

	@Override
	public String getQueriesGenerationPromptUseCode() {
		return metadataCache.get(key("getQueriesGenerationPromptUseCode"),
				() -> callUnchecked("getQueriesGenerationPromptUseCode",
						() -> webClient.get().uri(uri("getQueriesGenerationPromptUseCode"))
								.headers(this::applyCallerToken).retrieve().bodyToMono(String.class).block()));
	}

	// ---- remoted ------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Resilient by design: returns {@code false} when the connector's controller
	 * microservice is not part of, or not running in, this installation — i.e. when
	 * its id resolves to no topology member and no {@code direct} entry, or when the
	 * remote is unreachable/unregistered. This lets brain's deep-search discovery
	 * ({@code findImplementations(x -> x.isEnabled())}) simply skip a search service
	 * whose microservice is absent, instead of failing the whole enumeration.
	 * </p>
	 */
	@Override
	public boolean isEnabled() {
		// Not a member of this installation's topology (and no direct override) -> absent.
		if (urlResolver.baseUrlForMicroserviceId(microserviceId).isEmpty()) {
			LOGGER.debug("Search microservice '{}' is not in the topology; treating its search service as disabled",
					microserviceId);
			return false;
		}
		try {
			Boolean enabled = webClient.get().uri(uri("isEnabled")).headers(this::applyCallerToken).retrieve()
					.bodyToMono(Boolean.class).block();
			return Boolean.TRUE.equals(enabled);
		} catch (RuntimeException ex) {
			// Controller microservice not running / unreachable in this installation.
			LOGGER.debug("Search microservice '{}' not reachable; treating its search service as disabled: {}",
					microserviceId, ex.toString());
			return false;
		}
	}

	@Override
	public SearchableSystemMetaData findSystemById(String systemId) throws SearchServiceException {
		return call("findSystemById",
				() -> webClient.get().uri(uri("findSystemById", "systemId", systemId)).headers(this::applyCallerToken)
						.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(SearchableSystemMetaData.class)
						.block());
	}

	@Override
	public List<SearchableSystemMetaData> getSearchableSystems() throws SearchServiceException {
		return call("getSearchableSystems",
				() -> webClient.get().uri(uri("getSearchableSystems")).headers(this::applyCallerToken)
						.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(SYSTEM_LIST).block());
	}

	@Override
	public List<SearchResult> search(SearchQuery query, SearchableSystemMetaData system, int nEntryLimit)
			throws IOException, SearchServiceException {
		String systemId = system != null ? system.getCode() : null;
		return call("search",
				() -> webClient.post()
						.uri(uri("search", Map.of("systemId", String.valueOf(systemId), "nEntryLimit",
								String.valueOf(nEntryLimit))))
						.headers(this::applyCallerToken).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).bodyValue(query).retrieve().bodyToMono(SEARCH_RESULT_LIST)
						.block());
	}

	@Override
	public C aggregate(C oldConsolidated, C consolidated) {
		AggregateRequestBody<C> body = new AggregateRequestBody<>();
		body.setOldConsolidated(oldConsolidated);
		body.setConsolidated(consolidated);
		return callUnchecked("aggregate",
				() -> webClient.post().uri(uri("aggregate")).headers(this::applyCallerToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(body)
						.retrieve().bodyToMono(extractionType).block());
	}

	@Override
	public SearchResultAnalisysOutcome extractRelatedAnalisysReferences(String systemId, C extractedData)
			throws IOException, SearchServiceException {
		return call("extractRelatedAnalisysReferences",
				() -> webClient.post().uri(uri("extractRelatedAnalisysReferences", "systemId", systemId))
						.headers(this::applyCallerToken).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).bodyValue(extractedData).retrieve()
						.bodyToMono(SearchResultAnalisysOutcome.class).block());
	}

	@Override
	public List<CatalogueSample> getCataloguesListSample(String configurationCode) throws SearchServiceException {
		return call("getCataloguesListSample",
				() -> webClient.get().uri(uri("getCataloguesListSample", "configurationCode", configurationCode))
						.headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
						.bodyToMono(CATALOGUE_LIST).block());
	}

	@Override
	public List<CatalogueSample> getCachedCatalogues(String systemConfigurationCode) throws SearchServiceException {
		return call("getCachedCatalogues",
				() -> webClient.get()
						.uri(uri("getCachedCatalogues", "systemConfigurationCode", systemConfigurationCode))
						.headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
						.bodyToMono(CATALOGUE_LIST).block());
	}

	@Override
	public List<CatalogueSample> getCachedCatalogues() throws SearchServiceException {
		return call("getCachedCatalogues",
				() -> webClient.get().uri(uri("getCachedCatalogues")).headers(this::applyCallerToken)
						.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(CATALOGUE_LIST).block());
	}

	// ---- plumbing -----------------------------------------------------------

	private String key(String endpoint) {
		return microserviceId + "/" + basePath + "/" + endpoint;
	}

	protected void applyCallerToken(HttpHeaders headers) {
		String token = tokenPropagator.currentToken();
		if (StringUtils.hasText(token)) {
			headers.setBearerAuth(token);
		} else {
			LOGGER.debug("No caller token to forward to search microservice '{}'; call goes out unauthenticated",
					microserviceId);
		}
	}

	protected URI uri(String endpoint) {
		return UriComponentsBuilder.fromUriString(baseUrl() + "/" + basePath + "/" + endpoint).build().encode().toUri();
	}

	protected URI uri(String endpoint, String paramName, String paramValue) {
		return UriComponentsBuilder.fromUriString(baseUrl() + "/" + basePath + "/" + endpoint)
				.queryParam(paramName, paramValue).build().encode().toUri();
	}

	protected URI uri(String endpoint, Map<String, String> params) {
		UriComponentsBuilder b = UriComponentsBuilder.fromUriString(baseUrl() + "/" + basePath + "/" + endpoint);
		params.forEach(b::queryParam);
		return b.build().encode().toUri();
	}

	protected String baseUrl() {
		Optional<String> baseUrl = urlResolver.baseUrlForMicroserviceId(microserviceId);
		return baseUrl.orElseThrow(() -> new IllegalStateException("Cannot resolve the base url of the search "
				+ "microservice '" + microserviceId + "': it is not a member of the topology and has no 'direct' "
				+ "entry (gebo.microservices.topology.url.direct)."));
	}

	protected <T> T call(String operation, Supplier<T> remoteCall) throws SearchServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST {} on search microservice '{}'", operation, microserviceId);
		}
		try {
			return remoteCall.get();
		} catch (WebClientResponseException ex) {
			throw new SearchServiceException(
					"Remote " + operation + " failed: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString(), ex);
		} catch (RuntimeException ex) {
			throw new SearchServiceException("Remote " + operation + " failed", ex);
		}
	}

	protected <T> T callUnchecked(String operation, Supplier<T> remoteCall) {
		try {
			return remoteCall.get();
		} catch (WebClientResponseException ex) {
			throw new IllegalStateException(
					"Remote " + operation + " failed: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString(), ex);
		}
	}

	protected static String trimSlashes(String path) {
		String trimmed = path == null ? "" : path.trim();
		while (trimmed.startsWith("/")) {
			trimmed = trimmed.substring(1);
		}
		while (trimmed.endsWith("/")) {
			trimmed = trimmed.substring(0, trimmed.length() - 1);
		}
		return trimmed;
	}
}
